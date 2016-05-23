package com.bitdubai.fermat_art_plugin.layer.identity.fan.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
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
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_art_api.all_definition.enums.ArtExternalPlatform;
import com.bitdubai.fermat_art_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_art_api.all_definition.interfaces.ArtIdentity;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantExposeIdentitiesException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantExposeIdentityException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.ArtistManager;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.FanManager;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.util.FanExposingData;
import com.bitdubai.fermat_art_api.layer.identity.fan.exceptions.CantCreateFanIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.fan.exceptions.CantListFanIdentitiesException;
import com.bitdubai.fermat_art_api.layer.identity.fan.interfaces.Fanatic;
import com.bitdubai.fermat_art_plugin.layer.identity.fan.developer.bitdubai.version_1.database.FanaticIdentityDeveloperDatabaseFactory;
import com.bitdubai.fermat_art_plugin.layer.identity.fan.developer.bitdubai.version_1.event_handler.FanaticConnectionRequestAcceptedEventHandler;
import com.bitdubai.fermat_art_plugin.layer.identity.fan.developer.bitdubai.version_1.event_handler.FanaticConnectionRequestUpdatesEventHandler;
import com.bitdubai.fermat_art_plugin.layer.identity.fan.developer.bitdubai.version_1.exceptions.CantInitializeFanaticIdentityDatabaseException;
import com.bitdubai.fermat_art_plugin.layer.identity.fan.developer.bitdubai.version_1.structure.FanIdentityEventActions;
import com.bitdubai.fermat_art_plugin.layer.identity.fan.developer.bitdubai.version_1.structure.IdentityFanaticManagerImpl;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUserManager;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ArtistAcceptConnectionsType;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExposureLevel;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExternalPlatform;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.WrongTokenlyUserCredentialsException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.FanIdentityAlreadyExistsException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.TokenlyFanIdentityManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * Created by Gabriel Araujo on 10/03/16.
 */
@PluginInfo(difficulty = PluginInfo.Dificulty.MEDIUM, maintainerMail = "gabe_512@hotmail.com", createdBy = "gabohub", layer = Layers.IDENTITY, platform = Platforms.ART_PLATFORM, plugin = Plugins.FANATIC_IDENTITY)
public class FanaticPluginRoot extends AbstractPlugin implements
        DatabaseManagerForDevelopers,
        //FanaticIdentityManager,
        LogManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference (platform = Platforms.PLUG_INS_PLATFORM , layer = Layers.PLATFORM_SERVICE, addon  = Addons .EVENT_MANAGER )
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    private LogManager logManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.USER, addon = Addons.DEVICE_USER)
    private DeviceUserManager deviceUserManager;

    @NeededPluginReference(platform =  Platforms.ART_PLATFORM, layer = Layers.ACTOR_NETWORK_SERVICE, plugin = Plugins.FAN)
    private FanManager fanManager;

    @NeededPluginReference(platform =  Platforms.ART_PLATFORM, layer = Layers.ACTOR_NETWORK_SERVICE, plugin = Plugins.ARTIST)
    private ArtistManager artistActorNetWorkServiceManager;

    @NeededPluginReference(platform = Platforms.TOKENLY,layer = Layers.IDENTITY, plugin = Plugins.TOKENLY_FAN)
    private TokenlyFanIdentityManager tokenlyFanIdentityManager;

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    public static final String FANATIC_PROFILE_IMAGE_FILE_NAME = "fanaticIdentityProfileImage";
    public static final String FANATIC_PRIVATE_KEYS_FILE_NAME = "fanaticIdentityPrivateKey";

    private IdentityFanaticManagerImpl identityFanaticManager;

    /**
     * Represents the Fan Identity Event Actions.
     */
    private FanIdentityEventActions fanIdentityEventActions;

    /**
     * Represents the Event listener to initialize in this plugin.
     */
    private List<FermatEventListener> listenersAdded;

    /**
     * Default constructor
     */
    public FanaticPluginRoot() {
        super(new PluginVersionReference(new Version()));
        listenersAdded = new ArrayList<>();
    }

    @Override
    public void start() throws CantStartPluginException {
        try {
            this.serviceStatus = ServiceStatus.STARTED;
            identityFanaticManager = new IdentityFanaticManagerImpl(
                    this.logManager,
                    this.pluginDatabaseSystem,
                    this.pluginFileSystem,
                    this.pluginId,
                    this.deviceUserManager,
                    this.fanManager,
                    this.tokenlyFanIdentityManager);

            //Initialize the fan identity event actions.
            initializeFanIdentityEventActions();

            //Initialize event handlers
            FermatEventListener updatesListener = eventManager.getNewListener(
                    EventType.ARTIST_CONNECTION_REQUEST_UPDATES);
            updatesListener.setEventHandler(
                    new FanaticConnectionRequestUpdatesEventHandler(
                            this.fanIdentityEventActions,
                            this));
            eventManager.addListener(updatesListener);
            listenersAdded.add(updatesListener);
            //Another listener
            FermatEventListener acceptListener = eventManager.getNewListener(
                    EventType.ARTIST_CONNECTION_REQUEST_ACCEPTED_EVENT);
            acceptListener.setEventHandler(
                    new FanaticConnectionRequestAcceptedEventHandler(
                            this.fanIdentityEventActions,
                            this));
            eventManager.addListener(acceptListener);
            listenersAdded.add(acceptListener);

            exposeIdentities();
            System.out.println("############\n ART IDENTITY Fanatic STARTED\n");
            //testCreateArtist();
            //testAskForConnection();
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.FANATIC_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(e, Plugins.FANATIC_IDENTITY);
        }
    }

    /**
     * This method initializes the Fan Identity Event Actions.
     */
    private void initializeFanIdentityEventActions(){
        this.fanIdentityEventActions = new FanIdentityEventActions(
                artistActorNetWorkServiceManager,
                identityFanaticManager,
                tokenlyFanIdentityManager,
                fanManager);
    }

    private void exposeIdentities(){
        ArrayList<FanExposingData> artistExposingData = new ArrayList<>();
        try {
            for (Fanatic fan :
                    identityFanaticManager.listIdentitiesFromCurrentDeviceUser()) {
                List extraDataList = new ArrayList();
                extraDataList.add(fan.getProfileImage());
                HashMap<ArtExternalPlatform,String> externalPlatformInformationMap = new HashMap<>();
                externalPlatformInformationMap.put(fan.getExternalPlatform(),fan.getExternalUsername());
                extraDataList.add(externalPlatformInformationMap);
                String extraDataString = XMLParser.parseObject(extraDataList);
                artistExposingData.add(new FanExposingData(
                        fan.getPublicKey(),
                        fan.getAlias(),
                        extraDataString
                ));
            }
            fanManager.exposeIdentities(artistExposingData);
        } catch (CantListFanIdentitiesException e) {
            e.printStackTrace();
        } catch (CantExposeIdentitiesException e) {
            e.printStackTrace();
        }
    }

    private void testCreateArtist(){
        String alias = "perezilla";
        byte[] image = new byte[0];
        String password = "milestone";
        ExternalPlatform externalPlatformTokenly = ExternalPlatform.TOKENLY;
        ExposureLevel exposureLevelTokenly = ExposureLevel.DEFAULT_EXPOSURE_LEVEL;
        ArtistAcceptConnectionsType artistAcceptConnectionsTypeTokenly = ArtistAcceptConnectionsType.DEFAULT_ARTIST_ACCEPT_CONNECTION_TYPE;

        try {
            tokenlyFanIdentityManager.createFanIdentity(alias, image, password, externalPlatformTokenly);

        HashMap<ArtExternalPlatform, HashMap<UUID,String>> externalIdentites = null;

            externalIdentites = identityFanaticManager.listExternalIdentitiesFromCurrentDeviceUser();

        Iterator<Map.Entry<ArtExternalPlatform, HashMap<UUID, String>>> entries = externalIdentites.entrySet().iterator();
            UUID externalIdentityID = null;
            while (entries.hasNext()) {
                Map.Entry<ArtExternalPlatform, HashMap<UUID, String>> entry = entries.next();
                HashMap<UUID, String> artists = entry.getValue();
                Iterator<Map.Entry<UUID, String>> entiesSet = artists.entrySet().iterator();
                while(entiesSet.hasNext()){
                    Map.Entry<UUID, String> entrySet = entiesSet.next();
                    System.out.println("Key = " + entrySet.getKey() + ", Value = " + entrySet.getValue());
                    externalIdentityID = entrySet.getKey();
                }
            }
            System.out.println("externalIdentites = " + XMLParser.parseObject(externalIdentites));

            Fanatic Fanatic = null;
            if(externalIdentityID != null){
                Fanatic = identityFanaticManager.createFanaticIdentity(alias, image, externalIdentityID, ArtExternalPlatform.TOKENLY,"");
                fanManager.exposeIdentity(new FanExposingData(Fanatic.getPublicKey(),Fanatic.getAlias(),""));
                ArtIdentity artIdentity = identityFanaticManager.getLinkedIdentity(Fanatic.getPublicKey());
                System.out.println("artIdentity = " + artIdentity.toString());
            }else{
                System.out.println("###############\nNo funciona.");
            }
        } catch ( com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantCreateFanIdentityException e) {
            e.printStackTrace();
        } catch (FanIdentityAlreadyExistsException e) {
            e.printStackTrace();
        } catch (CantListFanIdentitiesException e) {
            e.printStackTrace();
        } catch (CantCreateFanIdentityException e) {
            e.printStackTrace();
        } catch (WrongTokenlyUserCredentialsException e) {
            e.printStackTrace();
        } catch (CantExposeIdentityException e) {
            e.printStackTrace();
        }

    }


    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        FanaticIdentityDeveloperDatabaseFactory dbFactory = new FanaticIdentityDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        FanaticIdentityDeveloperDatabaseFactory dbFactory = new FanaticIdentityDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
            FanaticIdentityDeveloperDatabaseFactory dbFactory = new FanaticIdentityDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        try {
            dbFactory.initializeDatabase();
        } catch (CantInitializeFanaticIdentityDatabaseException e) {
            e.printStackTrace();
        }
        return dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);

        // If we are here the database could not be opened, so we return an empty list
    }

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_art_plugin.layer.identity.Fanatic.developer.bitdubai.version_1.FanaticPluginRoot");
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
                if (FanaticPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                    FanaticPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                    FanaticPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                } else {
                    FanaticPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                }
            }

        } catch (Exception exception) {
            //FermatException e = new CantGetLogTool(CantGetLogTool.DEFAULT_MESSAGE, FermatException.wrapException(exception), "setLoggingLevelPerClass: " + ActorIssuerPluginRoot.newLoggingLevel, "Check the cause");
            // this.errorManager.reportUnexpectedAddonsException(Addons.EXTRA_USER, UnexpectedAddonsExceptionSeverity.DISABLES_THIS_ADDONS, e);
        }
    }

    /**
     * This method returns the plugin manager
     * @return
     */
    @Override
    public FermatManager getManager() {
        return this.identityFanaticManager;
    }
}
