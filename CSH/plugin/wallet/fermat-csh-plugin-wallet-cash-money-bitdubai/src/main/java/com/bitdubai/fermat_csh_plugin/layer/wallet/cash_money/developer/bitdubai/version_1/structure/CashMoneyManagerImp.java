package com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantCreateCashMoneyException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantLoadCashMoneyException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantTransactionCashMoneyException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWallet;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletManager;

import java.util.List;

/**
 * Created by francisco on 03/11/15.
 */
public class CashMoneyManagerImp implements CashMoneyWalletManager {

    String cashTransactionId;
    String publicKeyActorFrom;
    String publicKeyActorTo;
    String status;
    String balanceType;
    String transactionType;
    double amount;
    String cashCurrencyType;
    String cashReference;
    long runningBookBalance;
    long runningAvailableBalance;
    long timestamp;
    String mem;

    public CashMoneyManagerImp(String cashTransactionId, String publicKeyActorFrom, String publicKeyActorTo, String status, String balanceType, String transactionType, double amount, String cashCurrencyType, String cashReference, long runningBookBalance, long runningAvailableBalance, long timestamp, String mem) {
        this.cashTransactionId = cashTransactionId;
        this.publicKeyActorFrom = publicKeyActorFrom;
        this.publicKeyActorTo = publicKeyActorTo;
        this.status = status;
        this.balanceType = balanceType;
        this.transactionType = transactionType;
        this.amount = amount;
        this.cashCurrencyType = cashCurrencyType;
        this.cashReference = cashReference;
        this.runningBookBalance = runningBookBalance;
        this.runningAvailableBalance = runningAvailableBalance;
        this.timestamp = timestamp;
        this.mem = mem;
    }


    @Override
    public CashMoneyWallet loadCashMoneyWallet(String walletPublicKey) throws CantLoadCashMoneyException {
        return null;
    }

    @Override
    public void createCashMoney(String walletPublicKey, FiatCurrency fiatCurrency) throws CantCreateCashMoneyException {

    }
}
