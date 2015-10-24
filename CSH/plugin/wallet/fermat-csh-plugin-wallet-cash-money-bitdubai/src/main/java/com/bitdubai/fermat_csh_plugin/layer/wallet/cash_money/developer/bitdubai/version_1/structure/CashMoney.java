package com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.structure;

/**
 * Created by francisco on 15/10/15.
 */
public class CashMoney {

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


}
