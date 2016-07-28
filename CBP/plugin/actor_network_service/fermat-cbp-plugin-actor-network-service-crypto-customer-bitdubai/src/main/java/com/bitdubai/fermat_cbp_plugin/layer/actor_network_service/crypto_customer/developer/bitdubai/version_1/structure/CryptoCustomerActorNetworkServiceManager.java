package com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.exceptions.CantExposeIdentitiesException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.exceptions.CantExposeIdentityException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.interfaces.CryptoCustomerManager;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.interfaces.CryptoCustomerSearch;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.utils.CryptoCustomerExposingData;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_customer.developer.bitdubai.version_1.CryptoCustomerActorNetworkServicePluginRoot;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.ActorAlreadyRegisteredException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.CantRegisterActorException;

import java.util.Collection;

/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerActorNetworkServiceManager</code>
 * is the manager of the plug-in crypto customer actor network service of the cbp platform.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/11/2015.
 */
public final class CryptoCustomerActorNetworkServiceManager implements CryptoCustomerManager {

    private final CryptoCustomerActorNetworkServicePluginRoot pluginRoot;

    public CryptoCustomerActorNetworkServiceManager(final CryptoCustomerActorNetworkServicePluginRoot pluginRoot) {

        this.pluginRoot = pluginRoot;
    }

    @Override
    public final void exposeIdentity(final CryptoCustomerExposingData cryptoCustomer) throws CantExposeIdentityException {

        try {

            pluginRoot.registerActor(
                    cryptoCustomer.getPublicKey(),
                    cryptoCustomer.getAlias(),
                    cryptoCustomer.getAlias(),
                    null,
                    cryptoCustomer.getLocation(),
                    Actors.CBP_CRYPTO_CUSTOMER,
                    cryptoCustomer.getImage(),
                    0, 0
            );

        } catch (final ActorAlreadyRegisteredException | CantRegisterActorException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantExposeIdentityException(e, null, "Problem trying to register an identity component.");

        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantExposeIdentityException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public void updateIdentity(CryptoCustomerExposingData actor) throws CantExposeIdentityException {
        try {

            pluginRoot.updateRegisteredActor(
                    actor.getPublicKey(),
                    actor.getAlias(),
                    actor.getAlias(),
                    actor.getLocation(),
                    null,
                    actor.getImage()
            );
        } catch (Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantExposeIdentityException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public final void exposeIdentities(final Collection<CryptoCustomerExposingData> cryptoCustomerExposingDataList) throws CantExposeIdentitiesException {

        try {

            System.out.println(new StringBuilder().append("************** im in the network service exposing the identities: quantity:").append(cryptoCustomerExposingDataList.size()).toString());

            for (final CryptoCustomerExposingData cryptoCustomer : cryptoCustomerExposingDataList)
                this.exposeIdentity(cryptoCustomer);

        } catch (final CantExposeIdentityException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantExposeIdentitiesException(e, null, "Problem trying to expose an identity.");
        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantExposeIdentitiesException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public final CryptoCustomerSearch getSearch() {
        return new CryptoCustomerActorNetworkServiceSearch(pluginRoot);
    }

}
