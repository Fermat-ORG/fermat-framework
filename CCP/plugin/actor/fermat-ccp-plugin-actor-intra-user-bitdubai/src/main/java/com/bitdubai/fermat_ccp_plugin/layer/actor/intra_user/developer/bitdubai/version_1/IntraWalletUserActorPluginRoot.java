package com.bitdubai.fermat_ccp_plugin.layer.actor.intra_user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_ccp_api.all_definition.enums.EventType;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_ccp_api.layer.actor.Actor;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.CantAcceptIntraWalletUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.CantCancelIntraWalletUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.CantCreateIntraUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.CantCreateIntraWalletUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.CantCreateNewDeveloperException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.CantDenyConnectionException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.CantDisconnectIntraWalletUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.CantGetIntraUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.CantGetIntraUsersConnectedStateException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.CantGetIntraWalletUsersException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.CantSetPhotoException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.IntraUserNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.interfaces.IntraWalletUserActor;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.interfaces.IntraWalletUserActorManager;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.RequestAlreadySendException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.interfaces.IntraUserManager;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.interfaces.IntraUserNotification;
import com.bitdubai.fermat_ccp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.database.IntraWalletUserActorDao;
import com.bitdubai.fermat_ccp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.database.IntraWalletUserActorDeveloperDatabaseFactory;
import com.bitdubai.fermat_ccp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.event_handlers.IntraWalletUserNewNotificationsEventHandlers;
import com.bitdubai.fermat_ccp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.exceptions.CantAddPendingIntraWalletUserException;
import com.bitdubai.fermat_ccp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.exceptions.CantGetIntraWalletUserActorException;
import com.bitdubai.fermat_ccp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.exceptions.CantGetIntraWalletUsersListException;
import com.bitdubai.fermat_ccp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.exceptions.CantInitializeIntraWalletUserActorDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.exceptions.CantLoadPrivateKeyException;
import com.bitdubai.fermat_ccp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.exceptions.CantPersistPrivateKeyException;
import com.bitdubai.fermat_ccp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.exceptions.CantProcessNotificationsExceptions;
import com.bitdubai.fermat_ccp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.exceptions.CantUpdateConnectionException;
import com.bitdubai.fermat_ccp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.structure.IntraUserActorRecord;
import com.bitdubai.fermat_pip_api.layer.actor.exception.CantGetLogTool;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This plugin manages connections between users of the platform..
 * Provides contact information of Intra User
 * <p/>
 * Created by loui on 22/02/15.
 * modified by Natalia on 11/08/2015
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class IntraWalletUserActorPluginRoot extends AbstractPlugin implements
        DatabaseManagerForDevelopers,
        IntraWalletUserActorManager,
        LogManagerForDevelopers
{
    private IntraWalletUserActorDao intraWalletUserActorDao;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.NETWORK_SERVICE, plugin = Plugins.INTRA_WALLET_USER)
    private IntraUserManager intraUserNetworkServiceManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_BROADCASTER_SYSTEM)
    private Broadcaster broadcaster;

    private final List<FermatEventListener> listenersAdded = new ArrayList<>();

    /**
     * DealsWithLogger interface member variable
     */

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    /**
     * Service Interface member variables.
     */
    //ServiceStatus serviceStatus = ServiceStatus.CREATED;

    public static final String INTRA_USERS_PRIVATE_KEYS_DIRECTORY_NAME = "intraUserIdentityPrivateKeys";

    /**
     * AbstractPlugin interface implementation.
     */

    public IntraWalletUserActorPluginRoot() {
        super(new PluginVersionReference(new Version()));

    }

    /**
     * ActorIntraWalletUserManager interface implementation.
     */


    /**
     * That method registers a new intra user in the list
     * managed by this plugin with ContactState PENDING_HIS_ACCEPTANCE until the other intra user
     * accepts the connection request sent also by this method.
     *
     * @param intraUserLoggedInPublicKey The public key of the intra user sending the connection request.
     * @param intraUserToAddName         The name of the intra user to add
     * @param intraUserToAddPublicKey    The public key of the intra user to add
     * @param profileImage               The profile image that the intra user has
     * @throws CantCreateIntraWalletUserException
     */

    @Override
    public void askIntraWalletUserForAcceptance(String intraUserLoggedInPublicKey, String intraUserToAddName, String intraUserPhrase,String intraUserToAddPublicKey, byte[] profileImage) throws CantCreateIntraWalletUserException,RequestAlreadySendException {
        try {

            if (intraWalletUserActorDao.intraUserRequestExists(intraUserToAddPublicKey, ConnectionState.PENDING_REMOTELY_ACCEPTANCE)) {

                throw new RequestAlreadySendException("CAN'T INSERT INTRA USER", null, "", "The request already sent to actor.");

            } else if (intraWalletUserActorDao.intraUserRequestExists(intraUserToAddPublicKey, ConnectionState.PENDING_LOCALLY_ACCEPTANCE)){

                this.intraWalletUserActorDao.updateConnectionState(intraUserLoggedInPublicKey, intraUserToAddPublicKey, ConnectionState.CONNECTED);

            }else{
                this.intraWalletUserActorDao.createNewIntraWalletUser(intraUserLoggedInPublicKey, intraUserToAddName, intraUserToAddPublicKey, profileImage, ConnectionState.PENDING_REMOTELY_ACCEPTANCE,intraUserPhrase);
            }
        } catch (CantAddPendingIntraWalletUserException e) {
            throw new CantCreateIntraWalletUserException("CAN'T ADD NEW INTRA USER CONNECTION", e, "", "");
        } catch (RequestAlreadySendException e) {
            throw new RequestAlreadySendException("CAN'T ADD NEW INTRA USER CONNECTION", e, "", "The request already send.");

        } catch (Exception e) {
            throw new CantCreateIntraWalletUserException("CAN'T ADD NEW INTRA USER CONNECTION", FermatException.wrapException(e), "", "");
        }

    }


    /**
     * That method takes the information of a connection request, accepts
     * the request and adds the intra user to the list managed by this plugin with ContactState CONTACT.
     *
     * @param intraUserLoggedInPublicKey The public key of the intra user sending the connection request.
     * @param intraUserToAddPublicKey    The public key of the intra user to add
     * @throws CantAcceptIntraWalletUserException
     */

    @Override
    public void acceptIntraWalletUser(String intraUserLoggedInPublicKey, String intraUserToAddPublicKey) throws CantAcceptIntraWalletUserException {
        try {
            this.intraWalletUserActorDao.updateConnectionState(intraUserLoggedInPublicKey, intraUserToAddPublicKey, ConnectionState.CONNECTED);
        } catch (CantUpdateConnectionException e) {
            throw new CantAcceptIntraWalletUserException("CAN'T ACCEPT INTRA USER CONNECTION", e, "", "");
        } catch (Exception e) {
            throw new CantAcceptIntraWalletUserException("CAN'T ACCEPT INTRA USER CONNECTION", FermatException.wrapException(e), "", "");
        }
    }


    /**
     * That method rejects a connection request from another intra user
     *
     * @param intraUserLoggedInPublicKey The public key of the intra user identity that is the receptor of the request
     * @param intraUserToRejectPublicKey The public key of the intra user that sent the request
     * @throws CantDenyConnectionException
     */
    @Override
    public void denyConnection(String intraUserLoggedInPublicKey, String intraUserToRejectPublicKey) throws CantDenyConnectionException {

        try {
            this.intraWalletUserActorDao.updateConnectionState(intraUserLoggedInPublicKey, intraUserToRejectPublicKey, ConnectionState.DENIED_LOCALLY);
        } catch (CantUpdateConnectionException e) {
            throw new CantDenyConnectionException("CAN'T DENY INTRA USER CONNECTION", e, "", "");
        } catch (Exception e) {
            throw new CantDenyConnectionException("CAN'T DENY INTRA USER CONNECTION", FermatException.wrapException(e), "", "");
        }
    }

    /**
     * That method disconnect an intra user from the connections registry
     *
     * @param intraUserLoggedInPublicKey     The public key of the intra user identity that is the receptor of the request
     * @param intraUserToDisconnectPublicKey The public key of the intra user to disconnect as connection
     * @throws CantDisconnectIntraWalletUserException
     */
    @Override
    public void disconnectIntraWalletUser(String intraUserLoggedInPublicKey, String intraUserToDisconnectPublicKey) throws CantDisconnectIntraWalletUserException {
        try {
            this.intraWalletUserActorDao.updateConnectionState(intraUserLoggedInPublicKey, intraUserToDisconnectPublicKey, ConnectionState.DISCONNECTED_REMOTELY);
        } catch (CantUpdateConnectionException e) {
            throw new CantDisconnectIntraWalletUserException("CAN'T CANCEL INTRA USER CONNECTION", e, "", "");
        } catch (Exception e) {
            throw new CantDisconnectIntraWalletUserException("CAN'T CANCEL INTRA USER CONNECTION", FermatException.wrapException(e), "", "");
        }
    }


    /**
     * That method cancels an intra user from the connections registry
     *
     * @param intraUserLoggedInPublicKey The public key of the intra user identity that is the receptor of the request
     * @param intraUserToCancelPublicKey The public key of the intra user to cancel as connection
     * @throws CantCancelIntraWalletUserException
     */
    @Override
    public void cancelIntraWalletUser(String intraUserLoggedInPublicKey, String intraUserToCancelPublicKey) throws CantCancelIntraWalletUserException {
        try {
            this.intraWalletUserActorDao.updateConnectionState(intraUserLoggedInPublicKey, intraUserToCancelPublicKey, ConnectionState.CANCELLED_LOCALLY);
        } catch (CantUpdateConnectionException e) {
            throw new CantCancelIntraWalletUserException("CAN'T CANCEL INTRA USER CONNECTION", e, "", "");
        } catch (Exception e) {
            throw new CantCancelIntraWalletUserException("CAN'T CANCEL INTRA USER CONNECTION", FermatException.wrapException(e), "", "");
        }
    }

    /**
     * That method get the list of all intra users that are connections of the logged in one.
     *
     * @param intraUserLoggedInPublicKey the public key of the intra user logged in
     * @return the list of intra users the logged in intra user has as connections.
     * @throws CantGetIntraWalletUsersException
     */
    @Override
    public List<IntraWalletUserActor> getAllIntraWalletUsers(String intraUserLoggedInPublicKey, int max, int offset) throws CantGetIntraWalletUsersException {
        try {
            return this.intraWalletUserActorDao.getAllConnectedIntraWalletUsers(intraUserLoggedInPublicKey, max, offset);
        } catch (CantGetIntraWalletUsersListException e) {
            throw new CantGetIntraWalletUsersException("CAN'T LIST INTRA USER CONNECTIONS", e, "", "");
        } catch (Exception e) {
            throw new CantGetIntraWalletUsersException("CAN'T LIST INTRA USER CONNECTIONS", FermatException.wrapException(e), "", "");
        }
    }


    @Override
    public List<IntraWalletUserActor> getConnectedIntraWalletUsers(String intraUserLoggedInPublicKey) throws CantGetIntraWalletUsersException {
        try {
            return this.intraWalletUserActorDao.getAllConnectedIntraWalletUsers(intraUserLoggedInPublicKey,ConnectionState.CONNECTED,100,0);

        }
        catch (CantGetIntraWalletUsersListException e) {
            throw new CantGetIntraWalletUsersException("CAN'T LIST INTRA USER CONNECTIONS", e, "", "");
        }
        catch (Exception e) {
            throw new CantGetIntraWalletUsersException("CAN'T LIST INTRA USER CONNECTIONS", FermatException.wrapException(e), "", "");
        }
    }



    /**
     * That method get the list of all intra users
     * that sent a connection request and are waiting for the acceptance of the logged in one.
     *
     * @param intraUserLoggedInPublicKey the public key of the intra user logged in
     * @return the list of intra users the logged in intra user has as connections.
     * @throws CantGetIntraWalletUsersException
     */

    @Override
    public List<IntraWalletUserActor> getWaitingYourAcceptanceIntraWalletUsers(String intraUserLoggedInPublicKey, int max, int offset) throws CantGetIntraWalletUsersException {
        try {
            return this.intraWalletUserActorDao.getAllConnectedIntraWalletUsers(intraUserLoggedInPublicKey, ConnectionState.PENDING_LOCALLY_ACCEPTANCE, max, offset);
        } catch (CantGetIntraWalletUsersListException e) {
            throw new CantGetIntraWalletUsersException("CAN'T LIST INTRA USER ACCEPTED CONNECTIONS", e, "", "");
        } catch (Exception e) {
            throw new CantGetIntraWalletUsersException("CAN'T LIST INTRA USER ACCEPTED CONNECTIONS", FermatException.wrapException(e), "", "");
        }
    }


    /**
     * That method get  the list of all intra users
     * that the logged in one has sent connections request to and have not been answered yet..
     *
     * @param intraUserLoggedInPublicKey the public key of the intra user logged in
     * @return the list of intra users the logged in intra user has as connections.
     * @throws CantGetIntraWalletUsersException
     */

    @Override
    public List<IntraWalletUserActor> getWaitingTheirAcceptanceIntraWalletUsers(String intraUserLoggedInPublicKey, int max, int offset) throws CantGetIntraWalletUsersException {
        try {
            return this.intraWalletUserActorDao.getAllConnectedIntraWalletUsers(intraUserLoggedInPublicKey, ConnectionState.PENDING_REMOTELY_ACCEPTANCE, max, offset);
        } catch (CantGetIntraWalletUsersListException e) {
            throw new CantGetIntraWalletUsersException("CAN'T LIST INTRA USER PENDING_HIS_ACCEPTANCE CONNECTIONS", e, "", "");
        } catch (Exception e) {
            throw new CantGetIntraWalletUsersException("CAN'T LIST INTRA USER PENDING_HIS_ACCEPTANCE CONNECTIONS", FermatException.wrapException(e), "", "");
        }
    }

    @Override
    public void receivingIntraWalletUserRequestConnection(String intraUserLoggedInPublicKey, String intraUserToAddName, String intraUserPhrase, String intraUserToAddPublicKey, byte[] profileImage) throws CantCreateIntraWalletUserException {
        try {

            /**
             * if intra user exist on table
             * return error
             */
             if (intraWalletUserActorDao.intraUserRequestExists(intraUserToAddPublicKey, ConnectionState.PENDING_REMOTELY_ACCEPTANCE)){

                    this.intraWalletUserActorDao.updateConnectionState(intraUserLoggedInPublicKey, intraUserToAddPublicKey, ConnectionState.CONNECTED);
                    this.intraUserNetworkServiceManager.acceptIntraUser(intraUserLoggedInPublicKey,intraUserToAddPublicKey);
                }
            else{

                 if (!intraWalletUserActorDao.intraUserRequestExists(intraUserToAddPublicKey,ConnectionState.CONNECTED) || !intraWalletUserActorDao.intraUserRequestExists(intraUserToAddPublicKey, ConnectionState.PENDING_LOCALLY_ACCEPTANCE))
                        this.intraWalletUserActorDao.createNewIntraWalletUser(intraUserLoggedInPublicKey, intraUserToAddName, intraUserToAddPublicKey, profileImage, ConnectionState.PENDING_LOCALLY_ACCEPTANCE,intraUserPhrase);
            }



         } catch (CantAddPendingIntraWalletUserException e) {
            throw new CantCreateIntraWalletUserException("CAN'T ADD NEW INTRA USER REQUEST CONNECTION", e, "", "");

        } catch (RequestAlreadySendException e) {

            //the intra user connection request already exist don't process

        } catch (Exception e) {
            throw new CantCreateIntraWalletUserException("CAN'T ADD NEW INTRA USER REQUEST CONNECTION", FermatException.wrapException(e), "", "");
        }

    }




    @Override
    public Actor createActor(String intraUserLoggedInPublicKey, String actorName, byte[] photo) throws CantCreateIntraUserException{

     /*   ECCKeyPair keyPair = new ECCKeyPair();
       String publicKey = keyPair.getPublicKey();
        String privateKey = keyPair.getPrivateKey();

        try {

            persistPrivateKey(privateKey, publicKey);

            intraWalletUserActorDao.createActorIntraWalletUser(intraUserLoggedInPublicKey, actorName, publicKey, photo, ConnectionState.CONNECTED);
        }
        catch (CantCreateIntraWalletUserException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_INTRA_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateIntraUserException(CantCreateIntraUserException.DEFAULT_MESSAGE, e, "Cannot create .", null);

        }
        catch (CantPersistPrivateKeyException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_INTRA_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateIntraUserException(CantCreateIntraUserException.DEFAULT_MESSAGE, e, "Cannot persist private key file.", null);
        }
        catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_INTRA_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateIntraUserException(CantCreateIntraUserException.DEFAULT_MESSAGE, FermatException.wrapException(e), "There is a problem I can't identify.", null);
        }

        return new IntraUserActorRecord(publicKey, privateKey, actorName);*/
        return null;

    }



    @Override
    public Actor getActorByPublicKey(String intraUserLoggedInPublicKey, String actorPublicKey) throws CantGetIntraUserException, IntraUserNotFoundException{

        try
        {
           // String privateKey = getPrivateKey(actorPublicKey);

            Actor actor = this.intraWalletUserActorDao.getIntraUserActorByPublicKey(intraUserLoggedInPublicKey,actorPublicKey);

            //not found actor
            if(actor == null)
                throw new IntraUserNotFoundException("", null, ".","Intra User not found");

            return new IntraUserActorRecord(actorPublicKey, "",actor.getName(), actor.getPhoto());
        }
        catch(CantGetIntraWalletUserActorException e)
        {
            throw new CantGetIntraUserException("CAN'T GET INTRA USER ACTOR", e, "", "database error");
        }
        catch(Exception e)
        {
            throw new CantGetIntraUserException("CAN'T GET INTRA USER ACTOR", FermatException.wrapException(e), "", "unknown error");
        }


    }


    @Override
    public void setPhoto(String actorPublicKey, byte[] photo) throws CantSetPhotoException, IntraUserNotFoundException{

    }

    @Override
    public boolean isActorConnected(String publicKey) throws CantCreateNewDeveloperException {

        try {
            return  intraWalletUserActorDao.intraUserRequestExists(publicKey, ConnectionState.CONNECTED) ;
        } catch (CantGetIntraWalletUserActorException e) {
            throw new CantCreateNewDeveloperException("CAN'T GET INTRA USER ACTOR IS CONNECTED", e, "", "database error");
        }


    }

    @Override
    public IntraWalletUserActor getLastNotification(String intraUserConnectedPublicKey) throws CantGetIntraUserException {

        try {

            return intraWalletUserActorDao.getLastNotification(intraUserConnectedPublicKey);

        } catch (CantGetIntraWalletUsersListException e) {
            throw new CantGetIntraUserException("CAN'T GET INTRA USER LAST NOTIFICATION", e, "Error get database info", "");
        } catch (Exception e) {
            throw new CantGetIntraUserException("CAN'T GET INTRA USER LAST NOTIFICATION", FermatException.wrapException(e), "", "");
        }
    }


    @Override
    public ConnectionState getIntraUsersConnectionStatus(String intraUserConnectedPublicKey) throws CantGetIntraUsersConnectedStateException {

        try {

            IntraWalletUserActor intraWalletUserActor;

            intraWalletUserActor = intraWalletUserActorDao.getIntraUserConnectedInfo(intraUserConnectedPublicKey);

            if(intraWalletUserActor != null)
                return intraWalletUserActor.getContactState();
            else
                return ConnectionState.NO_CONNECTED;


        } catch (CantGetIntraWalletUsersListException e) {
            throw new CantGetIntraUsersConnectedStateException("CAN'T GET INTRA USER CONNECTED STATUS", e, "Error get database info", "");
        } catch (Exception e) {
            throw new CantGetIntraUsersConnectedStateException("CAN'T GET INTRA USER CONNECTED STATUS", FermatException.wrapException(e), "", "");
        }
    }


    private void persistPrivateKey(String privateKey, String publicKey) throws CantPersistPrivateKeyException {
        try {
            PluginTextFile file = this.pluginFileSystem.createTextFile(
                    pluginId,
                    DeviceDirectory.LOCAL_USERS.getName() + "/" + INTRA_USERS_PRIVATE_KEYS_DIRECTORY_NAME,
                    publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.setContent(privateKey);

            file.persistToMedia();

        } catch (CantPersistFileException | CantCreateFileException e) {
            throw new CantPersistPrivateKeyException(CantPersistPrivateKeyException.DEFAULT_MESSAGE, e, "Error creating or persisting file.", null);
        } catch (Exception e) {
            throw new CantPersistPrivateKeyException(CantPersistPrivateKeyException.DEFAULT_MESSAGE, FermatException.wrapException(e), "", "");
        }
    }

    private String getPrivateKey(String publicKey) throws CantLoadPrivateKeyException {
        try {
            PluginTextFile file = this.pluginFileSystem.getTextFile(
                    pluginId,
                    DeviceDirectory.LOCAL_USERS.getName() + "/" + INTRA_USERS_PRIVATE_KEYS_DIRECTORY_NAME,
                    publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );
            file.loadFromMedia();
            return file.getContent();
        } catch (CantLoadFileException e) {
            throw new CantLoadPrivateKeyException(CantLoadPrivateKeyException.DEFAULT_MESSAGE, e, "Error persist file.", null);
        } catch (CantCreateFileException e) {
            throw new CantLoadPrivateKeyException(CantLoadPrivateKeyException.DEFAULT_MESSAGE, e, "Error creating file.", null);
        } catch (Exception e) {
            throw new CantLoadPrivateKeyException(CantLoadPrivateKeyException.DEFAULT_MESSAGE, FermatException.wrapException(e), "", "unknown error");
        }
    }



    /**
     * Service Interface implementation.
     */
    @Override
    public void start() throws CantStartPluginException {
        try {
            /**
             * I created instance of IntraWalletUserActorDao
             * and initialize Database
             */
            this.intraWalletUserActorDao = new IntraWalletUserActorDao(pluginDatabaseSystem, this.pluginFileSystem, this.pluginId);

            this.intraWalletUserActorDao.initializeDatabase();

            /**
             * I will initialize the handling of com.bitdubai.platform events.
             */

            FermatEventListener fermatEventListener;
            FermatEventHandler fermatEventHandler;



            /**
             * Listener NetWorkService New Notifications event
             */
            fermatEventListener = eventManager.getNewListener(EventType.ACTOR_NETWORK_SERVICE_NEW_NOTIFICATIONS);
            fermatEventHandler = new IntraWalletUserNewNotificationsEventHandlers();
            ((IntraWalletUserNewNotificationsEventHandlers) fermatEventHandler).setIntraWalletUserManager(this);
            ((IntraWalletUserNewNotificationsEventHandlers) fermatEventHandler).setIntraUserManager(this.intraUserNetworkServiceManager);
            fermatEventListener.setEventHandler(fermatEventHandler);

            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            /**
             * I ask the list of pending requests to the Network Service to execute
             */

            try {
                this.processNotifications();
            }catch (Exception e){
                e.printStackTrace();
            }

            // set plugin status Started
            this.serviceStatus = ServiceStatus.STARTED;


        } catch (CantInitializeIntraWalletUserActorDatabaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_INTRA_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(e, Plugins.BITDUBAI_CCP_INTRA_USER_ACTOR);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_INTRA_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(e, Plugins.BITDUBAI_CCP_INTRA_USER_ACTOR);
        }

    }

    /**
     * DatabaseManagerForDevelopers Interface implementation.
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        IntraWalletUserActorDeveloperDatabaseFactory dbFactory = new IntraWalletUserActorDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseList(developerObjectFactory);


    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        IntraWalletUserActorDeveloperDatabaseFactory dbFactory = new IntraWalletUserActorDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {

        try {
            IntraWalletUserActorDeveloperDatabaseFactory dbFactory = new IntraWalletUserActorDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
            dbFactory.initializeDatabase();
            return dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (CantInitializeIntraWalletUserActorDatabaseException e) {
            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_INTRA_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (Exception e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_INTRA_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        // If we are here the database could not be opened, so we return an empry list
        return new ArrayList<>();
    }


    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("IntraWalletUserActorPluginRoot");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.structure.ActorIntraWalletUser");

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
        try {

            /**
             * I will check the current values and update the LogLevel in those which is different
             */
            for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
                /**
                 * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
                 */
                if (IntraWalletUserActorPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                    IntraWalletUserActorPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                    IntraWalletUserActorPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                } else {
                    IntraWalletUserActorPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                }
            }

        } catch (Exception exception) {
            FermatException e = new CantGetLogTool(CantGetLogTool.DEFAULT_MESSAGE, FermatException.wrapException(exception), "setLoggingLevelPerClass: " + IntraWalletUserActorPluginRoot.newLoggingLevel, "Check the cause");
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_INTRA_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        }

    }


/**
 * Private methods
 */

    /**
     * Procces the list o f notifications from Intra User Network Services
     * And update intra user actor contact state
     *
     * @throws CantProcessNotificationsExceptions
     */
    public void processNotifications() throws CantProcessNotificationsExceptions {

        try {

            System.out.println("PROCESSING NOTIFICATIONS IN INTRA USER WALLET ");
            List<IntraUserNotification> intraUserNotifications = intraUserNetworkServiceManager.getPendingNotifications();


            for (IntraUserNotification notification : intraUserNotifications) {

                String intraUserSendingPublicKey = notification.getActorSenderPublicKey();

                String intraUserToConnectPublicKey = notification.getActorDestinationPublicKey();

                switch (notification.getNotificationDescriptor()) {
                    //ASKFORACCEPTANCE occurs when other user request you a connection
                    case ASKFORACCEPTANCE:
                          this.receivingIntraWalletUserRequestConnection(intraUserToConnectPublicKey, notification.getActorSenderAlias(),notification.getActorSenderPhrase(), intraUserSendingPublicKey, notification.getActorSenderProfileImage());
                     break;
                    case CANCEL:
                        this.cancelIntraWalletUser(intraUserToConnectPublicKey,intraUserSendingPublicKey);
                        break;
                    case ACCEPTED:
                        this.acceptIntraWalletUser(intraUserToConnectPublicKey,intraUserSendingPublicKey);
//                        /**
//                         * fire event "INTRA_USER_CONNECTION_ACCEPTED_NOTIFICATION"
//                         */
//                        eventManager.raiseEvent(eventManager.getNewEvent(EventType.INTRA_USER_CONNECTION_ACCEPTED_NOTIFICATION));
                        //notify android view
                        broadcaster.publish(BroadcasterType.UPDATE_VIEW,"ACCEPTED_CONEXION");
                        broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, SubAppsPublicKeys.CCP_COMMUNITY.getCode(),"CONNECTIONACCEPT_" + intraUserSendingPublicKey);

                        break;
                    case DISCONNECTED:
                        this.disconnectIntraWalletUser(intraUserToConnectPublicKey, intraUserSendingPublicKey);
                        break;
                    case RECEIVED:
                        /**
                         * fire event "INTRA_USER_CONNECTION_REQUEST_RECEIVED_NOTIFICATION"
                         */
                        //eventManager.raiseEvent(eventManager.getNewEvent(EventType.INTRA_USER_CONNECTION_REQUEST_RECEIVED_NOTIFICATION));
                        break;
                    case DENIED:
                        this.denyConnection(intraUserToConnectPublicKey, intraUserSendingPublicKey);
                        break;
                    case INTRA_USER_NOT_FOUND:
                        this.intraWalletUserActorDao.updateConnectionState(intraUserToConnectPublicKey, intraUserSendingPublicKey, ConnectionState.INTRA_USER_NOT_FOUND);

                        break;
                    default:
                        break;

                }

                /**
                 * I confirm the application in the Network Service
                 */
                //TODO: VER PORQUE TIRA ERROR
                intraUserNetworkServiceManager.confirmNotification(notification.getId());

            }


        } catch (CantAcceptIntraWalletUserException e) {
            throw new CantProcessNotificationsExceptions("CAN'T PROCESS NETWORK SERVICE NOTIFICATIONS", e, "", "Error Update Contact State to Accepted");

        } catch (CantDisconnectIntraWalletUserException e) {
            throw new CantProcessNotificationsExceptions("CAN'T PROCESS NETWORK SERVICE NOTIFICATIONS", e, "", "Error Update Contact State to Disconnected");

        } catch (CantDenyConnectionException e) {
            throw new CantProcessNotificationsExceptions("CAN'T PROCESS NETWORK SERVICE NOTIFICATIONS", e, "", "Error Update Contact State to Denied");

        } catch (Exception e) {
            throw new CantProcessNotificationsExceptions("CAN'T PROCESS NETWORK SERVICE NOTIFICATIONS", FermatException.wrapException(e), "", "");

        }
    }
}











