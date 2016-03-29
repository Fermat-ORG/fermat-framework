package com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
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
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.CantInitializeDatabaseException;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.TokenlyApiManager;
import com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1.database.TokenlySongWalletDao;
import com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1.database.TokenlySongWalletDatabaseConstants;
import com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1.database.TokenlySongWalletDatabaseFactory;
import com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1.database.TokenlySongWalletDeveloperDatabaseFactory;
import com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1.structure.TokenlyWalletManager;
import com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1.structure.TokenlyWalletSongVault;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 15/03/16.
 */
public class TokenlyWalletPluginRoot extends AbstractPlugin implements
        DatabaseManagerForDevelopers,
        LogManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    LogManager logManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    protected PluginFileSystem pluginFileSystem;

    @NeededPluginReference(platform = Platforms.TOKENLY, layer = Layers.EXTERNAL_API, plugin = Plugins.TOKENLY_API)
    protected TokenlyApiManager tokenlyApiManager;

    /**
     * Represents the TokenlyWalletManager
     */
    TokenlyWalletManager tokenlyWalletManager;

    /**
     * Represents the TokenlyWalletSongVault
     */
    TokenlyWalletSongVault tokenlyWalletSongVault;

    /**
     * Represents the plugin database
     */
    Database database;

    /**
     * Represents the plugin Developer database factory.
     */
    TokenlySongWalletDeveloperDatabaseFactory tokenlySongWalletDeveloperDatabaseFactory;

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    /**
     * Default constructor
     */
    public TokenlyWalletPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    /**
     * This method returns the Plugin Manager.
     * @return
     */
    public FermatManager getManager(){
        return this.tokenlyWalletManager;
    }

    /**
     * This method initialize the database
     *
     * @throws CantInitializeDatabaseException
     */
    private void initializeDb() throws CantInitializeDatabaseException {

        try {
            /*
             * Open new database connection
             */
            this.database = this.pluginDatabaseSystem.openDatabase(
                    pluginId,
                    TokenlySongWalletDatabaseConstants.DATABASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            /*
             * The database exists but cannot be open. I can not handle this situation.
             */
            throw new CantInitializeDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

        } catch (DatabaseNotFoundException e) {

            /*
             * The database no exist may be the first time the plugin is running on this device,
             * We need to create the new database
             */
            TokenlySongWalletDatabaseFactory tokenlySongWalletDatabaseFactory =
                    new TokenlySongWalletDatabaseFactory(pluginDatabaseSystem);

            try {

                /*
                 * We create the new database
                 */
                this.database = tokenlySongWalletDatabaseFactory.createDatabase(
                        pluginId,
                        TokenlySongWalletDatabaseConstants.DATABASE_NAME);

            } catch (CantCreateDatabaseException cantOpenDatabaseException) {

                /*
                 * The database cannot be created. I can not handle this situation.
                 */
                throw new CantInitializeDatabaseException(
                        cantOpenDatabaseException.getLocalizedMessage());

            }
        }

    }

    public void start() throws CantStartPluginException{
        try{
            /**
             * Init plugin database
             */
            initializeDb();

            /*
             * Initialize Developer Database Factory
             */
            tokenlySongWalletDeveloperDatabaseFactory = new
                    TokenlySongWalletDeveloperDatabaseFactory(pluginDatabaseSystem,
                    pluginId);
            tokenlySongWalletDeveloperDatabaseFactory.initializeDatabase();

            /**
             * Init Database DAO.
             */
            TokenlySongWalletDao tokenlySongWalletDao = new TokenlySongWalletDao(
                    pluginDatabaseSystem,
                    pluginId,
                    database,
                    errorManager);

            /**
             * Init the song vault.
             */
            tokenlyWalletSongVault = new TokenlyWalletSongVault(
                    pluginFileSystem,
                    tokenlyApiManager);

            /**
             * Init plugin manager
             */
            this.tokenlyWalletManager = new TokenlyWalletManager(
                    tokenlySongWalletDao,
                    tokenlyWalletSongVault,
                    tokenlyApiManager,
                    errorManager);
            //testDownloadFile();
        } catch(CantInitializeDatabaseException e){
            errorManager.reportUnexpectedPluginException(
                    Plugins.TOKENLY_API,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new CantStartPluginException(
                    CantStartPluginException.DEFAULT_MESSAGE,
                    e,
                    "Cant start Song Wallet Tokenly plugin.",
                    "There's an error initializing the plugin database");
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.TOKENLY_API,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new CantStartPluginException(
                    CantStartPluginException.DEFAULT_MESSAGE,
                    e,
                    "Cant start Song Wallet Tokenly plugin.",
                    "Unexpected exception");
        }
        /**
         * nothing left to do.
         */
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        try{
            return tokenlySongWalletDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory);
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.TOKENLY_WALLET,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            return null;
        }

    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(
            DeveloperObjectFactory developerObjectFactory,
            DeveloperDatabase developerDatabase) {
        try{
            return tokenlySongWalletDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.TOKENLY_WALLET,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            return null;
        }
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(
            DeveloperObjectFactory developerObjectFactory,
            DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        try{
            return tokenlySongWalletDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.TOKENLY_WALLET,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            return null;
        }
    }

    public static LogLevel getLogLevelByClass(String className) {
        try{
            /**
             * sometimes the classname may be passed dynamically with an $moretext
             * I need to ignore whats after this.
             */
            String[] correctedClass = className.split((Pattern.quote("$")));
            return TokenlyWalletPluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e){
            /**
             * If I couldn't get the correct logging level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        try{
            returnedClasses.add("com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1.TokenlyWalletPluginRoot.java");
            return returnedClasses;
        } catch (Exception exception){
            errorManager.reportUnexpectedPluginException(
                    Plugins.TOKENLY_WALLET,
                    UnexpectedPluginExceptionSeverity.NOT_IMPORTANT,
                    FermatException.wrapException(exception));
            //I'll return an empty list
            return returnedClasses;
        }
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        try{
            /*
         * I will check the current values and update the LogLevel in those which is different
         */
            for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {

            /*
             * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
             */
                if (TokenlyWalletPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                    TokenlyWalletPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                    TokenlyWalletPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                } else {
                    TokenlyWalletPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                }
            }
        } catch (Exception exception){
            errorManager.reportUnexpectedPluginException(
                    Plugins.TOKENLY_WALLET,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
        }
    }

    private void testDownloadFile(){
        try{
            String testURL="https://www.dropbox.com/s/l8df6ixyiweq8yt/appSessionFragmenContract?dl=0";
            String testName="testFileName";
            this.tokenlyWalletSongVault.downloadFile(testURL, testName);
        } catch (Exception e){
            System.out.println("TKY: Test Download exception");
            e.printStackTrace();
        }
    }
}
