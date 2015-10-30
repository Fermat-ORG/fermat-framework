package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantSaveEventException;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.IncomingAssetOnBlockchainWaitingTransferenceAssetUserEvent;


/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 18/10/15.
 */
public class IncomingAssetOnBlockchainWaitingTransferenceAssetUserEventHandler implements FermatEventHandler {
    AssetReceptionRecorderService assetReceptionRecorderService;

    public void setAssetReceptionRecorderService(AssetReceptionRecorderService assetReceptionRecorderService) throws CantSetObjectException {
        if (assetReceptionRecorderService == null) {
            throw new CantSetObjectException("assetReceptionRecorderService is null");
        }
        this.assetReceptionRecorderService = assetReceptionRecorderService;
    }

    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {

        if (this.assetReceptionRecorderService.getStatus() == ServiceStatus.STARTED) {

            try {
                this.assetReceptionRecorderService.incomingAssetOnBlockchainWaitingTransferenceAssetUserEvent((IncomingAssetOnBlockchainWaitingTransferenceAssetUserEvent) fermatEvent);
            } catch (CantSaveEventException exception) {
                throw new CantSaveEventException(exception, "Handling the IncomingAssetOnBlockchainWaitingTransferenceAssetUserEvent", "Check the cause");
            } catch (ClassCastException exception) {
                //Logger LOG = Logger.getGlobal();
                //LOG.info("EXCEPTION DETECTOR----------------------------------");
                //exception.printStackTrace();
                throw new CantSaveEventException(FermatException.wrapException(exception), "Handling the IncomingAssetOnBlockchainWaitingTransferenceAssetUserEvent", "Cannot cast this event");
            } catch (Exception exception) {
                throw new CantSaveEventException(exception, "Handling the IncomingAssetOnBlockchainWaitingTransferenceAssetUserEvent", "Unexpected exception");
            }

        } else {
            throw new TransactionServiceNotStartedException();
        }

    }
}
