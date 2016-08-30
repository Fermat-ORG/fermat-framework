package com.bitdubai.fermat_cht_plugin.layer.sub_app_module.chat_community.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.interfaces.ChatSearch;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantGetChtActorSearchResult;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunitySearch;

/**
 * Created by Eleazar (eorono@protonmail.com) on 9/4/2016.
 * Edited by Miguel Rincon on 18/04/2016
 */


public class ChatActorCommunitySubAppModuleSearch implements ChatActorCommunitySearch {

    private final ChatManager chatActorNetworkServiceManager;

    public ChatActorCommunitySubAppModuleSearch(final ChatManager chatActorNetworkServiceManager) {

        this.chatActorNetworkServiceManager = chatActorNetworkServiceManager;
    }

    @Override
    public void getResult(String publicKey, DeviceLocation deviceLocation, double distance, String alias, Integer offSet, Integer max, String requesterPublicKey) throws CantGetChtActorSearchResult {
        try {

            ChatSearch chatActorSearch = chatActorNetworkServiceManager.getSearch();

           chatActorSearch.getResult(publicKey, deviceLocation, distance, alias, offSet, max, requesterPublicKey);

        } catch (final Exception exception) {

            throw new CantGetChtActorSearchResult(CantGetChtActorSearchResult.CONTEXT_CONTENT_SEPARATOR, FermatException.wrapException(exception), null, null);
        }
    }
}
