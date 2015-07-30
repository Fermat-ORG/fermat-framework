package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Wallet;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Language;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantAddLanguageStringException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantCopyWalletFactoryProjectLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantCreateEmptyWalletFactoryProjectLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantCreateWalletFactoryProjectProposalException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantDeleteLanguageStringException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantDeleteWalletFactoryProjectLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantDeleteWalletFactoryProjectProposalException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectLanguagesException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectNavigationStructureException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectProposalException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectProposalsException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantSetLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantSetWalletFactoryProjectNavigationStructureException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantUpdateWalletFactoryProjectProposalException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.LanguageNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.ProposalNotFoundException;

import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.middleware.wallet_factory.interfaces.WalletFactoryProjectProposalManager</code>
 * indicates the functionality of a WalletFactoryProjectProposalManager
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 25/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface WalletFactoryProjectProposalManager {

    /**
     * throw this method you can get all the proposals related to a project
     *
     * @return list of proposals
     * @throws CantGetWalletFactoryProjectProposalsException if something goes wrong
     */
    List<WalletFactoryProjectProposal> getProposals()  throws CantGetWalletFactoryProjectProposalsException;

    /**
     * this method returns an instance of a wallet factory proposal having in count the project you're working with and the name of the proposal
     *
     * @param proposal name
     * @return an instance of a WalletFactoryProjectProposal
     * @throws CantGetWalletFactoryProjectProposalException if something goes wrong
     * @throws ProposalNotFoundException if you cant find the proposal
     */
    WalletFactoryProjectProposal getProposalByName(String proposal) throws CantGetWalletFactoryProjectProposalException, ProposalNotFoundException;

    /**
     * this method returns an instance of a wallet factory proposal having in count the project you're working with and the id of the proposal
     *
     * @param id of the proposal you're trying to get
     * @return an instance of a WalletFactoryProjectProposal
     * @throws CantGetWalletFactoryProjectProposalException if something goes wrong
     * @throws ProposalNotFoundException if you cant find the proposal
     */
    WalletFactoryProjectProposal getProposalById(UUID id) throws CantGetWalletFactoryProjectProposalException, ProposalNotFoundException;

    /**
     * create a new proposal having in count the params and the project related to the manager
     *
     * @param alias of the proposal
     * @param walletType of the proposal
     * @return an instance of the new WalletFactoryProjectProposal
     * @throws CantCreateWalletFactoryProjectProposalException if something goes wrong
     */
    WalletFactoryProjectProposal createProposal(String alias, Wallets walletType) throws CantCreateWalletFactoryProjectProposalException;

    /**
     * create a new proposal having in count another proposal, only changing the name
     *
     * @param newAlias for the proposal
     * @param walletFactoryProjectProposal to copy
     * @return a new instance of WalletFactoryProjectProposal you just create
     * @throws CantCreateWalletFactoryProjectProposalException if something goes wrong
     */
    WalletFactoryProjectProposal copyProposal(String newAlias, WalletFactoryProjectProposal walletFactoryProjectProposal) throws CantCreateWalletFactoryProjectProposalException;

    /**
     * update the updatable attributes of a walletFactoryProjectProposal
     *
     * @param walletFactoryProjectProposal you're trying to update
     * @throws CantUpdateWalletFactoryProjectProposalException if something goes wrong
     * @throws ProposalNotFoundException if you cant find the proposal
     */
    void updateProposal(WalletFactoryProjectProposal walletFactoryProjectProposal) throws CantUpdateWalletFactoryProjectProposalException, ProposalNotFoundException;

    /**
     * delete the walletFactoryProjectProposal and its structure
     *
     * @param walletFactoryProjectProposal you're trying to delete
     * @throws CantDeleteWalletFactoryProjectProposalException if something goes wrong
     * @throws ProposalNotFoundException if you cant find the proposal
     */
    void deleteProposal(WalletFactoryProjectProposal walletFactoryProjectProposal) throws CantDeleteWalletFactoryProjectProposalException, ProposalNotFoundException;

    /**
     * brings the navigation structure of the project proposal
     *
     * @param walletFactoryProjectProposal from you want to get
     * @return Wallet navigation class structure
     * @throws CantGetWalletFactoryProjectNavigationStructureException
     */
    Wallet getNavigationStructure(WalletFactoryProjectProposal walletFactoryProjectProposal) throws CantGetWalletFactoryProjectNavigationStructureException;

    /**
     * converts an xml file in a navigation structure
     *
     * @param navigationStructure string xml
     * @return Wallet class structure
     * @throws CantGetWalletFactoryProjectNavigationStructureException if something goes wrong
     */
    Wallet getNavigationStructure(String navigationStructure) throws CantGetWalletFactoryProjectNavigationStructureException;

    /**
     * converts the given navigation structure in an xml file.
     *
     * @param wallet class navigation structure
     * @return xmlstring representating the navigation structure of the wallet
     * @throws CantGetWalletFactoryProjectNavigationStructureException if something goes wrong
     */
    String getNavigationStructureXml(Wallet wallet) throws CantGetWalletFactoryProjectNavigationStructureException;

    /**
     * converts the given navigation structure in an xml file and saves the xml file in the file structure
     *
     * @param wallet class navigation structure
     * @param walletFactoryProjectProposal you want to change
     * @throws CantSetWalletFactoryProjectNavigationStructureException if something goes wrong
     */
    void setNavigationStructureXml(Wallet wallet, WalletFactoryProjectProposal walletFactoryProjectProposal) throws CantSetWalletFactoryProjectNavigationStructureException;

    /**
     * This method returns an instance of the WalletFactoryProjectSkinManager, who has the methods to interact with the skins
     *
     * @param walletFactoryProjectProposal in which you are working
     * @return an instance of WalletFactoryProjectSkinManager
     */
    WalletFactoryProjectSkinManager getWalletFactoryProjectSkinManager(WalletFactoryProjectProposal walletFactoryProjectProposal);

    /**
     * This method returns an instance of the WalletFactoryProjectLanguageManager, who has the methods to interact with the languages
     *
     * @param walletFactoryProjectProposal in which you are working
     * @return an instance of WalletFactoryProjectLanguageManager
     */
    WalletFactoryProjectLanguageManager getWalletFactoryProjectLanguageManager(WalletFactoryProjectProposal walletFactoryProjectProposal);

}