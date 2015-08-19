/*
 * @#WalletPublisherMiddlewareManagerImpl.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.DescriptorFactoryProjectType;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.SkinDescriptorFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletDescriptorFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.LanguageDescriptorFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.enums.ComponentPublishedInformationStatus;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.exceptions.CantGetPublishedComponentInformationMiddlewareException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.exceptions.CantPublishComponentMiddlewareException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces.WalletPublisherMiddlewareManager;

import com.bitdubai.fermat_api.layer.dmp_module.wallet_publisher.interfaces.ComponentVersionDetail;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_publisher.interfaces.Image;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_publisher.interfaces.InformationPublishedComponent;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetWalletIconException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantPublishWalletInCatalogException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.CatalogItem;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.Language;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.Skin;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.WalletStoreManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.database.ComponentVersionDetailDao;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.database.InformationPublishedComponentDao;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.database.ScreensShotsComponentsDao;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.database.WalletPublisherMiddlewareDatabaseConstants;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.util.ImageManager;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
     * @see WalletPublisherMiddlewareManager#getPublishedComponents(String)
     */
    @Override
    public List<InformationPublishedComponent> getPublishedComponents(String publisherIdentityPublicKey) throws CantGetPublishedComponentInformationMiddlewareException {

        try {

            /*
             * Prepare the filters
             */
            Map<String, Object> filters = new HashMap<>();
            filters.put(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_PUBLISHER_IDENTITY_PUBLIC_KEY_COLUMN_NAME, publisherIdentityPublicKey);


            /*
             * Load the data from data base
             */
            return informationPublishedComponentDao.findAll(filters);

        } catch (CantReadRecordDataBaseException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * (non-Javadoc)
     * @see WalletPublisherMiddlewareManager#getPublishedWallets(String)
     */
    @Override
    public List<InformationPublishedComponent> getPublishedWallets(String publisherIdentityPublicKey) throws CantGetPublishedComponentInformationMiddlewareException {

        try {

            /*
             * Prepare the filters
             */
            Map<String, Object> filters = new HashMap<>();
            filters.put(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_PUBLISHER_IDENTITY_PUBLIC_KEY_COLUMN_NAME, publisherIdentityPublicKey);
            filters.put(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_COMPONENT_TYPE_COLUMN_NAME, DescriptorFactoryProjectType.WALLET.getCode());

            /*
             * Load the data from data base
             */
            return informationPublishedComponentDao.findAll(filters);

        } catch (CantReadRecordDataBaseException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * (non-Javadoc)
     * @see WalletPublisherMiddlewareManager#getPublishedSkins(String)
     */
    @Override
    public List<InformationPublishedComponent> getPublishedSkins(String publisherIdentityPublicKey) throws CantGetPublishedComponentInformationMiddlewareException {

        try {

            /*
             * Prepare the filters
             */
            Map<String, Object> filters = new HashMap<>();
            filters.put(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_PUBLISHER_IDENTITY_PUBLIC_KEY_COLUMN_NAME, publisherIdentityPublicKey);
            filters.put(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_COMPONENT_TYPE_COLUMN_NAME, DescriptorFactoryProjectType.SKIN.getCode());


            /*
             * Load the data from data base
             */
            return informationPublishedComponentDao.findAll(filters);

        } catch (CantReadRecordDataBaseException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * (non-Javadoc)
     * @see WalletPublisherMiddlewareManager#getPublishedLanguages(String)
     */
    @Override
    public List<InformationPublishedComponent> getPublishedLanguages(String publisherIdentityPublicKey) throws CantGetPublishedComponentInformationMiddlewareException {

        try {

            /*
             * Prepare the filters
             */
            Map<String, Object> filters = new HashMap<>();
            filters.put(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_PUBLISHER_IDENTITY_PUBLIC_KEY_COLUMN_NAME, publisherIdentityPublicKey);
            filters.put(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_COMPONENT_TYPE_COLUMN_NAME, DescriptorFactoryProjectType.LANGUAGE.getCode());


            /*
             * Load the data from data base
             */
            return informationPublishedComponentDao.findAll(filters);

        } catch (CantReadRecordDataBaseException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * (non-Javadoc)
     * @see WalletPublisherMiddlewareManager#getInformationPublishedComponentWithDetails(UUID)
     */
    @Override
    public InformationPublishedComponentMiddlewareImpl getInformationPublishedComponentWithDetails(UUID idInformationPublishedComponent) throws CantGetPublishedComponentInformationMiddlewareException {


        InformationPublishedComponentMiddlewareImpl informationPublishedComponentMiddlewareImpl = null;

        try {

            /*
             * Load the data from data base
             */
            informationPublishedComponentMiddlewareImpl = (InformationPublishedComponentMiddlewareImpl) informationPublishedComponentDao.findById(idInformationPublishedComponent);

            /*
             * Load all details from data base
             */
            List<ComponentVersionDetail> details = componentVersionDetailDao.findAll(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_COMPONENT_ID_COLUMN_NAME, idInformationPublishedComponent.toString());
            informationPublishedComponentMiddlewareImpl.setComponentVersionDetailList(details);

            /*
             * Load all screens shots
             */
            List<Image> images = screensShotsComponentsDao.findAll(WalletPublisherMiddlewareDatabaseConstants.SCREENS_SHOTS_COMPONENTS_COMPONENT_ID_COLUMN_NAME, idInformationPublishedComponent.toString());
            informationPublishedComponentMiddlewareImpl.setScreensShotsComponentList(images);

        } catch (CantReadRecordDataBaseException e) {
            e.printStackTrace();
        }

        return informationPublishedComponentMiddlewareImpl;
    }

    /**
     * (non-Javadoc)
     * @see WalletPublisherMiddlewareManager#publishSkin(SkinDescriptorFactoryProject, byte[], byte[], List, URL, String, Version, Version, Version, Version, String, String)
     */
    @Override
    public void publishSkin(SkinDescriptorFactoryProject skinDescriptorFactoryProject, byte[] icon, byte[] mainScreenShot, List<byte[]> screenShotDetails, URL videoUrl,String observations, Version initialWalletVersion, Version finalWalletVersion, Version initialPlatformVersion, Version finalPlatformVersion, String publisherIdentityPublicKey, String signature) throws CantPublishComponentMiddlewareException {

    }

    /**
     * (non-Javadoc)
     * @see WalletPublisherMiddlewareManager#publishLanguage(LanguageDescriptorFactoryProject, byte[], byte[], String, Version, Version, Version, Version, String, String)
     */
    @Override
    public void publishLanguage(LanguageDescriptorFactoryProject languageDescriptorFactoryProject, byte[] icon, byte[] mainScreenShot,String observations, Version initialWalletVersion, Version finalWalletVersion, Version initialPlatformVersion, Version finalPlatformVersion, String publisherIdentityPublicKey, String signature) throws CantPublishComponentMiddlewareException {

    }

    /**
     * (non-Javadoc)
     * @see WalletPublisherMiddlewareManager#publishWallet(WalletDescriptorFactoryProject, WalletCategory, byte[], byte[], List, URL, String, Version, Version, Version, Version, String, String)
     */
    @Override
    public void publishWallet(WalletDescriptorFactoryProject walletDescriptorFactoryProject, WalletCategory walletCategory, byte[] icon, byte[] mainScreenShot, List<byte[]> screenShotDetails, URL videoUrl,String observations, Version initialWalletVersion, Version finalWalletVersion, Version initialPlatformVersion, Version finalPlatformVersion, String publisherIdentityPublicKey, String signature) throws CantPublishComponentMiddlewareException {

        try {

            Version defaultVersion = new Version(1, 0, 0);
            CatalogItem catalogItem = constructCatalogItemObject(walletDescriptorFactoryProject, walletCategory, defaultVersion, icon, mainScreenShot, screenShotDetails, videoUrl, initialWalletVersion, finalWalletVersion, initialPlatformVersion, finalPlatformVersion);

            /* ----------------------------------------
             * Create the informationPublishedComponent
             * ----------------------------------------
             */
            InformationPublishedComponentMiddlewareImpl informationPublishedComponentMiddlewareImpl = new InformationPublishedComponentMiddlewareImpl();
            informationPublishedComponentMiddlewareImpl.setId(UUID.randomUUID());
            informationPublishedComponentMiddlewareImpl.setDescriptorFactoryProjectId(walletDescriptorFactoryProject.getId());
            informationPublishedComponentMiddlewareImpl.setDescriptorFactoryProjectName(walletDescriptorFactoryProject.getName());
            informationPublishedComponentMiddlewareImpl.setDescriptions(walletDescriptorFactoryProject.getDescription());
            informationPublishedComponentMiddlewareImpl.setStatus(ComponentPublishedInformationStatus.REQUESTED);
            informationPublishedComponentMiddlewareImpl.setStatusTimestamp(new Timestamp(System.currentTimeMillis()));
            informationPublishedComponentMiddlewareImpl.setPublisherIdentityPublicKey(publisherIdentityPublicKey);
            informationPublishedComponentMiddlewareImpl.setSignature(signature);

            //Create the icon image
            ImageMiddlewareImpl iconImg = new ImageMiddlewareImpl();
            iconImg.setFileId(UUID.randomUUID());
            iconImg.setComponentId(informationPublishedComponentMiddlewareImpl.getId());
            iconImg.setData(icon);

            informationPublishedComponentMiddlewareImpl.setIconImg(iconImg);

            // Create the mainScreenShots image
            ImageMiddlewareImpl mainScreenShotImg = new ImageMiddlewareImpl();
            mainScreenShotImg.setFileId(UUID.randomUUID());
            mainScreenShotImg.setComponentId(informationPublishedComponentMiddlewareImpl.getId());
            mainScreenShotImg.setData(mainScreenShot);

            informationPublishedComponentMiddlewareImpl.setMainScreenShotImg(mainScreenShotImg);

            // Save into data base
            informationPublishedComponentDao.create(informationPublishedComponentMiddlewareImpl);

            /* --------------------------
             * Create the version details
             * --------------------------
             */
            ComponentVersionDetailMiddlewareImpl componentVersionDetailMiddlewareImpl = new ComponentVersionDetailMiddlewareImpl();
            componentVersionDetailMiddlewareImpl.setId(UUID.randomUUID());
            componentVersionDetailMiddlewareImpl.setVersion(defaultVersion);
            componentVersionDetailMiddlewareImpl.setVersionTimestamp(new Timestamp(System.currentTimeMillis()));
            componentVersionDetailMiddlewareImpl.setInitialWalletVersion(initialWalletVersion);
            componentVersionDetailMiddlewareImpl.setFinalWalletVersion(finalWalletVersion);
            componentVersionDetailMiddlewareImpl.setInitialPlatformVersion(initialPlatformVersion);
            componentVersionDetailMiddlewareImpl.setFinalPlatformVersion(finalPlatformVersion);
            componentVersionDetailMiddlewareImpl.setObservations(observations);
            componentVersionDetailMiddlewareImpl.setScreenSize(walletDescriptorFactoryProject.getSkins().get(0).getScreenSize());
            componentVersionDetailMiddlewareImpl.setComponentId(informationPublishedComponentMiddlewareImpl.getId());
            componentVersionDetailMiddlewareImpl.setCatalogId(catalogItem.getId());

            // Save into data base
            componentVersionDetailDao.create(componentVersionDetailMiddlewareImpl);

            /* -------------------------------------
             * Create all screenShots images details
             * -------------------------------------
             */
            for (byte[] screen : screenShotDetails){

                ImageMiddlewareImpl screenShotImg = new ImageMiddlewareImpl();
                screenShotImg.setFileId(UUID.randomUUID());
                screenShotImg.setComponentId(informationPublishedComponentMiddlewareImpl.getId());
                screenShotImg.setData(mainScreenShot);

                // Save into data base
                screensShotsComponentsDao.create(screenShotImg);
            }

            /* -------------------------------------
             * Publish into the wallet Store
             * -------------------------------------
             */
            walletStoreManager.publishWallet(catalogItem);

            /*
             * If publish proccess is ok change the status and update in the database
             */
            informationPublishedComponentMiddlewareImpl.setStatus(ComponentPublishedInformationStatus.PUBLISHED);
            informationPublishedComponentDao.update(informationPublishedComponentMiddlewareImpl);

        } catch (CantPublishWalletInCatalogException e) {
            e.printStackTrace();
        } catch (CantInsertRecordDataBaseException e) {
            e.printStackTrace();
        } catch (CantUpdateRecordDataBaseException e) {
            e.printStackTrace();
        } catch (CantGetWalletIconException e) {
            e.printStackTrace();
        }

    }


    /**
     * This method encapsulate the creation of a object CatalogItem
     *
     * @param walletDescriptorFactoryProject
     * @param walletCategory
     * @param version
     * @param icon
     * @param mainScreenShot
     * @param screenShotDetails
     * @param videoUrl
     * @param initialWalletVersion
     * @param finalWalletVersion
     * @param initialPlatformVersion
     * @param finalPlatformVersion
     * @return CatalogItem
     * @throws CantGetWalletIconException
     */
    private CatalogItem constructCatalogItemObject(WalletDescriptorFactoryProject walletDescriptorFactoryProject, WalletCategory walletCategory, Version version, byte[] icon, byte[] mainScreenShot, List<byte[]> screenShotDetails, URL videoUrl, Version initialWalletVersion, Version finalWalletVersion, Version initialPlatformVersion, Version finalPlatformVersion) throws CantGetWalletIconException {

        /*
         * Construct the videos urls
         */
        List<URL> videoPreviews = new ArrayList<URL>();
        if (videoUrl != null) {
            videoPreviews.add(videoUrl);
        }


        /*
         * Construct the default skin
         */
        SkinDescriptorFactoryProject defaultSkinDescriptorFactoryProject = walletDescriptorFactoryProject.getSkins().get(0); //TODO: Get from the descriptor
        Skin defaultSkin = constructSkinObject(defaultSkinDescriptorFactoryProject,
                version,
                mainScreenShot,
                screenShotDetails,
                (!videoPreviews.isEmpty()),
                videoPreviews,
                initialWalletVersion,
                finalWalletVersion);


        /*
         * Create other supported skins list
         */
        List<Skin> otherSkinSupportedList = new ArrayList<>();
        for (SkinDescriptorFactoryProject skinDescriptorFactoryProject : walletDescriptorFactoryProject.getSkins()){

            /*
             * Construct
             */
            Skin skin = constructSkinObject(skinDescriptorFactoryProject,
                    version,
                    mainScreenShot,
                    screenShotDetails,
                    (!videoPreviews.isEmpty()),
                    videoPreviews,
                    initialWalletVersion,
                    finalWalletVersion);

            /*
             * Add to the list
             */
            otherSkinSupportedList.add(skin);
        }


        /*
         * Construct the default language
         */
        LanguageDescriptorFactoryProject defaultLanguageDescriptorFactoryProject =  walletDescriptorFactoryProject.getLanguages().get(0); //TODO: Get from the descriptor
        Language defaultLanguage = constructLanguageObject(defaultLanguageDescriptorFactoryProject,
                version,
                videoPreviews,
                initialWalletVersion,
                finalWalletVersion);
        /*
         * Create other supported languages list
         */
        List<Language> otherLanguageSupportedList = new ArrayList<>();
        for (LanguageDescriptorFactoryProject languageDescriptorFactoryProject : walletDescriptorFactoryProject.getLanguages()){

            /*
            * Construct
            */
            Language language = constructLanguageObject(languageDescriptorFactoryProject,
                    version,
                    videoPreviews,
                    initialWalletVersion,
                    finalWalletVersion);
            /*
             * Add to the list
             */
            otherLanguageSupportedList.add(language);
        }

        /*
         * Construct the catalog item instance
         */
        return walletStoreManager.constructCatalogItem(walletDescriptorFactoryProject.getId(),
                                                        0, //TODO: defaultSizeInBytes
                                                        walletDescriptorFactoryProject.getName(),
                                                        walletDescriptorFactoryProject.getDescription(),
                                                        walletCategory,
                                                        icon,
                                                        version,
                                                        initialPlatformVersion,
                                                        finalPlatformVersion,
                                                        otherSkinSupportedList,
                                                        defaultSkin,
                                                        defaultLanguage,
                                                        otherLanguageSupportedList,
                                                        null, //TODO: designer
                                                        null, //TODO: developer
                                                        null  //TODO: translator
                                                        );
    }

    /**
     * This method encapsulate the creation of a object Skin
     *
     * @param skinDescriptorFactoryProject
     * @param version
     * @param mainScreenShot
     * @param screenShotDetails
     * @param hasVideoPreview
     * @param videoPreviews
     * @param initialWalletVersion
     * @param finalWalletVersion
     * @return Skin
     */
   private Skin constructSkinObject(SkinDescriptorFactoryProject skinDescriptorFactoryProject, Version version, byte[] mainScreenShot, List<byte[]> screenShotDetails, boolean hasVideoPreview, List<URL> videoPreviews, Version initialWalletVersion, Version finalWalletVersion){


       /*
        * Construct the new instance
        */
       return walletStoreManager.constructSkin(skinDescriptorFactoryProject.getId(),
                                               skinDescriptorFactoryProject.getName(),
                                               skinDescriptorFactoryProject.getWalletId(),
                                               skinDescriptorFactoryProject.getScreenSize(),
                                               version,
                                               initialWalletVersion,
                                               finalWalletVersion,
                                               mainScreenShot,
                                               screenShotDetails,
                                               hasVideoPreview,
                                               videoPreviews,
                                               null, // TODO: skinURL cambiar por la pagina del publisher
                                               0,    // TODO: skinSizeInBytes
                                               null, // TODO: designer
                                               Boolean.TRUE);

   }

    /**
     * This method encapsulate the creation of a object Language
     *
     * @param languageDescriptorFactoryProject
     * @param version
     * @param videoPreviews
     * @param initialWalletVersion
     * @param finalWalletVersion
     * @return Language
     */
    private Language constructLanguageObject(LanguageDescriptorFactoryProject languageDescriptorFactoryProject, Version version, List<URL> videoPreviews, Version initialWalletVersion, Version finalWalletVersion){

        /*
        * Construct the new instance
        */
        return walletStoreManager.constructLanguage(languageDescriptorFactoryProject.getId(),
                                                    languageDescriptorFactoryProject.getName(),
                                                    languageDescriptorFactoryProject.getDescription(), //TODO: Get from the descriptor languageLabel
                                                    languageDescriptorFactoryProject.getWalletId(),
                                                    version,
                                                    initialWalletVersion,
                                                    finalWalletVersion,
                                                    videoPreviews,
                                                    null, // TODO: LanguageURL cambiar por la pagina del publisher
                                                    0,    // TODO: languajeSizeInBytes
                                                    null, // TODO: designer
                                                    Boolean.TRUE);

    }

}