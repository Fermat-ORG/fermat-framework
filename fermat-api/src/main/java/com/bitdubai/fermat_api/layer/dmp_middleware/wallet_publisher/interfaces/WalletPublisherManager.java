/*
 * @#WalletPublisherManager.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.LanguageDescriptorFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.SkinDescriptorFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.exceptions.CantPublishComponetException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.exceptions.CantGetPublishedComponentInformationException;

import java.util.List;
import java.util.Map;

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
     * This method returns the information stored about the all published component
     *
     * @return Map<String,List<ComponentPublishedInformation>>  A map where each entry corresponds to a component name and the list of versions published with their information
     * @throws CantGetPublishedComponentInformationException
     */
    public Map<String,List<ComponentPublishedInformation>> getPublishedComponents() throws CantGetPublishedComponentInformationException;

    /**
     * This method returns the information stored about the all published wallets
     *
     * @return Map<String,List<ComponentPublishedInformation>>  A map where each entry corresponds to a wallet name and the list of versions published with their information
     * @throws CantGetPublishedComponentInformationException
     */
    public Map<String,List<ComponentPublishedInformation>> getPublishedWallets() throws CantGetPublishedComponentInformationException;

    /**
     * This method returns the information stored about the all published skins
     *
     * @return Map<String,List<ComponentPublishedInformation>>  A map where each entry corresponds to a skin name and the list of versions published with their information
     * @throws CantGetPublishedComponentInformationException
     */
    public Map<String,List<ComponentPublishedInformation>> getPublishedSkins() throws CantGetPublishedComponentInformationException;

    /**
     * This method returns the information stored about the published language
     *
     * @return Map<String,List<ComponentPublishedInformation>>  A map where each entry corresponds to a language name and the list of versions published with their information
     * @throws CantGetPublishedComponentInformationException
     */
    public Map<String,List<ComponentPublishedInformation>> getPublishedLanguages() throws CantGetPublishedComponentInformationException;

    /**
     * This method publishes the skin factory project <code>SkinDescriptorFactoryProject</code> with the skin information in
     * the wallet store and register relevant information of this process.
     *
     * @param skinDescriptorFactoryProject the skin factory project to publish
     * @throws CantPublishComponetException
     */
    public void publishSkin(SkinDescriptorFactoryProject skinDescriptorFactoryProject) throws CantPublishComponetException;

    /**
     * This method publishes the language factory project <code>LanguageDescriptorFactoryProject</code> with the language information in
     * the wallet store and register relevant information of this process (publication timestamp).
     *
     * @param languageDescriptorFactoryProject the wallet factory project to publish
     * @throws CantPublishComponetException
     */
    public void publishLanguage(LanguageDescriptorFactoryProject languageDescriptorFactoryProject) throws CantPublishComponetException;

}