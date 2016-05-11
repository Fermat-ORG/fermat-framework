package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletManager;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.interfaces.CryptoTransmissionNetworkServiceManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.CantGetOutgoingIntraActorTransactionManagerException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorCantGetCryptoStatusException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.IntraActorCryptoTransactionManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.database.OutgoingIntraActorDao;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.database.OutgoingIntraActorTransactionDeveloperDatabaseFactory;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.exceptions.CantInitializeOutgoingIntraActorDaoException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.structure.OutgoingIntraActorTransactionManager;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.structure.OutgoingIntraActorTransactionProcessorAgent;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.util.OutgoingIntraActorTransactionHandlerFactory;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.bitcoin_vault.CryptoVaultManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.List;

/**
 * Created by loui on 20/02/15.
 */

@PluginInfo(createdBy = "Natalia Cortez", maintainerMail = "nattyco@gmail.com", platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.DESKTOP_MODULE, plugin = Plugins.WALLET_MANAGER)

public class OutgoingIntraActorTransactionPluginRoot extends AbstractPlugin implements
        DatabaseManagerForDevelopers,
        OutgoingIntraActorManager {


    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.BASIC_WALLET   , plugin = Plugins.BITCOIN_WALLET)
    private BitcoinWalletManager bitcoinWalletManager;


    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER         )
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM         , addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS        , layer = Layers.CRYPTO_VAULT   , plugin = Plugins.BITCOIN_VAULT)
    private CryptoVaultManager cryptoVaultManager;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS         , layer = Layers.CRYPTO_NETWORK  , plugin = Plugins.BITCOIN_NETWORK       )
    private BitcoinNetworkManager bitcoinNetworkManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM        , layer = Layers.NETWORK_SERVICE   , plugin = Plugins.CRYPTO_TRANSMISSION)
    private CryptoTransmissionNetworkServiceManager cryptoTransmissionNetworkServiceManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_BROADCASTER_SYSTEM)
    private Broadcaster broadcaster;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.BASIC_WALLET, plugin = Plugins.LOSS_PROTECTED_WALLET)
    private BitcoinLossProtectedWalletManager lossProtectedWalletManager;


    public OutgoingIntraActorTransactionPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    /*
     * OutgoingIntraUserTransaction member variables
     */
    private OutgoingIntraActorDao outgoingIntraActorDao;
    private OutgoingIntraActorTransactionProcessorAgent transactionProcessorAgent;
    private OutgoingIntraActorTransactionHandlerFactory transactionHandlerFactory;

    /**
     * DatabaseManagerForDevelopers implementation
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        OutgoingIntraActorTransactionDeveloperDatabaseFactory developerDatabaseFactory = new OutgoingIntraActorTransactionDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return developerDatabaseFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        OutgoingIntraActorTransactionDeveloperDatabaseFactory developerDatabaseFactory = new OutgoingIntraActorTransactionDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return developerDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        OutgoingIntraActorTransactionDeveloperDatabaseFactory developerDatabaseFactory = new OutgoingIntraActorTransactionDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return developerDatabaseFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
    }

    /*
     * OutgoingIntraActorManager Interface implementation
     */
    @Override
    public IntraActorCryptoTransactionManager getTransactionManager() throws CantGetOutgoingIntraActorTransactionManagerException {
        return new OutgoingIntraActorTransactionManager(this.pluginId,getErrorManager(),this.bitcoinWalletManager,this.pluginDatabaseSystem,lossProtectedWalletManager);
    }

    @Override
    public CryptoStatus getTransactionStatus(String transactionHash) throws OutgoingIntraActorCantGetCryptoStatusException {
        return this.outgoingIntraActorDao.getCryptoStatus(transactionHash);
    }

    /*
     * Service Interface implementation
     */
    @Override
    public void start() {
        try {
            this.outgoingIntraActorDao = new OutgoingIntraActorDao(getErrorManager(), this.pluginDatabaseSystem);
            this.outgoingIntraActorDao.initialize(this.pluginId);
            this.transactionHandlerFactory = new OutgoingIntraActorTransactionHandlerFactory(this.eventManager,this.bitcoinWalletManager, this.outgoingIntraActorDao,this.lossProtectedWalletManager);
            this.transactionProcessorAgent = new OutgoingIntraActorTransactionProcessorAgent(getErrorManager(),
                                                                                            this.cryptoVaultManager,
                                                                                            this.bitcoinNetworkManager,
                                                                                            this.bitcoinWalletManager,
                                                                                            this.outgoingIntraActorDao,
                                                                                            this.transactionHandlerFactory,
                                                                                            this.cryptoTransmissionNetworkServiceManager,
                                                                                            this.eventManager,
                                                                                            this.broadcaster,
                                                                                            this.lossProtectedWalletManager);
            this.transactionProcessorAgent.start();

            this.serviceStatus = ServiceStatus.STARTED;

        } catch (CantInitializeOutgoingIntraActorDaoException e) {
            reportUnexpectedException(e);
        } catch (Exception e) {
            reportUnexpectedException(FermatException.wrapException(e));
        }
    }

    private void reportUnexpectedException(Exception e) {
        reportError( UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
    }

    @Override
    public void stop() {
        this.transactionProcessorAgent.stop();

        this.serviceStatus = ServiceStatus.STOPPED;
    }

}
