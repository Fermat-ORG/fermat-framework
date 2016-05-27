package com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.RegTestNetwork.FermatTestNetworkNode;


import org.bitcoinj.core.Block;
import org.bitcoinj.core.BlockChain;
import org.bitcoinj.core.Context;
import org.bitcoinj.core.FilteredBlock;
import org.bitcoinj.core.GetDataMessage;
import org.bitcoinj.core.Message;
import org.bitcoinj.core.Peer;
import org.bitcoinj.core.PeerAddress;
import org.bitcoinj.core.PeerEventListener;
import org.bitcoinj.core.PeerGroup;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;

import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;


/**
 * Created by rodrigo on 5/20/16.
 */
public class FermatCryptoNetworkPluginRoot extends AbstractPlugin {

    public FermatCryptoNetworkPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    @Override
    public void start() throws CantStartPluginException {

        System.out.println("****FermatNetwork*** started");

        Context context = new Context(RegTestParams.get());
        Wallet wallet = new Wallet(context);
        BlockStore blockStore = new MemoryBlockStore(context.getParams());
        BlockChain blockChain= null;
        try {
            blockChain = new BlockChain(context.getParams(),wallet, blockStore);
        } catch (BlockStoreException e) {
            e.printStackTrace();
        }

        PeerGroup peerGroup = new PeerGroup(context.getParams(), blockChain);
        peerGroup.addWallet(wallet);

        FermatTestNetworkNode node1 = new FermatTestNetworkNode("52.27.68.19", 4877); //bitcoinNode 1
        FermatTestNetworkNode node2 = new FermatTestNetworkNode("52.34.251.168", 4877);  //bitcoinNode 2
        FermatTestNetworkNode node3 = new FermatTestNetworkNode("52.32.106.35", 4877);  //bitcoinNode 3
        FermatTestNetworkNode node4 = new FermatTestNetworkNode("52.34.184.168", 4877);  //bitcoinNode 4
        FermatTestNetworkNode node5 = new FermatTestNetworkNode("52.34.0.33", 4877);  //bitcoinNode 5
        FermatTestNetworkNode node6 = new FermatTestNetworkNode("52.26.116.72", 4877);  //bitcoinNode 6
        FermatTestNetworkNode node7 = new FermatTestNetworkNode("52.24.215.209", 4877);  //bitcoinNode 7
        FermatTestNetworkNode node8 = new FermatTestNetworkNode("52.11.159.154", 4877);  //bitcoinNode 8
        FermatTestNetworkNode node9 = new FermatTestNetworkNode("54.69.92.107", 4877);  //bitcoinNode 9
        FermatTestNetworkNode node10 = new FermatTestNetworkNode("54.68.133.89", 4877);  //bitcoinNode 10
        FermatTestNetworkNode node11 = new FermatTestNetworkNode("52.26.37.10", 4877);  //bitcoinNode 11
        FermatTestNetworkNode node12 = new FermatTestNetworkNode("52.33.107.247", 4877);  //bitcoinNode 12
        FermatTestNetworkNode node13 = new FermatTestNetworkNode("181.169.135.41", 4877);  //bitcoinNode 12
        peerGroup.addAddress(node1.getPeerAddress());
        peerGroup.addAddress(node2.getPeerAddress());
        peerGroup.addAddress(node3.getPeerAddress());
        peerGroup.addAddress(node4.getPeerAddress());
        peerGroup.addAddress(node5.getPeerAddress());
        peerGroup.addAddress(node6.getPeerAddress());
        peerGroup.addAddress(node7.getPeerAddress());
        peerGroup.addAddress(node8.getPeerAddress());
        peerGroup.addAddress(node9.getPeerAddress());
        peerGroup.addAddress(node10.getPeerAddress());
        peerGroup.addAddress(node11.getPeerAddress());
        peerGroup.addAddress(node12.getPeerAddress());
        peerGroup.addAddress(node13.getPeerAddress());

        peerGroup.setUserAgent("Fermatj", "0.1.1");
        peerGroup.addEventListener(new PeerEventListener() {
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
                System.out.println("Peer connected: " + peer.toString());
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

            }

            @Nullable
            @Override
            public List<Message> getData(Peer peer, GetDataMessage m) {
                return null;
            }
        });
        peerGroup.start();
        peerGroup.downloadBlockChain();

        System.out.println("***FermatNetwork*** Connected Peer: " + peerGroup.getConnectedPeers().toString());


    }
}
