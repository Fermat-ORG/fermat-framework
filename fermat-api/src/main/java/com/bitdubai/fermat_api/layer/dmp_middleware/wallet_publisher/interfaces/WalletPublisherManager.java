/*
 * @#WalletPublisherManager.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.exceptions.CantPublishComponetException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.exceptions.CantGetPublishedComponentInformationException;

import java.net.URL;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.middleware.wallet_factory.interfaces.WalletPublisherManager</code>
 * indicates the functionality of a WalletPublisherManager
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/07/15.
 * Update by Roberto Requena - (rart3001@gmail.com) on 04/08/2015
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface WalletPublisherManager {

    /**
     * This method returns all descriptor factory projects closed and ready to be published
     *
     * @return List<WalletFactoryProject>
     */
    public List<WalletFactoryProject> getProjectsReadyToPublish();

    /**
     * This method returns the information stored about the all published component
     *
     * @return List<InformationPublishedComponent>
     * @throws CantGetPublishedComponentInformationException
     */
    public List<InformationPublishedComponent> getPublishedComponents() throws CantGetPublishedComponentInformationException;

    /**
     * This method returns the information stored about the all published wallets
     *
     * @return List<InformationPublishedComponent>
     * @throws CantGetPublishedComponentInformationException
     */
    public List<InformationPublishedComponent> getPublishedWallets() throws CantGetPublishedComponentInformationException;

    /**
     * This method returns the information stored about the all published skins
     *
     * @return List<InformationPublishedComponent>
     * @throws CantGetPublishedComponentInformationException
     */
    public List<InformationPublishedComponent> getPublishedSkins() throws CantGetPublishedComponentInformationException;

    /**
     * This method returns the information stored about the published language
     *
     * @return List<InformationPublishedComponent>
     * @throws CantGetPublishedComponentInformationException
     */
    public List<InformationPublishedComponent>  getPublishedLanguages() throws CantGetPublishedComponentInformationException;

    /**
     * This method returns the information stored about the published component with his details
     * like versions, icon, screen shots etc...
     *
     * @param idInformationPublishedComponent
     * @return InformationPublishedComponent whit details
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
     * @throws CantPublishComponetException
     */
    public void publishSkin(SkinDescriptorFactoryProject skinDescriptorFactoryProject, byte[] icon, byte[] mainScreenShot, List<byte[]> screenShotDetails, URL videoUrl, String observations, Version initialWalletVersion, Version finalWalletVersion, Version initialPlatformVersion, Version finalPlatformVersion, String publisherIdentityPublicKey) throws CantPublishComponetException;

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
     * @throws CantPublishComponetException
     */
    public void publishLanguage(LanguageDescriptorFactoryProject languageDescriptorFactoryProject, byte[] icon, byte[] mainScreenShot, String observations, Version initialWalletVersion, Version finalWalletVersion, Version initialPlatformVersion, Version finalPlatformVersion, String publisherIdentityPublicKey) throws CantPublishComponetException;

    /**
     * This method publishes the wallet factory project <code>WalletDescriptorFactoryProject</code> with the wallet information in
     * the wallet store and register relevant information of this process.
     *
     * @param walletDescriptorFactoryProject
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
     * @throws CantPublishComponetException
     */
    public void publishWallet(WalletDescriptorFactoryProject walletDescriptorFactoryProject, byte[] icon, byte[] mainScreenShot, List<byte[]> screenShotDetails, URL videoUrl, String observations, Version initialWalletVersion, Version finalWalletVersion, Version initialPlatformVersion, Version finalPlatformVersion, String publisherIdentityPublicKey) throws CantPublishComponetException;


}