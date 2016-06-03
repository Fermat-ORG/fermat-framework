package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantSaveEventException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantStartServiceException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 9/03/16.
 */
public class AssetIssuingRecorderService {

    //VARIABLE DECLARATION
    private final EventManager eventManager;
    private final org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.database.AssetIssuingDAO assetIssuingDAO;

    private ServiceStatus serviceStatus = ServiceStatus.CREATED;
    private List<FermatEventListener> listenersAdded = new ArrayList<>();

    //CONSTRUCTORS

    public AssetIssuingRecorderService(EventManager eventManager, org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.database.AssetIssuingDAO assetIssuingDAO) {
        this.eventManager = eventManager;
        this.assetIssuingDAO = assetIssuingDAO;
    }

    //PUBLIC METHODS
    public void receiveNewEvent(FermatEvent event) throws CantSaveEventException {
        assetIssuingDAO.saveNewEvent(event);
    }

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

    //PRIVATE METHODS

    //GETTER AND SETTERS

    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }
    //INNER CLASSES
}
