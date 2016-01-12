package com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.RegTestNetwork;

import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkConfiguration;

import org.bitcoinj.core.PeerAddress;

import java.net.InetSocketAddress;

/**
 * Created by rodrigo on 1/9/16.
 * Represents a Network node of the internal regtest Bitcoin Network used in Fermat.
 */
public class FermatTestNetworkNode {
    private String ipAddress;
    private int portNumber;
    private PeerAddress peerAddress;

    public FermatTestNetworkNode(String ipAddress, int portNumber) {
        this.ipAddress = ipAddress;
        this.portNumber = portNumber;

        InetSocketAddress inetSocketAddress1 = new InetSocketAddress(ipAddress, portNumber);
        this.peerAddress= new PeerAddress(inetSocketAddress1);
    }

    public PeerAddress getPeerAddress() {
        return peerAddress;
    }
}
