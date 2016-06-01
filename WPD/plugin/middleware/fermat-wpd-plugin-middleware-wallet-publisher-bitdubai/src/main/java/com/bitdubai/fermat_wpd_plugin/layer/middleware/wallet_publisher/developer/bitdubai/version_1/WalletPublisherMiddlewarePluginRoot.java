package com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Service;
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
import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_wpd_api.all_definition.AppNavigationStructure;
import com.bitdubai.fermat_wpd_api.all_definition.enums.WalletFactoryProjectState;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Language;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ScreenSize;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_identity.designer.interfaces.DesignerIdentity;
import com.bitdubai.fermat_api.layer.dmp_identity.translator.interfaces.TranslatorIdentity;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_identity.developer.exceptions.CantSingMessageException;
import com.bitdubai.fermat_wpd_api.layer.wpd_identity.developer.interfaces.DeveloperIdentity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.enums.FactoryProjectType;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.interfaces.WalletFactoryProject;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.interfaces.WalletFactoryProjectManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_publisher.interfaces.WalletPublisherMiddlewareManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_publisher.interfaces.WalletPublisherMiddlewarePlugin;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.WalletStoreManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.exceptions.CantGetPublishedComponentInformationException;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.interfaces.InformationPublishedComponent;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.database.WalletPublisherMiddlewareDatabaseConstants;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.database.WalletPublisherMiddlewareDatabaseFactory;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.database.WalletPublisherMiddlewareDeveloperDatabaseFactory;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.exceptions.CantInitializeWalletPublisherMiddlewareDatabaseException;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.structure.WalletPublisherMiddlewareManagerImpl;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.WalletPublisherMiddlewarePluginRoot</code> is
 * the responsible to initialize all component to work together, and hold all resources they needed.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/07/15.
 * Update by Roberto Requena - (rart3001@gmail.com) on 04/08/2015
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletPublisherMiddlewarePluginRoot extends AbstractPlugin implements
        LogManagerForDevelopers,
        WalletPublisherMiddlewarePlugin,
        DatabaseManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER    )
    private LogManager logManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER         )
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededPluginReference(platform = Platforms.WALLET_PRODUCTION_AND_DISTRIBUTION, layer = Layers.MIDDLEWARE, plugin = Plugins.WALLET_FACTORY)
    private WalletFactoryProjectManager walletFactoryProjectManager;

    @NeededPluginReference(platform = Platforms.WALLET_PRODUCTION_AND_DISTRIBUTION, layer = Layers.NETWORK_SERVICE, plugin = Plugins.WALLET_STORE)
    private WalletStoreManager walletStoreManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM    )
    private PluginFileSystem pluginFileSystem;


    static Map<String, LogLevel> newLoggingLevel = new HashMap<>();

    /**
     * Represent the walletPublisherMiddlewareManagerImpl
     */
    private WalletPublisherMiddlewareManagerImpl walletPublisherMiddlewareManagerImpl;

    /**
     * Represent the walletPublisherMiddlewareDeveloperDatabaseFactory
     */
    private WalletPublisherMiddlewareDeveloperDatabaseFactory walletPublisherMiddlewareDeveloperDatabaseFactory;

    /**
     * Represent the dataBase
     */
    private Database dataBase;

    /**s
     * Constructor
     */
    public WalletPublisherMiddlewarePluginRoot(){
        super(new PluginVersionReference(new Version()));
    }

    /**
     * This method validate is all required resource are injected into
     * the plugin root by the platform
     *
     * @throws CantStartPluginException
     */
    private void validateInjectedResources() throws CantStartPluginException {

        /*
         * Validate If all resources are not null
         */
        if (logManager                      == null ||
                errorManager                == null ||
                errorManager                == null ||
                walletFactoryProjectManager == null ||
                pluginFileSystem            == null ||
                pluginDatabaseSystem        == null) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("logManager: " + logManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: " + errorManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("walletFactoryProjectManager: " + walletFactoryProjectManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("walletStoreManager: " + walletStoreManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("walletStoreManager: " + walletStoreManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginFileSystem: " + pluginFileSystem);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);

            String context = contextBuffer.toString();
            String possibleCause = "No all required resource are injected";
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, null, context, possibleCause);

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WPD_WALLET_PUBLISHER_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
            throw pluginStartException;
        }

    }


    /**
     * This method initialize the database
     *
     * @throws CantInitializeWalletPublisherMiddlewareDatabaseException
     */
    private void initializeDb() throws CantInitializeWalletPublisherMiddlewareDatabaseException {

        try {
            /*
             * Open new database connection
             */
            this.dataBase = this.pluginDatabaseSystem.openDatabase(pluginId, WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            /*
             * The database exists but cannot be open. I can not handle this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WPD_WALLET_PUBLISHER_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeWalletPublisherMiddlewareDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

        } catch (DatabaseNotFoundException e) {

            /*
             * The database no exist may be the first time the plugin is running on this device,
             * We need to create the new database
             */
            WalletPublisherMiddlewareDatabaseFactory walletPublisherMiddlewareDatabaseFactory = new WalletPublisherMiddlewareDatabaseFactory(pluginDatabaseSystem);

            try {

                /*
                 * We create the new database
                 */
                this.dataBase = walletPublisherMiddlewareDatabaseFactory.createDatabase(pluginId, WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            } catch (CantCreateDatabaseException cantOpenDatabaseException) {

                /*
                 * The database cannot be created. I can not handle this situation.
                 */
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WPD_WALLET_PUBLISHER_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantOpenDatabaseException);
                throw new CantInitializeWalletPublisherMiddlewareDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

            }
        }finally {

            //Close the database
            if (dataBase != null){
                dataBase.closeDatabase();
            }
        }

    }

    /**
     * (non-Javadoc)
     * @see Service#start()
     */
    @Override
    public void start() throws CantStartPluginException {

        System.out.println("WalletPublisherMiddlewarePluginRoot - start()");

        /*
         * Validate required resources
         */
        validateInjectedResources();

        try {

            /*
             * Initialize Database
             */
            initializeDb();

            /*
             * Initialize Developer Database Factory
             */
            walletPublisherMiddlewareDeveloperDatabaseFactory = new WalletPublisherMiddlewareDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
            walletPublisherMiddlewareDeveloperDatabaseFactory.initializeDatabase();

            /*
             * Initialize the manager
             */
            walletPublisherMiddlewareManagerImpl = new WalletPublisherMiddlewareManagerImpl(pluginId, pluginFileSystem, dataBase, walletStoreManager, logManager);

        } catch (CantInitializeWalletPublisherMiddlewareDatabaseException exception) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The Wallet Publisher Database triggered an unexpected problem that wasn't able to solve by itself";
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, context, possibleCause);

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
            throw pluginStartException;
        }


        //test();

        this.serviceStatus = ServiceStatus.STARTED;
    }

    /**
     * (non-Javadoc)
     * @see LogManagerForDevelopers#getClassesFullPath()
     */
    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("WalletPublisherMiddlewarePluginRoot");

        return returnedClasses;
    }

    /**
     * (non-Javadoc)
     * @see LogManagerForDevelopers#setLoggingLevelPerClass(Map<String, LogLevel>)
     */
    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {

        /*
         * I will check the current values and update the LogLevel in those which is different
         */
        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {

            /*
             * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
             */
            if (WalletPublisherMiddlewarePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                WalletPublisherMiddlewarePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                WalletPublisherMiddlewarePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                WalletPublisherMiddlewarePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }

    }

    /**
     * (non-Javadoc)
     * @see DatabaseManagerForDevelopers#getDatabaseList(DeveloperObjectFactory)
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return walletPublisherMiddlewareDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory);
    }

    /**
     * (non-Javadoc)
     * @see DatabaseManagerForDevelopers#getDatabaseTableList(DeveloperObjectFactory, DeveloperDatabase)
     */
    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return walletPublisherMiddlewareDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    /**
     * (non-Javadoc)
     * @see DatabaseManagerForDevelopers#getDatabaseTableContent(DeveloperObjectFactory, DeveloperDatabase, DeveloperDatabaseTable)
     */
    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return walletPublisherMiddlewareDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
    }

    /**
     * (non-Javadoc)
     * @see WalletPublisherMiddlewarePlugin#getWalletPublisherMiddlewareManagerInstance()
     */
    @Override
    public WalletPublisherMiddlewareManager getWalletPublisherMiddlewareManagerInstance() {
        return walletPublisherMiddlewareManagerImpl;
    }


    private void test(){

        try {

            WalletPublisherMiddlewareManager walletPublisherMiddlewareManager = getWalletPublisherMiddlewareManagerInstance();

            WalletFactoryProject walletFactoryProject = constructWalletFactoryProjectTest();
            byte[] icon = new byte[] { 0x11, 0x22, 0x33 };
            byte[] mainScreenShot = new byte[] { 0x11, 0x22, 0x33 };
            List<byte[]> screenShotDetails = new ArrayList<>();

            screenShotDetails.add(icon);
            screenShotDetails.add(mainScreenShot);

            URL videoUrl = new URL("http://www.youtube.com/watch?v=pBzjx7V3Ldw");
            String observations = "Its Rock!";
            Version initialPlatformVersion = new Version(1,0,0);
            Version finalPlatformVersion = new Version(1,0,0);
            String publisherIdentityPublicKey = "04D707E1C33B2C82AE81E3FACA2025D1E0E439F9AAFD52CA844D3AFA47A0480093EF343790546F1E7C1BB454A426E054E26F080A61B1C0083C25EE77C7F97C6A80";
            String signature = "25928f344d466ae103d9a6643113a5003f061e8d81ec64048aafa3cd7bfd25cf 26337334089289ea0de1770a067d110c776b4a6dfba25c1ef218eb5cb639c6c5";
            URL publisherWebsiteUrl = new URL("http://www.publishertest.com");

            walletPublisherMiddlewareManager.publishWallet(walletFactoryProject, icon, mainScreenShot, screenShotDetails, videoUrl, observations,  initialPlatformVersion, finalPlatformVersion, publisherWebsiteUrl, publisherIdentityPublicKey, signature);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private WalletFactoryProject constructWalletFactoryProjectTest(){
        WalletFactoryProject walletFactoryProject = new WalletFactoryProject() {

            @Override
            public String getProjectPublicKey() {
                return "04D707E1C33B2C82AE81E3FACA2025D1E0E439F9AAFD52CA844D3AFA47A0480093EF343790546F1E7C1BB454A426E054E26F080A61B1C0083C25EE77C7F97C6A80";
            }

            @Override
            public void setProjectPublicKey(String publickKey) {
                //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
            }

            @Override
            public String getName() {
                return "Wallet Publication Test "+ System.currentTimeMillis();
            }

            @Override
            public void setName(String name) {
                //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
            }

            @Override
            public String getDescription() {
                return "Wallet Test Publication description "+ System.currentTimeMillis();
            }

            @Override
            public void setDescription(String description) {
                //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
            }

            @Override
            public WalletType getWalletType() {
                return WalletType.NICHE;
            }

            @Override
            public void setWalletType(WalletType walletType) {
                //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
            }

            @Override
            public WalletFactoryProjectState getProjectState() {
                return WalletFactoryProjectState.CLOSED;
            }

            @Override
            public void setProjectState(WalletFactoryProjectState projectState) {
                //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
            }

            @Override
            public Timestamp getCreationTimestamp() {
                return new Timestamp(System.currentTimeMillis());
            }

            @Override
            public void setCreationTimestamp(Timestamp timestamp) {
                //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
            }

            @Override
            public Timestamp getLastModificationTimestamp() {
                return new Timestamp(System.currentTimeMillis());
            }

            @Override
            public void setLastModificationTimeststamp(Timestamp timestamp) {
                //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
            }

            @Override
            public int getSize() {
                return 50;
            }

            @Override
            public void setSize(int size) {
                //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
            }

            @Override
            public Skin getDefaultSkin() {
                return constructSkinTest();
            }

            @Override
            public void setDefaultSkin(Skin skin) {
                //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
            }

            @Override
            public List<Skin> getSkins() {
                List<Skin> skins = new ArrayList<>();
                skins.add(getDefaultSkin());
                return skins;
            }

            @Override
            public void setSkins(List<Skin> skins) {
                //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
            }

            @Override
            public Language getDefaultLanguage() {
                return constructLanguageTest();
            }

            @Override
            public void setDefaultLanguage(Language language) {
                //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
            }

            @Override
            public List<Language> getLanguages() {
                List<Language> languages = new ArrayList<>();
                languages.add(getDefaultLanguage());
                return languages;
            }

            @Override
            public void setLanguages(List<Language> languages) {
                //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
            }


            @Override
            public AppNavigationStructure getNavigationStructure() {

                AppNavigationStructure appNavigationStructure = new AppNavigationStructure();
                appNavigationStructure.setDeveloper(new DeveloperIdentity() {
                    @Override
                    public String getAlias() {
                        return "Rart3001";
                    }

                    @Override
                    public String getPublicKey() {
                        return "04D707E1C33B2C82AE81E3FACA2025D1E0E439F9AAFD52CA844D3AFA47A0480093EF343790546F1E7C1BB454A426E054E26F080A61B1C0083C25EE77C7F97C6A80";
                    }

                    @Override
                    public String createMessageSignature(String mensage) throws CantSingMessageException {
                        //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
                        return null;
                    }
                });


                return appNavigationStructure;
            }

            @Override
            public void setNavigationStructure(AppNavigationStructure navigationStructure) {
                //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
            }

            @Override
            public WalletCategory getWalletCategory() {
                return WalletCategory.BRANDED_NICHE_WALLET;
            }

            @Override
            public void setWalletCategory(WalletCategory walletCategory) {
                //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
            }

            @Override
            public FactoryProjectType getFactoryProjectType() {
                return FactoryProjectType.WALLET;
            }

            @Override
            public void setFactoryProjectType(FactoryProjectType factoryProjectType) {
                //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
            }
        };

        return walletFactoryProject;
    }


    private Skin constructSkinTest(){

        Skin skin = new Skin();

        skin.setId(UUID.randomUUID());
        skin.setName("Skin Publication Test " + System.currentTimeMillis());
        skin.setScreenSize(ScreenSize.SMALL);
        skin.setDesigner(new DesignerIdentity() {
            @Override
            public String getAlias() {
                return "Rart3001";
            }

            @Override
            public String getPublicKey() {
                return "04D707E1C33B2C82AE81E3FACA2025D1E0E439F9AAFD52CA844D3AFA47A0480093EF343790546F1E7C1BB454A426E054E26F080A61B1C0083C25EE77C7F97C6A80";
            }

            @Override
            public String createMessageSignature(String mensage) throws com.bitdubai.fermat_api.layer.dmp_identity.designer.exceptions.CantSingMessageException {
                //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
                return null;
            }
        });

        return skin;

    }


    private Language constructLanguageTest(){

        Language language = new Language();
        language.setId(UUID.randomUUID());
        language.setName("Language Publication Test " + System.currentTimeMillis());
        language.setType(Languages.LATIN_AMERICAN_SPANISH);
        language.setVersion(new Version(1, 0, 0));
        language.setTranslator(new TranslatorIdentity() {
            @Override
            public String getAlias() {
                return "Rart3001";
            }

            @Override
            public String getPublicKey() {
                return "04D707E1C33B2C82AE81E3FACA2025D1E0E439F9AAFD52CA844D3AFA47A0480093EF343790546F1E7C1BB454A426E054E26F080A61B1C0083C25EE77C7F97C6A80";
            }

            @Override
            public String createMessageSignature(String mensage) throws com.bitdubai.fermat_api.layer.dmp_identity.translator.exceptions.CantSingMessageException {
                //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
                return null;
            }
        });

        return language;
    }

    private void test2(){
        WalletPublisherMiddlewareManager walletPublisherMiddlewareManager = getWalletPublisherMiddlewareManagerInstance();

        try {

            List<InformationPublishedComponent> list = walletPublisherMiddlewareManager.getPublishedComponents("04D707E1C33B2C82AE81E3FACA2025D1E0E439F9AAFD52CA844D3AFA47A0480093EF343790546F1E7C1BB454A426E054E26F080A61B1C0083C25EE77C7F97C6A80");

            for (InformationPublishedComponent info:list) {
                System.out.println(info.toString());
            }

            InformationPublishedComponent infoWithDetails = walletPublisherMiddlewareManager.getInformationPublishedComponentWithDetails(list.get(0).getId());
            System.out.println(infoWithDetails.toString());


        } catch (CantGetPublishedComponentInformationException e) {
            e.printStackTrace();
        }
    }
}