package com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces;


import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.enums.Country;
import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_api.layer.core.MethodDetail;
import com.bitdubai.fermat_api.layer.modules.ModuleSettingsImpl;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.exceptions.CantGetDeviceLocationException;
import com.bitdubai.fermat_ccp_api.all_definition.enums.Frequency;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.CantCreateNewDeveloperException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.interfaces.IntraUserWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.interfaces.IntraWalletUserActor;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantDeleteIdentityException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantListIntraWalletUsersException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantUpdateIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetIntraUserConnectionStatusException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetIntraUsersListException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.IntraUserConnectionDenialFailedException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantConnectWithExternalAPIException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantCreateAddressException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantCreateBackupFileException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantCreateCountriesListException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantCreateGeoRectangleException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantGetCitiesListException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantGetCountryDependenciesListException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.Address;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.City;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.CountryDependency;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.ExtendedCity;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.GeoRectangle;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * The interface <code>IntraUserModuleManager</code>
 * provides the methods for the Intra Users sub app.
 */
public interface IntraUserModuleManager extends ModuleManager<IntraUserWalletSettings, ActiveActorIdentityInformation>,ModuleSettingsImpl<IntraUserWalletSettings> {


    /**
     * The method <code>createIntraUser</code> is used to create a new intra user
     *
     * @param intraUserName the name of the intra user to create
     * @param phrase the phrase of the intra user to create
     * @param profileImage  the profile image of the intra user to create
     * @return the login identity generated for the said intra user.
     * @throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CouldNotCreateIntraUserException
     */
     IntraUserLoginIdentity createIntraUser(String intraUserName, String phrase, byte[] profileImage,Location location) throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CouldNotCreateIntraUserException;

    /**
     *
     * @return
     * @throws CantGetDeviceLocationException
     */
    Location getLocationManager() throws CantGetDeviceLocationException;
    /**
     * The method <code>setProfileImage</code> let the current logged in intra user set its profile
     * picture.
     * @param image the profile picture to set
     * @throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantSaveProfileImageException
     */
     void setNewProfileImage(byte[] image, String intraUserPublicKey) throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantSaveProfileImageException;

    /**
     * The method <code>showAvailableLoginIdentities</code> lists the login identities that can be used
     * to log in as an Intra User for the current Device User.
     *
     * @return the list of identities the current Device User can use to log in
     * @throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantShowLoginIdentitiesException
     */
     List<IntraUserLoginIdentity> showAvailableLoginIdentities() throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantShowLoginIdentitiesException;

    /**
     * The method <code>login</code> let an intra user log in
     *
     * @param intraUserPublicKey the public key of the intra user to log in
     */
     void login(String intraUserPublicKey) throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantLoginIntraUserException;

    /**
     * The method <code>getSuggestionsToContact</code> searches for intra users that the logged in
     * intra user could be interested to add.
     *
     * @return a list with information of intra users
     * @throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetIntraUsersListException
     */
    @MethodDetail(looType = MethodDetail.LoopType.BACKGROUND,timeout = 30,timeoutUnit = TimeUnit.SECONDS)
    List<IntraUserInformation> getSuggestionsToContact(Location location, double distance, String alias,int max,int offset) throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetIntraUsersListException;


    /**
     * The method <code>getCacheSuggestionsToContact</code> get cache list of intra users that the logged in
     * intra user could be interested to add.
     *
     * @return a list with information of intra users
     * @throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetIntraUsersListException
     */

    @MethodDetail(looType = MethodDetail.LoopType.BACKGROUND,timeout = 30,timeoutUnit = TimeUnit.SECONDS)
    List<IntraUserInformation> getCacheSuggestionsToContact(int max,int offset) throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetIntraUsersListException;


    /**
     * The method <code>searchIntraUser</code> gives us an interface to manage a search for a particular
     * intra user
     *
     * @return a searching interface
     */
     IntraUserSearch searchIntraUser();

    /**
     * The method <code>askIntraUserForAcceptance</code> initialize the request of contact between
     * two intra users.
     *
     * @param intraUserToAddName      The name of the intra user to add
     * @param intraUserToAddPublicKey The public key of the intra user to add
     * @param OthersProfileImage      The profile image of the other intra user
     * @param MyProfileImage          The profile image of the logged intra user
     * @throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantStartRequestException
     */
     void askIntraUserForAcceptance(String intraUserToAddName, String intraUserPhrase,String intraUserToAddPublicKey, byte[] OthersProfileImage,byte[] MyProfileImage,String identityPublicKey,String identityAlias) throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantStartRequestException;

    /**
     * The method <code>acceptIntraUser</code> takes the information of a connection request, accepts
     * the request and adds the intra user to the list managed by this plugin with ContactState CONTACT.
     *
     * @param intraUserToAddName      The name of the intra user to add
     * @param intraUserToAddPublicKey The public key of the intra user to add
     * @param profileImage            The profile image that the intra user has
     * @throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantAcceptRequestException
     */
     void acceptIntraUser(String identityPublicKey,String intraUserToAddName, String intraUserToAddPublicKey, byte[] profileImage) throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantAcceptRequestException;


    /**
     * The method <code>denyConnection</code> denies a conection request from other intra user
     *
     * @param intraUserToRejectPublicKey the public key of the user to deny its connection request
     * @throws IntraUserConnectionDenialFailedException
     */
     void denyConnection(String intraUserLoggedPublicKey,String intraUserToRejectPublicKey) throws IntraUserConnectionDenialFailedException;

    /**
     * The method <code>disconnectIntraUSer</code> disconnect an intra user from the list managed by this
     * plugin
     *
     * @param intraUserToDisconnectPublicKey the public key of the intra user to disconnect
     * @throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.IntraUserDisconnectingFailedException
     */
     void disconnectIntraUSer(String intraUserLoggedPublicKey, String intraUserToDisconnectPublicKey) throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.IntraUserDisconnectingFailedException;

    /**
     * The method <code>cancelIntraUser</code> cancels an intra user from the list managed by this
     * @param intraUserToCancelPublicKey
     * @throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.IntraUserCancellingFailedException
     */
    void cancelIntraUser(String intraUserToCancelPublicKey) throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.IntraUserCancellingFailedException;

    /**
     * The method <code>getAllIntraUsers</code> returns the list of all intra users registered by the
     * logged in intra user
     *
     * @return the list of intra users connected to the logged in intra user
     * @throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetIntraUsersListException
     */
    @MethodDetail(looType = MethodDetail.LoopType.BACKGROUND,timeout = 40,timeoutUnit = TimeUnit.SECONDS)
     List<IntraUserInformation> getAllIntraUsers(String identityPublicKey,int max,int offset) throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetIntraUsersListException;

    /**
     * The method <code>getIntraUsersWaitingYourAcceptance</code> returns the list of intra users waiting to be accepted
     * or rejected by the logged in intra user
     *
     * @return the list of intra users waiting to be accepted or rejected by the  logged in intra user
     * @throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetIntraUsersListException
     */
    @MethodDetail(looType = MethodDetail.LoopType.BACKGROUND,timeout = 40,timeoutUnit = TimeUnit.SECONDS)
     List<IntraUserInformation> getIntraUsersWaitingYourAcceptance(String identityPublicKey,int max,int offset) throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetIntraUsersListException;

    /**
     * The method <code>getIntraUsersWaitingTheirAcceptance</code> list the intra users that haven't
     * answered to a sent connection request by the current logged in intra user.
     *
     * @return the list of intra users that haven't answered to a sent connection request by the current
     * logged in intra user.
     * @throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetIntraUsersListException
     */
     List<IntraUserInformation> getIntraUsersWaitingTheirAcceptance(String identityPublicKey,int max,int offset) throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetIntraUsersListException;

    /**
     *
     * @return active IntraUserLoginIdentity
     * @throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantShowLoginIdentitiesException
     */
     IntraUserLoginIdentity getActiveIntraUserIdentity() throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;

    /**
     * Count intra user waiting
     * @return
     */
     int getIntraUsersWaitingYourAcceptanceCount();


    /**
     * The method <code>updateIntraUserIdentity</code> change a identity information data
     * @param identityPublicKey
     * @param identityAlias
     * @param identityPhrase
     * @param profileImage
     * @throws CantUpdateIdentityException
     */
    void  updateIntraUserIdentity(String identityPublicKey, String identityAlias, String identityPhrase,byte[] profileImage, Long accuracy, Frequency frequency, Location location) throws CantUpdateIdentityException;


    /**
     *The method <code>deleteIntraUserIdentity</code> change identity status to inactive
     * @param identityPublicKey
     * @throws CantListIntraWalletUsersException
     */
    void  deleteIntraUserIdentity(String identityPublicKey) throws CantDeleteIdentityException;


    /**
     * The method <code>getIntraUsersConnectionStatus</code> return connection request status
     * @param intraUserConnectedPublicKey
     * @return ConnectionState object
     * @throws CantGetIntraUserConnectionStatusException
     */
    ConnectionState getIntraUsersConnectionStatus(String intraUserConnectedPublicKey) throws CantGetIntraUserConnectionStatusException;

    boolean isActorConnected(String publicKey) throws CantCreateNewDeveloperException;

    /**
     * The method <code>getLastNotification</code> get the last notification received by actor public key
     * @param intraUserConnectedPublicKey
     * @return IntraWalletUserActor notification object
     * @throws CantGetIntraUsersListException
     */

    IntraWalletUserActor getLastNotification(String intraUserConnectedPublicKey) throws CantGetIntraUsersListException;


    /**
     *
     * @param lstIntraUser
     * @throws CantGetIntraUsersListException
     */
    void saveCacheIntraUsersSuggestions(List<IntraUserInformation> lstIntraUser) throws CantGetIntraUsersListException;

    HashMap<String, Country> getCountryList() throws CantConnectWithExternalAPIException, CantCreateBackupFileException, CantCreateCountriesListException;


    List<CountryDependency> getCountryDependencies(String countryCode) throws CantGetCountryDependenciesListException, CantConnectWithExternalAPIException, CantCreateBackupFileException;


    List<City> getCitiesByCountryCode(String countryCode) throws CantGetCitiesListException;

    List<City> getCitiesByCountryCodeAndDependencyName(String countryName, String dependencyName) throws CantGetCitiesListException, CantCreateCountriesListException;

    GeoRectangle getGeoRectangleByLocation(String location) throws CantCreateGeoRectangleException;


    Address getAddressByCoordinate(double latitude, double longitude) throws CantCreateAddressException;

    GeoRectangle getRandomGeoLocation() throws CantCreateGeoRectangleException;

    Location getLocation() throws CantGetDeviceLocationException;

    List<ExtendedCity> getExtendedCitiesByFilter(String filter) throws CantGetCitiesListException;


    }
