package com.bitdubai.fermat_cht_plugin.layer.identity.chat.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_cht_api.all_definition.enums.ExposureLevel;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantCreateNewDeveloperException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetChatUserIdentityException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetPrivateKeyException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetProfileImageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetUserDeveloperIdentitiesException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantListIdentitiesException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantPersistPrivateKeyException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantPersistProfileImageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantUpdateChatIdentityException;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentity;
import com.bitdubai.fermat_cht_plugin.layer.identity.chat.developer.bitdubai.version_1.structure.ChatIdentityImpl;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUser;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 30/03/16.
 * Edited by Miguel Rincon on 19/04/2016
 */
public class ChatIdentityDatabaseDao {

    private ErrorManager errorManager;

    Database database;
    UUID pluginId;
    private static final String CHAT_PROFILE_IMAGE_FILE_NAME = "chatIdentityProfileImage";
    private static final String CHAT_PRIVATE_KEYS_FILE_NAME = "chatIdentityPrivateKey";
    /**
     * DealsWithPluginDatabaseSystem interface variable and implementation
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginFileSystem interface member variables
     */
    PluginFileSystem pluginFileSystem;

    /**
     * Constructor
     */
    public ChatIdentityDatabaseDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId,
                                   PluginFileSystem pluginFileSystem) throws CantOpenDatabaseException {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId;
        this.pluginFileSystem     = pluginFileSystem;

        try {
            database = openDatabase();
        } catch (CantOpenDatabaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
            throw new CantOpenDatabaseException("Cant Open Database Exception", e);
        } catch (CantCreateDatabaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
            throw new CantOpenDatabaseException("Cant Create Database Exception", e);
        }
    }

    private DatabaseTable getDatabaseTable(String tableName) {

        return database.getTable(tableName);
    }

    private Database openDatabase() throws CantOpenDatabaseException, CantCreateDatabaseException {
        try {
            database = pluginDatabaseSystem.openDatabase(this.pluginId, ChatIdentityDatabaseConstants.CHAT_DATABASE_NAME);
        } catch (DatabaseNotFoundException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, FermatException.wrapException(e));

            ChatIdentityDatabaseFactory chatIdentityDatabaseFactory = new ChatIdentityDatabaseFactory(pluginDatabaseSystem);
            database = chatIdentityDatabaseFactory.createDatabase(this.pluginId, ChatIdentityDatabaseConstants.CHAT_DATABASE_NAME);
        }
        return database;
    }

    public void createNewUser(String alias, String publicKey, String privateKey, DeviceUser deviceUser, byte[] profileImage, String country, String state, String city, String connectionState) throws CantCreateNewDeveloperException {

        try {
            if (aliasExists(alias)) {
                throw new CantCreateNewDeveloperException("Cant create new chat, alias exists.", "Chat Identity", "Cant create new Chat, alias exists.");
            }

            persistNewUserPrivateKeysFile(publicKey, privateKey);

            DatabaseTable table = getDatabaseTable(ChatIdentityDatabaseConstants.CHAT_TABLE_NAME); //this.database.getTable(ChatIdentityDatabaseConstants.CHAT_TABLE_NAME);
            DatabaseTableRecord record = table.getEmptyRecord();

            record.setStringValue(ChatIdentityDatabaseConstants.CHAT_PUBLIC_KEY_COLUMN_NAME, publicKey);
            record.setStringValue(ChatIdentityDatabaseConstants.CHAT_ALIAS_COLUMN_NAME, alias);
            record.setStringValue(ChatIdentityDatabaseConstants.CHAT_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME, deviceUser.getPublicKey());
            record.setStringValue(ChatIdentityDatabaseConstants.CHAT_COUNTRY_COLUMN_NAME, country);
            record.setStringValue(ChatIdentityDatabaseConstants.CHAT_STATE_COLUMN_NAME, state);
            record.setStringValue(ChatIdentityDatabaseConstants.CHAT_CITY_COLUMN_NAME, city);
            record.setStringValue(ChatIdentityDatabaseConstants.CHAT_CONNECTION_STATE_COLUMN_NAME, connectionState);

            table.insertRecord(record);

            if (profileImage != null)
                persistNewUserProfileImage(publicKey, profileImage);

        } catch (CantInsertRecordException e) {
            // Cant insert record.
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            throw new CantCreateNewDeveloperException(e.getMessage(), e, "Chat Identity", "Cant create new Chat, insert database problems.");
        } catch (CantPersistPrivateKeyException e) {
            // Cant insert record.
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            throw new CantCreateNewDeveloperException(e.getMessage(), e, "Chat Identity", "Cant create new Chat,persist private key error.");
        } catch (Exception e) {
            // Failure unknown.
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateNewDeveloperException(e.getMessage(), FermatException.wrapException(e), "Chat Identity", "Cant create new Asset Issuer, unknown failure.");
        }
    }

    public void changeExposureLevel(String publicKey, ExposureLevel exposureLevel) throws CantUpdateChatIdentityException
    {
        try {
            /**
             * 1) Get the table.
             */
            DatabaseTable table = getDatabaseTable(ChatIdentityDatabaseConstants.CHAT_TABLE_NAME);//this.database.getTable(ChatIdentityDatabaseConstants.CHAT_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get Chat Identity list, table not found.", "Chat Identity", "Cant get Chat Identity list, table not found.");
            }

            // 2) Find the Intra users.
            table.addStringFilter(ChatIdentityDatabaseConstants.CHAT_PUBLIC_KEY_COLUMN_NAME, publicKey, DatabaseFilterType.EQUAL);
            table.loadToMemory();


            // 3) Get Intra users.
            for (DatabaseTableRecord record : table.getRecords()) {
                //set new values
                record.setStringValue(ChatIdentityDatabaseConstants.CHAT_EXPOSURE_LEVEL_COLUMN_NAME, exposureLevel.getCode());
                table.updateRecord(record);
            }

        } catch (CantUpdateRecordException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            throw new CantUpdateChatIdentityException(e.getMessage(), e, "Chat Identity", "Cant update Chat Identity, database problems.");
        }  catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateChatIdentityException(e.getMessage(), FermatException.wrapException(e), "Chat Identity", "Cant update Chat Identity, unknown failure.");
        }
    }


    public void updateChatIdentity(String publicKey, String alias, byte[] profileImage, String country, String state, String city, String connectionState) throws CantUpdateChatIdentityException {
        try {
            /**
             * 1) Get the table.
             */
            DatabaseTable table = getDatabaseTable(ChatIdentityDatabaseConstants.CHAT_TABLE_NAME);//this.database.getTable(ChatIdentityDatabaseConstants.CHAT_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get Chat Identity list, table not found.", "Chat Identity", "Cant get Chat Identity list, table not found.");
            }

            // 2) Find the Intra users.
            table.addStringFilter(ChatIdentityDatabaseConstants.CHAT_PUBLIC_KEY_COLUMN_NAME, publicKey, DatabaseFilterType.EQUAL);
            table.loadToMemory();


            // 3) Get Intra users.
            for (DatabaseTableRecord record : table.getRecords()) {
                //set new values
                record.setStringValue(ChatIdentityDatabaseConstants.CHAT_ALIAS_COLUMN_NAME, alias);
                record.setStringValue(ChatIdentityDatabaseConstants.CHAT_STATE_COLUMN_NAME, state);
                record.setStringValue(ChatIdentityDatabaseConstants.CHAT_COUNTRY_COLUMN_NAME, country);
                record.setStringValue(ChatIdentityDatabaseConstants.CHAT_CITY_COLUMN_NAME, city);
                record.setStringValue(ChatIdentityDatabaseConstants.CHAT_CONNECTION_STATE_COLUMN_NAME, connectionState);
                table.updateRecord(record);
            }

            if (profileImage != null)
                persistNewUserProfileImage(publicKey, profileImage);

        } catch (CantUpdateRecordException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            throw new CantUpdateChatIdentityException(e.getMessage(), e, "Chat Identity", "Cant update Chat Identity, database problems.");
        } catch (CantPersistProfileImageException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            throw new CantUpdateChatIdentityException(e.getMessage(), e, "Chat Identity", "Cant update Chat Identity, persist image error.");
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateChatIdentityException(e.getMessage(), FermatException.wrapException(e), "Chat Identity", "Cant update Chat Identity, unknown failure.");
        }
    }

    public ChatIdentity getChatIdentity() throws CantGetChatUserIdentityException {

        // Setup method.
        ChatIdentity chatIdentity = null;
        DatabaseTable table; // Intra User table.

        // Get Chat identities list.
        try {

            /**
             * 1) Get the table.
             */
            table = this.database.getTable(ChatIdentityDatabaseConstants.CHAT_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get Cant identity list, table not found.", "Chat Identity", "Cant get Chat identity list, table not found.");
            }


            // 2) Find the Identity Issuers.

            table.loadToMemory();

            // 3) Get Chat Identity.

            for (DatabaseTableRecord record : table.getRecords()) {

                // Add records to list.
                chatIdentity = new ChatIdentityImpl(
                        record.getStringValue(ChatIdentityDatabaseConstants.CHAT_ALIAS_COLUMN_NAME),
                        record.getStringValue(ChatIdentityDatabaseConstants.CHAT_PUBLIC_KEY_COLUMN_NAME),
                        getChatProfileImagePrivateKey(record.getStringValue(ChatIdentityDatabaseConstants.CHAT_PUBLIC_KEY_COLUMN_NAME)),
                        record.getStringValue(ChatIdentityDatabaseConstants.CHAT_COUNTRY_COLUMN_NAME),
                        record.getStringValue(ChatIdentityDatabaseConstants.CHAT_STATE_COLUMN_NAME),
                        record.getStringValue(ChatIdentityDatabaseConstants.CHAT_CITY_COLUMN_NAME),
                        record.getStringValue(ChatIdentityDatabaseConstants.CHAT_CONNECTION_STATE_COLUMN_NAME));
            }
        } catch (CantLoadTableToMemoryException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            throw new CantGetChatUserIdentityException(e.getMessage(), e, "Chat Identity", "Cant load " + ChatIdentityDatabaseConstants.CHAT_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetChatUserIdentityException(e.getMessage(), FermatException.wrapException(e), "Chat Identity", "Cahat identity list, unknown failure.");
        }

        // Return the list values.
        return chatIdentity;
    }

    public List<ChatIdentity> getChatIdentitiesFromCurrentDeviceUser(DeviceUser deviceUser) throws CantListIdentitiesException {


        // Setup method.
        List<ChatIdentity> list = new ArrayList<ChatIdentity>(); // Chat list.
        DatabaseTable table; // Chat User table.

        // Get Chat identities list.
        try {

            /**
             * 1) Get the table.
             */
            table = this.database.getTable(ChatIdentityDatabaseConstants.CHAT_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get Chat identity list, table not found.", "Chat Identity", "Cant get Chat identity list, table not found.");
            }


            // 2) Find the Chat Identity.

            table.addStringFilter(ChatIdentityDatabaseConstants.CHAT_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME, deviceUser.getPublicKey(), DatabaseFilterType.EQUAL);
            table.loadToMemory();

            // 3) Get Chat Identity.

            for (DatabaseTableRecord record : table.getRecords()) {

                // Add records to list.
                list.add(new ChatIdentityImpl(record.getStringValue(ChatIdentityDatabaseConstants.CHAT_ALIAS_COLUMN_NAME),
                        record.getStringValue(ChatIdentityDatabaseConstants.CHAT_PUBLIC_KEY_COLUMN_NAME),
                        getChatIdentityPrivateKey(record.getStringValue(ChatIdentityDatabaseConstants.CHAT_PUBLIC_KEY_COLUMN_NAME)),
                        getChatProfileImagePrivateKey(record.getStringValue(ChatIdentityDatabaseConstants.CHAT_PUBLIC_KEY_COLUMN_NAME)),
                        pluginFileSystem,
                        pluginId,
                        record.getStringValue(ChatIdentityDatabaseConstants.CHAT_COUNTRY_COLUMN_NAME),
                        record.getStringValue(ChatIdentityDatabaseConstants.CHAT_STATE_COLUMN_NAME),
                        record.getStringValue(ChatIdentityDatabaseConstants.CHAT_CITY_COLUMN_NAME),
                        record.getStringValue(ChatIdentityDatabaseConstants.CHAT_CONNECTION_STATE_COLUMN_NAME)));
            }
        } catch (CantLoadTableToMemoryException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            throw new CantListIdentitiesException(e.getMessage(), e, "Chat Identity", "Cant load " + ChatIdentityDatabaseConstants.CHAT_TABLE_NAME + " table in memory.");
        } catch (CantGetPrivateKeyException e) {
            // Failure unknown.
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            throw new CantListIdentitiesException(e.getMessage(), e, "Chat Identity", "Can't get private key.");

        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListIdentitiesException(e.getMessage(), FermatException.wrapException(e), "Chat Identity", "Chat identity list, unknown failure.");
        }

        // Return the list values.
        return list;
    }

    private boolean aliasExists(String alias) throws CantCreateNewDeveloperException {


        DatabaseTable table;
        /**
         * Get developers identities list.
         * I select records on table
         */

        try {
            table = getDatabaseTable(ChatIdentityDatabaseConstants.CHAT_TABLE_NAME);//this.database.getTable(ChatIdentityDatabaseConstants.CHAT_TABLE_NAME);

            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check if alias exists", "Chat Identity", "");
            }

            table.addStringFilter(ChatIdentityDatabaseConstants.CHAT_ALIAS_COLUMN_NAME, alias, DatabaseFilterType.EQUAL);
            table.loadToMemory();

            return table.getRecords().size() > 0;


        } catch (CantLoadTableToMemoryException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            throw new CantCreateNewDeveloperException(e.getMessage(), e, "Chat  Identity", "Cant load " + ChatIdentityDatabaseConstants.CHAT_TABLE_NAME + " table in memory.");

        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateNewDeveloperException(e.getMessage(), FermatException.wrapException(e), "Chat Identity", "unknown failure.");
        }
    }

    private void persistNewUserPrivateKeysFile(String publicKey, String privateKey) throws CantPersistPrivateKeyException {
        try {
            PluginTextFile file = this.pluginFileSystem.createTextFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    CHAT_PRIVATE_KEYS_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.setContent(privateKey);

            file.persistToMedia();
        } catch (CantPersistFileException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
            throw new CantPersistPrivateKeyException("CAN'T PERSIST PRIVATE KEY ", e, "Error persist file.", null);
        } catch (CantCreateFileException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
            throw new CantPersistPrivateKeyException("CAN'T PERSIST PRIVATE KEY ", e, "Error creating file.", null);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantPersistPrivateKeyException("CAN'T PERSIST PRIVATE KEY ", FermatException.wrapException(e), "", "");
        }
    }

    private void persistNewUserProfileImage(String publicKey, byte[] profileImage) throws CantPersistProfileImageException {
        try {
            PluginBinaryFile file = this.pluginFileSystem.createBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    CHAT_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.setContent(profileImage);

            file.persistToMedia();
        } catch (CantPersistFileException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
            throw new CantPersistProfileImageException("CAN'T PERSIST PROFILE IMAGE ", e, "Error persist file.", null);
        } catch (CantCreateFileException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
            throw new CantPersistProfileImageException("CAN'T PERSIST PROFILE IMAGE ", e, "Error creating file.", null);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
            throw new CantPersistProfileImageException("CAN'T PERSIST PROFILE IMAGE ", FermatException.wrapException(e), "", "");
        }
    }

    public byte[] getChatProfileImagePrivateKey(String publicKey) throws CantGetProfileImageException {
        byte[] profileImage;
        try {
            PluginBinaryFile file = this.pluginFileSystem.getBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    CHAT_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.loadFromMedia();

            profileImage = file.getContent();

        } catch (CantLoadFileException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            throw new CantGetProfileImageException("CAN'T GET IMAGE PROFILE ", e, "Error loaded file.", null);
        } catch (FileNotFoundException | CantCreateFileException e) {
            profileImage = new byte[0];
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            // TODO: Revisar este manejo de excepcion
            // throw new CantGetIntraWalletUserIdentityProfileImageException("CAN'T GET IMAGE PROFILE ", e, "Error getting developer identity private keys file.", null);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetProfileImageException("CAN'T GET IMAGE PROFILE ", FermatException.wrapException(e), "", "");
        }

        return profileImage;
    }

    public String getChatIdentityPrivateKey(String publicKey) throws CantGetPrivateKeyException {
        String privateKey = "";
        try {
            PluginTextFile file = this.pluginFileSystem.getTextFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    CHAT_PRIVATE_KEYS_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );


            file.loadFromMedia();

            privateKey = file.getContent();

        } catch (CantLoadFileException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            throw new CantGetPrivateKeyException("CAN'T GET PRIVATE KEY ", e, "Error loaded file.", null);
        } catch (FileNotFoundException | CantCreateFileException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            throw new CantGetPrivateKeyException("CAN'T GET PRIVATE KEY ", e, "Error getting developer identity private keys file.", null);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetPrivateKeyException("CAN'T GET PRIVATE KEY ", FermatException.wrapException(e), "", "");
        }

        return privateKey;
    }
}
