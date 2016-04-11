package com.fermat_cht_plugin.layer.sub_app_module.chat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cht_api.layer.actor_network_service.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.interfaces.ChatSearch;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.utils.ChatExposingData;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantGetChtActorSearchResult;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunityInformation;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunitySearch;

import java.util.ArrayList;
import java.util.List;

public class ChatActorCommunitySubAppModuleSearch implements ChatActorCommunitySearch {

    private final ChatManager chatActorNetworkServiceManager;
    private String alias;

    public ChatActorCommunitySubAppModuleSearch(final ChatManager chatActorNetworkServiceManager) {

        this.chatActorNetworkServiceManager = chatActorNetworkServiceManager;
    }



    @Override
    public void addActorAlias(String alias) {

        this.alias = alias;
    }

    @Override
    public List<ChatActorCommunityInformation> getResult() throws CantGetChtActorSearchResult {

        try {

            ChatSearch chatActorSearch = chatActorNetworkServiceManager.getSearch();

            final List<ChatExposingData> chatExposingDataList = chatActorSearch.getResult();

            final List<ChatActorCommunityInformation> chatActorLocalCommunityInformationList = new ArrayList<>();

            for(ChatExposingData cced : chatExposingDataList)
                chatActorLocalCommunityInformationList.add(new ChatActorCommunitySubAppModuleInformationImpl(cced));

            return chatActorLocalCommunityInformationList;

        } catch (final Exception exception) {

            throw new CantGetChtActorSearchResult("", exception, "", "Unhandled Error.");
        }
    }
}
