package com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.exceptions.CantExposeIdentitiesException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.exceptions.CantExposeIdentityException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.interfaces.CryptoCustomerManager;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.interfaces.CryptoCustomerSearch;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.utils.CryptoCustomerExposingData;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_customer.developer.bitdubai.version_1.CryptoCustomerActorNetworkServicePluginRootTest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.ActorAlreadyRegisteredException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.CantRegisterActorException;

import java.util.Collection;

/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerActorNetworkServiceManager</code>
 * is the manager of the plug-in crypto customer actor network service of the cbp platform.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/11/2015.
 */
public final class CryptoCustomerActorNetworkServiceManagerTest implements CryptoCustomerManager {

    private final CryptoCustomerActorNetworkServicePluginRootTest pluginRoot;
    private final ErrorManager                   errorManager                  ;
    private final PluginVersionReference         pluginVersionReference        ;

    public CryptoCustomerActorNetworkServiceManagerTest(final CryptoCustomerActorNetworkServicePluginRootTest pluginRoot,
                                                    final ErrorManager errorManager,
                                                    final PluginVersionReference pluginVersionReference) {

        this.pluginRoot = pluginRoot;
        this.errorManager                   = errorManager                  ;
        this.pluginVersionReference         = pluginVersionReference        ;
    }

    @Override
    public final void exposeIdentity(final CryptoCustomerExposingData cryptoCustomer) throws CantExposeIdentityException {

        try {

            pluginRoot.registerActor(
                    cryptoCustomer.getPublicKey(),
                    cryptoCustomer.getAlias(),
                    cryptoCustomer.getAlias(),
                    null,
                    null,
                    Actors.CBP_CRYPTO_CUSTOMER,
                    cryptoCustomer.getImage(),
                    0,0
            );

        } catch (final ActorAlreadyRegisteredException| CantRegisterActorException e) {

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantExposeIdentityException(e, null, "Problem trying to register an identity component.");

        } catch (final Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
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
                    null,
                    null,
                    actor.getImage()
            );
        }catch (Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantExposeIdentityException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public final void exposeIdentities(final Collection<CryptoCustomerExposingData> cryptoCustomerExposingDataList) throws CantExposeIdentitiesException {

        try {

            System.out.println("************** im in the network service exposing the identities: quantity:"+cryptoCustomerExposingDataList.size());

            for (final CryptoCustomerExposingData cryptoCustomer : cryptoCustomerExposingDataList)
                this.exposeIdentity(cryptoCustomer);

        } catch (final CantExposeIdentityException e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantExposeIdentitiesException(e, null, "Problem trying to expose an identity.");
        } catch (final Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantExposeIdentitiesException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public final CryptoCustomerSearch getSearch() {
        return new CryptoCustomerActorNetworkServiceSearchTest(pluginRoot, errorManager, pluginVersionReference);
    }

}
