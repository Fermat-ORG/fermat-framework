package com.bitdubai.fermat_cbp_api.all_definition.wallet;

/**
 * Created by jorge on 30-09-2015.
 */
public interface Stock {
    float getBookedBalance();

    float getAvailableBalance();

    void addDebit(final StockTransaction transaction);

    void addCrebit(final StockTransaction transaction);
}
