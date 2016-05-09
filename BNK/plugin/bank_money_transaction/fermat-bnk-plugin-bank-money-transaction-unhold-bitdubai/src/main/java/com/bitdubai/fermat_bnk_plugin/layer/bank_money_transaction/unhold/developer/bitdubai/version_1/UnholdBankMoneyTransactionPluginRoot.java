package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.unhold.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.*;
import com.bitdubai.fermat_api.layer.all_definition.enums.*;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransaction;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransactionParameters;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankTransactionStatus;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.unhold.exceptions.CantGetUnholdTransactionException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.unhold.exceptions.CantMakeUnholdTransactionException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.unhold.interfaces.UnholdManager;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWalletManager;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.unhold.developer.bitdubai.version_1.database.UnholdBankMoneyTransactionDeveloperDatabaseFactory;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.unhold.developer.bitdubai.version_1.exceptions.CantInitializeUnholdBankMoneyTransactionDatabaseException;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.unhold.developer.bitdubai.version_1.structure.UnholdBankMoneyTransactionManager;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.unhold.developer.bitdubai.version_1.structure.UnholdBankMoneyTransactionProcessorAgent;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Created by memo on 25/11/15.
 */
@PluginInfo(createdBy = "guillermo20", maintainerMail = "guillermo20@gmail.com", platform = Platforms.BANKING_PLATFORM, layer = Layers.BANK_MONEY_TRANSACTION, plugin = Plugins.BITDUBAI_BNK_UNHOLD_MONEY_TRANSACTION)
public class UnholdBankMoneyTransactionPluginRoot extends AbstractPlugin implements DatabaseManagerForDevelopers,UnholdManager {

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededPluginReference(platform = Platforms.BANKING_PLATFORM, layer = Layers.WALLET, plugin = Plugins.BITDUBAI_BNK_BANK_MONEY_WALLET)
    BankMoneyWalletManager bankMoneyWalletManager;

    private UnholdBankMoneyTransactionProcessorAgent processorAgent;
    private UnholdBankMoneyTransactionManager unholdTransactionManager;

    public UnholdBankMoneyTransactionPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    @Override
    public void start() throws CantStartPluginException {
        System.out.println("platform = Platforms.BANKING_PLATFORM, layer = Layers.TRANSACTION, plugin = Plugins.BITDUBAI_BNK_UNHOLD_BANK_MONEY_TRANSACTION");
        try {
            unholdTransactionManager = new UnholdBankMoneyTransactionManager(pluginDatabaseSystem, pluginId, errorManager);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_HOLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, null);
        }

        processorAgent = new UnholdBankMoneyTransactionProcessorAgent(errorManager, unholdTransactionManager,bankMoneyWalletManager);
        processorAgent.start();
        //test();
        serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        processorAgent.stop();
        this.serviceStatus = ServiceStatus.STOPPED;
    }


    @Override
    public BankTransaction unHold(BankTransactionParameters parameters) throws CantMakeUnholdTransactionException {
        return unholdTransactionManager.unHold(parameters);
    }

    @Override
    public BankTransactionStatus getUnholdTransactionsStatus(UUID transactionId) throws CantGetUnholdTransactionException {
        return unholdTransactionManager.getUnholdTransactionsStatus(transactionId);
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        UnholdBankMoneyTransactionDeveloperDatabaseFactory factory = new UnholdBankMoneyTransactionDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return factory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        UnholdBankMoneyTransactionDeveloperDatabaseFactory factory = new UnholdBankMoneyTransactionDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return factory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        UnholdBankMoneyTransactionDeveloperDatabaseFactory factory = new UnholdBankMoneyTransactionDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        List<DeveloperDatabaseTableRecord> tableRecordList = null;
        try {
            factory.initializeDatabase();
            tableRecordList = factory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch(CantInitializeUnholdBankMoneyTransactionDatabaseException cantInitializeException) {
            FermatException e = new CantInitializeUnholdBankMoneyTransactionDatabaseException("Database cannot be initialized", cantInitializeException, "UnholdBankMoneyTransactionPluginRoot", "");
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_UNHOLD_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        }
        return tableRecordList;
    }

    @Override
    public boolean isTransactionRegistered(UUID transactionId) {
        return unholdTransactionManager.isTransactionRegistered(transactionId);
    }

    private void test(){
        final UUID id=UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
        BankTransactionParameters t = new BankTransactionParameters() {

            @Override
            public UUID getTransactionId() {
                return UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
            }

            @Override
            public String getPublicKeyPlugin() {
                return "foo";
            }

            @Override
            public String getPublicKeyWallet() {
                return WalletsPublicKeys.BNK_BANKING_WALLET.getCode();//"banking_wallet";
            }

            @Override
            public String getPublicKeyActor() {
                return "bar";
            }

            @Override
            public BigDecimal getAmount() {
                return new BigDecimal("30.0");
            }

            @Override
            public String getAccount() {
                return "1234123412341";
            }

            @Override
            public FiatCurrency getCurrency() {
                return FiatCurrency.ARGENTINE_PESO;
            }

            @Override
            public String getMemo() {
                return "test";
            }
        };
        try {
            unHold(t);
            BankTransactionStatus status=getUnholdTransactionsStatus(id);
            System.out.println("( bank testing getHoldTransactionsStatus) =" + status.getCode());
        }catch (FermatException e){
            System.out.println("(bank hold) exception " + e.getMessage());
        }

    }

}
