package com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantListCryptoBrokersException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerSearch;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.CryptoBrokerExposingData;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.CryptoBrokerActorNetworkServicePluginRoot;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantRequestProfileListException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.DiscoveryQueryParameters;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;

import java.util.ArrayList;
import java.util.List;

/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.structure.CryptoBrokerActorNetworkServiceSearch</code>
 * contains all the functionality to search crypto broker actors.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 24/11/2015.
 */
public final class CryptoBrokerActorNetworkServiceSearch extends CryptoBrokerSearch {

    private final CryptoBrokerActorNetworkServicePluginRoot pluginRoot;

    public CryptoBrokerActorNetworkServiceSearch(final CryptoBrokerActorNetworkServicePluginRoot pluginRoot) {

        this.pluginRoot = pluginRoot;
    }

    @Override
    public List<CryptoBrokerExposingData> getResult(Integer max, Integer offset) throws CantListCryptoBrokersException {

        try {

            DiscoveryQueryParameters discoveryQueryParameters = new DiscoveryQueryParameters(
                    Actors.CBP_CRYPTO_BROKER.getCode(),
                    null,
                    null,
                    null,
                    null,
                    null,
                    max,
                    null,
                    NetworkServiceType.UNDEFINED,
                    offset,
                    NetworkServiceType.CRYPTO_BROKER
            );

            final List<ActorProfile> list = pluginRoot.getConnection().listRegisteredActorProfiles(discoveryQueryParameters);

            final List<CryptoBrokerExposingData> cryptoBrokerExposingDataList = new ArrayList<>();

            for (final ActorProfile actorProfile : list) {

                cryptoBrokerExposingDataList.add(new CryptoBrokerExposingData(actorProfile.getIdentityPublicKey(), actorProfile.getAlias(), actorProfile.getPhoto(), actorProfile.getLocation(), 0, 0, actorProfile.getStatus()));
            }

            return cryptoBrokerExposingDataList;

        } catch (final CantRequestProfileListException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoBrokersException(e, "", "Problem trying to request list of registered components in communication layer.");

        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoBrokersException(e, "", "Unhandled error.");
        }
    }

    @Override
    public List<CryptoBrokerExposingData> getResult(String publicKey, DeviceLocation deviceLocation, double distance, String alias, Integer max, Integer offset) throws CantListCryptoBrokersException {
        try {

            /**
             * Constructor with params
             *
             * @param identityPublicKey    represents the identity public key of the component to discover.
             * @param networkServiceType   if we're looking for network services we'll set this value with the type of network service.
             * @param actorType            if we're looking for actors we'll set this value with the type of the actor.
             * @param name                 we can set here the name of the component to search or discover.
             * @param alias                we can set here the alias of the component to search or discover.
             * @param extraData            we can set here the extraData of the actor component to search or discover.
             * @param location             this param indicates a point for doing the discovery near it.
             * @param distance             this param indicates the distance to the point to look around.
             * @param isOnline             with this param we ask to the node the status of the profiles to discover.
             * @param lastConnectionTime   with this param we'll ask to the node only the profiles connected after the long timestamp.
             * @param max                  this param will be used with the pagination stuff.
             * @param offset               this param will be used with the pagination stuff.*/
            DiscoveryQueryParameters discoveryQueryParameters = new DiscoveryQueryParameters(
                    null,
                    NetworkServiceType.UNDEFINED,
                    Actors.CBP_CRYPTO_BROKER.getCode(),
                    null,
                    null,
                    null,
                    null,
                    null,
                    true,
                    //TODO: Se coloco null ya que leon necesita que esta valor null ya que solo esto se usa solo para buscar por publicKey del Actor
                    null, //publicKey,
                    max,
                    offset
            );
//            DiscoveryQueryParameters discoveryQueryParameters = new DiscoveryQueryParameters(
//                    Actors.CBP_CRYPTO_BROKER.getCode(),
//                    alias,
//                    distance,
//                    null,
//                    //TODO: Se coloco null ya que leon necesita que esta valor null ya que solo esto se usa solo para buscar por publicKey del Actor
//                    null, //publicKey,
//                    deviceLocation,
//                    max,
//                    null,
//                    NetworkServiceType.UNDEFINED,
//                    offset,
//                    NetworkServiceType.CRYPTO_BROKER
//            );

            final List<ActorProfile> list = pluginRoot.getConnection().listRegisteredActorProfiles(discoveryQueryParameters);

            final List<CryptoBrokerExposingData> cryptoBrokerExposingDataList = new ArrayList<>();

            for (final ActorProfile actorProfile : list) {
                System.out.println("************** I\'m a crypto Broker: " + actorProfile.getAlias() + " - " + actorProfile.getStatus());
                cryptoBrokerExposingDataList.add(new CryptoBrokerExposingData(actorProfile.getIdentityPublicKey(), actorProfile.getAlias(), actorProfile.getPhoto(), actorProfile.getLocation(), 0, 0, actorProfile.getStatus()));
            }

            return cryptoBrokerExposingDataList;

        } catch (final CantRequestProfileListException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoBrokersException(e, "", "Problem trying to request list of registered components in communication layer.");

        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoBrokersException(e, "", "Unhandled error.");
        }
    }

    @Override
    public List<CryptoBrokerExposingData> getResultLocation(DeviceLocation deviceLocation, Integer max, Integer offset) throws CantListCryptoBrokersException {

        try {

            DiscoveryQueryParameters discoveryQueryParameters = new DiscoveryQueryParameters(
                    Actors.CBP_CRYPTO_BROKER.getCode(),
                    null,
                    null,
                    null,
                    null,
                    deviceLocation,
                    max,
                    null,
                    NetworkServiceType.UNDEFINED,
                    offset,
                    NetworkServiceType.CRYPTO_BROKER
            );

            final List<ActorProfile> list = pluginRoot.getConnection().listRegisteredActorProfiles(discoveryQueryParameters);

            final List<CryptoBrokerExposingData> cryptoBrokerExposingDataList = new ArrayList<>();

            for (final ActorProfile actorProfile : list) {

                cryptoBrokerExposingDataList.add(new CryptoBrokerExposingData(actorProfile.getIdentityPublicKey(), actorProfile.getAlias(), actorProfile.getPhoto(), actorProfile.getLocation(), 0, 0, actorProfile.getStatus()));
            }

            return cryptoBrokerExposingDataList;

        } catch (final CantRequestProfileListException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoBrokersException(e, "", "Problem trying to request list of registered components in communication layer.");

        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoBrokersException(e, "", "Unhandled error.");
        }
    }

    @Override
    public List<CryptoBrokerExposingData> getResultDistance(double distance, Integer max, Integer offset) throws CantListCryptoBrokersException {

        try {

            DiscoveryQueryParameters discoveryQueryParameters = new DiscoveryQueryParameters(
                    Actors.CBP_CRYPTO_BROKER.getCode(),
                    null,
                    distance,
                    null,
                    null,
                    null,
                    max,
                    null,
                    NetworkServiceType.UNDEFINED,
                    offset,
                    NetworkServiceType.CRYPTO_BROKER
            );

            final List<ActorProfile> list = pluginRoot.getConnection().listRegisteredActorProfiles(discoveryQueryParameters);

            final List<CryptoBrokerExposingData> cryptoBrokerExposingDataList = new ArrayList<>();

            for (final ActorProfile actorProfile : list) {

                cryptoBrokerExposingDataList.add(new CryptoBrokerExposingData(actorProfile.getIdentityPublicKey(), actorProfile.getAlias(), actorProfile.getPhoto(), actorProfile.getLocation(), 0, 0, actorProfile.getStatus()));
            }

            return cryptoBrokerExposingDataList;

        } catch (final CantRequestProfileListException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoBrokersException(e, "", "Problem trying to request list of registered components in communication layer.");

        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoBrokersException(e, "", "Unhandled error.");
        }
    }

    @Override
    public List<CryptoBrokerExposingData> getResultAlias(String alias, Integer max, Integer offset) throws CantListCryptoBrokersException {

        try {

            DiscoveryQueryParameters discoveryQueryParameters = new DiscoveryQueryParameters(
                    Actors.CBP_CRYPTO_BROKER.getCode(),
                    alias,
                    null,
                    null,
                    null,
                    null,
                    max,
                    null,
                    NetworkServiceType.UNDEFINED,
                    offset,
                    NetworkServiceType.CRYPTO_BROKER
            );

            final List<ActorProfile> list = pluginRoot.getConnection().listRegisteredActorProfiles(discoveryQueryParameters);

            final List<CryptoBrokerExposingData> cryptoBrokerExposingDataList = new ArrayList<>();

            for (final ActorProfile actorProfile : list) {
                System.out.println("************** I\'m a crypto Broker: " + actorProfile.getAlias() + " - " + actorProfile.getStatus());
                cryptoBrokerExposingDataList.add(new CryptoBrokerExposingData(actorProfile.getIdentityPublicKey(), actorProfile.getAlias(), actorProfile.getPhoto(), actorProfile.getLocation(), 0, 0, actorProfile.getStatus()));
            }

            return cryptoBrokerExposingDataList;

        } catch (final CantRequestProfileListException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoBrokersException(e, "", "Problem trying to request list of registered components in communication layer.");

        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoBrokersException(e, "", "Unhandled error.");
        }
    }

    @Override
    public CryptoBrokerExposingData getResult(String publicKey) throws CantListCryptoBrokersException {

        try {

            DiscoveryQueryParameters discoveryQueryParameters = new DiscoveryQueryParameters(
                    //TODO:Hay que pasarle null porque no esta implementado de esa forma en p2p
                    //Actors.CBP_CRYPTO_BROKER.getCode(),
                    null,
                    null,
                    null,
                    null,
                    publicKey,
                    null,
                    null,
                    null,
                    NetworkServiceType.UNDEFINED,
                    null,
                    NetworkServiceType.CRYPTO_BROKER
            );

            final List<ActorProfile> list = pluginRoot.getConnection().listRegisteredActorProfiles(discoveryQueryParameters);

            CryptoBrokerExposingData cryptoBrokerExposingData = null;

            for (final ActorProfile actorProfile : list) {

                cryptoBrokerExposingData = new CryptoBrokerExposingData(actorProfile.getIdentityPublicKey(), actorProfile.getAlias(), actorProfile.getPhoto(), actorProfile.getLocation(), 0, 0, actorProfile.getStatus());
            }

            return cryptoBrokerExposingData;

        } catch (final CantRequestProfileListException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoBrokersException(e, "", "Problem trying to request list of registered components in communication layer.");

        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoBrokersException(e, "", "Unhandled error.");
        }
    }

    @Override
    public List<CryptoBrokerExposingData> getResult(final Integer max) throws CantListCryptoBrokersException {
        return null;
    }
}
