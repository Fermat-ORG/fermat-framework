package com.bitdubai.fermat_bch_api.layer.definition.event_manager.listeners;

import com.bitdubai.fermat_api.layer.all_definition.events.common.GenericEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.enums.EventType;

/**
 * Created by rodrigo on 4/29/16.
 */
public class BlockchainDownloadUpToDateEventListener extends GenericEventListener {
    public BlockchainDownloadUpToDateEventListener(FermatEventMonitor fermatEventMonitor) {
        super(EventType.BLOCKCHAIN_DOWNLOAD_UP_TO_DATE, fermatEventMonitor);
    }
}
