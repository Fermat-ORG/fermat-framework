package com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_ccp_api.all_definition.enums.EventType;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.interfaces.CryptoPaymentRequestManager;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.interfaces.DealsWithCryptoPaymentRequestNetworkService;
import com.bitdubai.fermat_ccp_api.layer.transaction.outgoing.intra_actor.interfaces.DealsWithOutgoingIntraActor;
import com.bitdubai.fermat_ccp_api.layer.transaction.outgoing.intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.event_handlers.CryptoPaymentRequestApprovedEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.event_handlers.CryptoPaymentRequestDeniedEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.event_handlers.CryptoPaymentRequestRefusedEventHandler;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The plugin <code>Crypto Payment</code> of <code>Request</code> is responsible for managing crypto payments request in
 * the platform.
 * To do that, it haves a database with all the information related to them and their history.
 *
 * Listen the events raised by Crypto Payment Request Network Service and its called by the modules to generate new requests.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 01/10/2015.
 */
public class CryptoPaymentRequestPluginRoot implements
        DealsWithCryptoPaymentRequestNetworkService,
        DealsWithErrors,
        DealsWithEvents,
        DealsWithOutgoingIntraActor,
        DealsWithPluginDatabaseSystem,
        Plugin,
        Service {

    /**
     * DealsWithCryptoPaymentRequestNetworkService Interface member variables
     */
    private CryptoPaymentRequestManager cryptoPaymentRequestManager;

    /*
     * DealsWithErrors Interface member variables.
     */
    private ErrorManager errorManager;

    /*
     * DealsWithEvents Interface member variables
     */
    private EventManager eventManager;
    private List<FermatEventListener> listenersAdded = new ArrayList<>();

    /**
     * DealsWithOutgoingIntraActor Interface member variables
     */
    private OutgoingIntraActorManager outgoingIntraActorManager;

    /*
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /*
     * Plugin Interface member variables.
     */
    private UUID pluginId;

    /*
     * Service Interface member variables.
     */
    private ServiceStatus serviceStatus = ServiceStatus.CREATED;

    /*
     * Service Interface implementation
     */
    @Override
    public void start() {

        //executePendingAddressExchangeRequests(cryptoAddressGenerationService);

        addCryptoPaymentRequestListener(
                EventType.CRYPTO_PAYMENT_APPROVED,
                new CryptoPaymentRequestApprovedEventHandler(this)
        );

        addCryptoPaymentRequestListener(
                EventType.CRYPTO_PAYMENT_DENIED,
                new CryptoPaymentRequestDeniedEventHandler(this)
        );

        addCryptoPaymentRequestListener(
                EventType.CRYPTO_PAYMENT_REFUSED,
                new CryptoPaymentRequestRefusedEventHandler(this)
        );

        this.serviceStatus = ServiceStatus.STARTED;
    }

    private void addCryptoPaymentRequestListener(FermatEventEnum fermatEventEnum, FermatEventHandler fermatEventHandler) {
        FermatEventListener fermatEventListener = eventManager.getNewListener(fermatEventEnum);
        fermatEventListener.setEventHandler(fermatEventHandler);
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);
    }

    @Override
    public void pause() {
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {

        for (FermatEventListener fermatEventListener : listenersAdded) {
            eventManager.removeListener(fermatEventListener);
        }
        listenersAdded.clear();

        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }


    private void reportUnexpectedException(Exception e) {
        this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_PAYMENT_REQUEST, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
    }

    @Override
    public void setCryptoPaymentRequestManager(CryptoPaymentRequestManager cryptoPaymentRequestManager) {
        this.cryptoPaymentRequestManager = cryptoPaymentRequestManager;
    }

    /*
         * DealsWithErrors Interface implementation
         */
    @Override
    public void setErrorManager(final ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /*
     * DealsWithEvents Interface implementation
     */
    @Override
    public void setEventManager(final EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public void setOutgoingIntraActorManager(OutgoingIntraActorManager outgoingIntraActorManager) {
        this.outgoingIntraActorManager = outgoingIntraActorManager;
    }

    /*
         * DealsWithPluginDatabaseSystem Interface implementation
         */
    @Override
    public void setPluginDatabaseSystem(final PluginDatabaseSystem pluginDatabaseSystemManager) {
        this.pluginDatabaseSystem = pluginDatabaseSystemManager;
    }

    /**
     * Plugin Interface implementation.
     */
    @Override
    public void setId(final UUID pluginId) {
        this.pluginId = pluginId;
    }

}
