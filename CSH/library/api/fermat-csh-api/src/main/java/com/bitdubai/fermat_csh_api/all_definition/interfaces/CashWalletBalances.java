package com.bitdubai.fermat_csh_api.all_definition.interfaces;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Alejandro Bicelis on 12/10/2015.
 */
public interface CashWalletBalances extends Serializable {

    BigDecimal getAvailableBalance();
    BigDecimal getBookBalance();
}
