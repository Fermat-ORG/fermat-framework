package com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.BitcoinNetworkSelector;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantBroadcastTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetGenesisTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.exceptions.CantSendAssetBitcoinsToUserException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.exceptions.GetNewCryptoAddressException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.vault_seed.VaultSeedGenerator;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.vault_seed.exceptions.CantCreateAssetVaultSeed;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.vault_seed.exceptions.CantLoadExistingVaultSeed;
import com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.database.AssetsOverBitcoinCryptoVaultDao;
import com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.exceptions.CantInitializeAssetsOverBitcoinCryptoVaultDatabaseException;
import com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.exceptions.InvalidSeedException;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.InsufficientMoneyException;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.crypto.MnemonicException;
import org.bitcoinj.wallet.DeterministicSeed;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_bch_plugin.layer.cryptovault.assetsoverbitcoin.developer.bitdubai.version_1.structure.AssetCryptoVaultManager</code>
 * In in charge of creating the master key from the generated seed and starts the agents that will create the
 * key hierarchy and the Hierarchy maintainer agent.<p/>
 * <p/>
 *
 * Created by Rodrigo Acosta - (acosta_rodrigo@hotmail.com) on 06/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class AssetCryptoVaultManager  {
    /**
     * AssetVaultManager variables
     */
    UUID pluginId;
    VaultKeyHierarchyGenerator vaultKeyHierarchyGenerator;
    AssetsOverBitcoinCryptoVaultDao dao;


    /**
     * File name information where the seed will be stored
     */
    private final String ASSET_VAULT_SEED_FILEPATH = "AssetVaultSeed";
    private final String ASSET_VAULT_SEED_FILENAME;


    /**
     * platform interfaces definition
     */
    BitcoinNetworkManager bitcoinNetworkManager;
    PluginFileSystem pluginFileSystem;
    PluginDatabaseSystem pluginDatabaseSystem;


    /**
     * Constructor
     * @param pluginId
     * @param pluginFileSystem
     */
    public AssetCryptoVaultManager(UUID pluginId,
                                   PluginFileSystem pluginFileSystem,
                                   PluginDatabaseSystem pluginDatabaseSystem,
                                   String seedFileName,
                                   BitcoinNetworkManager bitcoinNetworkManager) throws InvalidSeedException {

        this.pluginId = pluginId;
        ASSET_VAULT_SEED_FILENAME = seedFileName;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.bitcoinNetworkManager = bitcoinNetworkManager;

        /**
         * I will let the VaultKeyHierarchyGenerator to start and generate the hierarchy in a new thread
         */
        vaultKeyHierarchyGenerator = new VaultKeyHierarchyGenerator(getAssetVaultSeed(), pluginDatabaseSystem, this.bitcoinNetworkManager, this.pluginId);
        new Thread(vaultKeyHierarchyGenerator).start();
    }

    /**
     * Creates a new Seed or loads and existing one for the user logged.
     * @return
     * @throws CantCreateAssetVaultSeed
     * @throws CantLoadExistingVaultSeed
     */
    private DeterministicSeed getAssetVaultSeed()  throws InvalidSeedException{
        try{
            VaultSeedGenerator vaultSeedGenerator = new VaultSeedGenerator(this.pluginFileSystem, this.pluginId, ASSET_VAULT_SEED_FILEPATH, ASSET_VAULT_SEED_FILENAME);
            if (!vaultSeedGenerator.seedExists())
                vaultSeedGenerator.create();
            else
                vaultSeedGenerator.load();

            DeterministicSeed seed = new DeterministicSeed(vaultSeedGenerator.getSeedBytes(), vaultSeedGenerator.getMnemonicCode(), vaultSeedGenerator.getCreationTimeSeconds());
            seed.check();
            return seed;
        } catch (CantLoadExistingVaultSeed cantLoadExistingVaultSeed) {
            throw new InvalidSeedException(InvalidSeedException.DEFAULT_MESSAGE, cantLoadExistingVaultSeed, "there was an error trying to load an existing seed.", null);
        } catch (CantCreateAssetVaultSeed cantCreateAssetVaultSeed) {
            throw new InvalidSeedException(InvalidSeedException.DEFAULT_MESSAGE, cantCreateAssetVaultSeed, "there was an error trying to create a new seed.", null);
        } catch (MnemonicException e) {
            throw new InvalidSeedException(InvalidSeedException.DEFAULT_MESSAGE, e, "the seed that was generated is not valid.", null);
        }
    }


    /**
     * Will get a new crypto address from the asset vault account.
     * @param blockchainNetworkType
     * @return
     * @throws GetNewCryptoAddressException
     */

    public CryptoAddress getNewAssetVaultCryptoAddress(BlockchainNetworkType blockchainNetworkType) throws GetNewCryptoAddressException {
        /**
         * I create the account manually instead of getting it from the database because this method always returns addresses
         * from the asset vault account with Id 0.
         */
        HierarchyAccount vaultAccount = new HierarchyAccount(0, "Asset Vault");
        return vaultKeyHierarchyGenerator.getVaultKeyHierarchy().getBitcoinAddress(blockchainNetworkType, vaultAccount);
    }

    public long getAvailableBalanceForTransaction(String genesisTransaction) {
        return 0;
    }

    /**
     * Sends bitcoins to the specified address. It will create a new wallet object from the Keys generated from the
     * VaultKeyHierarchyGenerator and set an UTXO provider from the CryptoNetwork. Using my UTXO, I will create a new
     * transaction and broadcast it on the corresponding network.
     * @param genesisTransactionId
     * @param addressTo
     * @throws CantSendAssetBitcoinsToUserException
     */
    public void sendBitcoinAssetToUser(String genesisTransactionId, CryptoAddress addressTo) throws CantSendAssetBitcoinsToUserException {
        /**
         * I get the network Parameters from the passed address.
         */
        NetworkParameters networkParameters = null;
        Wallet wallet = null;
        Address address = null;
        try {
            address = new Address(networkParameters, addressTo.getAddress());
            networkParameters = address.getParameters();
        } catch (AddressFormatException e) {
            /**
             * if the address is not valid, I can't go on
             */
            throw new CantSendAssetBitcoinsToUserException(CantSendAssetBitcoinsToUserException.DEFAULT_MESSAGE, e, "The specified address is not valid. " + addressTo.getAddress(), "invalid address.");
        }


        /**
         * I will make sure that this network is being monitored by activating it
         */
        BlockchainNetworkType blockchainNetworkType = BitcoinNetworkSelector.getBlockchainNetworkType(networkParameters);
        try {
            getDao().setActiveNetworkType(blockchainNetworkType);
        } catch (CantExecuteDatabaseOperationException e) {
            e.printStackTrace();
        }


        /**
         * I create the wallet from my seed, and imported the Keys that are being used at the Crypto Network. This list was generated and is maintained
         * by the HierarchyMaintainer and passed to the Hierarchy Generator.
         */
        try {
            wallet = Wallet.fromSeed(networkParameters, getAssetVaultSeed());
            wallet.importKeys(vaultKeyHierarchyGenerator.getAllAccountsKeyList());
        } catch (InvalidSeedException e) {
            e.printStackTrace();
        }

        /**
         * I get the UTXO provider from the Crypto Network and set it to the wallet with the keys
         */
        wallet.setUTXOProvider(bitcoinNetworkManager.getUTXOProvider(blockchainNetworkType));

        /**
         * I get the genesis transaction and the value that was sent to me to resend this value substracting the fee
         */
        Transaction transaction = wallet.getTransaction(Sha256Hash.of(genesisTransactionId.getBytes()));
        long value = transaction.getValueSentToMe(wallet).getValue();
        value = value - 5000;

        Wallet.SendRequest request = Wallet.SendRequest.to(address, Coin.valueOf(value));
        try {
            wallet.completeTx(request);
            wallet.commitTx(request.tx);

            bitcoinNetworkManager.broadcastTransaction(blockchainNetworkType, request.tx);
        } catch (InsufficientMoneyException e) {
            e.printStackTrace();
        } catch (CantBroadcastTransactionException e) {
            e.printStackTrace();
        }


    }

    /**
     * instantiates and creates the dao object to access the database
     * @return
     */
    private AssetsOverBitcoinCryptoVaultDao getDao(){
        if (dao == null){
            try {
                dao = new AssetsOverBitcoinCryptoVaultDao(this.pluginDatabaseSystem, this.pluginId);
            } catch (CantInitializeAssetsOverBitcoinCryptoVaultDatabaseException e) {
                e.printStackTrace();
            }
        }

        return dao;
    }
}
