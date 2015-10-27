package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantSaveEventException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantStartServiceException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.interfaces.AssetTransactionService;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.bitdubai.version_1.structure.database.AssetReceptionDao;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.ReceivedNewDigitalAssetMetadataNotificationEvent;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/10/15.
 */
public class AssetReceptionRecorderService implements DealsWithEvents, AssetTransactionService {
    /**
     * DealsWithEvents Interface member variables.
     */
    private EventManager eventManager;
    private List<FermatEventListener> listenersAdded = new ArrayList<>();
    //Asset Issuing database registry
    AssetReceptionDao assetReceptionDao;
    /**
     * TransactionService Interface member variables.
     */
    private ServiceStatus serviceStatus = ServiceStatus.CREATED;

    public AssetReceptionRecorderService(AssetReceptionDao assetReceptionDao, EventManager eventManager) throws CantStartServiceException {
        try {
            setAssetReceptionDao(assetReceptionDao);
            setEventManager(eventManager);
        } catch (CantSetObjectException exception) {
            throw new CantStartServiceException(exception, "Cannot set the asset distribution database handler","The database handler is null");
        }
    }

    private void setAssetReceptionDao(AssetReceptionDao assetReceptionDao)throws CantSetObjectException{
        if(assetReceptionDao==null){
            throw new CantSetObjectException("The assetReceptionDao is null");
        }
        this.assetReceptionDao=assetReceptionDao;
    }

    public void receivedNewDigitalAssetMetadataNotificationEvent(ReceivedNewDigitalAssetMetadataNotificationEvent event) throws CantSaveEventException {
        Logger LOG = Logger.getGlobal();
        LOG.info("ASSET RECEPTION EVENT TEST, I GOT AN EVENT:\n"+event);
        this.assetReceptionDao.saveNewEvent(event.getEventType().getCode(), event.getSource().getCode());
        LOG.info("ASSET RECEPTION CHECK THE DATABASE");
    }

    @Override
    public void start() throws CantStartServiceException {
        //TODO: finish this
        try {
            /**
             * I will initialize the handling of com.bitdubai.platform events.
             */
            FermatEventListener fermatEventListener;
            FermatEventHandler fermatEventHandler;
            //TODO: change for the proper event
            //fermatEventListener = eventManager.getNewListener(EventType.INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_ISSUER);
            //fermatEventHandler = new IncomingAssetOnCryptoNetworkWaitingTransferenceAssetIssuerEventHandler();
            //((IncomingAssetOnCryptoNetworkWaitingTransferenceAssetIssuerEventHandler) fermatEventHandler).setAssetIssuingRecorderService(this);
            //fermatEventListener.setEventHandler(fermatEventHandler);
            //eventManager.addListener(fermatEventListener);
            //listenersAdded.add(fermatEventListener);

            fermatEventListener = eventManager.getNewListener(EventType.RECEIVED_NEW_DIGITAL_ASSET_METADATA_NOTIFICATION);
            fermatEventHandler = new ReceivedNewDigitalAssetMetadataNotificationEventHandler();
            ((ReceivedNewDigitalAssetMetadataNotificationEventHandler) fermatEventHandler).setAssetReceptionRecorderService(this);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);
            Logger LOG = Logger.getGlobal();
            LOG.info("ASSET RECEPTION EVENT RECORDER STARTED");
            this.serviceStatus=ServiceStatus.STARTED;
        } catch (CantSetObjectException exception){
            throw new CantStartServiceException(exception,"Starting the AssetReceptionRecorderService", "The AssetReceptionRecorderService is probably null");
        }
    }

    @Override
    public void stop() {
        removeRegisteredListeners();
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    private void removeRegisteredListeners(){
        for (FermatEventListener fermatEventListener : listenersAdded) {
            eventManager.removeListener(fermatEventListener);
        }
        listenersAdded.clear();
    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager=eventManager;
    }
}
