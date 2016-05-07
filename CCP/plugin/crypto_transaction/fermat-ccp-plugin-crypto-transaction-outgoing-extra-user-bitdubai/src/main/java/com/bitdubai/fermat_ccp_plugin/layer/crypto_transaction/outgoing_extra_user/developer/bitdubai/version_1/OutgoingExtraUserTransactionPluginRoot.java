package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_extra_user.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_extra_user.OutgoingExtraUserManager;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_extra_user.TransactionManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.bitcoin_vault.CryptoVaultManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_extra_user.developer.bitdubai.version_1.developerUtils.OutgoingExtraUserDeveloperDatabaseFactory;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_extra_user.developer.bitdubai.version_1.exceptions.CantDeliverDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_extra_user.developer.bitdubai.version_1.structure.OutgoingExtraUserDatabaseConstants;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_extra_user.developer.bitdubai.version_1.structure.OutgoingExtraUserTransactionProcessorAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ezequiel.Postan on 20/02/15.
 * Modified by lnacosta (laion.cj91@gmail.com) on 15/10/2015.
 */
public class OutgoingExtraUserTransactionPluginRoot extends AbstractPlugin implements
        DatabaseManagerForDevelopers ,
        OutgoingExtraUserManager     {


    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.BASIC_WALLET   , plugin = Plugins.BITCOIN_WALLET)
    private BitcoinWalletManager bitcoinWalletManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER         )
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM         , addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS        , layer = Layers.CRYPTO_VAULT   , plugin = Plugins.BITCOIN_VAULT)
    private CryptoVaultManager cryptoVaultManager;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS         , layer = Layers.CRYPTO_NETWORK  , plugin = Plugins.BITCOIN_NETWORK       )
    private BitcoinNetworkManager bitcoinNetworkManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_BROADCASTER_SYSTEM)
    private Broadcaster broadcaster;


    public OutgoingExtraUserTransactionPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }



    private OutgoingExtraUserTransactionProcessorAgent transactionProcessorAgent;

    /**
     * Service Interface implementation.
     */
    @Override
    public void start() throws CantStartPluginException  {

        com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_extra_user.developer.bitdubai.version_1.structure.OutgoingExtraUserDao dao = new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_extra_user.developer.bitdubai.version_1.structure.OutgoingExtraUserDao(errorManager, pluginDatabaseSystem, pluginId);
        try {
            dao.initialize();
        } catch (com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_extra_user.developer.bitdubai.version_1.exceptions.CantInitializeDaoException e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION,UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,e);
            throw new CantStartPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION);
        }
        catch (Exception exception){
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
        this.transactionProcessorAgent = new OutgoingExtraUserTransactionProcessorAgent(
                bitcoinWalletManager,
                cryptoVaultManager  ,
                bitcoinNetworkManager,
                errorManager        ,
                dao
        );
        this.transactionProcessorAgent.start();

        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public TransactionManager getTransactionManager() {

        return new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_extra_user.developer.bitdubai.version_1.structure.OutgoingExtraUserTransactionManager(
                bitcoinWalletManager,
                errorManager,
                pluginDatabaseSystem,
                pluginId
        );
    }

    @Override
    public void stop() {
        transactionProcessorAgent.stop();
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    /*
     * DatabaseManagerForDevelopers interface implementation
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        OutgoingExtraUserDeveloperDatabaseFactory dbFactory = new OutgoingExtraUserDeveloperDatabaseFactory(pluginId.toString(), OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_DATABASE_NAME);
        return dbFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return OutgoingExtraUserDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        Database database;
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_DATABASE_NAME);
            return OutgoingExtraUserDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, database, developerDatabaseTable);
        }catch (CantOpenDatabaseException cantOpenDatabaseException){
            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            FermatException e = new CantDeliverDatabaseException("I can't open database",cantOpenDatabaseException,"WalletId: " + developerDatabase.getName(),"");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        }
        catch (DatabaseNotFoundException databaseNotFoundException) {
            FermatException e = new CantDeliverDatabaseException("Database does not exists",databaseNotFoundException,"WalletId: " + developerDatabase.getName(),"");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        }
        catch (Exception exception) {
            FermatException e = new CantDeliverDatabaseException(CantDeliverDatabaseException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "WalletId: " + developerDatabase.getName(),"Check the cause");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        }
        // If we are here the database could not be opened, so we return an empry list
        return new ArrayList<>();
    }
}
