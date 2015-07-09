package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.interfaces;

import java.util.List;
import java.util.UUID;

/**
 * Created by eze on 2015.07.09..
 */
public interface WalletStoreMiddlewareManager {

    public List<WalletInformation> getWalletCatalog();

    public void installWallet(UUID walletCatalogId);

}
