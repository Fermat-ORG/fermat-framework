package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1;

/**
 * Created by ciencias on 2/16/15.
 */

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
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletManager;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.interfaces.CryptoTransmissionNetworkServiceManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.incoming_intra_user.IncomingIntraUserManager;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.database.IncomingIntraUserTransactionDeveloperDatabaseFactory;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.event_handlers.IncomingCryptoMetadataEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.CantInitializeIncomingIntraUserCryptoRegistryException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.CantStartIncomingIntraUserEventRecorderServiceException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.CantStartIncomingIntraUserRelayAgentException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.CantStartIntraUserCryptoMonitorAgentException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.structure.IncomingIntraUserCryptoMonitorAgent;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.structure.IncomingIntraUserEventRecorderService;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.structure.IncomingIntraUserMetadataMonitorAgent;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.structure.IncomingIntraUserRegistry;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.structure.IncomingIntraUserRelayAgent;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_bch_api.layer.crypto_router.incoming_crypto.IncomingCryptoManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.List;

/**
 * This plugin handles Intra User transactions, meaning transactions happening between users of the platform in both ends.
 * <p/>
 * One of the reasons for this plugin to exist is that a user can send money to another without a payment request at all.
 * In this case when the transaction is received by the payed user, someone has to decide to which wallet to send it.
 * <p/>
 * As this plugin is  monitoring all User to User transactions, it is the one perfect for the job of deciding where to
 * send the payment received.
 * <p/>
 * It can also process queries of all such transactions that happened in the past.
 * <p/>
 * * * * *
 */

@PluginInfo(createdBy = "Luis", maintainerMail = "nattyco@gmail.com", platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.DESKTOP_MODULE, plugin = Plugins.WALLET_MANAGER)

public class IncomingIntraUserTransactionPluginRoot extends AbstractPlugin
        implements DatabaseManagerForDevelopers,
       IncomingIntraUserManager {

    private final List<FermatEventListener> listenersAdded = new ArrayList<>();
    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.BASIC_WALLET, plugin = Plugins.BITCOIN_WALLET)
    private BitcoinWalletManager bitcoinWalletManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;
    @NeededPluginReference(platform = Platforms.BLOCKCHAINS, layer = Layers.CRYPTO_ROUTER, plugin = Plugins.INCOMING_CRYPTO)
    private IncomingCryptoManager incomingCryptoManager;
    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;
    @NeededPluginReference(platform = Platforms.BLOCKCHAINS, layer = Layers.CRYPTO_MODULE, plugin = Plugins.CRYPTO_ADDRESS_BOOK)
    private CryptoAddressBookManager cryptoAddressBookManager;
    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.NETWORK_SERVICE, plugin = Plugins.CRYPTO_TRANSMISSION)
    private CryptoTransmissionNetworkServiceManager cryptoTransmissionNetworkServiceManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_BROADCASTER_SYSTEM)
    private Broadcaster broadcaster;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.BASIC_WALLET, plugin = Plugins.LOSS_PROTECTED_WALLET)
    private BitcoinLossProtectedWalletManager lossProtectedWalletManager;
    /*
     * Incoming Intra User member variables
     */
    private IncomingIntraUserRegistry registry;
    private IncomingIntraUserRelayAgent relayAgent;
    private IncomingIntraUserCryptoMonitorAgent cryptoMonitorAgent;
    private IncomingIntraUserMetadataMonitorAgent metadataMonitorAgent;
    private IncomingIntraUserEventRecorderService eventRecorderService;

    public IncomingIntraUserTransactionPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    /*
     * DatabaseManagerForDevelopers methods implementation
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        try {
            IncomingIntraUserTransactionDeveloperDatabaseFactory databaseFactory = new IncomingIntraUserTransactionDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
            return databaseFactory.getDatabaseList(developerObjectFactory);
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            return new ArrayList<>();
        }
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        try {
            IncomingIntraUserTransactionDeveloperDatabaseFactory databaseFactory = new IncomingIntraUserTransactionDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
            return databaseFactory.getDatabaseTableList(developerObjectFactory);
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            return new ArrayList<>();
        }
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        try {
            IncomingIntraUserTransactionDeveloperDatabaseFactory dbFactory = new IncomingIntraUserTransactionDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
            dbFactory.initializeDatabase();
            return dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            return new ArrayList<>();
        }
    }

    /*
     * Service methods implementation
     */
    @Override
    public void start() throws CantStartPluginException {

        try {
            this.registry = new IncomingIntraUserRegistry(this.pluginDatabaseSystem);
            this.registry.initialize(this.pluginId);



            /**
             * I ask the list of pending requests to the Network Service to execute
             */



        } catch (CantInitializeIncomingIntraUserCryptoRegistryException e) {
            reportError( UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException("Registry could not be initialized", e, "", "");
        } catch (Exception e) {
            reportError( UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
            throw new CantStartPluginException("An unexpected exception happened", FermatException.wrapException(e), "", "");
        }

        try {
            this.eventRecorderService = new IncomingIntraUserEventRecorderService(this.eventManager, this.registry);
            eventRecorderService.start();
        } catch (CantStartIncomingIntraUserEventRecorderServiceException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException("Event Recorder Service could not be initialized", e, "", "");
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
            throw new CantStartPluginException("An unexpected exception happened", FermatException.wrapException(e), "", "");
        }

        try {
            this.relayAgent = new IncomingIntraUserRelayAgent(getErrorManager(), this.eventManager, this.bitcoinWalletManager, this.cryptoAddressBookManager, this.registry, this.cryptoTransmissionNetworkServiceManager,broadcaster,lossProtectedWalletManager);
            this.relayAgent.start();
        } catch (CantStartIncomingIntraUserRelayAgentException e) {
            this.eventRecorderService.stop();
            reportError( UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException("Relay Agent could not be initialized", e, "", "");
        } catch (Exception e) {
            this.eventRecorderService.stop();
            reportError( UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
            throw new CantStartPluginException("An unexpected exception happened", FermatException.wrapException(e), "", "");
        }

        try {
            this.cryptoMonitorAgent = new IncomingIntraUserCryptoMonitorAgent(getErrorManager(), this.incomingCryptoManager, this.registry);
            this.cryptoMonitorAgent.start();
        } catch (CantStartIntraUserCryptoMonitorAgentException e) {
            this.relayAgent.stop();
            this.eventRecorderService.stop();
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException("Crypto Monitor Agent could not be initialized", e, "", "");
        } catch (Exception e) {
            this.relayAgent.stop();
            this.eventRecorderService.stop();
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
            throw new CantStartPluginException("An unexpected exception happened", FermatException.wrapException(e), "", "");
        }

        try {
            this.metadataMonitorAgent = new IncomingIntraUserMetadataMonitorAgent(getErrorManager(), this.cryptoTransmissionNetworkServiceManager, this.registry);
            metadataMonitorAgent.start();
        } catch (CantStartIntraUserCryptoMonitorAgentException e) {
            this.relayAgent.stop();
            this.cryptoMonitorAgent.stop();
            this.eventRecorderService.stop();
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException("Metadata Monitor Agent could not be initialized", e, "", "");
        } catch (Exception e) {
            this.relayAgent.stop();
            this.cryptoMonitorAgent.stop();
            this.eventRecorderService.stop();
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
            throw new CantStartPluginException("An unexpected exception happened", FermatException.wrapException(e), "", "");
        }

        /**
         * Listener NetWorkService New Notifications event
         */

        FermatEventListener fermatEventListener;
        FermatEventHandler fermatEventHandler;

        fermatEventListener = eventManager.getNewListener(com.bitdubai.fermat_ccp_api.layer.platform_service.event_manager.enums.EventType.INCOMING_CRYPTO_METADATA);
        fermatEventHandler = new IncomingCryptoMetadataEventHandler(eventRecorderService);

        fermatEventListener.setEventHandler(fermatEventHandler);

        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        this.relayAgent.stop();
        this.cryptoMonitorAgent.stop();
        this.metadataMonitorAgent.stop();
        this.eventRecorderService.stop();
        this.serviceStatus = ServiceStatus.STOPPED;
    }




}
