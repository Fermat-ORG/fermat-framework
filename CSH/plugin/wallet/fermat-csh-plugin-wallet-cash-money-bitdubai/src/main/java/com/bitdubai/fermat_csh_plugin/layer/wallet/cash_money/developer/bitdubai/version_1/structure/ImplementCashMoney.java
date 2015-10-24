package com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_csh_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.exceptions.CantTransactionCashMoneyException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.exceptions.CantTransactionSummaryCashMoneyException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.interfaces.CashMoney;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.interfaces.CashMoneyTransaction;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.interfaces.CashMoneyTransactionSummary;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.database.CashMoneyWalletDao;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantGetCashMoneyBalance;

import java.util.List;

/**
 * Created by francisco on 21/10/15.
 */
public class ImplementCashMoney implements CashMoney  {

    String CashTransactionId;
    String PublicKeyActorFrom;
    String PublicKeyActorTo;
    String Status;
    String BalanceType;
    String TransactionType;
    String Amount;
    String CashCurrencyType;
    String CashReference;
    String Timestamp;
    String Memo;

    public ImplementCashMoney(String cashTransactionId, String publicKeyActorFrom, String publicKeyActorTo, String status, String balanceType, String transactionType, String amount, String cashCurrencyType, String cashReference, String timestamp, String memo, PluginDatabaseSystem pluginDatabaseSystem) {
        CashTransactionId = cashTransactionId;
        PublicKeyActorFrom = publicKeyActorFrom;
        PublicKeyActorTo = publicKeyActorTo;
        Status = status;
        BalanceType = balanceType;
        TransactionType = transactionType;
        Amount = amount;
        CashCurrencyType = cashCurrencyType;
        CashReference = cashReference;
        Timestamp = timestamp;
        Memo = memo;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    private PluginDatabaseSystem pluginDatabaseSystem;
    private CashMoneyWalletDao cashMoneyWalletDao = new CashMoneyWalletDao(pluginDatabaseSystem);


    @Override
    public double getBookBalance(BalanceType balanceType) throws CantTransactionCashMoneyException {
        try {
            return cashMoneyWalletDao.getCashMoneyBalance(balanceType.BOOK);
        } catch (CantGetCashMoneyBalance cantGetCashMoneyBalance) {
            throw new CantTransactionCashMoneyException(CantTransactionCashMoneyException.DEFAULT_MESSAGE,cantGetCashMoneyBalance,"get Book Balance","Cant Transaction CashMoney Exception");
        }
    }

    @Override
    public double getAvailableBalance(BalanceType balanceType) throws CantTransactionCashMoneyException {
        try {
            return cashMoneyWalletDao.getCashMoneyBalance(balanceType.AVAILABLE);
        } catch (CantGetCashMoneyBalance cantGetCashMoneyBalance) {
            throw new CantTransactionCashMoneyException(CantTransactionCashMoneyException.DEFAULT_MESSAGE,cantGetCashMoneyBalance,"get Available Balance","Cant Transaction CashMoney Exception");
        }
    }

    @Override
    public List<CashMoneyTransaction> getTransactions(BalanceType balanceType, int max, int offset) throws CantTransactionCashMoneyException {
        return null;
    }

    @Override
    public CashMoneyTransactionSummary getBrokerTransactionSummary(BalanceType balanceType) throws CantTransactionSummaryCashMoneyException {
        return null;
    }
}
