package com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.networkNodes;

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
        FermatTestNetworkNode node9 = new FermatTestNetworkNode("54.69.92.107", 19080);  //bitcoinNode 9
        FermatTestNetworkNode node10 = new FermatTestNetworkNode("54.68.133.89", 19090);  //bitcoinNode 10
        FermatTestNetworkNode node11 = new FermatTestNetworkNode("52.26.37.10", 19100);  //bitcoinNode 11
        FermatTestNetworkNode node12 = new FermatTestNetworkNode("52.33.107.247", 19110);  //bitcoinNode 12

        networkNodes.add(node1);
        networkNodes.add(node2);
        networkNodes.add(node3);
        networkNodes.add(node4);
        networkNodes.add(node5);
        networkNodes.add(node6);
        networkNodes.add(node7);
        networkNodes.add(node8);
        networkNodes.add(node9);
        networkNodes.add(node10);
        networkNodes.add(node11);
        networkNodes.add(node12);

//        FermatTestNetworkNode node1 = new FermatTestNetworkNode("localhost", 18333); //bitcoinNode 1
//        networkNodes.add(node1);
    }

    /**
     * will return a randmon existing client.
     * @return
     */
    public FermatTestNetworkNode getRandomNetworkNode(){
        int random = (int )(Math.random() * networkNodes.size());
        return networkNodes.get(random);
    }

    /**
     * gets the network nodes.
     * @return
     */
    public List<FermatTestNetworkNode> getNetworkNodes() {
        return networkNodes;
    }
}
