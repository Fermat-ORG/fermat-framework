package org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.structure.events;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;

import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantSaveEventException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantStartServiceException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.interfaces.AssetTransactionService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 23/10/15.
 */
public class RedeemPointRedemptionRecorderService implements DealsWithEvents, AssetTransactionService, DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

    //VARIABLE DECLARATION
    private ServiceStatus serviceStatus;

    {
        serviceStatus = ServiceStatus.CREATED;
    }

    private EventManager eventManager;

    private PluginDatabaseSystem pluginDatabaseSystem;
    private UUID pluginId;
    private List<FermatEventListener> listenersAdded;

    {
        listenersAdded = new ArrayList<>();
    }
    //CONSTRUCTORS

    public RedeemPointRedemptionRecorderService(EventManager eventManager,
                                                PluginDatabaseSystem pluginDatabaseSystem,
                                                UUID pluginId) throws CantSetObjectException {


        this.eventManager = Validate.verifySetter(eventManager, "eventManager is null");
        this.pluginDatabaseSystem = Validate.verifySetter(pluginDatabaseSystem, "pluginDatabaseSystem is null");
        this.pluginId = Validate.verifySetter(pluginId, "pluginId is null");
    }

    //PUBLIC METHODS

    void receiveNewEvent(FermatEvent event) throws CantSaveEventException {
        String context = "pluginDatabaseSystem: " + pluginDatabaseSystem + " - pluginId: " + pluginId + " - event: " + event;

        try {
            org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.structure.database.AssetRedeemPointRedemptionDAO rprDao = new org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.structure.database.AssetRedeemPointRedemptionDAO(pluginDatabaseSystem, pluginId);
            rprDao.saveNewEvent(event);
        } catch (DatabaseNotFoundException | CantOpenDatabaseException e) {
            throw new CantSaveEventException(e, context, CantSaveEventException.DEFAULT_MESSAGE);
        } catch (Exception e) {
            throw new CantSaveEventException(FermatException.wrapException(e), context, CantSaveEventException.DEFAULT_MESSAGE);
        }
    }


    @Override
    public void start() throws CantStartServiceException {

        FermatEventHandler handler = new RedeemPointRedemptionEventHandler(this);
        FermatEventListener fermatEventListener = eventManager.getNewListener(org.fermat.fermat_dap_api.layer.all_definition.enums.EventType.RECEIVE_NEW_DAP_MESSAGE);
        fermatEventListener.setEventHandler(handler);
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        fermatEventListener = eventManager.getNewListener(EventType.INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_REDEEM_POINT);
        fermatEventListener.setEventHandler(handler);
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        fermatEventListener = eventManager.getNewListener(EventType.INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_REDEEM_POINT);
        fermatEventListener.setEventHandler(handler);
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        fermatEventListener = eventManager.getNewListener(EventType.INCOMING_ASSET_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_REDEEM_POINT);
        fermatEventListener.setEventHandler(handler);
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        fermatEventListener = eventManager.getNewListener(EventType.INCOMING_ASSET_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_REDEMPTION);
        fermatEventListener.setEventHandler(handler);
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        addListener(fermatEventListener);
        serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        removeRegisteredListeners();
        serviceStatus = ServiceStatus.STOPPED;
    }

    //PRIVATE METHODS

    private void addListener(FermatEventListener listener) {
        eventManager.addListener(listener);
        listenersAdded.add(listener);
    }


    private void removeRegisteredListeners() {
        for (FermatEventListener fermatEventListener : listenersAdded) {
            eventManager.removeListener(fermatEventListener);
        }
        listenersAdded.clear();
    }

    //GETTER AND SETTERS
    @Override
    public ServiceStatus getStatus() {
        return serviceStatus;
    }

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    public List<FermatEventListener> getListenersAdded() {
        return Validate.verifyGetter(listenersAdded);
    }
    //INNER CLASSES

}
