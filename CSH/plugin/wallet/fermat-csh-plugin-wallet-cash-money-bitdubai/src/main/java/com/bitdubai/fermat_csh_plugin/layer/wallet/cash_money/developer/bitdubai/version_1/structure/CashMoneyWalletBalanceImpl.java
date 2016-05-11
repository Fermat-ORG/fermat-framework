package com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_csh_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_csh_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_csh_api.all_definition.exceptions.CashMoneyWalletInsufficientFundsException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletBalanceException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletBalance;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.database.CashMoneyWalletDao;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantRegisterCashMoneyWalletTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Alejandro Bicelis on 11/23/2015.
 */
public class CashMoneyWalletBalanceImpl implements CashMoneyWalletBalance {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;
    private final ErrorManager errorManager;
    private String walletPublicKey;
    private BalanceType balanceType;

    private CashMoneyWalletDao dao;


    public CashMoneyWalletBalanceImpl(final PluginDatabaseSystem pluginDatabaseSystem, final UUID pluginId,
                                      final ErrorManager errorManager, String walletPublicKey, BalanceType balanceType) throws CantGetCashMoneyWalletBalanceException {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.errorManager = errorManager;
        this.balanceType = balanceType;
        this.walletPublicKey = walletPublicKey;
        
        try {
            this.dao = new CashMoneyWalletDao(pluginDatabaseSystem, pluginId, errorManager);
            dao.initialize();

        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_WALLET_CASH_MONEY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetCashMoneyWalletBalanceException(CantGetCashMoneyWalletBalanceException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, null);
        }
    }

    @Override
    public BigDecimal getBalance() throws CantGetCashMoneyWalletBalanceException {
        return dao.getWalletBalance(walletPublicKey, balanceType);
    }

    @Override
    public void debit(UUID transactionId, String publicKeyActor, String publicKeyPlugin, BigDecimal amount, String memo) throws CantRegisterDebitException, CashMoneyWalletInsufficientFundsException {

        try {
            CashMoneyWalletTransactionImpl transaction = new CashMoneyWalletTransactionImpl(transactionId, this.walletPublicKey, publicKeyActor, publicKeyPlugin, TransactionType.DEBIT, this.balanceType, amount, memo, (new Date().getTime() / 1000), false);
            dao.debit(this.walletPublicKey, this.balanceType, amount);
            dao.registerTransaction(transaction);
        } catch (CantRegisterCashMoneyWalletTransactionException e) {
            throw new CantRegisterDebitException(CantRegisterDebitException.DEFAULT_MESSAGE, e, "Cant insert transaction record into database", null);
        }
    }

    @Override
    public void credit(UUID transactionId, String publicKeyActor, String publicKeyPlugin, BigDecimal amount, String memo) throws CantRegisterCreditException {
        try {
            CashMoneyWalletTransactionImpl transaction = new CashMoneyWalletTransactionImpl(transactionId, this.walletPublicKey, publicKeyActor, publicKeyPlugin, TransactionType.CREDIT, this.balanceType, amount, memo, (new Date().getTime() / 1000), false);
            dao.credit(this.walletPublicKey, this.balanceType, amount);
            dao.registerTransaction(transaction);
        } catch (CantRegisterCashMoneyWalletTransactionException e) {
            throw new CantRegisterCreditException(CantRegisterCreditException.DEFAULT_MESSAGE, e, "Cant insert transaction record into database", null);
        }
    }

}
