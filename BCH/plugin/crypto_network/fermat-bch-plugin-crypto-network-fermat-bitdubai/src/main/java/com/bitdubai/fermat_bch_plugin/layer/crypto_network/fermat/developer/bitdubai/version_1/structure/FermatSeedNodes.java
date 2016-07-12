package com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.version_1.structure;

import org.bitcoinj.core.PeerAddress;
import org.bitcoinj.params.MainNetParams;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodrigo on 6/30/16.
 */
public class FermatSeedNodes {
    public static List<PeerAddress> getNodes(){
        List<PeerAddress> seedNodes = new ArrayList<>();
        seedNodes.add(new PeerAddress(new InetSocketAddress("52.27.68.19", 4877))); //fermatNode 1
        seedNodes.add(new PeerAddress(new InetSocketAddress("52.34.251.168", 4877)));  //fermatNode 2
        seedNodes.add(new PeerAddress(new InetSocketAddress("52.32.106.35", 4877)));  //fermatNode 3
        seedNodes.add(new PeerAddress(new InetSocketAddress("52.34.184.168", 4877)));  //fermatNode 4
        seedNodes.add(new PeerAddress(new InetSocketAddress("52.34.0.33", 4877)));  //fermatNode 5
        seedNodes.add(new PeerAddress(new InetSocketAddress("52.26.116.72", 4877)));  //fermatNode 6
        seedNodes.add(new PeerAddress(new InetSocketAddress("52.24.215.209", 4877)));  //fermatNode 7
        seedNodes.add(new PeerAddress(new InetSocketAddress("52.11.159.154", 4877)));  //fermatNode 8
        seedNodes.add(new PeerAddress(new InetSocketAddress("54.69.92.107", 4877)));  //fermatNode 9
        seedNodes.add(new PeerAddress(new InetSocketAddress("54.68.133.89", 4877)));  //fermatNode 10
        seedNodes.add(new PeerAddress(new InetSocketAddress("52.26.37.10", 4877)));  //fermatNode 11
        seedNodes.add(new PeerAddress(new InetSocketAddress("52.33.107.247", 4877)));  //fermatNode 12

        return seedNodes;
    }
}
