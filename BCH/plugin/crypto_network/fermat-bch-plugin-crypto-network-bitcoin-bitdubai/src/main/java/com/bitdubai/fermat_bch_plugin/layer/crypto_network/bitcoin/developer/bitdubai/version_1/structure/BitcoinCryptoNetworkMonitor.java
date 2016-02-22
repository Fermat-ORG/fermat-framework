package com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.Agent;
import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.BitcoinNetworkSelector;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.BlockchainConnectionStatus;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.RegTestNetwork.FermatTestNetwork;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.RegTestNetwork.FermatTestNetworkNode;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantBroadcastTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantCancellBroadcastTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetBlockchainConnectionStatusException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetCryptoTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantStoreBitcoinTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkConfiguration;
import com.bitdubai.fermat_bch_api.layer.crypto_network.enums.Status;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.database.BitcoinCryptoNetworkDatabaseDao;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.exceptions.BlockchainException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.exceptions.CantLoadTransactionFromFileException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import org.bitcoinj.core.Block;
import org.bitcoinj.core.BlockChain;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.PeerGroup;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionBroadcast;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.net.discovery.DnsDiscovery;
import org.bitcoinj.params.RegTestParams;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by rodrigo on 10/4/15.
 */
public class BitcoinCryptoNetworkMonitor implements Agent {
    /**
     * class variables
     */
    Wallet wallet;
    PeerGroup peerGroup;
    File walletFileName;
    BlockChain blockChain;
    BitcoinNetworkEvents events;
    final NetworkParameters NETWORK_PARAMETERS;
    final BlockchainNetworkType BLOCKCHAIN_NETWORKTYPE;
    BitcoinCryptoNetworkDatabaseDao bitcoinCryptoNetworkDatabaseDao;
    Runnable monitorAgent;
    String threadName;
    Thread monitorAgentThread;




    /**
     * Platform variables
     */
    PluginDatabaseSystem pluginDatabaseSystem;
    UUID pluginId;
    PluginFileSystem pluginFileSystem;
    ErrorManager errorManager;
    final String TRANSACTION_DIRECTORY = "CryptoNetworkTransactions";

    /**
     * Constructor
     * @param pluginDatabaseSystem
     */
    public BitcoinCryptoNetworkMonitor(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId, Wallet wallet, File walletFilename, PluginFileSystem pluginFileSystem, ErrorManager errorManager) {
        /**
         * I initialize the local variables
         */
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.wallet = wallet;
        this.pluginId = pluginId;
        this.walletFileName = walletFilename;
        this.pluginFileSystem = pluginFileSystem;
        this.errorManager = errorManager;
        /**
         * I get the network parameter from the passed wallet.
         */
        NETWORK_PARAMETERS = wallet.getNetworkParameters();
        BLOCKCHAIN_NETWORKTYPE = BitcoinNetworkSelector.getBlockchainNetworkType(NETWORK_PARAMETERS);
    }


    @Override
    public void start() throws CantStartAgentException {
        /**
         * I define the runnable agent
         */
        monitorAgent = new Runnable() {
            @Override
            public void run() {
                try {
                    doTheMainTask();
                } catch (BlockchainException e) {
                    e.printStackTrace();
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }
            }
        };

        // the thread name
        threadName = "CryptoNetworkMonitor_" + BLOCKCHAIN_NETWORKTYPE.getCode();

        System.out.println("***CryptoNetwork*** Monitor started for Network " + this.BLOCKCHAIN_NETWORKTYPE.getCode());

        /**
         * I define the thread name and start it.
         */
        monitorAgentThread = new Thread(monitorAgent, threadName);
        monitorAgentThread.start();
    }

    @Override
    public void stop() {
        System.out.println("***CryptoNetwork*** Stopping monitor agent for " + BLOCKCHAIN_NETWORKTYPE.getCode());
        if (monitorAgentThread != null){
            peerGroup.stop();
            monitorAgentThread.interrupt();
            try {
                monitorAgentThread.join();
                System.out.println("***CryptoNetwork*** Thread and peer group successfully stopped.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Wallet setter
     * @param wallet
     */
    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }


    /**
     * Agent main method
     */
    private void doTheMainTask() throws BlockchainException {
        System.out.println("***CryptoNetwork***  starting and connecting...");

        try{
            /**
             * creates the blockchain object for the specified network.
             */
            BitcoinCryptoNetworkBlockChain CryptoNetworkBlockChain = new BitcoinCryptoNetworkBlockChain(pluginFileSystem, NETWORK_PARAMETERS, wallet);
            blockChain = CryptoNetworkBlockChain.getBlockChain();

            /**
             * creates the peerGroup object
             */
            peerGroup = new PeerGroup(NETWORK_PARAMETERS, blockChain);
            peerGroup.addWallet(this.wallet);

            /**
             * add the events
             */
            events = new BitcoinNetworkEvents(BLOCKCHAIN_NETWORKTYPE, pluginDatabaseSystem, pluginId, this.walletFileName);
            peerGroup.addEventListener(events);
            this.wallet.addEventListener(events);
            blockChain.addListener(events);


            /**
             * I will connect to the regTest server or search for peers if we are in a different network.
             */
            if (NETWORK_PARAMETERS == RegTestParams.get()){
                FermatTestNetwork fermatTestNetwork = new FermatTestNetwork();
                for (FermatTestNetworkNode node : fermatTestNetwork.getNetworkNodes()){
                    peerGroup.addAddress(node.getPeerAddress());
                }
            } else
                peerGroup.addPeerDiscovery(new DnsDiscovery(NETWORK_PARAMETERS));

            /**
             * Define internal agent information.
             */
            peerGroup.setUserAgent(BitcoinNetworkConfiguration.USER_AGENT_NAME, BitcoinNetworkConfiguration.USER_AGENT_VERSION);

            /**
             * Update stats related active networks
             */
            this.getDao().updateActiveNetworks(BLOCKCHAIN_NETWORKTYPE, wallet.getImportedKeys().size());

            /**
             * starts the monitoring
             */
            peerGroup.setDownloadTxDependencies(true);
            peerGroup.start();
            peerGroup.downloadBlockChain();
            System.out.println("***CryptoNetwork*** Blockchain Download completed. Total blocks: " + blockChain.getBestChainHeight());

            /**
             * I will broadcast any transaction that might be in broadcasting status.
             * I might have this if the process was interrupted while broadcasting.
             */
            resumeBroadcastOfPendingTransactions(BLOCKCHAIN_NETWORKTYPE);

        } catch (Exception e){
            e.printStackTrace();
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }

    /**
     * Will get all transactions hashes in Broadcasting status to resume them.
     */
    private void resumeBroadcastOfPendingTransactions(BlockchainNetworkType blockchainNetworkType) {
        try {
            for (String txId :  getDao().getBroadcastTransactionsByStatus(blockchainNetworkType, Status.BROADCASTING)){
                try {
                    this.broadcastTransaction(txId);
                } catch (CantBroadcastTransactionException e) {
                    getDao().setBroadcastStatus(Status.WITH_ERROR, peerGroup.getConnectedPeers().size(), e, txId);
                }
            }
        } catch (CantExecuteDatabaseOperationException e) {
            /**
             * If I couldn't get the list due to a database error, then nothing left to do
             */
            e.printStackTrace();
        } {

        }
    }

    /**
     * Broadcast a well formed, commited and signed transaction into the specified network
     *
     * @param txHash the transaction hash to be broadcasted
     * @return the broadcasted transaction.
     * @throws CantBroadcastTransactionException
     */
    public synchronized void broadcastTransaction(final String txHash) throws CantBroadcastTransactionException{
        /**
         * The transaction is stored in the Wallet and the database, so I will make sure this is correct.
         */
        Sha256Hash sha256Hash = Sha256Hash.wrap(txHash);
         validateTransactionExistsinDatabase(txHash);

        // gets the transaction from the wallet.
        Transaction transaction = wallet.getTransaction(sha256Hash);

        // if I don't have it, it wasn't yet commited, I will load it from file and commit it.
        if (transaction == null){
            try {
                transaction = loadTransactionFromDisk(txHash);
            } catch (CantLoadTransactionFromFileException e) {
                throw new CantBroadcastTransactionException(CantBroadcastTransactionException.DEFAULT_MESSAGE, e, "No transaction was found to broadcast.", null);
            }

            // commit and save
            try {
                wallet.commitTx(transaction);
                wallet.saveToFile(walletFileName);
            } catch (IOException e) {
                throw new CantBroadcastTransactionException(CantBroadcastTransactionException.DEFAULT_MESSAGE, e, "There was an error saving the wallet to disk.", "IO issue");
            }
        }

        final int connectedPeers = peerGroup.getConnectedPeers().size();

         /**
          * will update this transaction status to broadcasting.
          */
         try {
             getDao().setBroadcastStatus(Status.BROADCASTING, connectedPeers, null, txHash);
         } catch (CantExecuteDatabaseOperationException e) {
             e.printStackTrace();
         }

        System.out.println("***CryptoNetwork***  Broadcasting transaction " + txHash + "...");


         TransactionBroadcast transactionBroadcast = peerGroup.broadcastTransaction(transaction);
         transactionBroadcast.setMinConnections(BitcoinNetworkConfiguration.MIN_BROADCAST_CONNECTIONS);

        transactionBroadcast.setProgressCallback(new TransactionBroadcast.ProgressCallback() {
            @Override
            public void onBroadcastProgress(double progress) {
                System.out.println("***CryptoNetwork*** Broadcast progress for transaction " + txHash + ": " + progress * 100 + " %");
            }
        });


         ListenableFuture<Transaction> future = transactionBroadcast.future();
        /**
         * I add the future that will get the broadcast result into a call back to respond to it.
         */
        final Transaction finalTransaction = transaction;
        Futures.addCallback(future, new FutureCallback<Transaction>() {
            @Override
            public void onSuccess(Transaction result) {

                try {
                    getDao().setBroadcastStatus(Status.BROADCASTED, connectedPeers, null, txHash);
                    /**
                     * Store this outgoing transaction in the table
                     */
                    UUID transactionId = getDao().getBroadcastedTransactionId(BLOCKCHAIN_NETWORKTYPE, txHash);
                    storeOutgoingTransaction(wallet, finalTransaction, transactionId);


                    /**
                     * saves the wallet again.
                     */
                    wallet.saveToFile(walletFileName);

                    System.out.println("***CryptoNetwork***  Transaction successfully broadcasted: " + finalTransaction.getHashAsString());
                } catch (CantExecuteDatabaseOperationException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("***CryptoNetwork*** Error bradcasting transaction " + txHash + "...");
                try {
                    getDao().setBroadcastStatus(Status.WITH_ERROR, connectedPeers, (Exception) t, txHash);
                } catch (CantExecuteDatabaseOperationException e) {
                    e.printStackTrace();
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }
            }
        });

        /**
         * starts the broadcasting.
         */
        transactionBroadcast.broadcast();
    }

    /**
     * Loads the passed transaction from disk
     * @param txHash
     * @return
     */
    public Transaction loadTransactionFromDisk(String txHash) throws CantLoadTransactionFromFileException {
        try {
            PluginTextFile pluginTextFile = pluginFileSystem.getTextFile(this.pluginId, TRANSACTION_DIRECTORY, txHash, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            String transactionContent = pluginTextFile.getContent();

            Transaction transaction = (Transaction) XMLParser.parseXML(transactionContent, new Transaction(NETWORK_PARAMETERS));
            return transaction;
        } catch (Exception e) {
            throw new CantLoadTransactionFromFileException(CantLoadTransactionFromFileException.CONTEXT_CONTENT_SEPARATOR, e, "Error loading transaction " + txHash + " from disk.", "IO Error");
        }
    }


    /**
     * Will search this transaction in the database and make sure it exists.
     * @param txHash
     * @throws CantBroadcastTransactionException
     */
    private void validateTransactionExistsinDatabase(String txHash) throws CantBroadcastTransactionException{
        try {
            if (!getDao().transactionExistsInBroadcast(txHash)){
                throw new CantBroadcastTransactionException(CantBroadcastTransactionException.DEFAULT_MESSAGE, null, "the specified transaction " + txHash + " is not stored in the database.", "CryptoNetwork");
            }
        } catch (CantExecuteDatabaseOperationException e) {
            /**
             * if I couldn't validate it, because the database thrown an error, I will continue assuming that exists.
             */
            e.printStackTrace();
        }
    }

    /**
     * validates the passed transaction exists in the Wallet
     * @param sha256Hash
     */
    private void validateTransactionExistsInWallet(Sha256Hash sha256Hash) throws CantBroadcastTransactionException {
        Transaction transaction = wallet.getTransaction(sha256Hash);

        if (transaction == null){
            StringBuilder output = new StringBuilder("The transaction " + sha256Hash.toString());
            output.append(" is not stored in the CryptoNetwork.");
            output.append(System.lineSeparator());
            output.append("Stored transactions are:");
            output.append(System.lineSeparator());
            for (Transaction storedTransaction : wallet.getTransactions(true)){
                output.append(storedTransaction.getHashAsString());
                output.append(System.lineSeparator());
            }

            throw new CantBroadcastTransactionException(CantBroadcastTransactionException.DEFAULT_MESSAGE, null, output.toString(), null);
        }
    }

    /**
     * Stores and outgoing transaction into the database
     * @param wallet
     * @param tx
     * @param transactionId
     */
    private void storeOutgoingTransaction(Wallet wallet, Transaction tx, UUID transactionId) {
        events.saveOutgoingTransaction(wallet, tx, transactionId);
    }

    /**
     * gets the peer group
     * @return
     */
    public PeerGroup getPeerGroup() {
        return peerGroup;
    }

    /**
     * Gets from the DownloadPeer the block that matches the passed hash
     * @param blockHash
     * @return
     */
    private Block getBlockFromPeer(Sha256Hash blockHash) throws CantGetTransactionException {
        try {
            return peerGroup.getDownloadPeer().getBlock(blockHash).get(1, TimeUnit.MINUTES);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new CantGetTransactionException(CantGetTransactionException.DEFAULT_MESSAGE, e, "There was a problem trying to get the block from the Peer.", null);
        }
    }

    /**
     * Stores a Bitcoin Transaction in the CryptoNetwork to be broadcasted later
     * @param tx
     * @param transactionId
     * @throws CantStoreBitcoinTransactionException
     */
    public synchronized void storeBitcoinTransaction (Transaction tx, UUID transactionId, boolean commit) throws CantStoreBitcoinTransactionException{
        try {
            /**
             * I store it in the database
             */
            getDao().storeBitcoinTransaction(BLOCKCHAIN_NETWORKTYPE, tx.getHashAsString(), transactionId, peerGroup.getConnectedPeers().size(), peerGroup.getDownloadPeer().getAddress().toString());

            if (commit){
                // commit and save the transaction
                wallet.commitTx(tx);
                wallet.saveToFile(walletFileName);

                // verify it was successfuly stored.
                Transaction storedTransaction = wallet.getTransaction(tx.getHash());
                if (storedTransaction == null){
                    CantStoreBitcoinTransactionException e = new  CantStoreBitcoinTransactionException(CantStoreBitcoinTransactionException.DEFAULT_MESSAGE, null, "transaction was not correctly stored at the wallet.", null);
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                    throw e;

                }
            } else {
                // if no commit, then I will save it into a file.
                persistTransactionOnFile(tx);
            }


            System.out.println("***CryptoNetwork*** Transaction successfully stored for broadcasting: " + tx.getHashAsString());
        } catch (CantExecuteDatabaseOperationException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantStoreBitcoinTransactionException(CantStoreBitcoinTransactionException.DEFAULT_MESSAGE, e, "There was an error storing the transaction in the database", null);
        } catch (Exception e) {
            /**
             * If there was an error, then I will make sure that the transaction is not left stored at the database.
             */
            try {
                getDao().deleteStoredBitcoinTransaction(tx.getHashAsString());
                deleteTransactionFromFile(tx.getHashAsString());
            } catch (CantExecuteDatabaseOperationException e1) {
                /**
                 * I will ignore this error
                 */
                e1.printStackTrace();
            }

            CantStoreBitcoinTransactionException exception = new CantStoreBitcoinTransactionException(CantStoreBitcoinTransactionException.DEFAULT_MESSAGE, e, "Error storing the transaction in the wallet. TxId: " + tx.getHashAsString(), "Crypto Network");
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw exception;
        }
    }

    /**
     * deletes the passed transaction from file
     * @param hashAsString
     */
    private void deleteTransactionFromFile(String hashAsString) {
        try {
            pluginFileSystem.deleteTextFile(this.pluginId, TRANSACTION_DIRECTORY, hashAsString, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
        } catch (CantCreateFileException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stores the passed transaction into disk
     * @param tx
     */
    private void persistTransactionOnFile(Transaction tx) throws CantStoreBitcoinTransactionException {
        String txId = tx.getHashAsString();
        try {
            PluginTextFile pluginTextFile = pluginFileSystem.createTextFile(this.pluginId, TRANSACTION_DIRECTORY, txId, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            String transactionContent = XMLParser.parseObject(tx);
            pluginTextFile.setContent(transactionContent);
            pluginTextFile.persistToMedia();
        } catch (Exception e) {
            throw new CantStoreBitcoinTransactionException(CantStoreBitcoinTransactionException.DEFAULT_MESSAGE, e, "Cant store transaction into file", "IO Error");
        }
    }

    /**
     * returns and instance of the database dao class
     * @return
     */
    private BitcoinCryptoNetworkDatabaseDao getDao() {
        if (bitcoinCryptoNetworkDatabaseDao == null)
            bitcoinCryptoNetworkDatabaseDao = new BitcoinCryptoNetworkDatabaseDao(this.pluginId, this.pluginDatabaseSystem);
        return bitcoinCryptoNetworkDatabaseDao;
    }

    /**
     * invalidates the passed transaction by clearing inputs and outputs.
     * @param txHash
     */
    public void cancelBroadcast(String txHash) throws CantCancellBroadcastTransactionException{
        try {
            // delete the transaction
            deleteTransactionFromFile(txHash);

            /**
             * update Broadcasting table to set it to cancelled.
             */
            this.getDao().setBroadcastStatus(Status.CANCELLED, peerGroup.getConnectedPeers().size(), null, txHash);

            System.out.println("***CryptoNetwork*** Transaction " + txHash + " cancelled.");
        } catch (Exception e) {
            CantCancellBroadcastTransactionException exception = new CantCancellBroadcastTransactionException(CantCancellBroadcastTransactionException.DEFAULT_MESSAGE, e, "Transaction couldn't rollback properly.", "Crypto Network error");
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw exception;
        }
    }

    /**
     * Gets the current blockchainConnectionStatus
     * @return
     */
    public BlockchainConnectionStatus getBlockchainConnectionStatus() throws CantGetBlockchainConnectionStatusException {
        try{
            int connectedPeers = peerGroup.getConnectedPeers().size();
            String downloadNodeIp = peerGroup.getDownloadPeer().getAddress().toString();
            long downloadPing = peerGroup.getDownloadPeer().getLastPingTime();

            BlockchainConnectionStatus blockchainConnectionStatus = new BlockchainConnectionStatus(connectedPeers, downloadNodeIp, downloadPing, BLOCKCHAIN_NETWORKTYPE);

            return blockchainConnectionStatus;
        } catch (Exception e){
            CantGetBlockchainConnectionStatusException exception = new CantGetBlockchainConnectionStatusException(CantGetBlockchainConnectionStatusException.DEFAULT_MESSAGE, e, "Error getting connection status from peers.", null);
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw exception;
        }

    }

    /**
     * Gets the specified Transaction from the Blockchain by looking it locally, or on the blockchain using the block hash
     * @param transactionHash
     * @param transactionBlockHash
     * @return
     * @throws CantGetTransactionException
     */
    public Transaction getTransactionFromBlockChain(String transactionHash, String transactionBlockHash) throws CantGetTransactionException {
        /**
         * I will check I don't get nulls in the parameters
         */
        if (transactionHash == null )
            throw new CantGetTransactionException(CantGetTransactionException.DEFAULT_MESSAGE, null, "TxHash parameters can't be null", null);

        /**
         * I will get the transaction from our own database if we have it.
         */
        Sha256Hash transactionSha256Hash = Sha256Hash.wrap(transactionHash);
        Transaction storedTransaction = wallet.getTransaction(transactionSha256Hash);

        if (storedTransaction != null)
            return storedTransaction;

        /**
         * I don't have it locally, so I will try to get it from the stored blockstore
         */
        if (transactionBlockHash == null ){
            CantGetTransactionException e = new CantGetTransactionException(CantGetTransactionException.DEFAULT_MESSAGE, null, "BlockHash parameters can't be null", null);
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;

        }


        Sha256Hash blockHash = Sha256Hash.wrap(transactionBlockHash);

        try {
            /**
             * If I don't have this block, then I will get the block from the peer
             */

            Block genesisBlock = getBlockFromPeer(blockHash);

            /**
             * Will search all transactions from the block until I find my own.
             */
            for (Transaction transaction : genesisBlock.getTransactions()) {
                if (transaction.getHashAsString().contentEquals(transactionHash)) {
                    /**
                     * I form the CryptoTransaction and return it.
                     */
                    return transaction;
                }
            }
        } catch (Exception e) {
            CantGetTransactionException exception = new CantGetTransactionException(CantGetTransactionException.DEFAULT_MESSAGE, e, "error getting the Transaction from the blockchain" , null);
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw exception;
        }

        return null;
    }
}
