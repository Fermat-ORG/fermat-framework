package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantSaveEventException;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.ReceivedNewDigitalAssetMetadataNotificationEvent;

import java.util.logging.Logger;

/**
 * Created by Frank Contreras on 20/10/15.
 */
public class ReceivedNewDigitalAssetMetadataNotificationEventHandler implements FermatEventHandler {
    AssetReceptionRecorderService assetReceptionRecorderService;

    public void setAssetReceptionRecorderService(AssetReceptionRecorderService assetReceptionRecorderService) throws CantSetObjectException {
        if (assetReceptionRecorderService == null) {
            throw new CantSetObjectException("assetReceptionRecorderService is null");
        }
        this.assetReceptionRecorderService = assetReceptionRecorderService;
    }

    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {
        String context = "Event Type: " + fermatEvent.getEventType() +
                "Event Source: " + fermatEvent.getSource();

        if (assetReceptionRecorderService.getStatus() != ServiceStatus.STARTED) {
            throw new TransactionServiceNotStartedException();
        }

        if (!(fermatEvent instanceof ReceivedNewDigitalAssetMetadataNotificationEvent)) {
            //You're using the wrong handler..
            throw new CantSaveEventException(null, context, "The event received event is not an instance of ReceivedNewDigitalAssetMetadataNotificationEvent, use the right handler.");
        }

        ReceivedNewDigitalAssetMetadataNotificationEvent metadataNotificationEvent = (ReceivedNewDigitalAssetMetadataNotificationEvent) fermatEvent;

        try {
            System.out.println("ASSET RECEPTION event detected - type: " + fermatEvent.getEventType());
            System.out.println("ASSET RECEPTION event detected - source: " + fermatEvent.getSource());
            assetReceptionRecorderService.receivedNewDigitalAssetMetadataNotificationEvent(metadataNotificationEvent);
        } catch (Exception exception) {
            throw new CantSaveEventException(exception, "Handling the ReceivedNewDigitalAssetMetadataNotificationEvent", "Unexpected exception");
        }
    }
}
