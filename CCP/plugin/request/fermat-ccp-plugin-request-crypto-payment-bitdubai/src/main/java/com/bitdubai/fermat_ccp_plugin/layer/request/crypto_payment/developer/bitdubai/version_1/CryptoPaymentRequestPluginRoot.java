package com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantGetFeatureForDevelopersException;
import com.bitdubai.fermat_api.layer.all_definition.common.interfaces.FeatureForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.DevelopersUtilReference;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_ccp_api.all_definition.enums.EventType;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.interfaces.CryptoPaymentRequestManager;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.interfaces.DealsWithCryptoPaymentRequestNetworkService;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CantGetCryptoPaymentRegistryException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.interfaces.CryptoPaymentManager;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.interfaces.CryptoPaymentRegistry;
import com.bitdubai.fermat_ccp_api.layer.transaction.outgoing_intra_actor.interfaces.DealsWithOutgoingIntraActor;
import com.bitdubai.fermat_ccp_api.layer.transaction.outgoing_intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.event_handlers.CryptoPaymentRequestApprovedEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.event_handlers.CryptoPaymentRequestDeniedEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.event_handlers.CryptoPaymentRequestReceivedEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.event_handlers.CryptoPaymentRequestRefusedEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions.CantExecuteCryptoPaymentRequestPendingEventActionsException;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions.CantExecuteUnfinishedActionsException;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions.CantInitializeCryptoPaymentRequestEventActionsException;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions.CantInitializeCryptoPaymentRequestRegistryException;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.structure.CryptoPaymentRequestEventActions;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.structure.CryptoPaymentRequestRegistry;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.DealsWithWalletManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;

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
public class CryptoPaymentRequestPluginRoot extends AbstractPlugin implements
        CryptoPaymentManager,
        DealsWithCryptoPaymentRequestNetworkService,
        DealsWithErrors,
        DealsWithEvents,
        DealsWithOutgoingIntraActor,
        DealsWithPluginDatabaseSystem,
        DealsWithWalletManager {

    @Override
    public List<AddonVersionReference> getNeededAddonReferences() {
        return new ArrayList<>();
    }

    @Override
    public List<PluginVersionReference> getNeededPluginReferences() {
        List<PluginVersionReference> rList = new ArrayList<>();

        rList.add(new PluginVersionReference(Platforms.CRYPTO_CURRENCY_PLATFORM, Layers.NETWORK_SERVICE, Plugins.CRYPTO_PAYMENT_REQUEST, Developers.BITDUBAI, new Version()));
        rList.add(new PluginVersionReference(Platforms.CRYPTO_CURRENCY_PLATFORM, Layers.TRANSACTION, Plugins.OUTGOING_INTRA_ACTOR, Developers.BITDUBAI, new Version()));

        return rList;
    }
    @Override
    public List<DevelopersUtilReference> getAvailableDeveloperUtils() {
        return new ArrayList<>();
    }

    @Override
    protected void validateAndAssignReferences() {

    }

    @Override
    public FeatureForDevelopers getFeatureForDevelopers(final DevelopersUtilReference developersUtilReference) throws CantGetFeatureForDevelopersException {
        return null;
    }

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
     * DealsWithWalletManager Interface member variables.
     */
    private WalletManagerManager walletManagerManager;

    /*
     * Service Interface member variables.
     */
    private ServiceStatus serviceStatus = ServiceStatus.CREATED;

    @Override
    public CryptoPaymentRegistry getCryptoPaymentRegistry() throws CantGetCryptoPaymentRegistryException {

        if(cryptoPaymentRequestManager    == null ||
                errorManager              == null ||
                outgoingIntraActorManager == null ||
                pluginDatabaseSystem      == null ||
                pluginId                  == null ) {

            String context =
                    "cryptoPaymentRequestManager: " + cryptoPaymentRequestManager +
                    "errorManager: "                + errorManager                +
                    "outgoingIntraActorManager: "   + outgoingIntraActorManager   +
                    "pluginDatabaseSystem: "        + pluginDatabaseSystem        +
                    "pluginId: "                    + pluginId                    ;

            FermatException fermatException = new CantGetCryptoPaymentRegistryException(
                    context,
                    "Not all the references injected correctly"
            );
            reportUnexpectedException(fermatException);
            throw new CantGetCryptoPaymentRegistryException(fermatException);
        }

        try {
            CryptoPaymentRequestRegistry cryptoPaymentRegistry = new CryptoPaymentRequestRegistry(
                    cryptoPaymentRequestManager,
                    errorManager,
                    outgoingIntraActorManager,
                    pluginDatabaseSystem,
                    pluginId
            );

            cryptoPaymentRegistry.initialize();

            return cryptoPaymentRegistry;

        } catch (CantInitializeCryptoPaymentRequestRegistryException e) {

            reportUnexpectedException(e);
            throw new CantGetCryptoPaymentRegistryException(e);
        }

    }

    /**
     * Service Interface implementation
     */
    @Override
    public void start() throws CantStartPluginException {

        // adding listeners to the events

        addCryptoPaymentRequestListener(EventType.CRYPTO_PAYMENT_REQUEST_APPROVED, new CryptoPaymentRequestApprovedEventHandler(cryptoPaymentRequestManager, this, pluginDatabaseSystem, pluginId));

        addCryptoPaymentRequestListener(EventType.CRYPTO_PAYMENT_REQUEST_DENIED, new CryptoPaymentRequestDeniedEventHandler  (cryptoPaymentRequestManager, this, pluginDatabaseSystem, pluginId));

        addCryptoPaymentRequestListener(EventType.CRYPTO_PAYMENT_REQUEST_RECEIVED, new CryptoPaymentRequestReceivedEventHandler(cryptoPaymentRequestManager, this, pluginDatabaseSystem, pluginId, walletManagerManager));

        addCryptoPaymentRequestListener(EventType.CRYPTO_PAYMENT_REQUEST_REFUSED, new CryptoPaymentRequestRefusedEventHandler (cryptoPaymentRequestManager, this, pluginDatabaseSystem, pluginId));

        // executing pending event actions
        try {
            CryptoPaymentRequestEventActions eventActions = new CryptoPaymentRequestEventActions(
                    cryptoPaymentRequestManager,
                    pluginDatabaseSystem,
                    pluginId,
                    walletManagerManager
            );

            eventActions.initialize();

            eventActions.executePendingRequestEventActions();

            this.serviceStatus = ServiceStatus.STARTED;

        } catch(CantInitializeCryptoPaymentRequestEventActionsException     |
                CantExecuteCryptoPaymentRequestPendingEventActionsException e) {

            reportUnexpectedException(e);
            throw new CantStartPluginException(e, Plugins.BITDUBAI_CCP_CRYPTO_PAYMENT_REQUEST);
        }

        // executing unfinished actions
        try {

            CryptoPaymentRequestRegistry cryptoPaymentRegistry = new CryptoPaymentRequestRegistry(
                    cryptoPaymentRequestManager,
                    errorManager,
                    outgoingIntraActorManager,
                    pluginDatabaseSystem,
                    pluginId
            );

            cryptoPaymentRegistry.initialize();

            cryptoPaymentRegistry.executeUnfinishedActions();

        } catch(CantInitializeCryptoPaymentRequestRegistryException |
                CantExecuteUnfinishedActionsException               e) {

            reportUnexpectedException(e);
            throw new CantStartPluginException(e, Plugins.BITDUBAI_CCP_CRYPTO_PAYMENT_REQUEST);
        }


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

    private void reportUnexpectedException(Exception e) {
        this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_PAYMENT_REQUEST, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
    }

    @Override
    public void setCryptoPaymentRequestManager(final CryptoPaymentRequestManager cryptoPaymentRequestManager) {
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
    public void setOutgoingIntraActorManager(final OutgoingIntraActorManager outgoingIntraActorManager) {
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

    /**
     * DealsWithWalletManager Interface implementation.
     */
    @Override
    public void setWalletManagerManager(final WalletManagerManager walletManagerManager) {
        this.walletManagerManager = walletManagerManager;
    }

}
