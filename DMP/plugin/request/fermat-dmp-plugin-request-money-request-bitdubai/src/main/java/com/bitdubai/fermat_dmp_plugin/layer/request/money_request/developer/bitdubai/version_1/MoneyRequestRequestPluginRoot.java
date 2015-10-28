package com.bitdubai.fermat_dmp_plugin.layer.request.money_request.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.DiscoveryQueryParameters;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkService;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkServiceConnectionManager;
import com.bitdubai.fermat_api.layer.dmp_network_service.money_request.enums.CryptoRequestState;
import com.bitdubai.fermat_api.layer.dmp_network_service.money_request.exceptions.CantDeleteFromPendingCryptoRequestsException;
import com.bitdubai.fermat_api.layer.dmp_network_service.money_request.exceptions.CantGetPendingCryptoRequestsException;
import com.bitdubai.fermat_api.layer.dmp_network_service.money_request.exceptions.CantRejectRequestException;
import com.bitdubai.fermat_api.layer.dmp_network_service.money_request.exceptions.CantSendCryptoRequestException;
import com.bitdubai.fermat_api.layer.dmp_network_service.money_request.exceptions.CantSendMoneyRequestException;
import com.bitdubai.fermat_api.layer.dmp_network_service.money_request.interfaces.CryptoRequest;
import com.bitdubai.fermat_api.layer.dmp_network_service.money_request.interfaces.MoneyRequestNetworkServiceManager;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.IncomingMoneyRequestReceivedEvent;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.OutgoingMoneyRequestApprovedEvent;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.OutgoingMoneyRequestDeliveredEvent;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.OutgoingMoneyRequestRejectedEvent;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

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
    List<FermatEventListener> listenersAdded = new ArrayList<>();

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
        FermatEvent fermatEvent = eventManager.getNewEvent(EventType.INCOMING_MONEY_REQUEST_RECEIVED);
        ((IncomingMoneyRequestReceivedEvent) fermatEvent).setSource(EventSource.NETWORK_SERVICE_MONEY_REQUEST_PLUGIN);
        eventManager.raiseEvent(fermatEvent);

        FermatEvent fermatEvent_1 = eventManager.getNewEvent(EventType.OUTGOING_MONEY_REQUEST_DELIVERED);
        ((OutgoingMoneyRequestDeliveredEvent) fermatEvent).setSource(EventSource.NETWORK_SERVICE_MONEY_REQUEST_PLUGIN);
        eventManager.raiseEvent(fermatEvent);

        FermatEvent fermatEvent_2 = eventManager.getNewEvent(EventType.OUTGOING_MONEY_REQUEST_APPROVED);
        ((OutgoingMoneyRequestApprovedEvent) fermatEvent).setSource(EventSource.NETWORK_SERVICE_MONEY_REQUEST_PLUGIN);
        eventManager.raiseEvent(fermatEvent);

        FermatEvent fermatEvent_3 = eventManager.getNewEvent(EventType.OUTGOING_MONEY_REQUEST_REJECTED);
        ((OutgoingMoneyRequestRejectedEvent) fermatEvent).setSource(EventSource.NETWORK_SERVICE_MONEY_REQUEST_PLUGIN);
        eventManager.raiseEvent(fermatEvent);

    }

    /**
     * Service Interface implementation.
     */

    @Override
    public void start() {
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
         * I will remove the evnt listeners registered with the event manager. 
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

    @Override
    public PlatformComponentProfile getPlatformComponentProfilePluginRoot() {
        return null;
    }

    @Override
    public PlatformComponentType getPlatformComponentType() {
        return null;
    }

    @Override
    public NetworkServiceType getNetworkServiceType() {
        return null;
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

    @Override
    public DiscoveryQueryParameters constructDiscoveryQueryParamsFactory(PlatformComponentType platformComponentType, NetworkServiceType networkServiceType, String alias, String identityPublicKey, Location location, Double distance, String name, String extraData, Integer firstRecord, Integer numRegister, PlatformComponentType fromOtherPlatformComponentType, NetworkServiceType fromOtherNetworkServiceType) {
        return null;
    }

    /**
     * Handles the events CompleteComponentRegistrationNotification
     * @param platformComponentProfileRegistered
     */
    @Override
    public void handleCompleteComponentRegistrationNotificationEvent(PlatformComponentProfile platformComponentProfileRegistered) {

    }

    @Override
    public void handleFailureComponentRegistrationNotificationEvent(PlatformComponentProfile networkServiceApplicant, PlatformComponentProfile remoteParticipant) {

    }

    @Override
    public void handleCompleteRequestListComponentRegisteredNotificationEvent(List<PlatformComponentProfile> platformComponentProfileRegisteredList) {

    }


    /**
     * Handles the events CompleteRequestListComponentRegisteredNotificationEvent
     * @param remoteComponentProfile
     */
    @Override
    public void handleCompleteComponentConnectionRequestNotificationEvent(PlatformComponentProfile applicantComponentProfile, PlatformComponentProfile remoteComponentProfile) {

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
