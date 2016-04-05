package com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_remote.interfaces;

import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_remote.exceptions.CantGetChatRemoteListException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_remote.exceptions.CantLoginLocalException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_remote.exceptions.CantAcceptChatRemoteRequestException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_remote.exceptions.ChatRemoteCancellingFailedException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_remote.exceptions.ChatRemoteConnectionRejectionFailedException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_remote.exceptions.ChatRemoteDisconnectingFailedException;
import java.util.List;

/**
 * Created by Eleazar (eorono@protonmail.com) on 3/04/16.
 */

public interface ChatRemoteModuleManager {


    ChatRemoteSearch searchChatRemote();



    void acceptChatRemote(String chatRemoteToAddName, String chatRemoteToAddPublicKey, byte[] profileImage) throws CantAcceptChatRemoteRequestException;



    void denyConnection(String chatRemoteToRejectPublicKey) throws ChatRemoteConnectionRejectionFailedException;


    void disconnectChatRemote(String chatRemoteToDisconnectPublicKey) throws ChatRemoteDisconnectingFailedException;


    void cancelChatRemote(String chatRemoteToCancelPublicKey) throws ChatRemoteCancellingFailedException;


    List<ChatRemoteInformation> getAllChatRemote(int max, int offset) throws CantGetChatRemoteListException;


    List<ChatRemoteInformation> getChatRemoteWaitingYourAcceptance(int max, int offset) throws CantGetChatRemoteListException;



    void login(String customerPublicKey) throws CantLoginLocalException;


}