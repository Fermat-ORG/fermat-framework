/*
 * @#WalletPublisherMiddlewareManagerImpl.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectProposal;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.exceptions.CantCheckPublicationException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.exceptions.CantGetPublishedWalletsInformationException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.exceptions.CantPublishWalletException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces.WalletPublishedInformation;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces.WalletPublisherManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces.WalletPublisherMiddlewareManager;

import java.util.List;
import java.util.Map;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.structure.WalletPublisherMiddlewareManagerImpl</code> have
 * all the implementation of the business logic
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 03/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletPublisherMiddlewareManagerImpl implements WalletPublisherMiddlewareManager {

    /**
     * (non-Javadoc)
     * @see WalletPublisherManager#showPublishedWallets()
     */
    @Override
    public Map<String, List<WalletPublishedInformation>> showPublishedWallets() throws CantGetPublishedWalletsInformationException {
        return null;
    }

    /**
     * (non-Javadoc)
     * @see WalletPublisherManager#canBePublished(WalletFactoryProjectProposal)
     */
    @Override
    public boolean canBePublished(WalletFactoryProjectProposal walletFactoryProjectProposal) throws CantCheckPublicationException {
        return false;
    }

    /**
     * (non-Javadoc)
     * @see WalletPublisherManager#publishWallet(WalletFactoryProjectProposal)
     */
    @Override
    public void publishWallet(WalletFactoryProjectProposal walletFactoryProjectProposal) throws CantPublishWalletException {

    }

    /**
     * (non-Javadoc)
     * @see WalletPublisherManager#publishSkin(WalletFactoryProjectProposal)
     */
    @Override
    public void publishSkin(WalletFactoryProjectProposal walletFactoryProjectProposal) throws CantPublishWalletException {

    }

    /**
     * (non-Javadoc)
     * @see WalletPublisherManager#publishLanguage(WalletFactoryProjectProposal)
     */
    @Override
    public void publishLanguage(WalletFactoryProjectProposal walletFactoryProjectProposal) throws CantPublishWalletException {

    }
}
