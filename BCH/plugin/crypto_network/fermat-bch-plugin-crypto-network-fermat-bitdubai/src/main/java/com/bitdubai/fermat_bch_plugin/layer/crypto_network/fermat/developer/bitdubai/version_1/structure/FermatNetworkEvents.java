package com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;

import com.bitdubai.fermat_bch_api.layer.crypto_network.util.BlockchainDownloadProgress;
import com.bitdubai.fermat_bch_api.layer.crypto_network.events.BlockchainDownloadUpToDateEvent;
import com.bitdubai.fermat_bch_api.layer.crypto_network.fermat.FermatNetworkConfiguration;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.version_1.database.FermatCryptoNetworkDatabaseDao;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.version_1.util.FermatTransactionConverter;

import org.fermatj.core.AbstractBlockChain;
import org.fermatj.core.Block;
import org.fermatj.core.BlockChainListener;
import org.fermatj.core.Coin;
import org.fermatj.core.Context;
import org.fermatj.core.ECKey;
import org.fermatj.core.FilteredBlock;
import org.fermatj.core.GetDataMessage;
import org.fermatj.core.Message;
import org.fermatj.core.NetworkParameters;
import org.fermatj.core.Peer;
import org.fermatj.core.PeerAddress;
import org.fermatj.core.PeerEventListener;
import org.fermatj.core.ScriptException;
import org.fermatj.core.Sha256Hash;
import org.fermatj.core.StoredBlock;
import org.fermatj.core.Transaction;
import org.fermatj.core.VerificationException;
import org.fermatj.core.Wallet;
import org.fermatj.core.WalletEventListener;
import org.fermatj.script.Script;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

/**
 * Created by rodrigo on 6/22/16.
 */
public class FermatNetworkEvents implements WalletEventListener, PeerEventListener, BlockChainListener {



    /**
     * Class variables
     */
    private final FermatCryptoNetworkDatabaseDao dao;
    File walletFilename;
    final BlockchainNetworkType NETWORK_TYPE;
    final Context context;
    final NetworkParameters NETWORK_PARAMETERS;
    BlockchainDownloadProgress blockchainDownloadProgress;
    Wallet cryptoNetworkWallet;
    private final CryptoCurrency CURRENCY = FermatNetworkConfiguration.CRYPTO_CURRENCY;


    /**
     * platform variables
     */
    final EventManager eventManager;

    /**
     * Constructor
     */
    public FermatNetworkEvents(BlockchainNetworkType blockchainNetworkType,
                                File walletFilename,
                                Context context,
                                Wallet wallet,
                                FermatCryptoNetworkDatabaseDao FermatCryptoNetworkDatabaseDao,
                                EventManager eventManager) {
        this.NETWORK_TYPE = blockchainNetworkType;
        this.walletFilename = walletFilename;
        this.context = context;
        this.NETWORK_PARAMETERS = context.getParams();
        this.cryptoNetworkWallet = wallet;
        this.dao = FermatCryptoNetworkDatabaseDao;
        this.eventManager = eventManager;

        //define the blockchain download progress class with zero values
        blockchainDownloadProgress = new BlockchainDownloadProgress(NETWORK_TYPE, 0, 0, 0, 0);

    }

    @Override
    public void onPeersDiscovered(Set<PeerAddress> peerAddresses) {

    }

    @Override
    public void onBlocksDownloaded(Peer peer, Block block, FilteredBlock filteredBlock, int blocksLeft) {
        if (blocksLeft % 1000 == 0)
            System.out.println("***FermatCryptoNetwork*** Block downloaded on " + NETWORK_TYPE.getCode() + ". Pending blocks: " + blocksLeft);


        /**
         * sets the blockchainDownloader data
         */
        blockchainDownloadProgress.setPendingBlocks(blocksLeft);
        blockchainDownloadProgress.setLastBlockDownloadTime(block.getTimeSeconds());

        if (blockchainDownloadProgress.getTotalBlocks() == 0)
            blockchainDownloadProgress.setTotalBlocks(blocksLeft);

        blockchainDownloadProgress.setDownloader(peer.toString());

        /**
         * Now I will raise the BlockchainDownloadUpToDateEvent to notify that we are updated
         */
        if (blocksLeft == 0){
            BlockchainDownloadUpToDateEvent blockchainDownloadUpToDateEvent = new BlockchainDownloadUpToDateEvent(blockchainDownloadProgress, NETWORK_TYPE);
            eventManager.raiseEvent(blockchainDownloadUpToDateEvent);
            System.out.println("***FermatCryptoNetwork*** BlockchainDownloadUpToDateEvent raised. " + blockchainDownloadProgress.toString());
        }

    }



    @Override
    public void onChainDownloadStarted(Peer peer, int blocksLeft) {
        System.out.println("***FermatCryptoNetwork*** Blockchain Download started for " + NETWORK_TYPE.getCode() + " network. Blocks left: " + blocksLeft);
        blockchainDownloadProgress.setTotalBlocks(blocksLeft);
    }

    @Override
    public void onPeerConnected(Peer peer, int peerCount) {
        StringBuilder logAggresive = new StringBuilder("***FermatCryptoNetwork*** New Peer connection on " + NETWORK_TYPE.getCode() + "  to " + peer.toString());
        logAggresive.append(System.getProperty("line.separator"));
        logAggresive.append("Total connected peers for Network " + NETWORK_TYPE.getCode() + ": " + peerCount);
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
        //System.out.println("Transaction on Crypto Network:" + t.toString());
    }

    @Nullable
    @Override
    public List<Message> getData(Peer peer, GetDataMessage m) {
        return null;
    }

    @Override
    public void onCoinsReceived(Wallet wallet, Transaction tx, Coin prevBalance, Coin newBalance) {
        /**
         * I will save the wallet after a change.
         */
        try {
            cryptoNetworkWallet.saveToFile(walletFilename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * I'm converting the Bitcoin transaction into all the CryptoTransactions that might contain
         */
        for (CryptoTransaction cryptoTransaction : FermatTransactionConverter.getCryptoTransactions(NETWORK_TYPE, CURRENCY, cryptoNetworkWallet, tx)){
            saveCryptoTransaction(cryptoTransaction);
        }
    }

    /**
     * saves into the database the passed crypto transaction
     * @param cryptoTransaction
     */
    public void saveCryptoTransaction(CryptoTransaction cryptoTransaction) {
        try {
            dao.saveCryptoTransaction(cryptoTransaction, null);
        } catch (CantExecuteDatabaseOperationException e) {
            //todo maybe try saving to disk if database fails.
            e.printStackTrace();
        }
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

    }

    @Override
    public void onReorganize(Wallet wallet) {

    }

    @Override
    public synchronized void onTransactionConfidenceChanged(Wallet wallet, Transaction tx) {
        /**
         * I'm converting the Bitcoin transaction into all the CryptoTransactions that might contain
         */
        for (CryptoTransaction cryptoTransaction : FermatTransactionConverter.getCryptoTransactions(NETWORK_TYPE, CURRENCY, cryptoNetworkWallet, tx)){
            saveCryptoTransaction(cryptoTransaction);
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
     * Blockchain events
     * @param block
     * @throws VerificationException
     */
    @Override
    public void notifyNewBestBlock(StoredBlock block) throws VerificationException {

    }

    @Override
    public void reorganize(StoredBlock splitPoint, List<StoredBlock> oldBlocks, List<StoredBlock> newBlocks) throws VerificationException {

    }

    @Override
    public boolean isTransactionRelevant(Transaction tx) throws ScriptException {
        return true;
    }

    @Override
    public void receiveFromBlock(Transaction tx, StoredBlock block, AbstractBlockChain.NewBlockType blockType, int relativityOffset) throws VerificationException {
        //System.out.println("received from block " + tx.toString());
    }

    @Override
    public boolean notifyTransactionIsInBlock(Sha256Hash txHash, StoredBlock block, AbstractBlockChain.NewBlockType blockType, int relativityOffset) throws VerificationException {
        return true;
    }


    /**
     * returns the blockchain download progress class.
     * @return
     */
    public BlockchainDownloadProgress getBlockchainDownloadProgress(){
        return blockchainDownloadProgress;
    }
}
