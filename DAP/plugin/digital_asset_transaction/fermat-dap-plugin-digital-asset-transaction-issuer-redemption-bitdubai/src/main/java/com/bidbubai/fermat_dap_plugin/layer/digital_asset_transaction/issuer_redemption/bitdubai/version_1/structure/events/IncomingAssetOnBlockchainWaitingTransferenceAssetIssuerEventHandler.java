package com.bidbubai.fermat_dap_plugin.layer.digital_asset_transaction.issuer_redemption.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantSaveEventException;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.IncomingAssetOnBlockchainWaitingTransferenceAssetIssuerEvent;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 18/10/15.
 */
public class IncomingAssetOnBlockchainWaitingTransferenceAssetIssuerEventHandler implements FermatEventHandler {
    IssuerRedemptionRecorderService issuerRedemptionRecorderService;

    public void setIssuerRedemptionRecorderService(IssuerRedemptionRecorderService issuerRedemptionRecorderService) throws CantSetObjectException {
        if(issuerRedemptionRecorderService ==null){
            throw new CantSetObjectException("issuerRedemptionRecorderService is null");
        }
        this.issuerRedemptionRecorderService = issuerRedemptionRecorderService;
    }
    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {

        if(this.issuerRedemptionRecorderService.getStatus()== ServiceStatus.STARTED) {

            try {
                this.issuerRedemptionRecorderService.incomingAssetOnBlockchainWaitingTransferenceAssetIssuerEvent((IncomingAssetOnBlockchainWaitingTransferenceAssetIssuerEvent) fermatEvent);
            } catch(CantSaveEventException exception){
                throw new CantSaveEventException(exception,"Handling the IncomingAssetOnBlockchainWaitingTransferenceAssetIssuerEvent", "Check the cause");
            } catch(ClassCastException exception){
                //Logger LOG = Logger.getGlobal();
                //LOG.info("EXCEPTION DETECTOR----------------------------------");
                //exception.printStackTrace();
                throw new CantSaveEventException(FermatException.wrapException(exception), "Handling the IncomingAssetOnBlockchainWaitingTransferenceAssetIssuerEvent", "Cannot cast this event");
            } catch(Exception exception){
                throw new CantSaveEventException(exception,"Handling the IncomingAssetOnBlockchainWaitingTransferenceAssetIssuerEvent", "Unexpected exception");
            }

        }else {
            throw new TransactionServiceNotStartedException();
        }

    }
}
