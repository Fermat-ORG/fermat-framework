package com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUserManager;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExternalPlatform;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.IdentityNotFoundException;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.TokenlyApiManager;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantCreateFanIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantGetFanIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantListFanIdentitiesException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantUpdateFanIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.Fan;
import com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.TokenlyFanIdentityManager;
import com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.database.TokenlyFanIdentityDeveloperDatabaseFactory;
import com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.exceptions.CantInitializeTokenlyFanIdentityDatabaseException;
import com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.structure.TokenlyFanIdentityImp;
import com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.structure.TokenlyIdentityFanManagerImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * Created by Gabriel Araujo on 10/03/16.
 */
public class TokenlyFanIdentityPluginRoot extends AbstractPlugin implements
        DatabaseManagerForDevelopers,
        TokenlyFanIdentityManager,
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

    public static final String TOKENLY_FAN_IDENTITY_PROFILE_IMAGE = "tokenlyfanIdentityProfileImage";
    public static final String TOKENLY_FAN_IDENTITY_PRIVATE_KEY = "tokenlyfanIdentityPrivateKey";

    private TokenlyIdentityFanManagerImpl identityArtistManager;
    /**
     * Default constructor
     */
    public TokenlyFanIdentityPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }
    @Override
    public void start() throws CantStartPluginException {
        try {
            this.serviceStatus = ServiceStatus.STARTED;
            identityArtistManager = new TokenlyIdentityFanManagerImpl(
                    this.errorManager,
                    this.logManager,
                    this.pluginDatabaseSystem,
                    this.pluginFileSystem,
                    this.pluginId,
                    this.deviceUserManager,
                    this.tokenlyApiManager);

            System.out.println("############\n TKY IDENTITY FAN STARTED\n");
            testCreateArtist();
            //testAskForConnection();
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.ARTIST_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(e, Plugins.ARTIST_IDENTITY);
        }

//        try {
//            registerIdentitiesANS();
//        } catch (Exception e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
//        }
    }

    private void testCreateArtist(){
        try {
            String alias = "Gabo";
            byte[] image = new byte[0];
            String externalName = "El gabo fan";
            String externalAccessToken = "El access token";
            ExternalPlatform externalPlatform = ExternalPlatform.TOKENLY;
            Fan fan = createFanIdentity(alias, image, externalName, externalAccessToken, externalPlatform);
            Fan fan1 = getFanIdentity(fan.getId());
            System.out.println("##############################\n");
            System.out.println("fan1 = " + XMLParser.parseObject(new TokenlyFanIdentityImp(fan1.getAlias(), fan1.getId(), fan1.getPublicKey(), fan1.getProfileImage(), fan1.getExternalUsername(),
                    fan1.getExternalAccesToken(), fan1.getExternalPlatform())));
        } catch (CantCreateFanIdentityException e) {
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
//        } catch (CantCreateFanIdentityException e) {
//            e.printStackTrace();
//        }
//    }
    @Override
    public List<Fan> listIdentitiesFromCurrentDeviceUser() throws CantListFanIdentitiesException {
        return identityArtistManager.getIdentityFanFromCurrentDeviceUser();
    }

    @Override
    public Fan createFanIdentity(String alias, byte[] profileImage, String externalUserName, String externalAccessToken, ExternalPlatform externalPlatform) throws  CantCreateFanIdentityException {
        if(tokenlyApiManager.isTokenlyAccessVaild(externalUserName,externalAccessToken)){
            return identityArtistManager.createNewIdentityFan(alias, profileImage, externalUserName, externalAccessToken, externalPlatform);
        }else{
            return null;
        }
    }


    @Override
    public void updateFanIdentity(String alias, UUID id,String publicKey, byte[] profileImage, String externalUserName, String externalAccessToken, ExternalPlatform externalPlatform) throws CantUpdateFanIdentityException {
        identityArtistManager.updateIdentityFan(alias, id, publicKey, profileImage, externalUserName, externalAccessToken, externalPlatform);
    }

    @Override
    public Fan getFanIdentity(UUID id) throws CantGetFanIdentityException, IdentityNotFoundException {
        return identityArtistManager.getIdentitFan(id);
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        TokenlyFanIdentityDeveloperDatabaseFactory dbFactory = new TokenlyFanIdentityDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        TokenlyFanIdentityDeveloperDatabaseFactory dbFactory = new TokenlyFanIdentityDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        try {
            TokenlyFanIdentityDeveloperDatabaseFactory dbFactory = new TokenlyFanIdentityDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
            dbFactory.initializeDatabase();
            return dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (CantInitializeTokenlyFanIdentityDatabaseException e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.TOKENLY_FAN, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
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
