package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.AssetTransactionService;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.CantSaveEventException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.CantStartServiceException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.AssetIssuingTransactionDao;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.IncomingAssetOnCryptoNetworkWaitingTransferenceAssetIssuerEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 29/09/15.
 */
public class AssetIssuingRecorderService implements DealsWithEvents, AssetTransactionService {

    /**
     * DealsWithEvents Interface member variables.
     */
    private EventManager eventManager;
    private List<FermatEventListener> listenersAdded = new ArrayList<>();
    //Asset Issuing database registry
    AssetIssuingTransactionDao assetIssuingTransactionDao;
    /**
     * TransactionService Interface member variables.
     */
    private ServiceStatus serviceStatus = ServiceStatus.CREATED;

    public AssetIssuingRecorderService(AssetIssuingTransactionDao assetIssuingTransactionDao) throws CantStartServiceException {
        try {
            setAssetIssuingDao(assetIssuingTransactionDao);
        } catch (CantSetObjectException exception) {
            throw new CantStartServiceException(exception, "Cannot set the asset issuing database handler","The database handler is null");
        }
    }

    private void setAssetIssuingDao(AssetIssuingTransactionDao assetIssuingTransactionDao)throws CantSetObjectException{
        if(assetIssuingTransactionDao==null){
            throw new CantSetObjectException("The AssetIssuingDao is null");
        }
        this.assetIssuingTransactionDao=assetIssuingTransactionDao;
    }

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    public void incomingAssetOnCryptoNetworkWaitingTransferenceAssetIssuerEvent(IncomingAssetOnCryptoNetworkWaitingTransferenceAssetIssuerEvent event) throws CantSaveEventException {
        //TODO: SaveEventMethod in database
        //this.assetIssuingTransactionDao.saveNewEvent(event.getEventType().getCode(), event.getSource().getCode());
        //System.out.println("TTF - INCOMING CRYPTO EVENTRECORDER: NEW EVENT REGISTERED");
    }

    @Override
    public void start() throws CantStartServiceException {

    }

    @Override
    public void stop() {

    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }
}
