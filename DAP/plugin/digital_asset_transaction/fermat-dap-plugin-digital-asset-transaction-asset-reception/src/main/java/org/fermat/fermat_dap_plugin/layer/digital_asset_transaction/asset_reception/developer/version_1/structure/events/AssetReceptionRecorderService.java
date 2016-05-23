package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.version_1.structure.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantSaveEventException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantStartServiceException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.interfaces.AssetTransactionService;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.version_1.structure.database.AssetReceptionDao;

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
            throw new CantStartServiceException(exception, "Cannot set the asset distribution database handler", "The database handler is null");
        }
    }

    private void setAssetReceptionDao(AssetReceptionDao assetReceptionDao) throws CantSetObjectException {
        if (assetReceptionDao == null) {
            throw new CantSetObjectException("The assetReceptionDao is null");
        }
        this.assetReceptionDao = assetReceptionDao;
    }

    void receiveNewEvent(FermatEvent event) throws CantSaveEventException {
        this.assetReceptionDao.saveNewEvent(event.getEventType().getCode(), event.getSource().getCode());
    }

    @Override
    public void start() throws CantStartServiceException {
        //TODO: finish this
        /**
         * I will initialize the handling of com.bitdubai.platform events.
         */
        FermatEventListener fermatEventListener;
        FermatEventHandler fermatEventHandler = new AssetReceptionEventHandler(this);
        fermatEventListener = eventManager.getNewListener(EventType.INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_USER);
        fermatEventListener.setEventHandler(fermatEventHandler);
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        fermatEventListener = eventManager.getNewListener(EventType.INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_USER);
        fermatEventListener.setEventHandler(fermatEventHandler);
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        fermatEventListener = eventManager.getNewListener(EventType.INCOMING_ASSET_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_USER);
        fermatEventListener.setEventHandler(fermatEventHandler);
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        fermatEventListener = eventManager.getNewListener(EventType.INCOMING_ASSET_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_USER);
        fermatEventListener.setEventHandler(fermatEventHandler);
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        fermatEventListener = eventManager.getNewListener(org.fermat.fermat_dap_api.layer.all_definition.enums.EventType.RECEIVE_NEW_DAP_MESSAGE);
        fermatEventListener.setEventHandler(fermatEventHandler);
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        Logger LOG = Logger.getGlobal();
        LOG.info("ASSET RECEPTION EVENT RECORDER STARTED");
        this.serviceStatus = ServiceStatus.STARTED;

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

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }
}
