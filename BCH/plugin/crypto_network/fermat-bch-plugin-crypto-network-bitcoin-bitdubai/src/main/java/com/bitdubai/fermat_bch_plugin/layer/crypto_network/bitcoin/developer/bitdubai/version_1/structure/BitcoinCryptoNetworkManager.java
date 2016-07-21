package com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.ProtocolStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransactionType;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;

import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetImportedAddressesException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.NetworkMonitorIsNotRunningException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.util.BitcoinTransactionConverter;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantMonitorCryptoNetworkException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantStoreTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.manager.BlockchainManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.util.BlockchainConnectionStatus;
import com.bitdubai.fermat_bch_api.layer.crypto_network.util.BlockchainDownloadProgress;
import com.bitdubai.fermat_bch_api.layer.crypto_network.util.BroadcastStatus;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantBroadcastTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantCancellBroadcastTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetActiveBlockchainNetworkTypeException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetBlockchainConnectionStatusException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetBlockchainDownloadProgress;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetBroadcastStatusException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetCryptoTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetTransactionCryptoStatusException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantStoreBitcoinTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.BitcoinNetworkConfiguration;
import com.bitdubai.fermat_bch_api.layer.crypto_network.enums.Status;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.enums.CryptoVaults;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.database.BitcoinCryptoNetworkDatabaseDao;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.exceptions.CantLoadTransactionFromFileException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.util.BitcoinBlockchainNetworkSelector;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.util.BitcoinBlockchainProvider;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.util.TransactionProtocolData;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.google.common.collect.Lists;

import org.apache.commons.lang.StringUtils;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.Context;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionInput;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.store.UnreadableWalletException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;



/**
 * The Class <code>com.bitdubai.fermat_bch_plugin.layer.cryptonetwork.bitcoin.developer.bitdubai.version_1.structure.BitcoinCryptoNetworkManager</code>
 * Starts the monitoring agent that will listen to transactions. Based on the passed public Keys from the network type
 * it will activate a different agent to listen to that network.
 * <p/>
 * <p/>
 * Created by Rodrigo Acosta - (acosta_rodrigo@hotmail.com) on 13/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class BitcoinCryptoNetworkManager  implements TransactionProtocolManager {


    /**
     * class variables
     */
    BitcoinCryptoNetworkMonitor bitcoinCryptoNetworkMonitor;
    final String WALLET_PATH;
    private final CryptoCurrency BITCOIN = BitcoinNetworkConfiguration.CRYPTO_CURRENCY;
    private final BitcoinCryptoNetworkDatabaseDao dao;
    private final BitcoinBlockchainProvider blockchainProvider;

    /**
     * List of running agents per network
     */
    HashMap<BlockchainNetworkType, BitcoinCryptoNetworkMonitor> runningAgents;
    private BitcoinCryptoNetworkMonitor regTestNetworkMonitor;
    private BitcoinCryptoNetworkMonitor testNetNetworkMonitor;
    private BitcoinCryptoNetworkMonitor mainNetNetworkMonitor;

    /**
     * Platform variables
     */
    EventManager eventManager;
    UUID pluginId;
    PluginFileSystem pluginFileSystem;
    ErrorManager errorManager;



    /**
     * Constructor
     * @param eventManager
     * @param pluginFileSystem
     * @param pluginId
     * @param errorManager
     * @param bitcoinCryptoNetworkDatabaseDao
     */
    public BitcoinCryptoNetworkManager(EventManager eventManager,
                                       PluginFileSystem pluginFileSystem,
                                       UUID pluginId,
                                       ErrorManager errorManager,
                                       BitcoinCryptoNetworkDatabaseDao bitcoinCryptoNetworkDatabaseDao,
                                       BitcoinBlockchainProvider blockchainProvider) {
        this.eventManager = eventManager;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
        this.errorManager = errorManager;
        this.WALLET_PATH = pluginFileSystem.getAppPath();
        this.dao = bitcoinCryptoNetworkDatabaseDao;
        this.blockchainProvider = blockchainProvider;

        runningAgents = new HashMap<>();


    }

    /**
     * Monitor the bitcoin network with the passes Key Lists.
     *
     * @param blockchainNetworkTypes
     * @param keyList
     */
    public  synchronized void monitorCryptoNetworkFromKeyList(CryptoVaults cryptoVault, List<BlockchainNetworkType> blockchainNetworkTypes, List<ECKey> keyList) throws CantMonitorCryptoNetworkException {
        /**
         * This method will be called from agents from the Vaults. New keys may be added on each call or not.
         */
        try {
            dao.updateCryptoVaultsStatistics(cryptoVault, keyList.size());
        } catch (CantExecuteDatabaseOperationException e) {
            //If stats where not updated, I will just continue.
            e.printStackTrace();
        }

        /**
         * For each network that is active to be monitored I will...
         */
        for (BlockchainNetworkType blockchainNetworkType : blockchainNetworkTypes) {
            System.out.println("***CryptoNetwork*** Monitor Request from " + cryptoVault.getCode() + " vault on " + blockchainNetworkType.getCode() + " for " + keyList.size() + " keys...");

            // I create the walletFile for this network
            File walletFile = new File(WALLET_PATH, blockchainNetworkType.getCode());
            /**
             * load (or create) the wallet.
             */
            Wallet wallet = null;
            try {
                wallet = getWallet(blockchainNetworkType, keyList);
            } catch (UnreadableWalletException e) {
                CantStartAgentException exception = new CantStartAgentException(e, "Unable to load wallet from file for network " + blockchainNetworkType.getCode(), "IO error");

                errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
                e.printStackTrace();
                throw new CantMonitorCryptoNetworkException(e,blockchainProvider, "IO Error");
            }

            Context context = wallet.getContext();

            /**
             * add new keys (if any).
             */
            boolean isWalletReset = false;

            /**
             * if this is the Watch Only Vault, I won't be importing keys, I will be watching them
             */
            if (cryptoVault == CryptoVaults.BITCOIN_WATCH_ONLY) {
                if (areNewKeysWatched(wallet, keyList, blockchainNetworkType)) {
                    NetworkParameters networkParameters = context.getParams();
                    for (ECKey ecKey : keyList) {
                        wallet.addWatchedAddress(ecKey.toAddress(networkParameters));
                    }

                    try {
                        wallet.saveToFile(walletFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    /**
                     * I do not need to reset the wallet because I will
                     * always be importing fresh (unused) keys.
                     */
                    isWalletReset = false;
                }
            } else {
                /**
                 * regular vault, so will try to import new keys if any
                 */
                if (areNewKeysAdded(wallet, keyList)) {
                    wallet.importKeys(keyList);
                    for (ECKey key : keyList){
                        wallet.addWatchedAddress(key.toAddress(context.getParams()));
                    }
                    try {
                        wallet.saveToFile(walletFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    /**
                     * I do not need to reset the wallet because I will
                     * always be importing fresh (unused) keys.
                     */
                    isWalletReset = true;
                }
            }


            /**
             * If the agent for this network is already running...
             */
            if (isAgentRunning(blockchainNetworkType)) {
                /**
                 * and the wallet was reseted because new keys were added
                 */
                if (isWalletReset) {
                    System.out.println("***CryptoNetwork*** new keys added from " + cryptoVault.getCode() + " vault in " + blockchainNetworkType.getCode() + " network...");


                    BitcoinCryptoNetworkMonitor bitcoinCryptoNetworkMonitor = runningAgents.get(blockchainNetworkType);
                    bitcoinCryptoNetworkMonitor.stop();
                    runningAgents.remove(blockchainNetworkType);
                    bitcoinCryptoNetworkMonitor = null;

                    /**
                     * once the agent is stopped, I will restart it with the new wallet and the reset option.
                     */
                    File walletFilename = new File(WALLET_PATH, blockchainNetworkType.getCode());

                    switch (blockchainNetworkType){
                        case REG_TEST:
                            regTestNetworkMonitor = new BitcoinCryptoNetworkMonitor(pluginId, wallet, isWalletReset, walletFilename, pluginFileSystem, errorManager, dao, eventManager, blockchainProvider);
                            bitcoinCryptoNetworkMonitor = regTestNetworkMonitor;
                            runningAgents.put(blockchainNetworkType, regTestNetworkMonitor);
                            break;
                        case TEST_NET:
                            testNetNetworkMonitor = new BitcoinCryptoNetworkMonitor(pluginId, wallet, isWalletReset, walletFilename, pluginFileSystem, errorManager, dao, eventManager, blockchainProvider);
                            bitcoinCryptoNetworkMonitor = testNetNetworkMonitor;
                            runningAgents.put(blockchainNetworkType, testNetNetworkMonitor);
                            break;
                        case PRODUCTION:
                            mainNetNetworkMonitor = new BitcoinCryptoNetworkMonitor(pluginId, wallet, isWalletReset, walletFilename, pluginFileSystem, errorManager, dao, eventManager, blockchainProvider);
                            bitcoinCryptoNetworkMonitor = mainNetNetworkMonitor;
                            runningAgents.put(blockchainNetworkType, mainNetNetworkMonitor);
                            break;
                    }

                    try {
                        bitcoinCryptoNetworkMonitor.start();
                    } catch (CantStartAgentException e) {
                        throw new CantMonitorCryptoNetworkException(e,blockchainProvider, "IO Error");
                    }
                }
            } else {
                /**
                 * If the agent for the network is not running, I will start a new one.
                 */
                File walletFilename = new File(WALLET_PATH, blockchainNetworkType.getCode());

                BitcoinCryptoNetworkMonitor bitcoinCryptoNetworkMonitor = null;
                switch (blockchainNetworkType){
                    case REG_TEST:
                        regTestNetworkMonitor = new BitcoinCryptoNetworkMonitor(pluginId, wallet, isWalletReset, walletFilename, pluginFileSystem, errorManager, dao, eventManager, blockchainProvider);
                        bitcoinCryptoNetworkMonitor = regTestNetworkMonitor;
                        runningAgents.put(blockchainNetworkType, regTestNetworkMonitor);
                        break;
                    case TEST_NET:
                        testNetNetworkMonitor = new BitcoinCryptoNetworkMonitor(pluginId, wallet, isWalletReset, walletFilename, pluginFileSystem, errorManager, dao, eventManager, blockchainProvider);
                        bitcoinCryptoNetworkMonitor = testNetNetworkMonitor;
                        runningAgents.put(blockchainNetworkType, testNetNetworkMonitor);
                        break;
                    case PRODUCTION:
                        mainNetNetworkMonitor = new BitcoinCryptoNetworkMonitor(pluginId, wallet, isWalletReset, walletFilename, pluginFileSystem, errorManager, dao, eventManager, blockchainProvider);
                        bitcoinCryptoNetworkMonitor = mainNetNetworkMonitor;
                        runningAgents.put(blockchainNetworkType, mainNetNetworkMonitor);
                        break;
                }

                System.out.println("***CryptoNetwork*** starting new agent with " + keyList.size() + " keys for " + cryptoVault.getCode() + " vault...");

                try {
                    bitcoinCryptoNetworkMonitor.start();
                } catch (CantStartAgentException e) {
                    throw new CantMonitorCryptoNetworkException(e,blockchainProvider, "IO Error");
                }
            }

            /**
             * I will update the detailed stats table with the keys that are imported in the wallet.
             */
            List<ECKey> importedKEys = wallet.getImportedKeys();
            updateDetailedCryptoStats(cryptoVault, blockchainNetworkType, importedKEys);
        }
    }

    /**
     * Will compare if from the passed KeyList there is a missing watched address in the wallet
     *
     * @param wallet
     * @param keyList
     * @return
     */
    private boolean areNewKeysWatched(Wallet wallet, List<ECKey> keyList, BlockchainNetworkType blockchainNetworkType) {
        List<Address> watchedAddresses = wallet.getWatchedAddresses();
        List<Address> newAddresses = new ArrayList<>();

        NetworkParameters networkParameters = BitcoinBlockchainNetworkSelector.getNetworkParameter(blockchainNetworkType);
        for (ECKey ecKey : keyList) {
            newAddresses.add(ecKey.toAddress(networkParameters));
        }

        /**
         * I compare both lists.
         */
        newAddresses.removeAll(watchedAddresses);
        return !newAddresses.isEmpty();
    }

    /**
     * Updates the detailed stats with the passed information
     *
     * @param cryptoVault
     * @param blockchainNetworkType
     * @param keyList
     */
    private void updateDetailedCryptoStats(CryptoVaults cryptoVault, BlockchainNetworkType blockchainNetworkType, List<ECKey> keyList) {
        try {
            dao.deleteDetailedCryptoStats(cryptoVault, blockchainNetworkType);
            dao.updateDetailedCryptoStats(cryptoVault, blockchainNetworkType, keyList);
        } catch (CantExecuteDatabaseOperationException e) {
            /**
             * if the stats are not updated, I can continue anyway.
             */
            e.printStackTrace();
        }
    }

    /**
     * Will create the wallet Object. I will try to load it from disk from a previous execution
     * by forming the name wallet_[NETWORK]. If it doesn't exists, then I will create a new object for this network.
     *
     * @return
     */
    private synchronized Wallet getWallet(BlockchainNetworkType blockchainNetworkType, @Nullable List<ECKey> keyList) throws UnreadableWalletException {
        Wallet wallet = null;
        File walletFile = new File(WALLET_PATH, blockchainNetworkType.getCode());

        final NetworkParameters NETWORK_PARAMETER = BitcoinBlockchainNetworkSelector.getNetworkParameter(blockchainNetworkType);

        // if the wallet file exists, I will get it from the Network Monitor
        if (walletFile.exists()){
            BitcoinCryptoNetworkMonitor monitor = null;
            try {
                monitor = getMonitor(blockchainNetworkType);
            } catch (NetworkMonitorIsNotRunningException e) {
                //if there was an error, I will continue and try to get the wallet from file.
            }

            if (monitor != null)
                wallet = monitor.getWallet();
            else
                wallet = Wallet.loadFromFile(walletFile);


            return wallet;
            }
         else {
            // will get the context for this wallet.
            Context context = new Context(NETWORK_PARAMETER);

            wallet = new Wallet(context);
            wallet.importKeys(keyList);

            for (ECKey key : keyList){
                wallet.addWatchedAddress(key.toAddress(NETWORK_PARAMETER));
            }


            /**
             * Will set the autosave information and save it.
             */
            wallet.autosaveToFile(walletFile, 10, TimeUnit.SECONDS, null);
            try {
                wallet.saveToFile(walletFile);
            } catch (IOException e1) {
                e1.printStackTrace(); // I will continue because the key addition will trigger an autosave anyway.
            }
            return wallet;
        }
    }

    /**
     * Will compare the keys already saved in the wallet loaded from disk against the list passed by the vault.
     * If there are additions, it will return true.
     * If they are no new keys, will return false.
     *
     * @param wallet
     * @param keys
     * @return
     */
    private boolean areNewKeysAdded(Wallet wallet, List<ECKey> keys) {
        List<ECKey> walletKeys = wallet.getImportedKeys();
        /**
         * I remove from the passed list, everything is already saved in the wallet-
         */
        List<ECKey> tempKeyList = new ArrayList<>();

        tempKeyList.addAll(keys);
        tempKeyList.removeAll(walletKeys);

        /**
         * If there are still keys, then we have new ones.
         */
        return tempKeyList.size() > 0;
    }

    /**
     * Verifies if for the passed network type, an Agent is already running.
     *
     * @param blockchainNetworkType
     * @return
     */
    private boolean isAgentRunning(BlockchainNetworkType blockchainNetworkType) {
        return runningAgents.get(blockchainNetworkType) != null;
    }


    /**
     * TransactionProtocolManager interface implementations
     */

    /**
     * Confirms the reception of a transaction.
     * This will change the ProtocolStatus of a transaction from ToBeNotified to NoActionRequired
     *
     * @param transactionID
     * @throws CantConfirmTransactionException
     */
    @Override
    public void confirmReception(UUID transactionID) throws CantConfirmTransactionException {
        try {
            dao.confirmReception(transactionID);
        } catch (CantExecuteDatabaseOperationException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantConfirmTransactionException(CantConfirmTransactionException.DEFAULT_MESSAGE, e, "Crypto Network issue confirming transaction.", "database issue");
        }
    }

    /**
     * Gets the list of pending transactions, which are marked as Pending_NOTIFIED
     *
     * @param specialist
     * @return
     * @throws CantDeliverPendingTransactionsException
     */
    @Override
    public List<com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction> getPendingTransactions(Specialist specialist) throws CantDeliverPendingTransactionsException {
        /**
         * the list to return
         */
        List<com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction> transactionList = new ArrayList<>();

        /**
         * Will get all the pendingCryptoTransactions data
         */
        try {
            for (TransactionProtocolData transactionProtocolData : getPendingTransactionProtocolData()) {
                com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction transaction;
                /**
                 * I create the transaction protocol object and fill it with the data
                 */
                transaction = new com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction(
                        transactionProtocolData.getTransactionId(),
                        transactionProtocolData.getCryptoTransaction(),
                        transactionProtocolData.getAction(),
                        transactionProtocolData.getTimestamp());
                /**
                 * and Add it to the list
                 */
                transactionList.add(transaction);

                /**
                 * Will set the Protocol Status of this transaction to Sending Notified.
                 */
                dao.setTransactionProtocolStatus(transaction.getTransactionID(), ProtocolStatus.SENDING_NOTIFIED);
            }
        } catch (CantExecuteDatabaseOperationException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantDeliverPendingTransactionsException(CantDeliverPendingTransactionsException.DEFAULT_MESSAGE, e, "database error getting the pending transactions.", "database issue");
        }

        return transactionList;
    }

    /**
     * Gets the pending transaction data
     *
     * @return
     */
    private List<TransactionProtocolData> getPendingTransactionProtocolData() throws CantExecuteDatabaseOperationException {
        return dao.getPendingTransactionProtocolData();
    }

    /**
     * Gets the CryptoTransaction list that matches the specified hash
     *
     * @param txHash
     * @return
     * @throws CantGetCryptoTransactionException
     */
    public List<CryptoTransaction> getCryptoTransactions(String txHash) throws CantGetCryptoTransactionException {
        try {
            return dao.getCryptoTransactions(txHash, null, null);
        } catch (CantExecuteDatabaseOperationException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetCryptoTransactionException(CantGetCryptoTransactionException.DEFAULT_MESSAGE, e, "database operation issue.", "database error");
        }
    }


    /**
     * Broadcast a well formed, commited and signed transaction into the specified network
     *
     * @param txHash
     * @throws CantBroadcastTransactionException
     */
    public synchronized void broadcastTransaction(String txHash) throws CantBroadcastTransactionException {
        /**
         * will get the network for this transaction and then call the right agent to broadcast it.
         */
        BlockchainNetworkType blockchainNetworkType;
        try {
            blockchainNetworkType = dao.getBlockchainNetworkTypeFromBroadcast(txHash);
        } catch (CantExecuteDatabaseOperationException e) {
            blockchainNetworkType = BlockchainNetworkType.getDefaultBlockchainNetworkType();
        }

        //make sure we did have the agent running for this network
        BitcoinCryptoNetworkMonitor monitor = null;
        try {
            monitor = getMonitor(blockchainNetworkType);
        } catch (NetworkMonitorIsNotRunningException e) {
            throw new CantBroadcastTransactionException(CantBroadcastTransactionException.DEFAULT_MESSAGE, e, "Broadcasting transaction " + txHash, "monitor not enabled.");
        }

        monitor.broadcastTransaction(txHash);
    }


    /**
     * Gets the specified bitcoin transaction
     *
     * @param transactionHash
     * @return
     */
    public Transaction getBlockchainProviderTransaction(BlockchainNetworkType blockchainNetworkType, String transactionHash) {
        // will make sure we have a running agent for this network
        BitcoinCryptoNetworkMonitor monitor = runningAgents.get(blockchainNetworkType);

        if (monitor == null)
            return null;

        Sha256Hash sha256Hash = Sha256Hash.wrap(transactionHash);
        Transaction transaction = monitor.getBitcoinTransaction(sha256Hash);

        return transaction;
    }

    /**
     * Gets all the transactions stored in the specified network.
     *
     * @param blockchainNetworkType
     * @return
     */
    public List<Transaction> getBlockchainProviderTransactions(BlockchainNetworkType blockchainNetworkType) {
        //make sure the monitor is running
        BitcoinCryptoNetworkMonitor monitor = runningAgents.get(blockchainNetworkType);

        if (monitor == null)
            return null;
        else
            return monitor.getWallet().getTransactionsByTime();
    }


    /**
     * Gets the transaction passed in the network.
     * It will look first if it is a local trasaction, if not I will look for it at the stored blockchain
     * and if still not found, I will look for it at the Blockchain requesting it from multiple peers.
     *
     * @param blockchainNetworkType
     * @param parentTransactionHash
     * @param transactionBlockHash
     * @return
     * @throws CantGetTransactionsException
     */
    private Transaction getTransactionFromBlockChain(BlockchainNetworkType blockchainNetworkType, String parentTransactionHash, String transactionBlockHash) throws CantGetTransactionsException {
        //make sure the agent is running
        BitcoinCryptoNetworkMonitor monitor = null;
        try {
            monitor = getMonitor(blockchainNetworkType);
        } catch (NetworkMonitorIsNotRunningException e) {
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, e, "Monitor not started for this network type " + blockchainNetworkType.toString(), "Network reset");
        }


        /**
         * will get it from the specified agent monitoring the passed network.
         */
        try {
            return monitor.getTransactionFromBlockChain(parentTransactionHash, transactionBlockHash);
        } catch (CantGetTransactionException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, e, "Error getting the parent transaction from the blockchain.", "Blockchain error");
        }
    }

    /**
     * Will get all the CryptoTransactions stored in the CryptoNetwork which are a child of a parent Transaction
     *
     * @param parentHash
     * @return
     * @throws CantGetCryptoTransactionException
     */

    public List<CryptoTransaction> getChildCryptoTransaction(String parentHash) throws CantGetCryptoTransactionException {
        CryptoTransaction cryptoTransaction = null;
        /**
         * I will get the list of stored transactions for the default network.
         */
        List<Transaction> transactions = getBlockchainProviderTransactions(BlockchainNetworkType.getDefaultBlockchainNetworkType());

        for (Transaction transaction : transactions) {
            /**
             * I will search on the inputs of each transaction and search for the passed hash.
             */
            for (TransactionInput input : transaction.getInputs()) {
                if (input.getOutpoint().getHash().toString().contentEquals(parentHash))
                    cryptoTransaction = BitcoinTransactionConverter.getCryptoTransaction(BlockchainNetworkType.getDefaultBlockchainNetworkType(), transaction, BITCOIN);
            }
        }

        /**
         * If i couldn't find a match, then I will return null.
         */
        if (cryptoTransaction == null)
            return null;

        /**
         * I will add the Crypto Transaction to the list and verify if I need to inform any previous state.
         */
        List<CryptoTransaction> cryptoTransactions = new ArrayList<>();
        cryptoTransactions.add(cryptoTransaction);

        /**
         * I need to return all the previous CryptoStates of the CryptoTransaction,
         * so I will manually add them.
         */
        if (cryptoTransaction.getCryptoStatus() == CryptoStatus.IRREVERSIBLE) {
            CryptoTransaction onBlockChain = duplicateCryptoTransaction(cryptoTransaction, CryptoStatus.ON_BLOCKCHAIN);
            cryptoTransactions.add(onBlockChain);

            CryptoTransaction onCryptoNetwork = duplicateCryptoTransaction(cryptoTransaction, CryptoStatus.ON_CRYPTO_NETWORK);
            cryptoTransactions.add(onCryptoNetwork);
        }

        if (cryptoTransaction.getCryptoStatus() == CryptoStatus.ON_BLOCKCHAIN) {
            CryptoTransaction onCryptoNetwork = duplicateCryptoTransaction(cryptoTransaction, CryptoStatus.ON_CRYPTO_NETWORK);
            cryptoTransactions.add(onCryptoNetwork);
        }

        return cryptoTransactions;
    }

    /**
     * instantiates a new cryptoTransaction with a new CryptoStatus
     *
     * @param cryptoTransaction
     * @param cryptoStatus
     * @return
     */
    private CryptoTransaction duplicateCryptoTransaction(CryptoTransaction cryptoTransaction, CryptoStatus cryptoStatus) {
        CryptoTransaction newCryptoTransaction = BitcoinTransactionConverter.copyCryptoTransaction(cryptoTransaction);
        newCryptoTransaction.setCryptoStatus(cryptoStatus);
        return newCryptoTransaction;
    }


    /**
     * gets the current Crypto Status for the specified Transaction ID
     *
     * @param txHash the Bitcoin transaction hash
     * @return the last crypto status
     * @throws CantGetTransactionCryptoStatusException
     */
    public CryptoStatus getCryptoStatus(String txHash) throws CantGetTransactionCryptoStatusException {
        try {
            return dao.getTransactionCryptoStatus(txHash);
        } catch (CantExecuteDatabaseOperationException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetTransactionCryptoStatusException(CantGetTransactionCryptoStatusException.DEFAULT_MESSAGE, e, "Database error getting CryptoStatus for transaction: " + txHash, "database issue");
        }
    }


    /**
     * Stores a Bitcoin Transaction in the CryptoNetwork to be broadcasted later
     *
     * @param blockchainNetworkType
     * @param tx
     * @param transactionId
     * @throws CantStoreBitcoinTransactionException
     */
    public synchronized void storeTransaction(BlockchainNetworkType blockchainNetworkType, Transaction tx, UUID transactionId, boolean commit) throws CantStoreTransactionException {
        BitcoinCryptoNetworkMonitor monitor = null;
        try {
            monitor  = getMonitor(blockchainNetworkType);
        } catch (NetworkMonitorIsNotRunningException e) {
            throw new CantStoreTransactionException(e, "Network Monitor not initialized for this blockchain network type." + blockchainNetworkType.toString() + " Retry later.", "monitor not started.");
        }

        try {
            monitor.storeBitcoinTransaction(tx, transactionId, commit);
        } catch (CantStoreBitcoinTransactionException e) {
            throw new CantStoreTransactionException(e, blockchainProvider, "IO Error");
        }
    }

    /**
     * Returns the broadcast Status for a specified transaction.
     *
     * @param txHash
     * @return
     * @throws CantGetBroadcastStatusException
     */
    public BroadcastStatus getBroadcastStatus(String txHash) throws CantGetBroadcastStatusException {
        try {
            return dao.getBroadcastStatus(txHash);
        } catch (CantExecuteDatabaseOperationException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetBroadcastStatusException(CantGetBroadcastStatusException.DEFAULT_MESSAGE, e, "There was a database error getting the status", "database issue");
        }
    }

    /**
     * Will mark the passed transaction as cancelled, and it won't be broadcasted again.
     *
     * @param txHash
     * @throws CantCancellBroadcastTransactionException
     */
    public void cancelBroadcast(String txHash) throws CantCancellBroadcastTransactionException {

        /**
         * I will get the network type this transaction belongs to
         */
        BlockchainNetworkType blockchainNetworkType;
        try {
            blockchainNetworkType = dao.getBlockchainNetworkTypeFromBroadcast(txHash);
        } catch (CantExecuteDatabaseOperationException e) {
            blockchainNetworkType = BlockchainNetworkType.getDefaultBlockchainNetworkType();
        }

        // I will make sure the agent is started
        BitcoinCryptoNetworkMonitor monitor = null;
        try {
            monitor = getMonitor(blockchainNetworkType);
        } catch (NetworkMonitorIsNotRunningException e) {
            throw new CantCancellBroadcastTransactionException(CantCancellBroadcastTransactionException.DEFAULT_MESSAGE, e, "Monitor not started for this network type." + blockchainNetworkType.toString(), "Monitor reset");
        }

        /**
         * Will invalidate the transaction in the wallet
        */
        monitor.cancelBroadcast(txHash);

        /**
         * marks the transaction as cancelled in the database
         */
        try {
            dao.setBroadcastStatus(Status.CANCELLED, 0, null, txHash);
        } catch (CantExecuteDatabaseOperationException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCancellBroadcastTransactionException(CantCancellBroadcastTransactionException.DEFAULT_MESSAGE, e, "Database error while cancelling transaction.", "database issue");
        }

    }

    /**
     * Will get the BlockchainConnectionStatus for the specified network.it
     *
     * @param blockchainNetworkType the Network type we won't to get info from. If the passed network is not currently activated,
     *                              then we will receive null.
     * @return BlockchainConnectionStatus with information of amount of peers currently connected, etc.
     * @throws CantGetBlockchainConnectionStatusException
     */
    public BlockchainConnectionStatus getBlockchainConnectionStatus(BlockchainNetworkType blockchainNetworkType) throws CantGetBlockchainConnectionStatusException {
        BitcoinCryptoNetworkMonitor monitor = null;
        try {
            monitor = getMonitor(blockchainNetworkType);
        } catch (NetworkMonitorIsNotRunningException e) {
            throw new CantGetBlockchainConnectionStatusException(CantGetBlockchainConnectionStatusException.DEFAULT_MESSAGE, e, "Monitor not started for this network type " + blockchainNetworkType.toString() + ". Retry later.", "monitor reset");
        }

        return monitor.getBlockchainConnectionStatus();
    }

    /**
     * Gets a stored CryptoTransaction in wathever network.
     *
     * @param txHash the transaction hash we want to get the CryptoTransaction
     * @return the last recorded CryptoTransaction.
     * @throws CantGetCryptoTransactionException
     */
    public CryptoTransaction getCryptoTransaction(String txHash, @Nullable CryptoTransactionType cryptoTransactionType, @Nullable CryptoAddress toAddress) throws CantGetCryptoTransactionException {
        if (StringUtils.isBlank(txHash))
            throw new CantGetCryptoTransactionException(CantGetCryptoTransactionException.DEFAULT_MESSAGE, null ,"Invalid parameter. TxHash: " + txHash, "GetCryptoTransaction on CryptoNetwork");

        CryptoTransaction cryptoTransaction = null;
        try {
            cryptoTransaction = dao.getCryptoTransaction(txHash, cryptoTransactionType, toAddress);
        } catch (CantExecuteDatabaseOperationException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetCryptoTransactionException(CantGetCryptoTransactionException.DEFAULT_MESSAGE, e, "database error getting the last crypto transaction.", "database error");
        }

        // If I didn't got it in the right transactionType, will try getting them all
        if (cryptoTransaction == null){
            try {
                cryptoTransaction = dao.getCryptoTransaction(txHash, null, toAddress);
            } catch (CantExecuteDatabaseOperationException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantGetCryptoTransactionException(CantGetCryptoTransactionException.DEFAULT_MESSAGE, e, "database error getting the last crypto transaction.", "database error");
            }
        }


        /**
         * This might happen if the transaction we are searching for is not yet broadcasted
         */
        if (cryptoTransaction == null){
            /**
             * I will get the transaction from all running agents
             */
            for (Map.Entry<BlockchainNetworkType, BitcoinCryptoNetworkMonitor> entry : this.runningAgents.entrySet()){
                try {
                    Transaction transaction = entry.getValue().loadTransactionFromDisk(txHash);

                    // If I find it on a running agent, then I will form the CryptoTransaction and return it.
                    if (transaction != null){
                        cryptoTransaction = BitcoinTransactionConverter.getCryptoTransaction(entry.getKey(), transaction, BITCOIN);
                        cryptoTransaction.setCryptoTransactionType(CryptoTransactionType.OUTGOING);
                        return cryptoTransaction;
                    }
                } catch (CantLoadTransactionFromFileException | CantCreateFileException e) {
                    CantGetCryptoTransactionException exception = new CantGetCryptoTransactionException(CantGetCryptoTransactionException.DEFAULT_MESSAGE, e, "There was an error getting the CryptoTransaction from disk", "IO Error");
                    errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
                    throw exception;
                } catch (FileNotFoundException e) {
                    // If I couldn't find it, then it just may not be at that network
                    e.printStackTrace();
                }
            }
        }

        /**
         * if it is still null then there is something wrong
         */
        if (cryptoTransaction == null){
            CantGetCryptoTransactionException exception = new CantGetCryptoTransactionException(CantGetCryptoTransactionException.DEFAULT_MESSAGE, null, "The passed Transaction hash " + txHash + " is not stored anywhere!", "wrong transaction hash");
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_NETWORK, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, exception);
            throw exception;
        }

        return cryptoTransaction;
    }

    /**
     * Based on the passed transaction chain of Transactions hashes and Blocks hashes, determines the entire path
     * of the chain until the Genesis Transaction is reached.
     * The genesis Transaction will be the first transaction in the map.
     *
     * @param blockchainNetworkType the active network to validate
     * @param transactionChain      a Map with the form TransactionHash / BlockHash
     * @return all the CryptoTransactions originated at the genesis transaction
     * @throws CantGetCryptoTransactionException
     */
    public CryptoTransaction getGenesisCryptoTransaction(@Nullable BlockchainNetworkType blockchainNetworkType, LinkedHashMap<String, String> transactionChain) throws CantGetCryptoTransactionException {
        try {
            if (blockchainNetworkType == null)
                blockchainNetworkType = BlockchainNetworkType.getDefaultBlockchainNetworkType();
            CryptoTransaction cryptoTransaction = BitcoinTransactionConverter.getCryptoTransaction(blockchainNetworkType, this.getGenesisTransaction(blockchainNetworkType, transactionChain), BITCOIN);
            return cryptoTransaction;
        } catch (CantGetTransactionException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetCryptoTransactionException(CantGetCryptoTransactionException.DEFAULT_MESSAGE, e, null, null);
        }
    }

    /**
     * Based on a passed CryptoTransaction, returns a list of all the previous generated cryptoTransactions
     * with the different CryptoStatus genereated
     *
     * @param cryptoTransaction
     * @return
     */
    private List<CryptoTransaction> getCryptoTransactionWithAllCryptoStatus(CryptoTransaction cryptoTransaction) {
        List<CryptoTransaction> cryptoTransactions = new ArrayList<>();

        CryptoTransaction onCryptoNetwork = cryptoTransaction;
        switch (cryptoTransaction.getCryptoStatus()) {
            case IRREVERSIBLE:
                onCryptoNetwork.setCryptoStatus(CryptoStatus.ON_CRYPTO_NETWORK);
                cryptoTransactions.add(onCryptoNetwork);

                CryptoTransaction onBlockChain = cryptoTransaction;
                onBlockChain.setCryptoStatus(CryptoStatus.ON_BLOCKCHAIN);
                cryptoTransactions.add(onBlockChain);

                cryptoTransactions.add(cryptoTransaction);
                break;
            case ON_BLOCKCHAIN:
                onCryptoNetwork.setCryptoStatus(CryptoStatus.ON_CRYPTO_NETWORK);
                cryptoTransactions.add(onCryptoNetwork);

                cryptoTransactions.add(cryptoTransaction);
                break;
            default:
                cryptoTransactions.add(cryptoTransaction);
                break;
        }

        return cryptoTransactions;
    }


    /**
     * Based on the passed transaction chain of Transactions hashes and Blocks hashes, determines the entire path
     * of the chain until the Genesis Transaction is reached.
     * The genesis Transaction will be the first transaction in the map.
     *
     * @param blockchainNetworkType the active network to validate
     * @param transactionChain      a Map with the form TransactionHash / BlockHash
     * @return the transaction that represents the GenesisTransaction
     * @throws CantGetCryptoTransactionException
     */
    private Transaction getGenesisTransaction(@Nullable BlockchainNetworkType blockchainNetworkType, LinkedHashMap<String, String> transactionChain) throws CantGetTransactionException {
        if (transactionChain.isEmpty())
            throw new CantGetTransactionException(CantGetTransactionException.DEFAULT_MESSAGE, null, "Transaction chain information is required.", "missing data");

        /**
         * will set default network if non provided.
         */
        if (blockchainNetworkType == null)
            blockchainNetworkType = BlockchainNetworkType.getDefaultBlockchainNetworkType();

        Transaction parentTransaction = null, childTransaction = null, genesisTransaction = null;

        /**
         * I will iterate the passed map backwards getting one at the time, the transactions locally (or remotely if not found)
         * make sure they are linked together lineally with the next transaction, until I reach the first genesis Transaction in the map.
         *
         * iterating reverse using guava :-)
         */

        for (Map.Entry<String, String> entry : Lists.reverse(Lists.newArrayList(transactionChain.entrySet()))) {
            /**
             * first iteration may have block hash null because transaction is not yet confirmed. If this is the case I will move to the next one
             */
            if (StringUtils.isBlank(entry.getValue()))
                continue;

            /**
             * if this is the first entry, I will only get the first transaction in the chain
             */
            if (childTransaction == null) {
                try {
                    childTransaction = this.getTransactionFromBlockChain(blockchainNetworkType, entry.getKey(), entry.getValue());
                } catch (CantGetTransactionsException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                    throw new CantGetTransactionException(CantGetTransactionException.DEFAULT_MESSAGE, e, "Error downloading child transaction from peer.", "Network issue - Timeout");
                }
            }
            try {
                /**
                 * I will compare the childTransaction against the parent transaction, making sure that the child Transaction is an has an outputPoint to his parent.
                 */
                parentTransaction = this.getTransactionFromBlockChain(blockchainNetworkType, entry.getKey(), entry.getValue());

                if (!childTransaction.equals(parentTransaction) && !childTransaction.getInput(0).getOutpoint().getHash().equals(parentTransaction.getHash())) {


                    StringBuilder output = new StringBuilder("The passed chain of transactions is not lineal.");
                    output.append(System.lineSeparator());
                    output.append("Transaction hash " + childTransaction.getHashAsString() + " is not a child of " + parentTransaction.getHashAsString());


                    CantGetTransactionException e =  new CantGetTransactionException(CantGetTransactionException.DEFAULT_MESSAGE, null, output.toString(), null);
                    errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                    throw e;
                }

                /**
                 * I will set the new childTransaction for the next run.
                 */
                childTransaction = parentTransaction;

                /**
                 * I will set the genesis Transaction as the parent, so that when the loop is over, I will be getting
                 * truly the genesisTransaction.
                 */
                genesisTransaction = parentTransaction;
            } catch (CantGetTransactionsException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantGetTransactionException(CantGetTransactionException.DEFAULT_MESSAGE, e, "Error downloading transaction from peer.", "Network issue - Timeout");
            }
        }
        return genesisTransaction;
    }

    /**
     * Based on the Parent transaction passed, will return all the CryptoTransactions that are a direct descendant of this parent.
     * Only if it is a locally stored transaction
     *
     * @param parentTransactionHash the hash of the parent trasaction
     * @return the list of CryptoTransactions that are a direct child of the parent.
     * @throws CantGetCryptoTransactionException
     */
    public List<CryptoTransaction> getChildTransactionsFromParent(String parentTransactionHash) throws CantGetCryptoTransactionException {
        List<CryptoTransaction> cryptoTransactions = new ArrayList<>();
        try {
            for (Map.Entry<Transaction, BlockchainNetworkType> entry : getChildBitcoinTransactionsFromParent(parentTransactionHash).entrySet()){
                cryptoTransactions.add(BitcoinTransactionConverter.getCryptoTransaction(entry.getValue(), entry.getKey(), BITCOIN));
            }
        } catch (CantGetTransactionException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetCryptoTransactionException(CantGetCryptoTransactionException.DEFAULT_MESSAGE, e, "error getting list of Bitcoin Transactions", null);
        }

        return cryptoTransactions;
    }


    /**
     * Based on the Parent transaction passed, will return all the Bitcoin Transactions that are a direct descendant of this parent.
     * Only if it is a locally stored transaction
     *
     * @param parentTransactionHash the hash of the parent trasaction
     * @return the list of Bitcoin Transactions that are a direct child of the parent.
     * @throws CantGetCryptoTransactionException
     */
    private Map<Transaction, BlockchainNetworkType> getChildBitcoinTransactionsFromParent(String parentTransactionHash) throws CantGetTransactionException{
        Map<Transaction,BlockchainNetworkType> transactions = new HashMap<>();

        Sha256Hash txHash = Sha256Hash.wrap(parentTransactionHash);
        /**
         * I will get all the stored transactions on all active networks
         */
        try {
            for (BlockchainNetworkType blockchainNetworkType : this.dao.getActiveBlockchainNetworkTypes()){
                for (Transaction transaction : this.getBlockchainProviderTransactions(blockchainNetworkType)){
                    /**
                     * for each transaction I will search in the input the outpoint that matches the passed transactionHash
                     */
                    for (TransactionInput input : transaction.getInputs()){
                        if (input.getOutpoint().getHash().equals(txHash))
                            transactions.put(transaction, blockchainNetworkType);
                    }
                }
            }
        } catch (CantExecuteDatabaseOperationException e) {
            e.printStackTrace();
        }
        /**
         * I'm not removing duplicates
         */
        return transactions;
    }

    /**
     * Gets the list of stored CryptoTransactions for the specified network type
     * @param blockchainNetworkType the network type to get the transactions from.
     * @return the list of Crypto Transaction
     * @throws CantGetCryptoTransactionException
     */
    public List<CryptoTransaction> getCryptoTransactions(BlockchainNetworkType blockchainNetworkType, CryptoAddress addressTo, @Nullable CryptoTransactionType cryptoTransactionType) throws CantGetCryptoTransactionException {
        try{
            return this.dao.getCryptoTransactions(blockchainNetworkType, addressTo, cryptoTransactionType);
        } catch (Exception e){
            throw new CantGetCryptoTransactionException(CantGetCryptoTransactionException.DEFAULT_MESSAGE, e, "There was an error getting the list of stored cryptoTransactions", "Database issue");
        }

    }

    /**
     * returns the blockchain download progress for the passed network type
     * @param blockchainNetworkType
     * @return
     * @throws CantGetBlockchainDownloadProgress
     */
    public BlockchainDownloadProgress getBlockchainDownloadProgress(BlockchainNetworkType blockchainNetworkType) throws CantGetBlockchainDownloadProgress {
        BitcoinCryptoNetworkMonitor networkMonitor = this.runningAgents.get(blockchainNetworkType);
        if (networkMonitor == null) {
            //it means the monitor is not running now, probably due to a reset.
            //will return a cero download progress

            return new BlockchainDownloadProgress(blockchainNetworkType, 0, 0, 0, 0);
        } else
        return networkMonitor.getBlockchainDownloadProgress();
    }

    /**
     * Gets the active networks running on the Crypto Network
     * @return the list of active networks {MainNet, TestNet and RegTest}
     * @throws CantGetActiveBlockchainNetworkTypeException
     */
    public List<BlockchainNetworkType> getActivesBlockchainNetworkTypes() throws CantGetActiveBlockchainNetworkTypeException {
        try {
            return  dao.getActiveBlockchainNetworkTypes();
        } catch (CantExecuteDatabaseOperationException e) {
            throw new CantGetActiveBlockchainNetworkTypeException(e, "error getting list of active networks.", "database issue");
        }
    }

    /**
     * returns the list of stored cryptoaddress we are monitoing from imported seeds.
     * @param blockchainNetworkType
     * @return
     */
    public List<CryptoAddress> getImportedAddresses(BlockchainNetworkType blockchainNetworkType) {
        List<CryptoAddress> cryptoAddressList = new ArrayList<>();
        try {
            for (String rawAddress : dao.getImportedAddresses(blockchainNetworkType)){
                CryptoAddress cryptoAddress = new CryptoAddress(rawAddress, CryptoCurrency.BITCOIN);
                cryptoAddressList.add(cryptoAddress);
            }
        } catch (CantExecuteDatabaseOperationException e) {
            return cryptoAddressList;
        }
        return cryptoAddressList;
    }

    /**
     * gets the network monitor if it is running.
     * @param blockchainNetworkType
     * @return
     */
    private synchronized BitcoinCryptoNetworkMonitor getMonitor(BlockchainNetworkType blockchainNetworkType) throws NetworkMonitorIsNotRunningException {
        try {
            if (!dao.isNetworkActive(blockchainNetworkType))
                throw new NetworkMonitorIsNotRunningException(blockchainNetworkType.getCode(), "Network is not activated. Generate an address in the requested network to active.");
        } catch (CantExecuteDatabaseOperationException e) {
            //if I couldn't get it from the database, I will continue.
        }

        switch (blockchainNetworkType){
            case REG_TEST:
                if (regTestNetworkMonitor == null)
                    throw new NetworkMonitorIsNotRunningException(blockchainNetworkType.getCode() + " agent is null." , "Possible being restarted");
                return regTestNetworkMonitor;
            case TEST_NET:
                if (testNetNetworkMonitor == null)
                    throw new NetworkMonitorIsNotRunningException(blockchainNetworkType.getCode() + " agent is null." , "Possible being restarted");
                return testNetNetworkMonitor;
            case PRODUCTION:
                if (mainNetNetworkMonitor == null)
                    throw new NetworkMonitorIsNotRunningException(blockchainNetworkType.getCode() + " agent is null." , "Possible being restarted");
                return mainNetNetworkMonitor;
            default:
                throw new NetworkMonitorIsNotRunningException(blockchainNetworkType.getCode() + " is not a valid blockchain network code." , "Wrong network code.");
        }
    }

}
