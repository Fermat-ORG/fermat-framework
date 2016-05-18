package com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.structure;

import android.util.Base64;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantListCryptoBrokersException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerSearch;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.CryptoBrokerExposingData;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.CryptoBrokerActorNetworkServicePluginRootTest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantRequestProfileListException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.DiscoveryQueryParameters;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.List;

/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.structure.CryptoBrokerActorNetworkServiceSearch</code>
 * contains all the functionality to search crypto broker actors.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 24/11/2015.
 */
public final class CryptoBrokerActorNetworkServiceSearchTest extends CryptoBrokerSearch {

    private final CryptoBrokerActorNetworkServicePluginRootTest pluginRoot;
    private final ErrorManager                   errorManager                  ;
    private final PluginVersionReference         pluginVersionReference        ;

    public CryptoBrokerActorNetworkServiceSearchTest(final CryptoBrokerActorNetworkServicePluginRootTest pluginRoot,
                                                     final ErrorManager errorManager,
                                                     final PluginVersionReference pluginVersionReference) {

        this.pluginRoot = pluginRoot;
        this.errorManager = errorManager;
        this.pluginVersionReference = pluginVersionReference;
    }

    @Override
    public List<CryptoBrokerExposingData> getResult() throws CantListCryptoBrokersException {

        try {

            DiscoveryQueryParameters discoveryQueryParameters = new DiscoveryQueryParameters(
                    Actors.CBP_CRYPTO_BROKER.getCode(),
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    NetworkServiceType.UNDEFINED,
                    null,
                    NetworkServiceType.CRYPTO_BROKER
            );

            final List<ActorProfile> list = pluginRoot.getConnection().listRegisteredActorProfiles(discoveryQueryParameters);

            final List<CryptoBrokerExposingData> cryptoBrokerExposingDataList = new ArrayList<>();

            for (final ActorProfile actorProfile : list) {

                System.out.println("************** I'm a crypto broker searched: "+actorProfile);
                System.out.println("************** Do I have profile image?: "+(actorProfile.getPhoto() != null));

                byte[] imageByte;

                if (actorProfile.getExtraData() != null)
                    imageByte = Base64.decode(actorProfile.getExtraData(), Base64.DEFAULT);
                else
                    imageByte = null;


                cryptoBrokerExposingDataList.add(new CryptoBrokerExposingData(actorProfile.getIdentityPublicKey(), actorProfile.getAlias(), imageByte));
            }

            return cryptoBrokerExposingDataList;

        } catch (final CantRequestProfileListException e) {

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
