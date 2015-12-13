package com.bitdubai.fermat_csh_plugin.layer.wallet_module.cash_money.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_csh_api.all_definition.interfaces.CashWalletBalances;

/**
 * Created by Alejandro Bicelis on 12/10/2015.
 */
public class CashWalletBalancesImpl implements CashWalletBalances {

    private double availableBalance;
    private double bookBalance;

    public CashWalletBalancesImpl(double availableBalance, double bookBalance)
    {
        this.availableBalance = availableBalance;
        this.bookBalance = bookBalance;
    }

    @Override
    public double getAvailableBalance() {
        return this.availableBalance;
    }

    @Override
    public double getBookBalance() {
        return this.bookBalance;
    }
}
