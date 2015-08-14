/*
 * @#WalletPublisherMiddlewareManagerImpl.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.exceptions.CantGetPublishedComponentInformationException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.exceptions.CantPublishComponetException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces.InformationPublishedComponent;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces.WalletPublisherMiddlewareManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.interfaces.WalletStoreManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.database.ComponentVersionDetailDao;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.database.InformationPublishedComponentDao;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.database.ScreensShotsComponentsDao;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.util.ImageManager;

import java.net.URL;
import java.util.List;
import java.util.UUID;

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
     * Represent the componentVersionDetailDao
     */
    private ComponentVersionDetailDao componentVersionDetailDao;

    /**
     * Represent the screensShotsComponentsDao
     */
    private ScreensShotsComponentsDao screensShotsComponentsDao;

    /**
     * Represent the walletStoreManager
     */
    private WalletStoreManager walletStoreManager;

    /**
     * Constructor whit parameters
     *
     * @param pluginOwnerId
     * @param pluginFileSystem
     * @param dataBase
     * @param walletStoreManager
     * @param logManager
     */
    public WalletPublisherMiddlewareManagerImpl(UUID pluginOwnerId, PluginFileSystem pluginFileSystem, Database dataBase, WalletStoreManager walletStoreManager, LogManager logManager) {
        this.walletStoreManager = walletStoreManager;
        this.informationPublishedComponentDao = new InformationPublishedComponentDao(dataBase, new ImageManager(pluginOwnerId, pluginFileSystem));
        this.componentVersionDetailDao = new ComponentVersionDetailDao(dataBase);
        this.screensShotsComponentsDao = new ScreensShotsComponentsDao(dataBase, new ImageManager(pluginOwnerId, pluginFileSystem));
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
     * @see WalletPublisherMiddlewareManager#getInformationPublishedComponentWithDetails(UUID)
     */
    @Override
    public InformationPublishedComponent getInformationPublishedComponentWithDetails(UUID idInformationPublishedComponent) throws CantGetPublishedComponentInformationException {
        return null;
    }

    /**
     * (non-Javadoc)
     * @see WalletPublisherMiddlewareManager#publishSkin(SkinDescriptorFactoryProject, byte[], byte[], List, URL, String, Version, Version, Version, Version, String)
     */
    @Override
    public void publishSkin(SkinDescriptorFactoryProject skinDescriptorFactoryProject, byte[] icon, byte[] mainScreenShot, List<byte[]> screenShotDetails, URL videoUrl,String observations, Version initialWalletVersion, Version finalWalletVersion, Version initialPlatformVersion, Version finalPlatformVersion, String publisherIdentityPublicKey) throws CantPublishComponetException {

    }

    /**
     * (non-Javadoc)
     * @see WalletPublisherMiddlewareManager#publishLanguage(LanguageDescriptorFactoryProject, byte[], byte[], String, Version, Version, Version, Version, String)
     */
    @Override
    public void publishLanguage(LanguageDescriptorFactoryProject languageDescriptorFactoryProject, byte[] icon, byte[] mainScreenShot,String observations, Version initialWalletVersion, Version finalWalletVersion, Version initialPlatformVersion, Version finalPlatformVersion, String publisherIdentityPublicKey) throws CantPublishComponetException {

    }

    /**
     * (non-Javadoc)
     * @see WalletPublisherMiddlewareManager#publishWallet(WalletDescriptorFactoryProject, byte[], byte[], List, URL, String, Version, Version, Version, Version, String)
     */
    @Override
    public void publishWallet(WalletDescriptorFactoryProject walletDescriptorFactoryProject, byte[] icon, byte[] mainScreenShot, List<byte[]> screenShotDetails, URL videoUrl,String observations, Version initialWalletVersion, Version finalWalletVersion, Version initialPlatformVersion, Version finalPlatformVersion, String publisherIdentityPublicKey) throws CantPublishComponetException{

    }

}
