package com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;



import org.fermatj.core.Block;
import org.fermatj.core.BlockChain;
import org.fermatj.core.FilteredBlock;
import org.fermatj.core.GetDataMessage;
import org.fermatj.core.Message;
import org.fermatj.core.NetworkParameters;
import org.fermatj.core.Peer;
import org.fermatj.core.PeerAddress;
import org.fermatj.core.PeerEventListener;
import org.fermatj.core.PeerGroup;
import org.fermatj.core.Transaction;
import org.fermatj.core.Wallet;
import org.fermatj.params.RegTestParams;
import org.fermatj.store.BlockStore;
import org.fermatj.store.BlockStoreException;
import org.fermatj.store.MemoryBlockStore;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Set;




/**
 * Created by rodrigo on 5/20/16.
 */
public class FermatCryptoNetworkPluginRoot extends AbstractPlugin {

    public FermatCryptoNetworkPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    @Override
    public void start() throws CantStartPluginException {

        System.out.println("***FermatNetwork*** started");


        final NetworkParameters networkParameters = RegTestParams.get();
        Wallet wallet = new Wallet(networkParameters);

        BlockStore blockstore = new MemoryBlockStore(networkParameters);
        BlockChain blockchain = null;
        try {
            blockchain = new BlockChain(networkParameters, wallet, blockstore);
        } catch (BlockStoreException e1) {
            e1.printStackTrace();
        }
        PeerGroup peerGroup = new PeerGroup(networkParameters, blockchain);
        peerGroup.addWallet(wallet);


        PeerAddress peer1 = new PeerAddress(new InetSocketAddress("52.27.68.19", 4877));
        PeerAddress peer2 = new PeerAddress(new InetSocketAddress("52.34.251.168", 4877));
        PeerAddress peer3 = new PeerAddress(new InetSocketAddress("52.32.106.35", 4877));

        peerGroup.addAddress(peer1);
        peerGroup.addAddress(peer2);
        peerGroup.addAddress(peer3);

        peerGroup.setUserAgent("fermatj", "0.0.1");
        peerGroup.addEventListener(new PeerEventListener() {
            @Override
            public void onPeersDiscovered(Set<PeerAddress> set) {

            }

            @Override
            public void onBlocksDownloaded(Peer peer, Block block, FilteredBlock filteredBlock, int i) {

            }

            @Override
            public void onChainDownloadStarted(Peer peer, int i) {

            }

            @Override
            public void onPeerConnected(Peer peer, int i) {
                System.out.println("***FermatNetwork*** Peer conectado: " + peer.toString());
            }

            @Override
            public void onPeerDisconnected(Peer peer, int i) {

            }

            @Override
            public Message onPreMessageReceived(Peer peer, Message message) {
                return null;
            }

            @Override
            public void onTransaction(Peer peer, Transaction transaction) {

            }


            @Override
            public List<Message> getData(Peer peer, GetDataMessage getDataMessage) {
                return null;
            }
        });
        peerGroup.start();
        peerGroup.downloadBlockChain();
        System.out.println(wallet.freshReceiveAddress().toString());


    }



}