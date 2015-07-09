package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.middleware.wallet_contacts.DealsWithWalletFactory</code>
 * indicates that the plugin needs the functionality of a WalletFactoryManager
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 08/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface DealsWithWalletFactory {
    void setWalletFactoryManager(WalletFactoryManager walletFactoryManager);
}
