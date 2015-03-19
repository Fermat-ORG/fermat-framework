package com.bitdubai.fermat_core.layer._8_crypto.address_book.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._10_network_service.intra_user.IntraUser;
import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer._1_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer._2_os.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer._2_os.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventListener;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventSource;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventType;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.events.IncomingCryptoIdentifiedFromDeviceUserEvent;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.events.IncomingCryptoIdentifiedFromExtraUserEvent;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.events.IncomingCryptoIdentifiedFromIntraUserEvent;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.events.IncomingCryptoReceivedFromDeviceUserEvent;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.events.IncomingCryptoReceivedFromExtraUserEvent;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.events.IncomingCryptoReceivedFromIntraUserEvent;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.events.IncomingCryptoReceptionConfirmedFromDeviceUserEvent;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.events.IncomingCryptoReceptionConfirmedFromExtraUserEvent;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.events.IncomingCryptoReceptionConfirmedFromIntraUserEvent;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.events.IncomingCryptoReversedFromDeviceUserEvent;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.events.IncomingCryptoReversedFromExtraUserEvent;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.events.IncomingCryptoReversedFromIntraUserEvent;
import com.bitdubai.fermat_api.layer._2_os.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer._2_os.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer._5_user.device_user.DeviceUser;
import com.bitdubai.fermat_api.layer._8_crypto.Crypto;
import com.bitdubai.fermat_api.layer._8_crypto.address_book.AddressBookManager;
import com.bitdubai.fermat_api.layer._8_crypto.address_book.exceptions.ExampleException;
import com.bitdubai.fermat_core.layer._13_transaction.incoming_crypto.developer.bitdubai.version_1.event_handlers.IncomingCryptoIdentifiedEventHandler;
import com.bitdubai.fermat_core.layer._13_transaction.incoming_crypto.developer.bitdubai.version_1.event_handlers.IncomingCryptoReceivedEventHandler;
import com.bitdubai.fermat_core.layer._13_transaction.incoming_crypto.developer.bitdubai.version_1.event_handlers.IncomingCryptoReceptionConfimedEventHandler;
import com.bitdubai.fermat_core.layer._13_transaction.incoming_crypto.developer.bitdubai.version_1.event_handlers.IncomingCryptoReversedEventHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by loui on 20/02/15.
 */

/**
 * This Plug-in has the responsibility to manage the relationship between users and crypto addresses.
 *
 * * * * * *
 */

public class AddressBookCryptoPluginRoot implements Service, Crypto, AddressBookManager, DealsWithEvents, DealsWithErrors, DealsWithPluginFileSystem, DealsWithPluginDatabaseSystem, Plugin {


    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    List<EventListener> listenersAdded = new ArrayList<>();

    /**
     * DealWithEvents Interface member variables.
     */
    EventManager eventManager;

    /**
     * UsesDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;


    /**
     * UsesFileSystem Interface member variables.
     */
    PluginFileSystem pluginFileSystem;

    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    UUID pluginId;


    /**
     * Service Interface implementation.
     */

    @Override
    public void start() {
        /**
         * I will initialize the handling of com.bitdubai.platform events.
         */

        EventListener eventListener;
        EventHandler eventHandler;

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

        for (EventListener eventListener : listenersAdded) {
            eventManager.removeListener(eventListener);
        }

        listenersAdded.clear();
        this.serviceStatus = ServiceStatus.STOPPED;

    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }

    /**
     * Address Book Manager implementation. 
     */
    @Override
    public void exampleMethod() throws ExampleException {

    }
    
    /**
     * UsesFileSystem Interface implementation.
     */

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }



    /**
     * DealsWithPluginDatabaseSystem interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    

    /**
     * DealWithEvents Interface implementation.
     */

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }


    /**
     *DealWithErrors Interface implementation.
     */

    @Override
    public void setErrorManager(ErrorManager errorManager) {

    }


    /**
     * DealsWithPluginIdentity methods implementation.
     */

    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }




}
