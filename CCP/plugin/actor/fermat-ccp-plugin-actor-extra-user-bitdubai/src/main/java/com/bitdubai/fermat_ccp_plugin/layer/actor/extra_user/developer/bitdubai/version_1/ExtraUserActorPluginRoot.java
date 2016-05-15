package com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.CantSetPhotoException;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.ExtraUserNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_ccp_api.layer.actor.Actor;
import com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.exceptions.CantInitializeExtraUserActorDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.exceptions.CantLoadPrivateKeyException;
import com.bitdubai.fermat_pip_api.layer.actor.exception.CantGetDataBaseTool;
import com.bitdubai.fermat_pip_api.layer.actor.exception.CantGetLogTool;

import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.interfaces.ExtraUserManager;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.CantCreateExtraUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.CantGetExtraUserException;
import com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.database.ExtraUserActorDeveloperDatabaseFactory;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * The Class <code>ExtraUserActorPluginRoot</code>
 * Implements the ExtraUserManager interface with all his methods.
 * <p/>
 * In this plug-in manages a registry of known extra users.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 02/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
@PluginInfo(createdBy = "Natalia Cortez", maintainerMail = "nattyco@gmail.com", platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.DESKTOP_MODULE, plugin = Plugins.WALLET_MANAGER)

public class ExtraUserActorPluginRoot extends AbstractPlugin implements
        DatabaseManagerForDevelopers,
        ExtraUserManager,
        LogManagerForDevelopers {


    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    private LogManager logManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;


    public ExtraUserActorPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }


    static Map<String, LogLevel> newLoggingLevel = new HashMap<>();

    /**
     * ExtraUserManager Interface member variables.
     */
    private com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.database.ExtraUserActorDao extraUserActorDao;

    public static final String EXTRA_USERS_PROFILE_IMAGE_DIRECTORY_NAME = "extraUserIdentityProfileImages";
    public static final String EXTRA_USERS_PRIVATE_KEYS_DIRECTORY_NAME = "extraUserIdentityPrivateKeys";

    /**
     * Service Interface implementation.
     */
    @Override
    public void start() throws CantStartPluginException {
        logManager.log(ExtraUserActorPluginRoot.getLogLevelByClass(this.getClass().getName()), "Extra User Actor Plugin Initializing...", null, null);

        try {
            extraUserActorDao = new com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.database.ExtraUserActorDao(pluginDatabaseSystem, pluginId);
            extraUserActorDao.initialize();
        } catch (CantInitializeExtraUserActorDatabaseException e) {
            reportError( UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, e, "There is a problem when trying to initialize ExtraUser DAO", null);
        } catch (Exception e) {
            reportError( UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, e, "There is a problem I can't identify.", null);
        }

        logManager.log(ExtraUserActorPluginRoot.getLogLevelByClass(this.getClass().getName()), "Extra User Actor Plugin Successfully initialized...", null, null);

        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        extraUserActorDao = null;
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public Actor createActor(String actorName) throws CantCreateExtraUserException {
        logManager.log(ExtraUserActorPluginRoot.getLogLevelByClass(this.getClass().getName()), "Creating Extra User...", null, null);

        ECCKeyPair keyPair = new ECCKeyPair();
        String publicKey = keyPair.getPublicKey();
        String privateKey = keyPair.getPrivateKey();

        try {
            long unixTime = System.currentTimeMillis() / 1000L;


            persistPrivateKey(privateKey, publicKey);

            extraUserActorDao.createActor(actorName, publicKey, unixTime);
        } catch (CantCreateExtraUserException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.exceptions.CantPersistPrivateKeyException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateExtraUserException(CantCreateExtraUserException.DEFAULT_MESSAGE, e, "Cannot persist private key file.", null);
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateExtraUserException(CantCreateExtraUserException.DEFAULT_MESSAGE, e, "There is a problem I can't identify.", null);
        }

        logManager.log(ExtraUserActorPluginRoot.getLogLevelByClass(this.getClass().getName()), "Extra User Created Successfully.", null, null);
        return new com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUserActorRecord(publicKey, privateKey, actorName);
    }

    @Override
    public Actor createActor(String actorName, byte[] photo) throws CantCreateExtraUserException {
        logManager.log(ExtraUserActorPluginRoot.getLogLevelByClass(this.getClass().getName()), "Creating Extra User...", null, null);

        ECCKeyPair keyPair = new ECCKeyPair();
        String publicKey = keyPair.getPublicKey();
        String privateKey = keyPair.getPrivateKey();

        try {
            long unixTime = System.currentTimeMillis() / 1000L;


            persistPrivateKey(privateKey, publicKey);

            persistPhoto(publicKey, photo);

            extraUserActorDao.createActor(actorName, publicKey, unixTime);
        } catch (CantCreateExtraUserException e) {
            reportError( UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.exceptions.CantPersistPrivateKeyException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateExtraUserException(CantCreateExtraUserException.DEFAULT_MESSAGE, e, "Cannot persist private key file.", null);
        } catch (com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.exceptions.CantPersistPhotoException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateExtraUserException(CantCreateExtraUserException.DEFAULT_MESSAGE, e, "Cannot persist photo file.", null);
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateExtraUserException(CantCreateExtraUserException.DEFAULT_MESSAGE, e, "There is a problem I can't identify.", null);
        }

        logManager.log(ExtraUserActorPluginRoot.getLogLevelByClass(this.getClass().getName()), "Extra User Created Successfully.", null, null);
        return new com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUserActorRecord(publicKey, privateKey,actorName);
    }

    @Override
    public Actor getActorByPublicKey(String actorPublicKey) throws CantGetExtraUserException, ExtraUserNotFoundException {
        logManager.log(ExtraUserActorPluginRoot.getLogLevelByClass(this.getClass().getName()), "Trying to get an specific extra user...", null, null);

        try {String privateKey = getPrivateKey(actorPublicKey);
            byte[] image;
            try {
                image = loadPhoto(actorPublicKey);
            } catch(FileNotFoundException e) {
                image = new  byte[0];
            }
            Actor actor = extraUserActorDao.getActorByPublicKey(actorPublicKey);

            return new com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUserActorRecord(actorPublicKey, privateKey,actor.getName(), image);

        } catch (CantGetExtraUserException | ExtraUserNotFoundException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (CantLoadPrivateKeyException e) {
            reportError( UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetExtraUserException(CantGetExtraUserException.DEFAULT_MESSAGE, e, "There is a problem loading private key file.", null);
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetExtraUserException(CantGetExtraUserException.DEFAULT_MESSAGE, e, "There is a problem I can't identify.", null);
        }
    }

    @Override
    public void setPhoto(String actorPublicKey, byte[] photo) throws CantSetPhotoException, ExtraUserNotFoundException {
        try {
            extraUserActorDao.getActorByPublicKey(actorPublicKey);

            PluginBinaryFile pluginBinaryFile = this.pluginFileSystem.getBinaryFile(
                    pluginId,
                    DeviceDirectory.LOCAL_USERS.getName() + "/" + EXTRA_USERS_PROFILE_IMAGE_DIRECTORY_NAME,
                    actorPublicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );
            pluginBinaryFile.loadFromMedia();
            pluginBinaryFile.setContent(photo);
            pluginBinaryFile.persistToMedia();

        } catch (ExtraUserNotFoundException | CantCreateFileException | CantGetExtraUserException | CantPersistFileException | CantLoadFileException e) {
            throw new CantSetPhotoException(CantSetPhotoException.DEFAULT_MESSAGE, e, null, null);
        } catch (FileNotFoundException e){
            try {
                persistPhoto(actorPublicKey, photo);
            } catch (com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.exceptions.CantPersistPhotoException z) {
                reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, z);
                throw new CantSetPhotoException(CantSetPhotoException.DEFAULT_MESSAGE, z, "There is a problem trying to persist the photo.", null);
            } catch (Exception z) {
                reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, z);
                throw new CantSetPhotoException(CantSetPhotoException.DEFAULT_MESSAGE, z, "There is a problem I can't identify.", null);
            }
        } catch (Exception e) {
            reportError( UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantSetPhotoException(CantSetPhotoException.DEFAULT_MESSAGE, e, "There is a problem I can't identify.", null);
        }
    }

    private void persistPrivateKey(String privateKey, String publicKey) throws com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.exceptions.CantPersistPrivateKeyException {
        try {
            PluginTextFile file = this.pluginFileSystem.createTextFile(
                    pluginId,
                    DeviceDirectory.LOCAL_USERS.getName() + "/" + EXTRA_USERS_PRIVATE_KEYS_DIRECTORY_NAME,
                    publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.setContent(privateKey);

            file.persistToMedia();

        } catch (CantPersistFileException | CantCreateFileException e) {
            throw new com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.exceptions.CantPersistPrivateKeyException(com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.exceptions.CantPersistPrivateKeyException.DEFAULT_MESSAGE, e, "Error creating or persisting file.", null);
        } catch (Exception e) {
            throw new com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.exceptions.CantPersistPrivateKeyException(com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.exceptions.CantPersistPrivateKeyException.DEFAULT_MESSAGE, FermatException.wrapException(e), "", "");
        }
    }

   private String getPrivateKey(String publicKey) throws CantLoadPrivateKeyException {
        try {
            PluginTextFile file = this.pluginFileSystem.getTextFile(
                    pluginId,
                    DeviceDirectory.LOCAL_USERS.getName() + "/" + EXTRA_USERS_PRIVATE_KEYS_DIRECTORY_NAME,
                    publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );
            file.loadFromMedia();
            return file.getContent();
        } catch (CantLoadFileException e) {
            throw new CantLoadPrivateKeyException(CantLoadPrivateKeyException.DEFAULT_MESSAGE, e, "Error persist file.", null);
        } catch (CantCreateFileException e) {
            throw new CantLoadPrivateKeyException(CantLoadPrivateKeyException.DEFAULT_MESSAGE, e, "Error creating file.", null);
        } catch (Exception e) {
            throw new CantLoadPrivateKeyException(CantLoadPrivateKeyException.DEFAULT_MESSAGE, FermatException.wrapException(e), "", "");
        }
    }

    private void persistPhoto(String publicKey, byte[] photo) throws com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.exceptions.CantPersistPhotoException {
        try {
            PluginBinaryFile file = this.pluginFileSystem.createBinaryFile(
                    pluginId,
                    DeviceDirectory.LOCAL_USERS.getName() + "/" + EXTRA_USERS_PROFILE_IMAGE_DIRECTORY_NAME,
                    publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.setContent(photo);

            file.persistToMedia();
        } catch (CantPersistFileException e) {
            throw new com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.exceptions.CantPersistPhotoException(com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.exceptions.CantPersistPhotoException.DEFAULT_MESSAGE, e, "Error persist file.", null);

        } catch (CantCreateFileException e) {
            throw new com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.exceptions.CantPersistPhotoException(com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.exceptions.CantPersistPhotoException.DEFAULT_MESSAGE, e, "Error creating file.", null);
        } catch (Exception e) {
            throw new com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.exceptions.CantPersistPhotoException(com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.exceptions.CantPersistPhotoException.DEFAULT_MESSAGE, FermatException.wrapException(e), "", "");
        }
    }

    private byte[] loadPhoto(String publicKey) throws CantLoadPrivateKeyException, FileNotFoundException {
        try {
            PluginBinaryFile file = this.pluginFileSystem.getBinaryFile(
                    pluginId,
                    DeviceDirectory.LOCAL_USERS.getName() + "/" + EXTRA_USERS_PROFILE_IMAGE_DIRECTORY_NAME,
                    publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );
            file.loadFromMedia();
            return file.getContent();
        } catch (CantLoadFileException e) {
            throw new CantLoadPrivateKeyException(CantLoadPrivateKeyException.DEFAULT_MESSAGE, e, "Error loading file.", null);
        } catch (CantCreateFileException e) {
            throw new CantLoadPrivateKeyException(CantLoadPrivateKeyException.DEFAULT_MESSAGE, e, "Error creating file.", null);
        }
    }

    @Override
    public List<String> getClassesFullPath() {

        List<String> returnedClasses = new ArrayList<>();
        returnedClasses.add("ExtraUserActorPluginRoot");
        returnedClasses.add("ExtraUserActorRecord");
        returnedClasses.add("ExtraUserActorDeveloperDatabaseFactory");
        returnedClasses.add("ExtraUserActorDatabaseFactory");
        returnedClasses.add("ExtraUserActorDao");

        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        try {
            for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet())
                ExtraUserActorPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
        } catch (Exception exception) {
            FermatException e = new CantGetLogTool(CantGetLogTool.DEFAULT_MESSAGE, FermatException.wrapException(exception), "setLoggingLevelPerClass: " + ExtraUserActorPluginRoot.newLoggingLevel, "Check the cause");
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }

    /**
     * Static method to get the logging level from any class under root.
     *
     * @param className which i want to know the log level
     * @return log level
     */
    public static LogLevel getLogLevelByClass(String className) {
        try {
            String[] correctedClass = className.split(Pattern.quote("$"));
            return ExtraUserActorPluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception exception) {
            return DEFAULT_LOG_LEVEL;
        }
    }

    /**
     * DatabaseManagerForDevelopers interface implementation
     * Returns the list of databases implemented on this plug in.
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        try {
            ExtraUserActorDeveloperDatabaseFactory dbFactory = new ExtraUserActorDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
            return dbFactory.getDatabaseList(developerObjectFactory);
        } catch (Exception exception) {
            exception.printStackTrace();
            FermatException e = new CantGetDataBaseTool(CantGetDataBaseTool.DEFAULT_MESSAGE, FermatException.wrapException(exception), "getDatabaseList: " + developerObjectFactory, "Check the cause");
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            return new ArrayList<>();
        }
    }

    /**
     * returns the list of tables for the given database
     *
     * @param developerObjectFactory developer object factory
     * @param developerDatabase database from where we want to bring information
     * @return list of instance of DeveloperDatabaseTable
     */
    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        try {
            ExtraUserActorDeveloperDatabaseFactory dbFactory = new ExtraUserActorDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
            return dbFactory.getDatabaseTableList(developerObjectFactory);
        } catch (Exception exception) {
            FermatException e = new CantGetDataBaseTool(CantGetDataBaseTool.DEFAULT_MESSAGE, FermatException.wrapException(exception), "getDatabaseTableContent: Database: " + developerDatabase.getName(), "Check the cause");
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            return new ArrayList<>();
        }
    }

    /**
     * returns the list of records for the passed table
     *
     * @param developerObjectFactory developer object factory
     * @param developerDatabase database from where we want to bring information
     * @param developerDatabaseTable table from where we want to bring information
     * @return a list of instances of DeveloperDatabaseTableRecord
     */
    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        try {
            ExtraUserActorDeveloperDatabaseFactory dbFactory = new ExtraUserActorDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
            dbFactory.initializeDatabase();
            return dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (Exception exception) {
            FermatException e = new CantGetDataBaseTool(CantGetDataBaseTool.DEFAULT_MESSAGE, FermatException.wrapException(exception), "getDatabaseTableContent: Database: " + developerDatabase.getName() + " - Table: " + developerDatabaseTable.getName(), "Check the cause");
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            return new ArrayList<>();
        }
    }
}
