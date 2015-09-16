package com.bitdubai.fermat_dmp_plugin.layer.request.money_request.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.event.EventSource;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_network_service.NetworkService;
import com.bitdubai.fermat_api.layer.dmp_network_service.money_request.enums.CryptoRequestState;
import com.bitdubai.fermat_api.layer.dmp_network_service.money_request.exceptions.CantDeleteFromPendingCryptoRequestsException;
import com.bitdubai.fermat_api.layer.dmp_network_service.money_request.exceptions.CantGetPendingCryptoRequestsException;
import com.bitdubai.fermat_api.layer.dmp_network_service.money_request.exceptions.CantRejectRequestException;
import com.bitdubai.fermat_api.layer.dmp_network_service.money_request.exceptions.CantSendCryptoRequestException;
import com.bitdubai.fermat_api.layer.dmp_network_service.money_request.exceptions.CantSendMoneyRequestException;
import com.bitdubai.fermat_api.layer.dmp_network_service.money_request.interfaces.CryptoRequest;
import com.bitdubai.fermat_api.layer.dmp_network_service.money_request.interfaces.MoneyRequestNetworkServiceManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.PlatformEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventHandler;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventListener;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.IncomingMoneyRequestReceivedEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.OutgoingMoneyRequestApprovedEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.OutgoingMoneyRequestDeliveredEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.OutgoingMoneyRequestRejectedEvent;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by loui on 23/02/15.
 */
public class MoneyRequestRequestPluginRoot implements Service, NetworkService, DealsWithEvents, DealsWithErrors, DealsWithPluginFileSystem, Plugin, MoneyRequestNetworkServiceManager {


    /**
     * Service Interface menber variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    List<EventListener> listenersAdded = new ArrayList<>();

    /**
     * PluginFileSystem interface member variables.
     */

    PluginFileSystem pluginFileSystem;


    /**
     * DealsWithEvents Interface menber variables.
     */

    EventManager eventManager;
    ErrorManager errorManager;
    /**
     * Plugin interface menber varuiables.
     */
    UUID pluginId;


    /**
     * MoneyRequestNetworkServicePluginRoot methods implementation.
     */

    public void sendMoneyRequest() {
        //TODO METODO NO IMPLEMENTADO AUN - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
    }


    public void events() {
        PlatformEvent platformEvent = eventManager.getNewEvent(EventType.INCOMING_MONEY_REQUEST_RECEIVED);
        ((IncomingMoneyRequestReceivedEvent) platformEvent).setSource(EventSource.NETWORK_SERVICE_MONEY_REQUEST_PLUGIN);
        eventManager.raiseEvent(platformEvent);

        PlatformEvent platformEvent_1 = eventManager.getNewEvent(EventType.OUTGOING_MONEY_REQUEST_DELIVERED);
        ((OutgoingMoneyRequestDeliveredEvent) platformEvent).setSource(EventSource.NETWORK_SERVICE_MONEY_REQUEST_PLUGIN);
        eventManager.raiseEvent(platformEvent);

        PlatformEvent platformEvent_2 = eventManager.getNewEvent(EventType.OUTGOING_MONEY_REQUEST_APPROVED);
        ((OutgoingMoneyRequestApprovedEvent) platformEvent).setSource(EventSource.NETWORK_SERVICE_MONEY_REQUEST_PLUGIN);
        eventManager.raiseEvent(platformEvent);

        PlatformEvent platformEvent_3 = eventManager.getNewEvent(EventType.OUTGOING_MONEY_REQUEST_REJECTED);
        ((OutgoingMoneyRequestRejectedEvent) platformEvent).setSource(EventSource.NETWORK_SERVICE_MONEY_REQUEST_PLUGIN);
        eventManager.raiseEvent(platformEvent);

    }

    /**
     * Service Interface implementation.
     */

    @Override
    public void start() {
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
         * I will remove the evnt listeners registered with the event manager. 
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
     * DealsWithErrors interface implementation.
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

    @Override
    public UUID getId() {
        return this.pluginId;
    }

    /*
     * MoneyRequestNetworkServiceManager Interface methods implementatio
     */

    @Override
    public List<CryptoRequest> getPendingReceivedCryptoRequests(String identityPublicKey) throws CantGetPendingCryptoRequestsException {
        //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
        return null;
    }

    @Override
    public CryptoRequestState getSentRequestState(UUID requestId) {
        //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
        return null;
    }

    @Override
    public void deleteFromPendingReceivedCryptoRequests(UUID requestId) throws CantDeleteFromPendingCryptoRequestsException {
        //TODO METODO NO IMPLEMENTADO AUN - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
    }

    @Override
    public void requestCrypto(UUID requestId, String receptorWalletPublicKey, CryptoAddress addressToSendThePayment, long cryptoAmount, String loggedInIntraUserPublicKey, String intraUserToSendRequestPublicKey, String description) throws CantSendCryptoRequestException {
        //TODO METODO NO IMPLEMENTADO AUN - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
    }

    @Override
    public void requestMoney(String receptorWalletPublicKey, String requestSenderPublicKey, String requestDestinationPublicKey, String requestDescription, CryptoAddress addressToSendThePayment, FiatCurrency fiatCurrency, long fiatAmount) throws CantSendMoneyRequestException {
        //TODO METODO NO IMPLEMENTADO AUN - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
    }


    @Override
    public void rejectRequest(UUID requestId, String intraUserThatSentTheRequestPublicKey) throws CantRejectRequestException {
        //TODO METODO NO IMPLEMENTADO AUN - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
    }
}
