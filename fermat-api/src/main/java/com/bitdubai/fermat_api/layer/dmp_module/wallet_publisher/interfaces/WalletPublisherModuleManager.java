/*
 * @#WalletPublisherModuleManager.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_api.layer.dmp_module.wallet_publisher.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Language;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_identity.publisher.interfaces.PublisherIdentity;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_publisher.exceptions.CantGetPublishedComponentInformationException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_publisher.exceptions.CantPublishComponentException;


import java.net.URL;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.dmp_module.wallet_publisher.interfaces.WalletPublisherModuleManager</code>
 * indicates the functionality of a WalletPublisherModuleManager
 * <p/>
 *
 * Created by Ezequiel on 09/07/15.
 * Update by Roberto Requena - (rart3001@gmail.com) on 04/08/2015
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface WalletPublisherModuleManager {

    /**
     * This method returns all descriptor factory projects closed and ready to be published
     *
     * @return List<DescriptorFactoryProject>
     */
    public List<WalletFactoryProject> getProjectsReadyToPublish() throws CantGetWalletFactoryProjectException;

    /**
     * This method returns the information stored about the all published component
     *
     * @param publisherIdentity
     * @return List<InformationPublishedComponentMiddleware>
     * @throws CantGetPublishedComponentInformationException
     */
    public List<InformationPublishedComponent> getPublishedComponents(PublisherIdentity publisherIdentity) throws CantGetPublishedComponentInformationException;

    /**
     * This method returns the information stored about the all published wallets
     *
     * @param publisherIdentity
     * @return List<InformationPublishedComponentMiddleware>
     * @throws CantGetPublishedComponentInformationException
     */
    public List<InformationPublishedComponent> getPublishedWallets(PublisherIdentity publisherIdentity) throws CantGetPublishedComponentInformationException;

    /**
     * This method returns the information stored about the all published skins
     *
     * @param publisherIdentity
     * @return List<InformationPublishedComponentMiddleware>
     * @throws CantGetPublishedComponentInformationException
     */
    public List<InformationPublishedComponent> getPublishedSkins(PublisherIdentity publisherIdentity) throws CantGetPublishedComponentInformationException;

    /**
     * This method returns the information stored about the published language
     *
     * @param publisherIdentity
     * @return List<InformationPublishedComponentMiddleware>
     * @throws CantGetPublishedComponentInformationException
     */
    public List<InformationPublishedComponent>  getPublishedLanguages(PublisherIdentity publisherIdentity) throws CantGetPublishedComponentInformationException;

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
     * @param skin
     * @param icon
     * @param mainScreenShot
     * @param screenShotDetails
     * @param videoUrl
     * @param observations
     * @param initialWalletVersion
     * @param finalWalletVersion
     * @param initialPlatformVersion
     * @param finalPlatformVersion
     * @param publisherIdentity
     * @throws CantPublishComponentException
     */
    public void publishSkin(Skin skin, byte[] icon, byte[] mainScreenShot, List<byte[]> screenShotDetails, URL videoUrl, String observations, Version initialWalletVersion, Version finalWalletVersion, Version initialPlatformVersion, Version finalPlatformVersion, PublisherIdentity publisherIdentity) throws CantPublishComponentException;

    /**
     * This method publishes the language factory project <code>LanguageDescriptorFactoryProject</code> with the language information in
     * the wallet store and register relevant information of this process.
     *
     * @param language
     * @param icon
     * @param mainScreenShot
     * @param observations
     * @param initialWalletVersion
     * @param finalWalletVersion
     * @param initialPlatformVersion
     * @param finalPlatformVersion
     * @param publisherIdentity
     * @throws CantPublishComponentException
     */
    public void publishLanguage(Language language, byte[] icon, byte[] mainScreenShot, String observations, Version initialWalletVersion, Version finalWalletVersion, Version initialPlatformVersion, Version finalPlatformVersion, PublisherIdentity publisherIdentity) throws CantPublishComponentException;

    /**
     * This method publishes the wallet factory project <code>WalletFactoryProject</code> with the wallet information in
     * the wallet store and register relevant information of this process.
     *
     * @param walletFactoryProject
     * @param walletCategory
     * @param icon
     * @param mainScreenShot
     * @param screenShotDetails
     * @param videoUrl
     * @param observations
     * @param initialWalletVersion
     * @param finalWalletVersion
     * @param initialPlatformVersion
     * @param publisherWebsiteUrl
     * @param finalPlatformVersion
     * @param publisherIdentity
     * @throws CantPublishComponentException
     */
    public void publishWallet(WalletFactoryProject walletFactoryProject, WalletCategory walletCategory, byte[] icon, byte[] mainScreenShot, List<byte[]> screenShotDetails, URL videoUrl, String observations, Version initialWalletVersion, Version finalWalletVersion, Version initialPlatformVersion, Version finalPlatformVersion, URL publisherWebsiteUrl, PublisherIdentity publisherIdentity) throws CantPublishComponentException;


}
