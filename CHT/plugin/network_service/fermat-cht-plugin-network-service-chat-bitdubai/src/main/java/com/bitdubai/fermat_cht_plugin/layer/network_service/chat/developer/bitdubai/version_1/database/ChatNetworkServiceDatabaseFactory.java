package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.UUID;

/**
 *  The Class  <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.ChatNetworkServiceDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 *
 * Created by natalia on 18/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ChatNetworkServiceDatabaseFactory {
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private ErrorManager errorManager;
    public ChatNetworkServiceDatabaseFactory(final PluginDatabaseSystem pluginDatabaseSystem, ErrorManager errorManager) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.errorManager = errorManager;
    }
    private void reportUnexpectedError(final Exception e) {
        errorManager.reportUnexpectedPluginException(Plugins.CHAT_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
    }

    public Database createDatabase(UUID ownerId     ,
                                      String databaseName) throws CantCreateDatabaseException {

        try {

            Database database = this.pluginDatabaseSystem.createDatabase(
                    ownerId     ,
                    databaseName
            );

            DatabaseTableFactory table;
            DatabaseFactory databaseFactory = database.getDatabaseFactory();
            /**
             * Create Chat table.
             */
            table = databaseFactory.newTableFactory(ownerId, ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_TABLE);

            table.addColumn(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_ID_COLUMN_NAME, DatabaseDataType.STRING, 150, Boolean.TRUE);
            table.addColumn(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_RESPONSE_TO_COLUMN_NAME, DatabaseDataType.STRING,150 , Boolean.FALSE);
            table.addColumn(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_IDCHAT_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_IDOBJECTO_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_LOCALACTORTYPE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_LOCALACTORPUBKEY_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_REMOTEACTORTYPE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_REMOTEACTORPUBKEY_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_CHATNAME_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_CHATSTATUS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_MESSAGE_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_DATE_COLUMN_NAME, DatabaseDataType.STRING, 200, Boolean.FALSE);
            table.addColumn(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_IDMENSAJE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_MESSAGE_COLUMN_NAME, DatabaseDataType.STRING, 4000, Boolean.FALSE);
            table.addColumn(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_DISTRIBUTIONSTATUS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_PROCCES_STATUS_COLUMN_NAME,DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_PROTOCOL_STATE_COLUMN_NAME,DatabaseDataType.STRING,100,Boolean.FALSE);
            table.addColumn(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_SENTDATE_COLUMN_NAME,DatabaseDataType.STRING, 200, Boolean.FALSE);
            table.addColumn(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_READ_MARK_COLUMN_NAME, DatabaseDataType.STRING, 6, Boolean.FALSE);
            table.addColumn(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_SENT_COUNT_COLUMN_NAME, DatabaseDataType.INTEGER, 6, Boolean.FALSE);
            table.addColumn(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_XML_MSG_REPRESENTATION, DatabaseDataType.STRING, 4000, Boolean.FALSE);

            table.addIndex(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
                //  System.out.println("ChatNetworkServicePluginRoot - table:" + table);
            } catch (CantCreateTableException cantCreateTableException) {
                CantCreateDatabaseException cantCreateDatabaseException = new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
                reportUnexpectedError(cantCreateDatabaseException);
                throw cantCreateDatabaseException;
            }catch(Exception e){
                CantCreateDatabaseException cantCreateDatabaseException = new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, e, "", e.getMessage());
                reportUnexpectedError(cantCreateDatabaseException);
                throw cantCreateDatabaseException;
            }


            return database;
        } catch (InvalidOwnerIdException invalidOwnerId) {
            /**
             * This shouldn't happen here because I was the one who gave the owner id to the database file system,
             * but anyway, if this happens, I can not continue.
             */
            CantCreateDatabaseException cantCreateDatabaseException = new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, invalidOwnerId, "", "There is a problem with the ownerId of the database.");
            reportUnexpectedError(cantCreateDatabaseException);
            throw cantCreateDatabaseException;
        }
    }
}

