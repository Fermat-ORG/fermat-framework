package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.events;

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
    AssetDistributionRecorderService assetDistributionRecorderService;

    public void setAssetDistributionRecorderService(AssetDistributionRecorderService assetDistributionRecorderService) throws CantSetObjectException {
        if(assetDistributionRecorderService==null){
            throw new CantSetObjectException("assetDistributionRecorderService is null");
        }
        this.assetDistributionRecorderService=assetDistributionRecorderService;
    }
    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {

        if(this.assetDistributionRecorderService.getStatus()== ServiceStatus.STARTED) {

            try {
                this.assetDistributionRecorderService.incomingAssetOnBlockchainWaitingTransferenceAssetUserEvent((IncomingAssetOnBlockchainWaitingTransferenceAssetUserEvent) fermatEvent);
            } catch(CantSaveEventException exception){
                throw new CantSaveEventException(exception,"Handling the IncomingAssetOnBlockchainWaitingTransferenceAssetUserEvent", "Check the cause");
            } catch(ClassCastException exception){
                //Logger LOG = Logger.getGlobal();
                //LOG.info("EXCEPTION DETECTOR----------------------------------");
                //exception.printStackTrace();
                throw new CantSaveEventException(FermatException.wrapException(exception), "Handling the IncomingAssetOnBlockchainWaitingTransferenceAssetUserEvent", "Cannot cast this event");
            } catch(Exception exception){
                throw new CantSaveEventException(exception,"Handling the IncomingAssetOnBlockchainWaitingTransferenceAssetUserEvent", "Unexpected exception");
            }

        }else {
            throw new TransactionServiceNotStartedException();
        }

    }
}
