package com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;

/**
 * Created by rodrigo on 1/10/16.
 */
public class BlockchainConnectionStatus {
    int connectedNodesCount;
    String downloadNodeIp;
    long  downloadNodePing;
    BlockchainNetworkType blockchainNetworkType;


    public BlockchainConnectionStatus(int connectedNodesCount, String downloadNodeIp, Long downloadNodePing, BlockchainNetworkType blockchainNetworkType) {
        this.connectedNodesCount = connectedNodesCount;
        this.downloadNodeIp = downloadNodeIp;
        this.downloadNodePing = downloadNodePing;
        this.blockchainNetworkType = blockchainNetworkType;
    }

    public int getConnectedNodesCount() {
        return connectedNodesCount;
    }

    public String getDownloadNodeIp() {
        return downloadNodeIp;
    }

    public long getDownloadNodePing() {
        return downloadNodePing;
    }

    public BlockchainNetworkType getBlockchainNetworkType() {
        return blockchainNetworkType;
    }

    public Boolean IsConnected() {
        if (connectedNodesCount == 0)
            return false;
        else
            return true;
    }
}
