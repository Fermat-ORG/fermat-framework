package com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_ccp_api.all_definition.enums.EventType;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.interfaces.CryptoPaymentRequestManager;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CantGetCryptoPaymentRegistryException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.interfaces.CryptoPaymentManager;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.interfaces.CryptoPaymentRegistry;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.database.CryptoPaymentRequestDeveloperDatabaseFactory;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.event_handlers.CryptoPaymentRequestNewsEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.event_handlers.IncomingIntraUserTransactionDebitEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.event_handlers.OutgoingIntraUserRollbackTransactionEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions.CantExecuteCryptoPaymentRequestPendingEventActionsException;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions.CantExecuteUnfinishedActionsException;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions.CantInitializeCryptoPaymentRequestEventActionsException;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions.CantInitializeCryptoPaymentRequestRegistryException;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.structure.CryptoPaymentRequestEventActions;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.structure.CryptoPaymentRequestRegistry;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;

import java.util.ArrayList;
import java.util.List;

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
        DatabaseManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER         )
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER         )
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM         , addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.NETWORK_SERVICE, plugin = Plugins.CRYPTO_PAYMENT_REQUEST)
    private CryptoPaymentRequestManager cryptoPaymentRequestManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.TRANSACTION    , plugin = Plugins.OUTGOING_INTRA_ACTOR  )
    private OutgoingIntraActorManager outgoingIntraActorManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.MIDDLEWARE     , plugin = Plugins.WALLET_MANAGER        )
    private WalletManagerManager walletManagerManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_BROADCASTER_SYSTEM)
    private Broadcaster broadcaster;

    private final List<FermatEventListener> listenersAdded;

    public CryptoPaymentRequestPluginRoot() {
        super(new PluginVersionReference(new Version()));

        listenersAdded = new ArrayList<>();
    }

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
                    pluginId,
                    broadcaster
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

        // executing pending event actions
        try {
            CryptoPaymentRequestEventActions eventActions = new CryptoPaymentRequestEventActions(
                    cryptoPaymentRequestManager,
                    pluginDatabaseSystem,
                    pluginId,
                    walletManagerManager,
                    eventManager,
                    broadcaster
            );

            eventActions.initialize();

            FermatEventListener fermatEventListener = eventManager.getNewListener(EventType.CRYPTO_PAYMENT_REQUEST_NEWS);
            fermatEventListener.setEventHandler(new CryptoPaymentRequestNewsEventHandler(this, eventActions));
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            /**
             * Listener Incoming Transaction credit notifications event
             */

            FermatEventHandler fermatEventHandler;

            fermatEventListener = eventManager.getNewListener(com.bitdubai.fermat_ccp_api.layer.platform_service.event_manager.enums.EventType.INCOMING_INTRA_USER_DEBIT_TRANSACTION);
            fermatEventHandler = new IncomingIntraUserTransactionDebitEventHandler(this);

            fermatEventListener.setEventHandler(fermatEventHandler);

            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            /**
             * Listener Outgoing Intra User Rollback transaction notifications event
             */
            fermatEventListener = eventManager.getNewListener(com.bitdubai.fermat_ccp_api.layer.platform_service.event_manager.enums.EventType.OUTGOING_INTRA_USER_ROLLBACK_TRANSACTION);
            fermatEventHandler = new OutgoingIntraUserRollbackTransactionEventHandler(this);

            fermatEventListener.setEventHandler(fermatEventHandler);

            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            eventActions.executePendingRequestEventActions();

            this.serviceStatus = ServiceStatus.STARTED;

        } catch(CantInitializeCryptoPaymentRequestEventActionsException     |
                CantExecuteCryptoPaymentRequestPendingEventActionsException e) {

            reportUnexpectedException(e);
            throw new CantStartPluginException(e, this.getPluginVersionReference());
        }

        // executing unfinished actions
        try {

            CryptoPaymentRequestRegistry cryptoPaymentRegistry = new CryptoPaymentRequestRegistry(
                    cryptoPaymentRequestManager,
                    errorManager,
                    outgoingIntraActorManager,
                    pluginDatabaseSystem,
                    pluginId,
                    broadcaster
            );

            cryptoPaymentRegistry.initialize();

            cryptoPaymentRegistry.executeUnfinishedActions();

        } catch(CantInitializeCryptoPaymentRequestRegistryException |
                CantExecuteUnfinishedActionsException               e) {

            reportUnexpectedException(e);
            throw new CantStartPluginException(e, this.getPluginVersionReference());
        }


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
        this.errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return new CryptoPaymentRequestDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseList(developerObjectFactory);

    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return new CryptoPaymentRequestDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        try {
            return new CryptoPaymentRequestDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<>();
        }
    }

}
