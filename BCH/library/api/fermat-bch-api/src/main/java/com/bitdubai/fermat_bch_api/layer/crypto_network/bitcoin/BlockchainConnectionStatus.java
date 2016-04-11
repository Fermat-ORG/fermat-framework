package com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;

import java.util.List;

/**
 * Created by rodrigo on 1/10/16.
 */
public class BlockchainConnectionStatus {
    private int connectedNodesCount;
    private ConnectedBitcoinNode downloadNode;
    private List<ConnectedBitcoinNode> connectedNodes;
    private BlockchainNetworkType blockchainNetworkType;


    /**
     * Constructor
     * @param connectedNodesCount
     * @param downloadNode
     * @param connectedNodes
     * @param blockchainNetworkType
     */
    public BlockchainConnectionStatus(int connectedNodesCount, ConnectedBitcoinNode downloadNode, List<ConnectedBitcoinNode> connectedNodes, BlockchainNetworkType blockchainNetworkType) {
        this.connectedNodesCount = connectedNodesCount;
        this.downloadNode = downloadNode;
        this.connectedNodes = connectedNodes;
        this.blockchainNetworkType = blockchainNetworkType;
    }

    public int getConnectedNodesCount() {
        return connectedNodesCount;
    }

    public ConnectedBitcoinNode getDownloadNode() {
        return downloadNode;
    }

    public List<ConnectedBitcoinNode> getConnectedNodes() {
        return connectedNodes;
    }

    public BlockchainNetworkType getBlockchainNetworkType() {
        return blockchainNetworkType;
    }
}

