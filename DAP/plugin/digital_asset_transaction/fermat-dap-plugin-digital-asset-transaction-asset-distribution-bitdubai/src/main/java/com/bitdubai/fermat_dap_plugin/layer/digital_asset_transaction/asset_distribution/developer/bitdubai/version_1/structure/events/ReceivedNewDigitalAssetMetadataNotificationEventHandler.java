package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantSaveEventException;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.ReceivedNewDigitalAssetMetadataNotificationEvent;

/**
 * Created by Luis Campo (campusprize@gmail.com) on 20/10/15.
 */
public class ReceivedNewDigitalAssetMetadataNotificationEventHandler implements FermatEventHandler {
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

        if (!(fermatEvent instanceof ReceivedNewDigitalAssetMetadataNotificationEvent)) {
            //You're using the wrong handler..
            throw new CantSaveEventException(null, context, "The event received event is not an instance of ReceivedNewDigitalAssetMetadataNotificationEvent, use the right handler.");
        }

        ReceivedNewDigitalAssetMetadataNotificationEvent metadataNotificationEvent = (ReceivedNewDigitalAssetMetadataNotificationEvent) fermatEvent;

        try {
            assetDistributionRecorderService.receivedNewDigitalAssetMetadataNotificationrEvent(metadataNotificationEvent);
        } catch (Exception exception) {
            throw new CantSaveEventException(exception, "Handling the ReceivedNewDigitalAssetMetadataNotificationEvent", "Unexpected exception");
        }
    }
}
