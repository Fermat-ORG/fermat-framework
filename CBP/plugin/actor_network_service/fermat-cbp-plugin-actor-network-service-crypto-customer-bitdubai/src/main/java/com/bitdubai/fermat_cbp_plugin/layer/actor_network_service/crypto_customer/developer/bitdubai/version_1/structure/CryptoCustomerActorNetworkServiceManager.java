package com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_customer.developer.bitdubai.version_1.structure;

import android.util.Base64;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.exceptions.CantExposeIdentitiesException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.exceptions.CantExposeIdentityException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.interfaces.CryptoCustomerManager;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.interfaces.CryptoCustomerSearch;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.utils.CryptoCustomerExposingData;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRegisterComponentException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerActorNetworkServiceManager</code>
 * is the manager of the plug-in crypto customer actor network service of the cbp platform.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/11/2015.
 */
public final class CryptoCustomerActorNetworkServiceManager implements CryptoCustomerManager {

    private final CommunicationsClientConnection communicationsClientConnection;
    private final ErrorManager                   errorManager                  ;
    private final PluginVersionReference         pluginVersionReference        ;


    private PlatformComponentProfile platformComponentProfile;

    public CryptoCustomerActorNetworkServiceManager(final CommunicationsClientConnection communicationsClientConnection,
                                                    final ErrorManager errorManager,
                                                    final PluginVersionReference pluginVersionReference) {

        this.communicationsClientConnection = communicationsClientConnection;
        this.errorManager                   = errorManager                  ;
        this.pluginVersionReference         = pluginVersionReference        ;
    }

    private ConcurrentHashMap<String, CryptoCustomerExposingData> cryptoCustomersToExpose;

    @Override
    public final void exposeIdentity(final CryptoCustomerExposingData cryptoCustomer) throws CantExposeIdentityException {

        try {

            if (!isRegistered()) {

                addCryptoCustomerToExpose(cryptoCustomer);

            } else {

                final String imageString = Base64.encodeToString(cryptoCustomer.getImage(), Base64.DEFAULT);

                final PlatformComponentProfile actorPlatformComponentProfile = communicationsClientConnection.constructPlatformComponentProfileFactory(
                        cryptoCustomer.getPublicKey(),
                        (cryptoCustomer.getAlias()),
                        (cryptoCustomer.getAlias().toLowerCase() + "_" + platformComponentProfile.getName().replace(" ", "_")),
                        NetworkServiceType.UNDEFINED,
                        PlatformComponentType.ACTOR_CRYPTO_CUSTOMER,
                        imageString
                );

                communicationsClientConnection.registerComponentForCommunication(platformComponentProfile.getNetworkServiceType(), actorPlatformComponentProfile);

                if (cryptoCustomersToExpose != null && cryptoCustomersToExpose.containsKey(cryptoCustomer.getPublicKey()))
                    cryptoCustomersToExpose.remove(cryptoCustomer.getPublicKey());
            }

        } catch (final CantRegisterComponentException e) {

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
            if (isRegistered()) {

                final String imageString = Base64.encodeToString(actor.getImage(), Base64.DEFAULT);


                final PlatformComponentProfile platformComponentProfile = communicationsClientConnection.constructPlatformComponentProfileFactory(
                        actor.getPublicKey(),
                        (actor.getAlias()),
                        (actor.getAlias().toLowerCase() + "_" + this.platformComponentProfile.getName().replace(" ", "_")),
                        NetworkServiceType.UNDEFINED,
                        PlatformComponentType.ACTOR_CRYPTO_CUSTOMER,
                        imageString);

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            communicationsClientConnection.updateRegisterActorProfile(platformComponentProfile.getNetworkServiceType(), platformComponentProfile);
                        } catch (CantRegisterComponentException e) {
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();
            }
        }catch (Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantExposeIdentityException(e, null, "Unhandled Exception.");
        }
    }

    private void addCryptoCustomerToExpose(final CryptoCustomerExposingData cryptoCustomerExposingData) {

        if (cryptoCustomersToExpose == null)
            cryptoCustomersToExpose = new ConcurrentHashMap<>();

        cryptoCustomersToExpose.putIfAbsent(cryptoCustomerExposingData.getPublicKey(), cryptoCustomerExposingData);
    }

    @Override
    public final void exposeIdentities(final Collection<CryptoCustomerExposingData> cryptoCustomerExposingDataList) throws CantExposeIdentitiesException {

        try {

            System.out.println("************** im in the network service exposing the identities: quantitty:"+cryptoCustomerExposingDataList.size());

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

    private boolean isRegistered() {
        return platformComponentProfile != null;
    }

    public final void setPlatformComponentProfile(final PlatformComponentProfile platformComponentProfile) {

        this.platformComponentProfile = platformComponentProfile;

        if (platformComponentProfile != null && cryptoCustomersToExpose != null && !cryptoCustomersToExpose.isEmpty()) {

            try {

                this.exposeIdentities(cryptoCustomersToExpose.values());

            } catch (final CantExposeIdentitiesException e){

                errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            }
        }
    }

    @Override
    public final CryptoCustomerSearch getSearch() {
        return new CryptoCustomerActorNetworkServiceSearch(communicationsClientConnection, errorManager, pluginVersionReference);
    }

}
