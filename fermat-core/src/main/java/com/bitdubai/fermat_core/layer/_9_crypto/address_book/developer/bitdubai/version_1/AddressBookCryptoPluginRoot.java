package com.bitdubai.fermat_core.layer._9_crypto.address_book.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._11_network_service.intra_user.IntraUser;
import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer._1_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer._2_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer._2_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.EventListener;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.EventSource;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.EventType;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.events.IncomingCryptoIdentifiedFromDeviceUserEvent;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.events.IncomingCryptoIdentifiedFromExtraUserEvent;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.events.IncomingCryptoIdentifiedFromIntraUserEvent;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.events.IncomingCryptoReceivedFromDeviceUserEvent;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.events.IncomingCryptoReceivedFromExtraUserEvent;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.events.IncomingCryptoReceivedFromIntraUserEvent;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.events.IncomingCryptoReceptionConfirmedFromDeviceUserEvent;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.events.IncomingCryptoReceptionConfirmedFromExtraUserEvent;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.events.IncomingCryptoReceptionConfirmedFromIntraUserEvent;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.events.IncomingCryptoReversedFromDeviceUserEvent;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.events.IncomingCryptoReversedFromExtraUserEvent;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.events.IncomingCryptoReversedFromIntraUserEvent;
import com.bitdubai.fermat_api.layer._3_os.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer._3_os.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer._5_user.device_user.DeviceUser;
import com.bitdubai.fermat_api.layer._9_crypto.Crypto;
import com.bitdubai.fermat_api.layer._9_crypto.address_book.AddressBookManager;
import com.bitdubai.fermat_api.layer._9_crypto.address_book.exceptions.ExampleException;
import com.bitdubai.fermat_core.layer._9_crypto.address_book.developer.bitdubai.version_1.event_handlers.IncomingCryptoIdentifiedEventHandler;
import com.bitdubai.fermat_core.layer._9_crypto.address_book.developer.bitdubai.version_1.event_handlers.IncomingCryptoReceivedEventHandler;
import com.bitdubai.fermat_core.layer._9_crypto.address_book.developer.bitdubai.version_1.event_handlers.IncomingCryptoReceptionConfimedEventHandler;
import com.bitdubai.fermat_core.layer._9_crypto.address_book.developer.bitdubai.version_1.event_handlers.IncomingCryptoReversedEventHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by loui on 20/02/15.
 */
public class AddressBookCryptoPluginRoot implements Service, Crypto, AddressBookManager, DealsWithEvents, DealsWithErrors, DealsWithPluginFileSystem, Plugin {

    /**
     * AddressBookPluginCryptoRoot Interface implementation.
     */

    public void getCryptoAddress(IntraUser intraUser) {}
    public void getCryptoAddress(/*ExtraUser extraUser*/){}
    public void getCryptoAddress(DeviceUser deviceUser){}

    public void getNewAddressForUser(CryptoCurrency cryptoCurrency /*, IntraUser intraUser*/){


    }

    public void saveAddress (/*IntraUser intraUser,*/ CryptoAddress cryptoAddress){


    }

    private void eventsToRaise(){

        PlatformEvent platformEvent = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_IDENTIFIED_FROM_EXTRA_USER);
        ((IncomingCryptoIdentifiedFromExtraUserEvent)platformEvent).setSource(EventSource.CRYPTO_ADDRESS_BOOK);
        eventManager.raiseEvent(platformEvent);

        PlatformEvent platformEvent_1 = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_IDENTIFIED_FROM_INTRA_USER);
        ((IncomingCryptoIdentifiedFromIntraUserEvent)platformEvent).setSource(EventSource.CRYPTO_ADDRESS_BOOK);
        eventManager.raiseEvent(platformEvent);

        PlatformEvent platformEvent_2 = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_IDENTIFIED_FROM_DEVICE_USER);
        ((IncomingCryptoIdentifiedFromDeviceUserEvent)platformEvent).setSource(EventSource.CRYPTO_ADDRESS_BOOK);
        eventManager.raiseEvent(platformEvent);

        PlatformEvent platformEvent_3 = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_RECEIVED_FROM_EXTRA_USER);
        ((IncomingCryptoReceivedFromExtraUserEvent)platformEvent).setSource(EventSource.CRYPTO_ADDRESS_BOOK);
        eventManager.raiseEvent(platformEvent);

        PlatformEvent platformEvent_4 = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_RECEIVED_FROM_INTRA_USER);
        ((IncomingCryptoReceivedFromIntraUserEvent)platformEvent).setSource(EventSource.CRYPTO_ADDRESS_BOOK);
        eventManager.raiseEvent(platformEvent);

        PlatformEvent platformEvent_5 = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_RECEIVED_FROM_DEVICE_USER);
        ((IncomingCryptoReceivedFromDeviceUserEvent)platformEvent).setSource(EventSource.CRYPTO_ADDRESS_BOOK);
        eventManager.raiseEvent(platformEvent);

        PlatformEvent platformEvent_6 = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_DEVICE_USER);
        ((IncomingCryptoReceptionConfirmedFromDeviceUserEvent)platformEvent).setSource(EventSource.CRYPTO_ADDRESS_BOOK);
        eventManager.raiseEvent(platformEvent);

        PlatformEvent platformEvent_7 = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_INTRA_USER);
        ((IncomingCryptoReceptionConfirmedFromIntraUserEvent)platformEvent).setSource(EventSource.CRYPTO_ADDRESS_BOOK);
        eventManager.raiseEvent(platformEvent);
        eventManager.raiseEvent(platformEvent);

        PlatformEvent platformEvent_8 = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_EXTRA_USER);
        ((IncomingCryptoReceptionConfirmedFromExtraUserEvent)platformEvent).setSource(EventSource.CRYPTO_ADDRESS_BOOK);
        eventManager.raiseEvent(platformEvent);
        eventManager.raiseEvent(platformEvent);

        PlatformEvent platformEvent_9 = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_REVERSED_FROM_INTRA_USER);
        ((IncomingCryptoReversedFromIntraUserEvent)platformEvent).setSource(EventSource.CRYPTO_ADDRESS_BOOK);
        eventManager.raiseEvent(platformEvent);

        PlatformEvent platformEvent_10 = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_REVERSED_FROM_EXTRA_USER);
        ((IncomingCryptoReversedFromExtraUserEvent)platformEvent).setSource(EventSource.CRYPTO_ADDRESS_BOOK);
        eventManager.raiseEvent(platformEvent);

        PlatformEvent platformEvent_11 = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_REVERSED_FROM_DEVICE_USER);
        ((IncomingCryptoReversedFromDeviceUserEvent)platformEvent).setSource(EventSource.CRYPTO_ADDRESS_BOOK);
        eventManager.raiseEvent(platformEvent);

    }

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

        eventListener = eventManager.getNewListener(EventType.INCOMING_CRYPTO_IDENTIFIED);
        eventHandler = new IncomingCryptoIdentifiedEventHandler();
        ((IncomingCryptoIdentifiedEventHandler) eventHandler).setAddressBookManager(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);
        
        eventListener = eventManager.getNewListener(EventType.INCOMING_CRYPTO_RECEIVED);
        eventHandler = new IncomingCryptoReceivedEventHandler();
        ((IncomingCryptoReceivedEventHandler) eventHandler).setAddressBookManager(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);

        eventListener = eventManager.getNewListener(EventType.INCOMING_CRYPTO_RECEPTION_CONFIRMED);
        eventHandler = new IncomingCryptoReceptionConfimedEventHandler();
        ((IncomingCryptoReceptionConfimedEventHandler) eventHandler).setAddressBookManager(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);


        eventListener = eventManager.getNewListener(EventType.INCOMING_CRYPTO_REVERSED);
        eventHandler = new IncomingCryptoReversedEventHandler();
        ((IncomingCryptoReversedEventHandler) eventHandler).setAddressBookManager(this);
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
