package com.bitdubai.fermat_bch_api.layer.crypto_network.events;


import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_bch_api.layer.crypto_network.util.BlockchainDownloadProgress;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.enums.EventType;

/**
 * Created by rodrigo on 4/29/16.
 */
public class BlockchainDownloadUpToDateEvent extends AbstractFermatBchEvent {
    private BlockchainDownloadProgress blockchainDownloadProgress;
    private BlockchainNetworkType blockchainNetworkType;

    /**
     * default constructor
     */
    public BlockchainDownloadUpToDateEvent() {
        super(EventType.BLOCKCHAIN_DOWNLOAD_UP_TO_DATE);
    }

    /**
     * overloaded constructor
     * @param blockchainDownloadProgress
     * @param blockchainNetworkType
     */
    public BlockchainDownloadUpToDateEvent(BlockchainDownloadProgress blockchainDownloadProgress, BlockchainNetworkType blockchainNetworkType) {
        super(EventType.BLOCKCHAIN_DOWNLOAD_UP_TO_DATE);
        this.blockchainDownloadProgress = blockchainDownloadProgress;
        this.blockchainNetworkType = blockchainNetworkType;
    }

    public BlockchainDownloadProgress getBlockchainDownloadProgress() {
        return blockchainDownloadProgress;
    }

    public BlockchainNetworkType getBlockchainNetworkType() {
        return blockchainNetworkType;
    }
}
