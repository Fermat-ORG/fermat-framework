package com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantListArtistsException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.ActorSearch;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.util.FanExposingData;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.FanActorNetworkServicePluginRoot;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantRequestProfileListException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;

import java.util.ArrayList;
import java.util.List;

/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.structure.FanActorNetworkServiceSearch</code>
 * contains all the functionality to search crypto broker actors.
 * <p>
 * Created by Gabriel Araujo 31/03/2016.
 */
public final class FanActorNetworkServiceSearch extends ActorSearch<FanExposingData> {

    private final FanActorNetworkServicePluginRoot pluginRoot;

    public FanActorNetworkServiceSearch(FanActorNetworkServicePluginRoot fanActorNetworkServicePluginRoot) {
        this.pluginRoot = fanActorNetworkServicePluginRoot;
    }

    @Override
    public List<FanExposingData> getResult(Integer max, Integer offset) throws CantListArtistsException {

        try {

            com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.DiscoveryQueryParameters discoveryQueryParameters = new com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.DiscoveryQueryParameters(
                    Actors.ART_FAN.getCode(),           // NetworkServiceType you want to find
                    null,
                    null,
                    null,
                    null,
                    null,
                    max,
                    null,
                    NetworkServiceType.UNDEFINED,
                    offset,
                    NetworkServiceType.FAN_ACTOR
            );

            final List<ActorProfile> list = pluginRoot.getConnection().listRegisteredActorProfiles(discoveryQueryParameters);

            final List<FanExposingData> fanExposingDataArrayList = new ArrayList<>();

            for (final ActorProfile actorProfile : list) {

                fanExposingDataArrayList.add(
                        new FanExposingData(
                                actorProfile.getIdentityPublicKey(),
                                actorProfile.getAlias(),
                                actorProfile.getExtraData(),
                                actorProfile.getLocation()));
            }

            return fanExposingDataArrayList;

        } catch (final CantRequestProfileListException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListArtistsException(e, "", "Problem trying to request list of registered components in communication layer.");

        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListArtistsException(e, "", "Unhandled error.");
        }
    }

    @Override
    public List<FanExposingData> getResult(
            String publicKey,
            DeviceLocation deviceLocation,
            double distance,
            String alias,
            Integer max,
            Integer offset) throws CantListArtistsException {
        try {

            com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.DiscoveryQueryParameters discoveryQueryParameters = new com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.DiscoveryQueryParameters(
                    Actors.ART_FAN.getCode(),
                    alias,
                    distance,
                    null,
                    publicKey,
                    deviceLocation,
                    max,
                    null,
                    NetworkServiceType.UNDEFINED,
                    offset,
                    NetworkServiceType.FAN_ACTOR
            );

            final List<ActorProfile> list = pluginRoot.getConnection().listRegisteredActorProfiles(discoveryQueryParameters);

            final List<FanExposingData> fanExposingDataArrayList = new ArrayList<>();

            for (final ActorProfile actorProfile : list) {

                fanExposingDataArrayList.add(
                        new FanExposingData(
                                actorProfile.getIdentityPublicKey(),
                                actorProfile.getAlias(),
                                actorProfile.getExtraData(),
                                actorProfile.getLocation()));
            }

            return fanExposingDataArrayList;

        } catch (final CantRequestProfileListException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListArtistsException(e, "", "Problem trying to request list of registered components in communication layer.");

        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListArtistsException(e, "", "Unhandled error.");
        }
    }

    @Override
    public List<FanExposingData> getResultLocation(
            DeviceLocation deviceLocation,
            Integer max,
            Integer offset) throws CantListArtistsException {
        try {

            com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.DiscoveryQueryParameters discoveryQueryParameters = new com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.DiscoveryQueryParameters(
                    Actors.ART_FAN.getCode(),
                    null,
                    null,
                    null,
                    null,
                    deviceLocation,
                    max,
                    null,
                    NetworkServiceType.UNDEFINED,
                    offset,
                    NetworkServiceType.FAN_ACTOR
            );

            final List<ActorProfile> list = pluginRoot.getConnection().listRegisteredActorProfiles(discoveryQueryParameters);

            final List<FanExposingData> fanExposingDataArrayList = new ArrayList<>();

            for (final ActorProfile actorProfile : list) {

                fanExposingDataArrayList.add(
                        new FanExposingData(
                                actorProfile.getIdentityPublicKey(),
                                actorProfile.getAlias(),
                                actorProfile.getExtraData(),
                                actorProfile.getLocation()));
            }

            return fanExposingDataArrayList;

        } catch (final CantRequestProfileListException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListArtistsException(e, "", "Problem trying to request list of registered components in communication layer.");

        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListArtistsException(e, "", "Unhandled error.");
        }
    }

    @Override
    public List<FanExposingData> getResultDistance(
            double distance,
            Integer max,
            Integer offset) throws CantListArtistsException {
        try {

            com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.DiscoveryQueryParameters discoveryQueryParameters = new com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.DiscoveryQueryParameters(
                    Actors.ART_FAN.getCode(),
                    null,
                    distance,
                    null,
                    null,
                    null,
                    max,
                    null,
                    NetworkServiceType.UNDEFINED,
                    offset,
                    NetworkServiceType.FAN_ACTOR
            );

            final List<ActorProfile> list = pluginRoot.getConnection().listRegisteredActorProfiles(discoveryQueryParameters);

            final List<FanExposingData> fanExposingDataArrayList = new ArrayList<>();

            for (final ActorProfile actorProfile : list) {

                fanExposingDataArrayList.add(
                        new FanExposingData(
                                actorProfile.getIdentityPublicKey(),
                                actorProfile.getAlias(),
                                actorProfile.getExtraData(),
                                actorProfile.getLocation()));
            }

            return fanExposingDataArrayList;

        } catch (final CantRequestProfileListException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListArtistsException(e, "", "Problem trying to request list of registered components in communication layer.");

        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListArtistsException(e, "", "Unhandled error.");
        }
    }

    @Override
    public List<FanExposingData> getResultAlias(String alias, Integer max, Integer offset)
            throws CantListArtistsException {
        try {

            com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.DiscoveryQueryParameters discoveryQueryParameters = new com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.DiscoveryQueryParameters(
                    Actors.ART_FAN.getCode(),
                    alias,
                    null,
                    null,
                    null,
                    null,
                    max,
                    null,
                    NetworkServiceType.UNDEFINED,
                    offset,
                    NetworkServiceType.FAN_ACTOR
            );

            final List<ActorProfile> list = pluginRoot.getConnection().listRegisteredActorProfiles(discoveryQueryParameters);

            final List<FanExposingData> fanExposingDataArrayList = new ArrayList<>();

            for (final ActorProfile actorProfile : list) {

                fanExposingDataArrayList.add(
                        new FanExposingData(
                                actorProfile.getIdentityPublicKey(),
                                actorProfile.getAlias(),
                                actorProfile.getExtraData(),
                                actorProfile.getLocation()));
            }

            return fanExposingDataArrayList;

        } catch (final CantRequestProfileListException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListArtistsException(e, "", "Problem trying to request list of registered components in communication layer.");

        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListArtistsException(e, "", "Unhandled error.");
        }
    }

    @Override
    public List<FanExposingData> getResult(final Integer max) throws CantListArtistsException {
        /**
         * TODO: to implement
         */
        return null;
    }
}
