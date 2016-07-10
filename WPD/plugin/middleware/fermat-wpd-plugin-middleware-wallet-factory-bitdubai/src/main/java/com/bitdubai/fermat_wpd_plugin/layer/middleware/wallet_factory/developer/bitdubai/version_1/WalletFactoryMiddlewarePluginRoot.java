package com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_wpd_api.all_definition.enums.WalletFactoryProjectState;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_wpd_api.all_definition.AppNavigationStructure;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ScreenSize;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.enums.FactoryProjectType;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.exceptions.CantChangeProjectStateException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.exceptions.CantCreateWalletFactoryProjectException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.exceptions.CantDeleteWalletFactoryProjectException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.exceptions.CantExportWalletFactoryProjectException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.exceptions.CantSaveWalletFactoryProyect;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.interfaces.WalletFactoryProject;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.interfaces.WalletFactoryProjectManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_factory.exceptions.CantCloneInstalledWalletException;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.database.WalletFactoryMiddlewareDatabaseConstants;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.database.WalletFactoryMiddlewareDatabaseFactory;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.database.WalletFactoryMiddlewareDeveloperDatabaseFactory;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure.WalletFactoryProjectMiddlewareManager;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure.myDesignerIdentity;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure.myTranslatorIdentity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class WalletFactoryMiddlewarePluginRoot extends AbstractPlugin implements
        DatabaseManagerForDevelopers,
        WalletFactoryProjectManager {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER         )
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM    )
    private PluginFileSystem pluginFileSystem;

    WalletFactoryProjectMiddlewareManager walletFactoryProjectMiddlewareManager;

    public WalletFactoryMiddlewarePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }
    /**
     * WalletFactoryProjectMiddlewareManager Interfaces member variables.
     */

    @Override
    public void start() throws CantStartPluginException {
        // I created the WalletFactoryProjectMiddlewareManager object
        walletFactoryProjectMiddlewareManager = new WalletFactoryProjectMiddlewareManager(this.pluginId, pluginDatabaseSystem, pluginFileSystem);

        // I will create the database
        try {
            Database database = pluginDatabaseSystem.openDatabase(pluginId, WalletFactoryMiddlewareDatabaseConstants.DATABASE_NAME);
            database.closeDatabase();
        } catch (CantOpenDatabaseException | DatabaseNotFoundException e) {
            try {
                WalletFactoryMiddlewareDatabaseFactory databaseFactory = new WalletFactoryMiddlewareDatabaseFactory(this.pluginDatabaseSystem);
                databaseFactory.createDatabase(this.pluginId, WalletFactoryMiddlewareDatabaseConstants.DATABASE_NAME);
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WPD_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantStartPluginException();
            } catch (Exception exception) {
                throw new CantStartPluginException("Cannot start WalletFactoryMiddleware plugin.", FermatException.wrapException(exception), null, null);
            }
        }

        //create initial data in the database
        //test(WalletFactoryProjectState.CLOSED);
        //test(WalletFactoryProjectState.IN_PROGRESS);
        this.serviceStatus = ServiceStatus.STARTED;
    }

    /**
     * DatabaseManagerForDevelopers interface implementation
     * Returns the list of databases implemented on this plug in.
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        WalletFactoryMiddlewareDeveloperDatabaseFactory dbFactory = new WalletFactoryMiddlewareDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return dbFactory.getDatabaseList(developerObjectFactory);
    }

    /**
     * returns the list of tables for the given database
     *
     * @param developerObjectFactory
     * @param developerDatabase
     * @return
     */
    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        WalletFactoryMiddlewareDeveloperDatabaseFactory dbFactory = new WalletFactoryMiddlewareDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return dbFactory.getDatabaseTableList(developerObjectFactory);
    }

    /**
     * returns the list of records for the passed table
     *
     * @param developerObjectFactory
     * @param developerDatabase
     * @param developerDatabaseTable
     * @return
     */
    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        WalletFactoryMiddlewareDeveloperDatabaseFactory dbFactory = new WalletFactoryMiddlewareDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        List<DeveloperDatabaseTableRecord> developerDatabaseTableRecordList = null;
        try {
            dbFactory.initializeDatabase();
            developerDatabaseTableRecordList = dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (Exception e) {
            System.out.println("******* Error trying to get database table list for plugin Wallet Factory");
        }
        return developerDatabaseTableRecordList;
    }

    @Override
    public WalletFactoryProject getWalletFactoryProjectByPublicKey(String publicKey) throws CantGetWalletFactoryProjectException {
        return walletFactoryProjectMiddlewareManager.getWalletFactoryProject(publicKey);
    }

    @Override
    public List<WalletFactoryProject> getWalletFactoryProjectByState(WalletFactoryProjectState walletFactoryProjectState) throws CantGetWalletFactoryProjectException {
        List<WalletFactoryProject> projects = walletFactoryProjectMiddlewareManager.getWalletFactoryProjectsByState(walletFactoryProjectState);
        return projects;
    }

    @Override
    public List<WalletFactoryProject> getAllWalletFactoryProjects() throws CantGetWalletFactoryProjectException {
        return walletFactoryProjectMiddlewareManager.getAllFactoryProjects();
    }

    /**
     * Creates and (almost) empty project and persists it.
     * @return
     * @throws CantCreateWalletFactoryProjectException
     */
    @Override
    public WalletFactoryProject createEmptyWalletFactoryProject() throws CantCreateWalletFactoryProjectException {
        return walletFactoryProjectMiddlewareManager.getNewWalletFactoryProject();
    }

    /**
     * Persists in disk and database all changes in the project
     * @param walletFactoryProject
     * @throws CantSaveWalletFactoryProyect
     */
    @Override
    public void saveWalletFactoryProjectChanges(WalletFactoryProject walletFactoryProject) throws CantSaveWalletFactoryProyect {
        try {
            walletFactoryProjectMiddlewareManager.saveWalletFactoryProject(walletFactoryProject);
        } catch (Exception exception) {
            throw new CantSaveWalletFactoryProyect(CantSaveWalletFactoryProyect.DEFAULT_MESSAGE, exception, "there was an error saving the Project information.", null);
        }
    }

    @Override
    public void deleteWalletProjectFactory(WalletFactoryProject walletFactoryProject) throws CantDeleteWalletFactoryProjectException {

    }

    @Override
    public void uploadWalletFactoryProjectToRepository(WalletFactoryProject walletFactoryProject) throws CantSaveWalletFactoryProyect {
        walletFactoryProjectMiddlewareManager.uploadWalletFactoryProjectToRepository((walletFactoryProject));
    }

    @Override
    public void exportProjectToRepository(WalletFactoryProject walletFactoryProject, String githubRepository, String userName, String password) throws CantExportWalletFactoryProjectException {
        try {
            walletFactoryProjectMiddlewareManager.exportProjectToRepository(walletFactoryProject, githubRepository, userName, password);
        } catch (Exception e) {
            throw new CantExportWalletFactoryProjectException(CantExportWalletFactoryProjectException.DEFAULT_MESSAGE, e, null, null);
        }
    }

    @Override
    public void markProkectAsPublished(WalletFactoryProject walletFactoryProject) throws CantChangeProjectStateException {
        walletFactoryProject.setProjectState(WalletFactoryProjectState.PUBLISHED);
        try {
            this.saveWalletFactoryProjectChanges(walletFactoryProject);
        } catch (Exception e) {
            throw new CantChangeProjectStateException(CantChangeProjectStateException.DEFAULT_MESSAGE, e, null, null);
        }
    }

    private void test(WalletFactoryProjectState state){
        try {
            WalletFactoryProject walletFactoryProject = createEmptyWalletFactoryProject();
            walletFactoryProject.setName("ProyectoPrueba");
            walletFactoryProject.setWalletCategory(WalletCategory.BRANDED_REFERENCE_WALLET);
            walletFactoryProject.setDescription("WFP de prueba");
            walletFactoryProject.setProjectState(state);
            walletFactoryProject.setFactoryProjectType(FactoryProjectType.WALLET);
            walletFactoryProject.setCreationTimestamp(new Timestamp(System.currentTimeMillis()));
            walletFactoryProject.setSize(300);
            walletFactoryProject.setProjectPublicKey(UUID.randomUUID().toString());
            walletFactoryProject.setWalletType(WalletType.REFERENCE);
            Skin skin = new Skin();
            skin.setId(UUID.randomUUID());
            skin.setName("SkinTest");

            myDesignerIdentity designerIdentity = new myDesignerIdentity();
            designerIdentity.setAlias("Alias");
            designerIdentity.setPublicKey(UUID.randomUUID().toString());
            skin.setDesigner(designerIdentity);
            skin.setScreenSize(ScreenSize.MEDIUM);
            skin.setSize(100);
            skin.setVersion(new Version("1.0.0"));
            walletFactoryProject.setDefaultSkin(skin);
            List<Skin> skins = new ArrayList<>();
            skins.add(skin);
            walletFactoryProject.setSkins(skins);

            com.bitdubai.fermat_api.layer.all_definition.resources_structure.Language language = new com.bitdubai.fermat_api.layer.all_definition.resources_structure.Language();
            language.setName("TestLanguage");
            myTranslatorIdentity translatorIdentity = new myTranslatorIdentity();
            translatorIdentity.setPublicKey(UUID.randomUUID().toString());
            translatorIdentity.setAlias("Alias");
            language.setTranslator(translatorIdentity);
            language.setId(UUID.randomUUID());
            language.setType(Languages.AMERICAN_ENGLISH);
            language.setVersion(new Version(1, 0, 0));
            language.setSize(100);

            walletFactoryProject.setDefaultLanguage(language);
            List<com.bitdubai.fermat_api.layer.all_definition.resources_structure.Language> languages = new ArrayList<>();
            languages.add(language);
            walletFactoryProject.setLanguages(languages);
            walletFactoryProject.setLastModificationTimeststamp(new Timestamp(System.currentTimeMillis()));

            AppNavigationStructure navigationStructure = new AppNavigationStructure();
            navigationStructure.setPublicKey(UUID.randomUUID().toString());

            walletFactoryProject.setNavigationStructure(navigationStructure);



            this.saveWalletFactoryProjectChanges(walletFactoryProject);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cloneInstalledWallet(InstalledWallet wallet, String newName) throws CantCloneInstalledWalletException {
        walletFactoryProjectMiddlewareManager.cloneInstalledWallet(wallet, newName);
    }
}
