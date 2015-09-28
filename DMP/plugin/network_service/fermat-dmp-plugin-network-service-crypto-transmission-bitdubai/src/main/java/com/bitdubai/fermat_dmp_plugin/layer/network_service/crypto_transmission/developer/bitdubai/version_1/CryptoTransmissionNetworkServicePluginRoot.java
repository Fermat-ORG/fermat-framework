package com.bitdubai.fermat_dmp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.components.DiscoveryQueryParameters;
import com.bitdubai.fermat_api.layer.all_definition.components.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.dmp_network_service.NetworkServiceConnectionManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_api.layer.dmp_network_service.NetworkService;
import com.bitdubai.fermat_api.layer.dmp_network_service.money.interfaces.MoneyNetworkServiceManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.MoneyReceivedEvent;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by natalia on 01/08/2015
 */

public class CryptoTransmissionNetworkServicePluginRoot implements Service, NetworkService, MoneyNetworkServiceManager, DealsWithEvents, DealsWithErrors, DealsWithPluginFileSystem, Plugin {

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    List<FermatEventListener> listenersAdded = new ArrayList<>();

    /**
     * DealWithEvents Interface member variables.
     */
    EventManager eventManager;
    ErrorManager errorManager;
    /**
     * UsesFileSystem Interface member variables.
     */
    PluginFileSystem pluginFileSystem;

    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    UUID pluginId;


    /**
     * MoneyPluginRoot Interface implementation.
     */

    public void sendMoney() {


    }

    private void moneyReceived() {

        FermatEvent fermatEvent = eventManager.getNewEvent(EventType.MONEY_RECEIVED);
        ((MoneyReceivedEvent) fermatEvent).setSource(EventSource.NETWORK_SERVICE_MONEY_PLUGIN);
        eventManager.raiseEvent(fermatEvent);

    }

    /**
     * Service Interface implementation.
     */

    @Override
    public void start() throws CantStartPluginException {
        /**
         * I will initialize the handling of platform events.
         */
        FermatEventListener fermatEventListener;
        FermatEventHandler fermatEventHandler;
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void pause() {
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        /**
         * I will remove all the event listeners registered with the event manager.
         */

        for (FermatEventListener fermatEventListener : listenersAdded) {
            eventManager.removeListener(fermatEventListener);
        }
        listenersAdded.clear();
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }


    /**
     * NetworkService Interface implementation.
     */

    @Override
    public UUID getId() {
        return this.pluginId;
    }

    @Override
    public List<PlatformComponentProfile> getRemoteNetworkServicesRegisteredList() {
        return null;
    }

    @Override
    public void requestRemoteNetworkServicesRegisteredList(DiscoveryQueryParameters discoveryQueryParameters) {

    }

    @Override
    public NetworkServiceConnectionManager getNetworkServiceConnectionManager() {
        return null;
    }


    /**
     * UsesFileSystem Interface implementation.
     */

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    /**
     * DealWithEvents Interface implementation.
     */

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }


    /**
     * DealWithErrors Interface implementation.
     */

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealsWithPluginIdentity methods implementation.
     */

    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }


}
