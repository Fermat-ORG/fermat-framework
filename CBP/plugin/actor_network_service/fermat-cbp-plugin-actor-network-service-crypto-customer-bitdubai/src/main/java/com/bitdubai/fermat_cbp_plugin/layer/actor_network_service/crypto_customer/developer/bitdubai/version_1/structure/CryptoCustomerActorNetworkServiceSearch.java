package com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.exceptions.CantListCryptoCustomersException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.interfaces.CryptoCustomerSearch;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.utils.CryptoCustomerExposingData;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_customer.developer.bitdubai.version_1.CryptoCustomerActorNetworkServicePluginRoot;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantRequestProfileListException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.DiscoveryQueryParameters;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;

import java.util.ArrayList;
import java.util.List;

/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerActorNetworkServiceSearch</code>
 * contains all the functionality to search crypto customer actors.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 25/11/2015.
 */
public final class CryptoCustomerActorNetworkServiceSearch extends CryptoCustomerSearch {

    private final CryptoCustomerActorNetworkServicePluginRoot pluginRoot;

    public CryptoCustomerActorNetworkServiceSearch(final CryptoCustomerActorNetworkServicePluginRoot pluginRoot) {

        this.pluginRoot = pluginRoot;
    }

    @Override
    public List<CryptoCustomerExposingData> getResult(Integer max, Integer offSet) throws CantListCryptoCustomersException {

        try {

            DiscoveryQueryParameters discoveryQueryParameters = new DiscoveryQueryParameters(
                    Actors.CBP_CRYPTO_CUSTOMER.getCode(),
                    null,
                    null,
                    null,
                    null,
                    null,
                    max,
                    null,
                    NetworkServiceType.UNDEFINED,
                    offSet,
                    NetworkServiceType.CRYPTO_CUSTOMER
            );

            final List<ActorProfile> list = pluginRoot.getConnection().listRegisteredActorProfiles(discoveryQueryParameters);

            final List<CryptoCustomerExposingData> cryptoCustomerExposingDataList = new ArrayList<>();

            for (final ActorProfile actorProfile : list) {

                System.out.println(new StringBuilder().append("************** I'm a crypto customer searched: ").append(actorProfile).toString());
                System.out.println(new StringBuilder().append("************** Do I have profile image?: ").append(actorProfile.getPhoto() != null).toString());

                cryptoCustomerExposingDataList.add(new CryptoCustomerExposingData(actorProfile.getIdentityPublicKey(), actorProfile.getAlias(), actorProfile.getPhoto(), actorProfile.getLocation(), 0, 0, actorProfile.getStatus()));
            }

            return cryptoCustomerExposingDataList;

        } catch (final CantRequestProfileListException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoCustomersException(e, "", "Problem trying to request list of registered components in communication layer.");

        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoCustomersException(e, "", "Unhandled error.");
        }
    }

    @Override
    public List<CryptoCustomerExposingData> getResult(String publicKey, DeviceLocation deviceLocation, double distance, String alias, Integer max, Integer offSet) throws CantListCryptoCustomersException {

        try {

            DiscoveryQueryParameters discoveryQueryParameters = new DiscoveryQueryParameters(
                    null, //TODO: Se coloco null ya que leon necesita que esta valor null porque esto solo se usa solo para buscar por publicKey del Actor
                    NetworkServiceType.UNDEFINED,
                    Actors.CBP_CRYPTO_CUSTOMER.getCode(),
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

//            DiscoveryQueryParameters discoveryQueryParameters = new DiscoveryQueryParameters(
//                    Actors.CBP_CRYPTO_CUSTOMER.getCode(),
//                    alias,
//                    distance,
//                    null,
//                    //TODO: Se coloco null ya que leon necesita que esta valor null ya que solo esto se usa solo para buscar por publicKey del Actor
//                    null,///publicKey,
//                    deviceLocation,
//                    max,
//                    null,
//                    NetworkServiceType.UNDEFINED,
//                    offSet,
//                    NetworkServiceType.CRYPTO_CUSTOMER
//            );

            final List<ActorProfile> list = pluginRoot.getConnection().listRegisteredActorProfiles(discoveryQueryParameters);

            final List<CryptoCustomerExposingData> cryptoCustomerExposingDataList = new ArrayList<>();

            for (final ActorProfile actorProfile : list) {

                System.out.println(new StringBuilder().append("************** I'm a crypto customer searched: ").append(actorProfile).append(" - ").append(actorProfile.getStatus()).toString());
                System.out.println(new StringBuilder().append("************** Do I have profile image?: ").append(actorProfile.getPhoto() != null).toString());

                cryptoCustomerExposingDataList.add(new CryptoCustomerExposingData(actorProfile.getIdentityPublicKey(), actorProfile.getAlias(), actorProfile.getPhoto(), actorProfile.getLocation(), 0, 0, actorProfile.getStatus()));
            }

            return cryptoCustomerExposingDataList;

        } catch (final CantRequestProfileListException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoCustomersException(e, "", "Problem trying to request list of registered components in communication layer.");

        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoCustomersException(e, "", "Unhandled error.");
        }
    }

    @Override
    public List<CryptoCustomerExposingData> getResultLocation(DeviceLocation deviceLocation, Integer max, Integer offSet) throws CantListCryptoCustomersException {

        try {

            DiscoveryQueryParameters discoveryQueryParameters = new DiscoveryQueryParameters(
                    Actors.CBP_CRYPTO_CUSTOMER.getCode(),
                    null,
                    null,
                    null,
                    null,
                    deviceLocation,
                    max,
                    null,
                    NetworkServiceType.UNDEFINED,
                    offSet,
                    NetworkServiceType.CRYPTO_CUSTOMER
            );

            final List<ActorProfile> list = pluginRoot.getConnection().listRegisteredActorProfiles(discoveryQueryParameters);

            final List<CryptoCustomerExposingData> cryptoCustomerExposingDataList = new ArrayList<>();

            for (final ActorProfile actorProfile : list) {

                System.out.println(new StringBuilder().append("************** I'm a crypto customer searched: ").append(actorProfile).toString());
                System.out.println(new StringBuilder().append("************** Do I have profile image?: ").append(actorProfile.getPhoto() != null).toString());

                cryptoCustomerExposingDataList.add(new CryptoCustomerExposingData(actorProfile.getIdentityPublicKey(), actorProfile.getAlias(), actorProfile.getPhoto(), actorProfile.getLocation(), 0, 0, actorProfile.getStatus()));
            }

            return cryptoCustomerExposingDataList;

        } catch (final CantRequestProfileListException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoCustomersException(e, "", "Problem trying to request list of registered components in communication layer.");

        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoCustomersException(e, "", "Unhandled error.");
        }
    }

    @Override
    public List<CryptoCustomerExposingData> getResultDistance(double distance, Integer max, Integer offSet) throws CantListCryptoCustomersException {

        try {

            DiscoveryQueryParameters discoveryQueryParameters = new DiscoveryQueryParameters(
                    Actors.CBP_CRYPTO_CUSTOMER.getCode(),
                    null,
                    distance,
                    null,
                    null,
                    null,
                    max,
                    null,
                    NetworkServiceType.UNDEFINED,
                    offSet,
                    NetworkServiceType.CRYPTO_CUSTOMER
            );

            final List<ActorProfile> list = pluginRoot.getConnection().listRegisteredActorProfiles(discoveryQueryParameters);

            final List<CryptoCustomerExposingData> cryptoCustomerExposingDataList = new ArrayList<>();

            for (final ActorProfile actorProfile : list) {

                System.out.println(new StringBuilder().append("************** I'm a crypto customer searched: ").append(actorProfile).toString());
                System.out.println(new StringBuilder().append("************** Do I have profile image?: ").append(actorProfile.getPhoto() != null).toString());

                cryptoCustomerExposingDataList.add(new CryptoCustomerExposingData(actorProfile.getIdentityPublicKey(), actorProfile.getAlias(), actorProfile.getPhoto(), actorProfile.getLocation(), 0, 0, actorProfile.getStatus()));
            }

            return cryptoCustomerExposingDataList;

        } catch (final CantRequestProfileListException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoCustomersException(e, "", "Problem trying to request list of registered components in communication layer.");

        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoCustomersException(e, "", "Unhandled error.");
        }
    }

    @Override
    public List<CryptoCustomerExposingData> getResultAlias(String alias, Integer max, Integer offSet) throws CantListCryptoCustomersException {

        try {

            DiscoveryQueryParameters discoveryQueryParameters = new DiscoveryQueryParameters(
                    Actors.CBP_CRYPTO_CUSTOMER.getCode(),
                    alias,
                    null,
                    null,
                    null,
                    null,
                    max,
                    null,
                    NetworkServiceType.UNDEFINED,
                    offSet,
                    NetworkServiceType.CRYPTO_CUSTOMER
            );

            final List<ActorProfile> list = pluginRoot.getConnection().listRegisteredActorProfiles(discoveryQueryParameters);

            final List<CryptoCustomerExposingData> cryptoCustomerExposingDataList = new ArrayList<>();

            for (final ActorProfile actorProfile : list) {

                System.out.println(new StringBuilder().append("************** I'm a crypto customer searched: ").append(actorProfile).toString());
                System.out.println(new StringBuilder().append("************** Do I have profile image?: ").append(actorProfile.getPhoto() != null).toString());

                cryptoCustomerExposingDataList.add(new CryptoCustomerExposingData(actorProfile.getIdentityPublicKey(), actorProfile.getAlias(), actorProfile.getPhoto(), actorProfile.getLocation(), 0, 0, actorProfile.getStatus()));
            }

            return cryptoCustomerExposingDataList;

        } catch (final CantRequestProfileListException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoCustomersException(e, "", "Problem trying to request list of registered components in communication layer.");

        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoCustomersException(e, "", "Unhandled error.");
        }
    }

    @Override
    public CryptoCustomerExposingData getResult(String publicKey) throws CantListCryptoCustomersException {
        try {

            DiscoveryQueryParameters discoveryQueryParameters = new DiscoveryQueryParameters(
                    //TODO:Hay que pasarle null porque no esta implementado de esa forma en p2p
                    null,//Actors.CBP_CRYPTO_CUSTOMER.getCode(),
                    null,
                    null,
                    null,
                    publicKey,
                    null,
                    null,
                    null,
                    NetworkServiceType.UNDEFINED,
                    null,
                    NetworkServiceType.CRYPTO_CUSTOMER
            );

            final List<ActorProfile> list = pluginRoot.getConnection().listRegisteredActorProfiles(discoveryQueryParameters);

            CryptoCustomerExposingData cryptoCustomerExposingData = null;

            for (final ActorProfile actorProfile : list) {

                System.out.println(new StringBuilder().append("************** I'm a crypto customer searched: ").append(actorProfile).toString());
                System.out.println(new StringBuilder().append("************** Do I have profile image?: ").append(actorProfile.getPhoto() != null).toString());

                cryptoCustomerExposingData = new CryptoCustomerExposingData(actorProfile.getIdentityPublicKey(), actorProfile.getAlias(), actorProfile.getPhoto(), actorProfile.getLocation(), 0, 0, actorProfile.getStatus());
            }

            return cryptoCustomerExposingData;

        } catch (final CantRequestProfileListException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoCustomersException(e, "", "Problem trying to request list of registered components in communication layer.");

        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoCustomersException(e, "", "Unhandled error.");
        }
    }

    @Override
    public List<CryptoCustomerExposingData> getResult(final Integer max) throws CantListCryptoCustomersException {
        return null;
    }
}
