package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantSaveEventException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantStartServiceException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.interfaces.AssetTransactionService;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.bitdubai.version_1.AssetReceptionPluginRoot;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.bitdubai.version_1.structure.database.AssetReceptionDao;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.exceptions.CantGetLoggedInDeviceUserException;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.IncomingAssetOnBlockchainWaitingTransferenceAssetUserEvent;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.IncomingAssetOnCryptoNetworkWaitingTransferenceAssetUserEvent;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.IncomingAssetReversedOnBlockchainWaitingTransferenceAssetUserEvent;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.IncomingAssetReversedOnCryptoNetworkNetworkWaitingTransferenceAssetUserEvent;
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
    AssetReceptionPluginRoot assetReceptionPluginRoot;
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

    public void setAssetReceptionPluginRoot(AssetReceptionPluginRoot assetReceptionPluginRoot)throws CantSetObjectException{
        if(assetReceptionPluginRoot==null){
            throw new CantSetObjectException("The assetReceptionPluginRoot is null");
        }
        this.assetReceptionPluginRoot=assetReceptionPluginRoot;

    }

    public void receivedNewDigitalAssetMetadataNotificationEvent(ReceivedNewDigitalAssetMetadataNotificationEvent event) throws CantSaveEventException {
        //Logger LOG = Logger.getGlobal();
        //LOG.info("ASSET RECEPTION EVENT TEST, I GOT AN EVENT:\n"+event);
        startMonitorAgent();
        this.assetReceptionDao.saveNewEvent(event.getEventType().getCode(), event.getSource().getCode());
        //LOG.info("ASSET RECEPTION CHECK THE DATABASE");
    }

    public void incomingAssetOnCryptoNetworkWaitingTransferenceAssetUserEvent(IncomingAssetOnCryptoNetworkWaitingTransferenceAssetUserEvent event) throws CantSaveEventException {
        //Logger LOG = Logger.getGlobal();
        //LOG.info("EVENT TEST, I GOT AN EVENT:\n"+event);
        startMonitorAgent();
        this.assetReceptionDao.saveNewEvent(event.getEventType().getCode(), event.getSource().getCode());
        //LOG.info("CHECK THE DATABASE");
    }

    public void incomingAssetOnBlockchainWaitingTransferenceAssetUserEvent(IncomingAssetOnBlockchainWaitingTransferenceAssetUserEvent event) throws CantSaveEventException {
        //Logger LOG = Logger.getGlobal();
        //LOG.info("EVENT TEST, I GOT AN EVENT:\n"+event);
        startMonitorAgent();
        this.assetReceptionDao.saveNewEvent(event.getEventType().getCode(), event.getSource().getCode());
        //LOG.info("CHECK THE DATABASE");
    }

    public void incomingAssetReversedOnCryptoNetworkWaitingTransferenceAssetUserEvent(IncomingAssetReversedOnCryptoNetworkNetworkWaitingTransferenceAssetUserEvent event) throws CantSaveEventException {
        //Logger LOG = Logger.getGlobal();
        //LOG.info("EVENT TEST, I GOT AN EVENT:\n"+event);
        this.assetReceptionDao.saveNewEvent(event.getEventType().getCode(), event.getSource().getCode());
        //LOG.info("CHECK THE DATABASE");
    }

    public void incomingAssetReversedOnBlockchainWaitingTransferenceAssetUserEvent(IncomingAssetReversedOnBlockchainWaitingTransferenceAssetUserEvent event) throws CantSaveEventException {
        //Logger LOG = Logger.getGlobal();
        //LOG.info("EVENT TEST, I GOT AN EVENT:\n"+event);
        this.assetReceptionDao.saveNewEvent(event.getEventType().getCode(), event.getSource().getCode());
        //LOG.info("CHECK THE DATABASE");
    }

    private void startMonitorAgent() throws CantSaveEventException {
        try {
            this.assetReceptionPluginRoot.startMonitorAgent();
        } catch (CantGetLoggedInDeviceUserException exception) {
            throw new CantSaveEventException(exception, "Starting AssetReceptionMonitorAgent","Cannot get Logged in device user");
        } catch (CantSetObjectException exception) {
            throw new CantSaveEventException(exception, "Starting AssetReceptionMonitorAgent","Cannot set an object in AssetReceptionMonitorAgent");
        } catch (CantStartAgentException exception) {
            throw new CantSaveEventException(exception, "Starting AssetReceptionMonitorAgent","Cannot start AssetReceptionMonitorAgent");
        }
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
            fermatEventListener = eventManager.getNewListener(EventType.INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_USER);
            fermatEventHandler = new IncomingAssetOnCryptoNetworkWaitingTransferenceAssetUserEventHandler();
            ((IncomingAssetOnCryptoNetworkWaitingTransferenceAssetUserEventHandler) fermatEventHandler).setAssetReceptionRecorderService(this);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            fermatEventListener = eventManager.getNewListener(EventType.INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_USER);
            fermatEventHandler = new IncomingAssetOnBlockchainWaitingTransferenceAssetUserEventHandler();
            ((IncomingAssetOnBlockchainWaitingTransferenceAssetUserEventHandler) fermatEventHandler).setAssetReceptionRecorderService(this);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            fermatEventListener = eventManager.getNewListener(EventType.INCOMING_ASSET_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_USER);
            fermatEventHandler = new IncomingAssetReversedOnCryptoNetworkWaitingTransferenceAssetUserEventHandler();
            ((IncomingAssetReversedOnCryptoNetworkWaitingTransferenceAssetUserEventHandler) fermatEventHandler).setAssetReceptionRecorderService(this);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            fermatEventListener = eventManager.getNewListener(EventType.INCOMING_ASSET_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_USER);
            fermatEventHandler = new IncomingAssetReversedOnBlockchainWaitingTransferenceAssetUserEventHandler();
            ((IncomingAssetReversedOnBlockchainWaitingTransferenceAssetUserEventHandler) fermatEventHandler).setAssetReceptionRecorderService(this);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

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
