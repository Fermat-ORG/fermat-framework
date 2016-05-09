package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionStatusRestockDestock;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_destock.exceptions.CantCreateCryptoMoneyDestockException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_destock.interfaces.CryptoMoneyDestockManager;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.exceptions.MissingCryptoMoneyDestockDataException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.math.BigDecimal;
import java.sql.Timestamp;
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
    private ErrorManager errorManager;

    /**
     * Constructor with params.
     *
     * @param pluginDatabaseSystem  database system reference.
     * @param pluginId              of this module.
     */
    public StockTransactionCryptoMoneyDestockManager(final PluginDatabaseSystem pluginDatabaseSystem,
                                                     final UUID pluginId,
                                                     ErrorManager errorManager) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId            ;
        this.errorManager = errorManager;
    }

    @Override
    public void createTransactionDestock(String publicKeyActor, CryptoCurrency cryptoCurrency, String cbpWalletPublicKey, String cryWalletPublicKey, BigDecimal amount, String memo, BigDecimal priceReference, OriginTransaction originTransaction, String originTransactionId, BlockchainNetworkType blockchainNetworkType) throws CantCreateCryptoMoneyDestockException {
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
                originTransaction,
                originTransactionId,
                blockchainNetworkType);

        try {
            StockTransactionCryptoMoneyDestockFactory stockTransactionCryptoMoneyDestockFactory = new StockTransactionCryptoMoneyDestockFactory(pluginDatabaseSystem, pluginId);
            stockTransactionCryptoMoneyDestockFactory.saveCryptoMoneyDestockTransactionData(cryptoMoneyRestockTransaction);
        } catch (DatabaseOperationException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_MONEY_DESTOCK, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateCryptoMoneyDestockException("Database Operation.", FermatException.wrapException(e), null, null);

        } catch (MissingCryptoMoneyDestockDataException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_MONEY_DESTOCK, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateCryptoMoneyDestockException("Missing Cash Money Restock Data.", FermatException.wrapException(e), null, null);

        }
    }
}
