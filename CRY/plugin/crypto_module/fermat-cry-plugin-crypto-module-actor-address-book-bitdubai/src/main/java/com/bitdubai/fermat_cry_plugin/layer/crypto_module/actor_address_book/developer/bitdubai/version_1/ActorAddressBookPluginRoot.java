package com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventListener;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer._2_os.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer._2_os.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer._5_user.device_user.DealsWithDeviceUsers;
import com.bitdubai.fermat_api.layer._5_user.device_user.DeviceUserManager;
import com.bitdubai.fermat_api.layer._5_user.extra_user.DealsWithExtraUsers;
import com.bitdubai.fermat_api.layer._5_user.extra_user.ExtraUserManager;
import com.bitdubai.fermat_api.layer._5_user.intra_user.DealsWithIntraUsers;
import com.bitdubai.fermat_api.layer._5_user.intra_user.IntraUserManager;
import com.bitdubai.fermat_api.layer.cry_3_crypto_module.Crypto;
import com.bitdubai.fermat_api.layer.cry_3_crypto_module.actor_address_book.ActorAddressBookManager;
import com.bitdubai.fermat_api.layer.cry_3_crypto_module.actor_address_book.exceptions.ExampleException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure.ActorAddressBook;

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

public class ActorAddressBookPluginRoot implements ActorAddressBookManager, Crypto,DealsWithDeviceUsers,DealsWithExtraUsers,DealsWithIntraUsers, DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem, DealsWithErrors, DealsWithEvents, Plugin, Service {

    /**
     * AddressBookManager Interface member variables.
     */
    private ActorAddressBook addressBook;

    /**
     * DealsWithDeviceUsers Interface member variables.
     */

    DeviceUserManager deviceUserManager;


    /**
     * DealsWithExtraUsers Interface member variables.
     */

    ExtraUserManager extraUserManager;

    /**
     * DealsWithIntraUsers Interface member variables.
     */


    IntraUserManager intraUserManager;
    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginFileSystem Interface member variables.
     */
    PluginFileSystem pluginFileSystem;

    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealWithEvents Interface member variables.
     */
    EventManager eventManager;


    /**
     * Plugin Interface member variables.
     */
    UUID pluginId;

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    List<EventListener> listenersAdded = new ArrayList<>();






    /**
     * Address Book Manager implementation. 
     */
    @Override
    public void exampleMethod() throws ExampleException {

    }

    /**
     * DealsWithDeviceUsers interface implementation.
     */

    @Override
    public void setDeviceUserManager(DeviceUserManager deviceUserManager){
        this.deviceUserManager = deviceUserManager;
    }

    /**
     * DealsWithExtraUsers interface implementation.
     */

    @Override
    public void setExtraUserManager(ExtraUserManager extraUserManager){
        this.extraUserManager = extraUserManager;
    }

    /**
     * DealsWithIntraUsers interface implementation.
     */

    @Override
    public void setIntraUserManager(IntraUserManager intraUserManager){
        this.intraUserManager = intraUserManager;
    }

    /**
     * DealsWithPluginDatabaseSystem interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * DealsWithPluginFileSystem Interface implementation.
     */

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    /**
     *DealWithErrors Interface implementation.
     */

    @Override
    public void setErrorManager(ErrorManager errorManager) {
            this.errorManager = errorManager;
    }

    /**
     * DealWithEvents Interface implementation.
     */

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }


    /**
     * Plugin methods implementation.
     */

    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    /**
     * Service Interface implementation.
     */

    @Override
    public void start() {
        /**
         * I will initialize the handling of com.bitdubai.platform events.
         */

        this.addressBook = new ActorAddressBook(this.pluginId,this.errorManager);

        this.addressBook.setDeviceUserManager(this.deviceUserManager);
        this.addressBook.setExtraUserManager(this.extraUserManager);
        this.addressBook.setIntraUserManager(this.intraUserManager);
        this.addressBook.setPluginDatabaseSystem(this.pluginDatabaseSystem);


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




}
