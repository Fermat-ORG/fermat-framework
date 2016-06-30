package com.bitdubai.fermat_bch_api.layer.crypto_network.util;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;

import java.util.List;

/**
 * Created by rodrigo on 1/10/16.
 */
public class BlockchainConnectionStatus {
    private List<ConnectedBitcoinNode> connectedNodes;
    private BlockchainNetworkType blockchainNetworkType;

    /**
     * constructor
     * @param connectedNodes
     * @param blockchainNetworkType
     */
    public BlockchainConnectionStatus(List<ConnectedBitcoinNode> connectedNodes, BlockchainNetworkType blockchainNetworkType) {
        this.connectedNodes = connectedNodes;
        this.blockchainNetworkType = blockchainNetworkType;
    }

    /**
     * the network type of this status.
     * @return
     */
    public BlockchainNetworkType getBlockchainNetworkType() {
        return blockchainNetworkType;
    }

    /**
     * the amount of nodes we are connected to.
     * @return
     */
    public int getNumConnectedNodes() {
        return connectedNodes.size();
    }

    /**
     * gets the connection status for the network.
     * @return
     */
    public boolean isConnected(){
        return connectedNodes.size() > 0;
    }

    @Override
    public String toString() {
        return "BlockchainConnectionStatus{" +
                "connectedNodes=" + connectedNodes +
                ", blockchainNetworkType=" + blockchainNetworkType +
                '}';
    }
}

