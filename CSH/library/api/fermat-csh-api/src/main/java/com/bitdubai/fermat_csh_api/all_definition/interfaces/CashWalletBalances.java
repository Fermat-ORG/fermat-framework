package com.bitdubai.fermat_csh_api.all_definition.interfaces;

import java.math.BigDecimal;

/**
 * Created by Alejandro Bicelis on 12/10/2015.
 */
public interface CashWalletBalances {

    BigDecimal getAvailableBalance();
    BigDecimal getBookBalance();
}
