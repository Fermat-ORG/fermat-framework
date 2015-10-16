package com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_csh_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_csh_api.all_definition.enums.CashCurrencyType;
import com.bitdubai.fermat_csh_api.all_definition.enums.CashTransactionStatus;
import com.bitdubai.fermat_csh_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.interfaces.CashMoneyBalanceRecord;

import java.util.UUID;

/**
 * Created by francisco on 15/10/15.
 */
public class CashMoney implements CashMoneyBalanceRecord {

    private String cash_transaction_id;
    private String public_key_customer;
    private String public_key_broker;
    private String balance_type;
    private String transaction_type;
    private String amount;
    private String cash_currency_type;
    private String cash_reference;
    private String running_book_balance;
    private String running_available_balance;
    private String status;

    public CashMoney(String cash_transaction_id, String public_key_customer, String public_key_broker, String balance_type, String transaction_type, String amount, String cash_currency_type, String cash_reference, String running_book_balance, String running_available_balance, String status) {
        this.cash_transaction_id = cash_transaction_id;
        this.public_key_customer = public_key_customer;
        this.public_key_broker = public_key_broker;
        this.balance_type = balance_type;
        this.transaction_type = transaction_type;
        this.amount = amount;
        this.cash_currency_type = cash_currency_type;
        this.cash_reference = cash_reference;
        this.running_book_balance = running_book_balance;
        this.running_available_balance = running_available_balance;
        this.status = status;
    }

    @Override
    public UUID getCashTransactionId() {
        return null;
    }

    @Override
    public String getPublicKeyActorFrom() {
        return null;
    }

    @Override
    public String getPublicKeyActorTo() {
        return null;
    }

    @Override
    public CashTransactionStatus getStatus() {
        return null;
    }

    @Override
    public BalanceType getBalanceType() {
        return null;
    }

    @Override
    public TransactionType getTransactionType() {
        return null;
    }

    @Override
    public double getAmount() {
        return 0;
    }

    @Override
    public CashCurrencyType getCashCurrencyType() {
        return null;
    }

    @Override
    public String getCashReference() {
        return null;
    }

    @Override
    public long getTimestamp() {
        return 0;
    }

    @Override
    public String getMemo() {
        return null;
    }
}
