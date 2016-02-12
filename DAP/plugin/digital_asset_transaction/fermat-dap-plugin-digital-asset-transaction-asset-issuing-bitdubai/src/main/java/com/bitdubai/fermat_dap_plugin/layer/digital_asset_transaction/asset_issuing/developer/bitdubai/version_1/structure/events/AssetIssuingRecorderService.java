package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantSaveEventException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantStartServiceException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.interfaces.AssetTransactionService;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.AssetIssuingTransactionDao;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

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

    public AssetIssuingRecorderService(AssetIssuingTransactionDao assetIssuingTransactionDao, EventManager eventManager) throws CantStartServiceException {
        try {
            setAssetIssuingDao(assetIssuingTransactionDao);
            setEventManager(eventManager);
        } catch (CantSetObjectException exception) {
            throw new CantStartServiceException(exception, "Cannot set the asset issuing database handler", "The database handler is null");
        }
    }

    private void setAssetIssuingDao(AssetIssuingTransactionDao assetIssuingTransactionDao) throws CantSetObjectException {
        if (assetIssuingTransactionDao == null) {
            throw new CantSetObjectException("The AssetIssuingDao is null");
        }
        this.assetIssuingTransactionDao = assetIssuingTransactionDao;
    }

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    public void receiveNewEvent(FermatEvent event) throws CantSaveEventException {
        this.assetIssuingTransactionDao.saveNewEvent(event.getEventType().getCode(), event.getSource().getCode());
    }

    @Override
    public void start() throws CantStartServiceException {

        try {
            /**
             * I will initialize the handling of com.bitdubai.platform events.
             */

            AssetIssuingEventHandler handler = new AssetIssuingEventHandler(this);

            FermatEventListener fermatEventListener;
            fermatEventListener = eventManager.getNewListener(EventType.INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_ISSUER);
            fermatEventListener.setEventHandler(handler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            fermatEventListener = eventManager.getNewListener(EventType.INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_ISSUER);
            fermatEventListener.setEventHandler(handler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            fermatEventListener = eventManager.getNewListener(EventType.INCOMING_ASSET_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_ISSUER);
            fermatEventListener.setEventHandler(handler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            fermatEventListener = eventManager.getNewListener(EventType.INCOMING_ASSET_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_ISSUER);
            fermatEventListener.setEventHandler(handler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);
            this.serviceStatus = ServiceStatus.STARTED;
        } catch (Exception exception) {
            throw new CantStartServiceException(exception, "Starting the AssetIssuingRecorderService", "The AssetIssuingRecorderService is probably null");
        }

    }

    @Override
    public void stop() {
        removeRegisteredListeners();
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    private void removeRegisteredListeners() {
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
