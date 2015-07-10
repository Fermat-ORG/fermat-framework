package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.middleware.wallet_contacts.DealsWithWalletPublisher</code>
 * indicates that the plugin needs the functionality of a WalletPublisherManager
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 08/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface DealsWithWalletPublisher {
    void setWalletPublisherManager(WalletPublisherManager walletPublisherManager);
}
