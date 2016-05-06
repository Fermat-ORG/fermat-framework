package com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
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
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUserManager;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ArtistAcceptConnectionsType;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExposureLevel;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExternalPlatform;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.IdentityNotFoundException;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.WrongTokenlyUserCredentialsException;
import com.bitdubai.fermat_tky_api.all_definitions.interfaces.User;
import com.bitdubai.fermat_tky_api.layer.external_api.exceptions.CantGetUserException;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.TokenlyApiManager;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.ArtistIdentityAlreadyExistsException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantCreateArtistIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantGetArtistIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantListArtistIdentitiesException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantUpdateArtistIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.interfaces.Artist;
import com.bitdubai.fermat_tky_api.layer.identity.artist.interfaces.TokenlyArtistIdentityManager;
import com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.database.TokenlyArtistIdentityDeveloperDatabaseFactory;
import com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.exceptions.CantInitializeTokenlyArtistIdentityDatabaseException;
import com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.structure.TokenlyIdentityArtistManagerImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;


/**
 * Created by Gabriel Araujo on 10/03/16.
 */
@PluginInfo(difficulty = PluginInfo.Dificulty.MEDIUM, maintainerMail = "gabe_512@hotmail.com", createdBy = "gabohub", layer = Layers.IDENTITY, platform = Platforms.TOKENLY, plugin = Plugins.TOKENLY_ARTIST)
public class TokenlyArtistIdentityPluginRoot extends AbstractPlugin implements
        DatabaseManagerForDevelopers,
        TokenlyArtistIdentityManager,
        LogManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    private LogManager logManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.USER, addon = Addons.DEVICE_USER)
    private DeviceUserManager deviceUserManager;

    @NeededPluginReference(platform =  Platforms.TOKENLY, layer = Layers.EXTERNAL_API, plugin = Plugins.TOKENLY_API)
    private TokenlyApiManager tokenlyApiManager;

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    public static final String TOKENLY_ARTIST_IDENTITY_PROFILE_IMAGE = "tokenlyartistIdentityProfileImage";
    public static final String TOKENLY_ARTIST_IDENTITY_PRIVATE_KEY = "tokenlyartistIdentityPrivateKey";

    private TokenlyIdentityArtistManagerImpl identityArtistManager;
    /**
     * Default constructor
     */
    public TokenlyArtistIdentityPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }
    @Override
    public void start() throws CantStartPluginException {
        try {
            this.serviceStatus = ServiceStatus.STARTED;
            identityArtistManager = new TokenlyIdentityArtistManagerImpl(
                    this.errorManager,
                    this.logManager,
                    this.pluginDatabaseSystem,
                    this.pluginFileSystem,
                    this.pluginId,
                    this.deviceUserManager,
                    this.tokenlyApiManager);

            //System.out.println("############\n TKY IDENTITY ARTIST STARTED\n");
            //testCreateArtist();
            //testAskForConnection();
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.TOKENLY_ARTIST, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(e, Plugins.TOKENLY_ARTIST);
        }

//        try {
//            registerIdentitiesANS();
//        } catch (Exception e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
//        }
    }

    private void testCreateArtist(){
        try {
            String alias = "username";
            byte[] image = new byte[0];
            String password = "password";
            ExternalPlatform externalPlatform = ExternalPlatform.TOKENLY;
            ExposureLevel exposureLevel = ExposureLevel.PRIVATE;
            ArtistAcceptConnectionsType artistAcceptConnectionsType = ArtistAcceptConnectionsType.MANUAL;
            Artist artist = createArtistIdentity(alias,image,password,externalPlatform,exposureLevel,artistAcceptConnectionsType);
            Artist artist1 = getArtistIdentity(artist.getId());

            System.out.println("##############################\n");
            System.out.println("artist1 = " + artist1);
//            System.out.println("artist1 ="+ XMLParser.parseObject(new TokenlyArtistIdentityImp(artist1.getId(),artist1.getTokenlyId(),artist1.getPublicKey(),artist1.getProfileImage(),artist1.getUsername(),
//                            artist1.getApiToken(),artist1.getApiSecretKey(),artist1.getExternalPlatform(),artist1.getExposureLevel(),artist1.getArtistAcceptConnectionsType())));
//
//            )
        } catch (CantCreateArtistIdentityException | ArtistIdentityAlreadyExistsException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    private void testUpdateArtist(Artist artist){
//        String externalName = "El gabo artist que envia";
//        String externalAccessToken = "El access token";
//        ExternalPlatform externalPlatform = ExternalPlatform.TOKENLY;
//        ExposureLevel exposureLevel = ExposureLevel.PRIVATE;
//        ArtistAcceptConnectionsType artistAcceptConnectionsType = ArtistAcceptConnectionsType.MANUAL;
//        try {
//            updateArtistIdentity(artist.getAlias(),artist.getPublicKey(),artist.getProfileImage(),externalName,externalAccessToken,externalPlatform,exposureLevel,artistAcceptConnectionsType);
//        } catch (CantUpdateArtistIdentityException e) {
//            e.printStackTrace();
//        }
//    }
    @Override
    public List<Artist> listIdentitiesFromCurrentDeviceUser() throws CantListArtistIdentitiesException {
        return identityArtistManager.getIdentityArtistFromCurrentDeviceUser();
    }

    @Override
    public Artist createArtistIdentity(String userName, byte[] profileImage, String password,ExternalPlatform externalPlatform,
                                       ExposureLevel exposureLevel, ArtistAcceptConnectionsType artistAcceptConnectionsType) throws CantCreateArtistIdentityException, ArtistIdentityAlreadyExistsException, WrongTokenlyUserCredentialsException {
        //TODO: Fix this Gabo. Manuel
        User user=null;
        try{
            if(externalPlatform == ExternalPlatform.DEFAULT_EXTERNAL_PLATFORM)
                user = tokenlyApiManager.validateTokenlyUser(userName, password);
        } catch (CantGetUserException | InterruptedException | ExecutionException  e) {
            e.printStackTrace();
        }
        if(user!=null){
            return identityArtistManager.createNewIdentityArtist(user,password,profileImage,externalPlatform,exposureLevel,artistAcceptConnectionsType);
        }else{
            return null;
        }
    }


    @Override
    public Artist updateArtistIdentity(String username, String password, UUID id, String publicKey, byte[] profileImage, ExternalPlatform externalPlatform,
                                       ExposureLevel exposureLevel, ArtistAcceptConnectionsType artistAcceptConnectionsType) throws CantUpdateArtistIdentityException, WrongTokenlyUserCredentialsException {
        User user=null;
        try{
            if(externalPlatform == ExternalPlatform.DEFAULT_EXTERNAL_PLATFORM)
                user = tokenlyApiManager.validateTokenlyUser(username, password);
        } catch (CantGetUserException |InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        if(user != null)
            return identityArtistManager.updateIdentityArtist(user, password, id, publicKey, profileImage, externalPlatform,exposureLevel,artistAcceptConnectionsType);
        return null;
    }

    @Override
    public Artist getArtistIdentity(UUID id) throws CantGetArtistIdentityException, IdentityNotFoundException {
        return identityArtistManager.getIdentitArtist(id);
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        TokenlyArtistIdentityDeveloperDatabaseFactory dbFactory = new TokenlyArtistIdentityDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        TokenlyArtistIdentityDeveloperDatabaseFactory dbFactory = new TokenlyArtistIdentityDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        try {
            TokenlyArtistIdentityDeveloperDatabaseFactory dbFactory = new TokenlyArtistIdentityDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
            dbFactory.initializeDatabase();
            return dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (CantInitializeTokenlyArtistIdentityDatabaseException e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.TOKENLY_ARTIST, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        // If we are here the database could not be opened, so we return an empty list
        return new ArrayList<>();
    }

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_art_plugin.layer.identity.artist.developer.bitdubai.version_1.ArtistIdentityPluginRoot");
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
                if (newLoggingLevel.containsKey(pluginPair.getKey())) {
                    newLoggingLevel.remove(pluginPair.getKey());
                    newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                } else {
                    newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                }
            }

        } catch (Exception exception) {
            //FermatException e = new CantGetLogTool(CantGetLogTool.DEFAULT_MESSAGE, FermatException.wrapException(exception), "setLoggingLevelPerClass: " + ActorIssuerPluginRoot.newLoggingLevel, "Check the cause");
            // this.errorManager.reportUnexpectedAddonsException(Addons.EXTRA_USER, UnexpectedAddonsExceptionSeverity.DISABLES_THIS_ADDONS, e);
        }
    }
}
