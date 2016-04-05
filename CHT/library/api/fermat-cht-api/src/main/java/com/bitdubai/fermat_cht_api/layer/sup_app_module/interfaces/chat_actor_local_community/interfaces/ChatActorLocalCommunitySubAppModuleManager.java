package com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_local_community.interfaces;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;


import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_local_community.exceptions.ActorChatConnectionAlreadyRequestesException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_local_community.exceptions.ActorChatTypeNotSupportedException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_local_community.exceptions.ActorConnectionRequestNotFoundException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_local_community.exceptions.CantAcceptChatRequestException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_local_community.exceptions.CantListChatActorException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_local_community.exceptions.CantListChatIdentitiesToSelectException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_local_community.exceptions.CantRequestActorConnectionException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_local_community.exceptions.CantValidateActorConnectionStateException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_local_community.exceptions.ChatActorConnectionDenialFailedException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_local_community.exceptions.ChatActorDisconnectingFailedException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_local_community.exceptions.ChatActorLocalCancellingFailedException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_local_community.settings.ChatLocalCommunitySettings;


import java.util.List;
import java.util.UUID;

/**
 * Created by Eleazar (eorono@protonmail.com) on 3/04/16.
 */
public interface ChatActorLocalCommunitySubAppModuleManager extends ModuleManager <ChatLocalCommunitySettings, ChatActorLocalCommunitySelectableIdentity> {


    List<ChtActorLocalCommunityInformation> listWorldChatActor(ChatActorLocalCommunitySelectableIdentity selectedIdentity, final int max, final int offset) throws CantListChatActorException;

    List<ChatActorLocalCommunitySelectableIdentity> listSelectableIdentities() throws CantListChatIdentitiesToSelectException;

    void setSelectedActorIdentity(ChatActorLocalCommunitySelectableIdentity identity);

    ChatActorLocalCommunitySearch getChatActorSearch();

    void requestConnectionToChatActorLocal(ChatActorLocalCommunitySelectableIdentity selectedIdentity     ,
                                         ChtActorLocalCommunityInformation       chatActorLocalToContact) throws CantRequestActorConnectionException,
            ActorChatTypeNotSupportedException,
            ActorChatConnectionAlreadyRequestesException;

    void acceptChatActorLocal(UUID requestId) throws CantAcceptChatRequestException, ActorConnectionRequestNotFoundException;

    void denyChatConnection(UUID requestId) throws ChatActorConnectionDenialFailedException, ActorConnectionRequestNotFoundException;

    void disconnectChatActor(UUID requestId) throws ChatActorDisconnectingFailedException, ActorConnectionRequestNotFoundException;

    void cancelChatActor(UUID requestId) throws ChatActorLocalCancellingFailedException, ActorConnectionRequestNotFoundException;

    List<ChtActorLocalCommunityInformation> listAllConnectedCryptoBrokers(final ChatActorLocalCommunitySelectableIdentity selectedIdentity,
                                                                         final int                                     max             ,
                                                                         final int                                     offset          ) throws CantListChatActorException;

    List<ChtActorLocalCommunityInformation> listChatActorPendingLocalAction    (final ChatActorLocalCommunitySelectableIdentity selectedIdentity,
                                                                               final int max,
                                                                               final int offset) throws CantListChatActorException;

    List<ChtActorLocalCommunityInformation> listCryptoBrokersPendingRemoteAction(final ChatActorLocalCommunitySelectableIdentity selectedIdentity,
                                                                                final int max,
                                                                                final int offset) throws CantListChatActorException;

    int getChatActorWaitingYourAcceptanceCount();

    ConnectionState getActorConnectionState(String publicKey) throws CantValidateActorConnectionStateException;
    @Override
    SettingsManager<ChatLocalCommunitySettings> getSettingsManager();

    @Override
    ChatActorLocalCommunitySelectableIdentity getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException;

    @Override
    void createIdentity(String name, String phrase, byte[] profile_img) throws Exception;

    @Override
    void setAppPublicKey(String publicKey);

    @Override
    int[] getMenuNotifications();
}
