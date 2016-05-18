package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionStatusRestockDestock;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.bank_money_restock.exceptions.CantCreateBankMoneyRestockException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.bank_money_restock.interfaces.BankMoneyRestockManager;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.exceptions.MissingBankMoneyRestockDataException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;


/**
 * The Class <code>StockTransactionBankMoneyRestockManager</code>
 * contains all the business logic of Bank Money Transaction
 * <p/>
 * Created by franklin on 17/11/15.
 */
public class StockTransactionBankMoneyRestockManager implements
        BankMoneyRestockManager {
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;
    private ErrorManager errorManager;

    /**
     * Constructor with params.
     *
     * @param pluginDatabaseSystem database system reference.
     * @param pluginId             of this module.
     */
    public StockTransactionBankMoneyRestockManager(final PluginDatabaseSystem pluginDatabaseSystem,
                                                   final UUID pluginId,
                                                   final ErrorManager errorManager) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.errorManager = errorManager;
    }

    @Override
    public void createTransactionRestock(String publicKeyActor, FiatCurrency fiatCurrency, String cbpWalletPublicKey, String bankWalletPublicKey, String bankAccount, BigDecimal amount, String memo, BigDecimal priceReference, OriginTransaction originTransaction, String originTransactionId) throws CantCreateBankMoneyRestockException {
        java.util.Date date = new java.util.Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        BankMoneyRestockTransactionImpl bankMoneyRestockTransaction = new BankMoneyRestockTransactionImpl(
                UUID.randomUUID(),
                publicKeyActor,
                fiatCurrency,
                cbpWalletPublicKey,
                bankWalletPublicKey,
                memo,
                "INIT TRANSACTION",
                bankAccount,
                amount,
                timestamp,
                TransactionStatusRestockDestock.INIT_TRANSACTION,
                priceReference,
                originTransaction,
                originTransactionId);

        try {
            StockTransactionBankMoneyRestockFactory stockTransactionBankMoneyRestockFactory = new StockTransactionBankMoneyRestockFactory(pluginDatabaseSystem, pluginId);
            stockTransactionBankMoneyRestockFactory.saveBankMoneyRestockTransactionData(bankMoneyRestockTransaction);
        } catch (DatabaseOperationException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BANK_MONEY_RESTOCK, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateBankMoneyRestockException("Database Operation.", FermatException.wrapException(e), null, null);
        } catch (MissingBankMoneyRestockDataException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BANK_MONEY_RESTOCK, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateBankMoneyRestockException("Missing Bank Money Restock Data Exception.", FermatException.wrapException(e), null, null);

        }
    }

}
