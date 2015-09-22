package com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.BitcoinNetworkSelector;
import com.bitdubai.fermat_bch_api.layer.crypto_network.enums.BlockchainNetworkType;
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

/**
 * Created by rodrigo on 9/20/15.
 */
class VaultKeyHierarchy implements DealsWithPluginDatabaseSystem{
    DeterministicSeed seed;
    DeterministicKey rootKey;
    DeterministicHierarchy masterHierarchy;

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
    public VaultKeyHierarchy(DeterministicSeed seed, PluginDatabaseSystem pluginDatabaseSystem) throws VaultKeyHierarchyException {
        this.seed = seed;
        this.pluginDatabaseSystem = pluginDatabaseSystem;

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
    public CryptoAddress getNewCryptoAddressFromChain(BlockchainNetworkType blockchainNetworkType,int chainNumber) throws InvalidChainNumberException {
        /**
         * I validate that I have this Chain number registered.
         */
        if (!isValidChainNumber(chainNumber))
            throw new InvalidChainNumberException(InvalidChainNumberException.DEFAULT_MESSAGE, null, "Chain Number: " + chainNumber, "invalid account number.");

        /**
         * I create the path that I will used to retrieve the key.
         * Depending on the crypto network, I will get the key from different branches.
         * m/0 Production
         * m/1 Test
         * m/2 RegTest
         */
        List<ChildNumber> path = ImmutableList.of(new ChildNumber(BitcoinNetworkSelector.getNetworkAccountNumber(blockchainNetworkType), true),new ChildNumber(chainNumber, true), new ChildNumber(getAvailablePositionFromChain(chainNumber), true));
        DeterministicKey keyAtPosition = masterHierarchy.deriveChild(path, false, true, ChildNumber.ZERO);

        /**
         * Depending on the BlockchainNetworkType specified, I form the correct Address
         */
        NetworkParameters networkParameters = BitcoinNetworkSelector.getNetworkParameter(blockchainNetworkType);
        CryptoAddress cryptoAddress = new CryptoAddress(keyAtPosition.toAddress(networkParameters).toString(), CryptoCurrency.BITCOIN);

        /**
         * I update the next available position in the path
         */
        setNextAvailablePositionForChain(chainNumber);


        return cryptoAddress;
    }


    private boolean isValidChainNumber(int chainNumber){
        return true;
    }

    public CryptoAddress getUsedCryptoAddressFromChain(int chainNumber, int position){
        return null;
    }

     private int getAvailablePositionFromChain (int chainNumber){
        return 0;
    }

    private void setNextAvailablePositionForChain(int chainNumber){

    }
}


