package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_restock.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.CryptoMoneyTransaction;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_restock.developer.bitdubai.version_1.database.StockTransactionsCryptoMoneyRestockDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_restock.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_restock.developer.bitdubai.version_1.exceptions.MissingCryptoMoneyRestockDataException;

import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 11/12/15.
 */
public class StockTransactionCryptoMoneyRestockFactory {
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;

    /**
     * Constructor with params.
     *
     * @param pluginDatabaseSystem database system reference.
     * @param pluginId             of this module.
     */
    public StockTransactionCryptoMoneyRestockFactory(final PluginDatabaseSystem pluginDatabaseSystem,
                                                     final UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    private StockTransactionsCryptoMoneyRestockDatabaseDao getStockTransactionCryptoMoneyRestockDao() {

        return new StockTransactionsCryptoMoneyRestockDatabaseDao(pluginDatabaseSystem, pluginId);
    }

    public void saveCryptoMoneyRestockTransactionData(CryptoMoneyTransaction cryptoMoneyTransaction) throws DatabaseOperationException, MissingCryptoMoneyRestockDataException {
        getStockTransactionCryptoMoneyRestockDao().saveCryptoMoneyRestockTransactionData(cryptoMoneyTransaction);
    }

    public List<CryptoMoneyTransaction> getCryptoMoneyTransactionList(DatabaseTableFilter filter) throws DatabaseOperationException, InvalidParameterException {
        return getStockTransactionCryptoMoneyRestockDao().getCryptoMoneyTransactionList(filter);
    }
}
