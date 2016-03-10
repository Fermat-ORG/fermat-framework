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
    long progress;

    public BlockchainDownloadProgress(BlockchainNetworkType blockchainNetworkType, int pendingBlocks, int totalBlocks, int downloadedBlocks, long progress) {
        this.blockchainNetworkType = blockchainNetworkType;
        this.pendingBlocks = pendingBlocks;
        this.totalBlocks = totalBlocks;
        this.downloadedBlocks = downloadedBlocks;
        this.progress = progress;
    }

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

    public long getProgress() {
        return progress;
    }
}
