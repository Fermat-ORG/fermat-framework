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
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExternalPlatform;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.CantInitializeDatabaseException;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.TokenlyApiManager;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music.MusicUser;
import com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.Fan;
import com.bitdubai.fermat_tky_api.layer.song_wallet.interfaces.WalletSong;
import com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1.database.TokenlySongWalletDao;
import com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1.database.TokenlySongWalletDatabaseConstants;
import com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1.database.TokenlySongWalletDatabaseFactory;
import com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1.database.TokenlySongWalletDeveloperDatabaseFactory;
import com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1.structure.TokenlyWalletManager;
import com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1.structure.TokenlyWalletSongVault;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;


/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 15/03/16.
 */
@PluginInfo(difficulty = PluginInfo.Dificulty.MEDIUM, maintainerMail = "darkpriestrelative@gmail.com", createdBy = "darkestpriest", layer = Layers.SONG_WALLET, platform = Platforms.TOKENLY, plugin = Plugins.TOKENLY_WALLET)
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

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_BROADCASTER_SYSTEM)
    private Broadcaster broadcaster;

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

    /**
     * Represents the plugin database DAO.
     */
    TokenlySongWalletDao tokenlySongWalletDao;

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
            tokenlySongWalletDao = new TokenlySongWalletDao(
                    pluginDatabaseSystem,
                    pluginId,
                    database,
                    errorManager);

            /**
             * Init the song vault.
             */
            tokenlyWalletSongVault = new TokenlyWalletSongVault(
                    pluginFileSystem,
                    tokenlyApiManager,
                    pluginId,
                    broadcaster);

            /**
             * Init plugin manager
             */
            this.tokenlyWalletManager = new TokenlyWalletManager(
                    tokenlySongWalletDao,
                    tokenlyWalletSongVault,
                    tokenlyApiManager,
                    broadcaster);
            //testDownloadFile();
            //testSynchronizeSongs();
            //testAutomaticSyncSongs();
            //testDeleteSong();
            //testDownloadDeletedSong();
            //testDownloadSongsAndRecoverBytesArray();
            //testCancelDownload();
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
            this.tokenlyWalletSongVault.downloadFile(testURL, testName, UUID.randomUUID(),null);
        } catch (Exception e){
            System.out.println("TKY: Test Download exception");
            e.printStackTrace();
        }
    }

    private void testSynchronizeSongs(){
        try{
            Fan fanIdentity = getTestFanIdentity();
            this.tokenlyWalletManager.synchronizeSongsByUser(fanIdentity);
        } catch (Exception e){
            System.out.println("TKY: Test Download song exception");
            e.printStackTrace();
        }
    }

    private void testAutomaticSyncSongs(){
        try{
            //Please, make this test with any songs in database
            Fan fanIdentity = getTestFanIdentity();
            this.tokenlyWalletManager.synchronizeSongs(fanIdentity);
        } catch (Exception e){
            System.out.println("TKY: Test Automatic Download song exception");
            e.printStackTrace();
        }
    }

    private void testDeleteSong(){
        try{
            //Get the available songs
            List<WalletSong> availableSongsList = this.tokenlyWalletManager.getAvailableSongs();
            System.out.println("TKY - AVAILABLE List "+availableSongsList);
            int indexToDelete = 0;
            UUID idToDelete = availableSongsList.get(indexToDelete).getSongId();
            this.tokenlyWalletManager.deleteSong(idToDelete);
            availableSongsList = this.tokenlyWalletManager.getAvailableSongs();
            System.out.println("TKY - NEW AVAILABLE List " + availableSongsList);
            //Get the deleted songs
            List<WalletSong> deletedSongsList = this.tokenlyWalletManager.getDeletedSongs();
            System.out.println("TKY - Deleted List " + deletedSongsList);
        } catch (Exception e){
            System.out.println("TKY: Test Delete song exception");
            e.printStackTrace();
        }
    }

    private void testDownloadDeletedSong(){
        try{
            UUID deletedSongId = UUID.fromString("a5a68fe3-923c-42f4-b0b2-6755b7970fd9");
            Fan fanIdentity = getTestFanIdentity();
            this.tokenlyWalletManager.downloadSong(deletedSongId, fanIdentity.getMusicUser());
        } catch (Exception e){
            System.out.println("TKY: Test download Deleted song exception");
            e.printStackTrace();
        }
    }

    private void testDownloadSongsAndRecoverBytesArray(){
        try{
            //testSynchronizeSongs();
            List<WalletSong> availableSongsList = this.tokenlyWalletManager.getAvailableSongs();
            System.out.println("TKY - AVAILABLE List "+availableSongsList);
            WalletSong songToRecover = availableSongsList.get(0);
            WalletSong fullSong=this.tokenlyWalletManager.getSongWithBytes(
                    songToRecover.getSongId());
            byte[] songBytes = fullSong.getSongBytes();
            FileOutputStream fos = new FileOutputStream("/storage/emulated/0/Music/test/"+fullSong.getName().replace(" ","_"));
            fos.write(songBytes);
            fos.close();
        } catch (Exception e){
            System.out.println("TKY: array bytes exception");
            e.printStackTrace();
        }
    }

    private void testCancelDownload(){
        try {
            Fan fanIdentity = getTestFanIdentity();
            this.tokenlyWalletManager.synchronizeSongsByUser(fanIdentity);
        } catch (Exception e){
            System.out.println("TKY: cancel download exception");
            e.printStackTrace();
        }
    }

    private Fan getTestFanIdentity(){
        Fan fanIdentity = new Fan() {
            @Override
            public List<String> getConnectedArtists() {
                return null;
            }

            @Override
            public void addNewArtistConnected(String userName) {

            }

            @Override
            public String getArtistsConnectedStringList() {
                return null;
            }

            @Override
            public void addArtistConnectedList(String xmlStringList) {

            }

            @Override
            public String getTokenlyId() {
                return null;
            }

            @Override
            public String getUsername() {
                return null;
            }

            @Override
            public String getEmail() {
                return null;
            }

            @Override
            public String getApiToken() {
                return null;
            }

            @Override
            public String getApiSecretKey() {
                return null;
            }


            @Override
            public UUID getId() {
                return null;
            }

            @Override
            public String getPublicKey() {
                return null;
            }

            @Override
            public byte[] getProfileImage() {
                return new byte[0];
            }

            @Override
            public void setNewProfileImage(byte[] imageBytes) {

            }

            @Override
            public ExternalPlatform getExternalPlatform() {
                return null;
            }

            @Override
            public MusicUser getMusicUser() {
                MusicUser hardocedUser = new MusicUser() {
                    @Override
                    public String getTokenlyId() {
                        return "18873727-da0f-4b50-a213-cc40c6b4562d";
                    }

                    @Override
                    public String getUsername() {
                        return "pereznator";
                    }

                    @Override
                    public String getEmail() {
                        return "darkpriestrelative@gmail.com";
                    }

                    @Override
                    public String getApiToken() {
                        return "Tvn1yFjTsisMHnlI";
                    }

                    @Override
                    public String getApiSecretKey() {
                        return "K0fW5UfvrrEVQJQnK27FbLgtjtWHjsTsq3kQFB6Y";
                    }
                };
                return hardocedUser;
            }

            @Override
            public String getUserPassword() {
                return null;
            }
        };
        return fanIdentity;
    }

}
