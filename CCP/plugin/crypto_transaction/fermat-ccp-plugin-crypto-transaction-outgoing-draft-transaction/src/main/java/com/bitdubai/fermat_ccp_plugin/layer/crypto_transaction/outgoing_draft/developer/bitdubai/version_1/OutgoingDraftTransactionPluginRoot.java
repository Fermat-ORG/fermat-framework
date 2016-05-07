package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantStoreBitcoinTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.bitcoin_vault.CryptoVaultManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.transactions.DraftTransaction;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CantGetDraftTransactionException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_draft.OutgoingDraftManager;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.database.OutgoingDraftTransactionDao;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.database.OutgoingDraftTransactionDatabaseConstants;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.database.OutgoingDraftTransactionDeveloperDatabaseFactory;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.CantInitializeOutgoingIntraActorDaoException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.util.OutgoingDraftTransactionWrapper;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * * * * * * *
 * * *
 */

public class OutgoingDraftTransactionPluginRoot extends AbstractPlugin implements
        DatabaseManagerForDevelopers,
        OutgoingDraftManager {

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.BASIC_WALLET, plugin = Plugins.BITCOIN_WALLET)
    private BitcoinWalletManager bitcoinWalletManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS, layer = Layers.CRYPTO_VAULT, plugin = Plugins.BITCOIN_VAULT)
    private CryptoVaultManager cryptoVaultManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_BROADCASTER_SYSTEM)
    private Broadcaster broadcaster;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS, layer = Layers.CRYPTO_NETWORK, plugin = Plugins.BITCOIN_NETWORK)
    private BitcoinNetworkManager bitcoinNetworkManager;

    private com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.structure.OutgoingDraftTransactionAgent outgoingDraftTransactionAgent;


    private OutgoingDraftTransactionDao outgoingDraftTransactionDao;

    public OutgoingDraftTransactionPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }


    /*
     * DatabaseManagerForDevelopers interface implementation
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return OutgoingDraftTransactionDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory, pluginId);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return OutgoingDraftTransactionDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        Database database;
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, OutgoingDraftTransactionDatabaseConstants.OUTGOING_DRAFT_DATABASE_NAME);
            return OutgoingDraftTransactionDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, database, developerDatabaseTable);
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            FermatException e = new CantDeliverPendingTransactionsException("I can't open database", cantOpenDatabaseException, "WalletId: " + developerDatabase.getName(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (DatabaseNotFoundException databaseNotFoundException) {
            FermatException e = new CantDeliverPendingTransactionsException("Database does not exists", databaseNotFoundException, "WalletId: " + developerDatabase.getName(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (Exception e) {
            FermatException e1 = new CantDeliverPendingTransactionsException("Unexpected Exception", e, "", "");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e1);
        }
        // If we are here the database could not be opened, so we return an empry list
        return new ArrayList<>();
    }

    /**
     * Service Interface implementation.
     */
    @Override
    public void start() throws CantStartPluginException {
        try {

            outgoingDraftTransactionDao = new OutgoingDraftTransactionDao(
                    errorManager,
                    pluginDatabaseSystem,
                    cryptoVaultManager);
            outgoingDraftTransactionDao.initialize(pluginId);


            outgoingDraftTransactionAgent = new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.structure.OutgoingDraftTransactionAgent(
                    errorManager,
                    cryptoVaultManager,
                    bitcoinNetworkManager,
                    bitcoinWalletManager,
                    outgoingDraftTransactionDao,
                    null, // transmission not used now
                    eventManager,
                    broadcaster
            );
            outgoingDraftTransactionAgent.start();

        } catch (CantInitializeOutgoingIntraActorDaoException e) {
            e.printStackTrace();
        }

        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {

        outgoingDraftTransactionAgent.stop();
        this.serviceStatus = ServiceStatus.STOPPED;
    }


    @Override
    public void addInputsToDraftTransaction(UUID requestId, DraftTransaction draftTransaction, long valueToSend, CryptoAddress addressTo, String walletPublicKey, ReferenceWallet referenceWallet, String memo, String actorToPublicKey, Actors actorToType, String actorFromPublicKey, Actors actorFromType, BlockchainNetworkType blockchainNetworkType) {
        try {
            cryptoVaultManager.saveTransaction(draftTransaction);

            outgoingDraftTransactionDao.registerNewTransaction(
                    requestId,
                    draftTransaction.getTxHash(),
                    walletPublicKey,
                    addressTo,
                    valueToSend,
                    null,
                    memo,
                    actorFromPublicKey,
                    actorFromType,
                    actorToPublicKey,
                    actorToType,
                    referenceWallet,
                    false,
                    blockchainNetworkType);

            if (!outgoingDraftTransactionAgent.isRunning()) {
                outgoingDraftTransactionAgent.start();
            }

        } catch (com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantInsertRecordException | CantStoreBitcoinTransactionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public DraftTransaction getPending(UUID requestId) {
        DraftTransaction draftTransaction;
        try {
            OutgoingDraftTransactionWrapper outgoingDraftTransactionWrapper = outgoingDraftTransactionDao.getTransaction(requestId);
            draftTransaction = cryptoVaultManager.getDraftTransaction(outgoingDraftTransactionWrapper.getBlockchainNetworkType(), outgoingDraftTransactionWrapper.getTxHash());
        } catch (CantLoadTableToMemoryException | InvalidParameterException | CantGetDraftTransactionException e) {
            return null;
        }
        return draftTransaction;
    }

    @Override
    public void markRead(UUID requestId) {
        outgoingDraftTransactionDao.markReadTransaction(requestId);
    }
}
