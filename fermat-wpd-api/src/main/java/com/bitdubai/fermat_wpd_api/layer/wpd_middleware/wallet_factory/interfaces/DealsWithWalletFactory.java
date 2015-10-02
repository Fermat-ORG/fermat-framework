package com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.interfaces;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.middleware.wallet_contacts.DealsWithWalletFactory</code>
 * indicates that the plugin needs the functionality of a WalletFactoryProjectManager
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 08/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface DealsWithWalletFactory {

    /**
     * Throw this method the platform can set to the plugins who implements this interface the WalletFactory Manager
     * that they need to do the right job.
     *
     * @param walletFactoryProjectManager
     */
    void setWalletFactoryProjectManager(WalletFactoryProjectManager walletFactoryProjectManager);
}
