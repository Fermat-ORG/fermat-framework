package com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.BitcoinNetworkSelector;
import com.bitdubai.fermat_bch_api.layer.crypto_network.enums.BlockchainNetworkType;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.exceptions.GetNewCryptoAddressException;
import com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.database.AssetVaultCryptoVaultDao;
import com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.exceptions.InconsistentDatabaseResultException;
import com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.exceptions.InvalidChainNumberException;
import com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.exceptions.VaultKeyHierarchyException;
import com.google.common.collect.ImmutableList;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicHierarchy;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.crypto.MnemonicException;
import org.bitcoinj.wallet.DeterministicSeed;

import java.util.List;
import java.util.UUID;

/**
 * Created by rodrigo on 9/20/15.
 */
class VaultKeyHierarchy implements DealsWithPluginDatabaseSystem{
    DeterministicSeed seed;
    DeterministicKey rootKey;
    DeterministicHierarchy masterHierarchy;

    UUID pluginId;

    /**
     * DealsWithPluginDatabaseSystem interface variables and implementation
     */
    PluginDatabaseSystem pluginDatabaseSystem;
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * Constructor. Creates the vault key Hierarchy following these steps:
     * Receives the seed that was created or stored for this vault.
     * Uses the seed to generate a Master Key.
     * Create the Hierarchy with the master Key
     * Generates the Chain number 0 which is the ones used by the vault (m/AccountNumber/ChainNumber)
     * New RedeemPoints will be m/0/0, m/0/1,..., m/0/n
     * @param seed the seed used to create the master key and the hierarchy.
     */
    public VaultKeyHierarchy(DeterministicSeed seed, PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) throws VaultKeyHierarchyException {
        this.seed = seed;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;

        try {
            /**
             * I validate the seed is correct.
             */
            seed.check();
            /**
             * Create the master node, which is m
             */
            createMasterNode();
        } catch (MnemonicException e) {
            throw new VaultKeyHierarchyException(VaultKeyHierarchyException.DEFAULT_MESSAGE, e, "The mnemonic code used to generate the hierarchy, is not valid.", "re generate the seed if vault balance is zero.");
        }
    }

    /**
     * this will create the master hierarchy using the saved seed.
     */
    private void createMasterNode(){
        rootKey = HDKeyDerivation.createMasterPrivateKey(seed.getSeedBytes());
        masterHierarchy = new DeterministicHierarchy(rootKey);
    }

    public void addNewRedeemPoint(){

    }

    /**
     * Generates a new CryptoAddress from the passed chain number by deriving a new key from the hierarchy.
     * @param chainNumber
     * @return
     * @throws InvalidChainNumberException
     */
    public CryptoAddress getNewCryptoAddressFromChain(BlockchainNetworkType blockchainNetworkType,int chainNumber) throws GetNewCryptoAddressException {
        /**
         * I validate that I have this Chain number registered.
         */
        int accountNumber = BitcoinNetworkSelector.getNetworkAccountNumber(blockchainNetworkType);
        try {
            if (!isValidChainNumber(accountNumber, chainNumber))
                throw new GetNewCryptoAddressException(GetNewCryptoAddressException.DEFAULT_MESSAGE, null, "Chain Number: " + chainNumber, "invalid account number.");
        } catch (CantExecuteDatabaseOperationException e) {
            throw new GetNewCryptoAddressException (GetNewCryptoAddressException.DEFAULT_MESSAGE, e, "error trying to get a result from database." , null);
        }

        /**
         * I create the path that I will used to retrieve the key.
         * Depending on the crypto network, I will get the key from different branches.
         * m/0 Production
         * m/1 Test
         * m/2 RegTest
         */
        List<ChildNumber> path = null;
        try {
            path = ImmutableList.of(new ChildNumber(accountNumber, true), new ChildNumber(chainNumber, true), new ChildNumber(getAvailablePositionFromChain(accountNumber, chainNumber), true));
        } catch (InconsistentDatabaseResultException | CantExecuteDatabaseOperationException e) {
            throw new GetNewCryptoAddressException (GetNewCryptoAddressException.DEFAULT_MESSAGE, e, "couldn't form the path of the hierarchy chain. There was a problem in the database.", null);
        }
        DeterministicKey keyAtPosition = masterHierarchy.deriveChild(path, false, true, ChildNumber.ZERO);

        /**
         * Depending on the BlockchainNetworkType specified, I form the correct Address
         */
        NetworkParameters networkParameters = BitcoinNetworkSelector.getNetworkParameter(blockchainNetworkType);
        CryptoAddress cryptoAddress = new CryptoAddress(keyAtPosition.toAddress(networkParameters).toString(), CryptoCurrency.BITCOIN);

        /**
         * I update the next available position in the path
         */
        try {
            setNextAvailablePositionForChain(accountNumber, chainNumber);
        } catch (InconsistentDatabaseResultException e) {
            throw new GetNewCryptoAddressException(GetNewCryptoAddressException.DEFAULT_MESSAGE, e, "The specified Account and Chain numbers doesn't exists.", "data inconsistency.");
        } catch (CantExecuteDatabaseOperationException e) {
            throw new GetNewCryptoAddressException(GetNewCryptoAddressException.DEFAULT_MESSAGE, e, "this was a database problem.", "DB plugin issue.");
        }


        return cryptoAddress;
    }


    /**
     * Validates if the account and chain combination is valid
     * @param accountNumber
     * @param chainNumber
     * @return
     * @throws CantExecuteDatabaseOperationException
     */
    private boolean isValidChainNumber(int accountNumber, int chainNumber) throws CantExecuteDatabaseOperationException {
        return getAssetVaultCryptoVaultDao().isValidChainNumber(accountNumber, chainNumber);
    }

    /**
     * Will get the position available to use to get a new key
     * @param accountNumber
     * @param chainNumber
     * @return
     * @throws InconsistentDatabaseResultException
     * @throws CantExecuteDatabaseOperationException
     */
     private int getAvailablePositionFromChain (int accountNumber, int chainNumber) throws InconsistentDatabaseResultException, CantExecuteDatabaseOperationException {
        return getAssetVaultCryptoVaultDao().getAvailableKeyPosition(accountNumber, chainNumber);
    }

    /**
     * Will increase by 1 the next available position to get a new key
     * @param accountNumber
     * @param chainNumber
     * @throws InconsistentDatabaseResultException
     * @throws CantExecuteDatabaseOperationException
     */
    private void setNextAvailablePositionForChain(int accountNumber, int chainNumber) throws InconsistentDatabaseResultException, CantExecuteDatabaseOperationException {
        getAssetVaultCryptoVaultDao().setNewAvailableKeyPosition(accountNumber, chainNumber);
    }

    /**
     * gets the DAO object to access the database methods.
     * @return
     */
    private AssetVaultCryptoVaultDao getAssetVaultCryptoVaultDao(){
        AssetVaultCryptoVaultDao assetVaultCryptoVaultDao = new AssetVaultCryptoVaultDao(this.pluginId, this.pluginDatabaseSystem);
        return assetVaultCryptoVaultDao;
    }
}


