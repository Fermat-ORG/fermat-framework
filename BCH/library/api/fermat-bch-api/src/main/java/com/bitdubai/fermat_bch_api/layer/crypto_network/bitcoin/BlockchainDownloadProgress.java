package com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;

import java.io.Serializable;

/**
 * Created by rodrigo on 3/9/16.
 */
public class BlockchainDownloadProgress implements Serializable{
    BlockchainNetworkType blockchainNetworkType;
    int pendingBlocks;
    int totalBlocks;
    int downloadedBlocks;
    int progress;
    String downloader;
    long lastBlockDownloadTime;

    public BlockchainDownloadProgress(BlockchainNetworkType blockchainNetworkType, int pendingBlocks, int totalBlocks, int downloadedBlocks, int progress) {
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

    public int getProgress() {
        return progress;
    }

    public void setPendingBlocks(int pendingBlocks) {
        this.pendingBlocks = pendingBlocks;

        downloadedBlocks = totalBlocks - pendingBlocks;

        if (downloadedBlocks<0)
            downloadedBlocks = 0;

        progress = (totalBlocks > 0) ? ((downloadedBlocks * 100) / totalBlocks) : 100;

    }

    public void setTotalBlocks(int totalBlocks) {
        this.totalBlocks = totalBlocks;
    }

    public String getDownloader() {
        return downloader;
    }

    public void setDownloader(String downloader) {
        this.downloader = downloader;
    }

    public long getLastBlockDownloadTime() {
        return lastBlockDownloadTime;
    }

    public void setLastBlockDownloadTime(long lastBlockDownloadTime) {
        this.lastBlockDownloadTime = lastBlockDownloadTime;
    }

    @Override
    public String toString() {
        return "BlockchainDownloadProgress{" +
                "blockchainNetworkType=" + blockchainNetworkType +
                ", pendingBlocks=" + pendingBlocks +
                ", totalBlocks=" + totalBlocks +
                ", downloadedBlocks=" + downloadedBlocks +
                ", progress=" + progress +
                ", downloader='" + downloader + '\'' +
                '}';
    }
}
