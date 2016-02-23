
package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database.communications;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;

import java.util.UUID;

/**
 *  The Class  <code>com.bitdubai.fermat_cht_plugin.layer.network_service.network_service_chat.developer.bitdubai.version_1.database.Network Service ChatNetworkServiceDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 *
 * Created by Gabriel Araujo - (gabe_512@hotmail.com) on 06/01/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class CommunicationChatNetworkServiceDatabaseFactory implements DealsWithPluginDatabaseSystem {

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Constructor with parameters to instantiate class
     * .
     *
     * @param pluginDatabaseSystem DealsWithPluginDatabaseSystem
     */
    public CommunicationChatNetworkServiceDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * Create the database
     *
     * @param ownerId      the owner id
     * @param databaseName the database name
     * @return Database
     * @throws CantCreateDatabaseException
     */
    public Database createDatabase(UUID ownerId, String databaseName) throws CantCreateDatabaseException {
        Database database;

        /**
         * I will create the database where I am going to store the information of this wallet.
         */
        try {
            database = this.pluginDatabaseSystem.createDatabase(ownerId, databaseName);
        } catch (CantCreateDatabaseException cantCreateDatabaseException) {
            /**
             * I can not handle this situation.
             */
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "Exception not handled by the plugin, There is a problem and i cannot create the database.");
        }

        /**
         * Next, I will add the needed tables.
         */
        try {
            DatabaseTableFactory table;
            DatabaseFactory databaseFactory = database.getDatabaseFactory();
            /**
             * Create incoming messages table.
             */
            table = databaseFactory.newTableFactory(ownerId, CommunicationChatNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_NAME);

            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_MESSAGES_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_MESSAGES_SENDER_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_MESSAGES_RECEIVER_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TEXT_CONTENT_COLUMN_NAME, DatabaseDataType.STRING, 4000, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_MESSAGES_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);

            table.addIndex(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_MESSAGES_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            } catch(Exception e){
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, e, "", e.getMessage());
            }
            /**
             * Create outgoing messages table.
             */
            table = databaseFactory.newTableFactory(ownerId, CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TABLE_NAME);

            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_SENDER_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_RECEIVER_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TEXT_CONTENT_COLUMN_NAME, DatabaseDataType.STRING, 4000, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);

            table.addIndex(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }
            catch(Exception e){
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, e, "", e.getMessage());
            }

            /**
             * Create Chat table.
             */
            table = databaseFactory.newTableFactory(ownerId, CommunicationChatNetworkServiceDatabaseConstants.INCOMING_CHAT_TABLE_NOTIFICATION_TABLE_NAME);

            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_TRANSACTION_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_TRANSACTION_HASH_COLUMN_NAME, DatabaseDataType.STRING,100 , Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_IDCHAT_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_IDOBJECTO_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_LOCALACTORTYPE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_LOCALACTORPUBKEY_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_REMOTEACTORTYPE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_REMOTEACTORPUBKEY_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_CHATNAME_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_CHATSTATUS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_MESSAGE_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_DATE_COLUMN_NAME, DatabaseDataType.STRING, 200, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_IDMENSAJE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_MESSAGE_COLUMN_NAME, DatabaseDataType.STRING, 4000, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_DISTRIBUTIONSTATUS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_PROCCES_STATUS_COLUMN_NAME,DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_PROTOCOL_STATE_COLUMN_NAME,DatabaseDataType.STRING,100,Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_SENTDATE_COLUMN_NAME,DatabaseDataType.STRING, 200, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_READ_MARK_COLUMN_NAME,DatabaseDataType.STRING, 6, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_RESPONSE_TO_NOTIFICATION_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);

            table.addIndex(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_CHAT_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
              //  System.out.println("ChatNetworkServicePluginRoot - table:" + table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }catch(Exception e){
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, e, "", e.getMessage());
            }

            table = databaseFactory.newTableFactory(ownerId, CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_TABLE_NOTIFICATION_TABLE_NAME);

            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_CHAT_TRANSACTION_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_CHAT_TRANSACTION_HASH_COLUMN_NAME, DatabaseDataType.STRING,100 , Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_CHAT_IDCHAT_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_CHAT_IDOBJECTO_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_CHAT_LOCALACTORTYPE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_CHAT_LOCALACTORPUBKEY_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_CHAT_REMOTEACTORTYPE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_CHAT_REMOTEACTORPUBKEY_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_CHAT_CHATNAME_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_CHAT_CHATSTATUS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_CHAT_MESSAGE_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_CHAT_DATE_COLUMN_NAME, DatabaseDataType.STRING, 200, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_CHAT_IDMENSAJE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_CHAT_MESSAGE_COLUMN_NAME, DatabaseDataType.STRING, 4000, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_CHAT_DISTRIBUTIONSTATUS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_CHAT_PROCCES_STATUS_COLUMN_NAME,DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_CHAT_PROTOCOL_STATE_COLUMN_NAME,DatabaseDataType.STRING,100,Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_CHAT_SENTDATE_COLUMN_NAME,DatabaseDataType.STRING, 200, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_CHAT_READ_MARK_COLUMN_NAME,DatabaseDataType.STRING, 6, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_CHAT_SENT_COUNT_COLUMN_NAME, DatabaseDataType.INTEGER, 6, Boolean.FALSE);
            table.addColumn(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_CHAT_RESPONSE_TO_NOTIFICATION_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);

            table.addIndex(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
                //  System.out.println("ChatNetworkServicePluginRoot - table:" + table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }catch(Exception e){
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, e, "", e.getMessage());
            }
        } catch (InvalidOwnerIdException invalidOwnerId) {
            /**
             * This shouldn't happen here because I was the one who gave the owner id to the database file system,
             * but anyway, if this happens, I can not continue.
             */
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, invalidOwnerId, "", "There is a problem with the ownerId of the database.");
        }
        return database;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }
}