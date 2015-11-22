package com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.ProtocolStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.enums.TransactionTypes;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.database.BitcoinCryptoNetworkDatabaseDao;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.Block;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.FilteredBlock;
import org.bitcoinj.core.GetDataMessage;
import org.bitcoinj.core.Message;
import org.bitcoinj.core.Peer;
import org.bitcoinj.core.PeerAddress;
import org.bitcoinj.core.PeerEventListener;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionInput;
import org.bitcoinj.core.TransactionOutput;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.core.WalletEventListener;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptChunk;
import org.bitcoinj.script.ScriptOpCodes;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Nullable;

/**
 * Created by rodrigo on 10/4/15.
 */
public class BitcoinNetworkEvents implements WalletEventListener, PeerEventListener {
    /**
     * Class variables
     */
    BitcoinCryptoNetworkDatabaseDao dao;
    File walletFilename;

    /**
     * Platform variables
     */
    PluginDatabaseSystem pluginDatabaseSystem;
    UUID pluginId;

    /**
     * Constructor
     * @param pluginDatabaseSystem
     */
    public BitcoinNetworkEvents(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId, File walletFilename) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.walletFilename = walletFilename;
    }

    @Override
    public void onPeersDiscovered(Set<PeerAddress> peerAddresses) {

    }

    @Override
    public void onBlocksDownloaded(Peer peer, Block block, FilteredBlock filteredBlock, int blocksLeft) {
        //System.out.println("*****CryptoNetwork Blockdownloaded. Pending blocks: " + blocksLeft);
        //System.out.println("*****CryptoNetwork " + filteredBlock.toString());
    }

    @Override
    public void onChainDownloadStarted(Peer peer, int blocksLeft) {

    }

    @Override
    public void onPeerConnected(Peer peer, int peerCount) {
        StringBuilder logAggresive = new StringBuilder("New connection on CryptoNetwork2 to Peer " + peer.toString());
        logAggresive.append(System.getProperty("line.separator"));
        logAggresive.append("Total connected peers: " + peerCount);
        System.out.println(logAggresive.toString());
    }

    @Override
    public void onPeerDisconnected(Peer peer, int peerCount) {

    }

    @Override
    public Message onPreMessageReceived(Peer peer, Message m) {
        return null;
    }

    @Override
    public void onTransaction(Peer peer, Transaction t) {
        //System.out.println("Transaction on Crypto Network 2:" + t.toString());
    }

    @Nullable
    @Override
    public List<Message> getData(Peer peer, GetDataMessage m) {
        return null;
    }

    @Override
    public void onCoinsReceived(Wallet wallet, Transaction tx, Coin prevBalance, Coin newBalance) {
        /**
         * Register the new incoming transaction into the database
         */
        saveIncomingTransaction(wallet, tx);
    }

    /**
     * Registers the outgoing transaction
     * @param wallet
     * @param tx
     * @param prevBalance
     * @param newBalance
     */
    @Override
    public void onCoinsSent(Wallet wallet, Transaction tx, Coin prevBalance, Coin newBalance) {
        /**
         * register the new outgoing transaction into the database
         */
        saveOutgoingTransaction(wallet, tx);
    }

    private void saveOutgoingTransaction(Wallet wallet, Transaction tx) {
        /**
         * Register the new outgoing transaction into the database
         */
        try {
            getDao().saveNewOutgoingTransaction(tx.getHashAsString(),
                    getTransactionCryptoStatus(tx),
                    tx.getConfidence().getDepthInBlocks(),
                    getOutgoingTransactionAddressTo(tx),
                    getOutgoingTransactionAddressFrom(wallet, tx),
                    getOutgoingTransactionValue(wallet,tx),
                    getTransactionOpReturn(tx),
                    ProtocolStatus.NO_ACTION_REQUIRED);
        } catch (Exception e) {
            /**
             * if there is an error in getting information from the transaction object.
             * I will try saving the transaction with minimal information.
             * I will complete this info in the agent that triggers the events.
             */
            e.printStackTrace();
            try{
                CryptoAddress errorAddress = new CryptoAddress("error", CryptoCurrency.BITCOIN);
                getDao().saveNewOutgoingTransaction(tx.getHashAsString(),
                        getTransactionCryptoStatus(tx),
                        0,
                        errorAddress,
                        errorAddress,
                        0,
                        "",
                        ProtocolStatus.NO_ACTION_REQUIRED);
            } catch (CantExecuteDatabaseOperationException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Extracts from the outputs, the ones used to generate the op_Return and the value with in it.
     * if it doesn't has any, returns and empty string.
     * @param tx
     * @return
     */
    private String getTransactionOpReturn(Transaction tx) {
        String hash = "";
        try{
            for (TransactionOutput output : tx.getOutputs()){
                /**
                 * if this is an OP_RETURN output, I will get the hash
                 */
                if (output.getScriptPubKey().isOpReturn()){
                    /**
                     * I get the chunks of the Script to get the op_Return value
                     */
                    for (ScriptChunk chunk : output.getScriptPubKey().getChunks()){
                        if (chunk.equalsOpCode(64))
                            hash = new String(chunk.data);
                    }
                }
            }
        } catch (Exception e){
            return "";
        }
        return hash;
    }

    @Override
    public void onReorganize(Wallet wallet) {

    }

    @Override
    public void onTransactionConfidenceChanged(Wallet wallet, Transaction tx) {
        /**
         * Depending this is a outgoing or incoming transaction, I will set the CryptoStatus
         */
        CryptoStatus cryptoStatus = getTransactionCryptoStatus(tx);
        try {
            if (isIncomingTransaction(tx.getHashAsString()))
                addMissingTransactions(wallet, tx, cryptoStatus, TransactionTypes.INCOMING);
            else
                addMissingTransactions(wallet, tx, cryptoStatus, TransactionTypes.OUTGOING);
        } catch (CantExecuteDatabaseOperationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onWalletChanged(Wallet wallet) {
        /**
         * I will save the wallet after a change.
         */
        try {
            wallet.saveToFile(walletFilename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onScriptsChanged(Wallet wallet, List<Script> scripts, boolean isAddingScripts) {

    }

    @Override
    public void onKeysAdded(List<ECKey> keys) {
        // I may need to reset the wallet in this case?
    }

    /**
     * instantiates the database object
     * @return
     */
    private BitcoinCryptoNetworkDatabaseDao getDao(){
        if (dao == null)
            dao = new BitcoinCryptoNetworkDatabaseDao(this.pluginId, this.pluginDatabaseSystem);
        return dao;
    }

    /**
     * Gets the Crypto Status of the transaction by calculating the transaction depth
     * I need to check if the transaction had another Crypto Status to verify if this is a reversion.
     * Example, if it was under 1 block (ON_BLOCKCHAIN) and now is 0 (ON_CRYPTO_NETWORK), is a Reversion on Blockchain.
     * @param tx
     * @return
     */
    private CryptoStatus getTransactionCryptoStatus(Transaction tx){
        try{
            int depth = tx.getConfidence().getDepthInBlocks();

            if (depth == 0)
                return CryptoStatus.ON_CRYPTO_NETWORK;
            else if(depth == 1)
                return CryptoStatus.ON_BLOCKCHAIN;
            else if (depth >= 2)
                return CryptoStatus.IRREVERSIBLE;
            else
                return CryptoStatus.PENDING_SUBMIT;
        } catch (Exception e){
            return CryptoStatus.ON_CRYPTO_NETWORK;
        }
    }

    /**
     * Extracts the Address To from an Incoming Transaction
     * @param tx
     * @return
     */
    private CryptoAddress getIncomingTransactionAddressTo (Wallet wallet, Transaction tx){
        Address address = null;

        /**
         * I will loop from the outputs that include keys that are in my wallet
         */
        for (TransactionOutput output : tx.getWalletOutputs(wallet)){
            /**
             * get the address from the output
             */
            address = output.getScriptPubKey().getToAddress(wallet.getNetworkParameters());
        }
        CryptoAddress cryptoAddress = new CryptoAddress(address.toString(), CryptoCurrency.BITCOIN);
        return cryptoAddress;
    }

    /**
     * Extracts the AddressFrom from an Incoming Transaction
     * @param tx
     * @return
     */
    private CryptoAddress getIncomingTransactionAddressFrom (Transaction tx){
        CryptoAddress cryptoAddress= null;
        try{
            Address address = null;

            for (TransactionInput input : tx.getInputs()){
                if (input.getFromAddress() != null)
                    address = input.getFromAddress();
            }

            cryptoAddress = new CryptoAddress(address.toString(), CryptoCurrency.BITCOIN);
        } catch (Exception e){
            /**
             * if there is an error, because this may not always be possible to get.
             */
            cryptoAddress = new CryptoAddress("Empty", CryptoCurrency.BITCOIN);
        }
        return cryptoAddress;
    }

    /**
     * Extracts the Address From from an Outgoing Transaction
     * @param tx
     * @return
     */
    private CryptoAddress getOutgoingTransactionAddressFrom (Wallet wallet, Transaction tx){
        return getIncomingTransactionAddressTo(wallet, tx);
    }

    /**
     * Extracts the Address To from an outgoing Transaction
     * @param tx
     * @return
     */
    private CryptoAddress getOutgoingTransactionAddressTo (Transaction tx){
        return getIncomingTransactionAddressFrom(tx);
    }

    /**
     * determines if the passed transaction is incoming or outgoing transaction
     * @param txHash
     * @return true if is an IncomingTransactin, false if is outgoing.
     */
    private boolean isIncomingTransaction(String txHash) throws CantExecuteDatabaseOperationException {
        return getDao().isIncomingTransaction(txHash);
    }

    /**
     * Creates a new platform transaction with the new crypto Status
     * @param transactionType
     * @param wallet
     * @param tx
     */
    private void setTransactionCryptoStatus(TransactionTypes transactionType , Wallet wallet, Transaction tx) {

        /**
         * I will get the previous CryptoStatus of the transaction to see if it is a Reversion
         */
        CryptoStatus storedCryptoStatus = null;
        try {
            storedCryptoStatus = getStoredTransactionCryptoStatus(transactionType, tx.getHashAsString());
        } catch (CantExecuteDatabaseOperationException e) {
            e.printStackTrace();
        }
        /**
         * Also get the current CryptoStatus that triggered the event.
         */
        CryptoStatus currentCryptoStatus = getTransactionCryptoStatus(tx);


        /**
         * if the stored CryptoStatus is the same as the current one (for example IRREVERSIBLE)
         * then there is nothing left to do
         */
        if (storedCryptoStatus == currentCryptoStatus)
            return;

        CryptoStatus cryptoStatusToSet = null;
        switch (storedCryptoStatus) {
            case ON_CRYPTO_NETWORK:

            case ON_BLOCKCHAIN:
                /**
                 * If it was as ON_BLOCKCHAIN and now is ON_CRYPTO_NETWORK, then Is a reversion to ON CRYPTO_NETWORK
                 */
                if (currentCryptoStatus == CryptoStatus.ON_CRYPTO_NETWORK)
                    cryptoStatusToSet = CryptoStatus.REVERSED_ON_CRYPTO_NETWORK;
                else
                    cryptoStatusToSet = currentCryptoStatus;
                break;
            case IRREVERSIBLE:
                /**
                 * If the transaction was in irreversible and now is something different, is a reversion.
                 */
                if (currentCryptoStatus == CryptoStatus.ON_CRYPTO_NETWORK)
                    cryptoStatusToSet = CryptoStatus.REVERSED_ON_CRYPTO_NETWORK;
                else if (currentCryptoStatus == CryptoStatus.ON_BLOCKCHAIN)
                    cryptoStatusToSet = CryptoStatus.REVERSED_ON_BLOCKCHAIN;
                else
                    cryptoStatusToSet = currentCryptoStatus;
                break;
            default:
                cryptoStatusToSet = currentCryptoStatus;
        }

        /**
         * Now I will set the new transaction on the incoming or outgoing tables
         */
        switch (transactionType) {
            case INCOMING:
                /**
                 * Register the new incoming transaction into the database
                 */
                saveIncomingTransaction(wallet, tx);
                break;
            case OUTGOING:
                /**
                 * Register the new incoming transaction into the database
                 */
               saveOutgoingTransaction(wallet,tx);
                break;
        }
    }

    /**
     * gets the stored crypto status from the specified transaction.
     * @param transactionType
     * @param txHash
     * @return
     * @throws CantExecuteDatabaseOperationException
     */
    private CryptoStatus getStoredTransactionCryptoStatus(TransactionTypes transactionType, String txHash) throws CantExecuteDatabaseOperationException {
        return getDao().getStoredTransactionCryptoStatus(transactionType, txHash);
    }

    /**
     * saves the new incoming transaction into the database
     * @param wallet
     * @param tx
     */
    private void saveIncomingTransaction(Wallet wallet, Transaction tx) {
        CryptoStatus cryptoStatus = getTransactionCryptoStatus(tx);

        /**
         * I will insert any missing previous state for this transaction
         */
        addMissingTransactions(wallet, tx, cryptoStatus, TransactionTypes.INCOMING);

        /**
         * saves into database the incoming transaction if it is new.
         */
        if (isNewTransaction(tx.getHashAsString(), cryptoStatus)) {
            try {
                getDao().saveNewIncomingTransaction(tx.getHashAsString(),
                        cryptoStatus,
                        tx.getConfidence().getDepthInBlocks(),
                        getIncomingTransactionAddressTo(wallet, tx),
                        getIncomingTransactionAddressFrom(tx),
                        getIncomingTransactionValue(wallet, tx),
                        getTransactionOpReturn(tx),
                        ProtocolStatus.TO_BE_NOTIFIED);
            } catch (Exception e) {
                /**
                 * if there is an error in getting information from the transaction object.
                 * I will try saving the transaction with minimal information.
                 * I will complete this info in the agent that triggers the events.
                 */
                e.printStackTrace();
                try {
                    CryptoAddress errorAddress = new CryptoAddress("error", CryptoCurrency.BITCOIN);
                    getDao().saveNewIncomingTransaction(tx.getHashAsString(),
                            getTransactionCryptoStatus(tx),
                            0,
                            errorAddress,
                            errorAddress,
                            0,
                            "",
                            ProtocolStatus.TO_BE_NOTIFIED);
                } catch (CantExecuteDatabaseOperationException e1) {
                    e1.printStackTrace();
                }
            }

        }
    }

    /**
     * Verifies if this transaction already exists in the database or not.
     * @param txHash
     * @param cryptoStatus
     * @return
     */
    private boolean isNewTransaction(String txHash, CryptoStatus cryptoStatus){
        try {
            return getDao().isNewTransaction(txHash, cryptoStatus);
        } catch (CantExecuteDatabaseOperationException e) {
            /**
             * if there is an error I can't risk loosing this transaction.
             */
            return true;
        }
    }

    /**
     * saves into the database any missed transaction with the corresponding CryptoStatus.
     * @param wallet
     * @param tx
     * @param missedCryptoStatus
     */
    private void saveMissingIncomingTransaction(Wallet wallet, Transaction tx, CryptoStatus missedCryptoStatus) throws CantExecuteDatabaseOperationException {
        getDao().saveNewIncomingTransaction(tx.getHashAsString(),
                missedCryptoStatus,
                tx.getConfidence().getDepthInBlocks(),
                getIncomingTransactionAddressTo(wallet, tx),
                getIncomingTransactionAddressFrom(tx),
                getIncomingTransactionValue(wallet, tx),
                getTransactionOpReturn(tx),
                ProtocolStatus.TO_BE_NOTIFIED);
    }

    /**
     * saves into the database any missed transaction with the corresponding CryptoStatus.
     * @param wallet
     * @param tx
     * @param missedCryptoStatus
     */
    private void saveMissingOutgoingTransaction(Wallet wallet, Transaction tx, CryptoStatus missedCryptoStatus) throws CantExecuteDatabaseOperationException {
        getDao().saveNewOutgoingTransaction(tx.getHashAsString(),
                missedCryptoStatus,
                tx.getConfidence().getDepthInBlocks(),
                getIncomingTransactionAddressTo(wallet, tx),
                getIncomingTransactionAddressFrom(tx),
                getIncomingTransactionValue(wallet, tx),
                getTransactionOpReturn(tx),
                ProtocolStatus.TO_BE_NOTIFIED);
    }




    /**
     * gets the value sent to me in a transaction
     * @param wallet
     * @param tx
     * @return
     */
    private long getIncomingTransactionValue(Wallet wallet, Transaction tx) {
        try{
            return tx.getValueSentToMe(wallet).getValue();
        } catch (Exception e){
            return 0;
        }
    }

    /**
     * gets the value sent from me in a transaction
     * @param wallet
     * @param tx
     * @return
     */
    private long getOutgoingTransactionValue(Wallet wallet, Transaction tx) {
        try{
            return tx.getValueSentFromMe(wallet).getValue();
        } catch (Exception e){
            return 0;
        }
    }

    /**
     * Will add any missing transaction in case we detect the transaction only as inBlockChain or Irreversible
     * @param wallet
     * @param tx
     * @param currentCryptoStatus
     */
    private void addMissingTransactions(Wallet wallet, Transaction tx, CryptoStatus currentCryptoStatus, TransactionTypes transactionType){
        /**
         * I get the last store CryptoStatus from the database, if any.
         */
        CryptoStatus storedCryptoStatus = null;
        try {
            storedCryptoStatus = getDao().getStoredTransactionCryptoStatus(null, tx.getHashAsString());
        } catch (CantExecuteDatabaseOperationException e) {
            storedCryptoStatus = null;
        }

        /**
         * If I don't have any transaction and we receive ON_BLOCKCHAIN, I will insert a new one with ON_CRYPTO_NETWORK
         */
        if (currentCryptoStatus == CryptoStatus.ON_BLOCKCHAIN){
            if (storedCryptoStatus == null){
                try{
                    /**
                     * depending on the transaction type, I will add it as incoming or outgoing
                     */
                    if (transactionType == TransactionTypes.INCOMING)
                        saveMissingIncomingTransaction(wallet, tx, CryptoStatus.ON_CRYPTO_NETWORK);
                    else
                        saveMissingOutgoingTransaction(wallet, tx, CryptoStatus.ON_CRYPTO_NETWORK);
                } catch (CantExecuteDatabaseOperationException e){
                    e.printStackTrace(); //I will continue to at least save the incoming transaction.
                }
            }
        }

        /**
         * if the current status is IRREVERSIBLE, I will insert both ON_CRYPTO_NETWORK and ON_BLOCKCHAIN if they are missing.
         */
        if (currentCryptoStatus == CryptoStatus.IRREVERSIBLE){
            if (storedCryptoStatus == null){
                try{
                    if (transactionType == TransactionTypes.INCOMING){
                        saveMissingIncomingTransaction(wallet, tx, CryptoStatus.ON_CRYPTO_NETWORK);
                        saveMissingIncomingTransaction(wallet, tx, CryptoStatus.ON_BLOCKCHAIN);
                    } else {
                        saveMissingOutgoingTransaction(wallet, tx, CryptoStatus.ON_CRYPTO_NETWORK);
                        saveMissingOutgoingTransaction(wallet, tx, CryptoStatus.ON_BLOCKCHAIN);
                    }

                } catch (CantExecuteDatabaseOperationException e){
                    e.printStackTrace(); //I will continue to at least save the incoming transaction.
                }
            }
            if (storedCryptoStatus == CryptoStatus.ON_CRYPTO_NETWORK){
                try{
                    if (transactionType == TransactionTypes.INCOMING){
                        saveMissingIncomingTransaction(wallet, tx, CryptoStatus.ON_BLOCKCHAIN);
                        saveMissingIncomingTransaction(wallet, tx, CryptoStatus.IRREVERSIBLE);
                    } else {
                        saveMissingOutgoingTransaction(wallet, tx, CryptoStatus.ON_BLOCKCHAIN);
                        saveMissingOutgoingTransaction(wallet, tx, CryptoStatus.IRREVERSIBLE);
                    }

                } catch (CantExecuteDatabaseOperationException e){
                    e.printStackTrace(); //I will continue to at least save the incoming transaction.
                }
            }
        }


    }
}

