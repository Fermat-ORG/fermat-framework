package org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.structure.events;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;

import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantSaveEventException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 23/10/15.
 */
public class RedeemPointRedemptionEventHandler implements FermatEventHandler {

    //VARIABLE DECLARATION
    private RedeemPointRedemptionRecorderService recorderService;

    //CONSTRUCTORS

    public RedeemPointRedemptionEventHandler(RedeemPointRedemptionRecorderService recorderService) {
        this.recorderService = recorderService;
    }

    //PUBLIC METHODS
    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {
        if (recorderService.getStatus() != ServiceStatus.STARTED) {
            throw new TransactionServiceNotStartedException();
        }
        if (fermatEvent == null) {
            throw new CantSaveEventException(null, "Handling the ReceivedNewDigitalAssetMetadataNotificationEvent", "Illegal Argument, this method takes an ReceivedNewDigitalAssetMetadataNotificationEvent and was passed an null");
        }
        String context = "Event Type: " + fermatEvent.getEventType() +
                "Event Source: " + fermatEvent.getSource();
        System.out.println("VAMM: RECEIVED A NEW EVENT!");
        System.out.println("VAMM: " + context);

        recorderService.receiveNewEvent(fermatEvent);
    }

    //PRIVATE METHODS

    //GETTERS AND SETTERS

    //INNER CLASSES
}
