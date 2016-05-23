package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_transfer.developer.version_1.structure.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.fermat.fermat_dap_api.layer.all_definition.enums.EventType;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantSaveEventException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantStartServiceException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.interfaces.AssetTransactionService;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_transfer.developer.version_1.structure.database.AssetTransferDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Víctor Mars (marsvicam@gmail.com) on 08/01/16.
 */
public class AssetTransferRecorderService implements DealsWithEvents, AssetTransactionService {
    /**
     * DealsWithEvents Interface member variables.
     */
    private EventManager eventManager;
    private List<FermatEventListener> listenersAdded = new ArrayList<>();
    private AssetTransferDAO assetDistributionDao;
    /**
     * TransactionService Interface member variables.
     */
    private ServiceStatus serviceStatus = ServiceStatus.CREATED;

    public AssetTransferRecorderService(AssetTransferDAO assetDistributionDao, EventManager eventManager) throws CantStartServiceException {
        try {
            setAssetTransferDAO(assetDistributionDao);
            setEventManager(eventManager);
        } catch (CantSetObjectException exception) {
            throw new CantStartServiceException(exception, "Cannot set the asset distribution database handler", "The database handler is null");
        }
    }

    private void setAssetTransferDAO(AssetTransferDAO assetDistributionDao) throws CantSetObjectException {
        if (assetDistributionDao == null) {
            throw new CantSetObjectException("The AssetIssuingDao is null");
        }
        this.assetDistributionDao = assetDistributionDao;
    }

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    void receiveNewEvent(FermatEvent event) throws CantSaveEventException {
        this.assetDistributionDao.saveNewEvent(event.getEventType().getCode(), event.getSource().getCode());
    }

    @Override
    public void start() throws CantStartServiceException {
        try {
            /**
             * I will initialize the handling of com.bitdubai.platform events.
             */
            FermatEventListener fermatEventListener;
            FermatEventHandler fermatEventHandler = new AssetTransferEventHandler(this);

            fermatEventListener = eventManager.getNewListener(EventType.RECEIVE_NEW_DAP_MESSAGE);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);


            this.serviceStatus = ServiceStatus.STARTED;
        } catch (Exception exception) {
            throw new CantStartServiceException(exception, "Starting the AssetDistributionRecorderService", "Registering the event listeners");
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
