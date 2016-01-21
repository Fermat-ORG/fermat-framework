package com.bitdubai.fermat_cht_api.layer.middleware.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteContactException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetContactException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantNewEmptyChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantNewEmptyContactException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantNewEmptyMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveContactException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveMessageException;

import java.util.List;
import java.util.UUID;

/**
 * Created by miguel payarez (miguel_payarez@hotmail.com) on 29/12/15.
 * Update by Manuel Perez on 08/01/2016 (fix naming conventions)
 */
public interface ChatManager extends FermatManager{

    //Documentar
    List<Chat> getChats() throws CantGetChatException;

    Chat getChatByChatId(UUID chatId) throws CantGetChatException;

    Chat newEmptyInstanceChat() throws CantNewEmptyChatException;

    void saveChat(Chat chat) throws CantSaveChatException;

    void deleteChat(Chat chat) throws CantDeleteChatException;

    List<Message> getMessages() throws CantGetMessageException;

    Message getMessageByChatId(UUID chatId) throws CantGetMessageException;

    Message getMessageByMessageId(UUID messageId) throws CantGetMessageException;

    Message newEmptyInstanceMessage() throws CantNewEmptyMessageException;

    void saveMessage(Message message) throws CantSaveMessageException;

    void deleteMessage(Message message) throws CantDeleteMessageException;

    List<Contact> getContacts() throws CantGetContactException;

    Contact getContactByContactId(UUID contactId) throws CantGetContactException;

    Contact newEmptyInstanceContact() throws CantNewEmptyContactException;

    void saveContact(Contact contact) throws CantSaveContactException;

    void deleteContact(Contact contact) throws CantDeleteContactException;

}
