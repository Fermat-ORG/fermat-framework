/*
 * @#DealsWithWalletPublisher.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */

package com.bitdubai.fermat_api.layer.dmp_module.wallet_publisher.interfaces;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.dmp_module.wallet_publisher.interfaces.DealsWithWalletPublisher</code>
 * indicates that the plugin needs the functionality of a WalletPublisherManager
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 08/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface DealsWithWalletPublisher {

    /**
     * Method that inject a instance of the WalletPublisherManager
     *
     * @param walletPublisherManager
     */
    void setWalletPublisherManager(WalletPublisherManager walletPublisherManager);
}
