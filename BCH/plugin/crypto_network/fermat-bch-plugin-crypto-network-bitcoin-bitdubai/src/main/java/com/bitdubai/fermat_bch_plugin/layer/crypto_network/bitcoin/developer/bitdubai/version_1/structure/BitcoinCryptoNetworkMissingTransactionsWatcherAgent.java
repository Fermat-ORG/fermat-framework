package com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.ProtocolStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.database.BitcoinCryptoNetworkDatabaseDao;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.exceptions.TransactionWatcherAgentException;

import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.Wallet;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The Class <code>com.bitdubai.fermat_bch_plugin.layer.cryptonetwork.bitcoin.developer.bitdubai.version_1.structure.BitcoinCryptoNetworkMissingTransactionsWatcherAgent</code>
 * Periodically checks the wallet with the list of transactions to find if all transactions have been notified and saved into the
 * database.
 * <p/>
 *
 * Created by Rodrigo Acosta - (acosta_rodrigo@hotmail.com) on 13/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
class BitcoinCryptoNetworkMissingTransactionsWatcherAgent {
    /**
     * class variables
     */
    private AtomicBoolean okToRun = new AtomicBoolean(false);
    private Wallet wallet;
    private final long AGENT_DELAY = 10000;

    /**
     * Platform variables
     */
    private PluginDatabaseSystem pluginDatabaseSystem;
    private UUID pluginId;

    /**
     * constructor
     * @param wallet
     * @param pluginDatabaseSystem
     * @param pluginId
     */
    public BitcoinCryptoNetworkMissingTransactionsWatcherAgent(Wallet wallet, PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.wallet = wallet;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    public void start() throws CantStartAgentException{
        okToRun.set(true);

        Thread agentThread = new Thread(new TransactionWatcherAgent(this.wallet, this.pluginDatabaseSystem, this.pluginId));
        agentThread.start();
    }


    /**
     * stops the agent executiong
     */
    public void stop(){
        okToRun.set(false);
    }

    private class TransactionWatcherAgent implements Runnable{
        /**
         * private class variables
         */
        Wallet wallet;
        BitcoinCryptoNetworkDatabaseDao dao;

        /**
         * platform variables
         */
        PluginDatabaseSystem pluginDatabaseSystem;
        UUID pluginId;

        /**
         * constructor
         * @param wallet
         * @param pluginDatabaseSystem
         * @param pluginId
         */
        public TransactionWatcherAgent(Wallet wallet, PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
            this.wallet = wallet;
            this.pluginDatabaseSystem = pluginDatabaseSystem;
            this.pluginId = pluginId;
        }

        @Override
        public void run() {
            while (okToRun.get()){
                try {
                    while (okToRun.get()){
                        doTheMainTask();
                        Thread.sleep(AGENT_DELAY);
                    }
                } catch (TransactionWatcherAgentException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * Compares the stored transactions in the wallet and the database for a missing one.
         * Inserts it in the database if something don't match.
         * @throws TransactionWatcherAgentException
         */
        private void doTheMainTask() throws TransactionWatcherAgentException {
            Set<Transaction> walletTransactions = getTransactionsFromWallet();
            Set<String> databaseTransactions = getTransactionHashFromDatabase();
            int walletTransactionsAmount = walletTransactions.size();
            int registeredTransactionsAmount = databaseTransactions .size();


            /**
             * if the amount of transactions is different, I will find them.
             */
            if (walletTransactionsAmount != registeredTransactionsAmount){
                for (Transaction transaction : getTransactionsFromWallet()){
                    if (databaseTransactions.remove(transaction.getHashAsString())){
                        registerMissingTransaction(transaction);
                    }
                }
            }
        }

        /**
         * Loads the stored transactions from the bitcoinj wallet
         * @return
         * @throws TransactionWatcherAgentException
         */
        private Set<Transaction> getTransactionsFromWallet() throws TransactionWatcherAgentException {
            try{

            } catch (Exception e){
                throw new TransactionWatcherAgentException(TransactionWatcherAgentException.DEFAULT_MESSAGE, e, "Cant get transactions from wallet", "IO error");
            }
            return wallet.getTransactions(false);
        }

        /**
         * loads the stored transactions from the database
         * @return
         * @throws TransactionWatcherAgentException
         */
        private Set<String> getTransactionHashFromDatabase() throws TransactionWatcherAgentException {
            try {
                return getDao().getStoredStransactionsHash();
            } catch (CantExecuteDatabaseOperationException e) {
                throw new TransactionWatcherAgentException(TransactionWatcherAgentException.DEFAULT_MESSAGE, e, "Cant get transactions from the database", "database error");
            }
        }

        /**
         * persists the missing transaction into the database
         * @param transaction
         */
        private void registerMissingTransaction(Transaction transaction) throws TransactionWatcherAgentException {
            //this needs to be fixed.
            if (transaction.getValueSentToMe(wallet).getValue() != 0){
                try {
                    getDao().saveNewIncomingTransaction(transaction.getHashAsString(),
                            CryptoStatus.ON_CRYPTO_NETWORK,
                            transaction.getConfidence().getDepthInBlocks(),
                            new CryptoAddress("error", CryptoCurrency.BITCOIN),
                            new CryptoAddress("error", CryptoCurrency.BITCOIN),
                            0,
                            "",
                            ProtocolStatus.TO_BE_NOTIFIED);
                } catch (CantExecuteDatabaseOperationException e) {
                    throw new TransactionWatcherAgentException(TransactionWatcherAgentException.DEFAULT_MESSAGE, e, "Cant register missing transaction.", "database error");
                }
            } else {
                //getDao().saveNewOutgoingTransaction();
            }
        }

        /**
         * instantiates the DAO class to access the DB
         * @return
         */
        private BitcoinCryptoNetworkDatabaseDao getDao(){
            if (dao == null)
                dao = new BitcoinCryptoNetworkDatabaseDao(this.pluginId, this.pluginDatabaseSystem);

            return dao;
        }
    }
}
