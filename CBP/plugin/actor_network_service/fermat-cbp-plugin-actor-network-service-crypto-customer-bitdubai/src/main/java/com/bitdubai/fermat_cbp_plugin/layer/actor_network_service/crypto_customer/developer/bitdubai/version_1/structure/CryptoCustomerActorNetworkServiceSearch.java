package com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_customer.developer.bitdubai.version_1.structure;

import android.util.Base64;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.DiscoveryQueryParameters;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.exceptions.CantListCryptoCustomersException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.interfaces.CryptoCustomerSearch;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.utils.CryptoCustomerExposingData;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRequestListException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;

import java.util.ArrayList;
import java.util.List;

/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerActorNetworkServiceSearch</code>
 * contains all the functionality to search crypto customer actors.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 25/11/2015.
 */
public final class CryptoCustomerActorNetworkServiceSearch extends CryptoCustomerSearch {

    private final CommunicationsClientConnection communicationsClientConnection;
    private final ErrorManager                   errorManager                  ;
    private final PluginVersionReference         pluginVersionReference        ;

    public CryptoCustomerActorNetworkServiceSearch(final CommunicationsClientConnection communicationsClientConnection,
                                                   final ErrorManager                   errorManager                  ,
                                                   final PluginVersionReference         pluginVersionReference        ) {

        this.communicationsClientConnection = communicationsClientConnection;
        this.errorManager                   = errorManager                  ;
        this.pluginVersionReference         = pluginVersionReference        ;
    }

    @Override
    public List<CryptoCustomerExposingData> getResult() throws CantListCryptoCustomersException {

        try {

            DiscoveryQueryParameters discoveryQueryParameters = communicationsClientConnection.constructDiscoveryQueryParamsFactory(
                    PlatformComponentType.ACTOR_CRYPTO_CUSTOMER, // PlatformComponentType you want to find
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

            final List<CryptoCustomerExposingData> cryptoCustomerExposingDataList = new ArrayList<>();

            for (final PlatformComponentProfile platformComponentProfile : list) {

                byte[] imageByte = Base64.decode(platformComponentProfile.getExtraData(), Base64.DEFAULT);
                cryptoCustomerExposingDataList.add(new CryptoCustomerExposingData(platformComponentProfile.getIdentityPublicKey(), platformComponentProfile.getAlias(), imageByte));
            }

            return cryptoCustomerExposingDataList;

        } catch (final CantRequestListException e) {

            errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoCustomersException(e, "", "Problem trying to request list of registered components in communication layer.");

        } catch (final Exception e) {

            errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoCustomersException(e, "", "Unhandled error.");
        }
    }

    @Override
    public List<CryptoCustomerExposingData> getResult(final Integer max) throws CantListCryptoCustomersException {
        return null;
    }
}
