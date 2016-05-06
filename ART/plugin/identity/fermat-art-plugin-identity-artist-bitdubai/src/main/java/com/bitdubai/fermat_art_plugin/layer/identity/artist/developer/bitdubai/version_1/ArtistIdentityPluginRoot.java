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
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_art_api.all_definition.enums.ArtExternalPlatform;
import com.bitdubai.fermat_art_api.all_definition.enums.ArtistAcceptConnectionsType;
import com.bitdubai.fermat_art_api.all_definition.enums.ExposureLevel;
import com.bitdubai.fermat_art_api.all_definition.exceptions.CantHideIdentityException;
import com.bitdubai.fermat_art_api.all_definition.exceptions.CantPublishIdentityException;
import com.bitdubai.fermat_art_api.all_definition.exceptions.IdentityNotFoundException;
import com.bitdubai.fermat_art_api.all_definition.interfaces.ArtIdentity;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantExposeIdentitiesException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.ArtistManager;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistExternalPlatformInformation;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistExposingData;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.ArtistIdentityAlreadyExistsException;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.CantCreateArtistIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.CantGetArtistIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.CantListArtistIdentitiesException;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.CantUpdateArtistIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.artist.interfaces.Artist;
import com.bitdubai.fermat_art_api.layer.identity.artist.interfaces.ArtistIdentityManager;
import com.bitdubai.fermat_art_plugin.layer.identity.artist.developer.bitdubai.version_1.database.ArtistIdentityDeveloperDatabaseFactory;
import com.bitdubai.fermat_art_plugin.layer.identity.artist.developer.bitdubai.version_1.exceptions.CantInitializeArtistIdentityDatabaseException;
import com.bitdubai.fermat_art_plugin.layer.identity.artist.developer.bitdubai.version_1.structure.ArtistIdentityImp;
import com.bitdubai.fermat_art_plugin.layer.identity.artist.developer.bitdubai.version_1.structure.IdentityArtistManagerImpl;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUserManager;
import com.bitdubai.fermat_tky_api.layer.identity.artist.interfaces.TokenlyArtistIdentityManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * Created by Gabriel Araujo on 10/03/16.
 */
@PluginInfo(difficulty = PluginInfo.Dificulty.MEDIUM, maintainerMail = "gabe_512@hotmail.com", createdBy = "gabohub", layer = Layers.IDENTITY, platform = Platforms.ART_PLATFORM, plugin = Plugins.ARTIST_IDENTITY)
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

    @NeededPluginReference(platform = Platforms.TOKENLY,layer = Layers.IDENTITY, plugin = Plugins.TOKENLY_ARTIST)
    private TokenlyArtistIdentityManager tokenlyArtistIdentityManager;

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

            exposeIdentities();

            System.out.println("############\n ART IDENTITY ARTIST STARTED\n");
            //testCreateArtist();
            //testAskForConnection();
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.ARTIST_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(e, Plugins.ARTIST_IDENTITY);
        }
    }

    private void exposeIdentities(){
        ArrayList<ArtistExposingData> artistExposingDatas = new ArrayList<>();
        try {
            for (Artist artist :
                    listIdentitiesFromCurrentDeviceUser()) {
                HashMap<ArtExternalPlatform,String> externalInformation = new HashMap<>();
                externalInformation.put(artist.getExternalPlatform(), artist.getExternalUsername());
                List extraData = new ArrayList();
                extraData.add(artist.getProfileImage());
                extraData.add(new ArtistExternalPlatformInformation(externalInformation));
                String xmlExtraData = XMLParser.parseObject(extraData);
                artistExposingDatas.add(new ArtistExposingData(
                        artist.getPublicKey(),
                        artist.getAlias(),
                        xmlExtraData
                ));
            }
            artistManager.exposeIdentities(artistExposingDatas);
        } catch (CantListArtistIdentitiesException e) {
            errorManager.reportUnexpectedPluginException(Plugins.ARTIST_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        } catch (CantExposeIdentitiesException e) {
            errorManager.reportUnexpectedPluginException(Plugins.ARTIST_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        }
    }

//    private void testCreateArtist(){
//        String alias = "perezilla";
//        byte[] image = new byte[0];
//        String password = "milestone";
//        ExternalPlatform externalPlatformTokenly = ExternalPlatform.TOKENLY;
//        ExposureLevel exposureLevelTokenly = ExposureLevel.DEFAULT_EXPOSURE_LEVEL;
//        ArtistAcceptConnectionsType artistAcceptConnectionsTypeTokenly = ArtistAcceptConnectionsType.DEFAULT_ARTIST_ACCEPT_CONNECTION_TYPE;
//
//        try {
//
//            tokenlyArtistIdentityManager.createArtistIdentity(alias, image, password, externalPlatformTokenly, exposureLevelTokenly, artistAcceptConnectionsTypeTokenly);
//            HashMap<ArtExternalPlatform, HashMap<UUID,String>> externalIdentites = listExternalIdentitiesFromCurrentDeviceUser();
//            Iterator<Map.Entry<ArtExternalPlatform, HashMap<UUID, String>>> entries = externalIdentites.entrySet().iterator();
//            UUID externalIdentityID = null;
//            while (entries.hasNext()) {
//                Map.Entry<ArtExternalPlatform, HashMap<UUID, String>> entry = entries.next();
//                HashMap<UUID, String> artists = entry.getValue();
//                Iterator<Map.Entry<UUID, String>> entiesSet = artists.entrySet().iterator();
//                while(entiesSet.hasNext()){
//                    Map.Entry<UUID, String> entrySet = entiesSet.next();
//                    System.out.println("Key = " + entrySet.getKey() + ", Value = " + entrySet.getValue());
//                    externalIdentityID = entrySet.getKey();
//                }
//            }
//            System.out.println("externalIdentites = " + XMLParser.parseObject(externalIdentites));
//
//            Artist artist = null;
//            if(externalIdentityID != null){
//                artist = createArtistIdentity(alias,image,ExposureLevel.DEFAULT_EXPOSURE_LEVEL,ArtistAcceptConnectionsType.DEFAULT_ARTIST_ACCEPT_CONNECTION_TYPE, externalIdentityID, ArtExternalPlatform.TOKENLY);
//                artistManager.exposeIdentity(new ArtistExposingData(artist.getPublicKey(),"El gabo",artist.getProfileImage()));
//                ActorSearch<ArtistExposingData> artistExposingDataActorSearch = artistManager.getSearch();
//                List<ArtistExposingData> artistExposingDatas = artistExposingDataActorSearch.getResult();
//                for (ArtistExposingData artistExposingData :
//                        artistExposingDatas) {
//                    System.out.println("artistExposingData = " + artistExposingData.toString());
//                }
//                ArtIdentity artIdentity = getLinkedIdentity(artist.getPublicKey());
//                System.out.println("artIdentity = " + artIdentity.toString());
//            }else{
//                System.out.println("###############\nNo funciona.");
//            }
//        } catch (com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantCreateArtistIdentityException e) {
//            e.printStackTrace();
//        } catch (com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.ArtistIdentityAlreadyExistsException e) {
//            e.printStackTrace();
//        } catch (CantCreateArtistIdentityException e) {
//            e.printStackTrace();
//        } catch (ArtistIdentityAlreadyExistsException e) {
//            e.printStackTrace();
//        } catch (CantListArtistIdentitiesException e) {
//            e.printStackTrace();
//        } catch (CantExposeIdentityException e) {
//            e.printStackTrace();
//        } catch (CantListArtistsException e) {
//            e.printStackTrace();
//        } catch (WrongTokenlyUserCredentialsException e) {
//            e.printStackTrace();
//        }
//    }
    @Override
    public List<Artist> listIdentitiesFromCurrentDeviceUser() throws CantListArtistIdentitiesException {
        return identityArtistManager.getIdentityArtistFromCurrentDeviceUser();
    }

    @Override
    public HashMap<ArtExternalPlatform, HashMap<UUID, String>> listExternalIdentitiesFromCurrentDeviceUser() throws CantListArtistIdentitiesException {

        /*
            We'll return a HashMap based on the external platform containing another hashmap with the user and the id to that platform
         */
        HashMap<ArtExternalPlatform, HashMap<UUID,String>> externalArtistIdentities = new HashMap<>();
        HashMap<UUID,String> externalArtist = new HashMap<>();
        for (ArtExternalPlatform externalPlatform:
             ArtExternalPlatform.values()) {
            //Future platform will need to be added manually to the switch
            switch (externalPlatform){
                case TOKENLY:
                    try {
                        final List<com.bitdubai.fermat_tky_api.layer.identity.artist.interfaces.Artist> tokenlyArtists = tokenlyArtistIdentityManager.listIdentitiesFromCurrentDeviceUser();

                        for (com.bitdubai.fermat_tky_api.layer.identity.artist.interfaces.Artist artist:
                                tokenlyArtists) {
                            externalArtist.put(artist.getId(),artist.getUsername());
                        }
                        if(externalArtist.size()>0)
                            externalArtistIdentities.put(externalPlatform,externalArtist);
                    } catch (com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantListArtistIdentitiesException e) {
                        e.printStackTrace();
                        errorManager.reportUnexpectedPluginException(Plugins.ARTIST_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                    }
                    break;
                default:
                    break;
            }
        }
        return externalArtistIdentities;
    }

    @Override
    public ArtIdentity getLinkedIdentity(String publicKey) {
        ArtIdentity artIdentity = null;
        try {
            Artist artist = identityArtistManager.getIdentityArtist(publicKey);
            if(artist != null){
                for (ArtExternalPlatform externalPlatform:
                        ArtExternalPlatform.values()) {
                    //Future platform will need to be added manually to the switch
                    switch (externalPlatform){
                        case TOKENLY:
                            final com.bitdubai.fermat_tky_api.layer.identity.artist.interfaces.Artist tokenlyArtist = tokenlyArtistIdentityManager.getArtistIdentity(artist.getExternalIdentityID());
                            if(tokenlyArtist != null){
                                artIdentity = new ArtistIdentityImp(
                                        tokenlyArtist.getPublicKey(),
                                        tokenlyArtist.getProfileImage(),
                                        tokenlyArtist.getUsername(),
                                        tokenlyArtist.getId(),
                                        externalPlatform,
                                        tokenlyArtist.getUsername());
                            }
                            break;
                    }
                }
            }
        } catch (CantGetArtistIdentityException e) {
            errorManager.reportUnexpectedPluginException(Plugins.ARTIST_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        } catch (com.bitdubai.fermat_tky_api.all_definitions.exceptions.IdentityNotFoundException e) {
            errorManager.reportUnexpectedPluginException(Plugins.ARTIST_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        } catch (com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantGetArtistIdentityException e) {
            errorManager.reportUnexpectedPluginException(Plugins.ARTIST_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        }
        return artIdentity;
    }


    @Override
    public Artist createArtistIdentity( final String alias,
                                        final byte[] imageBytes,
                                        final String externalUsername,
                                        ExposureLevel exposureLevel,
                                        ArtistAcceptConnectionsType acceptConnectionsType,
                                        final UUID externalIdentityID,
                                        final ArtExternalPlatform artExternalPlatform) throws CantCreateArtistIdentityException, ArtistIdentityAlreadyExistsException {

        return identityArtistManager.createNewIdentityArtist(
                alias,
                imageBytes,
                externalIdentityID,
                externalUsername,
                artExternalPlatform,
                exposureLevel,
                acceptConnectionsType);
    }

    @Override
    public void updateArtistIdentity(
            String alias, String publicKey, byte[] profileImage,
            ExposureLevel exposureLevel, ArtistAcceptConnectionsType acceptConnectionsType, UUID externalIdentityID,
            ArtExternalPlatform artExternalPlatform,String externalUserName) throws CantUpdateArtistIdentityException {

        identityArtistManager.updateIdentityArtist(
                alias,
                publicKey,
                profileImage,
                externalIdentityID,
                externalUserName,
                artExternalPlatform,
                exposureLevel,
                acceptConnectionsType);

    }

    @Override
    public Artist getArtistIdentity(String publicKey) throws CantGetArtistIdentityException, IdentityNotFoundException {
        return identityArtistManager.getIdentityArtist(publicKey);
    }

    @Override
    public void publishIdentity(String publicKey) throws CantPublishIdentityException, IdentityNotFoundException {
        identityArtistManager.registerIdentitiesANS(publicKey);
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
