package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.CashMoneyTransaction;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.database.StockTransactionsCashMoneyRestockDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.exceptions.MissingCashMoneyRestockDataException;

import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 11/12/15.
 */
public class StockTransactionCashMoneyRestockFactory {
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;

    /**
     * Constructor with params.
     *
     * @param pluginDatabaseSystem database system reference.
     * @param pluginId             of this module.
     */
    public StockTransactionCashMoneyRestockFactory(final PluginDatabaseSystem pluginDatabaseSystem,
                                                   final UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }


    private StockTransactionsCashMoneyRestockDatabaseDao getStockTransactionCashMoneyRestockDao() {

        return new StockTransactionsCashMoneyRestockDatabaseDao(pluginDatabaseSystem, pluginId);
    }

    public void saveCashMoneyRestockTransactionData(CashMoneyTransaction cashMoneyTransaction) throws DatabaseOperationException, MissingCashMoneyRestockDataException {
        getStockTransactionCashMoneyRestockDao().saveCashMoneyRestockTransactionData(cashMoneyTransaction);
    }

    public List<CashMoneyTransaction> getCashMoneyTransactionList(DatabaseTableFilter filter) throws DatabaseOperationException, InvalidParameterException {
        return getStockTransactionCashMoneyRestockDao().getCashMoneyTransactionList(filter);
    }
}
