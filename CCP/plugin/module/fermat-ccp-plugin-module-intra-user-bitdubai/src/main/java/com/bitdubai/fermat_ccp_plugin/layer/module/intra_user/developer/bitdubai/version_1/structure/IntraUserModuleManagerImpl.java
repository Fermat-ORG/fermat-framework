package com.bitdubai.fermat_ccp_plugin.layer.module.intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.core.MethodDetail;
import com.bitdubai.fermat_api.layer.modules.ModuleManagerImpl;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_api.layer.osa_android.location_system.exceptions.CantGetDeviceLocationException;
import com.bitdubai.fermat_ccp_api.all_definition.enums.Frequency;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.CantAcceptIntraWalletUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.CantCancelIntraWalletUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.CantCreateIntraWalletUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.CantCreateNewDeveloperException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.CantDenyConnectionException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.CantDisconnectIntraWalletUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.CantGetIntraUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.CantGetIntraUsersConnectedStateException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.CantGetIntraWalletUsersException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.interfaces.IntraUserWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.interfaces.IntraWalletUserActor;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.interfaces.IntraWalletUserActorManager;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantCreateNewIntraWalletUserException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantDeleteIdentityException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantListIntraWalletUsersException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantUpdateIdentityException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.RequestAlreadySendException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentity;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentityManager;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantAcceptRequestException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetIntraUserConnectionStatusException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetIntraUsersListException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantLoginIntraUserException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantSaveProfileImageException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantShowLoginIdentitiesException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantStartRequestException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CouldNotCreateIntraUserException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.IntraUserCancellingFailedException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.IntraUserConnectionDenialFailedException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.IntraUserDisconnectingFailedException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserLoginIdentity;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserSearch;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.ErrorSearchingCacheSuggestionsException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.ErrorSearchingSuggestionsException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.interfaces.IntraUserManager;
import com.bitdubai.fermat_ccp_plugin.layer.module.intra_user.developer.bitdubai.version_1.exceptions.CantLoadLoginsFileException;
import com.bitdubai.fermat_ccp_plugin.layer.module.intra_user.developer.bitdubai.version_1.utils.IntraUserSettings;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums.ProfileStatus;
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
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.GeolocationManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by mati on 2016.04.21..
 */
public class IntraUserModuleManagerImpl extends ModuleManagerImpl<IntraUserWalletSettings> implements IntraUserModuleManager {

    private static String INTRA_USER_LOGIN_FILE_NAME = "intraUsersLogin";
    private IntraWalletUserIdentity intraWalletUser;
    private IntraWalletUserIdentityManager intraWalletUserIdentityManager;
    private IntraWalletUserActorManager intraWalletUserManager;
    private IntraUserManager intraUserNertwokServiceManager;
    private ErrorManager errorManager;
    private String intraUserLoggedPublicKey;
    private String appPublicKey;
    private PluginTextFile intraUserLoginXml;
    private IntraUserSettings intraUserSettings = new IntraUserSettings();
    private LocationManager locationManager;

    private GeolocationManager geolocationManager;

    public IntraUserModuleManagerImpl(PluginFileSystem pluginFileSystem, UUID pluginId, PluginTextFile intraUserLoginXml, IntraWalletUserIdentity intraWalletUser, IntraWalletUserIdentityManager intraWalletUserIdentityManager, IntraWalletUserActorManager intraWalletUserManager, IntraUserManager intraUserNertwokServiceManager, ErrorManager errorManager, String intraUserLoggedPublicKey,LocationManager locationManager,GeolocationManager geolocationManager) {
        super(pluginFileSystem, pluginId);
        this.intraUserLoginXml = intraUserLoginXml;
        this.intraWalletUser = intraWalletUser;
        this.intraWalletUserIdentityManager = intraWalletUserIdentityManager;
        this.intraWalletUserManager = intraWalletUserManager;
        this.intraUserNertwokServiceManager = intraUserNertwokServiceManager;
        this.errorManager = errorManager;
        this.intraUserLoggedPublicKey = intraUserLoggedPublicKey;
        this.locationManager = locationManager;
        this.geolocationManager = geolocationManager;
    }

    /**
     * That method is used to create a new intra user
     *
     * @param intraUserName the name of the intra user to create
     * @param profileImage  the profile image of the intra user to create
     * @return the login identity generated for the said intra user.
     * @throws CouldNotCreateIntraUserException
     */
    @Override
    public IntraUserLoginIdentity createIntraUser(String intraUserName, String phrase, byte[] profileImage, Location location) throws CouldNotCreateIntraUserException {

        try {
            this.intraWalletUser = this.intraWalletUserIdentityManager.createNewIntraWalletUser(intraUserName, phrase, profileImage,Long.parseLong("100"), Frequency.NORMAL,location);


            return new IntraUserModuleLoginIdentity(intraWalletUser.getAlias(), intraWalletUser.getPublicKey(), intraWalletUser.getImage(), intraWalletUser.getAccuracy(),
                    intraWalletUser.getFrequency(), intraWalletUser.getLocation());
        } catch (CantCreateNewIntraWalletUserException e) {
            throw new CouldNotCreateIntraUserException("CAN'T CREATE INTRA USER", e, "", "Error in Intra user identity manager");
        } catch (Exception e) {
            throw new CouldNotCreateIntraUserException("CAN'T CREATE INTRA USER", FermatException.wrapException(e), "", "unknown exception");
        }
    }

    @Override
    public Location getLocationManager() throws CantGetDeviceLocationException
    {
        try {
          return locationManager.getLocation();

        // deviceLocation = new DeviceLocation(location.getLatitude(),location.getLongitude(),location.getTime(),location.getAltitude(),location.getSource());
         } catch (CantGetDeviceLocationException e) {
            throw new CantGetDeviceLocationException("CAN'T GET LOCATION MANAGER", FermatException.wrapException(e), "", "CantGetDeviceLocationException");

        }
    }
    /**
     * That method let the current logged in intra user set its profile
     * picture.
     *
     * @param image the profile picture to set
     * @throws CantSaveProfileImageException
     */
    @Override
    public void setNewProfileImage(byte[] image, String intraUserPublicKey) throws CantSaveProfileImageException {
        try {
            this.intraWalletUser.setNewProfileImage(image);
        }
//        catch (CantSetNewProfileImageException e){
//            throw new CantSaveProfileImageException("CAN'T SAVE INTRA USER PROFILE IMAGE",e,"","Error in Intra user identity manager");
//        }
        catch (Exception e) {
            throw new CantSaveProfileImageException("CAN'T SAVE INTRA USER PROFILE IMAGE", FermatException.wrapException(e), "", "unknown exception");
        }

    }


    /**
     * That method lists the login identities that can be used
     * to log in as an Intra User for the current Device User.
     *
     * @return the list of identities the current Device User can use to log in
     * @throws CantShowLoginIdentitiesException
     */
    @Override
    public List<IntraUserLoginIdentity> showAvailableLoginIdentities() throws CantShowLoginIdentitiesException {
        try {

            List<IntraUserLoginIdentity> intraUserLoginIdentityList = new ArrayList<IntraUserLoginIdentity>();

            List<IntraWalletUserIdentity> intraWalletUserList = this.intraWalletUserIdentityManager.getAllIntraWalletUsersFromCurrentDeviceUser();

            for (IntraWalletUserIdentity intraWalletUser : intraWalletUserList) {
                intraUserLoginIdentityList.add(new IntraUserModuleLoginIdentity(intraWalletUser.getAlias(), intraWalletUser.getPublicKey(), intraWalletUser.getImage(),
                        intraWalletUser.getAccuracy(),intraWalletUser.getFrequency(),intraWalletUser.getLocation()));
            }

            return intraUserLoginIdentityList;

        } catch (CantListIntraWalletUsersException e) {

            throw new CantShowLoginIdentitiesException("CAN'T GET Available Login Identities", e, "", "");
        } catch (Exception e) {
            throw new CantShowLoginIdentitiesException("CAN'T GET Available Login Identities", FermatException.wrapException(e), "", "unknown exception");
        }
    }


    /**
     * That method let an intra user log in
     *
     * @param intraUserPublicKey the public key of the intra user to log in
     */
    @Override
    public void login(String intraUserPublicKey) throws CantLoginIntraUserException {

        try {
            this.intraUserLoggedPublicKey = intraUserPublicKey;

            /**
             * Save on xml file the last intra user logged
             */

            /**
             * load file content
             */

            loadSettingsFile();

            /**
             * save logged intra user
             */
            intraUserSettings.setLoggedInPublicKey(this.intraUserLoggedPublicKey);

            intraUserLoginXml.setContent(XMLParser.parseObject(intraUserSettings));

            /**
             * persist xml file
             */

            intraUserLoginXml.persistToMedia();

        } catch (CantLoadLoginsFileException e) {
            throw new CantLoginIntraUserException("CAN'T LOGIN INTRA USER", e, "", "Error load xml file");

        } catch (Exception e) {
            throw new CantLoginIntraUserException("CAN'T LOGIN USER", FermatException.wrapException(e), "", "unknown exception");
        }

    }

    /**
     * That method searches for intra users that the logged in
     * intra user could be interested to add.
     *
     * @return a list with information of intra users
     * @throws CantGetIntraUsersListException
     */
    @Override
    @MethodDetail(looType = MethodDetail.LoopType.BACKGROUND,timeout = 35,timeoutUnit = TimeUnit.SECONDS)
    public List<IntraUserInformation> getSuggestionsToContact(Location location, double distance, String alias,int max, int offset) throws CantGetIntraUsersListException {

        try {

                //verifico la cache para mostrar los que tenia antes y los nuevos
              /*  List<IntraUserInformation> userCacheList = new ArrayList<>();
                try {
                    userCacheList = getCacheSuggestionsToContact(max, offset);
                } catch (CantGetIntraUsersListException e) {
                    e.printStackTrace();
                }*/

                List<IntraUserInformation> intraUserInformationModuleList = new ArrayList<>();

                List<IntraUserInformation> intraUserInformationList = new ArrayList<>();
                intraUserInformationList = intraUserNertwokServiceManager.getIntraUsersSuggestions(distance,alias,max, offset, location);



                for (IntraUserInformation intraUser : intraUserInformationList) {

                    //get connection state status
                    ConnectionState connectionState = this.intraWalletUserManager.getIntraUsersConnectionStatus(intraUser.getPublicKey());

                    //return intra user information - if not connected - status return null
                    IntraUserInformation intraUserInformation = new IntraUserModuleInformation(intraUser.getName(),intraUser.getPhrase(),intraUser.getPublicKey(),intraUser.getProfileImage(), connectionState,intraUser.getState(),intraUser.getContactRegistrationDate());
                    intraUserInformationModuleList.add(intraUserInformation);
                }


         /*   if(intraUserInformationModuleList!=null) {
                if (userCacheList.size() == 0) {

                    //save cache records
                    try {
                        saveCacheIntraUsersSuggestions(intraUserInformationModuleList);
                    } catch (CantGetIntraUsersListException e) {
                        e.printStackTrace();
                    }

                    return intraUserInformationModuleList;
                }
                else {
                    if (intraUserInformationModuleList.size() == 0) {
                        return userCacheList;
                    }
                    else {
                        for (IntraUserInformation intraUserCache : userCacheList) {
                            boolean exist = false;
                            for (IntraUserInformation intraUser : intraUserInformationModuleList) {
                                if (intraUserCache.getPublicKey().equals(intraUser.getPublicKey())) {
                                    exist = true;
                                    break;
                                }
                            }
                            if (!exist)
                                intraUserInformationModuleList.add(intraUserCache);
                        }

                        //save cache records
                        try {
                            saveCacheIntraUsersSuggestions(intraUserInformationModuleList);
                        } catch (CantGetIntraUsersListException e) {
                            e.printStackTrace();
                        }

                        return intraUserInformationModuleList;
                    }
                }
            }
            else {
                return userCacheList;
            }*/


            return intraUserInformationModuleList;

        }
        catch (ErrorSearchingSuggestionsException e) {
            throw new CantGetIntraUsersListException("CAN'T GET SUGGESTIONS TO CONTACT",e,"","Error on intra user network service");
        }
        catch (Exception e) {
            throw new CantGetIntraUsersListException("CAN'T GET SUGGESTIONS TO CONTACT",e,"","Unknown Error");
        }
    }

    @Override
    @MethodDetail(looType = MethodDetail.LoopType.BACKGROUND,timeout = 30,timeoutUnit = TimeUnit.SECONDS)
    public List<IntraUserInformation> getCacheSuggestionsToContact(int max, int offset) throws CantGetIntraUsersListException {
        try {

            List<IntraUserInformation> intraUserInformationModuleList = new ArrayList<>();

            List<IntraUserInformation> intraUserInformationList = new ArrayList<>();
            intraUserInformationList = intraUserNertwokServiceManager.getCacheIntraUsersSuggestions(max, offset);


            for (IntraUserInformation intraUser : intraUserInformationList) {

                //get connection state status
                ConnectionState connectionState = this.intraWalletUserManager.getIntraUsersConnectionStatus(intraUser.getPublicKey());

                //return intra user information - if not connected - status return null
                IntraUserInformation intraUserInformation = new IntraUserModuleInformation(intraUser.getName(),intraUser.getPhrase(),intraUser.getPublicKey(),intraUser.getProfileImage(), connectionState,ProfileStatus.OFFLINE,intraUser.getContactRegistrationDate());


               // intraUserInformation.setProfileImageNull();

                intraUserInformationModuleList.add(intraUserInformation);
            }

            return intraUserInformationModuleList;
        }
        catch (ErrorSearchingCacheSuggestionsException e) {
            throw new CantGetIntraUsersListException("CAN'T GET CACHE SUGGESTIONS TO CONTACT",e,"","Error on intra user network service");
        }
        catch (Exception e) {
            throw new CantGetIntraUsersListException("CAN'T GET CACHE SUGGESTIONS TO CONTACT",e,"","Unknown Error");

        }
    }

    @Override
    public void saveCacheIntraUsersSuggestions(List<IntraUserInformation> lstIntraUser) throws CantGetIntraUsersListException {
        try {

            intraUserNertwokServiceManager.saveCacheIntraUsersSuggestions(lstIntraUser);

        }
        catch (CantInsertRecordException e) {
            throw new CantGetIntraUsersListException("CAN'T GET SUGGESTIONS TO CONTACT",e,"","Error on intra user network service");
        }
        catch (Exception e) {
            throw new CantGetIntraUsersListException("CAN'T GET SUGGESTIONS TO CONTACT",e,"","Unknown Error");

        }
    }


    /**
     * That method gives us an interface to manage a search for a particular
     * intra user
     *
     * @return a searching interface
     */
    @Override
    public IntraUserSearch searchIntraUser() {
        return new IntraUserModuleSearch(this.intraUserNertwokServiceManager,this.intraWalletUserIdentityManager, this.intraWalletUserManager);
    }

    /**
     * Intra User Actor Manager
     */

    /**
     * That method initialize the request of contact between
     * two intra users.
     *
     * @param intraUserToAddName      The name of the intra user to add
     * @param intraUserToAddPublicKey The public key of the intra user to add
     * @param OthersProfileImage      The profile image of the other intra user
     * @param MyProfileImage          The profile image of the logged intra user
     * @throws CantStartRequestException
     */

    @Override
    public void askIntraUserForAcceptance(String intraUserToAddName, String intraUserToAddPhrase, String intraUserToAddPublicKey, byte[] OthersProfileImage,byte[] MyProfileImage, String identityPublicKey, String identityAlias) throws CantStartRequestException {

        try {

            //Get actor full profile image to save
            /**
             *Call Network Service Intra User to add request connection
             */

            if (  this.intraWalletUserManager.getIntraUsersConnectionStatus(intraUserToAddPublicKey)!= ConnectionState.CONNECTED){
                System.out.println("The User you are trying to connect with is not connected" +
                        "so we send the message to the intraUserNetworkService");
                this.intraUserNertwokServiceManager.askIntraUserForAcceptance(identityPublicKey, identityAlias, Actors.INTRA_USER, intraUserToAddName,intraUserToAddPhrase, intraUserToAddPublicKey, Actors.INTRA_USER, MyProfileImage);
            }else{
                this.intraUserNertwokServiceManager.acceptIntraUser(identityPublicKey, intraUserToAddPublicKey);
                System.out.println("The user is connected");
            }

            /**
             *Call Actor Intra User to add request connection
             */

            this.intraWalletUserManager.askIntraWalletUserForAcceptance(identityPublicKey, intraUserToAddName, intraUserToAddPhrase, intraUserToAddPublicKey, OthersProfileImage);


        } catch (CantCreateIntraWalletUserException e) {
            throw new CantStartRequestException("", e, "", "");
        } catch (RequestAlreadySendException e) {
            throw new CantStartRequestException("", e, "", "Intra user request already send");
        } catch (Exception e) {
            throw new CantStartRequestException("CAN'T ASK INTRA USER CONNECTION", FermatException.wrapException(e), "", "unknown exception");
        }
    }

    /**
     * That method takes the information of a connection request, accepts
     * the request and adds the intra user to the list managed by this plugin with ContactState CONTACT.
     *
     * @param intraUserToAddName      The name of the intra user to add
     * @param intraUserToAddPublicKey The public key of the intra user to add
     * @param profileImage            The profile image that the intra user has
     * @throws CantAcceptRequestException
     */
    @Override
    public void acceptIntraUser(String identityPublicKey, String intraUserToAddName, String intraUserToAddPublicKey, byte[] profileImage) throws CantAcceptRequestException {
        try {
            /**
             *Call Actor Intra User to accept request connection
             */
            this.intraWalletUserManager.acceptIntraWalletUser(identityPublicKey, intraUserToAddPublicKey);

            /**
             *Call Network Service Intra User to accept request connection
             */
            this.intraUserNertwokServiceManager.acceptIntraUser(identityPublicKey, intraUserToAddPublicKey);

        } catch (CantAcceptIntraWalletUserException e) {
            throw new CantAcceptRequestException("CAN'T ACCEPT INTRA USER CONNECTION - KEY " + intraUserToAddPublicKey, e, "", "");
        } catch (Exception e) {
            throw new CantAcceptRequestException("CAN'T ACCEPT INTRA USER CONNECTION - KEY " + intraUserToAddPublicKey, FermatException.wrapException(e), "", "unknown exception");
        }
    }


    /**
     * That method denies a conection request from other intra user
     *
     * @param intraUserToRejectPublicKey the public key of the user to deny its connection request
     * @throws IntraUserConnectionDenialFailedException
     */
    @Override
    public void denyConnection(String intraUserLoggedPublicKey, String intraUserToRejectPublicKey) throws IntraUserConnectionDenialFailedException {
        try {
            /**
             *Call Actor Intra User to denied request connection
             */

            this.intraWalletUserManager.denyConnection(intraUserLoggedPublicKey, intraUserToRejectPublicKey);

            /**
             *Call Network Service Intra User to denied request connection
             */
            this.intraUserNertwokServiceManager.denyConnection(intraUserLoggedPublicKey, intraUserToRejectPublicKey);

        } catch (CantDenyConnectionException e) {
            throw new IntraUserConnectionDenialFailedException("CAN'T DENY INTRA USER CONNECTION - KEY:" + intraUserToRejectPublicKey, e, "", "");
        } catch (Exception e) {
            throw new IntraUserConnectionDenialFailedException("CAN'T DENY INTRA USER CONNECTION - KEY:" + intraUserToRejectPublicKey, FermatException.wrapException(e), "", "unknown exception");
        }
    }

    /**
     * That method disconnect an intra user from the list managed by this
     * plugin
     *
     * @param intraUserToDisconnectPublicKey the public key of the intra user to disconnect
     * @throws IntraUserDisconnectingFailedException
     */
    @Override
    public void disconnectIntraUSer(String intraUserLoggedPublicKey, String intraUserToDisconnectPublicKey) throws IntraUserDisconnectingFailedException {
        try
        {
            /**
             *Call Actor Intra User to disconnect request connection
             */
            this.intraWalletUserManager.disconnectIntraWalletUser(intraUserLoggedPublicKey, intraUserToDisconnectPublicKey);

            /**
             *Call Network Service Intra User to disconnect request connection
             */

            this.intraUserNertwokServiceManager.disconnectIntraUSer(intraUserLoggedPublicKey, intraUserToDisconnectPublicKey);

        } catch (CantDisconnectIntraWalletUserException e) {
            throw new IntraUserDisconnectingFailedException("CAN'T DISCONNECT INTRA USER CONNECTION- KEY:" + intraUserToDisconnectPublicKey, e, "", "");
        } catch (Exception e) {
            throw new IntraUserDisconnectingFailedException("CAN'T DISCONNECT INTRA USER CONNECTION- KEY:" + intraUserToDisconnectPublicKey, FermatException.wrapException(e), "", "unknown exception");
        }
    }


    /**
     * That method cancels an intra user from the list managed by this
     *
     * @param intraUserToCancelPublicKey
     * @throws IntraUserCancellingFailedException
     */
    @Override
    public void cancelIntraUser(String intraUserToCancelPublicKey) throws IntraUserCancellingFailedException {
        try {
            /**
             *Call Actor Intra User to cancel request connection
             */

            this.intraWalletUserManager.cancelIntraWalletUser(this.intraUserLoggedPublicKey, intraUserToCancelPublicKey);

            /**
             *Call Network Service Intra User to cancel request connection
             */

            this.intraUserNertwokServiceManager.cancelIntraUSer(this.intraUserLoggedPublicKey, intraUserToCancelPublicKey);


        } catch (CantCancelIntraWalletUserException e) {
            throw new IntraUserCancellingFailedException("CAN'T CANCEL INTRA USER CONNECTION- KEY:" + intraUserToCancelPublicKey, e, "", "");
        } catch (Exception e) {
            throw new IntraUserCancellingFailedException("CAN'T CANCEL INTRA USER CONNECTION- KEY:" + intraUserToCancelPublicKey, FermatException.wrapException(e), "", "unknown exception");
        }
    }


    /**
     * That method returns the list of all intra users registered by the
     * logged in intra user
     *
     * @return the list of intra users connected to the logged in intra user
     * @throws CantGetIntraUsersListException
     */
    @Override
    @MethodDetail(looType = MethodDetail.LoopType.BACKGROUND,timeout = 30,timeoutUnit = TimeUnit.SECONDS)
    public List<IntraUserInformation> getAllIntraUsers(String identityPublicKey, int max, int offset) throws CantGetIntraUsersListException {
        try {
            List<IntraUserInformation> intraUserList = new ArrayList<IntraUserInformation>();


            List<IntraWalletUserActor> actorsList = this.intraWalletUserManager.getAllIntraWalletUsers(identityPublicKey, max, offset);

            for (IntraWalletUserActor intraUserActor : actorsList) {
                intraUserList.add(new IntraUserModuleInformation(intraUserActor.getName(),intraUserActor.getPhrase(),intraUserActor.getPublicKey(),intraUserActor.getProfileImage(),intraUserActor.getContactState(),ProfileStatus.ONLINE,intraUserActor.getContactRegistrationDate()));
            }
            return intraUserList;
        } catch (CantGetIntraWalletUsersException e) {
            throw new CantGetIntraUsersListException("CAN'T GET ALL INTRA USERS FROM LOGGED USER", e, "", "");
        } catch (Exception e) {
            throw new CantGetIntraUsersListException("CAN'T GET ALL INTRA USERS FROM LOGGED USER", FermatException.wrapException(e), "", "unknown exception");
        }
    }


    /**
     * That method returns the list of intra users waiting to be accepted
     * or rejected by the logged in intra user
     *
     * @return the list of intra users waiting to be accepted or rejected by the  logged in intra user
     * @throws CantGetIntraUsersListException
     */
    @Override
    @MethodDetail(looType = MethodDetail.LoopType.BACKGROUND,timeout = 20,timeoutUnit = TimeUnit.SECONDS)
    public List<IntraUserInformation> getIntraUsersWaitingYourAcceptance(String identityPublicKey, int max, int offset) throws CantGetIntraUsersListException {
        List<IntraUserInformation> intraUserList = new ArrayList<IntraUserInformation>();
        try {


            List<IntraWalletUserActor> actorsList = this.intraWalletUserManager.getWaitingYourAcceptanceIntraWalletUsers(identityPublicKey, max, offset);

            for (IntraWalletUserActor intraUserActor : actorsList) {
                intraUserList.add(new IntraUserModuleInformation(intraUserActor.getName(),"",intraUserActor.getPublicKey(),intraUserActor.getProfileImage(),intraUserActor.getContactState(),ProfileStatus.ONLINE,intraUserActor.getContactRegistrationDate()));
            }

            return intraUserList;
        } catch (CantGetIntraWalletUsersException e) {
            throw new CantGetIntraUsersListException("CAN'T GET INTRA USER WAITING YOUR ACCEPTANCE", e, "", "");
        } catch (Exception e) {
            throw new CantGetIntraUsersListException("CAN'T GET INTRA USER WAITING YOUR ACCEPTANCE", FermatException.wrapException(e), "", "unknown exception");
        }
    }

    /**
     * That method returns the list of intra users waiting to be accepted
     * or rejected by the logged in intra user
     *
     * @return the list of intra users waiting to be accepted or rejected by the  logged in intra user
     * @throws CantGetIntraUsersListException
     */
    @Override
    public int getIntraUsersWaitingYourAcceptanceCount() {
        //TODO: falta que este metodo que devuelva la cantidad de request de conexion que tenes
        try {

            if (getActiveIntraUserIdentity() != null){
                return getIntraUsersWaitingYourAcceptance(getActiveIntraUserIdentity().getPublicKey(), 100, 0).size();
            }

        } catch (CantGetIntraUsersListException e) {
            e.printStackTrace();
        } catch (CantGetActiveLoginIdentityException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    @Override
    public ConnectionState getIntraUsersConnectionStatus(String intraUserConnectedPublicKey) throws CantGetIntraUserConnectionStatusException {

        try {
            return this.intraWalletUserManager.getIntraUsersConnectionStatus(intraUserConnectedPublicKey);
        } catch (CantGetIntraUsersConnectedStateException e) {
            throw new CantGetIntraUserConnectionStatusException("CAN'T GET INTRA USER CONNECTION STATUS",e,"","Error on IntraUserIdentity Manager");

        }

    }


    @Override
    public IntraWalletUserActor getLastNotification(String intraUserConnectedPublicKey) throws CantGetIntraUsersListException {

        try {
            return this.intraWalletUserManager.getLastNotification(intraUserConnectedPublicKey);
        } catch (CantGetIntraUserException e) {
            throw new CantGetIntraUsersListException("CAN'T GET INTRA USER LAST NOTIFICATION",e,"","Error on IntraUserIdentity Manager");

        }

    }


    @Override
    public void updateIntraUserIdentity(String identityPublicKey, String identityAlias, String identityPhrase, byte[] profileImage, Long accuracy, Frequency frequency, Location location) throws CantUpdateIdentityException {
        try {
            this.intraWalletUserIdentityManager.updateIntraUserIdentity(identityPublicKey, identityAlias, identityPhrase, profileImage, accuracy, frequency,location);
        } catch (CantUpdateIdentityException e) {
            throw new CantUpdateIdentityException("CAN'T UPDATE INTRA USER IDENTITY", e, "", "Error on IntraUserIdentity Manager");
        } catch (Exception e) {
            throw new CantUpdateIdentityException("CAN'T UPDATE INTRA USER IDENTITY", FermatException.wrapException(e), "", "Error on IntraUserIdentity Manager");
        }
    }

    @Override
    public void deleteIntraUserIdentity(String identityPublicKey) throws CantDeleteIdentityException {
        try {
            this.intraWalletUserIdentityManager.deleteIntraUserIdentity(identityPublicKey);
        } catch (CantDeleteIdentityException e) {
            throw new CantDeleteIdentityException("CAN'T UPDATE INTRA USER IDENTITY", e, "", "Error on IntraUserIdentity Manager");
        } catch (Exception e) {
            throw new CantDeleteIdentityException("CAN'T UPDATE INTRA USER IDENTITY", FermatException.wrapException(e), "", "Error on IntraUserIdentity Manager");
        }
    }

    @Override
    public boolean isActorConnected(String publicKey) throws CantCreateNewDeveloperException {
        return intraWalletUserManager.isActorConnected(publicKey);
    }


    /**
     * That method list the intra users that haven't
     * answered to a sent connection request by the current logged in intra user.
     *
     * @return the list of intra users that haven't answered to a sent connection request by the current
     * logged in intra user.
     * @throws CantGetIntraUsersListException
     */
    @Override
    public List<IntraUserInformation> getIntraUsersWaitingTheirAcceptance(String identityPublicKey, int max, int offset) throws CantGetIntraUsersListException {
        try {
            List<IntraUserInformation> intraUserList = new ArrayList<IntraUserInformation>();

            List<IntraWalletUserActor> actorsList = this.intraWalletUserManager.getWaitingTheirAcceptanceIntraWalletUsers(identityPublicKey, max, offset);

            for (IntraWalletUserActor intraUserActor : actorsList) {
                intraUserList.add(new IntraUserModuleInformation(intraUserActor.getName(),"",intraUserActor.getPublicKey(),intraUserActor.getProfileImage(),intraUserActor.getContactState(),ProfileStatus.OFFLINE,intraUserActor.getContactRegistrationDate()));
            }
            return intraUserList;
        } catch (CantGetIntraWalletUsersException e) {
            throw new CantGetIntraUsersListException("CAN'T GET INTRA USER WAITING THEIR ACCEPTANCE", e, "", "Error on IntraUserActor Manager");
        } catch (Exception e) {
            throw new CantGetIntraUsersListException("CAN'T GET INTRA USER WAITING THEIR ACCEPTANCE", FermatException.wrapException(e), "", "unknown exception");
        }
    }

    @Override
    public IntraUserLoginIdentity getActiveIntraUserIdentity() throws CantGetActiveLoginIdentityException {
        try {

            IntraUserLoginIdentity intraUserLoginIdentity = null;

            List<IntraWalletUserIdentity> intraWalletUserList = this.intraWalletUserIdentityManager.getAllIntraWalletUsersFromCurrentDeviceUser();

            for (IntraWalletUserIdentity intraWalletUser : intraWalletUserList) {
                //TODO: Naty lo saqué esto porque el intraUserLoggedPublicKey está siempre en null, hay que darle valor.
                //if(intraWalletUser.getPublicKey().equals(intraUserLoggedPublicKey)) {
                intraUserLoginIdentity = new IntraUserModuleLoginIdentity(intraWalletUser.getAlias(), intraWalletUser.getPublicKey(), intraWalletUser.getImage(), intraWalletUser.getAccuracy(), intraWalletUser.getFrequency(), intraWalletUser.getLocation());
                break;
                //}
            }

            return intraUserLoginIdentity;

        } catch (CantListIntraWalletUsersException e) {

            throw new CantGetActiveLoginIdentityException("CAN'T GET Active Login Identities", e, "", "");
        } catch (Exception e) {
            throw new CantGetActiveLoginIdentityException("CAN'T GET active Login Identities", FermatException.wrapException(e), "", "unknown exception");
        }
    }


    @Override
    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
        try {
            return intraWalletUserIdentityManager.getAllIntraWalletUsersFromCurrentDeviceUser().get(0);
        } catch (CantListIntraWalletUsersException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e){
            return null;
        }
    }

    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {

    }

    @Override
    public void setAppPublicKey(String publicKey) {
        this.appPublicKey = publicKey;
    }

    @Override
    public int[] getMenuNotifications() {
        int[] notifications = new int[4];
        try {
            if(getSelectedActorIdentity() != null)
                notifications[2] = intraWalletUserManager.getWaitingYourAcceptanceIntraWalletUsers(getSelectedActorIdentity().getPublicKey(),99,0).size();
            else
                notifications[2] = 0;
        } catch (CantGetIntraWalletUsersException e) {
            e.printStackTrace();
        } catch (CantGetSelectedActorIdentityException | ActorIdentityNotSelectedException e) {
            e.printStackTrace();
        }
        return notifications;
    }


    /**
     * Geolocation Manager methods
     */


    @Override
    public HashMap<String, com.bitdubai.fermat_api.layer.all_definition.enums.Country> getCountryList() throws CantConnectWithExternalAPIException, CantCreateBackupFileException, CantCreateCountriesListException {
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
    public Address getAddressByCoordinate(double latitude, double longitude) throws CantCreateAddressException {
        return geolocationManager.getAddressByCoordinate(latitude, longitude);
    }

    @Override
    public GeoRectangle getRandomGeoLocation() throws CantCreateGeoRectangleException {
        return geolocationManager.getRandomGeoLocation();
    }

    @Override
    public Location getLocation() throws CantGetDeviceLocationException {
        return locationManager.getLocation();
    }

    @Override
    public List<ExtendedCity> getExtendedCitiesByFilter(String filter) throws CantGetCitiesListException {
        return geolocationManager.getExtendedCitiesByFilter(filter);
    }



    /**
     * private methods
     */

    private void loadSettingsFile() throws CantLoadLoginsFileException {

        try {
            /**
             *  I check if the file containing  the wallets settings  already exists or not.
             * If not exists I created it.
             * * *
             */

            intraUserLoginXml = pluginFileSystem.getTextFile(pluginId, pluginId.toString(), INTRA_USER_LOGIN_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

            /**
             * Now I read the content of the file and place it in memory.
             */
            intraUserLoginXml.loadFromMedia();

            String xml = intraUserLoginXml.getContent();

            intraUserSettings = (IntraUserSettings) XMLParser.parseXML(xml, intraUserSettings);


        } catch (FileNotFoundException fileNotFoundException) {
            /**
             * If the file did not exist it is not a problem. It only means this is the first time this plugin is running.
             *
             * I will create the file now, with an empty content so that when a new wallet is added we wont have to deal
             * with this file not existing again.
             * * * * *
             */

            try {
                intraUserLoginXml = pluginFileSystem.createTextFile(pluginId, pluginId.toString(), INTRA_USER_LOGIN_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            } catch (CantCreateFileException cantCreateFileException) {
                /**
                 * If I can not save this file, then this plugin shouldn't be running at all.
                 */
                throw new CantLoadLoginsFileException(CantLoadLoginsFileException.DEFAULT_MESSAGE, cantCreateFileException, null, null);
            }

            try {
                /**
                 * make default xml structure
                 */

                intraUserLoginXml.setContent(XMLParser.parseObject(intraUserSettings));

                intraUserLoginXml.setContent(XMLParser.parseObject(intraUserSettings));

//                /**
//                 * If I can not save this file, then this plugin shouldn't be running at all.
//                 */
//                throw new CantLoadLoginsFileException(CantLoadLoginsFileException.DEFAULT_MESSAGE, cantPersistFileException, null, null);
            }catch (Exception e){
                throw new CantLoadLoginsFileException(CantLoadLoginsFileException.DEFAULT_MESSAGE, e, null, null);
            }
        } catch (CantLoadFileException | CantCreateFileException e) {

            /**
             * In this situation we might have a corrupted file we can not read. For now the only thing I can do is
             * to prevent the plug-in from running.
             *
             * In the future there should be implemented a method to deal with this situation.
             * * * *
             */
            throw new CantLoadLoginsFileException(CantLoadLoginsFileException.DEFAULT_MESSAGE, e, null, null);
        } catch (Exception ex) {
            throw new CantLoadLoginsFileException(CantLoadLoginsFileException.DEFAULT_MESSAGE, FermatException.wrapException(ex), null, null);
        }
    }
}
