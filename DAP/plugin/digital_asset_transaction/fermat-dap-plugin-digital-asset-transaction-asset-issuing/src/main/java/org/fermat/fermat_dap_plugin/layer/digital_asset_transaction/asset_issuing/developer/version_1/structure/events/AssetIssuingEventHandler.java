package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.events;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;

import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantSaveEventException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 21/12/15.
 */
public class AssetIssuingEventHandler implements FermatEventHandler {

    //VARIABLE DECLARATION
    private AssetIssuingRecorderService recorderService;

    //CONSTRUCTORS

    public AssetIssuingEventHandler(AssetIssuingRecorderService assetIssuingRecorderService) {
        this.recorderService = assetIssuingRecorderService;
    }

    //PUBLIC METHODS

    /**
     * Throw the method <code>handleEvent</code> you can handle the fermat event.
     *
     * @param fermatEvent event to be handled.
     * @throws FermatException if something goes wrong.
     */
    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {
        if (fermatEvent == null)
            throw new CantSaveEventException(null, "Handling the ReceivedNewDigitalAssetMetadataNotificationEvent", "Illegal Argument, this method takes an ReceivedNewDigitalAssetMetadataNotificationEvent and was passed an null");

        System.out.println("VAMM: ASSET ISSUING RECEIVED A NEW EVENT!");
        System.out.println("VAMM: Type: " + fermatEvent.getEventType() + " - Source: " + fermatEvent.getSource());

        if (recorderService.getStatus() != ServiceStatus.STARTED) {
            throw new TransactionServiceNotStartedException();
        }
        recorderService.receiveNewEvent(fermatEvent);
    }
    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
