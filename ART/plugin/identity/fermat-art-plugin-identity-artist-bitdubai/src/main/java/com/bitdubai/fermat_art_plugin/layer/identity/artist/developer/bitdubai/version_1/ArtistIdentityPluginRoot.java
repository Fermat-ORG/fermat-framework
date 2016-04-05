package com.bitdubai.fermat_art_plugin.layer.identity.artist.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_art_api.all_definition.enums.ArtistAcceptConnectionsType;
import com.bitdubai.fermat_art_api.all_definition.enums.ExposureLevel;
import com.bitdubai.fermat_art_api.all_definition.enums.ExternalPlatform;
import com.bitdubai.fermat_art_api.all_definition.exceptions.CantHideIdentityException;
import com.bitdubai.fermat_art_api.all_definition.exceptions.CantPublishIdentityException;
import com.bitdubai.fermat_art_api.all_definition.exceptions.IdentityNotFoundException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.ArtistManager;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.ArtistIdentityAlreadyExistsException;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.CantCreateArtistIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.CantGetArtistIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.CantListArtistIdentitiesException;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.CantUpdateArtistIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.artist.interfaces.Artist;
import com.bitdubai.fermat_art_api.layer.identity.artist.interfaces.ArtistIdentityManager;
import com.bitdubai.fermat_art_plugin.layer.identity.artist.developer.bitdubai.version_1.database.ArtistIdentityDeveloperDatabaseFactory;
import com.bitdubai.fermat_art_plugin.layer.identity.artist.developer.bitdubai.version_1.exceptions.CantInitializeArtistIdentityDatabaseException;
import com.bitdubai.fermat_art_plugin.layer.identity.artist.developer.bitdubai.version_1.structure.IdentityArtistManagerImpl;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUserManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Gabriel Araujo on 10/03/16.
 */
public class ArtistIdentityPluginRoot extends AbstractPlugin implements
        DatabaseManagerForDevelopers,
        ArtistIdentityManager,
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

    @NeededPluginReference(platform =  Platforms.ART_PLATFORM, layer = Layers.ACTOR_NETWORK_SERVICE, plugin = Plugins.ARTIST)
    private ArtistManager artistManager;

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    public static final String ARTIST_PROFILE_IMAGE_FILE_NAME = "artistIdentityProfileImage";
    public static final String ARTIST_PRIVATE_KEYS_FILE_NAME = "artistIdentityPrivateKey";

    private IdentityArtistManagerImpl identityArtistManager;
    /**
     * Default constructor
     */
    public ArtistIdentityPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }
    @Override
    public void start() throws CantStartPluginException {
        try {
            this.serviceStatus = ServiceStatus.STARTED;
            identityArtistManager = new IdentityArtistManagerImpl(
                    this.errorManager,
                    this.logManager,
                    this.pluginDatabaseSystem,
                    this.pluginFileSystem,
                    this.pluginId,
                    this.deviceUserManager,
                    this.artistManager);

            System.out.println("############\n ART IDENTITY ARTIST STARTED\n");
            //testCreateArtist();
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

//    public void testAskForConnection()  {
//        try {
//            List<Artist> artists = listIdentitiesFromCurrentDeviceUser();
//            Artist transmisor = artists.get(0);
//            List<ArtistActor> artistActors = artistManager.getListActorArtistRegistered();
//            Artist receptor = null;
//            for (Artist aux: artistActors){
//                if(!Objects.equals(aux.getPublicKey(), transmisor.getPublicKey())){
//                    receptor = aux;
//                    break;
//                }
//
//            }
//            if(receptor != null){
//                System.out.println("#########################\nSolicitando conexión:\n"+
//                "From:"+transmisor.getAlias()+"\n"+transmisor.getPublicKey()+"\n"+"To:"+receptor.getAlias()+"\n"+receptor.getPublicKey()+"\n#######################################");
//                artistManager.askConnectionActorArtist(
//                        transmisor.getPublicKey(),
//                        transmisor.getAlias(),
//                        PlatformComponentType.NETWORK_SERVICE,
//                        receptor.getPublicKey(),
//                        receptor.getAlias(),
//                        PlatformComponentType.NETWORK_SERVICE,
//                        transmisor.getProfileImage()
//                );
//            }
//        } catch (CantListArtistIdentitiesException e) {
//            e.printStackTrace();
//        } catch (CantAskConnectionActorArtistNetworkServiceException e) {
//            e.printStackTrace();
//        } catch (CantRequestListActorArtistNetworkServiceRegisteredException e) {
//            e.printStackTrace();
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//    private void testCreateArtist(){
//        try {
//            Artist artist = createArtistIdentity("El Gabo que envia",new byte[0]);
//            System.out.println("#######################ART TEST");
//            testUpdateArtist(artist);
//            publishIdentity(artist.getPublicKey());
//        } catch (CantCreateArtistIdentityException | ArtistIdentityAlreadyExistsException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public List<Artist> listIdentitiesFromCurrentDeviceUser() throws CantListArtistIdentitiesException {
        return identityArtistManager.getIdentityArtistFromCurrentDeviceUser();
    }
    @Override
    public Artist createArtistIdentity(String alias, byte[] imageBytes) throws CantCreateArtistIdentityException, ArtistIdentityAlreadyExistsException {
        return identityArtistManager.createNewIdentityArtist(alias,imageBytes);
    }

    @Override
    public void updateArtistIdentity(String alias, String publicKey, byte[] profileImage, String externalUserName, String externalAccessToken, ExternalPlatform externalPlatform, ExposureLevel exposureLevel, ArtistAcceptConnectionsType artistAcceptConnectionsType) throws CantUpdateArtistIdentityException {
        identityArtistManager.updateIdentityArtist(alias,publicKey,profileImage,externalUserName,externalAccessToken,externalPlatform,exposureLevel,artistAcceptConnectionsType);
    }

    @Override
    public Artist getArtistIdentity(String publicKey) throws CantGetArtistIdentityException, IdentityNotFoundException {
        return identityArtistManager.getIdentitArtist(publicKey);
    }

    @Override
    public void publishIdentity(String publicKey) throws CantPublishIdentityException, IdentityNotFoundException {
//        try {
//            identityArtistManager.registerIdentitiesANS(publicKey);
//        } catch (CantRegisterActorArtistNetworkServiceException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void hideIdentity(String publicKey) throws CantHideIdentityException, IdentityNotFoundException {

    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        ArtistIdentityDeveloperDatabaseFactory dbFactory = new ArtistIdentityDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        ArtistIdentityDeveloperDatabaseFactory dbFactory = new ArtistIdentityDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        try {
            ArtistIdentityDeveloperDatabaseFactory dbFactory = new ArtistIdentityDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
            dbFactory.initializeDatabase();
            return dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (CantInitializeArtistIdentityDatabaseException e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.ARTIST_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
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
                if (ArtistIdentityPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                    ArtistIdentityPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                    ArtistIdentityPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                } else {
                    ArtistIdentityPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                }
            }

        } catch (Exception exception) {
            //FermatException e = new CantGetLogTool(CantGetLogTool.DEFAULT_MESSAGE, FermatException.wrapException(exception), "setLoggingLevelPerClass: " + ActorIssuerPluginRoot.newLoggingLevel, "Check the cause");
            // this.errorManager.reportUnexpectedAddonsException(Addons.EXTRA_USER, UnexpectedAddonsExceptionSeverity.DISABLES_THIS_ADDONS, e);
        }
    }
}
