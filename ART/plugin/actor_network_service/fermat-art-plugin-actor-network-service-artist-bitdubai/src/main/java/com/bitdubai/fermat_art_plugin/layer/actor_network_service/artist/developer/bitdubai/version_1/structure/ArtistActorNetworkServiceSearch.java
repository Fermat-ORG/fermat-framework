package com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantListArtistsException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.ActorSearch;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistExposingData;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.ArtistActorNetworkServicePluginRoot;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantRequestProfileListException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.DiscoveryQueryParameters;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;

import java.util.ArrayList;
import java.util.List;

/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.structure.ArtistActorNetworkServiceSearch</code>
 * contains all the functionality to search crypto broker actors.
 * <p>
 * Created by Gabriel Araujo 31/03/2016.
 */
public final class ArtistActorNetworkServiceSearch extends ActorSearch<ArtistExposingData> {

    private final ArtistActorNetworkServicePluginRoot pluginRoot;

    public ArtistActorNetworkServiceSearch(ArtistActorNetworkServicePluginRoot artistActorNetworkServicePluginRoot) {
        this.pluginRoot = artistActorNetworkServicePluginRoot;
    }

    @Override
    public List<ArtistExposingData> getResult(Integer max, Integer offset) throws CantListArtistsException {

        try {

            DiscoveryQueryParameters discoveryQueryParameters = new DiscoveryQueryParameters(
                    Actors.ART_ARTIST.getCode(),           // NetworkServiceType you want to find
                    null,
                    null,
                    null,
                    null,
                    null,
                    max,
                    null,
                    NetworkServiceType.UNDEFINED,
                    offset,
                    NetworkServiceType.ARTIST_ACTOR
            );

            final List<ActorProfile> list = pluginRoot.getConnection().listRegisteredActorProfiles(discoveryQueryParameters);

            final List<ArtistExposingData> artistExposingDataList = new ArrayList<>();

            for (final ActorProfile actorProfile : list) {

                artistExposingDataList.add(
                        new ArtistExposingData(
                                actorProfile.getIdentityPublicKey(),
                                actorProfile.getAlias(),
                                actorProfile.getExtraData(),
                                actorProfile.getLocation()));
            }

            return artistExposingDataList;

        } catch (final CantRequestProfileListException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListArtistsException(e, "", "Problem trying to request list of registered components in communication layer.");

        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListArtistsException(e, "", "Unhandled error.");
        }
    }

    @Override
    public List<ArtistExposingData> getResult(
            String publicKey,
            DeviceLocation deviceLocation,
            double distance,
            String alias,
            Integer max,
            Integer offset) throws CantListArtistsException {
        try {

            DiscoveryQueryParameters discoveryQueryParameters = new DiscoveryQueryParameters(
                    Actors.ART_ARTIST.getCode(),
                    alias,
                    distance,
                    null,
                    publicKey,
                    deviceLocation,
                    max,
                    null,
                    NetworkServiceType.UNDEFINED,
                    offset,
                    NetworkServiceType.ARTIST_ACTOR
            );

            final List<ActorProfile> list = pluginRoot.getConnection().listRegisteredActorProfiles(discoveryQueryParameters);

            final List<ArtistExposingData> artistExposingDataList = new ArrayList<>();

            for (final ActorProfile actorProfile : list) {

                artistExposingDataList.add(
                        new ArtistExposingData(
                                actorProfile.getIdentityPublicKey(),
                                actorProfile.getAlias(),
                                actorProfile.getExtraData(),
                                actorProfile.getLocation()));
            }

            return artistExposingDataList;

        } catch (final CantRequestProfileListException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListArtistsException(e, "", "Problem trying to request list of registered components in communication layer.");

        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListArtistsException(e, "", "Unhandled error.");
        }
    }

    @Override
    public List<ArtistExposingData> getResultLocation(
            DeviceLocation deviceLocation,
            Integer max,
            Integer offset) throws CantListArtistsException {
        try {

            DiscoveryQueryParameters discoveryQueryParameters = new DiscoveryQueryParameters(
                    Actors.ART_ARTIST.getCode(),
                    null,
                    null,
                    null,
                    null,
                    deviceLocation,
                    max,
                    null,
                    NetworkServiceType.UNDEFINED,
                    offset,
                    NetworkServiceType.ARTIST_ACTOR
            );

            final List<ActorProfile> list = pluginRoot.getConnection().listRegisteredActorProfiles(discoveryQueryParameters);

            final List<ArtistExposingData> artistExposingDataList = new ArrayList<>();

            for (final ActorProfile actorProfile : list) {

                artistExposingDataList.add(
                        new ArtistExposingData(
                                actorProfile.getIdentityPublicKey(),
                                actorProfile.getAlias(),
                                actorProfile.getExtraData(),
                                actorProfile.getLocation()));
            }

            return artistExposingDataList;

        } catch (final CantRequestProfileListException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListArtistsException(e, "", "Problem trying to request list of registered components in communication layer.");

        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListArtistsException(e, "", "Unhandled error.");
        }
    }

    @Override
    public List<ArtistExposingData> getResultDistance(
            double distance,
            Integer max,
            Integer offset) throws CantListArtistsException {
        try {

            DiscoveryQueryParameters discoveryQueryParameters = new DiscoveryQueryParameters(
                    Actors.ART_ARTIST.getCode(),
                    null,
                    distance,
                    null,
                    null,
                    null,
                    max,
                    null,
                    NetworkServiceType.UNDEFINED,
                    offset,
                    NetworkServiceType.ARTIST_ACTOR
            );

            final List<ActorProfile> list = pluginRoot.getConnection().listRegisteredActorProfiles(discoveryQueryParameters);

            final List<ArtistExposingData> artistExposingDataList = new ArrayList<>();

            for (final ActorProfile actorProfile : list) {

                artistExposingDataList.add(
                        new ArtistExposingData(
                                actorProfile.getIdentityPublicKey(),
                                actorProfile.getAlias(),
                                actorProfile.getExtraData(),
                                actorProfile.getLocation()));
            }

            return artistExposingDataList;

        } catch (final CantRequestProfileListException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListArtistsException(e, "", "Problem trying to request list of registered components in communication layer.");

        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListArtistsException(e, "", "Unhandled error.");
        }
    }

    @Override
    public List<ArtistExposingData> getResultAlias(String alias, Integer max, Integer offset) throws CantListArtistsException {
        try {

            DiscoveryQueryParameters discoveryQueryParameters = new DiscoveryQueryParameters(
                    Actors.ART_ARTIST.getCode(),
                    alias,
                    null,
                    null,
                    null,
                    null,
                    max,
                    null,
                    NetworkServiceType.UNDEFINED,
                    offset,
                    NetworkServiceType.ARTIST_ACTOR
            );

            final List<ActorProfile> list = pluginRoot.getConnection().listRegisteredActorProfiles(discoveryQueryParameters);

            final List<ArtistExposingData> artistExposingDataList = new ArrayList<>();

            for (final ActorProfile actorProfile : list) {

                artistExposingDataList.add(
                        new ArtistExposingData(
                                actorProfile.getIdentityPublicKey(),
                                actorProfile.getAlias(),
                                actorProfile.getExtraData(),
                                actorProfile.getLocation()));
            }

            return artistExposingDataList;

        } catch (final CantRequestProfileListException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListArtistsException(e, "", "Problem trying to request list of registered components in communication layer.");

        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListArtistsException(e, "", "Unhandled error.");
        }
    }

    @Override
    public List<ArtistExposingData> getResult(final Integer max) throws CantListArtistsException {
        /**
         * TODO: to implement
         */
        return null;
    }
}
