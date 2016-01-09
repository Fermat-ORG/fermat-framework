package com.bitdubai.fermat_cht_api.layer.middleware.interfaces;

import java.sql.Date;
import java.util.List;

/**
 * Created by miguel payarez (miguel_payarez@hotmail.com) on 29/12/15.
 * Update by Manuel Perez on 08/01/2016 (fix naming conventions)
 */
public interface ChatManager {

    //Todo: create a chat record or chat wrapper to clean this method, please, use english to name objects.
    void newChat (
            Integer chatId, //Why this id is an integer? you can use UUID
            Integer objectId, //This must be a String, this field can be an UUID or publicKey
            String localActorType,
            String localActorPublicKey,
            String remoteActorType,
            String remoteActorPublicKey,
            String chatName,
            boolean status, //Why this argument is a boolean?
            Date date,
            Date lastMessageDate
    );

    List<Object> chatList(
            Integer chatId, //Why this id is an integer? you can use UUID
            Integer objectId //This must be a String, this field can be an UUID or publicKey
    );

    List<Object> chatDetails(
            Integer chatId, //Why this id is an integer? you can use UUID
            Integer objectId //This must be a String, this field can be an UUID or publicKey
                              );

    List<Object> sendMessage(
            Integer messageId, //Why this id is an integer? you can use UUID
            Integer chatId, //Why this id is an integer? you can use UUID
            String message,
            Integer status, //Why this argument is an integer?
            Boolean type,
            Date messageDate
    );


}
