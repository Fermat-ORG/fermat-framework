package com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_customer_community.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ActorConnectionNotFoundException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantAcceptActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDenyActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDisconnectFromActorException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantListActorConnectionsException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.UnexpectedConnectionStateException;
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
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_customer.interfaces.CryptoCustomerActorConnectionManager;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_customer.interfaces.CryptoCustomerActorConnectionSearch;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_customer.utils.CryptoCustomerActorConnection;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_customer.utils.CryptoCustomerLinkedActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.exceptions.CantListCryptoCustomersException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.interfaces.CryptoCustomerManager;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.utils.CryptoCustomerExposingData;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantListCryptoBrokerIdentitiesException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentityManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantListIdentitiesToSelectException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantAcceptRequestException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantGetCryptoCustomerListException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantGetCryptoCustomerSearchResult;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantLoginCustomerException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantStartRequestException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CryptoCustomerCancellingFailedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CryptoCustomerDisconnectingFailedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunitySearch;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunitySelectableIdentity;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunitySubAppModuleManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.LinkedCryptoCustomerIdentity;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.settings.CryptoCustomerCommunitySettings;
import com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_customer_community.developer.bitdubai.version_1.CryptoCustomerCommunitySubAppModulePluginRoot;
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
 * Created by Alejandro Bicelis on 2/2/2016.
 */

public class CryptoCustomerCommunityManager
        extends ModuleManagerImpl<CryptoCustomerCommunitySettings>
        implements CryptoCustomerCommunitySubAppModuleManager, Serializable {

    private final CryptoBrokerIdentityManager cryptoBrokerIdentityManager;
    private final CryptoCustomerActorConnectionManager cryptoCustomerActorConnectionManager;
    private final CryptoCustomerManager cryptoCustomerActorNetworkServiceManager;

    private final CryptoCustomerCommunitySubAppModulePluginRoot pluginRoot;
    private final GeolocationManager geolocationManager;

    private String subAppPublicKey;

    public CryptoCustomerCommunityManager(CryptoBrokerIdentityManager cryptoBrokerIdentityManager,
                                          CryptoCustomerActorConnectionManager cryptoCustomerActorConnectionManager,
                                          CryptoCustomerManager cryptoCustomerActorNetworkServiceManager,
                                          CryptoCustomerCommunitySubAppModulePluginRoot pluginRoot,
                                          PluginFileSystem pluginFileSystem,
                                          UUID pluginId,
                                          final GeolocationManager geolocationManager) {
        super(pluginFileSystem, pluginId);

        this.cryptoBrokerIdentityManager = cryptoBrokerIdentityManager;
        this.cryptoCustomerActorConnectionManager = cryptoCustomerActorConnectionManager;
        this.cryptoCustomerActorNetworkServiceManager = cryptoCustomerActorNetworkServiceManager;
        this.pluginRoot = pluginRoot;
        this.geolocationManager = geolocationManager;
    }


    @Override
    public List<CryptoCustomerCommunityInformation> listWorldCryptoCustomers(CryptoCustomerCommunitySelectableIdentity selectedIdentity, DeviceLocation deviceLocation, double distance, String alias, int max, int offset) throws CantListCryptoCustomersException {
        List<CryptoCustomerCommunityInformation> worldCustomerList;
        List<CryptoCustomerCommunityInformation> worldCustomerListLocation;
        List<CryptoCustomerActorConnection> actorConnections;

        try {
            worldCustomerList = getCryptoCustomerSearch().getResult(selectedIdentity.getPublicKey(), deviceLocation, distance, alias, max, offset);
        } catch (CantGetCryptoCustomerSearchResult e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoCustomersException(e, "", "Error in listWorldCryptoCustomers trying to list world customers");
        }


        try {

            final CryptoCustomerLinkedActorIdentity linkedActorIdentity = new CryptoCustomerLinkedActorIdentity(selectedIdentity.getPublicKey(), selectedIdentity.getActorType());
            final CryptoCustomerActorConnectionSearch search = cryptoCustomerActorConnectionManager.getSearch(linkedActorIdentity);

            actorConnections = search.getResult(1000, 0);

        } catch (final CantListActorConnectionsException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoCustomersException(e, "", "Error trying to list actor connections.");
        }

        for (int i = 0; i < worldCustomerList.size(); i++) {
            CryptoCustomerCommunityInformation worldCustomer = worldCustomerList.get(i);
            for (CryptoCustomerActorConnection connectedCustomer : actorConnections) {
                if (worldCustomer.getPublicKey().equals(connectedCustomer.getPublicKey()))
                    worldCustomerList.set(i, new CryptoCustomerCommunitySubAppModuleInformation(worldCustomer.getPublicKey(), worldCustomer.getAlias(), worldCustomer.getImage(), connectedCustomer.getConnectionState(), connectedCustomer.getConnectionId(), worldCustomer.getLocation(), worldCustomer.getProfileStatus()));
            }
        }

        for (int i = 0; i < worldCustomerList.size(); i++) {
            String country = "--", place = "--";
            CryptoCustomerCommunitySubAppModuleInformation customer = (CryptoCustomerCommunitySubAppModuleInformation) worldCustomerList.get(i);

            final Location location = customer.getLocation();
            try {
                final Address address = geolocationManager.getAddressByCoordinate(location.getLatitude(), location.getLongitude());
                country = address.getCountry();
                place = address.getCity().equals("null") ? address.getCounty() : address.getCity();
            } catch (CantCreateAddressException ignore) {
            }

            customer.setCountry(country);
            customer.setPlace(place);

            System.out.println(new StringBuilder().append("************** Actor Customer Register: ").append(customer.getAlias()).append(" - ").append(customer.getProfileStatus()).append(" - ").append(customer.getConnectionState()).toString());
        }

        return worldCustomerList;
    }

    @Override
    public List<CryptoCustomerCommunitySelectableIdentity> listSelectableIdentities() throws CantListIdentitiesToSelectException {
        try {

            final List<CryptoCustomerCommunitySelectableIdentity> selectableIdentities = new ArrayList<>();

            final List<CryptoBrokerIdentity> cryptoBrokerIdentities = cryptoBrokerIdentityManager.listIdentitiesFromCurrentDeviceUser();

            for (final CryptoBrokerIdentity cbi : cryptoBrokerIdentities)
                selectableIdentities.add(new CryptoCustomerCommunitySelectableIdentityImpl(cbi));

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
    public void setSelectedActorIdentity(CryptoCustomerCommunitySelectableIdentity identity) {
        //Try to get appSettings
        CryptoCustomerCommunitySettings appSettings;
        try {
            appSettings = loadAndGetSettings(this.subAppPublicKey);
        } catch (Exception e) {
            appSettings = new CryptoCustomerCommunitySettings();
        }

        //If appSettings exist, save identity
        if (identity.getPublicKey() != null) {
            appSettings.setLastSelectedIdentityPublicKey(identity.getPublicKey());
        }

        try {
            persistSettings(this.subAppPublicKey, appSettings);
        } catch (CantPersistSettingsException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }


    @Override
    public List<CryptoCustomerCommunityInformation> getSuggestionsToContact(int max, int offset) throws CantGetCryptoCustomerListException {
        return null;
    }

    @Override
    public CryptoCustomerCommunitySearch getCryptoCustomerSearch() {
        return new CryptoCustomerCommunitySubAppModuleCommunitySearch(cryptoCustomerActorNetworkServiceManager);
    }

    @Override
    public void askCryptoCustomerForAcceptance(String cryptoCustomerToAddName, String cryptoCustomerToAddPublicKey, byte[] profileImage) throws CantStartRequestException {

    }

    @Override
    public void acceptCryptoCustomer(UUID connectionId) throws CantAcceptRequestException {
        try {
            System.out.println(new StringBuilder().append("************* im accepting in module the request: ").append(connectionId).toString());
            this.cryptoCustomerActorConnectionManager.acceptConnection(connectionId);
        } catch (CantAcceptActorConnectionRequestException | ActorConnectionNotFoundException | UnexpectedConnectionStateException e) {
            throw new CantAcceptRequestException("", e, "", "");
        }
    }

    @Override
    public void denyConnection(UUID connectionId) throws CantDenyActorConnectionRequestException {
        try {
            this.cryptoCustomerActorConnectionManager.denyConnection(connectionId);
        } catch (CantDenyActorConnectionRequestException | ActorConnectionNotFoundException | UnexpectedConnectionStateException e) {
            throw new CantDenyActorConnectionRequestException("", e, "", "");
        }

    }

    @Override
    public void disconnectCryptoCustomer(final UUID requestId) throws CryptoCustomerDisconnectingFailedException {

        try {
            cryptoCustomerActorConnectionManager.disconnect(requestId);

        } catch (final CantDisconnectFromActorException | UnexpectedConnectionStateException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CryptoCustomerDisconnectingFailedException("", e, "", "Error trying to disconnect the actor connection.");
        } catch (final ActorConnectionNotFoundException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CryptoCustomerDisconnectingFailedException("", e, "", "Connection request not found.");
        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CryptoCustomerDisconnectingFailedException("", e, "", "Unhandled Exception.");
        }
    }

    @Override
    public void cancelCryptoBroker(String cryptoCustomerToCancelPublicKey) throws CryptoCustomerCancellingFailedException {

    }

    @Override
    public List<CryptoCustomerCommunityInformation> getAllCryptoCustomers(int max, int offset) throws CantGetCryptoCustomerListException {
        return null;
    }


    @Override
    public List<LinkedCryptoCustomerIdentity> listCryptoCustomersPendingLocalAction(final CryptoCustomerCommunitySelectableIdentity selectedIdentity,
                                                                                    final int max,
                                                                                    final int offset) throws CantGetCryptoCustomerListException {

        try {

            final CryptoCustomerLinkedActorIdentity linkedActorIdentity = new CryptoCustomerLinkedActorIdentity(
                    selectedIdentity.getPublicKey(),
                    selectedIdentity.getActorType()
            );

            final CryptoCustomerActorConnectionSearch search = cryptoCustomerActorConnectionManager.getSearch(linkedActorIdentity);
            search.addConnectionState(ConnectionState.PENDING_LOCALLY_ACCEPTANCE);
            final List<CryptoCustomerActorConnection> actorsWithPendingRequest = search.getResult(max, offset);

            search.resetFilters();
            search.addConnectionState(ConnectionState.CONNECTED);
            final List<CryptoCustomerActorConnection> connectedActors = search.getResult(max, offset);

            final HashMap<String, Boolean> connectedActorsPublicKeys = new HashMap<>();
            for (CryptoCustomerActorConnection connectedCryptoCustomer : connectedActors) {
                connectedActorsPublicKeys.put(connectedCryptoCustomer.getPublicKey(), true);
            }

            final Set<LinkedCryptoCustomerIdentity> filteredActorsWihPendingRequest = new LinkedHashSet<>();
            for (CryptoCustomerActorConnection actorWithPendingRequest : actorsWithPendingRequest) {
                if (connectedActorsPublicKeys.get(actorWithPendingRequest.getPublicKey()) != null)
                    acceptCryptoCustomer(actorWithPendingRequest.getConnectionId());
                else
                    filteredActorsWihPendingRequest.add(new LinkedCryptoCustomerIdentityImpl(actorWithPendingRequest));
            }


            return new ArrayList<>(filteredActorsWihPendingRequest);

        } catch (final CantListActorConnectionsException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetCryptoCustomerListException("", e, "", "Error trying to list actor connections.");
        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetCryptoCustomerListException("", e, "", "Unhandled Exception.");
        }
    }

    @Override
    public List<CryptoCustomerCommunityInformation> listAllConnectedCryptoCustomers(CryptoCustomerCommunitySelectableIdentity selectedIdentity, int max, int offset) throws CantGetCryptoCustomerListException {

        try {

            final CryptoCustomerLinkedActorIdentity linkedActorIdentity = new CryptoCustomerLinkedActorIdentity(
                    selectedIdentity.getPublicKey(),
                    selectedIdentity.getActorType()
            );

            final CryptoCustomerActorConnectionSearch search = cryptoCustomerActorConnectionManager.getSearch(linkedActorIdentity);

            search.addConnectionState(ConnectionState.CONNECTED);

            final List<CryptoCustomerActorConnection> connectedActors = search.getResult(max, offset);

            final Set<CryptoCustomerCommunityInformation> filteredConnectedActors = new LinkedHashSet<>();

            CryptoCustomerExposingData cryptoBrokerExposingData;
            CryptoCustomerCommunitySubAppModuleInformation cryptoCustomerCommunitySubAppModuleInformation;

            for (CryptoCustomerActorConnection connectedActor : connectedActors) {
                cryptoBrokerExposingData = getCryptoCustomerSearch().getResult(connectedActor.getPublicKey());
                if (cryptoBrokerExposingData != null) {
                    cryptoCustomerCommunitySubAppModuleInformation = new CryptoCustomerCommunitySubAppModuleInformation(connectedActor, cryptoBrokerExposingData.getLocation());
                } else {
                    cryptoCustomerCommunitySubAppModuleInformation = new CryptoCustomerCommunitySubAppModuleInformation(connectedActor, connectedActor.getLocation());
                }

                Location actorLocation = cryptoCustomerCommunitySubAppModuleInformation.getLocation();
                Address address;
                try {
                    address = geolocationManager.getAddressByCoordinate(actorLocation.getLatitude(), actorLocation.getLongitude());
                    cryptoCustomerCommunitySubAppModuleInformation.setCountry(address.getCountry());
                    cryptoCustomerCommunitySubAppModuleInformation.setPlace(address.getCity());
                } catch (CantCreateAddressException ex) {
//                    GeoRectangle geoRectangle = geolocationManager.getRandomGeoLocation();
//                    address = geolocationManager.getAddressByCoordinate(geoRectangle.getLatitude(), geoRectangle.getLongitude());
                    cryptoCustomerCommunitySubAppModuleInformation.setCountry("");
                    cryptoCustomerCommunitySubAppModuleInformation.setPlace("");
                }
                filteredConnectedActors.add(cryptoCustomerCommunitySubAppModuleInformation);

            }

            return new ArrayList<>(filteredConnectedActors);

        } catch (final CantListActorConnectionsException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetCryptoCustomerListException("", e, "", "Error trying to list actor connections.");
        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetCryptoCustomerListException("", e, "", "Unhandled Exception.");
        }
    }

    @Override
    public List<CryptoCustomerCommunityInformation> getCryptoCustomersWaitingYourAcceptance(int max, int offset) throws CantGetCryptoCustomerListException {
        return null;
    }

    @Override
    public List<CryptoCustomerCommunityInformation> getCryptoCustomersWaitingTheirAcceptance(int max, int offset) throws CantGetCryptoCustomerListException {
        return null;
    }

    @Override
    public void login(String customerPublicKey) throws CantLoginCustomerException {

    }

    @Override
    public CryptoCustomerCommunitySelectableIdentity getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {

        //Try to get appSettings
        CryptoCustomerCommunitySettings appSettings;
        try {
            appSettings = loadAndGetSettings(this.subAppPublicKey);
        } catch (Exception e) {
            return null;
        }

        //Get all broker identities on local device
        List<CryptoBrokerIdentity> brokerIdentitiesInDevice = new ArrayList<>();
        try {
            brokerIdentitiesInDevice = cryptoBrokerIdentityManager.listIdentitiesFromCurrentDeviceUser();
        } catch (CantListCryptoBrokerIdentitiesException e) { /*Do nothing*/ }

        //No registered users in device
        if (brokerIdentitiesInDevice.size() == 0)
            return null;

        //If appSettings exists, get its selectedActorIdentityPublicKey property
        String lastSelectedIdentityPublicKey = appSettings.getLastSelectedIdentityPublicKey();

        CryptoCustomerCommunitySelectableIdentityImpl selectedIdentity = null;
        if (lastSelectedIdentityPublicKey != null) {

            for (CryptoBrokerIdentity identity : brokerIdentitiesInDevice) {
                if (identity.getPublicKey().equals(lastSelectedIdentityPublicKey)) {
                    selectedIdentity = constructProfileCustomer(identity);
                    break;
                }
            }

            if (selectedIdentity == null)
                throw new ActorIdentityNotSelectedException("", null, "", "");

            return selectedIdentity;
        }

        return null;
    }

    private CryptoCustomerCommunitySelectableIdentityImpl constructProfileCustomer(CryptoBrokerIdentity identity) {

        return new CryptoCustomerCommunitySelectableIdentityImpl(
                identity.getPublicKey(),
                Actors.CBP_CRYPTO_BROKER,
                identity.getAlias(),
                identity.getProfileImage(),
                identity.getFrequency(),
                identity.getAccuracy());
    }

    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {

        String createdPublicKey;

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
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            return;
        }


        //Try to get appSettings
        CryptoCustomerCommunitySettings appSettings;
        try {
            appSettings = loadAndGetSettings(this.subAppPublicKey);
        } catch (Exception e) {
            appSettings = new CryptoCustomerCommunitySettings();
        }

        //If appSettings exist
        if (createdPublicKey != null)
            appSettings.setLastSelectedIdentityPublicKey(createdPublicKey);

        try {
            persistSettings(this.subAppPublicKey, appSettings);
        } catch (CantPersistSettingsException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }


    @Override
    public void setAppPublicKey(String publicKey) {
        this.subAppPublicKey = publicKey;
    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
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
        return geolocationManager.getCitiesByCountryCodeAndDependencyName(countryName, dependencyName);
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
}
