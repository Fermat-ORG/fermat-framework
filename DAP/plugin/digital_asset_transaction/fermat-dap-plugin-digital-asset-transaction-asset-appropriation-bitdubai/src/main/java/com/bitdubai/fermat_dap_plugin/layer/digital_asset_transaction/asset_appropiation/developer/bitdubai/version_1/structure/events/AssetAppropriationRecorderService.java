package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantSaveEventException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantStartServiceException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.interfaces.AssetTransactionService;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.bitdubai.version_1.structure.database.AssetAppropriationDAO;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.bitdubai.version_1.structure.functional.AssetAppropriationVault;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 06/11/15.
 */
public class AssetAppropriationRecorderService implements AssetTransactionService {

    //VARIABLE DECLARATION
    private ServiceStatus serviceStatus;

    {
        serviceStatus = ServiceStatus.CREATED;
    }

    private final EventManager eventManager;
    private ErrorManager errorManager;
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;
    private List<FermatEventListener> listenersAdded;

    {
        listenersAdded = new ArrayList<>();
    }

    private final AssetAppropriationVault assetVault;
    //CONSTRUCTORS

    public AssetAppropriationRecorderService(UUID pluginId, EventManager eventManager, PluginDatabaseSystem pluginDatabaseSystem, AssetAppropriationVault assetVault) throws CantSetObjectException {
        this.pluginId = Validate.verifySetter(pluginId, "pluginId is null");
        this.eventManager = Validate.verifySetter(eventManager, "eventManager is null");
        this.pluginDatabaseSystem = Validate.verifySetter(pluginDatabaseSystem, "pluginDatabaseSystem is null");
        this.assetVault = Validate.verifySetter(assetVault, "assetVault is null");
    }


    //PUBLIC METHODS

    void receiveNewEvent(FermatEvent event) throws CantSaveEventException {
        String context = "pluginDatabaseSystem: " + pluginDatabaseSystem + " - pluginId: " + pluginId + " - event: " + event;

        try (AssetAppropriationDAO dao = new AssetAppropriationDAO(pluginDatabaseSystem, pluginId, assetVault)) {
            dao.saveNewEvent(event);
        } catch (DatabaseNotFoundException | CantOpenDatabaseException e) {
            throw new CantSaveEventException(e, context, CantSaveEventException.DEFAULT_MESSAGE);
        } catch (Exception e) {
            throw new CantSaveEventException(FermatException.wrapException(e), context, CantSaveEventException.DEFAULT_MESSAGE);
        }
    }

    @Override
    public void start() throws CantStartServiceException {
        String context = "PluginDatabaseSystem: " + pluginDatabaseSystem + " - Plugin ID: " + pluginId + " Event Manager: " + eventManager;

        try {

            //TODO CHANGE THESE EVENTS TO THE NEW ONES!!

            FermatEventListener onCryptoNetwork = eventManager.getNewListener(EventType.INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_USER);
            onCryptoNetwork.setEventHandler(new AssetAppropriationEventHandler(this));
            addListener(onCryptoNetwork);

            FermatEventListener onBlockChain = eventManager.getNewListener(EventType.INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_USER);
            onBlockChain.setEventHandler(new AssetAppropriationEventHandler(this));
            addListener(onBlockChain);


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
