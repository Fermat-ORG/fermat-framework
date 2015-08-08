/*
 * @#WalletPublisherMiddlewareManager.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectLanguage;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectProposal;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectSkin;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.exceptions.CantCheckPublicationException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.exceptions.CantGetPublishedComponentInformationException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.exceptions.CantPublishComponetException;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces.WalletPublisherMiddlewareManager</code> mark
 * the different between the Wallet Publisher middleware and the Wallet Publisher module when need injected into other plugin
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 04/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface WalletPublisherMiddlewareManager {

    /**
     * This method returns the information stored about the all published component
     *
     * @return Map<UUID, List<WalletFactoryProjectProposal>>  A map where each entry corresponds to a WalletFactoryProject id and the list of WalletFactoryProjectProposal to be publish with their information
     */
    public Map<UUID, List<WalletFactoryProjectProposal>> getWalletFactoryProjectProposalToPublish();

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
     * This method tells us if the given wallet factory project can be published in the wallet store.
     * This is done by consulting the platform if the code has been accepted by bitDubai
     *
     * @param walletFactoryProjectProposal the wallet factory project to publish
     * @return true if it can be published, false otherwise.
     * @throws CantCheckPublicationException
     */
    public boolean canBePublished(WalletFactoryProjectProposal walletFactoryProjectProposal) throws CantCheckPublicationException;

    /**
     * This method publishes the wallet factory project <code>WalletFactoryProjectProposal</code> in
     * the wallet store and register relevant information of this process.
     *
     * @param walletFactoryProjectProposal the wallet factory project to publish
     * @throws CantPublishComponetException
     */
    public void publishWallet(WalletFactoryProjectProposal walletFactoryProjectProposal) throws CantPublishComponetException;

    /**
     * This method publishes the skin factory project <code>WalletFactoryProjectSkin</code> with the skin information in
     * the wallet store and register relevant information of this process.
     *
     * @param walletFactoryProjectSkin the skin factory project to publish
     * @throws CantPublishComponetException
     */
    public void publishSkin(WalletFactoryProjectSkin walletFactoryProjectSkin) throws CantPublishComponetException;

    /**
     * This method publishes the language factory project <code>WalletFactoryProjectLanguage</code> with the language information in
     * the wallet store and register relevant information of this process (publication timestamp).
     *
     * @param walletFactoryProjectLanguage the wallet factory project to publish
     * @throws CantPublishComponetException
     */
    public void publishLanguage(WalletFactoryProjectLanguage walletFactoryProjectLanguage) throws CantPublishComponetException;

}
