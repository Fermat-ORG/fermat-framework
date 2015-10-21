package com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.structure;

/**
 * Created by francisco on 16/10/15.
 */
public class CashMoneyBalance   {

    private String balance_id;
    private String dabit;
    private String credit;
    private String balance;
    private String time;

    public CashMoneyBalance(String balance_id, String dabit, String credit, String balance, String time) {
        this.balance_id = balance_id;
        this.dabit = dabit;
        this.credit = credit;
        this.balance = balance;
        this.time = time;
    }
}
