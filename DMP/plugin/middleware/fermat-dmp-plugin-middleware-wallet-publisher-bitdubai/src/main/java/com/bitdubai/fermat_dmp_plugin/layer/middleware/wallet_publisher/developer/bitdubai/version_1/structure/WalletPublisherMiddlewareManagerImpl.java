/*
 * @#WalletPublisherMiddlewareManagerImpl.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.SkinDescriptorFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.LanguageDescriptorFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.exceptions.CantGetPublishedComponentInformationException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.exceptions.CantPublishComponetException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces.InformationPublishedComponent;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces.WalletPublisherMiddlewareManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.interfaces.WalletStoreManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.database.InformationPublishedComponentDao;

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
     * Represent the logManager
     */
    private LogManager logManager;

    /**
     * Represent the informationPublishedComponentDao
     */
    private InformationPublishedComponentDao informationPublishedComponentDao;

    /**
     * Represent the walletFactoryManager
     */
    private WalletFactoryManager walletFactoryManager;

    /**
     * Represent the walletStoreManager
     */
    private WalletStoreManager walletStoreManager;


    /**
     * Constructor whit parameters
     *
     * @param dataBase
     * @param walletStoreManager
     * @param walletFactoryManager
     * @param logManager
     */
    public WalletPublisherMiddlewareManagerImpl(Database dataBase, WalletStoreManager walletStoreManager,  WalletFactoryManager walletFactoryManager, LogManager logManager) {
        this.walletStoreManager = walletStoreManager;
        this.informationPublishedComponentDao = new InformationPublishedComponentDao(dataBase);
        this.walletFactoryManager = walletFactoryManager;
        this.logManager = logManager;
    }


    /**
     * (non-Javadoc)
     * @see WalletPublisherMiddlewareManager#getPublishedComponents()
     */
    @Override
    public List<InformationPublishedComponent> getPublishedComponents() throws CantGetPublishedComponentInformationException {
        return null;
    }

    /**
     * (non-Javadoc)
     * @see WalletPublisherMiddlewareManager#getPublishedWallets()
     */
    @Override
    public List<InformationPublishedComponent> getPublishedWallets() throws CantGetPublishedComponentInformationException {
        return null;
    }

    /**
     * (non-Javadoc)
     * @see WalletPublisherMiddlewareManager#getPublishedSkins()
     */
    @Override
    public List<InformationPublishedComponent> getPublishedSkins() throws CantGetPublishedComponentInformationException {
        return null;
    }

    /**
     * (non-Javadoc)
     * @see WalletPublisherMiddlewareManager#getPublishedLanguages()
     */
    @Override
    public List<InformationPublishedComponent> getPublishedLanguages() throws CantGetPublishedComponentInformationException {
        return null;
    }

    /**
     * (non-Javadoc)
     * @see WalletPublisherMiddlewareManager#publishSkin(SkinDescriptorFactoryProject)
     */
    @Override
    public void publishSkin(SkinDescriptorFactoryProject skinDescriptorFactoryProject) throws CantPublishComponetException {

    }

    /**
     * (non-Javadoc)
     * @see WalletPublisherMiddlewareManager#publishLanguage(LanguageDescriptorFactoryProject)
     */
    @Override
    public void publishLanguage(LanguageDescriptorFactoryProject languageDescriptorFactoryProject) throws CantPublishComponetException {

    }

}
