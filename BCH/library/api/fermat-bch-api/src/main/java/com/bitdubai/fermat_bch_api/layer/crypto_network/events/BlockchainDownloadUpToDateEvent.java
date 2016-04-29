package com.bitdubai.fermat_bch_api.layer.crypto_network.events;


import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.BlockchainDownloadProgress;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.enums.EventType;

/**
 * Created by rodrigo on 4/29/16.
 */
public class BlockchainDownloadUpToDateEvent extends AbstractFermatBchEvent {
    private BlockchainDownloadProgress blockchainDownloadProgress;
    private BlockchainNetworkType blockchainNetworkType;

    public BlockchainDownloadUpToDateEvent() {
        super(EventType.BLOCKCHAIN_DOWNLOAD_UP_TO_DATE);
    }

    public BlockchainDownloadProgress getBlockchainDownloadProgress() {
        return blockchainDownloadProgress;
    }

    public void setBlockchainDownloadProgress(BlockchainDownloadProgress blockchainDownloadProgress) {
        this.blockchainDownloadProgress = blockchainDownloadProgress;
    }

    public BlockchainNetworkType getBlockchainNetworkType() {
        return blockchainNetworkType;
    }

    public void setBlockchainNetworkType(BlockchainNetworkType blockchainNetworkType) {
        this.blockchainNetworkType = blockchainNetworkType;
    }
}
