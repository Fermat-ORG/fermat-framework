package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.BankMoneyTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.CryptoMoneyTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionStatusRestockDestock;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_destock.exceptions.CantCreateCryptoMoneyDestockException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_destock.interfaces.CryptoMoneyDestockManager;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.database.StockTransactionsCryptoMoneyDestockDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.exceptions.MissingCryptoMoneyDestockDataException;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>StockTransactionCryptoMoneyDestockManager</code>
 * contains all the business logic of Bank Money Transaction
 *
 * Created by franklin on 17/11/15.
 */
public class StockTransactionCryptoMoneyDestockManager implements
        CryptoMoneyDestockManager {
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

    @Override
    public void createTransactionDestock(String publicKeyActor, CryptoCurrency cryptoCurrency, String cbpWalletPublicKey, String cryWalletPublicKey, float amount, String memo, float priceReference, OriginTransaction originTransaction) throws CantCreateCryptoMoneyDestockException {
        java.util.Date date = new java.util.Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        CryptoMoneyDestockTransactionImpl cryptoMoneyRestockTransaction = new CryptoMoneyDestockTransactionImpl(
                UUID.randomUUID(),
                publicKeyActor,
                cryptoCurrency,
                cbpWalletPublicKey,
                cryWalletPublicKey,
                memo,
                "INIT TRANSACTION",
                amount,
                timestamp,
                TransactionStatusRestockDestock.INIT_TRANSACTION,
                priceReference,
                originTransaction);

        try {
            saveCryptoMoneyDestockTransactionData(cryptoMoneyRestockTransaction);
        } catch (DatabaseOperationException e) {
            e.printStackTrace();
        } catch (MissingCryptoMoneyDestockDataException e) {
            e.printStackTrace();
        }
    }
    private StockTransactionsCryptoMoneyDestockDatabaseDao getStockTransactionCryptoMoneyDestockDao() {

        return new StockTransactionsCryptoMoneyDestockDatabaseDao(pluginDatabaseSystem, pluginId);
    }

    private void saveCryptoMoneyDestockTransactionData(CryptoMoneyTransaction cryptoMoneyTransaction) throws DatabaseOperationException, MissingCryptoMoneyDestockDataException {
        getStockTransactionCryptoMoneyDestockDao().saveCryptoMoneyDestockTransactionData(cryptoMoneyTransaction);
    }

    public List<CryptoMoneyTransaction> getCryptoMoneyTransactionList(DatabaseTableFilter filter) throws DatabaseOperationException, InvalidParameterException{
        return getStockTransactionCryptoMoneyDestockDao().getCryptoMoneyTransactionList(filter);
    }

}
