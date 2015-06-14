package com.bitdubai.fermat_api.layer._16_module.wallet_store;

import com.bitdubai.fermat_api.layer._16_module.wallet_store.exceptions.CantGetWalletsException;

import java.util.List;

/**
 * Created by rodrigo on 09/05/15.
 */
public interface Catalog {

    List<Wallet> getWallets() throws CantGetWalletsException;
}
