package com.bitdubai.fermat_cbp_api.all_definition.wallet;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;

import java.util.Collection;

/**
 * Created by jorge on 30-09-2015.
 */
public interface Wallet {
    String getWalletPublicKey();

    String getOwnerPublicKey();

    void addStock(FermatEnum stockType);

    Stock getStock(FermatEnum stockType);

    Collection<Stock> getStocks();

    void performTransaction(WalletTransaction transaction);
}
