package com.bitdubai.fermat_ccp_plugin.layer.module.intra_user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.IntraUsers.IntraUserSettings;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_wallet_user.exceptions.CantAcceptIntraWalletUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_wallet_user.exceptions.CantCancelIntraWalletUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_wallet_user.exceptions.CantCreateIntraWalletUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_wallet_user.exceptions.CantDenyConnectionException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_wallet_user.exceptions.CantDisconnectIntraWalletUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_wallet_user.exceptions.CantGetIntraWalletUsersException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_wallet_user.interfaces.DealsWithCCPIntraWalletUsers;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_wallet_user.interfaces.IntraWalletUser;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_wallet_user.interfaces.IntraWalletUserManager;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_wallet_user.exceptions.CantCreateNewIntraWalletUserException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_wallet_user.exceptions.CantListIntraWalletUsersException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_wallet_user.interfaces.DealsWithCCPIdentityIntraWalletUser;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantAcceptRequestException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetIntraUsersListException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantLoginIntraUserException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantSaveProfileImageException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantShowLoginIdentitiesException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantStartRequestException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CouldNotCreateIntraUserException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.IntraUserCancellingFailedException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.IntraUserConectionDenegationFailedException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.IntraUserDisconnectingFailedException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserLoginIdentity;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserSearch;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.ErrorSearchingSuggestionsException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.interfaces.DealsWithIntraUsersNetworkService;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.interfaces.IntraUserManager;
import com.bitdubai.fermat_ccp_plugin.layer.module.intra_user.developer.bitdubai.version_1.exceptions.CantLoadLoginsFileException;
import com.bitdubai.fermat_ccp_plugin.layer.module.intra_user.developer.bitdubai.version_1.structure.IntraUserModuleInformation;
import com.bitdubai.fermat_ccp_plugin.layer.module.intra_user.developer.bitdubai.version_1.structure.IntraUserModuleLoginIdentity;
import com.bitdubai.fermat_ccp_plugin.layer.module.intra_user.developer.bitdubai.version_1.structure.IntraUserModuleSearch;
import com.bitdubai.fermat_pip_api.layer.pip_actor.exception.CantGetLogTool;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This plug-in provides the methods for the Intra Users sub app.
 * To manage Intra User information and intra users connections
 * Created by loui on 22/02/15.
 * Modified by Natalia Cortez on 11/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class IntraWalletUserModulePluginRoot extends AbstractPlugin implements DealsWithErrors,DealsWithIntraUsersNetworkService, DealsWithCCPIdentityIntraWalletUser,DealsWithCCPIntraWalletUsers, DealsWithPluginFileSystem, LogManagerForDevelopers, IntraUserModuleManager, Plugin, Service  {


    private static String INTRA_USER_LOGIN_FILE_NAME = "intraUsersLogin";

    private String intraUserLoggedPublicKey;

    private PluginTextFile intraUserLoginXml;

    private IntraUserSettings intraUserSettings = new IntraUserSettings();


    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER         )
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.ANDROID         , addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.NETWORK_SERVICE, plugin = Plugins.INTRA_WALLET_USER  )
    private IntraUserManager intraUserNertwokServiceManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.IDENTITY       , plugin = Plugins.INTRA_WALLET_USER  )
    private com.bitdubai.fermat_ccp_api.layer.identity.intra_wallet_user.interfaces.IntraWalletUserManager intraWalletUserIdentityManager;


    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.ACTOR          , plugin = Plugins.INTRA_WALLET_USER  )
    private IntraWalletUserManager intraWalletUserManager;

    private com.bitdubai.fermat_ccp_api.layer.identity.intra_wallet_user.interfaces.IntraWalletUser intraWalletUser;





    public IntraWalletUserModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }


    /**
     * DealsWithLogger interface member variable
     */

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();


    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }


    /**
     * IntraUserModuleManager Interface implementation.
     */


    /**
     * That method is used to create a new intra user
     *
     * @param intraUserName the name of the intra user to create
     * @param profileImage  the profile image of the intra user to create
     * @return the login identity generated for the said intra user.
     * @throws CouldNotCreateIntraUserException
     */
    @Override
    public IntraUserLoginIdentity createIntraUser(String intraUserName, byte[] profileImage) throws CouldNotCreateIntraUserException {

      try{
           this.intraWalletUser =  this.intraWalletUserIdentityManager.createNewIntraWalletUser(intraUserName, profileImage);


          return new IntraUserModuleLoginIdentity(intraWalletUser.getAlias(), intraWalletUser.getPublicKey(), intraWalletUser.getProfileImage());
      }

      catch (CantCreateNewIntraWalletUserException e){
            throw new CouldNotCreateIntraUserException("CAN'T CREATE INTRA USER",e,"","Error in Intra user identity manager");
      }
      catch(Exception e)
      {
          throw new CouldNotCreateIntraUserException("CAN'T CREATE INTRA USER",FermatException.wrapException(e),"","unknown exception");
      }
    }

    /**
     * That method let the current logged in intra user set its profile
     * picture.
     * @param image the profile picture to set
     * @throws CantSaveProfileImageException
     */
    @Override
    public void setNewProfileImage(byte[] image, String intraUserPublicKey) throws CantSaveProfileImageException {
        try{
            this.intraWalletUser.setNewProfileImage(image);
        }
//        catch (CantSetNewProfileImageException e){
//            throw new CantSaveProfileImageException("CAN'T SAVE INTRA USER PROFILE IMAGE",e,"","Error in Intra user identity manager");
//        }
        catch(Exception e)
        {
            throw new CantSaveProfileImageException("CAN'T SAVE INTRA USER PROFILE IMAGE",FermatException.wrapException(e),"","unknown exception");
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

            List<com.bitdubai.fermat_ccp_api.layer.identity.intra_wallet_user.interfaces.IntraWalletUser> intraWalletUserList =  this.intraWalletUserIdentityManager.getAllIntraWalletUsersFromCurrentDeviceUser();

            for (com.bitdubai.fermat_ccp_api.layer.identity.intra_wallet_user.interfaces.IntraWalletUser intraWalletUser : intraWalletUserList) {
                intraUserLoginIdentityList.add(new IntraUserModuleLoginIdentity(intraWalletUser.getAlias(), intraWalletUser.getPublicKey(), intraWalletUser.getProfileImage()));
            }

            return intraUserLoginIdentityList;

        } catch (CantListIntraWalletUsersException e) {

            throw new CantShowLoginIdentitiesException("CAN'T GET Available Login Identities",e,"","");
        }
        catch(Exception e)
        {
            throw new CantShowLoginIdentitiesException("CAN'T GET Available Login Identities",FermatException.wrapException(e),"","unknown exception");
        }
    }


    /**
     * That method let an intra user log in
     *
     * @param intraUserPublicKey the public key of the intra user to log in
     */
    @Override
    public void login(String intraUserPublicKey)throws CantLoginIntraUserException {

        try
        {
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

        }
        catch(CantLoadLoginsFileException e)
        {
            throw new CantLoginIntraUserException("CAN'T LOGIN INTRA USER",e,"","Error load xml file");

        }
        catch(Exception e)
        {
            throw new CantLoginIntraUserException("CAN'T LOGIN USER",FermatException.wrapException(e),"","unknown exception");
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
    public List<IntraUserInformation> getSuggestionsToContact(int max,int offset) throws CantGetIntraUsersListException {

        try {

            List<IntraUserInformation>  intraUserInformationList = new ArrayList<IntraUserInformation>();

            List<IntraUserInformation> intraUserList =  this.intraUserNertwokServiceManager.getIntraUsersSuggestions(max,offset);

            for (IntraUserInformation intraUser : intraUserList) {

                //byte[] image = intraUser.getProfileImage();
                intraUserInformationList.add(new IntraUserModuleInformation(intraUser.getName(),intraUser.getPublicKey(), intraUser.getProfileImage()));
            }

            return intraUserInformationList;
        }
        catch (ErrorSearchingSuggestionsException e) {
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
        return new IntraUserModuleSearch(this.intraUserNertwokServiceManager,this.intraWalletUserIdentityManager);
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
     * @param profileImage            The profile image that the intra user has
     * @throws CantStartRequestException
     */

    @Override
    public void askIntraUserForAcceptance(String intraUserToAddName, String intraUserToAddPublicKey, byte[] profileImage,String identityPublicKey,String identityAlias) throws CantStartRequestException {

        try
        {
            /**
             *Call Actor Intra User to add request connection
             */
            this.intraWalletUserManager.askIntraWalletUserForAcceptance(identityPublicKey, intraUserToAddName, intraUserToAddPublicKey, profileImage);

            /**
             *Call Network Service Intra User to add request connection
             */

            this.intraUserNertwokServiceManager.askIntraUserForAcceptance(identityPublicKey, identityAlias, Actors.INTRA_USER, intraUserToAddName, intraUserToAddPublicKey, Actors.INTRA_USER, profileImage);
        }
        catch(CantCreateIntraWalletUserException e)
        {
            throw new CantStartRequestException("",e,"","");
        }
        catch(Exception e)
        {
            throw new CantStartRequestException("CAN'T ASK INTRA USER CONNECTION",FermatException.wrapException(e),"","unknown exception");
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
    public void acceptIntraUser(String identityPublicKey,String intraUserToAddName, String intraUserToAddPublicKey, byte[] profileImage) throws CantAcceptRequestException {
        try
        {
            /**
             *Call Actor Intra User to accept request connection
             */
            this.intraWalletUserManager.acceptIntraWalletUser(identityPublicKey, intraUserToAddPublicKey);

            /**
             *Call Network Service Intra User to accept request connection
             */
            this.intraUserNertwokServiceManager.acceptIntraUser(identityPublicKey, intraUserToAddPublicKey);

        }
       catch(CantAcceptIntraWalletUserException e)
        {
            throw new CantAcceptRequestException("CAN'T ACCEPT INTRA USER CONNECTION - KEY " + intraUserToAddPublicKey,e,"","");
        }
        catch(Exception e)
        {
            throw new CantAcceptRequestException("CAN'T ACCEPT INTRA USER CONNECTION - KEY " + intraUserToAddPublicKey,FermatException.wrapException(e),"","unknown exception");
        }
    }



    /**
     * That method denies a conection request from other intra user
     *
     * @param intraUserToRejectPublicKey the public key of the user to deny its connection request
     * @throws IntraUserConectionDenegationFailedException
     */
    @Override
    public void denyConnection(String intraUserToRejectPublicKey) throws IntraUserConectionDenegationFailedException {
        try
        {
            /**
             *Call Actor Intra User to denied request connection
             */

           this.intraWalletUserManager.denyConnection(this.intraUserLoggedPublicKey, intraUserToRejectPublicKey);

            /**
             *Call Network Service Intra User to denied request connection
             */
            this.intraUserNertwokServiceManager.denyConnection(this.intraUserLoggedPublicKey, intraUserToRejectPublicKey);

        }
        catch(CantDenyConnectionException e)
        {
            throw new IntraUserConectionDenegationFailedException("CAN'T DENY INTRA USER CONNECTION - KEY:" + intraUserToRejectPublicKey,e,"","");
        }
        catch(Exception e)
        {
            throw new IntraUserConectionDenegationFailedException("CAN'T DENY INTRA USER CONNECTION - KEY:" + intraUserToRejectPublicKey,FermatException.wrapException(e),"","unknown exception");
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
    public void disconnectIntraUSer(String intraUserToDisconnectPublicKey) throws IntraUserDisconnectingFailedException {
        try
        {
            /**
             *Call Actor Intra User to disconnect request connection
             */
            this.intraWalletUserManager.disconnectIntraWalletUser(this.intraUserLoggedPublicKey, intraUserToDisconnectPublicKey);

            /**
             *Call Network Service Intra User to disconnect request connection
             */

            this.intraUserNertwokServiceManager.disconnectIntraUSer(this.intraUserLoggedPublicKey, intraUserToDisconnectPublicKey);

        }
        catch(CantDisconnectIntraWalletUserException e)
        {
            throw new IntraUserDisconnectingFailedException("CAN'T DISCONNECT INTRA USER CONNECTION- KEY:" + intraUserToDisconnectPublicKey,e,"","");
        }
        catch(Exception e)
        {
            throw new IntraUserDisconnectingFailedException("CAN'T DISCONNECT INTRA USER CONNECTION- KEY:" + intraUserToDisconnectPublicKey,FermatException.wrapException(e),"","unknown exception");
        }
    }


    /**
     * That method cancels an intra user from the list managed by this
     * @param intraUserToCancelPublicKey
     * @throws IntraUserCancellingFailedException
     */
    @Override
    public void cancelIntraUser(String intraUserToCancelPublicKey) throws IntraUserCancellingFailedException {
        try
        {
            /**
             *Call Actor Intra User to cancel request connection
             */

              this.intraWalletUserManager.cancelIntraWalletUser(this.intraUserLoggedPublicKey, intraUserToCancelPublicKey);

            /**
             *Call Network Service Intra User to cancel request connection
             */

            this.intraUserNertwokServiceManager.cancelIntraUSer(this.intraUserLoggedPublicKey, intraUserToCancelPublicKey);


        }
         catch(CantCancelIntraWalletUserException e)
        {
            throw new IntraUserCancellingFailedException("CAN'T CANCEL INTRA USER CONNECTION- KEY:" + intraUserToCancelPublicKey,e,"","");
        }
        catch(Exception e)
        {
            throw new IntraUserCancellingFailedException("CAN'T CANCEL INTRA USER CONNECTION- KEY:" + intraUserToCancelPublicKey,FermatException.wrapException(e),"","unknown exception");
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
    public List<IntraUserInformation> getAllIntraUsers(String identityPublicKey,int max,int offset) throws CantGetIntraUsersListException {
        try{
            List<IntraUserInformation> intraUserList= new ArrayList<IntraUserInformation>();


            List<IntraWalletUser> actorsList = this.intraWalletUserManager.getAllIntraWalletUsers(identityPublicKey, max, offset);

            for (IntraWalletUser intraUserActor : actorsList) {
                intraUserList.add(new IntraUserModuleInformation(intraUserActor.getName(),intraUserActor.getPublicKey(),intraUserActor.getProfileImage()));
            }
            return intraUserList;
        }
        catch(CantGetIntraWalletUsersException e) {
            throw new CantGetIntraUsersListException("CAN'T GET ALL INTRA USERS FROM LOGGED USER",e,"","");
        }
        catch(Exception e) {
            throw new CantGetIntraUsersListException("CAN'T GET ALL INTRA USERS FROM LOGGED USER",FermatException.wrapException(e),"","unknown exception");
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
    public List<IntraUserInformation> getIntraUsersWaitingYourAcceptance(String identityPublicKey,int max,int offset) throws CantGetIntraUsersListException {
        List<IntraUserInformation> intraUserList= new ArrayList<IntraUserInformation>();
        try {


            List<IntraWalletUser> actorsList = this.intraWalletUserManager.getWaitingYourAcceptanceIntraWalletUsers(identityPublicKey, max, offset);

            for (IntraWalletUser intraUserActor : actorsList) {
                intraUserList.add(new IntraUserModuleInformation(intraUserActor.getName(),intraUserActor.getPublicKey(),intraUserActor.getProfileImage()));
            }

            return intraUserList;
        }
       catch(CantGetIntraWalletUsersException e) {
            throw new CantGetIntraUsersListException("CAN'T GET INTRA USER WAITING YOUR ACCEPTANCE",e,"","");
        }
        catch(Exception e) {
            throw new CantGetIntraUsersListException("CAN'T GET INTRA USER WAITING YOUR ACCEPTANCE",FermatException.wrapException(e),"","unknown exception");
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
            return getIntraUsersWaitingYourAcceptance(getActiveIntraUserIdentity().getPublicKey(),100,0).size();
        } catch (CantGetIntraUsersListException e) {
            e.printStackTrace();
        } catch (CantGetActiveLoginIdentityException e) {
            e.printStackTrace();
        }
        return 0;
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
    public List<IntraUserInformation> getIntraUsersWaitingTheirAcceptance(String identityPublicKey,int max,int offset) throws CantGetIntraUsersListException {
        try
        {
            List<IntraUserInformation> intraUserList= new ArrayList<IntraUserInformation>();

             List<IntraWalletUser> actorsList = this.intraWalletUserManager.getWaitingTheirAcceptanceIntraWalletUsers(identityPublicKey, max, offset);

            for (IntraWalletUser intraUserActor : actorsList) {
                intraUserList.add(new IntraUserModuleInformation(intraUserActor.getName(),intraUserActor.getPublicKey(),intraUserActor.getProfileImage()));
            }
            return intraUserList;
        }
        catch(CantGetIntraWalletUsersException e)
        {
            throw new CantGetIntraUsersListException("CAN'T GET INTRA USER WAITING THEIR ACCEPTANCE",e,"","Error on IntraUserActor Manager");
        }
        catch(Exception e)
        {
            throw new CantGetIntraUsersListException("CAN'T GET INTRA USER WAITING THEIR ACCEPTANCE",FermatException.wrapException(e),"","unknown exception");
        }
    }

    @Override
    public IntraUserLoginIdentity getActiveIntraUserIdentity() throws CantGetActiveLoginIdentityException
    {
        try {

            IntraUserLoginIdentity intraUserLoginIdentity = null;

            List<com.bitdubai.fermat_ccp_api.layer.identity.intra_wallet_user.interfaces.IntraWalletUser> intraWalletUserList = this.intraWalletUserIdentityManager.getAllIntraWalletUsersFromCurrentDeviceUser();

            for (com.bitdubai.fermat_ccp_api.layer.identity.intra_wallet_user.interfaces.IntraWalletUser intraWalletUser : intraWalletUserList) {
                //TODO: Naty lo saqué esto porque el intraUserLoggedPublicKey está siempre en null, hay que darle valor.
                //if(intraWalletUser.getPublicKey().equals(intraUserLoggedPublicKey)) {
                    intraUserLoginIdentity =  new IntraUserModuleLoginIdentity(intraWalletUser.getAlias(), intraWalletUser.getPublicKey(), intraWalletUser.getProfileImage());
                    break;
                //}
             }

            return intraUserLoginIdentity;

        } catch (CantListIntraWalletUsersException e) {

            throw new CantGetActiveLoginIdentityException("CAN'T GET Active Login Identities",e,"","");
        }
        catch(Exception e)
        {
            throw new CantGetActiveLoginIdentityException("CAN'T GET active Login Identities",FermatException.wrapException(e),"","unknown exception");
        }
    }

    /**
     * DealsWithIntraUsersNetworkService Interface implementation.
     */

    @Override
    public void setIntraUserNetworkServiceManager(IntraUserManager intraUserManager) {
            this.intraUserNertwokServiceManager = intraUserManager;
    }

    /**
     * DealsWithCCPIdentityIntraWalletUser Interface implementation.
     */
    @Override
    public void setIdentityIntraUserManager(com.bitdubai.fermat_ccp_api.layer.identity.intra_wallet_user.interfaces.IntraWalletUserManager intraWalletUserManager) {
        this.intraWalletUserIdentityManager = intraWalletUserManager;
    }

    /**
     * DealsWithActorIntraUser Interface implementation.
     */

    @Override
    public void setIntraWalletUserManager(IntraWalletUserManager intraWalletUserManager) {
        this.intraWalletUserManager = intraWalletUserManager;
    }


    /**
     * DealsWithPluginFileSystem Interface implementation.
     */
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem){
        this.pluginFileSystem = pluginFileSystem;
    }

    /**
     * LogManagerForDevelopers Interface implementation.
     */



    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.module.intra_user.developer.bitdubai.version_1.IntraWalletUserModulePluginRoot");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.module.intra_user.developer.bitdubai.version_1.structure.IntraUserModuleInformation");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.module.intra_user.developer.bitdubai.version_1.structure.IntraUserModuleLoginIdentity");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.module.intra_user.developer.bitdubai.version_1.structure.IntraUserModuleSearch");

        /**
         * I return the values.
         */
        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        /**
         * Modify by Manuel on 25/07/2015
         * I will wrap all this method within a try, I need to catch any generic java Exception
         */
        try{

            /**
             * I will check the current values and update the LogLevel in those which is different
             */
            for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
                /**
                 * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
                 */
                if (IntraWalletUserModulePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                    IntraWalletUserModulePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                    IntraWalletUserModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                } else {
                    IntraWalletUserModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                }
            }

        }catch (Exception exception){
            FermatException e = new CantGetLogTool(CantGetLogTool.DEFAULT_MESSAGE, FermatException.wrapException(exception), "setLoggingLevelPerClass: "+ IntraWalletUserModulePluginRoot.newLoggingLevel ,"Check the cause");
            this.errorManager.reportUnexpectedPluginException(Plugins.INTRA_WALLET_USER, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        }

    }





    /**
     * Service Interface implementation.
     */
    @Override
    public void start() throws CantStartPluginException {
        try
        {
            this.serviceStatus = ServiceStatus.STARTED;

            /**
             * Get from xml file the last intra user logged
             */

            /**
             * load file content
             */

            loadSettingsFile();


            /**
             * get last logged intra user
             */
            this.intraUserLoggedPublicKey = intraUserSettings.getLoggedInPublicKey();

        }
        catch (CantLoadLoginsFileException e)
        {
            throw new CantStartPluginException("Error load logins xml file",e);
        }
        catch(Exception e)
        {
            throw new CantStartPluginException("Unknown Error",e);
        }

    }

        /**
         * private methods
         */

        private void loadSettingsFile() throws CantLoadLoginsFileException {

            try
            {
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

                try{
                    intraUserLoginXml = pluginFileSystem.createTextFile(pluginId, pluginId.toString(), INTRA_USER_LOGIN_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                }
                catch (CantCreateFileException cantCreateFileException ) {
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

                    intraUserLoginXml.persistToMedia();
                }
                catch (CantPersistFileException cantPersistFileException ) {

                    /**
                     * If I can not save this file, then this plugin shouldn't be running at all.
                     */
                    throw new CantLoadLoginsFileException(CantLoadLoginsFileException.DEFAULT_MESSAGE, cantPersistFileException, null, null);
                }
            }
            catch (CantLoadFileException | CantCreateFileException e) {

                /**
                 * In this situation we might have a corrupted file we can not read. For now the only thing I can do is
                 * to prevent the plug-in from running.
                 *
                 * In the future there should be implemented a method to deal with this situation.
                 * * * *
                 */
                throw new CantLoadLoginsFileException(CantLoadLoginsFileException.DEFAULT_MESSAGE, e, null, null);
            }
            catch(Exception ex)
            {
                throw new CantLoadLoginsFileException(CantLoadLoginsFileException.DEFAULT_MESSAGE, FermatException.wrapException(ex), null, null);
            }
        }

}
