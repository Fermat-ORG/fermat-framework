package com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1;

/**
 * Created by ciencias on 2/16/15.
 */

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_api.layer.dmp_transaction.incoming_intra_user.IncomingIntraUserManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventHandler;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventListener;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;

import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.event_handlers.IncomingCryptoIdentifiedFromIntraUserEventHandler;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.event_handlers.IncomingCryptoReceivedFromIntraUserEventHandler;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.event_handlers.IncomingCryptoReceptionConfirmedFromIntraUserEventHandler;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.event_handlers.IncomingCryptoReversedFromIntraUserEventHandler;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.event_handlers.MoneyReceivedEventHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * This plugin handles Intra User transactions, meaning transactions happening between users of the platform in both ends.
 * 
 * One of the reasons for this plugin to exist is that a user can send money to another without a payment request at all.
 * In this case when the transaction is received by the payed user, someone has to decide to which wallet to send it. 
 * 
 * As this plugin is  monitoring all User to User transactions, it is the one perfect for the job of deciding where to 
 * send the payment received.
 * 
 * It can also process queries of all such transactions that happened in the past. 
 * 
 * * * * * 
 */

public class IncomingIntraUserTransactionPluginRoot implements Service, IncomingIntraUserManager, DealsWithEvents, DealsWithErrors, DealsWithPluginFileSystem, Plugin {


    /**
     * PlatformService Interface member variables.
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
     * PlatformService Interface implementation.
     */


    @Override
    public void start() {
        /**
         * I will initialize the handling of com.bitdubai.platform events.
         */

        EventListener eventListener;
        EventHandler eventHandler;


        eventListener = eventManager.getNewListener(EventType.MONEY_RECEIVED);
        eventHandler = new MoneyReceivedEventHandler();
        ((MoneyReceivedEventHandler) eventHandler).setIncomingIntraUserManager(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);

        eventListener = eventManager.getNewListener(EventType.INCOMING_CRYPTO_IDENTIFIED_FROM_INTRA_USER);
        eventHandler = new IncomingCryptoIdentifiedFromIntraUserEventHandler();
        ((IncomingCryptoIdentifiedFromIntraUserEventHandler) eventHandler).setIncomingIntraUserManager(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);


        eventListener = eventManager.getNewListener(EventType.INCOMING_CRYPTO_RECEIVED_FROM_INTRA_USER);
        eventHandler = new IncomingCryptoReceivedFromIntraUserEventHandler();
        ((IncomingCryptoReceivedFromIntraUserEventHandler) eventHandler).setIncomingIntraUserManager(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);


        eventListener = eventManager.getNewListener(EventType.INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_INTRA_USER);
        eventHandler = new IncomingCryptoReceptionConfirmedFromIntraUserEventHandler();
        ((IncomingCryptoReceptionConfirmedFromIntraUserEventHandler) eventHandler).setIncomingIntraUserManager(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);

        eventListener = eventManager.getNewListener(EventType.INCOMING_CRYPTO_REVERSED_FROM_INTRA_USER);
        eventHandler = new IncomingCryptoReversedFromIntraUserEventHandler();
        ((IncomingCryptoReversedFromIntraUserEventHandler) eventHandler).setIncomingIntraUserManager(this);
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
     * Incoming Intra User Manager Interface implementation.
     */
    

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
