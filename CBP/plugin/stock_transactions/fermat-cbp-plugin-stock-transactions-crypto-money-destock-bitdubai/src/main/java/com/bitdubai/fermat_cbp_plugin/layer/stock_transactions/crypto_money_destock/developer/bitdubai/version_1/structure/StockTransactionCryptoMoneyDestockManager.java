package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.BankMoneyTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.CryptoMoneyTransaction;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.database.StockTransactionsCryptoMoneyDestockDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.exceptions.MissingCryptoMoneyDestockDataException;

import java.util.List;
import java.util.UUID;

/**
 * The Class <code>StockTransactionCryptoMoneyDestockManager</code>
 * contains all the business logic of Bank Money Transaction
 *
 * Created by franklin on 17/11/15.
 */
public class StockTransactionCryptoMoneyDestockManager {
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;

    /**
     * Constructor with params.
     *
     * @param pluginDatabaseSystem  database system reference.
     * @param pluginId              of this module.
     */
    public StockTransactionCryptoMoneyDestockManager(final PluginDatabaseSystem pluginDatabaseSystem,
                                                     final UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId            ;
    }

    private StockTransactionsCryptoMoneyDestockDatabaseDao getStockTransactionCryptoMoneyDestockDao() {

        return new StockTransactionsCryptoMoneyDestockDatabaseDao(pluginDatabaseSystem, pluginId);
    }

    public void saveCryptoMoneyDestockTransactionData(CryptoMoneyTransaction cryptoMoneyTransaction) throws DatabaseOperationException, MissingCryptoMoneyDestockDataException {
        getStockTransactionCryptoMoneyDestockDao().saveCryptoMoneyRestockTransactionData(cryptoMoneyTransaction);
    }

    public List<CryptoMoneyTransaction> getCryptoMoneyTransactionList(DatabaseTableFilter filter) throws DatabaseOperationException, InvalidParameterException{
        return getStockTransactionCryptoMoneyDestockDao().getCryptoMoneyTransactionList(filter);
    }
}
