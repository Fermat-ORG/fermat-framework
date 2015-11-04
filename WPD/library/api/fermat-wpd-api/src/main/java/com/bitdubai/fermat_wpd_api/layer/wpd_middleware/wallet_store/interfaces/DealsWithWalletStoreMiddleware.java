package com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.interfaces;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.middleware.wallet_contacts.DealsWithWalletStoreMiddleware</code>
 * indicates that the plugin needs the functionality of a WalletStoreManager
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 08/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface DealsWithWalletStoreMiddleware {
    void setWalletStoreManager(WalletStoreManager walletStoreManager);
}
