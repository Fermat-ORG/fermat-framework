package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantSaveEventException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantStartServiceException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.interfaces.AssetTransactionService;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.bitdubai.version_1.structure.database.UserRedemptionDao;
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
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 02/11/15.
 */
public class UserRedemptionRecorderService implements DealsWithEvents, AssetTransactionService {
    /**
     * DealsWithEvents Interface member variables.
     */
    private EventManager eventManager;
    private List<FermatEventListener> listenersAdded = new ArrayList<>();
    //Asset Issuing database registry
    UserRedemptionDao userRedemptionDao;
    /**
     * TransactionService Interface member variables.
     */
    private ServiceStatus serviceStatus = ServiceStatus.CREATED;

    public UserRedemptionRecorderService(UserRedemptionDao userRedemptionDao, EventManager eventManager) throws CantStartServiceException {
        try {
            userRedemptionDao(userRedemptionDao);
            setEventManager(eventManager);
        } catch (CantSetObjectException exception) {
            throw new CantStartServiceException(exception, "Cannot set the asset distribution database handler","The database handler is null");
        }
    }

    private void userRedemptionDao(UserRedemptionDao userRedemptionDao)throws CantSetObjectException{
        if(userRedemptionDao==null){
            throw new CantSetObjectException("The AssetIssuingDao is null");
        }
        this.userRedemptionDao=userRedemptionDao;
    }

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    public void incomingAssetOnCryptoNetworkWaitingTransferenceAssetUserEvent(IncomingAssetOnCryptoNetworkWaitingTransferenceAssetUserEvent event) throws CantSaveEventException {
        //Logger LOG = Logger.getGlobal();
        //LOG.info("EVENT TEST, I GOT AN EVENT:\n"+event);
        this.userRedemptionDao.saveNewEvent(event.getEventType().getCode(), event.getSource().getCode());
        //LOG.info("CHECK THE DATABASE");
    }

    public void incomingAssetOnBlockchainWaitingTransferenceAssetUserEvent(IncomingAssetOnBlockchainWaitingTransferenceAssetUserEvent event) throws CantSaveEventException {
        //Logger LOG = Logger.getGlobal();
        //LOG.info("EVENT TEST, I GOT AN EVENT:\n"+event);
        this.userRedemptionDao.saveNewEvent(event.getEventType().getCode(), event.getSource().getCode());
        //LOG.info("CHECK THE DATABASE");
    }

    public void incomingAssetReversedOnCryptoNetworkWaitingTransferenceAssetUserEvent(IncomingAssetReversedOnCryptoNetworkNetworkWaitingTransferenceAssetUserEvent event) throws CantSaveEventException {
        //Logger LOG = Logger.getGlobal();
        //LOG.info("EVENT TEST, I GOT AN EVENT:\n"+event);
        this.userRedemptionDao.saveNewEvent(event.getEventType().getCode(), event.getSource().getCode());
        //LOG.info("CHECK THE DATABASE");
    }

    public void incomingAssetReversedOnBlockchainWaitingTransferenceAssetUserEvent(IncomingAssetReversedOnBlockchainWaitingTransferenceAssetUserEvent event) throws CantSaveEventException {
        //Logger LOG = Logger.getGlobal();
        //LOG.info("EVENT TEST, I GOT AN EVENT:\n"+event);
        this.userRedemptionDao.saveNewEvent(event.getEventType().getCode(), event.getSource().getCode());
        //LOG.info("CHECK THE DATABASE");
    }

    public void receivedNewDigitalAssetMetadataNotificationrEvent(ReceivedNewDigitalAssetMetadataNotificationEvent event) throws CantSaveEventException {
        Logger LOG = Logger.getGlobal();
        //LOG.info("EVENT TEST, I GOT AN EVENT:\n"+event);
        this.userRedemptionDao.saveNewEvent(event.getEventType().getCode(), event.getSource().getCode());
        //LOG.info("CHECK THE DATABASE");
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
            ((IncomingAssetOnCryptoNetworkWaitingTransferenceAssetUserEventHandler) fermatEventHandler).setUserRedemptionRecorderService(this);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            fermatEventListener = eventManager.getNewListener(EventType.INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_USER);
            fermatEventHandler = new IncomingAssetOnBlockchainWaitingTransferenceAssetUserEventHandler();
            ((IncomingAssetOnBlockchainWaitingTransferenceAssetUserEventHandler) fermatEventHandler).setUserRedemptionRecorderService(this);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            fermatEventListener = eventManager.getNewListener(EventType.INCOMING_ASSET_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_USER);
            fermatEventHandler = new IncomingAssetReversedOnCryptoNetworkWaitingTransferenceAssetUserEventHandler();
            ((IncomingAssetReversedOnCryptoNetworkWaitingTransferenceAssetUserEventHandler) fermatEventHandler).setUserRedemptionRecorderService(this);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            fermatEventListener = eventManager.getNewListener(EventType.INCOMING_ASSET_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_USER);
            fermatEventHandler = new IncomingAssetReversedOnBlockchainWaitingTransferenceAssetUserEventHandler();
            ((IncomingAssetReversedOnBlockchainWaitingTransferenceAssetUserEventHandler) fermatEventHandler).setUserRedemptionRecorderService(this);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            fermatEventListener = eventManager.getNewListener(EventType.RECEIVED_NEW_DIGITAL_ASSET_METADATA_NOTIFICATION);
            fermatEventHandler = new ReceivedNewDigitalAssetMetadataNotificationEventHandler();
            ((ReceivedNewDigitalAssetMetadataNotificationEventHandler) fermatEventHandler).setUserRedemptionRecorderService(this);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            //Logger LOG = Logger.getGlobal();
            //LOG.info("ASSET ISSUING EVENT RECORDER STARTED");
            this.serviceStatus = ServiceStatus.STARTED;
        } catch (CantSetObjectException exception){
            throw new CantStartServiceException(exception,"Starting the UserRedemptionRecorderService", "The UserRedemptionRecorderService is probably null");
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
}
