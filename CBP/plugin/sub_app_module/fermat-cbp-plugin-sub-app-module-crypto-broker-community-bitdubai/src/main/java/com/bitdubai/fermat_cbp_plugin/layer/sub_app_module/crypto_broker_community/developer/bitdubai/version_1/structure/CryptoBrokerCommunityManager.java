package com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_broker_community.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ActorConnectionNotFoundException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantAcceptActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantCancelActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDenyActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDisconnectFromActorException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantListActorConnectionsException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantRequestActorConnectionException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ConnectionAlreadyRequestedException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.UnexpectedConnectionStateException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.UnsupportedActorTypeException;
import com.bitdubai.fermat_api.layer.actor_connection.common.structure_common_classes.ActorIdentityInformation;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.GeoFrequency;
import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.modules.ModuleManagerImpl;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_api.layer.osa_android.location_system.exceptions.CantGetDeviceLocationException;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.interfaces.CryptoBrokerActorConnectionManager;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.interfaces.CryptoBrokerActorConnectionSearch;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.utils.CryptoBrokerActorConnection;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.utils.CryptoBrokerLinkedActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerManager;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.CryptoBrokerExposingData;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantListCryptoBrokerIdentitiesException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentityManager;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions.CantListCryptoCustomerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions.CantPublishIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions.IdentityNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentityManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.classes.CryptoBrokerCommunitySubAppModuleInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.ActorConnectionAlreadyRequestedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.ActorTypeNotSupportedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantAcceptRequestException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantGetCryptoBrokerSearchResult;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantListCryptoBrokersException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantListIdentitiesToSelectException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantRequestConnectionException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantValidateConnectionStateException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CryptoBrokerCancellingFailedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CryptoBrokerConnectionDenialFailedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CryptoBrokerDisconnectingFailedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunitySearch;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunitySelectableIdentity;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunitySubAppModuleManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.settings.CryptoBrokerCommunitySettings;
import com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_broker_community.developer.bitdubai.version_1.CryptoBrokerCommunitySubAppModulePluginRoot;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantConnectWithExternalAPIException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantCreateAddressException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantCreateBackupFileException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantCreateCountriesListException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantCreateGeoRectangleException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantGetCitiesListException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantGetCountryDependenciesListException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.Address;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.City;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.Country;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.CountryDependency;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.ExtendedCity;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.GeoRectangle;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.GeolocationManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;


/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_broker_community.developer.bitdubai.version_1.structure.CryptoBrokerCommunityManager</code>
 * bla bla bla.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 21/12/2015.
 */
public class CryptoBrokerCommunityManager
        extends ModuleManagerImpl<CryptoBrokerCommunitySettings>
        implements CryptoBrokerCommunitySubAppModuleManager, Serializable {

    private final CryptoBrokerIdentityManager cryptoBrokerIdentityManager;
    private final CryptoBrokerActorConnectionManager cryptoBrokerActorConnectionManager;
    private final CryptoBrokerManager cryptoBrokerActorNetworkServiceManager;
    private final CryptoCustomerIdentityManager cryptoCustomerIdentityManager;
    private final GeolocationManager geolocationManager;
    private final LocationManager locationManager;

    private String subAppPublicKey;

    private final CryptoBrokerCommunitySubAppModulePluginRoot pluginRoot;

    public CryptoBrokerCommunityManager(final CryptoBrokerIdentityManager cryptoBrokerIdentityManager,
                                        final CryptoBrokerActorConnectionManager cryptoBrokerActorConnectionManager,
                                        final CryptoBrokerManager cryptoBrokerActorNetworkServiceManager,
                                        final CryptoCustomerIdentityManager cryptoCustomerIdentityManager,
                                        final CryptoBrokerCommunitySubAppModulePluginRoot pluginRoot,
                                        final PluginFileSystem pluginFileSystem,
                                        final UUID pluginId,
                                        final GeolocationManager geolocationManager,
                                        LocationManager locationManager) {

        super(pluginFileSystem, pluginId);

        this.cryptoBrokerIdentityManager = cryptoBrokerIdentityManager;
        this.cryptoBrokerActorConnectionManager = cryptoBrokerActorConnectionManager;
        this.cryptoBrokerActorNetworkServiceManager = cryptoBrokerActorNetworkServiceManager;
        this.cryptoCustomerIdentityManager = cryptoCustomerIdentityManager;
        this.pluginRoot = pluginRoot;
        this.geolocationManager = geolocationManager;
        this.locationManager = locationManager;
    }


    @Override
    public List<CryptoBrokerCommunityInformation> listWorldCryptoBrokers(CryptoBrokerCommunitySelectableIdentity selectedIdentity, DeviceLocation deviceLocation, double distance, String alias, int max, int offset) throws CantListCryptoBrokersException {

        List<CryptoBrokerCommunityInformation> worldBrokerList;
        List<CryptoBrokerActorConnection> actorConnections;

        try {
            worldBrokerList = getCryptoBrokerSearch().getResult(selectedIdentity.getPublicKey(), deviceLocation, distance, alias, max, offset);
        } catch (CantGetCryptoBrokerSearchResult e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoBrokersException(e, "", "Error in listWorldCryptoBrokers trying to list world brokers");
        }


        try {

            final CryptoBrokerLinkedActorIdentity linkedActorIdentity = new CryptoBrokerLinkedActorIdentity(selectedIdentity.getPublicKey(), selectedIdentity.getActorType());
            final CryptoBrokerActorConnectionSearch search = cryptoBrokerActorConnectionManager.getSearch(linkedActorIdentity);

            actorConnections = search.getResult(1000, 0);  //actorConnections = search.getResult(max, offset);

        } catch (final CantListActorConnectionsException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoBrokersException(e, "", "Error trying to list actor connections.");
        }

        for (int i = 0; i < worldBrokerList.size(); i++) {
            CryptoBrokerCommunityInformation worldBroker = worldBrokerList.get(i);
            for (CryptoBrokerActorConnection connectedBroker : actorConnections) {
                if (worldBroker.getPublicKey().equals(connectedBroker.getPublicKey()))
                    worldBrokerList.set(i, new CryptoBrokerCommunitySubAppModuleInformation(
                            worldBroker.getPublicKey(),
                            worldBroker.getAlias(),
                            worldBroker.getImage(),
                            connectedBroker.getConnectionState(),
                            connectedBroker.getConnectionId(),
                            worldBroker.getLocation(),
                            worldBroker.getProfileStatus(),
                            worldBroker.getCryptoBrokerIdentityExtraData()));
            }
        }

        for (int i = 0; i < worldBrokerList.size(); i++) {
            String country = "--", place = "--";
            CryptoBrokerCommunitySubAppModuleInformation brokerActor = (CryptoBrokerCommunitySubAppModuleInformation) worldBrokerList.get(i);

            final Location location = brokerActor.getLocation();
            try {
                final Address address = geolocationManager.getAddressByCoordinate(location.getLatitude(), location.getLongitude());
                country = address.getCountry();
                place = address.getCity().equals("null") ? address.getCounty() : address.getCity();
            } catch (CantCreateAddressException ignore) {
            }

            brokerActor.setCountry(country);
            brokerActor.setPlace(place);

            System.out.println(new StringBuilder().append("************** Actor Broker Register: ").append(brokerActor.getAlias()).append(" - ").append(brokerActor.getProfileStatus()).append(" - ").append(brokerActor.getConnectionState()).toString());
        }

        return worldBrokerList;
    }

    /**
     * We are listing here all crypto brokers and all crypto customer identities found in the device.
     */
    @Override
    public List<CryptoBrokerCommunitySelectableIdentity> listSelectableIdentities() throws CantListIdentitiesToSelectException {

        try {

            final List<CryptoBrokerCommunitySelectableIdentity> selectableIdentities = new ArrayList<>();

            final List<CryptoBrokerIdentity> cryptoBrokerIdentities = cryptoBrokerIdentityManager.listIdentitiesFromCurrentDeviceUser();

            for (final CryptoBrokerIdentity cbi : cryptoBrokerIdentities)
                selectableIdentities.add(new CryptoBrokerCommunitySelectableIdentityImpl(cbi));

            final List<CryptoCustomerIdentity> cryptoCustomerIdentities = cryptoCustomerIdentityManager.listAllCryptoCustomerFromCurrentDeviceUser();

            for (final CryptoCustomerIdentity cci : cryptoCustomerIdentities)
                selectableIdentities.add(new CryptoBrokerCommunitySelectableIdentityImpl(cci));

            return selectableIdentities;

        } catch (final CantListCryptoBrokerIdentitiesException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListIdentitiesToSelectException(e, "", "Error in DAO trying to list identities.");
        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListIdentitiesToSelectException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public void setSelectedActorIdentity(CryptoBrokerCommunitySelectableIdentity identity) {

        //Try to get appSettings
        CryptoBrokerCommunitySettings appSettings;
        try {
            appSettings = loadAndGetSettings(this.subAppPublicKey);
        } catch (Exception e) {
            appSettings = new CryptoBrokerCommunitySettings();
        }

        if (identity.getPublicKey() != null)
            appSettings.setLastSelectedIdentityPublicKey(identity.getPublicKey());

        if (identity.getActorType() != null)
            appSettings.setLastSelectedActorType(identity.getActorType());

        try {
            persistSettings(this.subAppPublicKey, appSettings);
        } catch (CantPersistSettingsException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }

    @Override
    public CryptoBrokerCommunitySearch getCryptoBrokerSearch() {
        return new CryptoBrokerCommunitySubAppModuleCommunitySearch(cryptoBrokerActorNetworkServiceManager);
    }

    @Override
    public CryptoBrokerCommunitySearch searchConnectedCryptoBroker(CryptoBrokerCommunitySelectableIdentity selectedIdentity) {
        return null;
    }

    @Override
    public void requestConnectionToCryptoBroker(final CryptoBrokerCommunitySelectableIdentity selectedIdentity,
                                                final CryptoBrokerCommunityInformation cryptoBrokerToContact) throws CantRequestConnectionException,
            ActorConnectionAlreadyRequestedException,
            ActorTypeNotSupportedException {

        try {

            final ActorIdentityInformation actorSending = new ActorIdentityInformation(
                    selectedIdentity.getPublicKey(),
                    selectedIdentity.getActorType(),
                    selectedIdentity.getAlias(),
                    selectedIdentity.getImage()
            );

            final ActorIdentityInformation actorReceiving = new ActorIdentityInformation(
                    cryptoBrokerToContact.getPublicKey(),
                    Actors.CBP_CRYPTO_BROKER,
                    cryptoBrokerToContact.getAlias(),
                    cryptoBrokerToContact.getImage()
            );

            final Location location = cryptoBrokerToContact.getLocation();

            cryptoBrokerActorConnectionManager.requestConnection(
                    actorSending,
                    actorReceiving,
                    location
            );

        } catch (final CantRequestActorConnectionException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantRequestConnectionException(e, "", "Error trying to request the actor connection.");
        } catch (final UnsupportedActorTypeException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new ActorTypeNotSupportedException(e, "", "Actor type is not supported.");
        } catch (final ConnectionAlreadyRequestedException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new ActorConnectionAlreadyRequestedException(e, "", "Connection already requested.");
        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantRequestConnectionException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public void acceptCryptoBroker(final UUID requestId) throws CantAcceptRequestException, ConnectionRequestNotFoundException {

        try {

            cryptoBrokerActorConnectionManager.acceptConnection(requestId);

        } catch (final CantAcceptActorConnectionRequestException |
                UnexpectedConnectionStateException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantAcceptRequestException(e, "", "Error trying to accept the actor connection.");
        } catch (final ActorConnectionNotFoundException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new ConnectionRequestNotFoundException(e, "", "Connection already requested.");
        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantAcceptRequestException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public void denyConnection(final UUID requestId) throws CryptoBrokerConnectionDenialFailedException,
            ConnectionRequestNotFoundException {

        try {

            cryptoBrokerActorConnectionManager.denyConnection(requestId);

        } catch (final CantDenyActorConnectionRequestException |
                UnexpectedConnectionStateException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CryptoBrokerConnectionDenialFailedException(e, "", "Error trying to deny the actor connection.");
        } catch (final ActorConnectionNotFoundException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new ConnectionRequestNotFoundException(e, "", "Connection request not found.");
        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CryptoBrokerConnectionDenialFailedException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public void disconnectCryptoBroker(final UUID requestId) throws CryptoBrokerDisconnectingFailedException,
            ConnectionRequestNotFoundException {

        try {

            cryptoBrokerActorConnectionManager.disconnect(requestId);

        } catch (final CantDisconnectFromActorException |
                UnexpectedConnectionStateException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CryptoBrokerDisconnectingFailedException(e, "", "Error trying to disconnect the actor connection.");
        } catch (final ActorConnectionNotFoundException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new ConnectionRequestNotFoundException(e, "", "Connection request not found.");
        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CryptoBrokerDisconnectingFailedException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public void cancelCryptoBroker(final UUID requestId) throws CryptoBrokerCancellingFailedException, ConnectionRequestNotFoundException {

        try {

            cryptoBrokerActorConnectionManager.cancelConnection(requestId);

        } catch (final CantCancelActorConnectionRequestException |
                UnexpectedConnectionStateException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CryptoBrokerCancellingFailedException(e, "", "Error trying to disconnect the actor connection.");
        } catch (final ActorConnectionNotFoundException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new ConnectionRequestNotFoundException(e, "", "Connection request not found.");
        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CryptoBrokerCancellingFailedException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public List<CryptoBrokerCommunityInformation> listAllConnectedCryptoBrokers(final CryptoBrokerCommunitySelectableIdentity selectedIdentity,
                                                                                final int max,
                                                                                final int offset) throws CantListCryptoBrokersException {

        try {

            final CryptoBrokerLinkedActorIdentity linkedActorIdentity = new CryptoBrokerLinkedActorIdentity(
                    selectedIdentity.getPublicKey(),
                    selectedIdentity.getActorType()
            );


            final CryptoBrokerActorConnectionSearch search = cryptoBrokerActorConnectionManager.getSearch(linkedActorIdentity);

            search.addConnectionState(ConnectionState.CONNECTED);

            final List<CryptoBrokerActorConnection> connectedActors = search.getResult(max, offset);

            final Set<CryptoBrokerCommunityInformation> filteredConnectedActors = new LinkedHashSet<>();

            CryptoBrokerExposingData cryptoBrokerExposingData;
            CryptoBrokerCommunitySubAppModuleInformation cryptoBrokerCommunitySubAppModuleInformation;

            for (CryptoBrokerActorConnection connectedActor : connectedActors) {
                cryptoBrokerExposingData = getCryptoBrokerSearch().getResult(connectedActor.getPublicKey());
                if (cryptoBrokerExposingData != null) {

                    cryptoBrokerCommunitySubAppModuleInformation = new CryptoBrokerCommunitySubAppModuleInformation(connectedActor, cryptoBrokerExposingData);
                } else {
                    cryptoBrokerCommunitySubAppModuleInformation = new CryptoBrokerCommunitySubAppModuleInformation(connectedActor, connectedActor.getLocation());
                }

                Location actorLocation = cryptoBrokerCommunitySubAppModuleInformation.getLocation();

                try {
                    final Address address = geolocationManager.getAddressByCoordinate(actorLocation.getLatitude(), actorLocation.getLongitude());
                    cryptoBrokerCommunitySubAppModuleInformation.setCountry(address.getCountry());
                    cryptoBrokerCommunitySubAppModuleInformation.setPlace(address.getCity().equals("null") ? address.getCounty() : address.getCity());

                } catch (CantCreateAddressException ex) {
                    cryptoBrokerCommunitySubAppModuleInformation.setCountry("");
                    cryptoBrokerCommunitySubAppModuleInformation.setPlace("");
                }

                filteredConnectedActors.add(cryptoBrokerCommunitySubAppModuleInformation);
            }

            return new ArrayList<>(filteredConnectedActors);

        } catch (final CantListActorConnectionsException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoBrokersException(e, "", "Error trying to list actor connections.");
        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoBrokersException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public List<CryptoBrokerCommunityInformation> listCryptoBrokersPendingLocalAction(final CryptoBrokerCommunitySelectableIdentity selectedIdentity,
                                                                                      final int max,
                                                                                      final int offset) throws CantListCryptoBrokersException {

        try {

            final CryptoBrokerLinkedActorIdentity linkedActorIdentity = new CryptoBrokerLinkedActorIdentity(
                    selectedIdentity.getPublicKey(),
                    selectedIdentity.getActorType()
            );

            final CryptoBrokerActorConnectionSearch search = cryptoBrokerActorConnectionManager.getSearch(linkedActorIdentity);

            search.addConnectionState(ConnectionState.PENDING_LOCALLY_ACCEPTANCE);

            final List<CryptoBrokerActorConnection> actorConnections = search.getResult(max, offset);

            final List<CryptoBrokerCommunityInformation> cryptoBrokerCommunityInformationList = new ArrayList<>();

            for (CryptoBrokerActorConnection cbac : actorConnections)
                cryptoBrokerCommunityInformationList.add(new CryptoBrokerCommunitySubAppModuleInformation(cbac));

            return cryptoBrokerCommunityInformationList;

        } catch (final CantListActorConnectionsException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoBrokersException(e, "", "Error trying to list actor connections.");
        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoBrokersException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public List<CryptoBrokerCommunityInformation> listCryptoBrokersPendingRemoteAction(final CryptoBrokerCommunitySelectableIdentity selectedIdentity,
                                                                                       final int max,
                                                                                       final int offset) throws CantListCryptoBrokersException {
        try {

            final CryptoBrokerLinkedActorIdentity linkedActorIdentity = new CryptoBrokerLinkedActorIdentity(
                    selectedIdentity.getPublicKey(),
                    selectedIdentity.getActorType()
            );

            final CryptoBrokerActorConnectionSearch search = cryptoBrokerActorConnectionManager.getSearch(linkedActorIdentity);

            search.addConnectionState(ConnectionState.PENDING_REMOTELY_ACCEPTANCE);

            final List<CryptoBrokerActorConnection> actorConnections = search.getResult(max, offset);

            final List<CryptoBrokerCommunityInformation> cryptoBrokerCommunityInformationList = new ArrayList<>();

            for (CryptoBrokerActorConnection actorConnection : actorConnections)
                cryptoBrokerCommunityInformationList.add(new CryptoBrokerCommunitySubAppModuleInformation(actorConnection));

            return cryptoBrokerCommunityInformationList;

        } catch (final CantListActorConnectionsException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoBrokersException(e, "", "Error trying to list actor connections.");
        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoBrokersException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public int getCryptoBrokersWaitingYourAcceptanceCount() {
        return 0;
    }

    @Override
    public ConnectionState getActorConnectionState(String publicKey) throws CantValidateConnectionStateException {

        try {
            CryptoBrokerCommunitySelectableIdentity selectedIdentity = getSelectedActorIdentity();
            final CryptoBrokerLinkedActorIdentity linkedActorIdentity = new CryptoBrokerLinkedActorIdentity(selectedIdentity.getPublicKey(), selectedIdentity.getActorType());
            final CryptoBrokerActorConnectionSearch search = cryptoBrokerActorConnectionManager.getSearch(linkedActorIdentity);
            final List<CryptoBrokerActorConnection> actorConnections = search.getResult(Integer.MAX_VALUE, 0);

            for (CryptoBrokerActorConnection connection : actorConnections) {
                if (publicKey.equals(connection.getPublicKey()))
                    return connection.getConnectionState();
            }

        } catch (final CantListActorConnectionsException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantValidateConnectionStateException(e, "", "Error trying to list actor connections.");
        } catch (Exception e) {
        }

        return ConnectionState.DISCONNECTED_LOCALLY;
    }

    @Override
    public HashMap<String, Country> getCountryList() throws CantConnectWithExternalAPIException, CantCreateBackupFileException, CantCreateCountriesListException {
        return geolocationManager.getCountryList();
    }

    @Override
    public List<CountryDependency> getCountryDependencies(String countryCode) throws CantGetCountryDependenciesListException, CantConnectWithExternalAPIException, CantCreateBackupFileException {
        return geolocationManager.getCountryDependencies(countryCode);
    }

    @Override
    public List<City> getCitiesByCountryCode(String countryCode) throws CantGetCitiesListException {
        return geolocationManager.getCitiesByCountryCode(countryCode);
    }

    @Override
    public List<City> getCitiesByCountryCodeAndDependencyName(String countryName, String dependencyName) throws CantGetCitiesListException, CantCreateCountriesListException {
        return getCitiesByCountryCodeAndDependencyName(countryName, dependencyName);
    }

    @Override
    public GeoRectangle getGeoRectangleByLocation(String location) throws CantCreateGeoRectangleException {
        return geolocationManager.getGeoRectangleByLocation(location);
    }

    @Override
    public Address getAddressByCoordinate(float latitude, float longitude) throws CantCreateAddressException {
        return geolocationManager.getAddressByCoordinate(latitude, longitude);
    }

    @Override
    public GeoRectangle getRandomGeoLocation() throws CantCreateGeoRectangleException {
        return geolocationManager.getRandomGeoLocation();
    }

    @Override
    public List<ExtendedCity> getExtendedCitiesByFilter(String filter) throws CantGetCitiesListException {
        return geolocationManager.getExtendedCitiesByFilter(filter);
    }

    @Override
    public Location getLocation() throws CantGetDeviceLocationException {
        return locationManager.getLocation();
    }


    @Override
    public CryptoBrokerCommunitySelectableIdentity getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {

        //Try to get appSettings
        CryptoBrokerCommunitySettings appSettings;
        try {
            appSettings = loadAndGetSettings(this.subAppPublicKey);
        } catch (Exception e) {
            return null;
        }

        //Get all customer identities on local device
        List<CryptoCustomerIdentity> customerIdentitiesInDevice = new ArrayList<>();
        try {
            customerIdentitiesInDevice = cryptoCustomerIdentityManager.listAllCryptoCustomerFromCurrentDeviceUser();
        } catch (CantListCryptoCustomerIdentityException e) { /*Do nothing*/ }


        //Get all broker identities on local device
        List<CryptoBrokerIdentity> brokerIdentitiesInDevice = new ArrayList<>();
        try {
            brokerIdentitiesInDevice = cryptoBrokerIdentityManager.listIdentitiesFromCurrentDeviceUser();
        } catch (CantListCryptoBrokerIdentitiesException e) { /*Do nothing*/ }

        //No registered users in device
        if (customerIdentitiesInDevice.isEmpty() && brokerIdentitiesInDevice.isEmpty())
            return null;

        //If appSettings exists, get its selectedActorIdentityPublicKey property
        String lastSelectedIdentityPublicKey = appSettings.getLastSelectedIdentityPublicKey();
        Actors lastSelectedActorType = appSettings.getLastSelectedActorType();

        if (lastSelectedIdentityPublicKey != null && lastSelectedActorType != null) {

            CryptoBrokerCommunitySelectableIdentityImpl selectedIdentity = null;

            if (lastSelectedActorType == Actors.CBP_CRYPTO_BROKER) {
                for (CryptoBrokerIdentity identity : brokerIdentitiesInDevice) {
                    if (identity.getPublicKey().equals(lastSelectedIdentityPublicKey)) {
                        selectedIdentity = new CryptoBrokerCommunitySelectableIdentityImpl(identity);
                        break;
                    }
                }

            } else if (lastSelectedActorType == Actors.CBP_CRYPTO_CUSTOMER) {
                for (CryptoCustomerIdentity identity : customerIdentitiesInDevice) {
                    if (identity.getPublicKey().equals(lastSelectedIdentityPublicKey)) {
                        selectedIdentity = new CryptoBrokerCommunitySelectableIdentityImpl(identity);
                        break;
                    }
                }
            }

            if (selectedIdentity == null)
                throw new ActorIdentityNotSelectedException("", null, "", "");

            return selectedIdentity;
        }

        return null;
    }


    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {

        String createdPublicKey = null;

        if (name.equals("Customer")) {
            try {
                final CryptoCustomerIdentity createdIdentity = cryptoCustomerIdentityManager.createCryptoCustomerIdentity(name, profile_img, 0, GeoFrequency.NONE);
                createdPublicKey = createdIdentity.getPublicKey();

                new Thread() {
                    @Override
                    public void run() {
                        try {
                            cryptoCustomerIdentityManager.publishIdentity(createdIdentity.getPublicKey());
                        } catch (CantPublishIdentityException | IdentityNotFoundException e) {
                            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                        }
                    }
                }.start();

            } catch (Exception e) {
                this.pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                return;
            }
        } else if (name.equals("Broker")) {
            try {
                final CryptoBrokerIdentity createdIdentity = cryptoBrokerIdentityManager.createCryptoBrokerIdentity(name, profile_img, 0, GeoFrequency.NONE);
                createdPublicKey = createdIdentity.getPublicKey();

                new Thread() {
                    @Override
                    public void run() {
                        try {
                            cryptoBrokerIdentityManager.publishIdentity(createdIdentity.getPublicKey());
                        } catch (Exception e) {
                            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                        }
                    }
                }.start();
            } catch (Exception e) {
                this.pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                return;
            }
        }


        //Try to get appSettings
        CryptoBrokerCommunitySettings appSettings;
        try {
            appSettings = loadAndGetSettings(this.subAppPublicKey);
        } catch (Exception e) {
            appSettings = new CryptoBrokerCommunitySettings();
        }

        if (createdPublicKey != null)
            appSettings.setLastSelectedIdentityPublicKey(createdPublicKey);

        if (name.equals("Customer"))
            appSettings.setLastSelectedActorType(Actors.CBP_CRYPTO_CUSTOMER);

        else if (name.equals("Broker"))
            appSettings.setLastSelectedActorType(Actors.CBP_CRYPTO_BROKER);

        try {
            persistSettings(this.subAppPublicKey, appSettings);
        } catch (CantPersistSettingsException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }

    @Override
    public void setAppPublicKey(final String publicKey) {
        this.subAppPublicKey = publicKey;
    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }
}
