package com.bidbubai.fermat_dap_plugin.layer.digital_asset_transaction.issuer_redemption.bitdubai.version_1.structure.events;

import com.bidbubai.fermat_dap_plugin.layer.digital_asset_transaction.issuer_redemption.bitdubai.version_1.structure.database.IssuerRedemptionDao;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantSaveEventException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantStartServiceException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.interfaces.AssetTransactionService;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.IncomingAssetOnBlockchainWaitingTransferenceAssetIssuerEvent;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.IncomingAssetOnCryptoNetworkWaitingTransferenceAssetIssuerEvent;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.IncomingAssetReversedOnBlockchainWaitingTransferenceAssetIssuerEvent;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.IncomingAssetReversedOnCryptoNetworkNetworkWaitingTransferenceAssetIssuerEvent;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 29/09/15.
 */
public class IssuerRedemptionRecorderService implements DealsWithEvents, AssetTransactionService {

    /**
     * DealsWithEvents Interface member variables.
     */
    private EventManager eventManager;
    private List<FermatEventListener> listenersAdded = new ArrayList<>();
    //Asset Issuing database registry
    IssuerRedemptionDao issuerRedemptionDao;
    /**
     * TransactionService Interface member variables.
     */
    private ServiceStatus serviceStatus = ServiceStatus.CREATED;

    public IssuerRedemptionRecorderService(IssuerRedemptionDao issuerRedemptionDao, EventManager eventManager) throws CantStartServiceException {
        try {
            setAssetIssuingDao(issuerRedemptionDao);
            setEventManager(eventManager);
        } catch (CantSetObjectException exception) {
            throw new CantStartServiceException(exception, "Cannot set the asset issuer redemption database handler","The database handler is null");
        }
    }

    private void setAssetIssuingDao(IssuerRedemptionDao issuerRedemptionDao)throws CantSetObjectException{
        if(issuerRedemptionDao==null){
            throw new CantSetObjectException("The AssetIssuingDao is null");
        }
        this.issuerRedemptionDao=issuerRedemptionDao;
    }

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    public void incomingAssetOnCryptoNetworkWaitingTransferenceAssetIssuerEvent(IncomingAssetOnCryptoNetworkWaitingTransferenceAssetIssuerEvent event) throws CantSaveEventException {
        //Logger LOG = Logger.getGlobal();
        //LOG.info("EVENT TEST, I GOT AN EVENT:\n"+event);
        this.issuerRedemptionDao.saveNewEvent(event.getEventType().getCode(), event.getSource().getCode());
        //LOG.info("CHECK THE DATABASE");
    }

    public void incomingAssetOnBlockchainWaitingTransferenceAssetIssuerEvent(IncomingAssetOnBlockchainWaitingTransferenceAssetIssuerEvent event) throws CantSaveEventException {
        //Logger LOG = Logger.getGlobal();
        //LOG.info("EVENT TEST, I GOT AN EVENT:\n"+event);
        this.issuerRedemptionDao.saveNewEvent(event.getEventType().getCode(), event.getSource().getCode());
        //LOG.info("CHECK THE DATABASE");
    }

    public void incomingAssetReversedOnBlockchainWaitingTransferenceAssetIssuerEvent(IncomingAssetReversedOnBlockchainWaitingTransferenceAssetIssuerEvent event) throws CantSaveEventException {
        //Logger LOG = Logger.getGlobal();
        //LOG.info("EVENT TEST, I GOT AN EVENT:\n"+event);
        this.issuerRedemptionDao.saveNewEvent(event.getEventType().getCode(), event.getSource().getCode());
        //LOG.info("CHECK THE DATABASE");
    }

    public void incomingAssetReversedOnCryptoNetworkWaitingTransferenceAssetIssuerEvent(IncomingAssetReversedOnCryptoNetworkNetworkWaitingTransferenceAssetIssuerEvent event) throws CantSaveEventException {
        //Logger LOG = Logger.getGlobal();
        //LOG.info("EVENT TEST, I GOT AN EVENT:\n"+event);
        this.issuerRedemptionDao.saveNewEvent(event.getEventType().getCode(), event.getSource().getCode());
        //LOG.info("CHECK THE DATABASE");
    }

    @Override
    public void start() throws CantStartServiceException {

        try{
            /**
             * I will initialize the handling of com.bitdubai.platform events.
             */
            FermatEventListener fermatEventListener;
            FermatEventHandler fermatEventHandler;
            fermatEventListener = eventManager.getNewListener(EventType.INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_ISSUER);
            fermatEventHandler = new IncomingAssetOnCryptoNetworkWaitingTransferenceAssetIssuerEventHandler();
            ((IncomingAssetOnCryptoNetworkWaitingTransferenceAssetIssuerEventHandler) fermatEventHandler).setIssuerRedemptionRecorderService(this);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            fermatEventListener = eventManager.getNewListener(EventType.INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_ISSUER);
            fermatEventHandler = new IncomingAssetOnBlockchainWaitingTransferenceAssetIssuerEventHandler();
            ((IncomingAssetOnBlockchainWaitingTransferenceAssetIssuerEventHandler) fermatEventHandler).setIssuerRedemptionRecorderService(this);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            fermatEventListener = eventManager.getNewListener(EventType.INCOMING_ASSET_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_ISSUER);
            fermatEventHandler = new IncomingAssetReversedOnBlockchainWaitingTransferenceAssetIssuerEventHandler();
            ((IncomingAssetReversedOnBlockchainWaitingTransferenceAssetIssuerEventHandler) fermatEventHandler).setIssuerRedemptionRecorderService(this);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            fermatEventListener = eventManager.getNewListener(EventType.INCOMING_ASSET_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_ISSUER);
            fermatEventHandler = new IncomingAssetReversedOnCryptoNetworkWaitingTransferenceAssetIssuerEventHandler();
            ((IncomingAssetReversedOnCryptoNetworkWaitingTransferenceAssetIssuerEventHandler) fermatEventHandler).setIssuerRedemptionRecorderService(this);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);
            //Logger LOG = Logger.getGlobal();
            //LOG.info("ASSET ISSUING EVENT RECORDER STARTED");
            this.serviceStatus=ServiceStatus.STARTED;
        } catch (CantSetObjectException exception){
            throw new CantStartServiceException(exception,"Starting the IssuerRedemptionRecorderService", "The IssuerRedemptionRecorderService is probably null");
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
