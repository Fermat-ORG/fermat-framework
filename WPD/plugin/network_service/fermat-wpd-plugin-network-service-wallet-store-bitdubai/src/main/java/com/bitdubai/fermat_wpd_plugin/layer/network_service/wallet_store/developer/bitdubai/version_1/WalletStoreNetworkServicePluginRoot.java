package com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.DiscoveryQueryParameters;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.interfaces.NetworkService;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkServiceConnectionManager;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ScreenSize;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_identity.designer.interfaces.DesignerIdentity;
import com.bitdubai.fermat_api.layer.dmp_identity.translator.interfaces.TranslatorIdentity;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_wpd_api.all_definition.exceptions.CantGetWalletLanguageException;
import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_identity.developer.exceptions.CantSingMessageException;
import com.bitdubai.fermat_wpd_api.layer.wpd_identity.developer.interfaces.DeveloperIdentity;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationLayerManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetCatalogItemException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetDesignerException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetDeveloperException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetSkinException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetTranslatorException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetWalletIconException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetWalletsCatalogException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantPublishDesignerInCatalogException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantPublishDeveloperInCatalogException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantPublishLanguageInCatalogException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantPublishSkinInCatalogException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantPublishTranslatorInCatalogException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantPublishWalletInCatalogException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.CatalogItem;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.Designer;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.DetailedCatalogItem;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.Developer;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.Language;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.Skin;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.Translator;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.WalletCatalog;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.WalletStoreManager;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.WalletStoreNetworkServiceMonitoringAgent;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.CatalogItemImpl;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.DetailedCatalogItemImpl;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.database.WalletStoreCatalogDatabaseConstants;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.database.WalletStoreCatalogDatabaseFactory;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.developerUtils.DeveloperDatabaseFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by loui on 17/02/15.
 */

/**
 * This plugin handles the information that constitutes a wallet; The resources and the navigation structure. When a new
 * wallet is installed in the local device, it is in charge of finding those two things, either from other peers, or from
 * a centralized location.
 * <p/>
 * A wallet can be run only when it has both its navigation structure on place and the resources it is based upon (like
 * images, sound files and the like)
 * <p/>
 * This plug in is also ready to share that information with other peers when requested.
 * <p/>
 * * * * *
 */

public class WalletStoreNetworkServicePluginRoot extends AbstractPlugin implements
        WalletStoreManager,
        NetworkService,
        LogManagerForDevelopers,
        DatabaseManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER         )
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER         )
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM    )
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER    )
    private LogManager logManager;

    /**
     * WalletStoreNetworkServicePluginRoot member variables
     */
    Database database;
    WalletStoreNetworkServiceMonitoringAgent agent;

    CommunicationLayerManager communicationLayerManager;

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    List<FermatEventListener> listenersAdded = new ArrayList<>();

    public WalletStoreNetworkServicePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    /**
     * DatabaseManagerForDevelopers implementation. List the databases available
     *
     * @param developerObjectFactory
     * @return
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        DeveloperDatabaseFactory developerDatabaseFactory = new DeveloperDatabaseFactory(pluginId.toString());
        return developerDatabaseFactory.getDatabaseList(developerObjectFactory);
    }

    /**
     * DatabaseManagerForDevelopers implementation. List the available tables
     *
     * @param developerObjectFactory
     * @param developerDatabase
     * @return
     */
    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return DeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    /**
     * DatabaseManagerForDevelopers implementation. List the records for the table
     *
     * @param developerObjectFactory
     * @param developerDatabase
     * @param developerDatabaseTable
     * @return
     */
    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return DeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, database, developerDatabaseTable);
    }


    private com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.WalletStoreManager getWalletStoreManager() {
        com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.WalletStoreManager walletStoreManager = new com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.WalletStoreManager(errorManager, logManager, pluginDatabaseSystem, pluginFileSystem, pluginId);
        return walletStoreManager;
    }

    /**
     * WalletStoreManager interface implementation
     *
     * @param catalogItem
     * @throws CantPublishWalletInCatalogException
     */
    @Override
    public void publishWallet(CatalogItem catalogItem) throws CantPublishWalletInCatalogException {
        getWalletStoreManager().publishWallet((CatalogItemImpl) catalogItem);
    }


    @Override
    public void publishSkin(Skin skin) throws CantPublishSkinInCatalogException {
        getWalletStoreManager().publishSkin(skin);
    }

    @Override
    public void publishLanguage(Language language) throws CantPublishLanguageInCatalogException {
        getWalletStoreManager().publishLanguage((com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Language) language);
    }

    @Override
    public void publishDesigner(Designer designer) throws CantPublishDesignerInCatalogException {
        getWalletStoreManager().publishDesigner((com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Designer) designer);
    }

    @Override
    public void publishDeveloper(Developer developer) throws CantPublishDeveloperInCatalogException {
        getWalletStoreManager().publishDeveloper((com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Developer) developer);
    }

    @Override
    public void publishTranslator(Translator translator) throws CantPublishTranslatorInCatalogException {
        getWalletStoreManager().publishTranslator((com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Translator) translator);
    }

    @Override
    public WalletCatalog getWalletCatalogue() throws CantGetWalletsCatalogException {
        return getWalletStoreManager().getWalletCatalogue();
    }

    @Override
    public CatalogItem getCatalogItem(UUID walletId) throws CantGetCatalogItemException {
        return getWalletStoreManager().getCatalogItem(walletId);
    }

    @Override
    public DetailedCatalogItem getDetailedCatalogItem(UUID walletId) throws CantGetCatalogItemException {
        return getWalletStoreManager().getDetailedCatalogItem(walletId);
    }

    @Override
    public Language getLanguage(UUID walletId) throws CantGetWalletLanguageException {
        return getWalletStoreManager().getLanguage(walletId);
    }

    @Override
    public Skin getSkin(UUID walletId) throws CantGetSkinException {
        return getWalletStoreManager().getSkin(walletId);
    }

    @Override
    public DeveloperIdentity getDeveloper(UUID developerId) throws CantGetDeveloperException {
        return getWalletStoreManager().getDeveloper(developerId);
    }

    @Override
    public DesignerIdentity getDesigner(UUID designerId) throws CantGetDesignerException {
        return getWalletStoreManager().getDesigner(designerId);
    }

    @Override
    public TranslatorIdentity getTranslator(UUID translatorId) throws CantGetTranslatorException {
        return getWalletStoreManager().getTranslator(translatorId);
    }

    /**
     * Service Interface implementation.
     */

    @Override
    public void start() throws CantStartPluginException {
        /**
         * I will try to open the database first, if it doesn't exists, then I create it
         */
        try {
          //  TestPublishWallet();
            database = pluginDatabaseSystem.openDatabase(pluginId, WalletStoreCatalogDatabaseConstants.WALLET_STORE_DATABASE);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            try {
                createWalletStoreNetworkServiceDatabase();
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_STORE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantStartPluginException();
            } catch (Exception exception) {
                throw new CantStartPluginException("Cannot start WalletStoreNetworkService plugin.", FermatException.wrapException(exception), null, null);
            }
        } catch (DatabaseNotFoundException databaseNotFoundException) {
            /**
             * The database doesn't exists, lets create it.
             */
            try {
                createWalletStoreNetworkServiceDatabase();
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_STORE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantStartPluginException();
            }
        } catch (Exception exception) {
            throw new CantStartPluginException("Cannot start WalletStoreNetworkService plugin.", FermatException.wrapException(exception), null, null);
        }


        /**
         * I will initialize the Monitoring agent
         */
        agent = new WalletStoreNetworkServiceMonitoringAgent(eventManager, errorManager, logManager, pluginDatabaseSystem, pluginId, communicationLayerManager);
        try {
            agent.start();
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_STORE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }

        /**
         * I will initialize the handling of platform events.
         */
        this.serviceStatus = ServiceStatus.STARTED;
    }

    /**
     * Creates the database with the Database Factory
     * Creates the database with the Database Factory
     * @throws CantCreateDatabaseException
     */
    private void createWalletStoreNetworkServiceDatabase() throws CantCreateDatabaseException {
        WalletStoreCatalogDatabaseFactory walletStoreCatalogDatabaseFactory = new WalletStoreCatalogDatabaseFactory(errorManager, logManager, pluginDatabaseSystem);
        database = walletStoreCatalogDatabaseFactory.createDatabase(pluginId, WalletStoreCatalogDatabaseConstants.WALLET_STORE_DATABASE);
    }

    @Override
    public void pause() {
        agent.stop();
        this.serviceStatus = ServiceStatus.PAUSED;

    }

    @Override
    public void resume() {
        try {
            agent.start();

        } catch (CantStartAgentException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_STORE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        /**
         * stop the monitoring agent
         */
        agent.stop();

        /**
         * I will remove all the event listeners registered with the event manager.
         */

        for (FermatEventListener fermatEventListener : listenersAdded) {
            eventManager.removeListener(fermatEventListener);
        }

        listenersAdded.clear();
        this.serviceStatus = ServiceStatus.STOPPED;

    }

    /**
     * NetworkService Interface implementation.
     */

    @Override
    public PlatformComponentProfile getPlatformComponentProfilePluginRoot() {
        return null;
    }

    @Override
    public PlatformComponentType getPlatformComponentType() {
        return null;
    }

    @Override
    public NetworkServiceType getNetworkServiceType() {
        return null;
    }

    @Override
    public List<PlatformComponentProfile> getRemoteNetworkServicesRegisteredList() {
        return null;
    }

    @Override
    public void requestRemoteNetworkServicesRegisteredList(DiscoveryQueryParameters discoveryQueryParameters) {

    }

    @Override
    public NetworkServiceConnectionManager getNetworkServiceConnectionManager() {
        return null;
    }

    @Override
    public DiscoveryQueryParameters constructDiscoveryQueryParamsFactory(PlatformComponentType platformComponentType, NetworkServiceType networkServiceType, String alias, String identityPublicKey, Location location, Double distance, String name, String extraData, Integer firstRecord, Integer numRegister, PlatformComponentType fromOtherPlatformComponentType, NetworkServiceType fromOtherNetworkServiceType) {
        return null;
    }

    /**
     * Handles the events CompleteComponentRegistrationNotification
     * @param platformComponentProfileRegistered
     */
    @Override
    public void handleCompleteComponentRegistrationNotificationEvent(PlatformComponentProfile platformComponentProfileRegistered) {

    }

    @Override
    public void handleFailureComponentRegistrationNotificationEvent(PlatformComponentProfile networkServiceApplicant, PlatformComponentProfile remoteParticipant) {

    }

    @Override
    public void handleCompleteRequestListComponentRegisteredNotificationEvent(List<PlatformComponentProfile> platformComponentProfileRegisteredList) {

    }


    /**
     * Handles the events CompleteRequestListComponentRegisteredNotificationEvent
     * @param remoteComponentProfile
     */
    @Override
    public void handleCompleteComponentConnectionRequestNotificationEvent(PlatformComponentProfile applicantComponentProfile, PlatformComponentProfile remoteComponentProfile) {

    }

    /**
     * Handles the events VPNConnectionCloseNotificationEvent
     * @param fermatEvent
     */
    @Override
    public void handleVpnConnectionCloseNotificationEvent(FermatEvent fermatEvent) {

       /* if(fermatEvent instanceof VPNConnectionCloseNotificationEvent){

            VPNConnectionCloseNotificationEvent vpnConnectionCloseNotificationEvent = (VPNConnectionCloseNotificationEvent) fermatEvent;

            if(vpnConnectionCloseNotificationEvent.getNetworkServiceApplicant() == getNetworkServiceType()){

                if(communicationNetworkServiceConnectionManager != null)
                communicationNetworkServiceConnectionManager.closeConnection(vpnConnectionCloseNotificationEvent.getRemoteParticipant().getIdentityPublicKey());

            }

        } */

    }

    /**
     * Handles the events ClientConnectionCloseNotificationEvent
     * @param fermatEvent
     */
    @Override
    public void handleClientConnectionCloseNotificationEvent(FermatEvent fermatEvent) {

      /*  if(fermatEvent instanceof ClientConnectionCloseNotificationEvent){
            this.register = false;
            if(communicationNetworkServiceConnectionManager != null)
            communicationNetworkServiceConnectionManager.closeAllConnection();
        }
        */

    }

    /*
    * Handles the events ClientConnectionLooseNotificationEvent
    */
    @Override
    public void handleClientConnectionLooseNotificationEvent(FermatEvent fermatEvent) {

//        if(communicationNetworkServiceConnectionManager != null)
//            communicationNetworkServiceConnectionManager.stop();

    }

    /*
     * Handles the events ClientSuccessfullReconnectNotificationEvent
     */
    @Override
    public void handleClientSuccessfullReconnectNotificationEvent(FermatEvent fermatEvent) {

//        if(communicationNetworkServiceConnectionManager != null)
//            communicationNetworkServiceConnectionManager.restart();

//        if(!this.register){
//            communicationRegistrationProcessNetworkServiceAgent.start();
//        }

    }

    @Override
    public boolean isRegister() {
        return false;
    }

    @Override
    public void setPlatformComponentProfilePluginRoot(PlatformComponentProfile platformComponentProfile) {

    }

    @Override
    public void initializeCommunicationNetworkServiceConnectionManager() {

    }

    @Override
    public String getIdentityPublicKey() {
        return null;
    }

    @Override
    public String getAlias() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getExtraData() {
        return null;
    }

    @Override
    public void handleNewMessages(FermatMessage incomingMessage) {
        //TODO implement handle new message
    }

    @Override
    public void handleNewSentMessageNotificationEvent(FermatMessage message) {

    }

    /**
     * LogManagerForDevelopers Interface implementation.
     */

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.WalletStoreNetworkServicePluginRoot");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.exceptions.CantDeleteRecordDataBaseException");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.exceptions.CantInsertRecordDataBaseException");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.exceptions.CantPublishItemInCatalogException");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.exceptions.InvalidDatabaseOperationException");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.exceptions.InvalidResultReturnedByDatabaseException");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.CatalogItemImpl");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Designer");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.DetailedCatalogItemImpl");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Developer");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Language");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Skin");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Translator");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.WalletCatalog");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.common.DatabaseOperations");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.database.WalletStoreCatalogDatabaseConstants");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.database.WalletStoreCatalogDatabaseDao");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.database.WalletStoreCatalogDatabaseFactory");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.developerUtils.DeveloperDatabaseFactory");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.networkService.database.IncomingMessageDAO");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.networkService.database.OutgoingMessageDAO");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.networkService.database.WalletStoreNetworkServiceDatabaseConstants");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.networkService.database.WalletStoreNetworkServiceDatabaseFactory");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.networkService.WalletStoreNetworkServiceLocalAgent");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.networkService.WalletStoreNetworkServiceManager");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.networkService.WalletStoreNetworkServiceMessage");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.WalletStoreManager");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.WalletStoreNetworkServiceMonitoringAgent");
        /**
         * I return the values.
         */
        return returnedClasses;
    }


    /**
     * Static method to get the logging level from any class under root.
     *
     * @param className
     * @return
     */
    public static LogLevel getLogLevelByClass(String className) {
        try {
            /**
             * sometimes the classname may be passed dinamically with an $moretext
             * I need to ignore whats after this.
             */
            String[] correctedClass = className.split((Pattern.quote("$")));
            return WalletStoreNetworkServicePluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception exception) {
            System.err.println("CantGetLogLevelByClass: " + exception.getMessage());
            return LogLevel.MINIMAL_LOGGING;
        }
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
            if (WalletStoreNetworkServicePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                WalletStoreNetworkServicePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                WalletStoreNetworkServicePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                WalletStoreNetworkServicePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }
    }


    //todo delete - TESTING
    public void TestPublishWallet() throws MalformedURLException, CantPublishWalletInCatalogException {
        try {
            UUID walletId = UUID.randomUUID();
            CatalogItemImpl catalogItemImpl;
            catalogItemImpl = new CatalogItemImpl();
            catalogItemImpl.setId(walletId);
            catalogItemImpl.setDefaultSizeInBytes(100);
            catalogItemImpl.setName("MatiWallet");
            catalogItemImpl.setCategory(WalletCategory.BRANDED_NICHE_WALLET);
            catalogItemImpl.setDescription("Prueba de insert");
            catalogItemImpl.setpublisherWebsiteUrl(new URL("http://examples.com/pages"));

            byte[] myIcon = new byte[]{0xa,0x2,0xf,(byte)0xff,(byte)0xff,(byte)0xff};
            catalogItemImpl.setIcon(myIcon);
            catalogItemImpl.setWalletCatalogId(walletId);

            com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Skin skin;
            skin = new com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Skin();
            byte[] presentationImage = new byte[]{0xa,0x2,0xf,(byte)0xff,(byte)0xff,(byte)0xff};
            skin.setPresentationImage(presentationImage);
            skin.setSkinSizeInBytes(100);
            skin.setFinalWalletVersion(new Version(1, 0, 0));
            skin.setHasVideoPreview(false);
            skin.setInitialWalletVersion(new Version("1.0.0"));
            skin.setVersion(new Version(1, 0, 0));
            skin.setWalletId(walletId);
            List<byte[]> imagelists = new ArrayList<>();
            imagelists.add(presentationImage);
            imagelists.add(presentationImage);
            imagelists.add(presentationImage);
            skin.setPreviewImageList(imagelists);
            skin.setId(UUID.randomUUID());
            skin.setName("Mi skin");
            skin.setIsDefault(true);

            //New set. Sets the ScreenSize
            skin.setScreenSize(ScreenSize.MEDIUM);

            final String designerId = UUID.randomUUID().toString();
            DesignerIdentity designer = new DesignerIdentity() {
                @Override
                public String getAlias() {
                    return "Diseñador";
                }

                @Override
                public String getPublicKey() {
                    return designerId;
                }

                @Override
                public String createMessageSignature(String mensage) throws com.bitdubai.fermat_api.layer.dmp_identity.designer.exceptions.CantSingMessageException {
                    //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
                    return null;
                }
            };

            skin.setDesigner(designer);

            DetailedCatalogItemImpl detailedCatalogItemImpl;
            detailedCatalogItemImpl = new DetailedCatalogItemImpl();
            detailedCatalogItemImpl.setDefaultSkin(skin);
            List<Skin> skins = new ArrayList<>();
            skins.add(skin);
            detailedCatalogItemImpl.setSkins(skins);
            detailedCatalogItemImpl.setVersion(new Version("1.0.0"));
            detailedCatalogItemImpl.setPlatformInitialVersion(new Version("1.0.0"));
            detailedCatalogItemImpl.setPlatformFinalVersion(new Version("1.0.0"));

            com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Language language = new com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Language();
            language.setWalletId(walletId);
            language.setId(UUID.randomUUID());
            language.setInitialWalletVersion(new Version("1.0.0"));
            language.setFinalWalletVersion(new Version("1.0.0"));
            language.setLanguageLabel("Espaól");
            language.setIsDefault(true);
            language.setVersion(new Version("1.0.0"));
            language.setLanguageName(Languages.SPANISH);
            language.setLanguagePackageSizeInBytes(100);


            final String traductorId = UUID.randomUUID().toString();
            TranslatorIdentity translator = new TranslatorIdentity() {
                @Override
                public String getAlias() {
                    return "Traductor";
                }

                @Override
                public String getPublicKey() {
                    return traductorId;
                }

                @Override
                public String createMessageSignature(String mensage) throws com.bitdubai.fermat_api.layer.dmp_identity.translator.exceptions.CantSingMessageException {
                    //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
                    return null;
                }
            };
            language.setTranslator(translator);


            detailedCatalogItemImpl.setLanguage(language);

            final String devId = UUID.randomUUID().toString();
            DeveloperIdentity developerIdentity = new DeveloperIdentity() {
                @Override
                public String getAlias() {
                    return "Franklin";
                }

                @Override
                public String getPublicKey() {
                    return devId;
                }

                @Override
                public String createMessageSignature(String mensage) throws CantSingMessageException {
                    //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
                    return null;
                }
            };

            detailedCatalogItemImpl.setDeveloper(developerIdentity);

            catalogItemImpl.setDetailedCatalogItemImpl(detailedCatalogItemImpl);


            //create an example icon file
            PluginTextFile file = pluginFileSystem.createTextFile(this.pluginId, "rodrigo", "archivo", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            file.setContent(myIcon.toString());
            file.persistToMedia();


            //retrieve it
            PluginTextFile loadedFile = pluginFileSystem.getTextFile(this.pluginId, "rodrigo", "archivo", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            loadedFile.loadFromMedia();
            byte[] loadedIcon = loadedFile.getContent().getBytes(Charset.forName("UTF-8"));



            this.publishWallet(catalogItemImpl);
        }
        catch(Exception exception) {
            exception.printStackTrace();
            throw new CantPublishWalletInCatalogException("Publish Wallet Test", exception, "Franklin", CatalogItemImpl.class.toString());
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see WalletStoreManager#constructEmptyCatalogItem()
     */
    public CatalogItem constructEmptyCatalogItem() {

        CatalogItemImpl catalogItemImpl = new CatalogItemImpl();

        return catalogItemImpl;
    }

    public Language constructLanguage(UUID languageId,
                                      Languages nameLanguage,
                                      String languageLabel,
                                      UUID walletId,
                                      Version version,
                                      Version initialWalletVersion,
                                      Version finalWalletVersion,
                                      List<URL> videoPreviews,
                                      long languageSizeInBytes,
                                      TranslatorIdentity translator,
                                      boolean isDefault)
    {
        com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Language languageImpl;
        languageImpl = new com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Language();

        languageImpl.setId(languageId);
        languageImpl.setLanguageName(nameLanguage);
        languageImpl.setLanguageLabel(languageLabel);
        languageImpl.setWalletId(walletId);
        languageImpl.setVersion(version);
        languageImpl.setInitialWalletVersion(initialWalletVersion);
        languageImpl.setFinalWalletVersion(finalWalletVersion);
        languageImpl.setLanguagePackageSizeInBytes((int) languageSizeInBytes);
        languageImpl.setTranslator(translator);
        languageImpl.setIsDefault(isDefault);

        return languageImpl;
    }

    public Skin constructSkin(UUID skinId,
                              String nameSkin,
                              UUID walletId,
                              ScreenSize screenSize,
                              Version version,
                              Version initialWalletVersion,
                              Version finalWalletVersion,
                              byte[] presentationImage,
                              List<byte[]> previewImageList,
                              boolean hasVideoPreview,
                              List<URL> videoPreviews,
                              long skinSizeInBytes,
                              DesignerIdentity designer,
                              boolean isDefault )
    {
        com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Skin skinImpl;
        skinImpl = new com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Skin();

        skinImpl.setId(skinId);
        skinImpl.setName(nameSkin);
        skinImpl.setWalletId(walletId);
        skinImpl.setScreenSize(screenSize);
        skinImpl.setVersion(version);
        skinImpl.setInitialWalletVersion(initialWalletVersion);
        skinImpl.setFinalWalletVersion(finalWalletVersion);
        skinImpl.setPresentationImage(presentationImage);
        skinImpl.setPreviewImageList(previewImageList);
        skinImpl.setHasVideoPreview(hasVideoPreview);
        skinImpl.setVideoPreviews(videoPreviews);
        skinImpl.setSkinSizeInBytes(skinSizeInBytes);
        skinImpl.setDesigner(designer);
        skinImpl.setIsDefault(isDefault);

        return skinImpl;
    }

    public CatalogItem constructCatalogItem(UUID walletId, int defaultSizeInBytes,
                                            String name, String description,
                                            WalletCategory walletCategory,
                                            byte[] icon,
                                            Version version,
                                            Version platformInitialVersion,
                                            Version platformFinalVersion,
                                            List<Skin> skins,
                                            Skin skin,
                                            Language language,
                                            DeveloperIdentity developer,
                                            List<Language> languages,
                                            URL publisherWebsiteUrl) throws CantGetWalletIconException {

        CatalogItemImpl catalogItemImpl = new CatalogItemImpl();
        DetailedCatalogItemImpl detailedCatalogItemImpl;

        detailedCatalogItemImpl = new DetailedCatalogItemImpl();

        catalogItemImpl.setId(walletId);
        catalogItemImpl.setDefaultSizeInBytes(defaultSizeInBytes);
        catalogItemImpl.setName(name);
        catalogItemImpl.setCategory(walletCategory);
        catalogItemImpl.setDescription(description);
        catalogItemImpl.setWalletCatalogId(walletId);
        catalogItemImpl.setIcon(icon);
        catalogItemImpl.setpublisherWebsiteUrl(publisherWebsiteUrl);

        com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Skin constructskin;
        constructskin = new com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Skin();

        constructskin.setId(skin.getSkinId());
        constructskin.setPresentationImage(skin.getPresentationImage());
        constructskin.setSkinSizeInBytes((int) skin.getSkinSizeInBytes());
        constructskin.setFinalWalletVersion(skin.getInitialWalletVersion());
        constructskin.setHasVideoPreview(false);
        constructskin.setInitialWalletVersion(skin.getInitialWalletVersion());
        constructskin.setVersion(skin.getVersion());
        constructskin.setWalletId(walletId);
        constructskin.setId(skin.getSkinId());
        constructskin.setName(skin.getSkinName());
        constructskin.setIsDefault(skin.isDefault());
        constructskin.setScreenSize(skin.getScreenSize());

        constructskin.setDesigner(skin.getDesigner());

        detailedCatalogItemImpl.setVersion(version);
        detailedCatalogItemImpl.setPlatformInitialVersion(platformInitialVersion);
        detailedCatalogItemImpl.setPlatformFinalVersion(platformFinalVersion);
        detailedCatalogItemImpl.setDefaultSkin(skin);
        detailedCatalogItemImpl.setSkins(skins);
        detailedCatalogItemImpl.setDeveloper(developer);
        detailedCatalogItemImpl.setLanguage(language);
        detailedCatalogItemImpl.setLanguages(languages);

        catalogItemImpl.setDetailedCatalogItemImpl(detailedCatalogItemImpl);

        return catalogItemImpl;
    }

}
