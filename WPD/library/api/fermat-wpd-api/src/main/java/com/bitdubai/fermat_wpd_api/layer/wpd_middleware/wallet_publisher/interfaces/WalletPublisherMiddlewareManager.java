/*
 * @#WalletPublisherMiddlewareManager.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_publisher.interfaces;


import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.interfaces.WalletFactoryProject;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.exceptions.CantGetPublishedComponentInformationException;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.exceptions.CantPublishComponentException;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.interfaces.InformationPublishedComponent;

import java.net.URL;
import java.util.List;
import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_publisher.interfaces.WalletPublisherMiddlewareManager</code> mark
 * the different between the Wallet Publisher middleware and the Wallet Publisher module when need injected into other plugin
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 04/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface WalletPublisherMiddlewareManager extends FermatManager {

    /**
     * This method returns the information stored about the all published component
     *
     * @param publisherIdentityPublicKey
     * @return List<InformationPublishedComponentMiddleware>
     * @throws CantGetPublishedComponentInformationException
     */
    public List<InformationPublishedComponent> getPublishedComponents(String publisherIdentityPublicKey) throws CantGetPublishedComponentInformationException;

    /**
     * This method returns the information stored about the all published wallets
     *
     * @param publisherIdentityPublicKey
     * @return List<InformationPublishedComponentMiddleware>
     * @throws CantGetPublishedComponentInformationException
     */
    public List<InformationPublishedComponent> getPublishedWallets(String publisherIdentityPublicKey) throws CantGetPublishedComponentInformationException;

    /**
     * This method returns the information stored about the all published skins
     *
     * @param publisherIdentityPublicKey
     * @return List<InformationPublishedComponentMiddleware>
     * @throws CantGetPublishedComponentInformationException
     */
    public List<InformationPublishedComponent> getPublishedSkins(String publisherIdentityPublicKey) throws CantGetPublishedComponentInformationException;

    /**
     * This method returns the information stored about the published language
     *
     * @param publisherIdentityPublicKey
     * @return List<InformationPublishedComponentMiddleware>
     * @throws CantGetPublishedComponentInformationException
     */
    public List<InformationPublishedComponent>  getPublishedLanguages(String publisherIdentityPublicKey) throws CantGetPublishedComponentInformationException;

    /**
     * This method returns the information stored about the published component with his details
     * like versions, icon, screen shots etc...
     *
     * @param idInformationPublishedComponent
     * @return InformationPublishedComponentMiddleware whit details
     * @throws CantGetPublishedComponentInformationException
     */
    public InformationPublishedComponent getInformationPublishedComponentWithDetails(UUID idInformationPublishedComponent) throws CantGetPublishedComponentInformationException;

    /**
     * This method publishes the skin factory project <code>SkinDescriptorFactoryProject</code> with the skin information in
     * the wallet store and register relevant information of this process.
     *
     * @param walletFactoryProject
     * @param icon
     * @param mainScreenShot
     * @param screenShotDetails
     * @param videoUrl
     * @param observations
     * @param initialWalletVersion
     * @param finalWalletVersion
     * @param publisherWebsiteUrl
     * @param publisherIdentityPublicKey
     * @param signature
     * @throws CantPublishComponentException
     */
    public void publishSkin(WalletFactoryProject walletFactoryProject, byte[] icon, byte[] mainScreenShot, List<byte[]> screenShotDetails, URL videoUrl,String observations, Version initialWalletVersion, Version finalWalletVersion, URL publisherWebsiteUrl, String publisherIdentityPublicKey, String signature) throws CantPublishComponentException;

    /**
     *
     * This method publishes the language factory project <code>LanguageDescriptorFactoryProject</code> with the language information in
     * the wallet store and register relevant information of this process.
     *
     * @param walletFactoryProject
     * @param icon
     * @param mainScreenShot
     * @param screenShotDetails
     * @param videoUrl
     * @param observations
     * @param initialWalletVersion
     * @param finalWalletVersion
     * @param publisherWebsiteUrl
     * @param publisherIdentityPublicKey
     * @param signature
     * @throws CantPublishComponentException
     */
    public void publishLanguage(WalletFactoryProject walletFactoryProject, byte[] icon, byte[] mainScreenShot, List<byte[]> screenShotDetails, URL videoUrl,String observations, Version initialWalletVersion, Version finalWalletVersion, URL publisherWebsiteUrl, String publisherIdentityPublicKey, String signature) throws CantPublishComponentException;

    /**
     * This method publishes the wallet factory project <code>WalletDescriptorFactoryProject</code> with the wallet information in
     * the wallet store and register relevant information of this process.
     *
     * @param walletFactoryProject
     * @param icon
     * @param mainScreenShot
     * @param screenShotDetails
     * @param videoUrl
     * @param observations
     * @param initialPlatformVersion
     * @param finalPlatformVersion
     * @param publisherWebsiteUrl
     * @param publisherIdentityPublicKey
     * @throws CantPublishComponentException
     */
    public void publishWallet(WalletFactoryProject walletFactoryProject, byte[] icon, byte[] mainScreenShot, List<byte[]> screenShotDetails, URL videoUrl, String observations, Version initialPlatformVersion, Version finalPlatformVersion, URL publisherWebsiteUrl, String publisherIdentityPublicKey, String signature) throws CantPublishComponentException;



}