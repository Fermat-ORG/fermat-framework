package com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.Agent;
import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransactionType;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;

import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantBroadcastTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantCancellBroadcastTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetBlockchainConnectionStatusException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantStoreBitcoinTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.enums.Status;
import com.bitdubai.fermat_bch_api.layer.crypto_network.fermat.FermatNetworkConfiguration;
import com.bitdubai.fermat_bch_api.layer.crypto_network.util.BlockchainConnectionStatus;
import com.bitdubai.fermat_bch_api.layer.crypto_network.util.BlockchainDownloadProgress;
import com.bitdubai.fermat_bch_api.layer.crypto_network.util.ConnectedBitcoinNode;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.version_1.database.FermatCryptoNetworkDatabaseDao;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.version_1.exceptions.CantLoadTransactionFromFileException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.version_1.util.FermatBlockchainNetworkSelector;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.version_1.util.FermatBlockchainProvider;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.version_1.util.FermatTransactionConverter;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import org.apache.commons.lang.StringUtils;
import org.bitcoinj.core.Block;
import org.bitcoinj.core.BlockChain;
import org.bitcoinj.core.Context;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Peer;
import org.bitcoinj.core.PeerAddress;
import org.bitcoinj.core.PeerGroup;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionConfidence;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.net.discovery.DnsDiscovery;
import org.bitcoinj.utils.ContextPropagatingThreadFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by rodrigo on 6/22/16.
 */
public class FermatCryptoNetworkMonitor  implements Agent {
    /**
     * class variables
     */
    private MonitorAgent monitorAgent;
    private String threadName;
    private Thread monitorAgentThread;
    private Wallet wallet;
    private File walletFileName;
    private BlockchainDownloadProgress blockchainDownloadProgress;
    private final FermatCryptoNetworkDatabaseDao dao;
    private final CryptoCurrency CURRENCY = FermatNetworkConfiguration.CRYPTO_CURRENCY;
    private FermatBlockchainProvider blockchainProvider;
    private ContextPropagatingThreadFactory contextPropagatingThreadFactory;


    final NetworkParameters NETWORK_PARAMETERS;
    final BlockchainNetworkType BLOCKCHAIN_NETWORKTYPE;
    final Context context;


    /**
     * Platform variables
     */
    UUID pluginId;
    PluginFileSystem pluginFileSystem;
    ErrorManager errorManager;
    final EventManager eventManager;


    /**
     * Constructor
     */
    public FermatCryptoNetworkMonitor(UUID pluginId,
                                       Wallet wallet,
                                       File walletFilename,
                                       PluginFileSystem pluginFileSystem,
                                       ErrorManager errorManager,
                                       FermatCryptoNetworkDatabaseDao FermatCryptoNetworkDatabaseDao,
                                       EventManager eventManager,
                                      FermatBlockchainProvider blockchainProvider) {
        /**
         * I initialize the local variables
         */
        this.wallet = wallet;
        this.pluginId = pluginId;
        this.walletFileName = walletFilename;
        this.pluginFileSystem = pluginFileSystem;
        this.errorManager = errorManager;
        this.context = wallet.getContext();
        this.dao = FermatCryptoNetworkDatabaseDao;
        this.eventManager = eventManager;
        this.blockchainProvider = blockchainProvider;


        /**
         * Define the constants
         */
        NETWORK_PARAMETERS = context.getParams();
        BLOCKCHAIN_NETWORKTYPE = FermatBlockchainNetworkSelector.getBlockchainNetworkType(NETWORK_PARAMETERS);

        this.blockchainProvider.setActiveBlockchainNetworkType(BLOCKCHAIN_NETWORKTYPE);

        contextPropagatingThreadFactory = new ContextPropagatingThreadFactory("FermatNetworkMonitor_" + BLOCKCHAIN_NETWORKTYPE.getCode());

    }


    @Override
    public void start() throws CantStartAgentException {
        /**
         * I define the MonitorAgent private class
         */
        monitorAgent = new MonitorAgent(this.wallet, this.walletFileName, this.pluginId, this.pluginFileSystem, this.errorManager, NETWORK_PARAMETERS, BLOCKCHAIN_NETWORKTYPE, dao, this.context);

        // I define the thread name and start it.
        threadName = "FermatCryptoNetworkMonitor_" + BLOCKCHAIN_NETWORKTYPE.getCode();
        monitorAgentThread = contextPropagatingThreadFactory.newThread(monitorAgent);

        /**
         * and start it
         */
        monitorAgentThread.start();
        System.out.println("***FermatCryptoNetwork*** Monitor started for Network " + this.BLOCKCHAIN_NETWORKTYPE.getCode());
    }

    @Override
    public void stop() {
        System.out.println("***FermatCryptoNetwork*** Stopping monitor agent for " + BLOCKCHAIN_NETWORKTYPE.getCode());
        if (monitorAgentThread != null){
            PeerGroup peerGroup = monitorAgent.getPeerGroup();
            if (peerGroup != null){
                peerGroup.stop();

                while (peerGroup.isRunning()){
                    //peergroup is still running.
                    System.out.println("***FermatCryptoNetwork*** stopping peer group service...");
                }
            }
            System.out.println("***FermatCryptoNetwork*** Thread and peer group successfully stopped.");
            try{
                monitorAgentThread.interrupt();
                System.out.println("***FermatCryptoNetwork*** Monitor Agent Thread state is: " + monitorAgentThread.getState().name());
            } catch (Exception e){
                e.printStackTrace();
            }

        }
    }


    /**
     * private class that runs on a separate thread
     */
    private class MonitorAgent implements Runnable{
        /**
         * private class variables
         */
        Wallet wallet;
        PeerGroup peerGroup;
        File walletFileName;
        BlockChain blockChain;
        FermatNetworkEvents events;
        final FermatCryptoNetworkDatabaseDao dao;

        // private class constanst
        final NetworkParameters NETWORK_PARAMETERS;
        final BlockchainNetworkType BLOCKCHAIN_NETWORKTYPE;
        final String TRANSACTION_DIRECTORY = "FermatCryptoNetworkTransactions";
        final Context context;


        /**
         * platform variables
         */
        UUID pluginId;
        PluginFileSystem pluginFileSystem;
        ErrorManager errorManager;


        /**
         * Private class Constructor
         * @param wallet
         * @param walletFileName
         * @param pluginId
         * @param pluginFileSystem
         * @param errorManager
         * @param networkParameters
         * @param blockchainNetworkType
         */
        public MonitorAgent(Wallet wallet,
                            File walletFileName,
                            UUID pluginId,
                            PluginFileSystem pluginFileSystem,
                            ErrorManager errorManager,
                            NetworkParameters networkParameters,
                            BlockchainNetworkType blockchainNetworkType,
                            FermatCryptoNetworkDatabaseDao FermatCryptoNetworkDatabaseDao,
                            Context context) {

            this.wallet = wallet;
            this.walletFileName = walletFileName;
            this.pluginId = pluginId;
            this.pluginFileSystem = pluginFileSystem;
            this.errorManager = errorManager;
            this.NETWORK_PARAMETERS = networkParameters;
            this.BLOCKCHAIN_NETWORKTYPE = blockchainNetworkType;
            this.context = context;
            this.dao = FermatCryptoNetworkDatabaseDao;
        }

        @Override
        public void run() {
            try {
                // start it all
                doTheMainTask();
            } catch (Exception e) {
                errorManager.reportUnexpectedPluginException(Plugins.FERMAT_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                e.printStackTrace();
            }
        }


        /**
         * Agent main method
         */
        private void doTheMainTask() throws Exception {
            System.out.println("***FermatCryptoNetwork***  starting and connecting on " + BLOCKCHAIN_NETWORKTYPE.getCode() + "...");

            try{
                /**
                /**
                 * creates the blockchain object for the specified network.
                 */
                FermatCryptoNetworkBlockChain cryptoNetworkBlockChain = new FermatCryptoNetworkBlockChain(pluginFileSystem, NETWORK_PARAMETERS, wallet, context);
                blockChain = cryptoNetworkBlockChain.getBlockChain();

                /**
                 * creates the peerGroup object
                 */
                peerGroup = new PeerGroup(this.context, blockChain);
                peerGroup.addWallet(this.wallet);

                /**
                 * add the events
                 */
                events = new FermatNetworkEvents(BLOCKCHAIN_NETWORKTYPE, this.walletFileName, this.context, wallet, dao, eventManager);
                peerGroup.addEventListener(events);

                this.wallet.addEventListener(events);
                blockChain.addListener(events);


                /**
                 * I will connect to the regTest server or search for peers if we are in a different network.
                 */
                //connect to local if possible
                peerGroup.setUseLocalhostPeerWhenPossible(true);


                /**
                 * I add the seed node of the fermat blockchain
                 */
                for (PeerAddress peerAddress : FermatSeedNodes.getNodes()){
                    peerGroup.addAddress(peerAddress);
                }

                /**
                 * Define internal agent information.
                 */
                peerGroup.setUserAgent(blockchainProvider.getAgentName(), blockchainProvider.getAgentVersion().toString());
                peerGroup.setMinBroadcastConnections(blockchainProvider.getMinimumBroadcastConnections());


                /**
                 * Update stats related active networks
                 */
                this.dao.updateActiveNetworks(BLOCKCHAIN_NETWORKTYPE, wallet.getImportedKeys().size());

                /**
                 * starts the monitoring
                 */
                peerGroup.setDownloadTxDependencies(true);
                peerGroup.start();
                peerGroup.startBlockChainDownload(cryptoNetworkBlockChain);

                System.out.println("***FermatCryptoNetwork*** Successful monitoring " + wallet.getImportedKeys().size() + " keys in " + BLOCKCHAIN_NETWORKTYPE.getCode() + " network.");
                System.out.println("***FermatCryptoNetwork*** PeerGroup running?: " + peerGroup.isRunning() + " with " + peerGroup.getConnectedPeers().size() + " connected peers.");

                /**
                 * I will broadcast any transaction that might be in broadcasting status.
                 * I might have this if the process was interrupted while broadcasting.
                 */
                resumeBroadcastOfPendingTransactions(BLOCKCHAIN_NETWORKTYPE);

            } catch (Exception e){
                e.printStackTrace();
                errorManager.reportUnexpectedPluginException(Plugins.FERMAT_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw e;
            }
        }

        /**
         * Will get all transactions hashes in Broadcasting status to resume them.
         */
        private void resumeBroadcastOfPendingTransactions(BlockchainNetworkType blockchainNetworkType) {
            // first will try to broadcast all transaction on the broadcast table
            try {
                for (String txId :  dao.getBroadcastTransactionsByStatus(blockchainNetworkType, Status.BROADCASTING)){
                    try {
                        this.broadcastTransaction(txId);
                    } catch (CantBroadcastTransactionException e) {
                        dao.setBroadcastStatus(Status.WITH_ERROR, peerGroup.getConnectedPeers().size(), e, txId);
                    }
                }
            } catch (CantExecuteDatabaseOperationException e) {
                /**
                 * If I couldn't get the list due to a database error, then nothing left to do
                 */
                e.printStackTrace();
            }

            //then I will continue with the ones at the wallet.
            for (Transaction transaction : wallet.getPendingTransactions()){
                try {
                    this.broadcastTransaction(transaction.getHashAsString());
                } catch (CantBroadcastTransactionException e) {
                    //if there was an error I just will print it.
                    e.printStackTrace();
                }
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

            //get the amount of peers we are connected to for stats.
            final int connectedPeers = peerGroup.getConnectedPeers().size();



            // if I don't have it, it wasn't yet commited, I will load it from file and commit it.
            if (transaction == null){
                try {
                    transaction = loadTransactionFromDisk(txHash);
                } catch (CantLoadTransactionFromFileException | FileNotFoundException | CantCreateFileException e) {
                    throw new CantBroadcastTransactionException(CantBroadcastTransactionException.DEFAULT_MESSAGE, e, "No transaction was found to broadcast.", null);
                }

                // if I didn't get it I can't go on.
                if (transaction == null)
                    throw new CantBroadcastTransactionException(CantBroadcastTransactionException.DEFAULT_MESSAGE, null, "No transaction was found to broadcast.", null);

                // commit and save
                try {
                    wallet.commitTx(transaction);
                    wallet.saveToFile(walletFileName);
                } catch (IOException e) {
                    throw new CantBroadcastTransactionException(CantBroadcastTransactionException.DEFAULT_MESSAGE, e, "There was an error saving the wallet to disk.", "IO issue");
                }
            } else{
                // if the transaction is not pending, there is something wrong.
                if (transaction.getConfidence().getConfidenceType() != TransactionConfidence.ConfidenceType.PENDING){
                    try {
                        dao.setBroadcastStatus(Status.BROADCASTED, connectedPeers, null, txHash);
                    } catch (CantExecuteDatabaseOperationException e) {
                        e.printStackTrace();
                    }
                    throw new CantBroadcastTransactionException(CantBroadcastTransactionException.DEFAULT_MESSAGE, null, "The transaction is no longer pending. No need to broadcast", "previous broadcast.");
                }
            }


            /**
             * will update this transaction status to broadcasting.
             */
            try {
                dao.setBroadcastStatus(Status.BROADCASTING, connectedPeers, null, txHash);
            } catch (CantExecuteDatabaseOperationException e) {
                e.printStackTrace();
            }

            System.out.println("***FermatCryptoNetwork***  Broadcasting transaction " + txHash + "...");

            final Transaction finalTransaction = transaction;



            ListenableFuture<Transaction> future = peerGroup.broadcastTransaction(transaction, blockchainProvider.getMinimumBroadcastConnections()).future();
            wallet.receivePending(finalTransaction, null);
            /**
             * I add the future that will get the broadcast result into a call back to respond to it.
             */
            Futures.addCallback(future, new FutureCallback<Transaction>() {
                @Override
                public void onSuccess(Transaction result) {

                    try {
                        dao.setBroadcastStatus(Status.BROADCASTED, connectedPeers, null, txHash);
                        /**
                         * Store this outgoing transaction in the table
                         */
                        UUID transactionId = dao.getBroadcastedTransactionId(BLOCKCHAIN_NETWORKTYPE, txHash);
                        storeOutgoingTransaction(wallet, finalTransaction, transactionId);


                        /**
                         * saves the wallet again.
                         */
                        wallet.saveToFile(walletFileName);

                        /**
                         * deletes the stored transaction on disk and DB to save space.
                         */
                        deleteStoredBroadcastTransaction(txHash);

                        System.out.println("***FermatCryptoNetwork***  Transaction successfully broadcasted: " + finalTransaction.getHashAsString());
                    } catch (CantExecuteDatabaseOperationException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    System.out.println("***FermatCryptoNetwork*** Error bradcasting transaction " + txHash + "...");

                    try {
                        dao.setBroadcastStatus(Status.WITH_ERROR, connectedPeers, (Exception) t, txHash);
                    } catch (CantExecuteDatabaseOperationException e) {
                        e.printStackTrace();
                        errorManager.reportUnexpectedPluginException(Plugins.FERMAT_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                    }
                }
            });
        }

        /**
         * Loads the passed transaction from disk
         * @param txHash
         * @return
         */
        public Transaction loadTransactionFromDisk(String txHash) throws FileNotFoundException, CantCreateFileException, CantLoadTransactionFromFileException {
            if (StringUtils.isBlank(txHash))
                throw new CantLoadTransactionFromFileException(CantLoadTransactionFromFileException.DEFAULT_MESSAGE, null, "txHash is not correct: " + txHash, "invalid parameter");

            PluginTextFile pluginTextFile = pluginFileSystem.getTextFile(this.pluginId, TRANSACTION_DIRECTORY, txHash, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            String transactionContent = pluginTextFile.getContent();

            Transaction transaction = (Transaction) XMLParser.parseXML(transactionContent, new Transaction(NETWORK_PARAMETERS));
            return transaction;

        }


        /**
         * Will search this transaction in the database and make sure it exists.
         * @param txHash
         * @throws CantBroadcastTransactionException
         */
        private void validateTransactionExistsinDatabase(String txHash) throws CantBroadcastTransactionException{
            try {
                if (!dao.transactionExistsInBroadcast(txHash)){
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
            for (CryptoTransaction cryptoTransaction : FermatTransactionConverter.getCryptoTransactions(BLOCKCHAIN_NETWORKTYPE, CURRENCY, wallet, tx)){
                try {
                    dao.saveCryptoTransaction(cryptoTransaction, transactionId);
                } catch (CantExecuteDatabaseOperationException e) {
                    //maybe try saving into disk if cant save it.
                    e.printStackTrace();
                }
            }

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
                 * Verify transaction is not already stored. Delete if it exists.
                 */
                String txHash = tx.getHashAsString();
                if (isTransactionAlreadyStored(txHash)){
                    deleteStoredBroadcastTransaction(txHash);
                }

                if (isTransactionAlreadyStored(txHash)){
                    throw new CantStoreBitcoinTransactionException(CantStoreBitcoinTransactionException.DEFAULT_MESSAGE, null, "transaction is already stored and could not be deleted. " + txHash, "storeBitcoinTransaction on CryptoNetwork.");
                }

                /**
                 * store the transaction as Pending submit in the transactions table
                 */
                CryptoTransaction cryptoTransaction = FermatTransactionConverter.getCryptoTransaction(BLOCKCHAIN_NETWORKTYPE, tx, CURRENCY );
                cryptoTransaction.setCryptoTransactionType(CryptoTransactionType.OUTGOING);
                dao.saveCryptoTransaction(cryptoTransaction, transactionId);

                /**
                 * I store it in the database in the broadcasting table
                 */
                Peer downloadPeer = peerGroup.getDownloadPeer();

                String peerAddress = "";
                if (downloadPeer != null)
                    peerAddress = downloadPeer.getAddress().toString();

                dao.storeBroadcastBitcoinTransaction(BLOCKCHAIN_NETWORKTYPE, tx.getHashAsString(), transactionId, peerGroup.getConnectedPeers().size(), peerAddress);

                //mark this transaction as our own.
                tx.getConfidence().setSource(TransactionConfidence.Source.SELF);

                if (commit){
                    // commit and save the transaction
                    wallet.commitTx(tx);
                    wallet.saveToFile(walletFileName);

                    // verify it was successfuly stored.
                    Transaction storedTransaction = wallet.getTransaction(tx.getHash());
                    if (storedTransaction == null){
                        CantStoreBitcoinTransactionException e = new  CantStoreBitcoinTransactionException(CantStoreBitcoinTransactionException.DEFAULT_MESSAGE, null, "transaction was not correctly stored at the wallet.", null);
                        errorManager.reportUnexpectedPluginException(Plugins.FERMAT_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                        throw e;

                    }
                } else {
                    // if no commit, then I will save it into a file.
                    persistTransactionOnFile(tx);
                }


                System.out.println("***FermatCryptoNetwork*** Transaction successfully stored for broadcasting: " + tx.getHashAsString());
            } catch (CantExecuteDatabaseOperationException e) {
                errorManager.reportUnexpectedPluginException(Plugins.FERMAT_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantStoreBitcoinTransactionException(CantStoreBitcoinTransactionException.DEFAULT_MESSAGE, e, "There was an error storing the transaction in the database", null);
            } catch (Exception e) {
                /**
                 * If there was an error, then I will make sure that the transaction is not left stored at the database.
                 */
                try {
                    dao.deleteStoredBroadcastBitcoinTransaction(tx.getHashAsString());
                    deleteTransactionFromFile(tx.getHashAsString());
                } catch (CantExecuteDatabaseOperationException e1) {
                    /**
                     * I will ignore this error
                     */
                    e1.printStackTrace();
                }

                CantStoreBitcoinTransactionException exception = new CantStoreBitcoinTransactionException(CantStoreBitcoinTransactionException.DEFAULT_MESSAGE, e, "Error storing the transaction in the wallet. TxId: " + tx.getHashAsString(), "Crypto Network");
                errorManager.reportUnexpectedPluginException(Plugins.FERMAT_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
                throw exception;
            }
        }

        private void deleteStoredBroadcastTransaction(String txHash) {
            if (isTransactionStoredInDB(txHash)){
                try {
                    this.dao.deleteStoredBroadcastBitcoinTransaction(txHash);
                } catch (CantExecuteDatabaseOperationException e) {
                    e.printStackTrace();
                }
            }

            if (isTransactionStoredInWallet(txHash)){
                //not much I can do now
            }

            if (isTransactionStoredOnDisk(txHash)){
                deleteTransactionFromFile(txHash);
            }

        }

        /**
         * Verifies if the transaction is already stored on the database, wallet and disk
         * @param txHash
         * @return
         */
        private boolean isTransactionAlreadyStored(String txHash) {
            if (isTransactionStoredInDB(txHash))
                return true;

            if (isTransactionStoredInWallet(txHash))
                return true;

            if (isTransactionStoredOnDisk(txHash))
                return true;

            return false;
        }

        private boolean isTransactionStoredOnDisk(String txHash) {
            try {
                PluginTextFile pluginTextFile = pluginFileSystem.getTextFile(this.pluginId, TRANSACTION_DIRECTORY, txHash, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                if (pluginTextFile == null)
                    return false;
                else
                    return true;
            } catch (Exception e) {
                return false;
            }

        }

        private boolean isTransactionStoredInWallet(String txHash) {
            Sha256Hash sha256Hash = Sha256Hash.wrap(txHash);
            Transaction transaction = this.wallet.getTransaction(sha256Hash);
            if (transaction == null)
                return false;
            else
                return true;
        }

        private boolean isTransactionStoredInDB(String txHash) {
            try {
                UUID uuid = dao.getBroadcastedTransactionId(this.BLOCKCHAIN_NETWORKTYPE, txHash);
                if (uuid == null)
                    return false;
                else
                    return true;
            } catch (CantExecuteDatabaseOperationException e) {
                return false;
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
         * invalidates the passed transaction by clearing inputs and outputs.
         * @param txHash
         */
        public void cancelBroadcast(String txHash) throws CantCancellBroadcastTransactionException {
            try {
                // delete the transaction
                deleteTransactionFromFile(txHash);

                /**
                 * update Broadcasting table to set it to cancelled.
                 */
                this.dao.setBroadcastStatus(Status.CANCELLED, peerGroup.getConnectedPeers().size(), null, txHash);

                System.out.println("***FermatCryptoNetwork*** Transaction " + txHash + " cancelled.");
            } catch (Exception e) {
                CantCancellBroadcastTransactionException exception = new CantCancellBroadcastTransactionException(CantCancellBroadcastTransactionException.DEFAULT_MESSAGE, e, "Transaction couldn't rollback properly.", "Crypto Network error");
                errorManager.reportUnexpectedPluginException(Plugins.FERMAT_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
                throw exception;
            }
        }

        /**
         * Gets the current blockchainConnectionStatus
         * @return
         */
        public BlockchainConnectionStatus getBlockchainConnectionStatus() throws CantGetBlockchainConnectionStatusException {
            try{
                List<ConnectedBitcoinNode> connectedBitcoinNodeList = new ArrayList<>();

                for (Peer peer : peerGroup.getConnectedPeers()){

                    Boolean isDownload = false;
                    if (peer.equals(peerGroup.getDownloadPeer()))
                        isDownload = true;

                    // If create the ConnectedBitcoinNode object
                    ConnectedBitcoinNode connectedBitcoinNode = new ConnectedBitcoinNode(peer.getAddress().toString(),
                            peer.getPeerVersionMessage().subVer,
                            isDownload,
                            peer.getPingTime());

                    //add it to the list
                    connectedBitcoinNodeList.add(connectedBitcoinNode);
                }

                BlockchainConnectionStatus blockchainConnectionStatus = new BlockchainConnectionStatus(connectedBitcoinNodeList, this.BLOCKCHAIN_NETWORKTYPE);
                return blockchainConnectionStatus;
            } catch (Exception e){
                CantGetBlockchainConnectionStatusException exception = new CantGetBlockchainConnectionStatusException(CantGetBlockchainConnectionStatusException.DEFAULT_MESSAGE, e, "Error getting connection status from peers.", null);
                errorManager.reportUnexpectedPluginException(Plugins.FERMAT_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
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
                errorManager.reportUnexpectedPluginException(Plugins.FERMAT_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
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
                errorManager.reportUnexpectedPluginException(Plugins.FERMAT_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
                throw exception;
            }

            return null;
        }

        /**
         * gets the wallet from the monitor
         * @return
         */
        public Wallet getWallet() {
            return wallet;
        }

        /**
         * gets the Blockchain download progress class
         * @return
         */
        public BlockchainDownloadProgress getBlockchainDownloadProgress() {
            return events.getBlockchainDownloadProgress();
        }

        public Transaction getBitcoinTransaction(Sha256Hash sha256Hash){
            return this.wallet.getTransaction(sha256Hash);
        }
    }

    /**
     * Public methods to access private MonitorAgent class
     */

    public synchronized void broadcastTransaction(final String txHash) throws CantBroadcastTransactionException{
        this.monitorAgent.broadcastTransaction(txHash);
    }

    public Transaction getTransactionFromBlockChain(String transactionHash, String transactionBlockHash) throws CantGetTransactionException{
        return this.monitorAgent.getTransactionFromBlockChain(transactionHash, transactionBlockHash);
    }

    public synchronized void storeBitcoinTransaction (Transaction tx, UUID transactionId, boolean commit) throws CantStoreBitcoinTransactionException{
        this.monitorAgent.storeBitcoinTransaction(tx, transactionId, commit);
    }


    public void cancelBroadcast(String txHash) throws CantCancellBroadcastTransactionException{
        this.monitorAgent.cancelBroadcast(txHash);
    }

    public BlockchainConnectionStatus getBlockchainConnectionStatus() throws CantGetBlockchainConnectionStatusException{
        return this.monitorAgent.getBlockchainConnectionStatus();
    }

    public Transaction loadTransactionFromDisk(String txHash) throws CantLoadTransactionFromFileException, FileNotFoundException, CantCreateFileException {
        return this.monitorAgent.loadTransactionFromDisk(txHash);
    }

    public Wallet getWallet(){
        return this.monitorAgent.getWallet();
    }

    public BlockchainDownloadProgress getBlockchainDownloadProgress(){
        return this.monitorAgent.getBlockchainDownloadProgress();
    }

    public Transaction getBitcoinTransaction(Sha256Hash sha256Hash){
        return this.monitorAgent.wallet.getTransaction(sha256Hash);
    }
}
