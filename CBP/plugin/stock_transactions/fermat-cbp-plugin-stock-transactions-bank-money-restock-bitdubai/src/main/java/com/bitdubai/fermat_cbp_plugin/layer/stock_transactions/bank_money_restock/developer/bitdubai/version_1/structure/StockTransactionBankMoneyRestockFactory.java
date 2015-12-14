package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.BankMoneyTransaction;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.database.BusinessTransactionBankMoneyRestockDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.exceptions.MissingBankMoneyRestockDataException;

import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 11/12/15.
 */
public class StockTransactionBankMoneyRestockFactory {
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;
    /**
     * Constructor with params.
     *
     * @param pluginDatabaseSystem  database system reference.
     * @param pluginId              of this module.
     */
    public StockTransactionBankMoneyRestockFactory(final PluginDatabaseSystem pluginDatabaseSystem,
                                                   final UUID pluginId            ) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId            ;
    }

    private BusinessTransactionBankMoneyRestockDatabaseDao getStockTransactionBankMoneyRestockDao() {

        return new BusinessTransactionBankMoneyRestockDatabaseDao(pluginDatabaseSystem, pluginId);
    }

    public void saveBankMoneyRestockTransactionData(BankMoneyTransaction bankMoneyTransaction) throws DatabaseOperationException, MissingBankMoneyRestockDataException {
        getStockTransactionBankMoneyRestockDao().saveBankMoneyRestockTransactionData(bankMoneyTransaction);
    }

    public List<BankMoneyTransaction> getBankMoneyTransactionList(DatabaseTableFilter filter) throws DatabaseOperationException, InvalidParameterException {
        return getStockTransactionBankMoneyRestockDao().getBankMoneyTransactionList(filter);
    }
}
