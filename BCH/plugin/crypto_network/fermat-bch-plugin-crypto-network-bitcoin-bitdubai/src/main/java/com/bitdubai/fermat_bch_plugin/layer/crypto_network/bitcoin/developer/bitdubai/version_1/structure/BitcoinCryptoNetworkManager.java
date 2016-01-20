package com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.ProtocolStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.BitcoinNetworkSelector;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.BlockchainConnectionStatus;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.BroadcastStatus;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantBroadcastTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantCancellBroadcastTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantFixTransactionInconsistenciesException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetBlockchainConnectionStatusException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetBroadcastStatusException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetCryptoTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetTransactionCryptoStatusException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantStoreBitcoinTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.enums.Status;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.enums.CryptoVaults;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.database.BitcoinCryptoNetworkDatabaseDao;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.exceptions.BlockchainException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.util.TransactionProtocolData;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.google.common.collect.Lists;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionInput;
import org.bitcoinj.core.TransactionOutput;
import org.bitcoinj.core.UTXO;
import org.bitcoinj.core.UTXOProvider;
import org.bitcoinj.core.UTXOProviderException;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.store.UnreadableWalletException;
import org.bitcoinj.wallet.WalletTransaction;

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

import static com.bitdubai.fermat_dap_api.layer.all_definition.util.Validate.isValidString;

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
public class BitcoinCryptoNetworkManager implements TransactionProtocolManager {

    /**
     * BitcoinJ wallet where I'm storing the public keys and transactions
     */
    private final String WALLET_FILENAME = "/data/data/com.bitdubai.fermat/files/wallet_";

    /**
     * UTXO Provider interface variables
     */
    BlockchainNetworkType utxoProviderNetworkParameter;

    /**
     * class variables
     */
    BitcoinCryptoNetworkMonitor bitcoinCryptoNetworkMonitor;
    File walletFile;

    /**
     * List of running agents per network
     */
    HashMap<BlockchainNetworkType, BitcoinCryptoNetworkMonitor> runningAgents;

    /**
     * Platform variables
     */
    EventManager eventManager;
    PluginDatabaseSystem pluginDatabaseSystem;
    BitcoinCryptoNetworkDatabaseDao bitcoinCryptoNetworkDatabaseDao;
    UUID pluginId;

    /**
     * Constructor
     *
     * @param eventManager
     * @param pluginDatabaseSystem
     */
    public BitcoinCryptoNetworkManager(EventManager eventManager, PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.eventManager = eventManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;

        runningAgents = new HashMap<>();
    }

    /**
     * Monitor the bitcoin network with the passes Key Lists.
     *
     * @param blockchainNetworkTypes
     * @param keyList
     */
    public void monitorNetworkFromKeyList(CryptoVaults cryptoVault, List<BlockchainNetworkType> blockchainNetworkTypes, List<ECKey> keyList) throws CantStartAgentException {
        /**
         * This method will be called from agents from the Vaults. New keys may be added on each call or not.
         */
        try {
            getDao().updateCryptoVaultsStatistics(cryptoVault, keyList.size());
        } catch (CantExecuteDatabaseOperationException e) {
            //If stats where not updated, I will just continue.
            e.printStackTrace();
        }

        /**
         * For each network that is active to be monitored I will...
         */
        for (BlockchainNetworkType blockchainNetworkType : blockchainNetworkTypes) {

            /**
             * load (if any) existing wallet.
             */
            Wallet wallet = getWallet(blockchainNetworkType, keyList);


            /**
             * add new keys (if any).
             */
            boolean isWalletReset = false;

            /**
             * if this is the Watch Only Vault, I won't be importing keys, I will be watching them
             */
            if (cryptoVault == CryptoVaults.BITCOIN_WATCH_ONLY) {
                if (areNewKeysWatched(wallet, keyList, blockchainNetworkType)) {
                    NetworkParameters networkParameters = BitcoinNetworkSelector.getNetworkParameter(blockchainNetworkType);
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
                    isWalletReset = true;
                }
            } else {
                /**
                 * regulat vault, so will try to import new keys if any
                 */
                if (areNewKeysAdded(wallet, keyList)) {
                    wallet.importKeys(keyList);
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
                    BitcoinCryptoNetworkMonitor bitcoinCryptoNetworkMonitor = runningAgents.get(blockchainNetworkType);
                    bitcoinCryptoNetworkMonitor.stop();
                    runningAgents.remove(blockchainNetworkType);


                    /**
                     * once the agent is stoped, I will restart it with the new wallet.
                     */
                    File walletFilename = new File(WALLET_FILENAME + blockchainNetworkType.getCode());
                    bitcoinCryptoNetworkMonitor = new BitcoinCryptoNetworkMonitor(this.pluginDatabaseSystem, pluginId, wallet, walletFilename);
                    runningAgents.put(blockchainNetworkType, bitcoinCryptoNetworkMonitor);

                    bitcoinCryptoNetworkMonitor.start();
                }
            } else {
                /**
                 * If the agent for the network is not running, I will start a new one.
                 */
                File walletFilename = new File(WALLET_FILENAME + blockchainNetworkType.getCode());
                BitcoinCryptoNetworkMonitor bitcoinCryptoNetworkMonitor = new BitcoinCryptoNetworkMonitor(this.pluginDatabaseSystem, pluginId, wallet, walletFilename);
                runningAgents.put(blockchainNetworkType, bitcoinCryptoNetworkMonitor);

                bitcoinCryptoNetworkMonitor.start();
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

        NetworkParameters networkParameters = BitcoinNetworkSelector.getNetworkParameter(blockchainNetworkType);
        for (ECKey ecKey : keyList) {
            newAddresses.add(ecKey.toAddress(networkParameters));
        }

        /**
         * I compare both lists.
         */
        newAddresses.removeAll(watchedAddresses);
        if (newAddresses.isEmpty())
            return false;
        else
            return true;
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
            getDao().deleteDetailedCryptoStats(cryptoVault, blockchainNetworkType);
            getDao().updateDetailedCryptoStats(cryptoVault, blockchainNetworkType, keyList);
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
    private synchronized Wallet getWallet(BlockchainNetworkType blockchainNetworkType, @Nullable List<ECKey> keyList) {
        Wallet wallet;
        String fileName = WALLET_FILENAME + blockchainNetworkType.getCode();
        walletFile = new File(fileName);
        try {
            wallet = Wallet.loadFromFile(walletFile);
        } catch (UnreadableWalletException e) {
            /**
             * If I couldn't load the wallet from file, I'm assuming is a new wallet and I will create it.
             * I'm creating it by importing the keys sent by the vault.
             */
            //wallet = Wallet.fromKeys(BitcoinNetworkSelector.getNetworkParameter(blockchainNetworkType), keyList);

            wallet = new Wallet(BitcoinNetworkSelector.getNetworkParameter(blockchainNetworkType));
            wallet.importKeys(keyList);

            /**
             * Will set the autosave information and save it.
             */
            wallet.autosaveToFile(walletFile, 1, TimeUnit.SECONDS, null);
            try {
                wallet.saveToFile(walletFile);
            } catch (IOException e1) {
                e1.printStackTrace(); // I will continue because the key addition will trigger an autosave anyway.
            }
        }
        return wallet;
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
        keys.removeAll(walletKeys);

        /**
         * If there are still keys, then we have new ones.
         */
        if (keys.size() > 0)
            return true;
        else
            return false;
    }

    /**
     * Verifies if for the passed network type, an Agent is already running.
     *
     * @param blockchainNetworkType
     * @return
     */
    private boolean isAgentRunning(BlockchainNetworkType blockchainNetworkType) {
        if (runningAgents.get(blockchainNetworkType) == null)
            return false;
        else
            return true;
    }

    /**
     * instantiate if needed the dao object to access the database
     *
     * @return
     */
    private BitcoinCryptoNetworkDatabaseDao getDao() {
        if (bitcoinCryptoNetworkDatabaseDao == null)
            bitcoinCryptoNetworkDatabaseDao = new BitcoinCryptoNetworkDatabaseDao(this.pluginId, this.pluginDatabaseSystem);
        return bitcoinCryptoNetworkDatabaseDao;
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
            getDao().confirmReception(transactionID);
        } catch (CantExecuteDatabaseOperationException e) {
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
                getDao().setTransactionProtocolStatus(transaction.getTransactionID(), ProtocolStatus.SENDING_NOTIFIED);
            }
        } catch (CantExecuteDatabaseOperationException e) {
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
        return getDao().getPendingTransactionProtocolData();
    }

    /**
     * Gets the CryptoTransaction list that matches the specified hash
     *
     * @param txHash
     * @return
     * @throws CantGetCryptoTransactionException
     */
    public List<CryptoTransaction> getGenesisTransaction(String txHash) throws CantGetCryptoTransactionException {
        try {
            return getDao().getIncomingCryptoTransaction(txHash);
        } catch (CantExecuteDatabaseOperationException e) {
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
            blockchainNetworkType = getDao().getBlockchainNetworkTypeFromBroadcast(txHash);
        } catch (CantExecuteDatabaseOperationException e) {
            blockchainNetworkType = BlockchainNetworkType.DEFAULT;
        }
        runningAgents.get(blockchainNetworkType).broadcastTransaction(txHash);
    }


    /**
     * Gets the specified bitcoin transaction
     *
     * @param transactionHash
     * @return
     */
    public Transaction getBitcoinTransaction(BlockchainNetworkType blockchainNetworkType, String transactionHash) {
        Wallet wallet = getWallet(blockchainNetworkType, null);
        Sha256Hash sha256Hash = Sha256Hash.wrap(transactionHash);
        Transaction transaction = wallet.getTransaction(sha256Hash);
        return transaction;
    }

    /**
     * Gets all the transactions stored in the specified network.
     *
     * @param blockchainNetworkType
     * @return
     */
    public List<Transaction> getBitcoinTransactions(BlockchainNetworkType blockchainNetworkType) {
        Wallet wallet = getWallet(blockchainNetworkType, null);
        return wallet.getTransactionsByTime();
    }

    public synchronized List<Transaction> getUnspentBitcoinTransactions(BlockchainNetworkType blockchainNetworkType) {
        Wallet wallet = getWallet(blockchainNetworkType, null);
        List<Transaction> transactions = new ArrayList<>(wallet.getTransactionPool(WalletTransaction.Pool.UNSPENT).values());
        return transactions;
    }

    /**
     * Will get the CryptoTransaction directly from the blockchain by requesting it to a peer.
     * If the transaction is not part of any of our vaults, we will ask it to a connected peer to retrieve it.
     *
     * @param txHash    the Hash of the transaction we are going to look for.
     * @param blockHash the Hash of block where this transaction was stored..
     * @return a CryptoTransaction with the information of the transaction.
     * @throws CantGetCryptoTransactionException
     */

    public CryptoTransaction getCryptoTransactionFromBlockChain(String txHash, String blockHash) throws CantGetCryptoTransactionException {
        /**
         * I will get the CryptoTransaction from all agents running. Only one will return the CryptoTransaction
         */
        for (BitcoinCryptoNetworkMonitor monitor : runningAgents.values()) {
            return monitor.getCryptoTransactionFromBlockChain(txHash, blockHash);
        }

        /**
         * if no agents are running, then no CryptoTransaction to return.
         */
        return null;
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
        /**
         * will get it from the specified agent monitoring the passed network.
         */
        try {
            return runningAgents.get(blockchainNetworkType).getTransactionFromBlockChain(parentTransactionHash, transactionBlockHash);
        } catch (CantGetTransactionException e) {
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
        List<Transaction> transactions = getBitcoinTransactions(BlockchainNetworkType.DEFAULT);

        for (Transaction transaction : transactions) {
            /**
             * I will search on the inputs of each transaction and search for the passed hash.
             */
            for (TransactionInput input : transaction.getInputs()) {
                if (input.getOutpoint().getHash().toString().contentEquals(parentHash))
                    cryptoTransaction = CryptoTransaction.getCryptoTransaction(transaction);
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
        CryptoTransaction newCryptoTransaction = new CryptoTransaction();

        newCryptoTransaction.setTransactionHash(cryptoTransaction.getTransactionHash());
        newCryptoTransaction.setBlockHash(cryptoTransaction.getBlockHash());
        newCryptoTransaction.setOp_Return(cryptoTransaction.getOp_Return());
        newCryptoTransaction.setAddressTo(cryptoTransaction.getAddressTo());
        newCryptoTransaction.setAddressFrom(cryptoTransaction.getAddressFrom());
        newCryptoTransaction.setCryptoAmount(cryptoTransaction.getCryptoAmount());
        newCryptoTransaction.setCryptoStatus(cryptoStatus);
        newCryptoTransaction.setCryptoCurrency(cryptoTransaction.getCryptoCurrency());

        return newCryptoTransaction;
    }

    /**
     * Will get all the CryptoTransactions stored in the CryptoNetwork which are a child of a parent Transaction
     *
     * @param parentHash the parent transaction
     * @param depth      the depth of how many transactions we will navigate until we reach the parent transaction. Max is 10
     * @return
     * @throws CantGetCryptoTransactionException
     */
    public List<CryptoTransaction> getChildCryptoTransaction(String parentHash, int depth) throws CantGetCryptoTransactionException {
        return null;
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
            return getDao().getTransactionCryptoStatus(txHash);
        } catch (CantExecuteDatabaseOperationException e) {
            throw new CantGetTransactionCryptoStatusException(CantGetTransactionCryptoStatusException.DEFAULT_MESSAGE, e, "Database error getting CryptoStatus for transaction: " + txHash, "database issue");
        }
    }

    /**
     * Will check and fix any inconsistency that may be in out transaction table.
     * For example, If i don't have all adressTo or From, or coin values of zero.
     *
     * @throws CantFixTransactionInconsistenciesException
     */
    private void fixTransactionInconsistencies() throws CantFixTransactionInconsistenciesException {
        List<TransactionProtocolData> transactions = null;

        try {
            transactions = getDao().getAllTransactionProtocolData();
        } catch (CantExecuteDatabaseOperationException e) {
            throw new CantFixTransactionInconsistenciesException(CantFixTransactionInconsistenciesException.DEFAULT_MESSAGE, e, "Database error.", "Database error.");
        }

        /**
         * Will iterate each transaction and fix any inconsistency
         */
        for (TransactionProtocolData transactionProtocolData : transactions) {
            if (transactionProtocolData.getCryptoTransaction().getAddressFrom().getAddress().contentEquals("Empty"))
                fixAddressFromInconsistency(transactionProtocolData);

            if (transactionProtocolData.getCryptoTransaction().getAddressTo().getAddress().contentEquals("Empty"))
                fixAddressToInconsistency(transactionProtocolData);

            if (transactionProtocolData.getCryptoTransaction().getCryptoAmount() == 0)
                fixCryptoAmountInconsistency(transactionProtocolData);
        }
    }

    /**
     * Fixes any inconsistency we may have in
     * the Crypto Amount
     *
     * @param transactionProtocolData
     */
    private void fixCryptoAmountInconsistency(TransactionProtocolData transactionProtocolData) {
        Transaction transaction = getBitcoinTransaction(BlockchainNetworkType.DEFAULT, transactionProtocolData.getCryptoTransaction().getTransactionHash());
        //todo get the correct address and update the database
    }

    /**
     * Fixes any inconsistency we may have in
     * the AddressTo
     *
     * @param transactionProtocolData
     */
    private void fixAddressToInconsistency(TransactionProtocolData transactionProtocolData) {
        Transaction transaction = getBitcoinTransaction(BlockchainNetworkType.DEFAULT, transactionProtocolData.getCryptoTransaction().getTransactionHash());
        //todo get the correct address and update the database
    }

    /**
     * Fixes any inconsistency we may have in the AddressFrom
     *
     * @param transactionProtocolData
     */
    private void fixAddressFromInconsistency(TransactionProtocolData transactionProtocolData) {
        Transaction transaction = getBitcoinTransaction(BlockchainNetworkType.DEFAULT, transactionProtocolData.getCryptoTransaction().getTransactionHash());
        //todo get the correct address and update the database

    }

    /**
     * Stores a Bitcoin Transaction in the CryptoNetwork to be broadcasted later
     *
     * @param blockchainNetworkType
     * @param tx
     * @param transactionId
     * @throws CantStoreBitcoinTransactionException
     */
    public synchronized void storeBitcoinTransaction(BlockchainNetworkType blockchainNetworkType, Transaction tx, UUID transactionId) throws CantStoreBitcoinTransactionException {
        runningAgents.get(blockchainNetworkType).storeBitcoinTransaction(tx, transactionId);
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
            return getDao().getBroadcastStatus(txHash);
        } catch (CantExecuteDatabaseOperationException e) {
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
            blockchainNetworkType = getDao().getBlockchainNetworkTypeFromBroadcast(txHash);
        } catch (CantExecuteDatabaseOperationException e) {
            blockchainNetworkType = BlockchainNetworkType.DEFAULT;
        }


        /**
         * Will invalidate the transaction in the wallet
        */
        runningAgents.get(blockchainNetworkType).cancelBroadcast(txHash);

        /**
         * marks the transaction as cancelled in the database
         */
        try {
            getDao().setBroadcastStatus(Status.CANCELLED, 0, null, txHash);
        } catch (CantExecuteDatabaseOperationException e) {
            throw new CantCancellBroadcastTransactionException(CantCancellBroadcastTransactionException.DEFAULT_MESSAGE, e, "Database error while cancelling transaction.", "database issue");
        }

    }

    /**
     * Will get the BlockchainConnectionStatus for the specified network.
     *
     * @param blockchainNetworkType the Network type we won't to get info from. If the passed network is not currently activated,
     *                              then we will receive null.
     * @return BlockchainConnectionStatus with information of amount of peers currently connected, etc.
     * @throws CantGetBlockchainConnectionStatusException
     */
    public BlockchainConnectionStatus getBlockchainConnectionStatus(BlockchainNetworkType blockchainNetworkType) throws CantGetBlockchainConnectionStatusException {
        return runningAgents.get(blockchainNetworkType).getBlockchainConnectionStatus();
    }

    /**
     * Gets a stored CryptoTransaction in wathever network.
     *
     * @param txHash the transaction hash we want to get the CryptoTransaction
     * @return the last recorded CryptoTransaction.
     * @throws CantGetCryptoTransactionException
     */
    public CryptoTransaction getCryptoTransaction(String txHash) throws CantGetCryptoTransactionException {
        try {
            return getDao().getCryptoTransaction(txHash);
        } catch (CantExecuteDatabaseOperationException e) {
            throw new CantGetCryptoTransactionException(CantGetCryptoTransactionException.DEFAULT_MESSAGE, e, "database error getting the last crypto transaction.", "database error");
        }
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
            CryptoTransaction cryptoTransaction = CryptoTransaction.getCryptoTransaction(this.getGenesisTransaction(blockchainNetworkType, transactionChain));
            return cryptoTransaction;
        } catch (CantGetTransactionException e) {
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
            blockchainNetworkType = BlockchainNetworkType.DEFAULT;

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
            if (!isValidString(entry.getValue()))
                continue;

            /**
             * if this is the first entry, I will only get the first transaction in the chain
             */
            if (childTransaction == null) {
                try {
                    childTransaction = this.getTransactionFromBlockChain(blockchainNetworkType, entry.getKey(), entry.getValue());
                } catch (CantGetTransactionsException e) {
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
                    throw new CantGetTransactionException(CantGetTransactionException.DEFAULT_MESSAGE, null, output.toString(), null);
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
            for (Transaction transaction : getChildBitcoinTransactionsFromParent(parentTransactionHash)){
                cryptoTransactions.add(CryptoTransaction.getCryptoTransaction(transaction));
            }
        } catch (CantGetTransactionException e) {
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
    private List<Transaction> getChildBitcoinTransactionsFromParent(String parentTransactionHash) throws CantGetTransactionException{
        List<Transaction> transactions = new ArrayList<>();

        Sha256Hash txHash = Sha256Hash.wrap(parentTransactionHash);
        /**
         * I will get all the stored transactions
         */
        for (Transaction transaction : this.getBitcoinTransactions(BlockchainNetworkType.DEFAULT)){
            /**
             * for each transaction I will search in the input the outpoint that matches the passed transactionHash
             */
            for (TransactionInput input : transaction.getInputs()){
                if (input.getOutpoint().getHash().equals(txHash))
                    transactions.add(transaction);
            }
        }
        /**
         * I'm not removing duplicates
         */
        return transactions;
    }
}
