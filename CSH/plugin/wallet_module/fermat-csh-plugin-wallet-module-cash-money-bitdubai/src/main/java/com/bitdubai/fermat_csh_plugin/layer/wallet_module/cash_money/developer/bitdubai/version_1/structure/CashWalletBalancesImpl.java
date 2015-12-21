package com.bitdubai.fermat_csh_plugin.layer.wallet_module.cash_money.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_csh_api.all_definition.interfaces.CashWalletBalances;

import java.math.BigDecimal;

/**
 * Created by Alejandro Bicelis on 12/10/2015.
 */
public class CashWalletBalancesImpl implements CashWalletBalances {

    private BigDecimal availableBalance;
    private BigDecimal bookBalance;

    public CashWalletBalancesImpl(BigDecimal availableBalance, BigDecimal bookBalance)
    {
        this.availableBalance = availableBalance;
        this.bookBalance = bookBalance;
    }

    @Override
    public BigDecimal getAvailableBalance() {
        return this.availableBalance;
    }

    @Override
    public BigDecimal getBookBalance() {
        return this.bookBalance;
    }
}
