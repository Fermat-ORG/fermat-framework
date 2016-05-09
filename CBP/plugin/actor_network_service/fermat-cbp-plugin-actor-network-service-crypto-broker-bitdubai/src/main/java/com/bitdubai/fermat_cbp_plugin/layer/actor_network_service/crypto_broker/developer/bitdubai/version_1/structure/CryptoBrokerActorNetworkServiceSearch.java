package com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.structure;

import android.util.Base64;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.DiscoveryQueryParameters;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantListCryptoBrokersException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerSearch;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.CryptoBrokerExposingData;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRequestListException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;

import java.util.ArrayList;
import java.util.List;

/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.structure.CryptoBrokerActorNetworkServiceSearch</code>
 * contains all the functionality to search crypto broker actors.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 24/11/2015.
 */
public final class CryptoBrokerActorNetworkServiceSearch extends CryptoBrokerSearch {

    private final CommunicationsClientConnection communicationsClientConnection;
    private final ErrorManager                   errorManager                  ;
    private final PluginVersionReference         pluginVersionReference        ;

    public CryptoBrokerActorNetworkServiceSearch(final CommunicationsClientConnection communicationsClientConnection,
                                                 final ErrorManager                   errorManager                  ,
                                                 final PluginVersionReference         pluginVersionReference        ) {

        this.communicationsClientConnection = communicationsClientConnection;
        this.errorManager = errorManager;
        this.pluginVersionReference = pluginVersionReference;
    }

    @Override
    public List<CryptoBrokerExposingData> getResult() throws CantListCryptoBrokersException {

        try {

            DiscoveryQueryParameters discoveryQueryParameters = communicationsClientConnection.constructDiscoveryQueryParamsFactory(
                    PlatformComponentType.ACTOR_CRYPTO_BROKER, // PlatformComponentType you want to find
                    NetworkServiceType   .UNDEFINED,           // NetworkServiceType you want to find
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

            final List<CryptoBrokerExposingData> cryptoBrokerExposingDataList = new ArrayList<>();

            for (final PlatformComponentProfile platformComponentProfile : list) {

                System.out.println("************** I'm a crypto broker searched: "+platformComponentProfile);
                System.out.println("************** Do I have profile image?: "+(platformComponentProfile.getExtraData() != null));

                byte[] imageByte;

                if (platformComponentProfile.getExtraData() != null)
                    imageByte = Base64.decode(platformComponentProfile.getExtraData(), Base64.DEFAULT);
                else
                    imageByte = null;


                cryptoBrokerExposingDataList.add(new CryptoBrokerExposingData(platformComponentProfile.getIdentityPublicKey(), platformComponentProfile.getAlias(), imageByte));
            }

            return cryptoBrokerExposingDataList;

        } catch (final CantRequestListException e) {

            errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoBrokersException(e, "", "Problem trying to request list of registered components in communication layer.");

        } catch (final Exception e) {

            errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoBrokersException(e, "", "Unhandled error.");
        }
    }

    @Override
    public List<CryptoBrokerExposingData> getResult(final Integer max) throws CantListCryptoBrokersException {
        return null;
    }
}
