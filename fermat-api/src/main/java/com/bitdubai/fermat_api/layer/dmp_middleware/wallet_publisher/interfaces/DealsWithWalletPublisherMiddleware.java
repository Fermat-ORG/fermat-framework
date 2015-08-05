/*
 * @#DealsWithWalletPublisherMiddleware.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces.DealsWithWalletPublisherMiddleware</code>
 * indicates that the plugin needs the functionality of a WalletPublisherMiddlewareManager
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 04/07/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface DealsWithWalletPublisherMiddleware {

    /**
     * Method that inject a instance of the WalletPublisherMiddlewareManager
     *
     * @param walletPublisherMiddlewareManager
     */
    void setWalletPublisherMiddlewareManager(WalletPublisherMiddlewareManager walletPublisherMiddlewareManager);
}
