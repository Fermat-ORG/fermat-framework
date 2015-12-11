package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_restock.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.CryptoMoneyTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionStatusRestockDestock;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_restock.exceptions.CantCreateCryptoMoneyRestockException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_restock.interfaces.CryptoMoneyRestockManager;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_restock.developer.bitdubai.version_1.database.StockTransactionsCryptoMoneyRestockDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_restock.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_restock.developer.bitdubai.version_1.exceptions.MissingCryptoMoneyRestockDataException;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>StockTransactionCryptoMoneyDestockManager</code>
 * contains all the business logic of Bank Money Transaction
 *
 * Created by franklin on 17/11/15.
 */
public class StockTransactionCryptoMoneyRestockManager implements
        CryptoMoneyRestockManager {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;

    /**
     * Constructor with params.
     *
     * @param pluginDatabaseSystem  database system reference.
     * @param pluginId              of this module.
     */
    public StockTransactionCryptoMoneyRestockManager(final PluginDatabaseSystem pluginDatabaseSystem,
                                                     final UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId            ;
    }

    @Override
    public void createTransactionRestock(String publicKeyActor, CryptoCurrency cryptoCurrency, String cbpWalletPublicKey, String cryWalletPublicKey,  float amount, String memo, float priceReference, OriginTransaction originTransaction) throws CantCreateCryptoMoneyRestockException {
        java.util.Date date = new java.util.Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        CryptoMoneyRestockTransactionImpl cryptoMoneyRestockTransaction = new CryptoMoneyRestockTransactionImpl(
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
            StockTransactionCryptoMoneyRestockFactory stockTransactionCryptoMoneyRestockFactory = new StockTransactionCryptoMoneyRestockFactory(pluginDatabaseSystem, pluginId);
            stockTransactionCryptoMoneyRestockFactory.saveCryptoMoneyRestockTransactionData(cryptoMoneyRestockTransaction);
        } catch (DatabaseOperationException e) {
            e.printStackTrace();
        } catch (MissingCryptoMoneyRestockDataException e) {
            e.printStackTrace();
        }
    }

}
