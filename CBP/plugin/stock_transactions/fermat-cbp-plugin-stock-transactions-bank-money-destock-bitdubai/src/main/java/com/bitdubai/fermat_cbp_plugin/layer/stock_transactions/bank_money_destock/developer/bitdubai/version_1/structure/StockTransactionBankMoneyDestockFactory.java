package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_destock.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.BankMoneyTransaction;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_destock.developer.bitdubai.version_1.database.BusinessTransactionBankMoneyDestockDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_destock.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_destock.developer.bitdubai.version_1.exceptions.MissingBankMoneyDestockDataException;

import java.util.List;
import java.util.UUID;


/**
 * Created by franklin on 11/12/15.
 */
public class StockTransactionBankMoneyDestockFactory {
    private final BusinessTransactionBankMoneyDestockDatabaseDao dao;

    /**
     * Constructor with params.
     *
     * @param pluginDatabaseSystem database system reference.
     * @param pluginId             the id of this plugin.
     */
    public StockTransactionBankMoneyDestockFactory(final PluginDatabaseSystem pluginDatabaseSystem, final UUID pluginId) {
        dao = new BusinessTransactionBankMoneyDestockDatabaseDao(pluginDatabaseSystem, pluginId);
    }


    public void saveBankMoneyDestockTransactionData(BankMoneyTransaction bankMoneyTransaction) throws DatabaseOperationException, MissingBankMoneyDestockDataException {
        dao.saveBankMoneyDestockTransactionData(bankMoneyTransaction);
    }

    public List<BankMoneyTransaction> getBankMoneyTransactionList(DatabaseTableFilter filter) throws DatabaseOperationException, InvalidParameterException {
        return dao.getBankMoneyTransactionList(filter);
    }
}
