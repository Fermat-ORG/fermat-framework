package com.bitdubai.fermat_cbp_api.all_definition.wallet;

/**
 * Created by jorge on 30-09-2015.
 */
public interface Stock extends Wallet {
    void debit(final StockTransaction transaction);
    void crebit(final StockTransaction transaction);
}
