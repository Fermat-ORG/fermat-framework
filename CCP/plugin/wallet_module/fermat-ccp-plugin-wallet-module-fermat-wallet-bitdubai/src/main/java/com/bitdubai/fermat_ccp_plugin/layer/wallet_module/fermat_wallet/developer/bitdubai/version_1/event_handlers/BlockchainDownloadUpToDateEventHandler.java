package com.bitdubai.fermat_ccp_plugin.layer.wallet_module.fermat_wallet.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_ccp_plugin.layer.wallet_module.fermat_wallet.developer.bitdubai.version_1.exceptions.BlockchainDownloadUpToDateEventHandlerException;

/**
 * Created by natalia on 29/04/16.
 */
public class BlockchainDownloadUpToDateEventHandler implements FermatEventHandler {


    private Broadcaster broadcaster;

    public BlockchainDownloadUpToDateEventHandler(Broadcaster broadcaster ) {

        this.broadcaster = broadcaster;
    }

    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {

            try
            {
                broadcaster.publish(BroadcasterType.UPDATE_VIEW,"BlockchainDownloadComplete");
            }
             catch (Exception e) {
                throw new BlockchainDownloadUpToDateEventHandlerException("An unexpected exception happened",FermatException.wrapException(e),"","");
            }


    }
}
