package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.CashMoneyTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionStatusRestockDestock;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.cash_money_restock.exceptions.CantCreateCashMoneyRestockException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.cash_money_restock.interfaces.CashMoneyRestockManager;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.database.StockTransactionsCashMoneyRestockDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.exceptions.MissingCashMoneyRestockDataException;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>StockTransactionBankMoneyRestockManager</code>
 * contains all the business logic of Bank Money Transaction
 *
 * Created by franklin on 17/11/15.
 */
public class StockTransactionCashMoneyRestockManager implements
        CashMoneyRestockManager {
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;

    /**
     * Constructor with params.
     *
     * @param pluginDatabaseSystem  database system reference.
     * @param pluginId              of this module.
     */
    public StockTransactionCashMoneyRestockManager(final PluginDatabaseSystem pluginDatabaseSystem,
                                                   final UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId            ;
    }

    @Override
    public void createTransactionRestock(String publicKeyActor, FiatCurrency fiatCurrency, String cbpWalletPublicKey, String cshWalletPublicKey, String cashReference, BigDecimal amount, String memo, BigDecimal priceReference, OriginTransaction originTransaction) throws CantCreateCashMoneyRestockException {

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
                originTransaction);

        try {
            StockTransactionCashMoneyRestockFactory stockTransactionCashMoneyRestockFactory = new StockTransactionCashMoneyRestockFactory(pluginDatabaseSystem, pluginId);
            stockTransactionCashMoneyRestockFactory.saveCashMoneyRestockTransactionData(cashMoneyRestockTransaction);
        } catch (DatabaseOperationException e) {
            e.printStackTrace();
        } catch (MissingCashMoneyRestockDataException e) {
            e.printStackTrace();
        }
    }

}
