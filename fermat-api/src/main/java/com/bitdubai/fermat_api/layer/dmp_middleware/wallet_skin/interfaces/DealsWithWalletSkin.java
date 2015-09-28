package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.interfaces;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.middleware.wallet_skin.DealsWithWalletSkin</code>
 * indicates that the plugin needs the functionality of a WalletSkinManager
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 29/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface DealsWithWalletSkin {

    /**
     * Throw this method the platform can set to the plugins who implements this interface the WalletSkin Manager
     * that they need to do the right job.
     *
     * @param walletSkinManager
     */
    void setWalletSkinManager(WalletSkinManager walletSkinManager);
}
