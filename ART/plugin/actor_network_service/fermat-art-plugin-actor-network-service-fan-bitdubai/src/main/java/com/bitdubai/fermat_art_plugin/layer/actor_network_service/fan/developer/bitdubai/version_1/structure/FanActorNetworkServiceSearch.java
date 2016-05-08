package com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.structure;

import android.util.Base64;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.DiscoveryQueryParameters;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantListArtistsException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.ActorSearch;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.util.FanExposingData;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRequestListException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.List;

/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.structure.FanActorNetworkServiceSearch</code>
 * contains all the functionality to search crypto broker actors.
 * <p>
 * Created by Gabriel Araujo 31/03/2016.
 */
public final class FanActorNetworkServiceSearch extends ActorSearch<FanExposingData> {

    private final CommunicationsClientConnection communicationsClientConnection;
    private final ErrorManager                   errorManager                  ;
    private final PluginVersionReference         pluginVersionReference        ;

    public FanActorNetworkServiceSearch(final CommunicationsClientConnection communicationsClientConnection,
                                        final ErrorManager errorManager,
                                        final PluginVersionReference pluginVersionReference) {

        this.communicationsClientConnection = communicationsClientConnection;
        this.errorManager = errorManager;
        this.pluginVersionReference = pluginVersionReference;
    }

    @Override
    public List<FanExposingData> getResult() throws CantListArtistsException {

        try {

            DiscoveryQueryParameters discoveryQueryParameters = communicationsClientConnection.constructDiscoveryQueryParamsFactory(
                    PlatformComponentType.ART_FAN, // PlatformComponentType you want to find
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

            final List<FanExposingData> fanExposingDatas = new ArrayList<>();

            for (final PlatformComponentProfile platformComponentProfile : list) {

//                System.out.println("************** I'm a ART Artist searched: "+platformComponentProfile);
//                System.out.println("************** Do I have profile image?: "+(platformComponentProfile.getExtraData() != null));

                byte[] imageByte;

                if (platformComponentProfile.getExtraData() != null)
                    imageByte = Base64.decode(platformComponentProfile.getExtraData(), Base64.DEFAULT);
                else
                    imageByte = null;


                fanExposingDatas.add(new FanExposingData(platformComponentProfile.getIdentityPublicKey(), platformComponentProfile.getAlias(), imageByte));
            }

            return fanExposingDatas;

        } catch (final CantRequestListException e) {

            errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListArtistsException(e, "", "Problem trying to request list of registered components in communication layer.");

        } catch (final Exception e) {

            errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListArtistsException(e, "", "Unhandled error.");
        }
    }

    @Override
    public List<FanExposingData> getResult(PlatformComponentType actorTypeToLookFor) throws CantListArtistsException {
        try {

            DiscoveryQueryParameters discoveryQueryParameters = communicationsClientConnection.constructDiscoveryQueryParamsFactory(
                    actorTypeToLookFor, // PlatformComponentType you want to find
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

            final List<FanExposingData> artistExposingDatas = new ArrayList<>();

            for (final PlatformComponentProfile platformComponentProfile : list) {

                byte[] imageByte;

                if (platformComponentProfile.getExtraData() != null)
                    imageByte = Base64.decode(platformComponentProfile.getExtraData(), Base64.DEFAULT);
                else
                    imageByte = null;


                artistExposingDatas.add(new FanExposingData(platformComponentProfile.getIdentityPublicKey(), platformComponentProfile.getAlias(), imageByte));
            }

            return artistExposingDatas;

        } catch (final CantRequestListException e) {

            errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListArtistsException(e, "", "Problem trying to request list of registered components in communication layer.");

        } catch (final Exception e) {

            errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListArtistsException(e, "", "Unhandled error.");
        }
    }

    @Override
    public List<FanExposingData> getResult(final Integer max) throws CantListArtistsException {
        return null;
    }
}
