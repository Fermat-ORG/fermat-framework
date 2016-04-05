package com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_remote_community.interfaces;

import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDenyActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;

import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantGetChatRemoteListException;
import com.bitdubai.fermat_cht_api.layer.identity.exceptions.CantListChatIdentityException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_local_community.exceptions.CantListChatActorException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_remote_community.exception.CantAcceptRequestException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_remote_community.exception.CantLoginChatRemoteException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_remote_community.exception.CantStartRequestException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_remote_community.exception.ChatRemoteCancellingFailedException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_remote_community.exception.ChatRemoteDisconnectingFailedException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_remote_community.settings.ChatRemoteCommunitySettings;

import java.util.List;
import java.util.UUID;

/**
 * Created by Eleazar (eorono@protonmail.com) on 3/04/16.
 */

public interface ChatRemoteCommunitySubAppModuleManager extends ModuleManager<ChatRemoteCommunitySettings, ActiveActorIdentityInformation> {


    List<ChatRemoteCommunityInformation> listWorldChatRemote(ChatRemoteCommunitySelectableIdentity selectedIdentity, final int max, final int offset) throws CantListChatActorException;


    List<ChatRemoteCommunitySelectableIdentity> listSelectableIdentities() throws CantListChatIdentityException;


    void setSelectedActorIdentity(ChatRemoteCommunitySelectableIdentity identity);




    List<LinkedChatRemoteIdentity> listChatRemotePendingLocalAction(final ChatRemoteCommunitySelectableIdentity selectedIdentity,
                                                                             final int max,
                                                                             final int offset) throws CantGetChatRemoteListException;

    List<ChatRemoteCommunityInformation> listAllConnectedChatRemote(final ChatRemoteCommunitySelectableIdentity selectedIdentity,
                                                                             final int max,
                                                                             final int offset) throws CantGetChatRemoteListException;


    void acceptChatRemote(UUID connectionId) throws CantAcceptRequestException;


    void denyConnection(UUID connectionId) throws CantDenyActorConnectionRequestException;


    List<ChatRemoteCommunityInformation> getSuggestionsToContact(int max, int offset) throws CantGetChatRemoteListException;

    ChatRemoteCommunitySearch getChatRemoteSearch();


    void askChatRemoteForAcceptance(String chatRemoteToAddName, String chatRemoteeToAddPublicKey, byte[] profileImage) throws CantStartRequestException;


    void disconnectChatRemote(final UUID requestId) throws ChatRemoteDisconnectingFailedException;


    void cancelChatRemote(String cryptoCustomerToCancelPublicKey) throws ChatRemoteCancellingFailedException;


    List<ChatRemoteCommunityInformation> getAllChatRemote(int max, int offset) throws CantGetChatRemoteListException;


    List<ChatRemoteCommunityInformation> getChatRemoteWaitingYourAcceptance(int max, int offset) throws CantGetChatRemoteListException;


    List<ChatRemoteCommunityInformation> getChatRemoteWaitingTheirAcceptance(int max, int offset) throws CantGetChatRemoteListException;



    void login(String remotePublicKey) throws CantLoginChatRemoteException;


    @Override
    SettingsManager<ChatRemoteCommunitySettings> getSettingsManager();

    @Override
    ChatRemoteCommunitySelectableIdentity getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException;

    @Override
    void createIdentity(String name, String phrase, byte[] profile_img) throws Exception;

    @Override
    void setAppPublicKey(String publicKey);

    @Override
    int[] getMenuNotifications();
}