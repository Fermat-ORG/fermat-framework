package com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces;


import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDenyActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.modules.ModuleSettingsImpl;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.exceptions.CantListCryptoCustomersException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantListIdentitiesToSelectException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantAcceptRequestException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantGetCryptoCustomerListException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.settings.CryptoCustomerCommunitySettings;
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

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by natalia on 16/09/15.
 * Modified by Alejandro Bicelis 08/02/16
 */

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_community.interfaces.CryptoCustomerModuleManager</code>
 * provides the methods for the Crypto Customer Community sub app, to Identity Management Customers and the relationship with other Customers.
 */
public interface CryptoCustomerCommunitySubAppModuleManager extends ModuleManager<CryptoCustomerCommunitySettings, ActiveActorIdentityInformation>,
        ModuleSettingsImpl<CryptoCustomerCommunitySettings>, Serializable {

    /**
     * The method <code>listWorldCryptoCustomers</code> returns the list of all crypto customers in the world,
     * setting their status (CONNECTED, for example) with respect to the selectedIdentity parameter
     * logged in crypto broker
     *
     * @return a list of all crypto customers in the world
     * @throws CantListCryptoCustomersException if something goes wrong.
     */
    List<CryptoCustomerCommunityInformation> listWorldCryptoCustomers(CryptoCustomerCommunitySelectableIdentity selectedIdentity, DeviceLocation deviceLocation, double distance, String alias, final int max, final int offset) throws CantListCryptoCustomersException;

    /**
     * The method <code>listSelectableIdentities</code> returns the list of all local broker identities on the device
     *
     * @return a list of all local broker identities on device
     * @throws CantListIdentitiesToSelectException if something goes wrong.
     */
    List<CryptoCustomerCommunitySelectableIdentity> listSelectableIdentities() throws CantListIdentitiesToSelectException;

    /**
     * The method <code>setSelectedActorIdentity</code> saves an identity as default
     */
    void setSelectedActorIdentity(CryptoCustomerCommunitySelectableIdentity identity);


    /**
     * The method <code>listCryptoBrokersPendingLocalAction</code> returns the list of crypto customers waiting to be accepted
     * or rejected by the logged user
     *
     * @return the list of crypto customers waiting to be accepted or rejected by the logged in user.
     * @throws CantGetCryptoCustomerListException if something goes wrong.
     */
    List<LinkedCryptoCustomerIdentity> listCryptoCustomersPendingLocalAction(final CryptoCustomerCommunitySelectableIdentity selectedIdentity,
                                                                             final int max,
                                                                             final int offset) throws CantGetCryptoCustomerListException;

    /**
     * The method <code>listAllConnectedCryptoCustomers</code> returns the list of all crypto customers registered by the
     * logged in user
     *
     * @return the list of crypto customers connected to the logged in user
     * @throws CantGetCryptoCustomerListException if something goes wrong.
     */
    List<CryptoCustomerCommunityInformation> listAllConnectedCryptoCustomers(final CryptoCustomerCommunitySelectableIdentity selectedIdentity,
                                                                             final int max,
                                                                             final int offset) throws CantGetCryptoCustomerListException;

    /**
     * The method <code>acceptCryptoCustomer</code> takes the information of a connection request, accepts
     * the request and adds the crypto customer to the list managed by this plugin with ContactState CONTACT.
     *
     * @param connectionId The id of the connection
     * @throws CantAcceptRequestException
     */
    void acceptCryptoCustomer(UUID connectionId) throws CantAcceptRequestException;


    /**
     * The method <code>denyConnection</code> denies a connection request from other crypto Customer
     *
     * @param connectionId the connection id of the user to deny its connection request
     * @throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CryptoCustomerConnectionRejectionFailedException
     */
    void denyConnection(UUID connectionId) throws CantDenyActorConnectionRequestException;


    /**
     * The method <code>listCryptoBrokersAvailablesToContact</code> searches for crypto Customer that the logged in
     * crypto customer could be interested to add.
     *
     * @return a list with information of crypto customer
     * @throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantGetCryptoCustomerListException
     */
    List<CryptoCustomerCommunityInformation> getSuggestionsToContact(int max, int offset) throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantGetCryptoCustomerListException;

    /**
     * The method <code>searchCryptoCustomer</code> gives us an interface to manage a search for a particular
     * crypto customer
     *
     * @return a searching interface
     */
    CryptoCustomerCommunitySearch getCryptoCustomerSearch();

    /**
     * The method <code>requestConnectionToCryptoBroker</code> initialize the request of contact between
     * a crypto Customer and a other crypto customer.
     *
     * @param cryptoCustomerToAddName      The name of the crypto customer to add
     * @param cryptoCustomerToAddPublicKey The public key of the crypto customer to add
     * @param profileImage                 The profile image that the crypto customer has
     * @throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantStartRequestException
     */
    void askCryptoCustomerForAcceptance(String cryptoCustomerToAddName, String cryptoCustomerToAddPublicKey, byte[] profileImage) throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantStartRequestException;


    /**
     * The method <code>disconnectCryptoCustomerr</code> disconnect an crypto Customer from the list managed by this
     * plugin
     *
     * @throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CryptoCustomerDisconnectingFailedException
     */
    void disconnectCryptoCustomer(final UUID requestId) throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CryptoCustomerDisconnectingFailedException;

    /**
     * The method <code>cancelCryptoCustomer</code> cancels an crypto Customer from the list managed by this
     *
     * @param cryptoCustomerToCancelPublicKey
     * @throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CryptoCustomerCancellingFailedException
     */
    void cancelCryptoBroker(String cryptoCustomerToCancelPublicKey) throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CryptoCustomerCancellingFailedException;

    /**
     * The method <code>getAllCryptoCustomers</code> returns the list of all crypto Customer registered by the
     * logged in crypto Customer
     *
     * @return the list of crypto Customer connected to the logged in broker
     * @throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantGetCryptoCustomerListException
     */
    List<CryptoCustomerCommunityInformation> getAllCryptoCustomers(int max, int offset) throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantGetCryptoCustomerListException;

    /**
     * The method <code>getCryptoCustomersWaitingYourAcceptance</code> returns the list of crypto Customer waiting to be accepted
     * or rejected by the logged in Customer
     *
     * @return the list of crypto Customer waiting to be accepted or rejected by the  logged in Customer
     * @throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantGetCryptoCustomerListException
     */
    List<CryptoCustomerCommunityInformation> getCryptoCustomersWaitingYourAcceptance(int max, int offset) throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantGetCryptoCustomerListException;

    /**
     * The method <code>listCryptoBrokersPendingRemoteAction</code> list the crypto Customer that haven't
     * answered to a sent connection request by the current logged in Customer.
     *
     * @return the list of crypto Customer that haven't answered to a sent connection request by the current
     * logged in Customer.
     * @throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantGetCryptoCustomerListException
     */
    List<CryptoCustomerCommunityInformation> getCryptoCustomersWaitingTheirAcceptance(int max, int offset) throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantGetCryptoCustomerListException;


    /**
     * The method <code>login</code> let an crypto Customer log in
     *
     * @param customerPublicKey the public key of the crypto Customer to log in
     */
    void login(String customerPublicKey) throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantLoginCustomerException;


    @Override
    SettingsManager<CryptoCustomerCommunitySettings> getSettingsManager();

    @Override
    CryptoCustomerCommunitySelectableIdentity getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException;

    @Override
    void createIdentity(String name, String phrase, byte[] profile_img) throws Exception;

    @Override
    void setAppPublicKey(String publicKey);

    @Override
    int[] getMenuNotifications();


    HashMap<String, Country> getCountryList() throws CantConnectWithExternalAPIException,
            CantCreateBackupFileException,
            CantCreateCountriesListException;

    List<CountryDependency> getCountryDependencies(String countryCode)
            throws CantGetCountryDependenciesListException,
            CantConnectWithExternalAPIException,
            CantCreateBackupFileException;

    List<City> getCitiesByCountryCode(String countryCode)
            throws CantGetCitiesListException;

    List<City> getCitiesByCountryCodeAndDependencyName(
            String countryName,
            String dependencyName)
            throws CantGetCitiesListException,
            CantCreateCountriesListException;

    GeoRectangle getGeoRectangleByLocation(String location)
            throws CantCreateGeoRectangleException;

    Address getAddressByCoordinate(float latitude, float longitude)
            throws CantCreateAddressException;

    GeoRectangle getRandomGeoLocation() throws CantCreateGeoRectangleException;

    List<ExtendedCity> getExtendedCitiesByFilter(String filter)
            throws CantGetCitiesListException;

}