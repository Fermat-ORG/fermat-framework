package com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.RegTestNetwork;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodrigo on 1/9/16.
 * Represents the RegTest internal Bitcoin network used by the platform.
 */
public class FermatTestNetwork {
    private List<FermatTestNetworkNode> networkNodes;

    /**
     * default constructor
     */
    public FermatTestNetwork() {
        addNetworkNodes();
    }

    /**
     * I add the predefined existing nodes to the network.
     */
    private void addNetworkNodes() {
        networkNodes = new ArrayList<>();

        FermatTestNetworkNode node1 = new FermatTestNetworkNode("52.27.68.19", 19000); //bitcoinNode 1
        FermatTestNetworkNode node2 = new FermatTestNetworkNode("52.34.251.168", 19010);  //bitcoinNode 2
        FermatTestNetworkNode node3 = new FermatTestNetworkNode("52.32.106.35", 19020);  //bitcoinNode 3
        FermatTestNetworkNode node4 = new FermatTestNetworkNode("52.34.184.168", 19030);  //bitcoinNode 4
        FermatTestNetworkNode node5 = new FermatTestNetworkNode("52.34.0.33", 19040);  //bitcoinNode 5
        FermatTestNetworkNode node6 = new FermatTestNetworkNode("52.26.116.72", 19050);  //bitcoinNode 6
        FermatTestNetworkNode node7 = new FermatTestNetworkNode("52.24.215.209", 19060);  //bitcoinNode 7
        FermatTestNetworkNode node8 = new FermatTestNetworkNode("52.11.159.154", 19070);  //bitcoinNode 8

        networkNodes.add(node1);
        networkNodes.add(node2);
        networkNodes.add(node3);
        networkNodes.add(node4);
        networkNodes.add(node5);
        networkNodes.add(node6);
        networkNodes.add(node7);
        networkNodes.add(node8);
    }

    /**
     * will return a ranmon existing client.
     * @return
     */
    public FermatTestNetworkNode getRandomNetworkNode(){
        int random = (int )(Math.random() * networkNodes.size());
        return networkNodes.get(random);
    }
}
