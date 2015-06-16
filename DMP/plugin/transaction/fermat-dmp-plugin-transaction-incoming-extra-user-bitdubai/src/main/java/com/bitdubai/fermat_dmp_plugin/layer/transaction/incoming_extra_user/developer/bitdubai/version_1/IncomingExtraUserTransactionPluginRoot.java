package com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1;

/**
 * Created by ciencias on 2/16/15.
 */

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._18_transaction.incoming_extra_user.IncomingExtraUserManager;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventListener;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventType;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.cry_3_crypto_module.actor_address_book.exceptions.ExampleException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.event_handlers.IncomingCryptoIdentifiedFromExtraUserEventHandler;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.event_handlers.IncomingCryptoReceivedFromExtraUserEventHandler;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.event_handlers.IncomingCryptoReceptionConfirmedFromExtraUserEventHandler;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.event_handlers.IncomingCryptoReversedFromExtraUserEventHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Incoming Extra User Transaction Manager Plugin is in charge of coordinating the transactions coming from outside the
 * system, meaning from people not a user of the platform.
 * 
 * This plugin knows which wallet to store the funds.
 * 
 * Usually a crypto address is generated from a particular wallet, and that payment should go there, but there is nothing
 * preventing a user to uninstall a wallet and discard the underlying structure in which the user interface was relaying.
 * 
 * For that reason it is necessary this middle man, to get sure any incoming payment for any wallet that ever existed is
 * not lost.
 * 
 * It can send the funds to a default wallet if some is defined or stored itself until the user manually release them.
 * 
 * It is also a centralized place where to query all of the incoming transaction from outside the system.
 *
 * 
 * * * * * * * 
 * * * 
 */

public class IncomingExtraUserTransactionPluginRoot implements Service, IncomingExtraUserManager, DealsWithEvents, DealsWithErrors, DealsWithPluginFileSystem, Plugin {


    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    List<EventListener> listenersAdded = new ArrayList<>();

    /**
     * UsesFileSystem Interface member variables.
     */
    PluginFileSystem pluginFileSystem;

    /**
     * DealWithEvents Interface member variables.
     */
    EventManager eventManager;

    /**
     * Plugin Interface member variables.
     */
    UUID pluginId;

    /**
     * Service Interface implementation.
     */


    @Override
    public void start() {
        /**
         * I will initialize the handling of platform events.
         */

        EventListener eventListener;
        EventHandler eventHandler;


        eventListener = eventManager.getNewListener(EventType.INCOMING_CRYPTO_IDENTIFIED_FROM_EXTRA_USER);
        eventHandler = new IncomingCryptoIdentifiedFromExtraUserEventHandler();
        ((IncomingCryptoIdentifiedFromExtraUserEventHandler) eventHandler).setIncomingExtraUserManager(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);


        eventListener = eventManager.getNewListener(EventType.INCOMING_CRYPTO_RECEIVED_FROM_EXTRA_USER);
        eventHandler = new IncomingCryptoReceivedFromExtraUserEventHandler();
        ((IncomingCryptoReceivedFromExtraUserEventHandler) eventHandler).setIncomingExtraUserManager(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);


        eventListener = eventManager.getNewListener(EventType.INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_EXTRA_USER);
        eventHandler = new IncomingCryptoReceptionConfirmedFromExtraUserEventHandler();
        ((IncomingCryptoReceptionConfirmedFromExtraUserEventHandler) eventHandler).setIncomingExtraUserManager(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);

        eventListener = eventManager.getNewListener(EventType.INCOMING_CRYPTO_REVERSED_FROM_EXTRA_USER);
        eventHandler = new IncomingCryptoReversedFromExtraUserEventHandler();
        ((IncomingCryptoReversedFromExtraUserEventHandler) eventHandler).setIncomingExtraUserManager(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);

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
    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }

    /**
     * IncomingExtraUserManager Interface implementation.
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
