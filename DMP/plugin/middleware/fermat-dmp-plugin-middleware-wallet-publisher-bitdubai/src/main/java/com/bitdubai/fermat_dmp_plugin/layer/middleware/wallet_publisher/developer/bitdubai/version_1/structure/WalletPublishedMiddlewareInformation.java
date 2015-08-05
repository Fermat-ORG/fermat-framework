/*
 * @#WalletPublishedMiddlewareInformation.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces.WalletPublishedInformation;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.structure.WalletPublishedMiddlewareInformation</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 03/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletPublishedMiddlewareInformation implements WalletPublishedInformation {

    @Override
    public String getVersion() {
        return null;
    }

    @Override
    public UUID getWalletFactoryProjectId() {
        return null;
    }

    @Override
    public String getWalletFactoryProjectName() {
        return null;
    }

    @Override
    public UUID getWalletId() {
        return null;
    }

    @Override
    public long getPublicationTimestamp() {
        return 0;
    }

    @Override
    public long getVersionTimestamp() {
        return 0;
    }
}
