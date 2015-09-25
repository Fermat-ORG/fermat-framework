package com.bitdubai.fermat_api.layer.ccp_middleware.wallet_language.interfaces;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.middleware.wallet_language.DealsWithWalletLanguage</code>
 * indicates that the plugin needs the functionality of a WalletLanguageManager
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 29/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface DealsWithWalletLanguage {

    /**
     * Throw this method the platform can set to the plugins who implements this interface the WalletLanguage Manager
     * that they need to do the right job.
     *
     * @param walletLanguageManager
     */
    void setWalletLanguageManager(WalletLanguageManager walletLanguageManager);
}
