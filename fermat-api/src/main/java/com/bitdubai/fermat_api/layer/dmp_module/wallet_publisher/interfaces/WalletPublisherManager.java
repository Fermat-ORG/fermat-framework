/*
 * @#WalletPublisherManager.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_api.layer.dmp_module.wallet_publisher.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.DescriptorFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.LanguageDescriptorFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.SkinDescriptorFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletDescriptorFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_publisher.exceptions.CantGetPublishedComponentInformationException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_publisher.exceptions.CantPublishComponentException;


import java.net.URL;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.dmp_module.wallet_publisher.interfaces.WalletPublisherManager</code>
 * indicates the functionality of a WalletPublisherManager
 * <p/>
 *
 * Created by Ezequiel on 09/07/15.
 * Update by Roberto Requena - (rart3001@gmail.com) on 04/08/2015
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface WalletPublisherManager {

    /**
     * This method returns all descriptor factory projects closed and ready to be published
     *
     * @return List<DescriptorFactoryProject>
     */
    public List<DescriptorFactoryProject> getProjectsReadyToPublish();

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
     * @param skinDescriptorFactoryProject
     * @param icon
     * @param mainScreenShot
     * @param screenShotDetails
     * @param videoUrl
     * @param observations
     * @param initialWalletVersion
     * @param finalWalletVersion
     * @param initialPlatformVersion
     * @param finalPlatformVersion
     * @param publisherIdentityPublicKey
     * @throws CantPublishComponentException
     */
    public void publishSkin(SkinDescriptorFactoryProject skinDescriptorFactoryProject, byte[] icon, byte[] mainScreenShot, List<byte[]> screenShotDetails, URL videoUrl, String observations, Version initialWalletVersion, Version finalWalletVersion, Version initialPlatformVersion, Version finalPlatformVersion, String publisherIdentityPublicKey) throws CantPublishComponentException;

    /**
     * This method publishes the language factory project <code>LanguageDescriptorFactoryProject</code> with the language information in
     * the wallet store and register relevant information of this process.
     *
     * @param languageDescriptorFactoryProject
     * @param icon
     * @param mainScreenShot
     * @param observations
     * @param initialWalletVersion
     * @param finalWalletVersion
     * @param initialPlatformVersion
     * @param finalPlatformVersion
     * @param publisherIdentityPublicKey
     * @throws CantPublishComponentException
     */
    public void publishLanguage(LanguageDescriptorFactoryProject languageDescriptorFactoryProject, byte[] icon, byte[] mainScreenShot, String observations, Version initialWalletVersion, Version finalWalletVersion, Version initialPlatformVersion, Version finalPlatformVersion, String publisherIdentityPublicKey) throws CantPublishComponentException;

    /**
     * This method publishes the wallet factory project <code>WalletDescriptorFactoryProject</code> with the wallet information in
     * the wallet store and register relevant information of this process.
     *
     * @param walletDescriptorFactoryProject
     * @param walletCategory
     * @param icon
     * @param mainScreenShot
     * @param screenShotDetails
     * @param videoUrl
     * @param observations
     * @param initialWalletVersion
     * @param finalWalletVersion
     * @param initialPlatformVersion
     * @param finalPlatformVersion
     * @param publisherIdentityPublicKey
     * @throws CantPublishComponentException
     */
    public void publishWallet(WalletDescriptorFactoryProject walletDescriptorFactoryProject, WalletCategory walletCategory, byte[] icon, byte[] mainScreenShot, List<byte[]> screenShotDetails, URL videoUrl, String observations, Version initialWalletVersion, Version finalWalletVersion, Version initialPlatformVersion, Version finalPlatformVersion, String publisherIdentityPublicKey) throws CantPublishComponentException;


}
