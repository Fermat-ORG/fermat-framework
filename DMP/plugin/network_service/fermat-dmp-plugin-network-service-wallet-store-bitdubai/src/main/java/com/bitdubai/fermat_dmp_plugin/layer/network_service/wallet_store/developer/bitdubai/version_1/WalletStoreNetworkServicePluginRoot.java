package com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ScreenSize;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantGetWalletLanguageException;
import com.bitdubai.fermat_api.layer.dmp_network_service.NetworkService;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetCatalogItemException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetDesignerException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetDeveloperException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetSkinException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetTranslatorException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetWalletsCatalogException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantPublishDesignerInCatalogException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantPublishLanguageInCatalogException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantPublishSkinInCatalogException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantPublishTranslatorInCatalogException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantPublishWalletInCatalogException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.CatalogItem;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.Designer;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.DetailedCatalogItem;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.Developer;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.Language;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.Skin;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.Translator;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.WalletCatalog;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.WalletStoreManager;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.WalletStoreNetworkServiceMonitoringAgent;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.database.WalletStoreNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.database.WalletStoreNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.developerUtils.DeveloperDatabaseFactory;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.EventListener;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.platform_info.interfaces.DealsWithPlatformInfo;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.platform_info.interfaces.PlatformInfo;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.platform_info.interfaces.PlatformInfoManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.platform_info.interfaces.exceptions.CantLoadPlatformInformationException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by loui on 17/02/15.
 */

/**
 * This plugin handles the information that constitutes a wallet; The resources and the navigation structure. When a new
 * wallet is installed in the local device, it is in charge of finding those two things, either from other peers, or from
 * a centralized location.
 * 
 * A wallet can be run only when it has both its navigation structure on place and the resources it is based upon (like
 * images, sound files and the like)
 * 
 * This plug in is also ready to share that information with other peers when requested.
 * 
 * * * * * 
 */

public class WalletStoreNetworkServicePluginRoot implements DatabaseManagerForDevelopers, DealsWithPlatformInfo, DealsWithEvents, DealsWithErrors,DealsWithLogger, DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem,WalletStoreManager, Service, NetworkService, LogManagerForDevelopers,Plugin {
    /**
     * WalletStoreNetworkServicePluginRoot member variables
     */
    Database database;
    WalletStoreNetworkServiceMonitoringAgent agent;

    /**
     * DealsWithPlaformInfo interface variables and implementation
     */
    PlatformInfoManager platformInfoManager;
    @Override
    public void setPlatformInfoManager(PlatformInfoManager platformInfoManager) {
        this.platformInfoManager = platformInfoManager;
    }

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    List<EventListener> listenersAdded = new ArrayList<>();

    /**
     * DealWithEvents Interface member variables.
     */
    EventManager eventManager;

    /**
     * DealsWithErrors interface member variable
     */
    ErrorManager errorManager;

    /**
     * DealsWithLogger interface member variable
     */
    LogManager logManager;

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    /**
     * DealsWithPluginDatabaseSystem interface member variables
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPLuginFileSystem Interface member variables.
     */
    PluginFileSystem pluginFileSystem;

    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    UUID pluginId;

    /**
     * DatabaseManagerForDevelopers implementation. List the databases available
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
     * @param developerObjectFactory
     * @param developerDatabase
     * @param developerDatabaseTable
     * @return
     */
    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return DeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, database, developerDatabaseTable);
    }


    private com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.WalletStoreManager getWalletStoreManager(){
        com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.WalletStoreManager walletStoreManager = new com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.WalletStoreManager(errorManager, logManager, pluginDatabaseSystem, pluginFileSystem, pluginId);
        return walletStoreManager;
    }

    /**
     * WalletStoreManager interface implementation
     * @param catalogItem
     * @throws CantPublishWalletInCatalogException
     */
    @Override
    public void publishWallet(CatalogItem catalogItem) throws CantPublishWalletInCatalogException {
        getWalletStoreManager().publishWallet((com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.CatalogItem) catalogItem);
    }


    @Override
    public void publishSkin(Skin skin) throws CantPublishSkinInCatalogException {
        getWalletStoreManager().publishSkin((com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Skin) skin);
    }

    @Override
    public void publishLanguage(Language language) throws CantPublishLanguageInCatalogException {
        getWalletStoreManager().publishLanguage((com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Language) language);
    }

    @Override
    public void publishDesigner(Designer designer) throws CantPublishDesignerInCatalogException {
        getWalletStoreManager().publishDesigner((com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Designer) designer);
    }

    @Override
    public void publishTranslator(Translator translator) throws CantPublishTranslatorInCatalogException {
        getWalletStoreManager().publishTranslator((com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Translator) translator);
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
    public Developer getDeveloper(UUID developerId) throws CantGetDeveloperException {
        return getWalletStoreManager().getDeveloper(developerId);
    }

    @Override
    public Designer getDesigner(UUID designerId) throws CantGetDesignerException {
        return getWalletStoreManager().getDesigner(designerId);
    }

    @Override
    public Translator getTranslator(UUID translatorId) throws CantGetTranslatorException {
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

            database = pluginDatabaseSystem.openDatabase(pluginId, WalletStoreNetworkServiceDatabaseConstants.WALLET_STORE_DATABASE);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            try {
                createWalletStoreNetworkServiceDatabase();
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_STORE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantStartPluginException();
            } catch (Exception exception){
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
        } catch (Exception exception){
            throw new CantStartPluginException("Cannot start WalletStoreNetworkService plugin.", FermatException.wrapException(exception), null, null);
        }


        /**
         * I will initialize the Monitoring agent
         */
        agent = new WalletStoreNetworkServiceMonitoringAgent(eventManager, errorManager, logManager, pluginDatabaseSystem, pluginId);
        try {
            agent.start();
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_STORE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }


        /**
         * I will initialize the handling of platform events.
         */
        EventListener eventListener;
        EventHandler eventHandler;


        this.serviceStatus = ServiceStatus.STARTED;

    }

    /**
     * Creates the database with the Database Factory
     * @throws CantCreateDatabaseException
     */
    private void createWalletStoreNetworkServiceDatabase() throws CantCreateDatabaseException {
        WalletStoreNetworkServiceDatabaseFactory walletStoreNetworkServiceDatabaseFactory = new WalletStoreNetworkServiceDatabaseFactory(errorManager, logManager, pluginDatabaseSystem);
        database = walletStoreNetworkServiceDatabaseFactory.createDatabase(pluginId, WalletStoreNetworkServiceDatabaseConstants.WALLET_STORE_DATABASE);
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

        for (EventListener eventListener : listenersAdded) {
            eventManager.removeListener(eventListener);
        }

        listenersAdded.clear();
        this.serviceStatus = ServiceStatus.STOPPED;

    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }


    /**
     * NetworkService Interface implementation.
     */
    
    @Override
    public UUID getId() {
        return null;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * UsesFileSystem Interface implementation.
     */

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }


    /**
     * DealWithEvents Interface implementation.
     */

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }



    /**
     *DealWithErrors Interface implementation.
     */

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }


    /**
     * DealsWithPluginIdentity methods implementation.
     */

    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }


    /**
     * DealsWithLogger Interface implementation.
     */

    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    /**
     * LogManagerForDevelopers Interface implementation.
     */

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.WalletStoreNetworkServicePluginRoot");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.WalletStoreNetworkServiceDatabaseFactory");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.WalletStoreNetworkServiceDatabaseDao");
        /**
         * I return the values.
         */
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
            if (WalletStoreNetworkServicePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                WalletStoreNetworkServicePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                WalletStoreNetworkServicePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                WalletStoreNetworkServicePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }
    }

    //todo delete - TESTING
    private void TestPublishWallet() throws MalformedURLException, CantPublishWalletInCatalogException {
        UUID walletId = UUID.randomUUID();
        System.out.println("Id to install: " + walletId.toString());
        com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.CatalogItem catalogItem;
        catalogItem = new com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.CatalogItem();
        catalogItem.setId(walletId);
        catalogItem.setDefaultSizeInBytes(100);
        catalogItem.setName("MatiWallet");
        catalogItem.setCategory(WalletCategory.BRANDED_NICHE_WALLET);
        catalogItem.setDescription("Prueba de insert");

        byte[] myIcon = new byte[]{114, 22};
        catalogItem.setIcon(myIcon);
        catalogItem.setWalletCatalogId(walletId);

        com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Skin skin;
        skin = new com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Skin();
        byte[] presentationImage = new byte[]{114, 22};
        skin.setPresentationImage(presentationImage);
        skin.setUrl(new URL("http://example.com/pages/"));
        skin.setSkinSizeInBytes(100);
        skin.setFinalWalletVersion(new Version(1, 0, 0));
        skin.setHasVideoPreview(false);
        skin.setInitialWalletVersion(new Version("1.0.0"));
        skin.setVersion(new Version(1, 0, 0));
        skin.setWalletId(walletId);
        skin.setId(UUID.randomUUID());
        skin.setName("Mi skin");
        skin.setIsDefault(true);
        //New set. Sets the ScreenSize
        skin.setScreenSize(ScreenSize.MEDIUM);


        com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Designer designer;
        designer = new com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Designer();
        designer.setiD(UUID.randomUUID());
        designer.setName("Diseñador");
        designer.setPublicKey("DFSDFKSDFPSDFJSDFsdkfjskdf");

        skin.setDesigner(designer);


        com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.DetailedCatalogItem detailedCatalogItem;
        detailedCatalogItem = new com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.DetailedCatalogItem();
        detailedCatalogItem.setDefaultSkin(skin);
        detailedCatalogItem.setVersion(new Version("1.0.0"));
        detailedCatalogItem.setPlatformInitialVersion(new Version("1.0.0"));
        detailedCatalogItem.setPlatformFinalVersion(new Version("1.0.0"));

        com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Language language = new com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Language();
        language.setUrl(new URL("http://example.com/pages/"));
        language.setWalletId(walletId);
        language.setId(UUID.randomUUID());
        language.setInitialWalletVersion(new Version("1.0.0"));
        language.setFinalWalletVersion(new Version("1.0.0"));
        language.setLanguageLabel("Espaól");
        language.setIsDefault(true);
        language.setVersion(new Version("1.0.0"));
        language.setLanguageName(Languages.SPANISH);
        language.setLanguagePackageSizeInBytes(100);


        com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Translator translator;
        translator = new com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Translator();

        translator.setId(UUID.randomUUID());
        translator.setName("Traductor");
        translator.setPublicKey("SDSDFSDFskdmfskdjfsdkjf");
        language.setTranslator(translator);




        detailedCatalogItem.setLanguage(language);
        com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Developer developer;
        developer = new com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Developer();
        developer.setName("Rodrigo");
        developer.setid(UUID.randomUUID());
        developer.setPublicKey("SDSDSDSDasdojasdiuahsdkasjdaskdasdk");
        detailedCatalogItem.setDeveloper(developer);

        catalogItem.setDetailedCatalogItem(detailedCatalogItem);


        this.publishWallet(catalogItem);

    }
}
