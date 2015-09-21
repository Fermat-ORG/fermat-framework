package com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.PublicKey;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.BitcoinNetworkConfiguration;
import com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.exceptions.InvalidAccountNumberException;
import com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.exceptions.VaultKeyHierarchyException;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.BitcoinCryptoNetworkManager;
import com.google.common.collect.ImmutableList;

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
     * Generates the Account number 0 which is the ones used by the vault (m/0)
     * New RedeemPoints will be m/1, m/2,..., m/n
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

    public void addNewRedeemPoint(int accountNumber){

    }

    /**
     * Generates a new CryptoAddress from the passed account number by deriving a new key from the hierarchy.
     * @param accountNumber
     * @return
     * @throws InvalidAccountNumberException
     */
    public CryptoAddress getNewCryptoAddressFromAccount(int accountNumber) throws InvalidAccountNumberException {
        /**
         * I validate that I have this account number registered.
         */
        if (!isValidAccountNumber(accountNumber))
            throw new InvalidAccountNumberException(InvalidAccountNumberException.DEFAULT_MESSAGE, null, "Account Number: " + accountNumber, "invalid account number.");

        /**
         * I create the path that I will used to retrieve the key
         */
        List<ChildNumber> path = ImmutableList.of(new ChildNumber(accountNumber, true), new ChildNumber(getNextAvailablePositionFromAccount(accountNumber), true));
        DeterministicKey keyAtPosition = masterHierarchy.deriveChild(path, false, true, ChildNumber.ZERO);

        /**
         * With the key and get I generate the bitcoin Address
         */
        CryptoAddress cryptoAddress = new CryptoAddress(keyAtPosition.toAddress(BitcoinNetworkConfiguration.NETWORK_PARAMETERS).toString(), CryptoCurrency.BITCOIN);

        /**
         * I update the next available position in the path
         */
        setNextAvailablePositionFromAccont(accountNumber);


        return cryptoAddress;
    }

    public PublicKey getNewPublicKeyFromAccount(int accountNumber){
        return null;
    }

    public DeterministicKey getNewExtendedPublicKeyFromAccount(int accountNumber){
        return null;
    }

    private boolean isValidAccountNumber(int accountNumber){
        return false;
    }

    public CryptoAddress getUsedCryptoAddressFromAccount(int accountNumber, int position){
        return null;
    }

     private int getNextAvailablePositionFromAccount (int accountNumber){
        return 0;
    }

    private void setNextAvailablePositionFromAccont(int accountNumber){

    }
}


