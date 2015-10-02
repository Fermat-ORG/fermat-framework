/*
 * @#WalletPublisherMiddlewareManagerImpl.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.enums.FactoryProjectType;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.interfaces.WalletFactoryProject;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_publisher.enums.ComponentPublishedInformationStatus;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_publisher.exceptions.CantGetPublishedComponentInformationMiddlewareException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_publisher.exceptions.CantPublishComponentMiddlewareException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_publisher.interfaces.WalletPublisherMiddlewareManager;

import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.enums.InformationPublishedComponentType;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.interfaces.ComponentVersionDetail;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.interfaces.Image;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.interfaces.InformationPublishedComponent;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetWalletIconException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.CatalogItem;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.WalletStoreManager;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Language;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.database.ComponentVersionDetailDao;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.database.InformationPublishedComponentDao;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.database.ScreensShotsComponentsDao;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.database.WalletPublisherMiddlewareDatabaseConstants;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.util.ImageManager;

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

    public static final String IMAGE_PATH_DIRECTORY = "wallet_publisher/images";

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
     *
     * @see WalletPublisherMiddlewareManager#getPublishedComponents(String)
     */
    @Override
    public List<InformationPublishedComponent> getPublishedComponents(String publisherIdentityPublicKey) throws CantGetPublishedComponentInformationMiddlewareException {

        try {

            /*
             * Load the data from data base
             */
            return informationPublishedComponentDao.findAll(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_PUBLISHER_IDENTITY_PUBLIC_KEY_COLUMN_NAME, publisherIdentityPublicKey);

        } catch (CantReadRecordDataBaseException e) {
            //TODO METODO NO IMPLEMENTADO AUN - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
            e.printStackTrace();
        }
        //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
        return null;
    }

    /**
     * (non-Javadoc)
     *
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
            filters.put(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_COMPONENT_TYPE_COLUMN_NAME, FactoryProjectType.WALLET.getCode());

            /*
             * Load the data from data base
             */
            return informationPublishedComponentDao.findAll(filters);

        } catch (CantReadRecordDataBaseException e) {
            //TODO METODO NO IMPLEMENTADO AUN - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
            e.printStackTrace();
        }
        //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
        return null;
    }

    /**
     * (non-Javadoc)
     *
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
            filters.put(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_COMPONENT_TYPE_COLUMN_NAME, FactoryProjectType.SKIN.getCode());


            /*
             * Load the data from data base
             */
            return informationPublishedComponentDao.findAll(filters);

        } catch (CantReadRecordDataBaseException e) {
            //TODO METODO NO IMPLEMENTADO AUN - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
            e.printStackTrace();
        }
        //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
        return null;
    }

    /**
     * (non-Javadoc)
     *
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
            filters.put(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_COMPONENT_TYPE_COLUMN_NAME, FactoryProjectType.LANGUAGE.getCode());

            /*
             * Load the data from data base
             */
            return informationPublishedComponentDao.findAll(filters);

        } catch (CantReadRecordDataBaseException e) {
            //TODO METODO NO IMPLEMENTADO AUN - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
            e.printStackTrace();
        }
        //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
        return null;
    }

    /**
     * (non-Javadoc)
     *
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
     *
     * @see WalletPublisherMiddlewareManager#publishSkin(WalletFactoryProject, byte[], byte[], List, URL, String, Version, Version, URL, String, String)
     */
    @Override
    public void publishSkin(WalletFactoryProject walletFactoryProject, byte[] icon, byte[] mainScreenShot, List<byte[]> screenShotDetails, URL videoUrl, String observations, Version initialWalletVersion, Version finalWalletVersion, URL publisherWebsiteUrl, String publisherIdentityPublicKey, String signature) throws CantPublishComponentMiddlewareException {

         /*
         * Construct the  Information Published Component
         */
        InformationPublishedComponentMiddlewareImpl informationPublishedComponentMiddlewareImpl = new InformationPublishedComponentMiddlewareImpl();

        /*
         * Configure type
         */
        informationPublishedComponentMiddlewareImpl.setType(InformationPublishedComponentType.SKIN);

        /*
         * publish the component
         */
        publishComponent(informationPublishedComponentMiddlewareImpl, walletFactoryProject, icon, mainScreenShot, screenShotDetails, videoUrl, observations, initialWalletVersion, finalWalletVersion, new Version(1, 0, 0), new Version(1, 0, 0), publisherWebsiteUrl, publisherIdentityPublicKey, signature);

    }

    /**
     * (non-Javadoc)
     *
     * @see WalletPublisherMiddlewareManager#publishLanguage(WalletFactoryProject, byte[], byte[], List, URL, String, Version, Version, URL, String, String)
     */
    @Override
    public void publishLanguage(WalletFactoryProject walletFactoryProject, byte[] icon, byte[] mainScreenShot, List<byte[]> screenShotDetails, URL videoUrl, String observations, Version initialWalletVersion, Version finalWalletVersion, URL publisherWebsiteUrl, String publisherIdentityPublicKey, String signature) throws CantPublishComponentMiddlewareException {

        /*
         * Construct the  Information Published Component
         */
        InformationPublishedComponentMiddlewareImpl informationPublishedComponentMiddlewareImpl = new InformationPublishedComponentMiddlewareImpl();

        /*
         * Configure type
         */
        informationPublishedComponentMiddlewareImpl.setType(InformationPublishedComponentType.LANGUAGE);

        /*
         * publish the component
         */
        publishComponent(informationPublishedComponentMiddlewareImpl, walletFactoryProject, icon, mainScreenShot, screenShotDetails, videoUrl, observations, initialWalletVersion, finalWalletVersion, new Version(1, 0, 0), new Version(1, 0, 0), publisherWebsiteUrl, publisherIdentityPublicKey, signature);

    }

    /**
     * (non-Javadoc)
     *
     * @see WalletPublisherMiddlewareManager#publishWallet(WalletFactoryProject, byte[], byte[], List, URL, String, Version, Version, URL, String, String)
     */
    @Override
    public void publishWallet(WalletFactoryProject walletFactoryProject, byte[] icon, byte[] mainScreenShot, List<byte[]> screenShotDetails, URL videoUrl, String observations, Version initialPlatformVersion, Version finalPlatformVersion, URL publisherWebsiteUrl, String publisherIdentityPublicKey, String signature) throws CantPublishComponentMiddlewareException {

        /*
         * Construct the  Information Published Component
         */
        InformationPublishedComponentMiddlewareImpl informationPublishedComponentMiddlewareImpl = new InformationPublishedComponentMiddlewareImpl();

        /*
         * Configure type
         */
        informationPublishedComponentMiddlewareImpl.setType(InformationPublishedComponentType.WALLET);

        /*
         * publish the component
         */
        publishComponent(informationPublishedComponentMiddlewareImpl, walletFactoryProject, icon, mainScreenShot, screenShotDetails, videoUrl, observations, new Version(1, 0, 0), new Version(1, 0, 0), initialPlatformVersion, finalPlatformVersion, publisherWebsiteUrl, publisherIdentityPublicKey, signature);
    }


    /**
     * This method have all logic to publish a component in the wallet store
     *
     * @param informationPublishedComponentMiddlewareImpl
     * @param walletFactoryProject
     * @param icon
     * @param mainScreenShot
     * @param screenShotDetails
     * @param videoUrl
     * @param observations
     * @param initialWalletVersion
     * @param finalWalletVersion
     * @param initialPlatformVersion
     * @param finalPlatformVersion
     * @param publisherWebsiteUrl
     * @param publisherIdentityPublicKey
     * @param signature
     * @throws CantPublishComponentMiddlewareException
     */
    private void publishComponent(InformationPublishedComponentMiddlewareImpl informationPublishedComponentMiddlewareImpl, WalletFactoryProject walletFactoryProject, byte[] icon, byte[] mainScreenShot, List<byte[]> screenShotDetails, URL videoUrl, String observations, Version initialWalletVersion, Version finalWalletVersion, Version initialPlatformVersion, Version finalPlatformVersion, URL publisherWebsiteUrl, String publisherIdentityPublicKey, String signature) throws CantPublishComponentMiddlewareException {

        try {

            Version defaultVersion = new Version(1, 0, 0);
            CatalogItem catalogItem = constructCatalogItemObject(walletFactoryProject, walletFactoryProject.getWalletCategory(), defaultVersion, icon, mainScreenShot, screenShotDetails, videoUrl, initialWalletVersion, finalWalletVersion, initialPlatformVersion, finalPlatformVersion, publisherWebsiteUrl);

            /* ----------------------------------------
             * Create the informationPublishedComponent
             * ----------------------------------------
             */
            informationPublishedComponentMiddlewareImpl.setId(UUID.randomUUID());
            informationPublishedComponentMiddlewareImpl.setWalletFactoryProjectId(walletFactoryProject.getProjectPublicKey());
            informationPublishedComponentMiddlewareImpl.setWalletFactoryProjectName(walletFactoryProject.getName());
            informationPublishedComponentMiddlewareImpl.setDescriptions(walletFactoryProject.getDescription());
            informationPublishedComponentMiddlewareImpl.setStatus(ComponentPublishedInformationStatus.REQUESTED);
            informationPublishedComponentMiddlewareImpl.setStatusTimestamp(new Timestamp(System.currentTimeMillis()));
            informationPublishedComponentMiddlewareImpl.setPublisherIdentityPublicKey(publisherIdentityPublicKey);
            informationPublishedComponentMiddlewareImpl.setSignature(signature);
            informationPublishedComponentMiddlewareImpl.setVideoUrl(videoUrl);


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
            componentVersionDetailMiddlewareImpl.setScreenSize(walletFactoryProject.getDefaultSkin().getScreenSize());
            componentVersionDetailMiddlewareImpl.setComponentId(informationPublishedComponentMiddlewareImpl.getId());
            componentVersionDetailMiddlewareImpl.setCatalogId(catalogItem.getId());

            /*--------------------------
             * Create the images
             * --------------------------
             */
            List<ImageMiddlewareImpl> images = new ArrayList<>();

            /*
             * Validate not null
             */
            if (screenShotDetails != null) {

                /* -------------------------------------
                 * Create all screenShots images details
                 * -------------------------------------
                 */
                for (byte[] screen : screenShotDetails) {

                    ImageMiddlewareImpl screenShotImg = new ImageMiddlewareImpl();
                    screenShotImg.setFileId(UUID.randomUUID());
                    screenShotImg.setComponentId(informationPublishedComponentMiddlewareImpl.getId());
                    screenShotImg.setData(mainScreenShot);
                    images.add(screenShotImg);
                }
            }


            // Save into data base
            informationPublishedComponentDao.create(informationPublishedComponentMiddlewareImpl, componentVersionDetailMiddlewareImpl, images);


            /* -------------------------------------
             * Publish into the wallet Store
             * -------------------------------------
             */
            walletStoreManager.publishWallet(catalogItem);

            /*
             * If publish process is ok change the status and update in the database
             */
            informationPublishedComponentMiddlewareImpl.setStatus(ComponentPublishedInformationStatus.PUBLISHED);
            informationPublishedComponentMiddlewareImpl.setStatusTimestamp(new Timestamp(System.currentTimeMillis()));
            informationPublishedComponentMiddlewareImpl.setPublicationTimestamp(new Timestamp(System.currentTimeMillis()));
            informationPublishedComponentDao.update(informationPublishedComponentMiddlewareImpl);

        } catch (Exception exception) {

            StringBuffer contextBuffer = new StringBuffer();

            String context = contextBuffer.toString();
            String possibleCause = "The Wallet Publisher encounter a problem";
            throw new CantPublishComponentMiddlewareException(CantStartPluginException.DEFAULT_MESSAGE, exception, context, possibleCause);

        }

    }


    /**
     * This method encapsulate the creation of a object CatalogItem
     *
     * @param walletFactoryProject
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
    private CatalogItem constructCatalogItemObject(WalletFactoryProject walletFactoryProject, WalletCategory walletCategory, Version version, byte[] icon, byte[] mainScreenShot, List<byte[]> screenShotDetails, URL videoUrl, Version initialWalletVersion, Version finalWalletVersion, Version initialPlatformVersion, Version finalPlatformVersion, URL publisherWebsiteUrl) throws CantGetWalletIconException {

        UUID catalogId = UUID.randomUUID();
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
        Skin skin = walletFactoryProject.getDefaultSkin();
        com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.Skin defaultSkin = constructSkinObject(catalogId,
                                                                                                                        skin,
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
        List<com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.Skin> otherSkinSupportedList = new ArrayList<>();
        for (Skin skinItem : walletFactoryProject.getSkins()) {

            /*
             * Construct
             */
            com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.Skin skinCatalogItem = constructSkinObject(catalogId,
                                                                                                                                skinItem,
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
            otherSkinSupportedList.add(skinCatalogItem);
        }


        /*
         * Construct the default language
         */
        Language language = walletFactoryProject.getDefaultLanguage();
        com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.Language defaultLanguage = constructLanguageObject(catalogId,
                                                                                                                                    language,
                                                                                                                                    version,
                                                                                                                                    videoPreviews,
                                                                                                                                    initialWalletVersion,
                                                                                                                                    finalWalletVersion);
        /*
         * Create other supported languages list
         */
        List<com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.Language> otherLanguageSupportedList = new ArrayList<>();
        for (Language languageItem : walletFactoryProject.getLanguages()) {

            /*
            * Construct
            */
            com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.Language languageCatalogItem = constructLanguageObject(catalogId,
                                                                                                                                            languageItem,
                                                                                                                                            version,
                                                                                                                                            videoPreviews,
                                                                                                                                            initialWalletVersion,
                                                                                                                                            finalWalletVersion);
            /*
             * Add to the list
             */
            otherLanguageSupportedList.add(languageCatalogItem);
        }

        /*
         * Construct the catalog item instance
         */
        return walletStoreManager.constructCatalogItem(catalogId,
                                                        walletFactoryProject.getSize(),
                                                        walletFactoryProject.getName(),
                                                        walletFactoryProject.getDescription(),
                                                        walletCategory,
                                                        icon,
                                                        version,
                                                        initialPlatformVersion,
                                                        finalPlatformVersion,
                                                        otherSkinSupportedList,
                                                        defaultSkin,
                                                        defaultLanguage,
                                                        walletFactoryProject.getNavigationStructure().getDeveloper(),
                                                        otherLanguageSupportedList,
                                                        publisherWebsiteUrl);
    }

    /**
     * This method encapsulate the creation of a object Skin
     *
     * @param skin
     * @param version
     * @param mainScreenShot
     * @param screenShotDetails
     * @param hasVideoPreview
     * @param videoPreviews
     * @param initialWalletVersion
     * @param finalWalletVersion
     * @return Skin
     */
    private com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.Skin constructSkinObject(UUID catalogId, Skin skin, Version version, byte[] mainScreenShot, List<byte[]> screenShotDetails, boolean hasVideoPreview, List<URL> videoPreviews, Version initialWalletVersion, Version finalWalletVersion) {

       /*
        * Construct the new instance
        */
        return walletStoreManager.constructSkin(skin.getId(),
                                                skin.getName(),
                                                catalogId,
                                                skin.getScreenSize(),
                                                version,
                                                initialWalletVersion,
                                                finalWalletVersion,
                                                mainScreenShot,
                                                screenShotDetails,
                                                hasVideoPreview,
                                                videoPreviews,
                                                skin.getSize(),
                                                skin.getDesigner(),
                                                Boolean.TRUE);

    }

    /**
     * This method encapsulate the creation of a object Language
     *
     * @param language
     * @param version
     * @param videoPreviews
     * @param initialWalletVersion
     * @param finalWalletVersion
     * @return Language
     */
    private com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.Language constructLanguageObject(UUID catalogId, Language language, Version version, List<URL> videoPreviews, Version initialWalletVersion, Version finalWalletVersion) {

        /*
        * Construct the new instance
        */
        return walletStoreManager.constructLanguage(language.getId(),
                                                    language.getType(),
                                                    language.getName(),
                                                    catalogId,
                                                    version,
                                                    initialWalletVersion,
                                                    finalWalletVersion,
                                                    videoPreviews,
                                                    language.getSize(),
                                                    language.getTranslator(),
                                                    Boolean.TRUE);

    }

}