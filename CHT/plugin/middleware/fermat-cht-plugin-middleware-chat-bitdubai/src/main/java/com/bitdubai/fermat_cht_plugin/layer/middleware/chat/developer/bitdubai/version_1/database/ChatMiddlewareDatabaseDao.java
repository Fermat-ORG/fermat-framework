package com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
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
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_cht_api.all_definition.enums.ChatStatus;
import com.bitdubai.fermat_cht_api.all_definition.enums.ContactStatus;
import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
import com.bitdubai.fermat_cht_api.all_definition.enums.TypeChat;
import com.bitdubai.fermat_cht_api.all_definition.enums.TypeMessage;
import com.bitdubai.fermat_cht_api.all_definition.events.enums.EventStatus;
import com.bitdubai.fermat_cht_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteContactConnectionException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteGroupMemberException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetChatUserIdentityException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetContactException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetContactListException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantNewEmptyChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantNewEmptyMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveContactException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveEventException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveGroupMemberException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Chat;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.ChatUserIdentity;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Contact;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.ContactConnection;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.GroupMember;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Message;
import com.bitdubai.fermat_cht_api.layer.middleware.utils.ChatImpl;
import com.bitdubai.fermat_cht_api.layer.middleware.utils.ChatUserIdentityImpl;
import com.bitdubai.fermat_cht_api.layer.middleware.utils.ContactConnectionImpl;
import com.bitdubai.fermat_cht_api.layer.middleware.utils.ContactImpl;
import com.bitdubai.fermat_cht_api.layer.middleware.utils.EventRecord;
import com.bitdubai.fermat_cht_api.layer.middleware.utils.MessageImpl;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.exceptions.CantGetPendingEventListException;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by miguel payarez miguel_payarez@hotmail.com on 06/01/16.
 * Modified by Franklin Marcano 09-01-2016
 * Updated by Jose Cardozo josejcb (josejcb89@gmail.com) on 16/03/16.
 */
public class ChatMiddlewareDatabaseDao {

    //TODO: Documentar
    private Database database;
    private PluginDatabaseSystem pluginDatabaseSystem;
    private UUID pluginId;
    private ErrorManager errorManager;
    private PluginFileSystem pluginFileSystem;
    private static String CHAT_USER_IDENTITY_PROFILE_IMAGE_FILE_NAME = "chatUserIdentityProfileImage";
    private static String CONTACT_IMAGE_FILE_NAME = "contactImage";
    private static String CONTACT_CONNECTION_IMAGE_FILE_NAME = "contactImage";

    /**
     * Constructor
     */
    public ChatMiddlewareDatabaseDao(PluginDatabaseSystem pluginDatabaseSystem,
                                     UUID pluginId,
                                     Database database,
                                     ErrorManager errorManager,
                                     PluginFileSystem pluginFileSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId;
        this.database             = database;
        this.errorManager         = errorManager;
        this.pluginFileSystem     = pluginFileSystem;
    }


    public List<ContactConnection> getContactConnections(DatabaseTableFilter filter) throws CantGetContactException, DatabaseOperationException
    {
        //if filter is null all records
        Database database = null;
        try {
            database = openDatabase();
            List<ContactConnection> contactConnections = new ArrayList<>();
            // I will add the contact information from the database
            List<DatabaseTableRecord> records=getContactConnectionData(filter);
            if(records==null|| records.isEmpty()){
                return contactConnections;
            }
            for (DatabaseTableRecord record : records) {
                final ContactConnection contactConnection = getContactConnectionTransaction(record);

                contactConnection.setProfileImage(getContactImage(contactConnection.getRemoteActorPublicKey()));

                contactConnections.add(contactConnection);
            }

            database.closeDatabase();

            return contactConnections;
        }
        catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new DatabaseOperationException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "error trying to get Contact from the database with filter: " + filter.toString(),
                    null);
        }
    }

    /**
     * This method returns the contact id by local public key.
     * @param localPublicKey
     * @return
     * @throws CantGetContactException
     * @throws DatabaseOperationException
     */
    //Revisar este metodo ya que de aca no  se van a sacar los actores, se sacaran de los actor connections
    public Contact getContactByLocalPublicKey(String localPublicKey) throws CantGetContactException, DatabaseOperationException
    {
        Database database = null;
        try {
            database = openDatabase();
            List<Contact> contacts = new ArrayList<>();
            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.CONTACTS_TABLE_NAME);
            DatabaseTableFilter filter = table.getEmptyTableFilter();
            filter.setType(DatabaseFilterType.EQUAL);
            filter.setValue(localPublicKey.toString());
            filter.setColumn(ChatMiddlewareDatabaseConstants.CONTACTS_REMOTE_ACTOR_PUB_KEY_COLUMN_NAME);
            // I will add the contact information from the database
            for (DatabaseTableRecord record : getContactData(filter)) {
                final Contact contact = getContactTransaction(record);

                contacts.add(contact);
            }

            database.closeDatabase();

            if(contacts.isEmpty()){
                return null;
            }

            return contacts.get(0);
        }
        catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new DatabaseOperationException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "error trying to get Contact from the database with filter: " + localPublicKey,
                    null);
        }
    }

    public void saveGroupMember(GroupMember groupMember) throws CantSaveGroupMemberException, DatabaseOperationException {
        try
        {
            database = openDatabase();
            DatabaseTransaction transaction = database.newTransaction();

            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.GROUP_MEMBER_TABLE_NAME);
            DatabaseTableRecord record = getGroupMemberRecord(groupMember);
            DatabaseTableFilter filter = table.getEmptyTableFilter();
            filter.setType(DatabaseFilterType.EQUAL);
            filter.setValue(groupMember.getGroupId().toString());
            filter.setColumn(ChatMiddlewareDatabaseConstants.GROUP_MEMBER_FIRST_KEY_COLUMN);

            if (isNewRecord(table, filter))
                transaction.addRecordToInsert(table, record);
            else {
                table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
                transaction.addRecordToUpdate(table, record);
            }

            //I execute the transaction and persist the database side of the Contact.
            database.executeTransaction(transaction);
            database.closeDatabase();

        }catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new DatabaseOperationException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "Error trying to save the Contact Transaction in the database.",
                    null);
        }
    }

    public void deleteGroupMember(GroupMember groupMember) throws
            CantDeleteGroupMemberException,
            DatabaseOperationException
    {
        try
        {
            database = openDatabase();
            DatabaseTransaction transaction = database.newTransaction();

            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.GROUP_MEMBER_TABLE_NAME);
            DatabaseTableRecord record = getGroupMemberRecord(groupMember);

            table.deleteRecord(record);

            //I execute the transaction and persist the database side of the Contact.
            database.executeTransaction(transaction);
            database.closeDatabase();

        }catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new DatabaseOperationException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "Error trying to delete the Group Member Transaction in the database.",
                    null);
        }
    }

    public List<GroupMember> getGroupsMemberByGroupId(UUID groupId){
        return null;
    }

    public void saveContact(Contact contact) throws
            CantSaveContactException,
            DatabaseOperationException {
        try
        {
            database = openDatabase();
            DatabaseTransaction transaction = database.newTransaction();

            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.CONTACTS_TABLE_NAME);
            DatabaseTableRecord record = getContactRecord(contact);
            DatabaseTableFilter filter = table.getEmptyTableFilter();
            filter.setType(DatabaseFilterType.EQUAL);
            filter.setValue(contact.getRemoteActorPublicKey().toString());
            filter.setColumn(ChatMiddlewareDatabaseConstants.CONTACTS_REMOTE_ACTOR_PUB_KEY_COLUMN_NAME);

            if (isNewRecord(table, filter))
                transaction.addRecordToInsert(table, record);
            else {
                table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
                transaction.addRecordToUpdate(table, record);
            }

            persistNewContactImage(contact.getRemoteActorPublicKey(), contact.getProfileImage());

            //I execute the transaction and persist the database side of the Contact.
            database.executeTransaction(transaction);
            database.closeDatabase();

        }catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new DatabaseOperationException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "Error trying to save the Contact Transaction in the database.",
                    null);
        }
    }


    public void deleteContactConnection(ContactConnection contactConnection) throws
            CantDeleteContactConnectionException,
            DatabaseOperationException
    {
        try
        {
            database = openDatabase();
            DatabaseTransaction transaction = database.newTransaction();

            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.CONTACTS_CONNECTION_TABLE_NAME);
            DatabaseTableRecord record = getContactConnectionRecord(contactConnection);

            table.deleteRecord(record);

            //I execute the transaction and persist the database side of the Contact.
            database.executeTransaction(transaction);
            database.closeDatabase();

        }catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new DatabaseOperationException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "Error trying to delete the Contact Transaction in the database.",
                    null);
        }
    }

    /**
     * This method returns all the chats recorded in database.
     * @return
     * @throws DatabaseOperationException
     * @throws CantGetChatException
     */
    public List<Chat> getChatList() throws
            DatabaseOperationException,
            CantGetChatException {
        /**
         * I'll pass null as argument to the next method to get all the chat list.
         */
        return getChats(null);
    }

    public List<Chat> getChats(DatabaseTableFilter filter) throws
            CantGetChatException,
            DatabaseOperationException
    {
        //if filter is null all records
        Database database = null;
        try {
            database = openDatabase();
            List<Chat> chats = new ArrayList<>();
            // I will add the contact information from the database
            List<DatabaseTableRecord> records=getChatData(filter);
            if(records==null|| records.isEmpty()){
                return chats;
            }
            for (DatabaseTableRecord record : getChatData(filter)) {
                final Chat chat = getChatTransaction(record);

                chats.add(chat);
            }

            database.closeDatabase();

            return chats;
        }
        catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new DatabaseOperationException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "error trying to get Contact from the database with filter: " + filter.toString(),
                    null);
        }
    }

    public Chat getChatByChatId(UUID chatId) throws
            CantGetChatException,
            DatabaseOperationException
    {
        Database database = null;
        try {
            database = openDatabase();
            List<Chat> chats = new ArrayList<>();
            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.CHATS_TABLE_NAME);
            DatabaseTableFilter filter = table.getEmptyTableFilter();
            filter.setType(DatabaseFilterType.EQUAL);
            filter.setValue(chatId.toString());
            filter.setColumn(ChatMiddlewareDatabaseConstants.CHATS_FIRST_KEY_COLUMN);
            // I will add the contact information from the database
            for (DatabaseTableRecord record : getChatData(filter)) {
                final Chat chat = getChatTransaction(record);

                chats.add(chat);
            }

            database.closeDatabase();

            if(chats.isEmpty()){
                return null;
            }

            return chats.get(0);
        }
        catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new CantGetChatException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    e,
                    "error trying to get Chat from the database with filter: " + chatId.toString(),
                    null);
        }
    }

    public Chat getChatByRemotePublicKey(String publicKey) throws
            CantGetChatException,
            DatabaseOperationException
    {
        Database database = null;
        try {
            database = openDatabase();
            List<Chat> chats = new ArrayList<>();
            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.CHATS_TABLE_NAME);
            DatabaseTableFilter filter = table.getEmptyTableFilter();
            filter.setType(DatabaseFilterType.EQUAL);
            filter.setValue(publicKey);
            filter.setColumn(ChatMiddlewareDatabaseConstants.CHATS_REMOTE_ACTOR_PUB_KEY_COLUMN_NAME);
            // I will add the contact information from the database
            for (DatabaseTableRecord record : getChatData(filter)) {
                final Chat chat = getChatTransaction(record);

                chats.add(chat);
            }

            database.closeDatabase();

            if(chats.isEmpty()){
                return null;
            }

            return chats.get(0);
        }
        catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new CantGetChatException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    e,
                    "error trying to get Chat from the database with filter: " + publicKey,
                    null);
        }
    }

    public Chat newEmptyInstanceChat() throws CantNewEmptyChatException
    {
        try{
            ChatImpl chat = new ChatImpl();
            chat.setChatId(UUID.randomUUID());
            return chat;
        } catch (Exception e){
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new CantNewEmptyChatException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "Error trying to get new empty instance chat",
                    null);
        }
    }

    public void saveChat(Chat chat) throws CantSaveChatException, DatabaseOperationException
    {
        try
        {
            database = openDatabase();
            DatabaseTransaction transaction = database.newTransaction();

            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.CHATS_TABLE_NAME);
            DatabaseTableRecord record = getChatRecord(chat);
            DatabaseTableFilter filter = table.getEmptyTableFilter();
            filter.setType(DatabaseFilterType.EQUAL);
            filter.setValue(chat.getChatId().toString());
            filter.setColumn(ChatMiddlewareDatabaseConstants.CHATS_FIRST_KEY_COLUMN);

            if (isNewRecord(table, filter))
                transaction.addRecordToInsert(table, record);
            else {
                table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
                transaction.addRecordToUpdate(table, record);
            }

            if (chat.getGroupMembersAssociated() != null)
            {
                for (GroupMember groupMember : chat.getGroupMembersAssociated()) {
                    record = getGroupMemberRecord(groupMember);
                    filter.setType(DatabaseFilterType.EQUAL);
                    filter.setValue(groupMember.getGroupMemberId().toString());
                    filter.setColumn(ChatMiddlewareDatabaseConstants.GROUP_MEMBER_FIRST_KEY_COLUMN);

                    if (isNewRecord(table, filter))
                        transaction.addRecordToInsert(table, record);
                    else {
                        table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
                        transaction.addRecordToUpdate(table, record);
                    }
                }
            }

            if (chat.getMessagesAsociated() != null)
            {
                for (Message message : chat.getMessagesAsociated())
                {
                    record = getMessageRecord(message);
                    filter.setType(DatabaseFilterType.EQUAL);
                    filter.setValue(message.getMessageId().toString());
                    filter.setColumn(ChatMiddlewareDatabaseConstants.MESSAGE_FIRST_KEY_COLUMN);

                    if (isNewRecord(table, filter))
                        transaction.addRecordToInsert(table, record);
                    else {
                        table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
                        transaction.addRecordToUpdate(table, record);
                    }
                }
            }

            //I execute the transaction and persist the database side of the chat.
            database.executeTransaction(transaction);
            database.closeDatabase();

        }catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new CantSaveChatException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "Error trying to save the Chat Transaction in the database.",
                    null);
        }
    }

    public void deleteChat(Chat chat) throws CantDeleteChatException, DatabaseOperationException
    {
        try
        {
            database = openDatabase();
            DatabaseTransaction transaction = database.newTransaction();

            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.CHATS_TABLE_NAME);
            DatabaseTableRecord record = getChatRecord(chat);

            table.deleteRecord(record);

            //I execute the transaction and persist the database side of the chat.
            database.executeTransaction(transaction);
            database.closeDatabase();

        }catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new CantDeleteChatException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "Error trying to delete the Chat Transaction in the database.",
                    null);
        }
    }


    public void deleteChats() throws CantDeleteChatException, DatabaseOperationException
    {
        try
        {
            database = openDatabase();
            DatabaseTransaction transaction = database.newTransaction();

            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.CHATS_TABLE_NAME);
            DatabaseTableFilter filter = table.getEmptyTableFilter();
            List<DatabaseTableRecord> records=getChatData(filter);
            if(records!=null && !records.isEmpty()){
                for (DatabaseTableRecord record : records) {
                    table.deleteRecord(record);
                }
            }
            //I execute the transaction and persist the database side of the chat.
            database.executeTransaction(transaction);
            database.closeDatabase();

        }catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new CantDeleteChatException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "Error trying to delete the Chat Transaction in the database.",
                    null);
        }
    }

    public void deleteMessagesByChatId(UUID chatId) throws CantDeleteMessageException, DatabaseOperationException
    {
        try
        {
            database = openDatabase();
            DatabaseTransaction transaction = database.newTransaction();

            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.MESSAGE_TABLE_NAME);
            DatabaseTableFilter filter = table.getEmptyTableFilter();
            filter.setType(DatabaseFilterType.EQUAL);
            filter.setValue(chatId.toString());
            filter.setColumn(ChatMiddlewareDatabaseConstants.MESSAGE_ID_CHAT_COLUMN_NAME);
            List<DatabaseTableRecord> records=getMessageData(filter);
            if(records!=null && !records.isEmpty()){
                for (DatabaseTableRecord record : records) {
                    table.deleteRecord(record);
                }
            }
            //I execute the transaction and persist the database side of the chat.
            database.executeTransaction(transaction);
            database.closeDatabase();

        }catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new CantDeleteMessageException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "Error trying to delete the Chat Transaction in the database.",
                    null);
        }
    }

    /**
     * This method returns a message created list.
     * The messages in CREATED status are saved in database, but, are not sent through the Network
     * Service.
     * @return
     * @throws DatabaseOperationException
     * @throws CantGetMessageException
     */
    public List<Message> getCreatedMessages() throws
            DatabaseOperationException,
            CantGetMessageException {
        try{
            DatabaseTable databaseTable=getDatabaseTable(
                    ChatMiddlewareDatabaseConstants.MESSAGE_TABLE_NAME);
            DatabaseTableFilter databaseTableFilter=databaseTable.getEmptyTableFilter();
            databaseTableFilter.setColumn(ChatMiddlewareDatabaseConstants.MESSAGE_STATUS_COLUMN_NAME);
            databaseTableFilter.setType(DatabaseFilterType.EQUAL);
            databaseTableFilter.setValue(MessageStatus.CREATED.getCode());
            return getMessages(databaseTableFilter);
        } catch (Exception e){
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new CantGetMessageException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "Error trying to delete the Chat Transaction in the database.",
                    null);
        }

    }

    /**
     * This method returns the full messages list.
     * @return
     * @throws DatabaseOperationException
     * @throws CantGetMessageException
     */
    public List<Message> getMessages() throws
            DatabaseOperationException,
            CantGetMessageException {
        /**
         * I'll pass null as argument to the next method to get all the chat list.
         */
        return getMessages(null);
    }

    public List<Message> getMessages(DatabaseTableFilter filter) throws CantGetMessageException, DatabaseOperationException
    {
        //if filter is null all records
        Database database = null;
        try {
            database = openDatabase();
            List<Message> messages = new ArrayList<>();
            // I will add the message information from the database
            List<DatabaseTableRecord> records=getMessageData(filter);
            if(records==null|| records.isEmpty()){
                return messages;
            }
            for (DatabaseTableRecord record : records) {
                final Message message = getMessageTransaction(record);

                messages.add(message);
            }

            database.closeDatabase();

            return messages;
        }
        catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new DatabaseOperationException(
                    DatabaseOperationException.DEFAULT_MESSAGE, 
                    e, 
                    e.getMessage(), 
                    null);
        }
    }

    public List<Message> getMessagesByChatId(UUID chatId) throws CantGetMessageException, DatabaseOperationException
    {
        Database database = null;
        try {
            database = openDatabase();
            List<Message> messages = new ArrayList<>();
            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.MESSAGE_TABLE_NAME);
            DatabaseTableFilter filter = table.getEmptyTableFilter();
            filter.setType(DatabaseFilterType.EQUAL);
            filter.setValue(chatId.toString());
            filter.setColumn(ChatMiddlewareDatabaseConstants.MESSAGE_ID_CHAT_COLUMN_NAME);
            // I will add the message information from the database
            List<DatabaseTableRecord> records=getMessageData(filter);
            if(records==null|| records.isEmpty()){
                return null;
            }
            for (DatabaseTableRecord record : records) {
                final Message message = getMessageTransaction(record);

                messages.add(message);
            }

            database.closeDatabase();

            if(messages.isEmpty()){
                return null;
            }

            return messages;
        }
        catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new CantGetMessageException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "error trying to get Message from the database with filter: " + chatId.toString(),
                    null);
        }
    }

    public Message getMessageByChatId(UUID chatId) throws CantGetMessageException, DatabaseOperationException
    {
        Database database = null;
        try {
            database = openDatabase();
//            List<Message> messages = new ArrayList<>();
            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.MESSAGE_TABLE_NAME);
            DatabaseTableFilter filter = table.getEmptyTableFilter();
            filter.setType(DatabaseFilterType.EQUAL);
            filter.setValue(chatId.toString());
            filter.setColumn(ChatMiddlewareDatabaseConstants.MESSAGE_ID_CHAT_COLUMN_NAME);

            // I will add the message information from the database
                final Message message = getMessageTransaction(getMessageDataDesceding(filter).get(0));

            database.closeDatabase();

            if(message == null){
                return null;
            }

            System.out.println("12345 MESSAGE = "+message.getCount());
            System.out.println("12345 COUNT = " + message.getMessage());

            return message;
        }
        catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new DatabaseOperationException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "error trying to get Message from the database with filter: " + chatId.toString(),
                    null);
        }
    }

    public int getCountMessageByChatId(UUID chatId) throws CantGetMessageException, DatabaseOperationException
    {
        int countRecord = 0;
        Database database = null;
        try {
            database = openDatabase();
            List<Message> messages = new ArrayList<>();
            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.MESSAGE_TABLE_NAME);
            DatabaseTableFilter filter = table.getEmptyTableFilter();
            filter.setType(DatabaseFilterType.EQUAL);
            filter.setValue(chatId.toString());
            //filter.setValue(TypeMessage.INCOMMING.getCode());
            filter.setColumn(ChatMiddlewareDatabaseConstants.MESSAGE_ID_CHAT_COLUMN_NAME);
            //filter.setColumn(ChatMiddlewareDatabaseConstants.MESSAGE_TYPE_COLUMN_NAME);
            // I will add the message information from the database
            List<DatabaseTableRecord> records=getMessageData(filter);
            if(records==null|| records.isEmpty()){
                return countRecord;
            }
            for (DatabaseTableRecord record : records) {
                final Message message = getMessageTransaction(record);

                messages.add(message);
            }

            database.closeDatabase();

            for (Message message : messages)
            {
                if (message.getStatus().getCode() != MessageStatus.READ.getCode())
                {
                    if (message.getType().getCode() != TypeMessage.OUTGOING.getCode()){
                        countRecord++;
                    }
                }
            }

            if(messages.isEmpty()){
                return countRecord;
            }

            return countRecord;
        }
        catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new CantGetMessageException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "error trying to get Count Message from the database with filter: " + chatId.toString(),
                    null);
        }
    }

    public Message getMessageByMessageId(UUID messageId) throws CantGetMessageException, DatabaseOperationException
    {
        Database database = null;
        try {
            database = openDatabase();
            List<Message> messages = new ArrayList<>();
            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.MESSAGE_TABLE_NAME);
            DatabaseTableFilter filter = table.getEmptyTableFilter();
            filter.setType(DatabaseFilterType.EQUAL);
            filter.setValue(messageId.toString());
            filter.setColumn(ChatMiddlewareDatabaseConstants.MESSAGE_FIRST_KEY_COLUMN);
            // I will add the message information from the database
            for (DatabaseTableRecord record : getMessageData(filter)) {
                final Message message = getMessageTransaction(record);

                messages.add(message);
            }

            database.closeDatabase();

            if(messages.isEmpty()){
                return null;
            }

            return messages.get(0);
        }
        catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new DatabaseOperationException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "error trying to get Message from the database with filter: " + messageId.toString(),
                    null);
        }
    }

    public Message newEmptyInstanceMessage() throws CantNewEmptyMessageException
    {
        MessageImpl message = new MessageImpl();
        message.setMessageId(UUID.randomUUID());
        return message;
    }

    public void saveMessage(Message message) throws CantSaveMessageException, DatabaseOperationException
    {
        try
        {
            System.out.println("*** 12345 case 4:send msg in Dao layer" + new Timestamp(System.currentTimeMillis()));
            database = openDatabase();
            DatabaseTransaction transaction = database.newTransaction();

            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.MESSAGE_TABLE_NAME);
            DatabaseTableFilter filter = table.getEmptyTableFilter();
            DatabaseTableRecord record;
            filter.setType(DatabaseFilterType.EQUAL);
            filter.setValue(message.getMessageId().toString());
            filter.setColumn(ChatMiddlewareDatabaseConstants.MESSAGE_FIRST_KEY_COLUMN);

            if (isNewRecord(table, filter)) {
                message.setCount(getLastMessageCount() + 1);
                record = getMessageRecord(message);
                transaction.addRecordToInsert(table, record);
            }
            else {
                record = getMessageRecord(message);
                table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
                transaction.addRecordToUpdate(table, record);
            }

            //I execute the transaction and persist the database side of the chat.
            database.executeTransaction(transaction);
            database.closeDatabase();

        }catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new DatabaseOperationException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "Error trying to save the message Transaction in the database.",
                    null);
        }
    }

    public void deleteMessage(Message message) throws
            CantDeleteMessageException,
            DatabaseOperationException
    {
        try
        {
            database = openDatabase();
            DatabaseTransaction transaction = database.newTransaction();

            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.MESSAGE_TABLE_NAME);
            DatabaseTableRecord record = getMessageRecord(message);

            table.deleteRecord(record);

            //I execute the transaction and persist the database side of the chat.
            database.executeTransaction(transaction);
            database.closeDatabase();

        }catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new CantDeleteMessageException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "Error trying to delete the message Transaction in the database.",
                    null);
        }
    }

    //TODO:Eliminar metodo
    public List<ChatUserIdentity> getChatUserIdentities(DatabaseTableFilter filter) throws CantGetChatUserIdentityException, DatabaseOperationException
    {
        //if filter is null all records
        Database database = null;
        try {
            database = openDatabase();
            List<ChatUserIdentity> chatUserIdentities = new ArrayList<>();
            // I will add the message information from the database
            List<DatabaseTableRecord> records=getChatUserIdentityData(filter);
            if(records==null|| records.isEmpty()){
                return chatUserIdentities;
            }
            for (DatabaseTableRecord record : records) {
                final ChatUserIdentity chatUserIdentity = getChatUserIdentityTransaction(record);

                chatUserIdentity.setNewProfileImage(getChatUserIdentityProfileImage(chatUserIdentity.getPublicKey()));

                chatUserIdentities.add(chatUserIdentity);
            }

            database.closeDatabase();

            return chatUserIdentities;
        }
        catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new DatabaseOperationException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    e,
                    e.getMessage(),
                    null);
        }
    }


    private byte[] getChatUserIdentityProfileImage(String publicKey) throws FileNotFoundException
    {
        byte[] profileImage = new byte[0];

        PluginBinaryFile file = null;
        try {
            file = this.pluginFileSystem.getBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    CHAT_USER_IDENTITY_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );
            file.loadFromMedia();
            profileImage = file.getContent();
        } catch (FileNotFoundException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new FileNotFoundException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "Error Create File not exist.",
                    null);
        } catch (CantCreateFileException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new FileNotFoundException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "Error Create File.",
                    null);
        }catch (Exception e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new FileNotFoundException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "Error general.",
                    null);
        }

        return profileImage;
    }

    private byte[] getContactImage(String publicKey) throws CantPersistFileException
    {
        byte[] profileImage = new byte[0];

        PluginBinaryFile file = null;
        try {
            file = this.pluginFileSystem.getBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    CONTACT_IMAGE_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );
            file.loadFromMedia();
            profileImage = file.getContent();
        } catch (FileNotFoundException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new CantPersistFileException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "Error Create File not exist.",
                    null);
        } catch (CantCreateFileException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new CantPersistFileException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "Error Create File.",
                    null);
        }catch (Exception e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new CantPersistFileException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "Error general.",
                    null);
        }

        return profileImage;
    }


    private void  persistNewChatUserIdentityProfileImage(String publicKey,byte[] profileImage) throws CantPersistFileException {

        PluginBinaryFile file = null;
        try {
            file = this.pluginFileSystem.createBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    CHAT_USER_IDENTITY_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.setContent(profileImage);

            file.persistToMedia();
        } catch (CantPersistFileException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new CantPersistFileException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "Error Persist File.",
                    null);
        } catch (CantCreateFileException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new CantPersistFileException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "Error Create File.",
                    null);
        }catch (Exception e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new CantPersistFileException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "Error general.",
                    null);
        }

    }

    private void  persistNewContactImage(String publicKey,byte[] profileImage) throws CantPersistFileException {

        PluginBinaryFile file = null;
        try {
            file = this.pluginFileSystem.createBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    CONTACT_IMAGE_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.setContent(profileImage);

            file.persistToMedia();
        } catch (CantPersistFileException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new CantPersistFileException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "Error Persist File.",
                    null);
        } catch (CantCreateFileException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new CantPersistFileException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "Error Create File.",
                    null);
        }catch (Exception e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new CantPersistFileException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "Error general.",
                    null);
        }

    }


    private Database openDatabase() throws CantOpenDatabaseException, CantCreateDatabaseException {
        try {
            database = pluginDatabaseSystem.openDatabase(this.pluginId, ChatMiddlewareDatabaseConstants.DATABASE_NAME);

        } catch (DatabaseNotFoundException e) {
            ChatMiddlewareDatabaseFactory chatMiddlewareDatabaseFactory = new ChatMiddlewareDatabaseFactory(pluginDatabaseSystem);
            database = chatMiddlewareDatabaseFactory.createDatabase(this.pluginId, ChatMiddlewareDatabaseConstants.DATABASE_NAME);
        }
        return database;
    }

    private DatabaseTable getDatabaseTable(String tableName) {
        return database.getTable(tableName);
    }

    private boolean isNewRecord(DatabaseTable table, DatabaseTableFilter filter) throws CantLoadTableToMemoryException {
        table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
        table.loadToMemory();
        return table.getRecords().isEmpty();
    }

    private DatabaseTableRecord getContactRecord(Contact contact) throws DatabaseOperationException
    {
        DatabaseTable databaseTable = getDatabaseTable(ChatMiddlewareDatabaseConstants.CONTACTS_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();

        record.setUUIDValue(ChatMiddlewareDatabaseConstants.CONTACTS_ID_CONTACT_COLUMN_NAME, contact.getContactId());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CONTACTS_ALIAS_COLUMN_NAME, contact.getAlias());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CONTACTS_REMOTE_NAME_COLUMN_NAME, contact.getRemoteName());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CONTACTS_REMOTE_ACTOR_TYPE_COLUMN_NAME, contact.getRemoteActorType().getCode());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CONTACTS_REMOTE_ACTOR_PUB_KEY_COLUMN_NAME, contact.getRemoteActorPublicKey());
        record.setLongValue(ChatMiddlewareDatabaseConstants.CONTACTS_CREATION_DATE_COLUMN_NAME, contact.getCreationDate());
        if(contact.getContactStatus()!=null){
            record.setStringValue(ChatMiddlewareDatabaseConstants.CONTACTS_CONTACT_STATUS_COLUMN_NAME, contact.getContactStatus().getCode());
        }

        return record;
    }

    private DatabaseTableRecord getGroupMemberRecord(GroupMember groupMember) throws DatabaseOperationException
    {
        DatabaseTable databaseTable = getDatabaseTable(ChatMiddlewareDatabaseConstants.GROUP_MEMBER_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();

        record.setUUIDValue(ChatMiddlewareDatabaseConstants.GROUP_MEMBER_ID_COLUMN_NAME, groupMember.getGroupId());
        record.setUUIDValue(ChatMiddlewareDatabaseConstants.GROUP_MEMBER_GROUP_ID_COLUMN_NAME, groupMember.getGroupId());
        record.setStringValue(ChatMiddlewareDatabaseConstants.GROUP_MEMBER_USER_REGISTERED_PUBLIC_KEY_COLUMN_NAME, groupMember.getActorPublicKey());
        record.setStringValue(ChatMiddlewareDatabaseConstants.GROUP_MEMBER_ALIAS_COLUMN_NAME, groupMember.getActorAlias());

        return record;
    }

    private DatabaseTableRecord getContactConnectionRecord(ContactConnection contactConnection) throws DatabaseOperationException
    {
        DatabaseTable databaseTable = getDatabaseTable(ChatMiddlewareDatabaseConstants.CONTACTS_CONNECTION_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();

        record.setUUIDValue(ChatMiddlewareDatabaseConstants.CONTACTS_CONNECTION_ID_CONTACT_COLUMN_NAME, contactConnection.getContactId());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CONTACTS_CONNECTION_ALIAS_COLUMN_NAME, contactConnection.getAlias());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CONTACTS_CONNECTION_REMOTE_NAME_COLUMN_NAME, contactConnection.getRemoteName());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CONTACTS_CONNECTION_REMOTE_ACTOR_TYPE_COLUMN_NAME, contactConnection.getRemoteActorType().getCode());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CONTACTS_CONNECTION_REMOTE_ACTOR_PUB_KEY_COLUMN_NAME, contactConnection.getRemoteActorPublicKey());
        record.setLongValue(ChatMiddlewareDatabaseConstants.CONTACTS_CONNECTION_CREATION_DATE_COLUMN_NAME, contactConnection.getCreationDate());
        if(contactConnection.getContactStatus()!=null){
            record.setStringValue(ChatMiddlewareDatabaseConstants.CONTACTS_CONNECTION_CONTACT_STATUS_COLUMN_NAME, contactConnection.getContactStatus().getCode());
        }

        return record;
    }

    private DatabaseTableRecord getChatUserIdentityRecord(ChatUserIdentity chatUserIdentity, String deviceUserPublicKey) throws DatabaseOperationException
    {
        DatabaseTable databaseTable = getDatabaseTable(ChatMiddlewareDatabaseConstants.IDENTITY_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();

        record.setStringValue(ChatMiddlewareDatabaseConstants.IDENTITY_PUBLIC_KEY_COLUMN_NAME, chatUserIdentity.getPublicKey());
        record.setStringValue(ChatMiddlewareDatabaseConstants.IDENTITY_ALIAS_COLUMN_NAME, chatUserIdentity.getAlias());
        record.setStringValue(ChatMiddlewareDatabaseConstants.IDENTITY_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME, deviceUserPublicKey);
        record.setStringValue(ChatMiddlewareDatabaseConstants.IDENTITY_ACTOR_TYPE_COLUMN_NAME, chatUserIdentity.getActorType().getCode());
        record.setStringValue(ChatMiddlewareDatabaseConstants.IDENTITY_PLATFORM_COMPONENT_TYPE_COLUMN_NAME, chatUserIdentity.getPlatformComponentType().getCode());

        return record;
    }

    private DatabaseTableRecord getChatRecord(Chat chat) throws DatabaseOperationException
    {
        DatabaseTable databaseTable = getDatabaseTable(ChatMiddlewareDatabaseConstants.CHATS_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();

        record.setUUIDValue(ChatMiddlewareDatabaseConstants.CHATS_ID_CHAT_COLUMN_NAME, chat.getChatId());
        record.setUUIDValue(ChatMiddlewareDatabaseConstants.CHATS_ID_OBJECT_COLUMN_NAME, chat.getObjectId());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CHATS_CHAT_NAME_COLUMN_NAME, chat.getChatName());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CHATS_LOCAL_ACTOR_TYPE_COLUMN_NAME, chat.getLocalActorType().getCode());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CHATS_LOCAL_ACTOR_PUB_KEY_COLUMN_NAME, chat.getLocalActorPublicKey());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CHATS_REMOTE_ACTOR_TYPE_COLUMN_NAME, chat.getRemoteActorType().getCode());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CHATS_REMOTE_ACTOR_PUB_KEY_COLUMN_NAME, chat.getRemoteActorPublicKey());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CHATS_STATUS_COLUMN_NAME, chat.getStatus().getCode());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CHATS_CREATION_DATE_COLUMN_NAME, chat.getDate().toString());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CHATS_LAST_MESSAGE_DATE_COLUMN_NAME, chat.getLastMessageDate().toString());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CHATS_CONTACT_ASSOCIATED_LIST, chat.getContactListString());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CHATS_TYPE_CHAT, chat.getTypeChat().getCode());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CHATS_SCHEDULED_DELIVERY, String.valueOf(chat.getScheduledDelivery()));

        return record;
    }

    private DatabaseTableRecord getMessageRecord(Message message) throws DatabaseOperationException
    {
        DatabaseTable databaseTable = getDatabaseTable(ChatMiddlewareDatabaseConstants.MESSAGE_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();

        record.setUUIDValue(ChatMiddlewareDatabaseConstants.MESSAGE_ID_MESSAGE_COLUMN_NAME, message.getMessageId());
        record.setUUIDValue(ChatMiddlewareDatabaseConstants.MESSAGE_ID_CHAT_COLUMN_NAME, message.getChatId());
        record.setStringValue(ChatMiddlewareDatabaseConstants.MESSAGE_TEXT_MESSAGE_COLUMN_NAME, message.getMessage());
        record.setStringValue(ChatMiddlewareDatabaseConstants.MESSAGE_STATUS_COLUMN_NAME, message.getStatus().getCode());
        record.setStringValue(ChatMiddlewareDatabaseConstants.MESSAGE_TYPE_COLUMN_NAME, message.getType().getCode());
        record.setStringValue(ChatMiddlewareDatabaseConstants.MESSAGE_MESSAGE_DATE_COLUMN_NAME, message.getMessageDate().toString());
        record.setUUIDValue(ChatMiddlewareDatabaseConstants.MESSAGE_CONTACT_ID, message.getContactId());
        record.setLongValue(ChatMiddlewareDatabaseConstants.MESSAGE_COUNT, message.getCount());

        return record;
    }

    private DatabaseTableRecord getCahtUserIdentityRecord(ChatUserIdentity chatUserIdentity) throws DatabaseOperationException
    {
        DatabaseTable databaseTable = getDatabaseTable(ChatMiddlewareDatabaseConstants.IDENTITY_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();

        record.setStringValue(ChatMiddlewareDatabaseConstants.IDENTITY_ALIAS_COLUMN_NAME, chatUserIdentity.getAlias());
        record.setStringValue(ChatMiddlewareDatabaseConstants.IDENTITY_PUBLIC_KEY_COLUMN_NAME, chatUserIdentity.getPublicKey());
        //record.setStringValue(ChatMiddlewareDatabaseConstants.IDENTITY_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME, chatUserIdentity.);
        record.setStringValue(ChatMiddlewareDatabaseConstants.IDENTITY_ACTOR_TYPE_COLUMN_NAME, chatUserIdentity.getActorType().getCode());

        return record;
    }

    private List<DatabaseTableRecord> getChatData(DatabaseTableFilter filter) throws CantLoadTableToMemoryException
    {
        DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.CHATS_TABLE_NAME);

        if (filter != null)
            table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());

        table.addFilterOrder(ChatMiddlewareDatabaseConstants.CHATS_LAST_MESSAGE_DATE_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

        table.loadToMemory();

        return table.getRecords();
    }

    private List<DatabaseTableRecord> getMessageData(DatabaseTableFilter filter) throws CantLoadTableToMemoryException
    {
        DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.MESSAGE_TABLE_NAME);

        if (filter != null)
            table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());

//        table.addFilterOrder(ChatMiddlewareDatabaseConstants.MESSAGE_ID_CHAT_COLUMN_NAME, DatabaseFilterOrder.ASCENDING);
        table.addFilterOrder(ChatMiddlewareDatabaseConstants.MESSAGE_COUNT, DatabaseFilterOrder.ASCENDING);

        table.loadToMemory();

        return table.getRecords();
    }

    private List<DatabaseTableRecord> getMessageDataDesceding(DatabaseTableFilter filter) throws CantLoadTableToMemoryException
    {
        DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.MESSAGE_TABLE_NAME);

        if (filter != null)
            table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());

        //table.addFilterOrder(ChatMiddlewareDatabaseConstants.MESSAGE_MESSAGE_DATE_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);
        //TODO: bring code from the other branch where this issue is fixed
        table.addFilterOrder(ChatMiddlewareDatabaseConstants.MESSAGE_COUNT, DatabaseFilterOrder.DESCENDING);

        table.loadToMemory();

        return table.getRecords();
    }

    private List<DatabaseTableRecord> getChatUserIdentityData(DatabaseTableFilter filter) throws CantLoadTableToMemoryException
    {
        DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.IDENTITY_TABLE_NAME);

        if (filter != null)
            table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());

        table.loadToMemory();

        return table.getRecords();
    }

    private List<DatabaseTableRecord> getContactData(DatabaseTableFilter filter) throws CantLoadTableToMemoryException
    {
        DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.CONTACTS_TABLE_NAME);

        if (filter != null)
            table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());

        table.loadToMemory();

        return table.getRecords();
    }


    private List<DatabaseTableRecord> getContactConnectionData(DatabaseTableFilter filter) throws CantLoadTableToMemoryException
    {
        DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.CONTACTS_CONNECTION_TABLE_NAME);

        if (filter != null)
            table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());

        table.loadToMemory();

        return table.getRecords();
    }

    private Chat getChatTransaction(final DatabaseTableRecord chatTransactionRecord) throws CantLoadTableToMemoryException, DatabaseOperationException, InvalidParameterException {
        ChatImpl chat = new ChatImpl();

        chat.setChatId(chatTransactionRecord.getUUIDValue(ChatMiddlewareDatabaseConstants.CHATS_ID_CHAT_COLUMN_NAME));
        chat.setObjectId(chatTransactionRecord.getUUIDValue(ChatMiddlewareDatabaseConstants.CHATS_ID_OBJECT_COLUMN_NAME));
        chat.setChatName(chatTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CHATS_CHAT_NAME_COLUMN_NAME));
        chat.setDate(Timestamp.valueOf(chatTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CHATS_CREATION_DATE_COLUMN_NAME)));
        chat.setLastMessageDate(Timestamp.valueOf(chatTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CHATS_LAST_MESSAGE_DATE_COLUMN_NAME)));
        chat.setRemoteActorPublicKey(chatTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CHATS_REMOTE_ACTOR_PUB_KEY_COLUMN_NAME));
        chat.setRemoteActorType(PlatformComponentType.getByCode(chatTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CHATS_REMOTE_ACTOR_TYPE_COLUMN_NAME)));
        chat.setLocalActorPublicKey(chatTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CHATS_LOCAL_ACTOR_PUB_KEY_COLUMN_NAME));
        chat.setLocalActorType(PlatformComponentType.getByCode(chatTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CHATS_LOCAL_ACTOR_TYPE_COLUMN_NAME)));
        chat.setStatus(ChatStatus.getByCode(chatTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CHATS_STATUS_COLUMN_NAME)));
        chat.setTypeChat(TypeChat.getByCode(chatTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CHATS_TYPE_CHAT)));
        chat.setScheduledDelivery(Boolean.valueOf(chatTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CHATS_SCHEDULED_DELIVERY)));

        try{
            chat.setContactAssociated(chatTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CHATS_CONTACT_ASSOCIATED_LIST));
        } catch (CantGetContactListException e) {
            //If the contactList String is invalid we need to get to stablest an empty list of contact
            List<Contact> contactList=new ArrayList<>();
            chat.setContactAssociated(contactList);
        }

        return chat;
    }

    private Message getMessageTransaction(final DatabaseTableRecord messageTransactionRecord) throws CantLoadTableToMemoryException, DatabaseOperationException, InvalidParameterException
    {
        MessageImpl message = new MessageImpl();

        message.setChatId(messageTransactionRecord.getUUIDValue(ChatMiddlewareDatabaseConstants.MESSAGE_ID_CHAT_COLUMN_NAME));
        message.setMessageId(messageTransactionRecord.getUUIDValue(ChatMiddlewareDatabaseConstants.MESSAGE_ID_MESSAGE_COLUMN_NAME));
        message.setMessage(messageTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.MESSAGE_TEXT_MESSAGE_COLUMN_NAME));
        message.setMessageDate(Timestamp.valueOf(messageTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.MESSAGE_MESSAGE_DATE_COLUMN_NAME)));
        message.setStatus(MessageStatus.getByCode(messageTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.MESSAGE_STATUS_COLUMN_NAME)));
        message.setType(TypeMessage.getByCode(messageTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.MESSAGE_TYPE_COLUMN_NAME)));
        message.setContactId(messageTransactionRecord.getUUIDValue(ChatMiddlewareDatabaseConstants.MESSAGE_CONTACT_ID));
        message.setCount(messageTransactionRecord.getLongValue(ChatMiddlewareDatabaseConstants.MESSAGE_COUNT));


        return message;
    }

    private ChatUserIdentity getChatUserIdentityTransaction(final DatabaseTableRecord chatUserIdentityTransactionRecord) throws CantLoadTableToMemoryException, DatabaseOperationException, InvalidParameterException
    {
        ChatUserIdentityImpl chatUserIdentity = null;
        try {
            chatUserIdentity = new ChatUserIdentityImpl(chatUserIdentityTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.IDENTITY_ALIAS_COLUMN_NAME),
                    null,
                    chatUserIdentityTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.IDENTITY_PUBLIC_KEY_COLUMN_NAME),
                    null,
                    getChatUserIdentityProfileImage(chatUserIdentityTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.IDENTITY_PUBLIC_KEY_COLUMN_NAME)),
                    Actors.getByCode(chatUserIdentityTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.IDENTITY_ACTOR_TYPE_COLUMN_NAME)),
                    PlatformComponentType.getByCode(chatUserIdentityTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.IDENTITY_PLATFORM_COMPONENT_TYPE_COLUMN_NAME))
                    );
        } catch (FileNotFoundException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
        }

        return chatUserIdentity;
    }

    private Contact getContactTransaction(final DatabaseTableRecord contactTransactionRecord) throws CantLoadTableToMemoryException, DatabaseOperationException, InvalidParameterException {

        ContactImpl contact = new ContactImpl();

        contact.setContactId(contactTransactionRecord.getUUIDValue(ChatMiddlewareDatabaseConstants.CONTACTS_ID_CONTACT_COLUMN_NAME));
        contact.setAlias(contactTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CONTACTS_ALIAS_COLUMN_NAME));
        contact.setRemoteName(contactTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CONTACTS_REMOTE_NAME_COLUMN_NAME));
        contact.setRemoteActorType(PlatformComponentType.getByCode(contactTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CONTACTS_REMOTE_ACTOR_TYPE_COLUMN_NAME)));
        contact.setRemoteActorPublicKey(contactTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CONTACTS_REMOTE_ACTOR_PUB_KEY_COLUMN_NAME));
        contact.setCreationDate(contactTransactionRecord.getLongValue(ChatMiddlewareDatabaseConstants.CONTACTS_CREATION_DATE_COLUMN_NAME));
        contact.setContactStatus(ContactStatus.getByCode(contactTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CONTACTS_CONTACT_STATUS_COLUMN_NAME)));

        return contact;
    }

    private ContactConnection getContactConnectionTransaction(final DatabaseTableRecord contactTransactionRecord) throws CantLoadTableToMemoryException, DatabaseOperationException, InvalidParameterException {
        ContactConnectionImpl contactConnection = new ContactConnectionImpl();

        contactConnection.setContactId(contactTransactionRecord.getUUIDValue(ChatMiddlewareDatabaseConstants.CONTACTS_CONNECTION_ID_CONTACT_COLUMN_NAME));
        contactConnection.setAlias(contactTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CONTACTS_CONNECTION_ALIAS_COLUMN_NAME));
        contactConnection.setRemoteName(contactTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CONTACTS_CONNECTION_REMOTE_NAME_COLUMN_NAME));
        contactConnection.setRemoteActorType(PlatformComponentType.getByCode(contactTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CONTACTS_CONNECTION_REMOTE_ACTOR_TYPE_COLUMN_NAME)));
        contactConnection.setRemoteActorPublicKey(contactTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CONTACTS_CONNECTION_REMOTE_ACTOR_PUB_KEY_COLUMN_NAME));
        contactConnection.setCreationDate(contactTransactionRecord.getLongValue(ChatMiddlewareDatabaseConstants.CONTACTS_CONNECTION_CREATION_DATE_COLUMN_NAME));
        contactConnection.setContactStatus(ContactStatus.getByCode(contactTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CONTACTS_CONNECTION_CONTACT_STATUS_COLUMN_NAME)));

        return contactConnection;
    }

    private void checkDatabaseRecords(List<DatabaseTableRecord> records) throws
            UnexpectedResultReturnedFromDatabaseException {
        /**
         * Represents the maximum number of records in <code>records</code>
         * I'm gonna set this number in 1 for now, because I want to check the records object has
         * one only result.
         */
        int VALID_RESULTS_NUMBER=1;
        int recordsSize;
        if(records.isEmpty()){
            return;
        }
        recordsSize=records.size();
        if(recordsSize>VALID_RESULTS_NUMBER){
            throw new UnexpectedResultReturnedFromDatabaseException("I excepted "+VALID_RESULTS_NUMBER+", but I got "+recordsSize);
        }
    }

    /**
     * This method returns the event database table
     * @return
     */
    private DatabaseTable getDatabaseEventsTable() {
        return database.getTable(
                ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_TABLE_NAME);
    }

    /**
     * This method returns the chat database table
     * @return
     */
    private DatabaseTable getDatabaseChatTable() {
        return database.getTable(
                ChatMiddlewareDatabaseConstants.CHATS_TABLE_NAME);
    }

    /**
     * This method returns the chat database table
     * @return
     */
    private DatabaseTable getDatabaseMessageTable() {
        return database.getTable(
                ChatMiddlewareDatabaseConstants.MESSAGE_TABLE_NAME);
    }

    /**
     * This method saves a new event in database
     * @param eventType
     * @param eventSource
     * @throws CantSaveEventException
     */
    public void saveNewEvent(
            String eventType,
            String eventSource,
            UUID chatId) throws CantSaveEventException {
        try {
            DatabaseTable databaseTable = getDatabaseEventsTable();
            DatabaseTableRecord eventRecord = databaseTable.getEmptyRecord();
            UUID eventRecordID = UUID.randomUUID();
            long unixTime = System.currentTimeMillis();
            //Logger LOG = Logger.getGlobal();
            //LOG.info("Distribution DAO:\nUUID:" + eventRecordID + "\n" + unixTime);
            eventRecord.setUUIDValue(ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_ID_COLUMN_NAME, eventRecordID);
            eventRecord.setStringValue(ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_EVENT_COLUMN_NAME, eventType);
            eventRecord.setStringValue(ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_SOURCE_COLUMN_NAME, eventSource);
            eventRecord.setStringValue(ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode());
            eventRecord.setLongValue(ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, unixTime);
            eventRecord.setUUIDValue(ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_CHAT_ID_COLUMN_NAME, chatId);
            databaseTable.insertRecord(eventRecord);

        } catch (CantInsertRecordException exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantSaveEventException(
                    exception,
                    "Saving new event.",
                    "Cannot insert a record in Open Contract database");
        } catch(Exception exception){
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantSaveEventException(
                    FermatException.wrapException(exception),
                    "Saving new event.",
                    "Unexpected exception");
        }
    }

    public List<EventRecord> getPendingEventList() throws
            CantGetPendingEventListException,
            UnexpectedResultReturnedFromDatabaseException {
        try{
            DatabaseTable databaseTable=getDatabaseEventsTable();
            List<EventRecord> eventRecords=new ArrayList<>();
            EventRecord eventRecord;
            databaseTable.addStringFilter(
                    ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_STATUS_COLUMN_NAME,
                    EventStatus.PENDING.getCode(),
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            if(records.isEmpty()){
                //There is no records in database, I'll return an empty list.
                return eventRecords;
            }
            for(DatabaseTableRecord databaseTableRecord : records){
                String eventTypeString=databaseTableRecord.getStringValue(
                        ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_EVENT_COLUMN_NAME);
                if(eventTypeString==null){
                    throw new UnexpectedResultReturnedFromDatabaseException("The event type is null");
                }
                eventRecord=new EventRecord(getEventTypeFromStringCode(eventTypeString));
                String eventId=databaseTableRecord.getStringValue(
                        ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_ID_COLUMN_NAME);
                eventRecord.setEventId(eventId);
                String eventSource =databaseTableRecord.getStringValue(
                        ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_SOURCE_COLUMN_NAME);
                if(eventSource==null){
                    throw new UnexpectedResultReturnedFromDatabaseException("The event source is null");
                }
                eventRecord.setEventSource(EventSource.getByCode(eventSource));
                String eventStatus=databaseTableRecord.getStringValue(
                        ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_STATUS_COLUMN_NAME);
                if(eventStatus==null){
                    throw new UnexpectedResultReturnedFromDatabaseException("The event status is null");
                }
                eventRecord.setEventStatus(EventStatus.getByCode(eventStatus));
                long timestamp=databaseTableRecord.getLongValue(
                        ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME);
                eventRecord.setTimestamp(timestamp);
                UUID chatId=databaseTableRecord.getUUIDValue(
                        ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_CHAT_ID_COLUMN_NAME);
                eventRecord.setChatId(chatId);
                eventRecords.add(eventRecord);
            }
            return eventRecords;
        } catch (CantLoadTableToMemoryException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new CantGetPendingEventListException(e,
                    "Getting events in EventStatus.PENDING",
                    "Cannot load the table into memory");
        } catch (InvalidParameterException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new CantGetPendingEventListException(e,
                    "Getting events in EventStatus.PENDING",
                    "Invalid parameter in EventStatus enum");
        } catch (Exception e){
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new CantGetPendingEventListException(e,
                    "Getting events in EventStatus.PENDING",
                    "Unexpected Exception");
        }
    }

    /**
     * This method returns the EventType by String code.
     * @param code
     * @return
     * @throws InvalidParameterException
     */
    private EventType getEventTypeFromStringCode(
            String code)
            throws InvalidParameterException {
        if(code.equals(EventType.INCOMING_CHAT.getCode())){
            return EventType.INCOMING_CHAT;
        }
        if(code.equals(EventType.OUTGOING_CHAT.getCode())){
            return EventType.OUTGOING_CHAT;
        }
        if(code.equals(EventType.INCOMING_STATUS.getCode())){
            return EventType.INCOMING_STATUS;
        }
        throw new InvalidParameterException("The code "+code+" is not valid in EvenType enum");
    }

    /**
     * This method returns a pending events list from database
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetPendingEventListException
     */
    public List<String> getPendingEvents() throws
            UnexpectedResultReturnedFromDatabaseException,
            CantGetPendingEventListException {
        try{
            DatabaseTable databaseTable=getDatabaseEventsTable();
            List<String> eventTypeList=new ArrayList<>();
            String eventId;
            databaseTable.addStringFilter(
                    ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_STATUS_COLUMN_NAME,
                    EventStatus.PENDING.getCode(),
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            if(records.isEmpty()){
                //There is no records in database, I'll return an empty list.
                return eventTypeList;
            }
            for(DatabaseTableRecord databaseTableRecord : records){
                eventId=databaseTableRecord.getStringValue(
                        ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_ID_COLUMN_NAME);
                eventTypeList.add(eventId);
            }
            return eventTypeList;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetPendingEventListException(e,
                    "Getting events in EventStatus.PENDING",
                    "Cannot load the table into memory");
        }
    }

    /**
     * This method updates the event status
     * @param eventId
     * @param eventStatus
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantUpdateRecordException
     */
    public void updateEventStatus(
            String eventId,
            EventStatus eventStatus) throws UnexpectedResultReturnedFromDatabaseException,
            CantUpdateRecordException {
        try{
            DatabaseTable databaseTable=getDatabaseEventsTable();
            databaseTable.addStringFilter(
                    ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_ID_COLUMN_NAME,
                    eventId,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record=records.get(0);
            record.setStringValue(
                    ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_STATUS_COLUMN_NAME,
                    eventStatus.getCode());
            databaseTable.updateRecord(record);
        }  catch (CantLoadTableToMemoryException exception) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    "Updating parameter "+ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_STATUS_COLUMN_NAME,"");
        }
    }

    /**
     * This method updates an event record by eventId
     * @param eventRecord
     */
    public void updateEventRecord(EventRecord eventRecord)
            throws UnexpectedResultReturnedFromDatabaseException {
        try{
            DatabaseTable databaseTable=getDatabaseEventsTable();
            String eventId=eventRecord.getEventId();
            databaseTable.addStringFilter(
                    ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_ID_COLUMN_NAME,
                    eventId,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            if(records.isEmpty()){
                throw new UnexpectedResultReturnedFromDatabaseException(
                        "This record "+eventRecord+" does not exists in database.");
            }
            DatabaseTableRecord record=records.get(0);
            record.setStringValue(
                    ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_ID_COLUMN_NAME,
                    eventRecord.getEventId());
            record.setStringValue(
                    ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_EVENT_COLUMN_NAME,
                    eventRecord.getEventType().getCode());
            record.setStringValue(
                    ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_SOURCE_COLUMN_NAME,
                    eventRecord.getEventSource().getCode());
            record.setStringValue(
                    ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_STATUS_COLUMN_NAME,
                    eventRecord.getEventStatus().getCode());
            record.setLongValue(
                    ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME,
                    eventRecord.getTimestamp());
            record.setUUIDValue(
                    ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_CHAT_ID_COLUMN_NAME,
                    eventRecord.getChatId());
        } catch (CantLoadTableToMemoryException exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    "Updating parameter "+ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_STATUS_COLUMN_NAME,"");
        } catch (Exception e){
            errorManager.reportUnexpectedPluginException(
                        Plugins.CHAT_MIDDLEWARE,
                        UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                        FermatException.wrapException(e));
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                        "Updating an event record",
                        "Unexpected Exception");

        }
    }

    /**
     * This method checks if the chat exists in database.
     * @param chatId
     * @return
     * @throws CantGetChatException
     */
    public boolean chatIdExists(UUID chatId) throws CantGetChatException{
        try{
            DatabaseTable databaseTable=getDatabaseChatTable();
            return checkIdChatExists(
                    chatId,
                    ChatMiddlewareDatabaseConstants.CHATS_ID_CHAT_COLUMN_NAME,
                    databaseTable);
        } catch (Exception e){
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new CantGetChatException(e,
                    "Checking if chat id exists",
                    "Unexpected Exception");
        }

    }

    /**
     * This method checks if the message exists in database.
     * @param messageId
     * @return
     * @throws CantGetChatException
     */
    public boolean messageIdExists(UUID messageId) throws CantGetChatException{
        try{
            DatabaseTable databaseTable=getDatabaseMessageTable();
            return checkIdMessageExists(
                    messageId,
                    ChatMiddlewareDatabaseConstants.MESSAGE_ID_MESSAGE_COLUMN_NAME,
                    databaseTable);
        } catch (Exception e){
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new CantGetChatException(e,
                    "Checking if message id exists",
                    "Unexpected Exception");
        }

    }

    /**
     * This method checks if an Id (UUID) exists in database
     * @param id
     * @param databaseColumn
     * @param databaseTable
     * @return
     * @throws CantGetChatException
     */
    private boolean checkIdChatExists(
            UUID id,
            String databaseColumn,
            DatabaseTable databaseTable) throws
            CantGetChatException {
        try{
            DatabaseTableFilter databaseTableFilter=databaseTable.getEmptyTableFilter();
            databaseTableFilter.setType(DatabaseFilterType.EQUAL);
            databaseTableFilter.setValue(id.toString());
            databaseTableFilter.setColumn(databaseColumn);
            List<DatabaseTableRecord> records=getChatData(databaseTableFilter);
            return !(records == null || records.isEmpty());
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetChatException(
                    e,
                    "Checking if Id exists in database",
                    "An unexpected error in database"
            );
        }
    }
    private boolean checkIdMessageExists(
            UUID id,
            String databaseColumn,
            DatabaseTable databaseTable) throws
            CantGetChatException {
        try{
            DatabaseTableFilter databaseTableFilter=databaseTable.getEmptyTableFilter();
            databaseTableFilter.setType(DatabaseFilterType.EQUAL);
            databaseTableFilter.setValue(id.toString());
            databaseTableFilter.setColumn(databaseColumn);
            List<DatabaseTableRecord> records=getMessageData(databaseTableFilter);
            return !(records == null || records.isEmpty());
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetChatException(
                    e,
                    "Checking if Id exists in database",
                    "An unexpected error in database"
            );
        }
    }

    public long getLastMessageCount(){
        Database database = null;
        try {
            database = openDatabase();
            List<Message> messages = new ArrayList<>();
            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.MESSAGE_TABLE_NAME);
            table.addFilterOrder(ChatMiddlewareDatabaseConstants.MESSAGE_COUNT, DatabaseFilterOrder.DESCENDING);
            table.setFilterTop("1");
            table.loadToMemory();

            for (DatabaseTableRecord record : table.getRecords()) {
                final Message message = getMessageTransaction(record);

                messages.add(message);
            }

            database.closeDatabase();

            if(messages.isEmpty()){
                System.out.println("**12345 LAST MESSAGE = NO MESSAGE");
                return 0;
            }

            System.out.println("**12345 LAST MESSAGE = " + messages.get(0).getCount());
            return messages.get(0).getCount();
        }
        catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
        }
        return 1;
    }
}