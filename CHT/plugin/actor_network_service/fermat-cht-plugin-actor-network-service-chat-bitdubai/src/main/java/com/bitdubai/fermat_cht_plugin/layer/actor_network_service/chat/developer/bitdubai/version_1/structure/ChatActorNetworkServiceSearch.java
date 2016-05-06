package com.bitdubai.fermat_cht_plugin.layer.actor_network_service.chat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.DiscoveryQueryParameters;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.util.Base64;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantListChatException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.interfaces.ChatSearch;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.utils.ChatExposingData;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRequestListException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 07/04/16.
 */
public class ChatActorNetworkServiceSearch extends ChatSearch {

    private final CommunicationsClientConnection communicationsClientConnection;
    private final ErrorManager errorManager                  ;
    private final PluginVersionReference pluginVersionReference        ;

    public ChatActorNetworkServiceSearch(final CommunicationsClientConnection communicationsClientConnection,
                                                 final ErrorManager                   errorManager                  ,
                                                 final PluginVersionReference         pluginVersionReference        ) {

        this.communicationsClientConnection = communicationsClientConnection;
        this.errorManager = errorManager;
        this.pluginVersionReference = pluginVersionReference;
    }

    @Override
    public List<ChatExposingData> getResult() throws CantListChatException {

        try {

            DiscoveryQueryParameters discoveryQueryParameters = communicationsClientConnection.constructDiscoveryQueryParamsFactory(
                    PlatformComponentType.ACTOR_CHAT, // PlatformComponentType you want to find
                    NetworkServiceType.UNDEFINED,           // NetworkServiceType you want to find
                    null,                                      // alias
                    null,                                      // identityPublicKey
                    null,                                      // location
                    null,                                      // distance
                    null,                                      // name
                    null,                                      // extraData
                    null,                                      // offset
                    null,                                      // max
                    null,                                      // fromOtherPlatformComponentType, when use this filter apply the identityPublicKey
                    null                                       // fromOtherNetworkServiceType, when use this filter apply the NetworkServiceType
            );

            final List<PlatformComponentProfile> list = communicationsClientConnection.requestListComponentRegistered(discoveryQueryParameters);

            final List<ChatExposingData> chatExposingDataArrayList = new ArrayList<>();

            for (final PlatformComponentProfile platformComponentProfile : list) {

                System.out.println("************** I'm a chat searched: "+platformComponentProfile);
//                System.out.println("************** Do I have profile image?: "+(platformComponentProfile.getExtraData() != null));

                byte[] imageByte;

                JsonParser parser = new JsonParser();

                Gson gson = new Gson();

                JsonObject extraData = parser.parse(platformComponentProfile.getExtraData()).getAsJsonObject();

                String country = gson.fromJson(extraData.get(ChatExtraDataJsonAttNames.COUNTRY), String.class);

                String state = gson.fromJson(extraData.get(ChatExtraDataJsonAttNames.STATE), String.class);

                String city = gson.fromJson(extraData.get(ChatExtraDataJsonAttNames.CITY),String.class);

                String imageString = gson.fromJson(extraData.get(ChatExtraDataJsonAttNames.IMG), String.class);

                if(imageString != null && !imageString.equals(""))
                    imageByte = Base64.decode(imageString, Base64.DEFAULT);
                else
                    imageByte = null;

                chatExposingDataArrayList.add(new ChatExposingData(platformComponentProfile.getIdentityPublicKey(), platformComponentProfile.getAlias(), imageByte, country, state, city));
            }

            return chatExposingDataArrayList;

        } catch (final CantRequestListException e) {

            errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListChatException(e, "", "Problem trying to request list of registered components in communication layer.");

        } catch (final Exception e) {

            errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListChatException(e, "", "Unhandled error.");
        }
    }

    @Override
    public List<ChatExposingData> getResult(final Integer max) throws CantListChatException {
        return null;
    }
}
