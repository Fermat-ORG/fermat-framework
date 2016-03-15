package com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;

/**
 * Created by rodrigo on 3/9/16.
 */
public class BlockchainDownloadProgress {
    BlockchainNetworkType blockchainNetworkType;
    int pendingBlocks;
    int totalBlocks;
    int downloadedBlocks;
    double progress;

    public BlockchainDownloadProgress(BlockchainNetworkType blockchainNetworkType, int pendingBlocks, int totalBlocks, int downloadedBlocks, double progress) {
        this.blockchainNetworkType = blockchainNetworkType;
        this.pendingBlocks = pendingBlocks;
        this.totalBlocks = totalBlocks;
        this.downloadedBlocks = downloadedBlocks;
        this.progress = progress;
    }

    /**
     * Getters and Setters
     */

    public BlockchainNetworkType getBlockchainNetworkType() {
        return blockchainNetworkType;
    }

    public int getPendingBlocks() {
        return pendingBlocks;
    }

    public int getTotalBlocks() {
        return totalBlocks;
    }

    public int getDownloadedBlocks() {
        return downloadedBlocks;
    }

    public double getProgress() {
        return progress;
    }

    public void setPendingBlocks(int pendingBlocks) {
        this.pendingBlocks = pendingBlocks;

        downloadedBlocks = totalBlocks - pendingBlocks;
        progress = (totalBlocks > 0) ? pendingBlocks / totalBlocks : 1.0;

    }

    public void setTotalBlocks(int totalBlocks) {
        this.totalBlocks = totalBlocks;
    }



}
