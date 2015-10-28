package com.bitdubai.fermat_dmp_plugin.layer.identity.translator.developer.bitdubai.version_1.estructure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.dmp_identity.translator.interfaces.TranslatorIdentity;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.exceptions.CantCreateNewDeveloperException;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.exceptions.CantGetUserDeveloperIdentitiesException;
import com.bitdubai.fermat_dmp_plugin.layer.identity.translator.developer.bitdubai.version_1.database.IdentityTranslatorDatabaseConstants;
import com.bitdubai.fermat_dmp_plugin.layer.identity.translator.developer.bitdubai.version_1.database.IdentityTranslatorDatabaseFactory;
import com.bitdubai.fermat_dmp_plugin.layer.identity.translator.developer.bitdubai.version_1.exceptions.CantGetTranslatorIdentityPrivateKeyException;
import com.bitdubai.fermat_dmp_plugin.layer.identity.translator.developer.bitdubai.version_1.exceptions.CantInitializeTranslatorIdentityDatabaseException;
import com.bitdubai.fermat_dmp_plugin.layer.identity.translator.developer.bitdubai.version_1.exceptions.CantPersistPrivateKeyException;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUser;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_pip_plugin.layer.identity_translator.developer.bitdubai.version_1.estructure.IdentityTranslatorDao</code>
 * all methods implementation to access the data base<p/>
 * <p/>
 * <p/>
 * Created by natalia on 31/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class IdentityTranslatorDao {

    private String TRANSLATOR_IDENTITY_PRIVATE_KEYS_FILE_NAME = "translatorIdentityPrivateKeys";

    /**
     * FileSystem Interface member variables.
     */
    PluginFileSystem pluginFileSystem;


    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealsWithPlatformDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    private Database database;

    UUID pluginId;

    /**
     * Constructor
     */

    public IdentityTranslatorDao(ErrorManager errorManager, PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId, PluginFileSystem pluginFileSystem) {
        this.errorManager = errorManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.pluginFileSystem = pluginFileSystem;
    }


    // Private instance methods declarations.

    public void initialize() throws CantInitializeTranslatorIdentityDatabaseException {
        /**
         * I will try to open the translator' database..
         */
        try {
            this.database = this.pluginDatabaseSystem.openDatabase(pluginId, IdentityTranslatorDatabaseConstants.TRANSLATOR_DB_NAME);
        } catch (DatabaseNotFoundException databaseNotFoundException) {

            IdentityTranslatorDatabaseFactory databaseFactory = new IdentityTranslatorDatabaseFactory();
            databaseFactory.setPluginDatabaseSystem(this.pluginDatabaseSystem);
            databaseFactory.setErrorManager(this.errorManager);

            try {
                this.database = databaseFactory.createDatabase(pluginId);
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                String message = CantInitializeTranslatorIdentityDatabaseException.DEFAULT_MESSAGE;
                FermatException cause = cantCreateDatabaseException.getCause();
                String context = "DataBase Factory: " + cantCreateDatabaseException.getContext();
                String possibleReason = "The exception occurred when calling  'databaseFactory.createDatabase()': " + cantCreateDatabaseException.getPossibleReason();

                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TRANSLATOR_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantInitializeTranslatorIdentityDatabaseException(message, cause, context, possibleReason);
            } catch (Exception exception) {
                throw new CantInitializeTranslatorIdentityDatabaseException(CantInitializeTranslatorIdentityDatabaseException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
            }
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            String message = CantInitializeTranslatorIdentityDatabaseException.DEFAULT_MESSAGE;
            FermatException cause = cantOpenDatabaseException.getCause();
            String context = "Create Database:" + cantOpenDatabaseException.getContext();
            String possibleReason = "The exception occurred while trying to open the database of users 'this.database = this.platformDatabaseSystem.openDatabase (\"ExtraUser\")': " + cantOpenDatabaseException.getPossibleReason();

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TRANSLATOR_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            /*
            Modified by Francisco Arce
            */
            throw new CantInitializeTranslatorIdentityDatabaseException(message, cause, context, possibleReason);
        } catch (Exception exception) {
            throw new CantInitializeTranslatorIdentityDatabaseException(CantInitializeTranslatorIdentityDatabaseException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    /**
     * Method that create a new translator in the database.
     *
     * @param alias
     * @param developerKeyPair
     * @param deviceUser
     * @throws CantCreateNewDeveloperException
     */

    public void createNewTranslator(String alias, ECCKeyPair developerKeyPair, DeviceUser deviceUser) throws CantCreateNewDeveloperException {
        try {
            if (aliasExists(alias)) {
                throw new CantCreateNewDeveloperException("Cant create new developer, alias exists.", "Translator Identity", "Cant create new developer, alias exists.");
            }

            DatabaseTable table = this.database.getTable(IdentityTranslatorDatabaseConstants.TRANSLATOR_TABLE_NAME);
            DatabaseTableRecord record = table.getEmptyRecord();

            String publicKey = developerKeyPair.getPublicKey();

            record.setStringValue(IdentityTranslatorDatabaseConstants.TRANSLATOR_DEVELOPER_PUBLIC_KEY_COLUMN_NAME, publicKey);
            record.setStringValue(IdentityTranslatorDatabaseConstants.TRANSLATOR_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME, deviceUser.getPublicKey());
            record.setStringValue(IdentityTranslatorDatabaseConstants.TRANSLATOR_DEVELOPER_ALIAS_COLUMN_NAME, alias);//deviceUser.getAlias()

            table.insertRecord(record);

            // Persist private key on a file
            persistNewUserPrivateKeysFile(publicKey, developerKeyPair.getPrivateKey());
        } catch (CantInsertRecordException e) {
            // Cant insert record.
            throw new CantCreateNewDeveloperException(e.getMessage(), e, "Translator Identity", "Cant create new developer, insert database problems.");
        } catch (CantPersistPrivateKeyException e) {
            // Cant insert record.
            throw new CantCreateNewDeveloperException(e.getMessage(), e, "Translator Identity", "Cant create new developer,persist private key error.");
        } catch (Exception e) {
            // Failure unknown.
            throw new CantCreateNewDeveloperException(e.getMessage(), e, "Translator Identity", "Cant create new developer, unknown failure.");
        }
    }


    /**
     * Method that list the translator related to the parametrized device user.
     *
     * @param deviceUser device user
     * @throws CantGetUserDeveloperIdentitiesException
     */
    public List<TranslatorIdentity> getDevelopersFromCurrentDeviceUser(DeviceUser deviceUser) throws CantGetUserDeveloperIdentitiesException {
        // Setup method.
        List<TranslatorIdentity> list = new ArrayList<TranslatorIdentity>(); // Developer list.
        DatabaseTable table; // Developer table.

        // Get developers identities list.
        try {
            /**
             * 1) Get the table.
             */
            table = this.database.getTable(IdentityTranslatorDatabaseConstants.TRANSLATOR_TABLE_NAME);
            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get developer identity list, table not \" + DeveloperIdentityDatabaseConstants.DEVELOPER_TABLE_NAME + \" found.", "Plugin Identity", "Cant get developer identity list, table not \" + DeveloperIdentityDatabaseConstants.DEVELOPER_TABLE_NAME + \" found.");
            }
            // 2) Find the developers.
            table.setStringFilter(IdentityTranslatorDatabaseConstants.TRANSLATOR_DEVELOPER_PUBLIC_KEY_COLUMN_NAME, deviceUser.getPublicKey(), DatabaseFilterType.EQUAL);
            table.loadToMemory();
            // 3) Get developers.
            for (DatabaseTableRecord record : table.getRecords()) {
                // Add records to list.
                list.add(new IdentityTranslatorTranslator(record.getStringValue(IdentityTranslatorDatabaseConstants.TRANSLATOR_DEVELOPER_ALIAS_COLUMN_NAME),
                        record.getStringValue(IdentityTranslatorDatabaseConstants.TRANSLATOR_DEVELOPER_PUBLIC_KEY_COLUMN_NAME), getDeveloperIdentiyPrivateKey(record.getStringValue(IdentityTranslatorDatabaseConstants.TRANSLATOR_DEVELOPER_PUBLIC_KEY_COLUMN_NAME))));
            }
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetUserDeveloperIdentitiesException(e.getMessage(), e, "Translator Identity", "Cant load " + IdentityTranslatorDatabaseConstants.TRANSLATOR_TABLE_NAME + " table in memory.");
        } catch (CantGetTranslatorIdentityPrivateKeyException e) {
            // Failure unknown.
            throw new CantGetUserDeveloperIdentitiesException(e.getMessage(), e, "Translator Identity", "Can't get private key.");
        } catch (Exception e) {
            throw new CantGetUserDeveloperIdentitiesException(e.getMessage(), FermatException.wrapException(e), "Translator Identity", "Cant get developer identity list, unknown failure.");
        }
        // Return the list values.
        return list;
    }

    /**
     * Puligin private methods
     */

    private void persistNewUserPrivateKeysFile(String publicKey, String privateKey) throws CantPersistPrivateKeyException {
        try {
            PluginTextFile file = this.pluginFileSystem.createTextFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    TRANSLATOR_IDENTITY_PRIVATE_KEYS_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.setContent(privateKey);
            file.persistToMedia();
        } catch (CantPersistFileException e) {
            throw new CantPersistPrivateKeyException("CAN'T PERSIST PRIVATE KEY ", e, "Error persist file.", null);
        } catch (CantCreateFileException e) {
            throw new CantPersistPrivateKeyException("CAN'T PERSIST PRIVATE KEY ", e, "Error creating file.", null);
        }
    }

    public String getDeveloperIdentiyPrivateKey(String publicKey) throws CantGetTranslatorIdentityPrivateKeyException {
        String privateKey = "";
        try {
            PluginTextFile file = this.pluginFileSystem.getTextFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    TRANSLATOR_IDENTITY_PRIVATE_KEYS_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.loadFromMedia();
            privateKey = file.getContent();
        } catch (CantLoadFileException e) {
            throw new CantGetTranslatorIdentityPrivateKeyException("CAN'T GET PRIVATE KEY ", e, "Error loaded file.", null);
        } catch (FileNotFoundException | CantCreateFileException e) {
            throw new CantGetTranslatorIdentityPrivateKeyException("CAN'T GET PRIVATE KEY ", e, "Error getting developer identity private keys file.", null);
        }
        return privateKey;
    }

    /**
     * <p>Method that check if alias exists.
     *
     * @param alias
     * @return boolean exists
     * @throws CantCreateNewDeveloperException
     */
    private boolean aliasExists(String alias) throws CantCreateNewDeveloperException {
        DatabaseTable table;
        /**
         * Get developers identities list.
         * I select records on table
         */
        try {
            table = this.database.getTable(IdentityTranslatorDatabaseConstants.TRANSLATOR_TABLE_NAME);
            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check if alias exists, table not \" + DeveloperIdentityDatabaseConstants.DEVELOPER_TABLE_NAME + \" found.", "Plugin Identity", "Cant check if alias exists, table not \" + DeveloperIdentityDatabaseConstants.DEVELOPER_TABLE_NAME + \" found.");
            }
            table.setStringFilter(IdentityTranslatorDatabaseConstants.TRANSLATOR_DEVELOPER_ALIAS_COLUMN_NAME, alias, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            return table.getRecords().size() > 0;
        } catch (CantLoadTableToMemoryException em) {
            throw new CantCreateNewDeveloperException(em.getMessage(), em, "Plugin Identity", "Cant load " + IdentityTranslatorDatabaseConstants.TRANSLATOR_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantCreateNewDeveloperException(e.getMessage(), e, "Plugin Identity", "Cant check if alias exists, unknown failure.");
        }
    }
}
