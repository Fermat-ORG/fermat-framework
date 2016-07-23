package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_destock.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionStatusRestockDestock;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.bank_money_destock.exceptions.CantCreateBankMoneyDestockException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.bank_money_destock.interfaces.BankMoneyDestockManager;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_destock.developer.bitdubai.version_1.BusinessTransactionBankMoneyDestockPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_destock.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_destock.developer.bitdubai.version_1.exceptions.MissingBankMoneyDestockDataException;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * The Class <code>StockTransactionBankMoneyRestockManager</code>
 * contains all the business logic of Bank Money Transaction
 * <p/>
 * Created by franklin on 17/11/15.
 */
public class StockTransactionBankMoneyDestockManager implements BankMoneyDestockManager {
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;
    private BusinessTransactionBankMoneyDestockPluginRoot pluginRoot;

    /**
     * Constructor with params.
     *
     * @param pluginDatabaseSystem database system reference.
     * @param pluginId             of this module.
     */
    public StockTransactionBankMoneyDestockManager(final PluginDatabaseSystem pluginDatabaseSystem,
                                                   final UUID pluginId,
                                                   final BusinessTransactionBankMoneyDestockPluginRoot pluginRoot) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.pluginRoot = pluginRoot;
    }

    /**
     * Method that create the transaction Destock
     *
     * @param publicKeyActor
     * @param fiatCurrency
     * @param cbpWalletPublicKey
     * @param bankWalletPublicKey
     * @param bankAccount
     * @param amount
     * @param memo
     * @param priceReference
     * @param originTransaction
     * @throws CantCreateBankMoneyDestockException
     */
    @Override
    public void createTransactionDestock(String publicKeyActor, FiatCurrency fiatCurrency, String cbpWalletPublicKey, String bankWalletPublicKey, String bankAccount, BigDecimal amount, String memo, BigDecimal priceReference, OriginTransaction originTransaction, String originTransactionId) throws CantCreateBankMoneyDestockException {
        java.util.Date date = new java.util.Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        BankMoneyDestockTransactionImpl bankMoneyRestockTransaction = new BankMoneyDestockTransactionImpl(
                UUID.randomUUID(),
                publicKeyActor,
                fiatCurrency,
                cbpWalletPublicKey,
                bankWalletPublicKey,
                memo,
                "INIT TRANSACTION",
                bankAccount,
                amount,
                timestamp,
                TransactionStatusRestockDestock.INIT_TRANSACTION,
                priceReference,
                originTransaction,
                originTransactionId);

        try {
            StockTransactionBankMoneyDestockFactory stockTransactionBankMoneyDestockFactory = new StockTransactionBankMoneyDestockFactory(pluginDatabaseSystem, pluginId);
            stockTransactionBankMoneyDestockFactory.saveBankMoneyDestockTransactionData(bankMoneyRestockTransaction);
        } catch (DatabaseOperationException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateBankMoneyDestockException("Database Operation.", FermatException.wrapException(e), null, null);
        } catch (MissingBankMoneyDestockDataException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateBankMoneyDestockException("Missing Bank Money Destock Data.", FermatException.wrapException(e), null, null);
        } catch (Exception exception) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new CantCreateBankMoneyDestockException("Unhandled Exception.", FermatException.wrapException(exception), null, null);
        }
    }

}
