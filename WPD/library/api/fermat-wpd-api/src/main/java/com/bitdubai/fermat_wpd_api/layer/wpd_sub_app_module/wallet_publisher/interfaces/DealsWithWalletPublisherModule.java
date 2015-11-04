/*
 * @#DealsWithWalletPublisherModule.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */

package com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.interfaces;

/**
 * The Class <code>com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.interfaces.DealsWithWalletPublisherModule</code>
 * indicates that the plugin needs the functionality of a WalletPublisherModuleManager
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 08/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface DealsWithWalletPublisherModule {

    /**
     * Method that inject a instance of the WalletPublisherModuleManager
     *
     * @param walletPublisherManager
     */
    void setWalletPublisherManager(WalletPublisherModuleManager walletPublisherManager);
}
