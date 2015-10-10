package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.DiscoveryQueryParameters;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkService;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkServiceConnectionManager;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.AddressExchangeRequestState;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantAcceptAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantConfirmAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantDenyAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantGetPendingAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantSendAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantListPendingAddressExchangeRequestsException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.PendingRequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.AddressExchangeRequest;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressesManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.database.CryptoAddressesNetworkServiceDao;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.exceptions.CantInitializeCryptoAddressesNetworkServiceDatabaseException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import java.util.List;
import java.util.UUID;

/**
 * This plugin manages the exchange of crypto addresses between actors.
 *
 * Created by Leon Acosta (laion.cj91@gmail.com) on 22/09/2015.
 */
public class CryptoAddressesNetworkServicePluginRoot implements CryptoAddressesManager, DealsWithErrors, DealsWithEvents, DealsWithPluginDatabaseSystem, NetworkService, Plugin, Service {

    /**
     * Service Interface member variables.
     */
    private ServiceStatus serviceStatus = ServiceStatus.CREATED;

    /**
     * DealWithEvents Interface member variables.
     */
    private ErrorManager errorManager;

    /**
     * DealWithEvents Interface member variables.
     */
    private EventManager eventManager;

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    private UUID pluginId;

    private CryptoAddressesNetworkServiceDao cryptoAddressesNetworkServiceDao;

    /**
     * Service Interface implementation.
     */
    @Override
    public void start() throws CantStartPluginException {

        try {

            cryptoAddressesNetworkServiceDao = new CryptoAddressesNetworkServiceDao(
                    pluginDatabaseSystem,
                    pluginId
            );

            cryptoAddressesNetworkServiceDao.initialize();

        } catch (CantInitializeCryptoAddressesNetworkServiceDatabaseException e) {

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_ADDRESSES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, e, "There is a problem when trying to initialize CryptoAddresses NetworkService DAO", null);
        } catch (Exception e) {

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_ADDRESSES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, e, "There is a problem I can't identify.", null);
        }

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
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }


    @Override
    public void sendAddressExchangeRequest(String                walletPublicKey        ,
                                           CryptoAddress         cryptoAddressToSend    ,
                                           Actors                actorTypeBy            ,
                                           Actors                actorTypeTo            ,
                                           String                requesterActorPublicKey,
                                           String                actorToRequestPublicKey,
                                           BlockchainNetworkType blockchainNetworkType  ) throws CantSendAddressExchangeRequestException {

        try {

            cryptoAddressesNetworkServiceDao.sendAddressExchangeRequest(
                    walletPublicKey,
                    cryptoAddressToSend,
                    actorTypeBy,
                    actorTypeTo,
                    requesterActorPublicKey,
                    actorToRequestPublicKey,
                    blockchainNetworkType
            );

        } catch (CantSendAddressExchangeRequestException e){

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_ADDRESSES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e){

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_ADDRESSES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantSendAddressExchangeRequestException(FermatException.wrapException(e), null, "Unhandled Exception.");
        }
    }

    @Override
    public void acceptAddressExchangeRequest(UUID          requestId            ,
                                             CryptoAddress cryptoAddressReceived) throws CantAcceptAddressExchangeRequestException,
                                                                                         PendingRequestNotFoundException          {

        try {

            // TODO send to the other part the acceptance with the crypto address

        } catch (Exception e){

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_ADDRESSES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantAcceptAddressExchangeRequestException(FermatException.wrapException(e), null, "Unhandled Exception.");
        }
    }

    @Override
    public List<AddressExchangeRequest> listPendingRequests(Actors                      actorType                  ,
                                                            AddressExchangeRequestState addressExchangeRequestState) throws CantListPendingAddressExchangeRequestsException {
        try {

            return cryptoAddressesNetworkServiceDao.listPendingRequests(
                    actorType                  ,
                    addressExchangeRequestState
            );

        } catch (CantListPendingAddressExchangeRequestsException e){

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_ADDRESSES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e){

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_ADDRESSES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListPendingAddressExchangeRequestsException(FermatException.wrapException(e), null, "Unhandled Exception.");
        }
    }

    @Override
    public AddressExchangeRequest getPendingRequest(UUID requestId) throws CantGetPendingAddressExchangeRequestException,
                                                                           PendingRequestNotFoundException              {

        try {

            return cryptoAddressesNetworkServiceDao.getPendingRequest(requestId);

        } catch (PendingRequestNotFoundException e){
            // when i don't find it i only pass the exception (maybe another plugin confirm the pending request).
            throw e;
        } catch (CantGetPendingAddressExchangeRequestException e){

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_ADDRESSES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e){

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_ADDRESSES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetPendingAddressExchangeRequestException(FermatException.wrapException(e), null, "Unhandled Exception.");
        }
    }

    @Override
    public void confirmAddressExchangeRequest(UUID requestId) throws CantConfirmAddressExchangeRequestException,
                                                                     PendingRequestNotFoundException           {

        try {

            cryptoAddressesNetworkServiceDao.confirmAddressExchangeRequest(requestId);

        } catch (CantConfirmAddressExchangeRequestException | PendingRequestNotFoundException e){
            // PendingRequestNotFoundException - THIS SHOULD' HAPPEN.
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_ADDRESSES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e){

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_ADDRESSES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantConfirmAddressExchangeRequestException(FermatException.wrapException(e), null, "Unhandled Exception.");
        }
    }

    @Override
    public void denyAddressExchangeRequest(UUID requestId) throws CantDenyAddressExchangeRequestException,
                                                                  PendingRequestNotFoundException        {

        try {

            // TODO send to the other part the denial
            cryptoAddressesNetworkServiceDao.confirmAddressExchangeRequest(requestId);

        } catch (PendingRequestNotFoundException e){
            // PendingRequestNotFoundException - THIS SHOULD' HAPPEN.
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_ADDRESSES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (CantConfirmAddressExchangeRequestException e){
            // PendingRequestNotFoundException - THIS SHOULD' HAPPEN.
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_ADDRESSES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantDenyAddressExchangeRequestException(FermatException.wrapException(e), null, "Can' confirm the request.");
        } catch (Exception e){

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_ADDRESSES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantDenyAddressExchangeRequestException(FermatException.wrapException(e), null, "Unhandled Exception.");
        }
    }


    /**
     * NetworkService Interface implementation.
     */
    @Override
    public UUID getId() {
        return pluginId;
    }

    @Override
    public PlatformComponentProfile getPlatformComponentProfile() {
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
    public void handleFailureComponentRegistrationNotificationEvent(PlatformComponentProfile networkServiceApplicant, DiscoveryQueryParameters discoveryQueryParameters) {

    }

    @Override
    public void handleCompleteRequestListComponentRegisteredNotificationEvent(List<PlatformComponentProfile> platformComponentProfileRegisteredList, DiscoveryQueryParameters discoveryQueryParameters) {

    }


    /**
     * Handles the events CompleteRequestListComponentRegisteredNotificationEvent
     * @param remoteComponentProfile
     */
    @Override
    public void handleCompleteComponentConnectionRequestNotificationEvent(PlatformComponentProfile remoteComponentProfile) {

    }

    /**
     * DealWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(final ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealWithEvents Interface implementation.
     */
    @Override
    public void setEventManager(final EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     * DealsWithPluginDatabaseSystem Interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(final PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * DealsWithPluginIdentity methods implementation.
     */
    @Override
    public void setId(final UUID pluginId) {
        this.pluginId = pluginId;
    }


}
