package com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;


import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.ActorChatConnectionAlreadyRequestesException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.ActorChatTypeNotSupportedException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.ActorConnectionRequestNotFoundException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantAcceptChatRequestException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantListChatActorException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantListChatIdentitiesToSelectException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantRequestActorConnectionException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantValidateActorConnectionStateException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.ChatActorConnectionDenialFailedException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.ChatActorDisconnectingFailedException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.ChatActorCancellingFailedException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.settings.ChatActorCommunitySettings;


import java.util.List;
import java.util.UUID;

/**
 * Created by Eleazar (eorono@protonmail.com) on 3/04/16.
 */
public interface ChatActorCommunitySubAppModuleManager extends ModuleManager <ChatActorCommunitySettings, ChatActorCommunitySelectableIdentity> {


    List<ChatActorCommunityInformation> listWorldChatActor(ChatActorCommunitySelectableIdentity selectedIdentity, final int max, final int offset) throws CantListChatActorException;

    List<ChatActorCommunitySelectableIdentity> listSelectableIdentities() throws CantListChatIdentitiesToSelectException;

    void setSelectedActorIdentity(ChatActorCommunitySelectableIdentity identity);

    ChatActorCommunitySearch getChatActorSearch();

    void requestConnectionToChatActor(ChatActorCommunitySelectableIdentity selectedIdentity     ,
                                         ChatActorCommunityInformation chatActorLocalToContact) throws CantRequestActorConnectionException,
            ActorChatTypeNotSupportedException,
            ActorChatConnectionAlreadyRequestesException;

    void acceptChatActor(UUID requestId) throws CantAcceptChatRequestException, ActorConnectionRequestNotFoundException;

    void denyChatConnection(UUID requestId) throws ChatActorConnectionDenialFailedException, ActorConnectionRequestNotFoundException;

    void disconnectChatActor(UUID requestId) throws ChatActorDisconnectingFailedException, ActorConnectionRequestNotFoundException;

    void cancelChatActor(UUID requestId) throws ChatActorCancellingFailedException, ActorConnectionRequestNotFoundException;

    List<ChatActorCommunityInformation> listAllConnectedCryptoBrokers(final ChatActorCommunitySelectableIdentity selectedIdentity,
                                                                         final int                                     max             ,
                                                                         final int                                     offset          ) throws CantListChatActorException;

    List<ChatActorCommunityInformation> listChatActorPendingLocalAction    (final ChatActorCommunitySelectableIdentity selectedIdentity,
                                                                               final int max,
                                                                               final int offset) throws CantListChatActorException;

    List<ChatActorCommunityInformation> listCryptoBrokersPendingRemoteAction(final ChatActorCommunitySelectableIdentity selectedIdentity,
                                                                                final int max,
                                                                                final int offset) throws CantListChatActorException;

    int getChatActorWaitingYourAcceptanceCount();

    ConnectionState getActorConnectionState(String publicKey) throws CantValidateActorConnectionStateException;
    @Override
    SettingsManager<ChatActorCommunitySettings> getSettingsManager();

    @Override
    ChatActorCommunitySelectableIdentity getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException;

    @Override
    void createIdentity(String name, String phrase, byte[] profile_img) throws Exception;

    @Override
    void setAppPublicKey(String publicKey);

    @Override
    int[] getMenuNotifications();
}
