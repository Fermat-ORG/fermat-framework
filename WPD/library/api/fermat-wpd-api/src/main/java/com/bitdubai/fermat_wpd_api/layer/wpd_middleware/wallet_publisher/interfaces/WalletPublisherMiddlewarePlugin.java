/*
 * @#WalletPublisherMiddlewarePlugin.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_publisher.interfaces;

/**
 * The Class <code>com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_publisher.interfaces.WalletPublisherMiddlewarePlugin</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 14/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface WalletPublisherMiddlewarePlugin {

    /**
     * Return a instance of the publisher middleware manager
     *
     * @return WalletPublisherMiddlewareManager
     */
    public WalletPublisherMiddlewareManager getWalletPublisherMiddlewareManagerInstance();
}
