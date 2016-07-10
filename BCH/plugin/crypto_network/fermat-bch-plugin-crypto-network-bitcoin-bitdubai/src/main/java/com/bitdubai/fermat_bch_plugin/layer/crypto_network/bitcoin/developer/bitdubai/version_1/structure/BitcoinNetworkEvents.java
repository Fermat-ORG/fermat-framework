package com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.util.BitcoinTransactionConverter;
import com.bitdubai.fermat_bch_api.layer.crypto_network.util.BlockchainDownloadProgress;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.BitcoinNetworkConfiguration;
import com.bitdubai.fermat_bch_api.layer.crypto_network.events.BlockchainDownloadUpToDateEvent;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.database.BitcoinCryptoNetworkDatabaseDao;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;

import org.bitcoinj.core.AbstractBlockChain;
import org.bitcoinj.core.Block;
import org.bitcoinj.core.BlockChainListener;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.Context;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.FilteredBlock;
import org.bitcoinj.core.GetDataMessage;
import org.bitcoinj.core.Message;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Peer;
import org.bitcoinj.core.PeerAddress;
import org.bitcoinj.core.PeerEventListener;
import org.bitcoinj.core.ScriptException;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.StoredBlock;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.VerificationException;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.core.WalletEventListener;
import org.bitcoinj.script.Script;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

/**
 * Created by rodrigo on 10/4/15.
 */
public class BitcoinNetworkEvents implements WalletEventListener, PeerEventListener, BlockChainListener {



    /**
     * Class variables
     */
    private final BitcoinCryptoNetworkDatabaseDao dao;
    File walletFilename;
    final BlockchainNetworkType NETWORK_TYPE;
    final Context context;
    final NetworkParameters NETWORK_PARAMETERS;
    BlockchainDownloadProgress blockchainDownloadProgress;
    Wallet cryptoNetworkWallet;
    private final CryptoCurrency BITCOIN = BitcoinNetworkConfiguration.CRYPTO_CURRENCY;


    /**
     * platform variables
     */
    final EventManager eventManager;

    /**
     * Constructor
     */
    public BitcoinNetworkEvents(BlockchainNetworkType blockchainNetworkType,
                                File walletFilename,
                                Context context,
                                Wallet wallet,
                                BitcoinCryptoNetworkDatabaseDao bitcoinCryptoNetworkDatabaseDao,
                                EventManager eventManager) {
        this.NETWORK_TYPE = blockchainNetworkType;
        this.walletFilename = walletFilename;
        this.context = context;
        this.NETWORK_PARAMETERS = context.getParams();
        this.cryptoNetworkWallet = wallet;
        this.dao = bitcoinCryptoNetworkDatabaseDao;
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
            System.out.println("***CryptoNetwork*** Block downloaded on " + NETWORK_TYPE.getCode() + ". Pending blocks: " + blocksLeft + ". Block date: " + block.getTime());

        if (blocksLeft < 50)
            System.out.println("***CryptoNetwork*** Block downloaded on " + NETWORK_TYPE.getCode() + ". Pending blocks: " + blocksLeft);


        /**
         * sets the blockchainDownloader data
         */
        blockchainDownloadProgress.setPendingBlocks(blocksLeft);
        blockchainDownloadProgress.setLastBlockDownloadTime(block.getTimeSeconds());


        blockchainDownloadProgress.setDownloader(peer.toString());

        /**
         * Now I will raise the BlockchainDownloadUpToDateEvent to notify that we are updated
         */
        if (blocksLeft == 0){
            BlockchainDownloadUpToDateEvent blockchainDownloadUpToDateEvent = new BlockchainDownloadUpToDateEvent(blockchainDownloadProgress, NETWORK_TYPE);
            eventManager.raiseEvent(blockchainDownloadUpToDateEvent);
            System.out.println("***CryptoNetwork*** BlockchainDownloadUpToDateEvent raised. " + blockchainDownloadProgress.toString());
        }

    }



    @Override
    public void onChainDownloadStarted(Peer peer, int blocksLeft) {
        System.out.println("***CryptoNetwork*** Blockchain Download started for " + NETWORK_TYPE.getCode() + " network. Blocks left: " + blocksLeft);
        blockchainDownloadProgress.setTotalBlocks(blocksLeft);
    }

    @Override
    public void onPeerConnected(Peer peer, int peerCount) {
        StringBuilder logAggresive = new StringBuilder("***CryptoNetwork*** New Peer connection on " + NETWORK_TYPE.getCode() + "  to " + peer.toString());
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
      for (CryptoTransaction cryptoTransaction : BitcoinTransactionConverter.getCryptoTransactions(NETWORK_TYPE, BITCOIN, cryptoNetworkWallet, tx)){
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
        for (CryptoTransaction cryptoTransaction : BitcoinTransactionConverter.getCryptoTransactions(NETWORK_TYPE, BITCOIN, cryptoNetworkWallet, tx)){
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

