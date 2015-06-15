package com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure;

import org.bitcoinj.core.AbstractBlockChain;
import org.bitcoinj.core.Block;
import org.bitcoinj.core.BlockChainListener;
import org.bitcoinj.core.GetDataMessage;
import org.bitcoinj.core.Message;
import org.bitcoinj.core.Peer;
import org.bitcoinj.core.PeerEventListener;
import org.bitcoinj.core.ScriptException;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.StoredBlock;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.VerificationException;

import java.util.List;

import javax.annotation.Nullable;

/**
 * Created by rodrigo on 27/05/15.
 */
class BitcoinEventListeners implements BlockChainListener, PeerEventListener{


    /**
     * BlockChainEventListener interface implementation
     * @param block
     * @throws VerificationException
     */
    @Override
    public void notifyNewBestBlock(StoredBlock block) throws VerificationException {

    }


    /**
     * BlockChainEventListener interface implementation
     * @param splitPoint
     * @param oldBlocks
     * @param newBlocks
     * @throws VerificationException
     */
    @Override
    public void reorganize(StoredBlock splitPoint, List<StoredBlock> oldBlocks, List<StoredBlock> newBlocks) throws VerificationException {

    }

    /**
     * BlockChainEventListener interface implementation     *
     * @param tx
     * @return
     * @throws ScriptException
     */
    @Override
    public boolean isTransactionRelevant(Transaction tx) throws ScriptException {
        return true;
    }

    /**
     * BlockChainEventListener interface implementation
     * @param tx
     * @param block
     * @param blockType
     * @param relativityOffset
     * @throws VerificationException
     */
    @Override
    public void receiveFromBlock(Transaction tx, StoredBlock block, AbstractBlockChain.NewBlockType blockType, int relativityOffset) throws VerificationException {
        System.out.println("Receive from block. Transaction: " + tx.toString());
        System.out.println("block: " + block.toString());

    }

    /**
     * BlockChainEventListener interface implementation
     * @param txHash
     * @param block
     * @param blockType
     * @param relativityOffset
     * @return
     * @throws VerificationException
     */
    @Override
    public boolean notifyTransactionIsInBlock(Sha256Hash txHash, StoredBlock block, AbstractBlockChain.NewBlockType blockType, int relativityOffset) throws VerificationException {
        return true;
    }


    /**
     * PeerEventListener intercace implementation
     * @param peer
     * @param block
     * @param blocksLeft
     */
    @Override
    public void onBlocksDownloaded(Peer peer, Block block, int blocksLeft) {
        System.out.println("Blocks Downloaded. Blocks Left " + blocksLeft);

    }

    /**
     * PeerEventListener intercace implementation
     * @param peer
     * @param blocksLeft
     */
    @Override
    public void onChainDownloadStarted(Peer peer, int blocksLeft) {
        System.out.println("Blockchain Download started. Blocks left: " + blocksLeft);
    }

    /**
     * PeerEventListener intercace implementation
     * @param peer
     * @param peerCount
     */
    @Override
    public void onPeerConnected(Peer peer, int peerCount) {
        System.out.println("Peer connected (total: " + peerCount + "). Peer info: " + peer.toString());
    }

    /**
     * PeerEventListener intercace implementation
     * @param peer
     * @param peerCount
     */
    @Override
    public void onPeerDisconnected(Peer peer, int peerCount) {
        System.out.println("Peer disconnected. Total Peers are " + peerCount);
    }

    /**
     * PeerEventListener intercace implementation
     * @param peer
     * @param m
     * @return
     */
    @Override
    public Message onPreMessageReceived(Peer peer, Message m) {
        return null;
    }

    /**
     * PeerEventListener intercace implementation
     * @param peer
     * @param t
     */
    @Override
    public void onTransaction(Peer peer, Transaction t) {

    }

    /**
     * PeerEventListener intercace implementation
     * @param peer
     * @param m
     * @return
     */
    @Nullable
    @Override
    public List<Message> getData(Peer peer, GetDataMessage m) {
        return null;
    }
}
