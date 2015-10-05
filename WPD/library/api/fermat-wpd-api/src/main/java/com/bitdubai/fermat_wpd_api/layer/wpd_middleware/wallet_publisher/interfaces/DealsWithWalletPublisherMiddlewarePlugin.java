/*
 * @#DealsWithWalletPublisherMiddlewarePlugin.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_publisher.interfaces;

/**
 * The Class <code>com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_publisher.interfaces.DealsWithWalletPublisherMiddlewarePlugin</code>
 * indicates that the plugin needs the functionality of a walletPublisherMiddlewarePlugin
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 04/07/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface DealsWithWalletPublisherMiddlewarePlugin {

    /**
     * Method that inject a instance of the walletPublisherMiddlewarePlugin
     *
     * @param walletPublisherMiddlewarePlugin
     */
    void setWalletPublisherMiddlewarePlugin(WalletPublisherMiddlewarePlugin walletPublisherMiddlewarePlugin);
}
