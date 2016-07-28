package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionStatusRestockDestock;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.cash_money_restock.exceptions.CantCreateCashMoneyRestockException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.cash_money_restock.interfaces.CashMoneyRestockManager;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.StockTransactionsCashMoneyRestockPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.exceptions.MissingCashMoneyRestockDataException;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * The Class <code>StockTransactionBankMoneyRestockManager</code>
 * contains all the business logic of Bank Money Transaction
 * <p/>
 * Created by franklin on 17/11/15.
 */
public class StockTransactionCashMoneyRestockManager implements
        CashMoneyRestockManager {
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;
    private final StockTransactionsCashMoneyRestockPluginRoot pluginRoot;

    /**
     * Constructor with params.
     *
     * @param pluginDatabaseSystem database system reference.
     * @param pluginId             of this module.
     */
    public StockTransactionCashMoneyRestockManager(final PluginDatabaseSystem pluginDatabaseSystem,
                                                   final UUID pluginId,
                                                   StockTransactionsCashMoneyRestockPluginRoot pluginRoot) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.pluginRoot = pluginRoot;
    }

    @Override
    public void createTransactionRestock(String publicKeyActor, FiatCurrency fiatCurrency, String cbpWalletPublicKey, String cshWalletPublicKey, String cashReference, BigDecimal amount, String memo, BigDecimal priceReference, OriginTransaction originTransaction, String originTransactionId) throws CantCreateCashMoneyRestockException {

        java.util.Date date = new java.util.Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        CashMoneyRestockTransactionImpl cashMoneyRestockTransaction = new CashMoneyRestockTransactionImpl(
                UUID.randomUUID(),
                publicKeyActor,
                fiatCurrency,
                cbpWalletPublicKey,
                cshWalletPublicKey,
                memo,
                "INIT TRANSACTION",
                cashReference,
                amount,
                timestamp,
                TransactionStatusRestockDestock.INIT_TRANSACTION,
                priceReference,
                originTransaction,
                originTransactionId);

        try {
            StockTransactionCashMoneyRestockFactory stockTransactionCashMoneyRestockFactory = new StockTransactionCashMoneyRestockFactory(pluginDatabaseSystem, pluginId);
            stockTransactionCashMoneyRestockFactory.saveCashMoneyRestockTransactionData(cashMoneyRestockTransaction);
        } catch (DatabaseOperationException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateCashMoneyRestockException("Database Operation.", FermatException.wrapException(e), null, null);
        } catch (MissingCashMoneyRestockDataException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateCashMoneyRestockException("Missing Cash Money Restock Data.", FermatException.wrapException(e), null, null);

        }
    }

}
