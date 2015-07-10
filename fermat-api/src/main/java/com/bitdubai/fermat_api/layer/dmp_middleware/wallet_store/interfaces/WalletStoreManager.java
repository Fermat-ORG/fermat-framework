package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.interfaces;

import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.middleware.wallet_factory.interfaces.WalletStoreManager</code>
 * indicates the functionality of a WalletStoreManager
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface WalletStoreManager {
    public List<WalletInformation> getWalletCatalog();

    public void installWallet(UUID walletCatalogId);
}