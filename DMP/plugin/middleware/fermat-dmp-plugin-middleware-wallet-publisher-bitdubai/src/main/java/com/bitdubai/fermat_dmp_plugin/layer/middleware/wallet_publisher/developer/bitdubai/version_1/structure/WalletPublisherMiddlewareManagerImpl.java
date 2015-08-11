/*
 * @#WalletPublisherMiddlewareManagerImpl.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectsException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectLanguage;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectSkin;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.DescriptorFactoryProjectType;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.exceptions.CantCheckPublicationException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.exceptions.CantGetPublishedComponentInformationException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.exceptions.CantPublishComponetException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces.ComponentPublishedInformation;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces.WalletPublisherMiddlewareManager;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantPublishWalletInCatalogException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.CatalogItem;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.WalletStoreManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.database.WalletPublisherMiddlewareDao;

import java.util.HashMap;
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
     * Represent the walletPublisherMiddlewareDao
     */
    private WalletPublisherMiddlewareDao walletPublisherMiddlewareDao;

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
        this.walletPublisherMiddlewareDao = new WalletPublisherMiddlewareDao(dataBase);
        this.walletFactoryManager = walletFactoryManager;
        this.logManager = logManager;
    }

    /**
     * (non-Javadoc)
     * @see WalletPublisherMiddlewareManager#getWalletFactoryProjectToPublish()
     */
    @Override
    public List<WalletFactoryProject> getWalletFactoryProjectToPublish(){

        List<WalletFactoryProject> resultList = null;

        try {

            /**
             * Find all WFP Closed
             */
            resultList =  walletFactoryManager.getAllWalletFactoryProjectsClosed();

        } catch (CantGetWalletFactoryProjectsException e) {
            e.printStackTrace();
        }

        return  resultList;
    }

    /**
     * (non-Javadoc)
     * @see WalletPublisherMiddlewareManager#getPublishedComponents()
     */
    @Override
    public Map<String, List<ComponentPublishedInformation>> getPublishedComponents() throws CantGetPublishedComponentInformationException {






        return null;
    }

    /**
     * (non-Javadoc)
     * @see WalletPublisherMiddlewareManager#getPublishedWallets()
     */
    @Override
    public Map<String, List<ComponentPublishedInformation>> getPublishedWallets() throws CantGetPublishedComponentInformationException {

        Map<String, Object> filters = new HashMap<>();
        filters.put("type", DescriptorFactoryProjectType.WALLET);



        return null;
    }

    /**
     * (non-Javadoc)
     * @see WalletPublisherMiddlewareManager#getPublishedSkins()
     */
    @Override
    public Map<String, List<ComponentPublishedInformation>> getPublishedSkins() throws CantGetPublishedComponentInformationException {
        return null;
    }

    /**
     * (non-Javadoc)
     * @see WalletPublisherMiddlewareManager#getPublishedLanguages()
     */
    @Override
    public Map<String, List<ComponentPublishedInformation>> getPublishedLanguages() throws CantGetPublishedComponentInformationException {
        return null;
    }

    /**
     * (non-Javadoc)
     * @see WalletPublisherMiddlewareManager#canBePublished(WalletFactoryProject)
     */
    @Override
    public boolean canBePublished(WalletFactoryProject walletFactoryProject) throws CantCheckPublicationException {
        return false;
    }

    /**
     * (non-Javadoc)
     * @see WalletPublisherMiddlewareManager#publishWallet(WalletFactoryProject)
     */
    @Override
    public void publishWallet(WalletFactoryProject walletFactoryProject) throws CantPublishComponetException {

        /**
         * Obtain a empty catalogItem
         */
        CatalogItem catalogItem = walletStoreManager.constructEmptyCatalogItem();

        try {

            /*
             * Configure the empty catalogItem
             */



            /*
             * Publish the wallet
             */
            walletStoreManager.publishWallet(catalogItem);


            /*
             * Create the published information
             */
             ComponentPublishedMiddlewareInformation componentPublishedMiddlewareInformation = new ComponentPublishedMiddlewareInformation();
             componentPublishedMiddlewareInformation.setCatalogId(catalogItem.getId());
             componentPublishedMiddlewareInformation.getFinalPlatformVersion();


        } catch (CantPublishWalletInCatalogException e) {
            e.printStackTrace();
        }

    }

    /**
     * (non-Javadoc)
     * @see WalletPublisherMiddlewareManager#publishSkin(WalletFactoryProjectSkin)
     */
    @Override
    public void publishSkin(WalletFactoryProjectSkin walletFactoryProjectSkin) throws CantPublishComponetException {

    }

    /**
     * (non-Javadoc)
     * @see WalletPublisherMiddlewareManager#publishLanguage(WalletFactoryProjectLanguage)
     */
    @Override
    public void publishLanguage(WalletFactoryProjectLanguage walletFactoryProjectLanguage) throws CantPublishComponetException {

    }

}
