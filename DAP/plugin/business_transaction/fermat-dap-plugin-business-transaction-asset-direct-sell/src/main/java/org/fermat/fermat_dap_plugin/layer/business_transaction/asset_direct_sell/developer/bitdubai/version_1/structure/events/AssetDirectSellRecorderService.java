package org.fermat.fermat_dap_plugin.layer.business_transaction.asset_direct_sell.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import org.fermat.fermat_dap_api.layer.all_definition.util.Validate;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantSaveEventException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantStartServiceException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.interfaces.AssetTransactionService;
import org.fermat.fermat_dap_plugin.layer.business_transaction.asset_direct_sell.developer.bitdubai.version_1.structure.database.AssetDirectSellDAO;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Luis Torres (lutor1106@gmail.com") 16/03/16
 */
public class AssetDirectSellRecorderService implements AssetTransactionService {

    //VARIABLE DECLARATION
    private ServiceStatus serviceStatus;

    {
        serviceStatus = ServiceStatus.CREATED;
    }

    private final EventManager eventManager;
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;
    private final AssetDirectSellDAO assetDirectSellDAO;
    private List<FermatEventListener> listenersAdded;

    {
        listenersAdded = new ArrayList<>();
    }

    //CONSTRUCTORS

    public AssetDirectSellRecorderService(EventManager eventManager, PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId, AssetDirectSellDAO assetDirectSellDAO) {
        this.eventManager = eventManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.assetDirectSellDAO = assetDirectSellDAO;
    }

    //PUBLIC METHODS

    @Override
    public void start() throws CantStartServiceException {
        String context = "PluginDatabaseSystem: " + pluginDatabaseSystem + " - Plugin ID: " + pluginId + " Event Manager: " + eventManager;
        try {
            System.out.println("TEST START RECORDER SERVICE");
        } catch (Exception e) {
            throw new CantStartServiceException(e, context, "An unexpected exception happened while trying to start the AssetAppropriationRecordeService.");
        }
        serviceStatus = ServiceStatus.STARTED;
    }


    @Override
    public void stop() {
        removeRegisteredListeners();
        serviceStatus = ServiceStatus.STOPPED;
    }
    void receiveNewEvent(FermatEvent event) throws CantSaveEventException {
        String context = "pluginDatabaseSystem: " + pluginDatabaseSystem + " - pluginId: " + pluginId + " - event: " + event;

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

    public List<FermatEventListener> getListenersAdded() {
        return Validate.verifyGetter(listenersAdded);
    }


    //INNER CLASSES
}
