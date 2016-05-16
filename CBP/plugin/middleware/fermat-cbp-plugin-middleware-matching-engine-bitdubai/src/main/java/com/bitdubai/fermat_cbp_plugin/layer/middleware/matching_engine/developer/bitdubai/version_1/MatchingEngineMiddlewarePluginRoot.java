package com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.CantStopAgentException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
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
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.bank_money_destock.interfaces.BankMoneyDestockManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.cash_money_destock.interfaces.CashMoneyDestockManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_destock.interfaces.CryptoMoneyDestockManager;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.agents.MatchingEngineMiddlewareEarningsTransactionGeneratorAgent;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.agents.MatchingEngineMiddlewareTransactionMonitorAgent;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.database.MatchingEngineMiddlewareDao;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.database.MatchingEngineMiddlewareDeveloperDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.exceptions.CantInitializeDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure.MatchingEngineMiddlewareManager;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure.earning_extraction.BankEarningExtractor;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure.earning_extraction.CashEarningExtractor;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure.earning_extraction.CryptoEarningExtractor;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.List;


/**
 * Matching Engine Middleware CBP Plug-In;
 * <p/>
 * Description:
 * This plug-in has the mission of:
 * - Calculation of the earnings between a pair of currencies.
 * - Extraction of this calculated earning from the specified wallet, and deposited in another specified wallet.
 * <p/>
 * To accomplish this task it contains a series of features:
 * - CRUD of Earning Settings (pairs of currencies related with a wallet, and the specified wallet to deposit the earnings).
 * - An agent with the responsibility of getting all the new confirmed transactions in the wallet where we will extract the earnings.
 * - An agent with the responsibility of calculate the earnings between pair of currencies and to execute the respective extract and deposit actions.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/01/2015.
 *
 * @author lnacosta
 * @version 1.0
 */
@PluginInfo(createdBy = "lnacosta", maintainerMail = "laion.cj91@gmail.com", platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.MIDDLEWARE, plugin = Plugins.MATCHING_ENGINE)
public final class MatchingEngineMiddlewarePluginRoot extends AbstractPlugin implements DatabaseManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    protected PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.WALLET, plugin = Plugins.CRYPTO_BROKER_WALLET)
    private CryptoBrokerWalletManager cryptoBrokerWalletManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.STOCK_TRANSACTIONS, plugin = Plugins.BANK_MONEY_DESTOCK)
    private BankMoneyDestockManager bankMoneyDestockManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.STOCK_TRANSACTIONS, plugin = Plugins.CASH_MONEY_DESTOCK)
    private CashMoneyDestockManager cashMoneyDestockManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.STOCK_TRANSACTIONS, plugin = Plugins.CRYPTO_MONEY_DESTOCK)
    private CryptoMoneyDestockManager cryptoMoneyDestockManager;


    public MatchingEngineMiddlewarePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    private MatchingEngineMiddlewareManager fermatManager;

    private MatchingEngineMiddlewareTransactionMonitorAgent monitorAgent;

    private MatchingEngineMiddlewareEarningsTransactionGeneratorAgent transactionGeneratorAgent;

    @Override
    public FermatManager getManager() {
        return fermatManager;
    }

    @Override
    public void start() throws CantStartPluginException {

        try {

            final MatchingEngineMiddlewareDao dao = new MatchingEngineMiddlewareDao(
                    pluginDatabaseSystem,
                    pluginId
            );

            dao.initialize();

            fermatManager = new MatchingEngineMiddlewareManager(dao, errorManager, getPluginVersionReference(), cryptoBrokerWalletManager);
            fermatManager.addEarningsExtractor(new BankEarningExtractor(bankMoneyDestockManager));
            fermatManager.addEarningsExtractor(new CashEarningExtractor(cashMoneyDestockManager));
            fermatManager.addEarningsExtractor(new CryptoEarningExtractor(cryptoMoneyDestockManager));

            monitorAgent = new MatchingEngineMiddlewareTransactionMonitorAgent(cryptoBrokerWalletManager, errorManager, dao, getPluginVersionReference());
            monitorAgent.start();

            transactionGeneratorAgent = new MatchingEngineMiddlewareEarningsTransactionGeneratorAgent(errorManager, dao, getPluginVersionReference());
            transactionGeneratorAgent.start();

            super.start();

        } catch (final CantInitializeDatabaseException cantInitializeActorConnectionDatabaseException) {

            errorManager.reportUnexpectedPluginException(
                    getPluginVersionReference(),
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    cantInitializeActorConnectionDatabaseException
            );

            throw new CantStartPluginException(
                    cantInitializeActorConnectionDatabaseException,
                    "Matching Engine Middleware.",
                    "Problem initializing database of the plug-in."
            );
        }
    }

    @Override
    public void pause() {

        try {

            monitorAgent.pause();
            transactionGeneratorAgent.pause();

        } catch (CantStopAgentException cantStopAgentException) {
            errorManager.reportUnexpectedPluginException(
                    getPluginVersionReference(),
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    cantStopAgentException
            );
        }

        super.pause();
    }

    @Override
    public void resume() {

        try {

            monitorAgent.resume();
            transactionGeneratorAgent.resume();

        } catch (CantStartAgentException cantStartAgentException) {
            errorManager.reportUnexpectedPluginException(
                    getPluginVersionReference(),
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    cantStartAgentException
            );
        }

        super.resume();
    }

    @Override
    public void stop() {

        monitorAgent.stop();
        transactionGeneratorAgent.stop();

        super.stop();
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(final DeveloperObjectFactory developerObjectFactory) {

        return new MatchingEngineMiddlewareDeveloperDatabaseFactory(
                pluginDatabaseSystem,
                pluginId
        ).getDatabaseList(
                developerObjectFactory
        );

    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(final DeveloperObjectFactory developerObjectFactory,
                                                             final DeveloperDatabase developerDatabase) {

        return new MatchingEngineMiddlewareDeveloperDatabaseFactory(
                pluginDatabaseSystem,
                pluginId
        ).getDatabaseTableList(
                developerObjectFactory
        );
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(final DeveloperObjectFactory developerObjectFactory,
                                                                      final DeveloperDatabase developerDatabase,
                                                                      final DeveloperDatabaseTable developerDatabaseTable) {

        return new MatchingEngineMiddlewareDeveloperDatabaseFactory(
                pluginDatabaseSystem,
                pluginId
        ).getDatabaseTableContent(
                developerObjectFactory,
                developerDatabaseTable
        );
    }

}
