package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantSaveEventException;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.ReceivedNewTransactionStatusNotificationEvent;

/**
 * Created by Luis Campo (campusprize@gmail.com) on 20/10/15.
 */
public class ReceivedNewTransactionStatusNotificationEventHandler implements FermatEventHandler {
    AssetDistributionRecorderService assetDistributionRecorderService;

    public void setAssetDistributionRecorderService(AssetDistributionRecorderService assetDistributionRecorderService) throws CantSetObjectException {
        if (assetDistributionRecorderService == null) {
            throw new CantSetObjectException("assetDistributionRecorderService is null");
        }
        this.assetDistributionRecorderService = assetDistributionRecorderService;
    }

    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {
        String context = "Event Type: " + fermatEvent.getEventType() +
                "Event Source: " + fermatEvent.getSource();

        if (assetDistributionRecorderService.getStatus() != ServiceStatus.STARTED) {
            throw new TransactionServiceNotStartedException();
        }

        if (!(fermatEvent instanceof ReceivedNewTransactionStatusNotificationEvent)) {
            //You're using the wrong handler..
            throw new CantSaveEventException(null, context, "The event received event is not an instance of ReceivedNewTransactionStatusNotificationEvent, use the right handler.");
        }

        ReceivedNewTransactionStatusNotificationEvent metadataNotificationEvent = (ReceivedNewTransactionStatusNotificationEvent) fermatEvent;

        try {
            assetDistributionRecorderService.receivedTransactionStatusNotificationrEvent(metadataNotificationEvent);
        } catch (Exception exception) {
            throw new CantSaveEventException(exception, "Handling the ReceivedNewTransactionStatusNotificationEvent", "Unexpected exception");
        }
    }
}
