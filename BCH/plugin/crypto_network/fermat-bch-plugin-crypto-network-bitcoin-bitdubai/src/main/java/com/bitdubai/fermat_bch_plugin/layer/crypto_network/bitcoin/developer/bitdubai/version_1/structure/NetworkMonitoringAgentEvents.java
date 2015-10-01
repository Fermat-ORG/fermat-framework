package com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_cry_api.layer.definition.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import org.bitcoinj.core.AbstractWalletEventListener;
import org.bitcoinj.core.Block;
import org.bitcoinj.core.BlockChainListener;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.FilteredBlock;
import org.bitcoinj.core.GetDataMessage;
import org.bitcoinj.core.Message;
import org.bitcoinj.core.Peer;
import org.bitcoinj.core.PeerAddress;
import org.bitcoinj.core.PeerEventListener;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.Wallet;

import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

/**
 * Created by rodrigo on 9/30/15.
 */
public class NetworkMonitoringAgentEvents extends AbstractWalletEventListener implements PeerEventListener{
    EventManager eventManager;

    /**
     * Constructor
     * @param eventManager
     */
    public NetworkMonitoringAgentEvents(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public void onCoinsReceived(Wallet wallet, Transaction tx, Coin prevBalance, Coin newBalance) {
        super.onCoinsReceived(wallet, tx, prevBalance, newBalance);
        System.out.println("Bitcoins recieved. Prev Balance: "+ prevBalance + " new Balance:" + newBalance);

        //todo start IncomingCryptoOnBlockChain
        FermatEvent transactionEvent = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_ON_BLOCKCHAIN);
        transactionEvent.setSource(EventSource.CRYPTO_VAULT);
        eventManager.raiseEvent(transactionEvent);
    }

    @Override
    public void onTransactionConfidenceChanged(Wallet wallet, Transaction tx) {
        super.onTransactionConfidenceChanged(wallet, tx);
    }

    @Override
    public void onPeersDiscovered(Set<PeerAddress> peerAddresses) {

    }

    @Override
    public void onBlocksDownloaded(Peer peer, Block block, FilteredBlock filteredBlock, int blocksLeft) {

    }

    @Override
    public void onChainDownloadStarted(Peer peer, int blocksLeft) {

    }

    @Override
    public void onPeerConnected(Peer peer, int peerCount) {

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
        System.out.println("New transaction detected: " + t.toString());
    }

    @Nullable
    @Override
    public List<Message> getData(Peer peer, GetDataMessage m) {
        return null;
    }
}
