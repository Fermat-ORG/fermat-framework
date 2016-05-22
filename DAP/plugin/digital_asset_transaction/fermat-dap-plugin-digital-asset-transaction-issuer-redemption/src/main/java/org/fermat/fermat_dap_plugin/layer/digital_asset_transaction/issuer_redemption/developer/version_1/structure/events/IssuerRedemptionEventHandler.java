package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_redemption.developer.version_1.structure.events;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;

import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantSaveEventException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 23/10/15.
 */
public class IssuerRedemptionEventHandler implements FermatEventHandler {

    //VARIABLE DECLARATION
    private IssuerRedemptionRecorderService recorderService;

    //CONSTRUCTORS

    public IssuerRedemptionEventHandler(IssuerRedemptionRecorderService recorderService) throws CantSetObjectException {
        this.recorderService = Validate.verifySetter(recorderService, "recorderService is null");
    }

    //PUBLIC METHODS
    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {
        if (recorderService.getStatus() != ServiceStatus.STARTED) {
            throw new TransactionServiceNotStartedException();
        }
        if (fermatEvent == null) {
            throw new CantSaveEventException(null, "IssuerRedemptionEventHandler", "Illegal Argument, this method takes an ReceivedNewDigitalAssetMetadataNotificationEvent and was passed an null");
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
