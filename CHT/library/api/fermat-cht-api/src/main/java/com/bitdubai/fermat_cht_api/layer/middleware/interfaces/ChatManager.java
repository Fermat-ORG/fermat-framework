package com.bitdubai.fermat_cht_api.layer.middleware.interfaces;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by miguel payarez (miguel_payarez@hotmail.com) on 29/12/15.
 * Update by Manuel Perez on 08/01/2016 (fix naming conventions)
 */
public interface ChatManager {

    //Todo: create a chat record or chat wrapper to clean this method, please, use english to name objects.

    List<Chat> getChats();

    Chat getChatByChatId(UUID chatId);

    Chat newEmptyInstanceChat();

    void saveChat(Chat chat);

    void deleteChat(Chat chat);

    List<Message> getMessages();

    Message getMessageByChatId(UUID chatId);

    Message getMessageByMessageId(UUID messageId);

    Chat newEmptyInstanceMessage();

    void saveMessage(Message message);

    void deleteMessage(Message message);
}
