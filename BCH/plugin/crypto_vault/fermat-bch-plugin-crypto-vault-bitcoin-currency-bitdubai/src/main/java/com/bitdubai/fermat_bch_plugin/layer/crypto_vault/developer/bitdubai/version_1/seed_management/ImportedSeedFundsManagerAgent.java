package com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.seed_management;

import com.bitdubai.fermat_api.AbstractAgent;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantBroadcastTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantStoreTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.manager.BlockchainManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.HierarchyAccount.HierarchyAccount;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.HierarchyAccount.HierarchyAccountType;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.vault_seed.VaultSeedGenerator;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.BitcoinFee;
import com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.database.BitcoinCurrencyCryptoVaultDao;
import com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.BitcoinCurrencyCryptoVaultManager;
import com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.VaultKeyHierarchyGenerator;
import com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.util.BitcoinBlockchainNetworkSelector;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.InsufficientMoneyException;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.WalletTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by rodrigo on 7/14/16.
 */
public class ImportedSeedFundsManagerAgent extends AbstractAgent{
    private final BlockchainManager bitcoinNetworkManager;
    private final BitcoinCurrencyCryptoVaultDao dao;
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final PluginFileSystem pluginFileSystem;
    private final UUID pluginId;
    private Agent agent;
    private final String CRYPTO_VAULT_SEED_FILEPATH;
    private final String CRYPTO_VAULT_SEED_FILENAME;


    /**
     * constructor
     * @param sleepTime
     * @param timeUnit
     * @param bitcoinNetworkManager
     * @param dao

     * @param pluginDatabaseSystem
     * @param pluginId
     */
    public ImportedSeedFundsManagerAgent(long sleepTime, TimeUnit timeUnit, BlockchainManager bitcoinNetworkManager, BitcoinCurrencyCryptoVaultDao dao, PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId, PluginFileSystem pluginFileSystem, String vaultSeedFilePath, String vaultSeedFileName) {
        super(sleepTime, timeUnit, 1);
        this.bitcoinNetworkManager = bitcoinNetworkManager;
        this.dao = dao;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.pluginFileSystem = pluginFileSystem;
        this.CRYPTO_VAULT_SEED_FILENAME = vaultSeedFileName;
        this.CRYPTO_VAULT_SEED_FILEPATH = vaultSeedFilePath;

        agent = new Agent(this.bitcoinNetworkManager, this.dao, this.pluginDatabaseSystem, this.pluginId, this.pluginFileSystem, this.CRYPTO_VAULT_SEED_FILEPATH, this.CRYPTO_VAULT_SEED_FILENAME);
    }


    @Override
    protected Runnable agentJob() {
        return this.agent;
    }

    @Override
    protected void onErrorOccur() {

    }

    /**
     * private class that executes the agent
     */
    private class Agent implements Runnable{
        private final BlockchainManager bitcoinNetworkManager;
        private final BitcoinCurrencyCryptoVaultDao dao;
        private final PluginDatabaseSystem pluginDatabaseSystem;
        private final UUID pluginId;
        private final HierarchyAccount hierarchyAccount;
        private final PluginFileSystem pluginFileSystem;
        private final String CRYPTO_VAULT_SEED_FILEPATH;
        private final String CRYPTO_VAULT_SEED_FILENAME;


        public Agent(BlockchainManager bitcoinNetworkManager, BitcoinCurrencyCryptoVaultDao dao, PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId, PluginFileSystem pluginFileSystem,String vaultSeedFilePath, String vaultSeedFileName) {
            this.bitcoinNetworkManager = bitcoinNetworkManager;
            this.dao = dao;
            this.pluginDatabaseSystem = pluginDatabaseSystem;
            this.pluginId = pluginId;
            this.pluginFileSystem = pluginFileSystem;

            this.CRYPTO_VAULT_SEED_FILENAME = vaultSeedFileName;
            this.CRYPTO_VAULT_SEED_FILEPATH = vaultSeedFilePath;


            hierarchyAccount = new HierarchyAccount(0, "Master Account", HierarchyAccountType.MASTER_ACCOUNT);
        }

        @Override
        public void run() {
            try {
                for (ImportedSeed importedSeed : dao.getImportedSeeds()){
                    for(DeterministicSeed seed : getImportedSeeds()){
                        if (importedSeed.getImportedSeedDate() == seed.getCreationTimeSeconds()){
                            doTheMainTask(importedSeed, seed);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private List<DeterministicSeed> getImportedSeeds(){
            List<DeterministicSeed> importedSeedList = new ArrayList<>();
            VaultSeedGenerator vaultSeedGenerator = new VaultSeedGenerator(this.pluginFileSystem, this.pluginId, CRYPTO_VAULT_SEED_FILEPATH, CRYPTO_VAULT_SEED_FILENAME);

            /**
             * if no main seed exists, then there is no imported seeds to retrieve
             */
            if (!vaultSeedGenerator.seedExists())
                return importedSeedList;

            return vaultSeedGenerator.getImportedSeeds();
        }


        private void doTheMainTask(ImportedSeed importedSeed, DeterministicSeed seed) throws AddressFormatException, InsufficientMoneyException, CantStoreTransactionException, CantBroadcastTransactionException {
            final BlockchainNetworkType blockchainNetworkType = importedSeed.getBlockchainNetworkType();
            final NetworkParameters NETWORK_PARAMETERS = BitcoinBlockchainNetworkSelector.getNetworkParameter(blockchainNetworkType);
            VaultKeyHierarchyGenerator keyHierarchyGenerator = new VaultKeyHierarchyGenerator(seed, true, pluginDatabaseSystem, bitcoinNetworkManager, pluginId);
            keyHierarchyGenerator.run();

            // prepare the wallet
            Wallet wallet = Wallet.fromSeed(NETWORK_PARAMETERS, seed);
            wallet.importKeys(keyHierarchyGenerator.getVaultKeyHierarchy().getDerivedKeys(hierarchyAccount));

            /**
             * Add transactions to the wallet that we can use to spend.
             */
            List<Transaction> transactions = bitcoinNetworkManager.getBlockchainProviderTransactions(blockchainNetworkType);

            for (Transaction transaction : transactions){
                if (!transaction.isEveryOwnedOutputSpent(wallet)){
                    WalletTransaction walletTransaction = new WalletTransaction(WalletTransaction.Pool.UNSPENT, transaction);
                    wallet.addWalletTransaction(walletTransaction);
                }
            }

            Coin balance = wallet.getBalance();
            //if I have coins, let's send them
            if (!balance.isZero()){
                Address address = new Address(NETWORK_PARAMETERS, importedSeed.getCryptoAddress().getAddress());
                Wallet.SendRequest sendRequest = Wallet.SendRequest.to(address, balance);
                sendRequest.fee = Coin.valueOf(BitcoinFee.NORMAL.getFee());
                sendRequest.emptyWallet = true;
                wallet.completeTx(sendRequest);

                bitcoinNetworkManager.storeTransaction(blockchainNetworkType, sendRequest.tx, UUID.randomUUID(), true);
                bitcoinNetworkManager.broadcastTransaction(sendRequest.tx.getHashAsString());

            }

            importedSeed.setBalance(balance.getValue());
            importedSeed.setProgress(ImportSeedProgress.COMPLETED);

            try {
                dao.updateImportedSeed(importedSeed);
            } catch (CantExecuteDatabaseOperationException e) {
                e.printStackTrace();
            }

        }
    }
}
