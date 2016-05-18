package com.bitdubai.fermat_ccp_plugin.layer.identity.intra_user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.modules.interfaces.FermatSettings;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_ccp_api.layer.actor.Actor;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantCreateNewIntraWalletUserException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantDeleteIdentityException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantListIntraWalletUsersException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantUpdateIdentityException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentity;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentityManager;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.interfaces.IntraUserManager;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.database.IntraWalletUserIdentityDao;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.database.IntraWalletUserIdentityDeveloperDatabaseFactory;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.exceptions.CantInitializeIntraWalletUserIdentityDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.exceptions.CantListIntraWalletUserIdentitiesException;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.exceptions.CantUpdateIntraUserIdentityException;
import com.bitdubai.fermat_pip_api.layer.user.device_user.exceptions.CantGetLoggedInDeviceUserException;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUser;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUserManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An Intra-User identity is used to "authenticate" in a wallet or some sub-apps.
 * The management is done through this plugin.
 * The User can create, list or set a new profile picture for an identity.
 * <p/>
 * The "authentication" is managed in each wallet or sub-app in which is used.
 * <p/>
 * Created by loui on 22/02/15.
 * Modified by Leon Acosta - (laion.cj91@gmail.com) on 07/08/15.z
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

@PluginInfo(createdBy = "Leon Acosta", maintainerMail = "nattyco@gmail.com", platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.DESKTOP_MODULE, plugin = Plugins.WALLET_MANAGER)

public class IntraWalletUserIdentityPluginRoot extends AbstractPlugin
        implements DatabaseManagerForDevelopers,
                    IntraWalletUserIdentityManager,
                   LogManagerForDevelopers {



    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM    )
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM       , layer = Layers.USER           , addon = Addons.DEVICE_USER        )
    private DeviceUserManager deviceUserManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.NETWORK_SERVICE, plugin = Plugins.INTRA_WALLET_USER  )
    private IntraUserManager intraActorManager;

    private IntraWalletUserIdentityDao intraWalletUserIdentityDao;

    private static Map<String, LogLevel> newLoggingLevel = new HashMap<>();

    public static final String INTRA_WALLET_USERS_PROFILE_IMAGE_FILE_NAME = "intraWalletUserIdentityProfileImage";
    public static final String INTRA_WALLET_USERS_PRIVATE_KEYS_FILE_NAME = "intraWalletUserIdentityPrivateKey";

    public IntraWalletUserIdentityPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }


    /**
     * List Intra Users linked to current Device User
     * <p/>
     * The Intra-Users are linked to the device with the publicKey (it can be acquired by deviceUserManager.getLoggedInDeviceUser()
     * <p/>
     * IntraWalletUserIdentityDao is used to database access (select).
     *
     * @return a list of instances of the class IntraWalletUserIdentity found in structure package of the plugin
     * @throws CantListIntraWalletUsersException
     */
    @Override
    public ArrayList<com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentity> getAllIntraWalletUsersFromCurrentDeviceUser() throws CantListIntraWalletUsersException {

        try {

            ArrayList<com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentity> intraWalletUserList1 = new ArrayList<IntraWalletUserIdentity>();


            DeviceUser loggedUser = deviceUserManager.getLoggedInDeviceUser();
            intraWalletUserList1 = intraWalletUserIdentityDao.getAllIntraUserFromCurrentDeviceUser(loggedUser);


            return intraWalletUserList1;

        } catch (CantGetLoggedInDeviceUserException e) {
          throw new CantListIntraWalletUsersException("CAN'T GET INTRA WALLET USER IDENTITIES", e, "Error get logged user device", "");
        } catch (CantListIntraWalletUserIdentitiesException e) {
            throw new CantListIntraWalletUsersException("CAN'T GET INTRA WALLET USER IDENTITIES", e, "", "");
        } catch (Exception e) {
            throw new CantListIntraWalletUsersException("CAN'T GET INTRA WALLET USER IDENTITIES", FermatException.wrapException(e), "", "");
        }
    }

    /**
     * Create an Identity Intra-User
     * <p/>
     * When an User decides to create a new identity, that is achieved throw this method.
     * The new Intra-User is linked to the current device user (it can be acquired by deviceUserManager.getLoggedInDeviceUser()
     * The key-pair is created with a new ECCKeyPair()
     * <p/>
     * The user must have an alias and a profileImage for the identification, if not, give an Exception.
     * <p/>
     * The privateKey must be saved in a file with "$publicKey" like name.
     * The profileImage must be saved in a file with "$publicKey_profileImage" like name.
     * <p/>
     * IntraWalletUserIdentityDao is used to database access (insert).
     *
     * @param alias        the alias that the user choose as intra user identity
     * @param profileImage the profile image to identify this identity
     * @return an instance of the class IntraWalletUserIdentity found in structure package of the plugin
     * @throws CantCreateNewIntraWalletUserException
     */
    @Override
    public IntraWalletUserIdentity createNewIntraWalletUser(String alias, String phrase, byte[] profileImage) throws CantCreateNewIntraWalletUserException {
        try {
            DeviceUser loggedUser = deviceUserManager.getLoggedInDeviceUser();

            ECCKeyPair keyPair = new ECCKeyPair();
           String publicKey = keyPair.getPublicKey();
            String privateKey = keyPair.getPrivateKey();

            intraWalletUserIdentityDao.createNewUser(alias,phrase, publicKey, privateKey, loggedUser, profileImage);

            IntraWalletUserIdentity intraWalletUserIdentity = new com.bitdubai.fermat_ccp_api.layer.identity.intra_user.structure.IntraWalletUserIdentity(alias,phrase, publicKey, privateKey, profileImage);

            //registerIdentities();
            //registerIdentity(intraWalletUserIdentityDao.getAllIntraUserFromCurrentDeviceUser(loggedUser).get(0));
            registerIdentity(intraWalletUserIdentity);

            return intraWalletUserIdentity;
        } catch (CantGetLoggedInDeviceUserException e) {
            throw new CantCreateNewIntraWalletUserException("CAN'T CREATE NEW INTRA WALLET USER IDENTITY", e, "Error getting current logged in device user", "");
        } catch (Exception e) {
            throw new CantCreateNewIntraWalletUserException("CAN'T CREATE NEW INTRA WALLET USER IDENTITY", FermatException.wrapException(e), "", "");
        }

    }

    @Override
    public com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentity createNewIntraWalletUser(String alias,  byte[] profileImage) throws CantCreateNewIntraWalletUserException {
        try {
            DeviceUser loggedUser = deviceUserManager.getLoggedInDeviceUser();

            ECCKeyPair keyPair = new ECCKeyPair();
            String publicKey = keyPair.getPublicKey();
            String privateKey = keyPair.getPrivateKey();

            intraWalletUserIdentityDao.createNewUser(alias,"", publicKey, privateKey, loggedUser, profileImage);

            com.bitdubai.fermat_ccp_api.layer.identity.intra_user.structure.IntraWalletUserIdentity intraWalletUserIdentity = new com.bitdubai.fermat_ccp_api.layer.identity.intra_user.structure.IntraWalletUserIdentity(alias,"", publicKey, privateKey, profileImage);

            registerIdentities();

            return intraWalletUserIdentity;
        } catch (CantGetLoggedInDeviceUserException e) {
            throw new CantCreateNewIntraWalletUserException("CAN'T CREATE NEW INTRA WALLET USER IDENTITY", e, "Error getting current logged in device user", "");
        } catch (Exception e) {
            throw new CantCreateNewIntraWalletUserException("CAN'T CREATE NEW INTRA WALLET USER IDENTITY", FermatException.wrapException(e), "", "");
        }

    }

    @Override
   public boolean  hasIntraUserIdentity() throws CantListIntraWalletUsersException{
        try {

            DeviceUser loggedUser = deviceUserManager.getLoggedInDeviceUser();
            return intraWalletUserIdentityDao.getAllIntraUserFromCurrentDeviceUser(loggedUser).size() > 0;
        } catch (CantGetLoggedInDeviceUserException e) {
            throw new CantListIntraWalletUsersException("CAN'T GET IF INTRA WALLET USER IDENTITIES  EXISTS", e, "Error get logged user device", "");
        } catch (CantListIntraWalletUserIdentitiesException e) {
            throw new CantListIntraWalletUsersException("CAN'T GET IF WALLET USER IDENTITIES EXISTS", e, "", "");

        } catch (Exception e) {
            throw new CantListIntraWalletUsersException("CAN'T GET IF INTRA WALLET USER IDENTITY EXISTS", FermatException.wrapException(e), "", "");
        }
    }

    @Override
    public void updateIntraUserIdentity(String identityPublicKey, String identityAlias, String phrase, byte[] profileImage) throws CantUpdateIdentityException {
            try {
                intraWalletUserIdentityDao.updateIdentity(identityPublicKey,identityAlias,phrase,profileImage);
                updateIdentity(identityPublicKey,identityAlias,phrase,profileImage);

            }
            catch(CantUpdateIntraUserIdentityException e)
            {
                throw new CantUpdateIdentityException("CAN'T UPDATE INTRA USER IDENTITY", e, "", "Error persisting data");
            }
            catch(Exception e)
            {
                throw new CantUpdateIdentityException("CAN'T UPDATE INTRA USER IDENTITY", FermatException.wrapException(e), "", "");
            }
    }

    private void updateIdentity(String publicKey,String alias,String phrase,byte[] img){
        intraActorManager.updateActor(intraActorManager.contructIdentity(publicKey,alias,phrase,Actors.INTRA_USER,img));
    }

    @Override
    public void deleteIntraUserIdentity(String identityPublicKey) throws CantDeleteIdentityException {
        try
        {
            intraWalletUserIdentityDao.deleteIdentity(identityPublicKey);
        }
        catch(CantUpdateIntraUserIdentityException e)
        {
            throw new CantDeleteIdentityException("CAN'T DELETE INTRA USER IDENTITY", e, "", "Error persisting data");
        }
        catch(Exception e)
        {
            throw new CantDeleteIdentityException("CAN'T DELETE INTRA USER IDENTITY", FermatException.wrapException(e), "", "");
        }
    }

    /**
     * Service Interface implementation.
     */
    @Override
    public void start() throws CantStartPluginException {

        try {

            this.intraWalletUserIdentityDao = new IntraWalletUserIdentityDao(pluginDatabaseSystem, this.pluginFileSystem, this.pluginId);
            this.intraWalletUserIdentityDao.initializeDatabase();

            // Register identities
            registerIdentities();

        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        }
        this.serviceStatus = ServiceStatus.STARTED;

    }

    /**
     * DatabaseManagerForDevelopers Interface implementation.
     */

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        IntraWalletUserIdentityDeveloperDatabaseFactory dbFactory = new IntraWalletUserIdentityDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        IntraWalletUserIdentityDeveloperDatabaseFactory dbFactory = new IntraWalletUserIdentityDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseTableList(developerObjectFactory);
    }


    public void registerIdentities(){
        try {
            List<IntraWalletUserIdentity> lstIntraWalletUSer = intraWalletUserIdentityDao.getAllIntraUserFromCurrentDeviceUser(deviceUserManager.getLoggedInDeviceUser());
            List<Actor> lstActors = new ArrayList<Actor>();
            for(IntraWalletUserIdentity user : lstIntraWalletUSer){
                lstActors.add(intraActorManager.contructIdentity(user.getPublicKey(), user.getAlias(), user.getPhrase(),Actors.INTRA_USER,user.getImage()));
            }
            intraActorManager.registrateActors(lstActors);
        } catch (CantListIntraWalletUserIdentitiesException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (CantGetLoggedInDeviceUserException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }

    public void registerIdentity(IntraWalletUserIdentity intraWalletUserIdentity){
        intraActorManager.registrateActor(intraActorManager.contructIdentity(intraWalletUserIdentity.getPublicKey(), intraWalletUserIdentity.getAlias(), intraWalletUserIdentity.getPhrase(), Actors.INTRA_USER, intraWalletUserIdentity.getImage()));

    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {

        try {
            IntraWalletUserIdentityDeveloperDatabaseFactory dbFactory = new IntraWalletUserIdentityDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
            dbFactory.initializeDatabase();
            return dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (CantInitializeIntraWalletUserIdentityDatabaseException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        // If we are here the database could not be opened, so we return an empry list
        return new ArrayList<>();
    }

    /**
     * LogManagerForDevelopers Interface implementation.
     */
    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<>();

        returnedClasses.add("com.bitdubai.fermat_ccp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.IntraWalletUserIdentityPluginRoot");
        returnedClasses.add("com.bitdubai.fermat_ccp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.database.IntraWalletUserIdentityDao");
        returnedClasses.add("com.bitdubai.fermat_ccp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.database.IntraWalletUserIdentityDatabaseConstants");
        returnedClasses.add("com.bitdubai.fermat_ccp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.database.IntraWalletUserIdentityDatabaseFactory");
        returnedClasses.add("com.bitdubai.fermat_ccp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.structure.IntraWalletUserIdentity");

        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        /**
         * I will check the current values and update the LogLevel in those which is different
         */
        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
            /**
             * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
             */
            if (IntraWalletUserIdentityPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                IntraWalletUserIdentityPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                IntraWalletUserIdentityPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                IntraWalletUserIdentityPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }
    }


    @Override
    public SettingsManager<FermatSettings> getSettingsManager() {
        return null;
    }

    @Override
    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException {
        return null;
    }

    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {

    }

    @Override
    public void setAppPublicKey(String publicKey) {

    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }


}
