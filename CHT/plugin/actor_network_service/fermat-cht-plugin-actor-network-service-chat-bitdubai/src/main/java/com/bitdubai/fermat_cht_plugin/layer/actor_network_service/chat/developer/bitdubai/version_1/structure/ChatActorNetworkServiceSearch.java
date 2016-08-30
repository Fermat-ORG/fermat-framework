package com.bitdubai.fermat_cht_plugin.layer.actor_network_service.chat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantListChatException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.interfaces.ChatSearch;
import com.bitdubai.fermat_cht_plugin.layer.actor_network_service.chat.developer.bitdubai.version_1.ChatActorNetworkServicePluginRoot;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.DiscoveryQueryParameters;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/05/2016.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ChatActorNetworkServiceSearch extends ChatSearch {

    private final ChatActorNetworkServicePluginRoot pluginRoot;

    public ChatActorNetworkServiceSearch(final ChatActorNetworkServicePluginRoot pluginRoot) {

        this.pluginRoot = pluginRoot;
    }

    @Override
    public void getResult(String publicKey, DeviceLocation deviceLocation, double distance, String alias, Integer offSet, Integer max, String requesterPublicKey) throws CantListChatException {

        try {

            DiscoveryQueryParameters discoveryQueryParameters = new DiscoveryQueryParameters(
                    publicKey, //TODO: Se coloco null ya que leon necesita que esta valor null porque esto solo se usa solo para buscar por publicKey del Actor
                    NetworkServiceType.UNDEFINED,
                    Actors.CHAT.getCode(),
                    null,
                    alias,
                    null,
                    deviceLocation,
                    distance,
                    true,
                    null,
                    max,
                    offSet,
                    false);

            pluginRoot.discoveryActorProfiles(discoveryQueryParameters,requesterPublicKey);

        }catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListChatException(e, "", "Unhandled error.");
        }
    }
}
