package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Language;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantAddLanguageStringException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantCopyWalletFactoryProjectLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantCreateEmptyWalletFactoryProjectLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantDeleteLanguageStringException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantDeleteWalletFactoryProjectLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectLanguagesException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantSetLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.LanguageNotFoundException;

import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.middleware.wallet_factory.interfaces.WalletFactoryProjectLanguageManager</code>
 * indicates the functionality of a WalletFactoryProjectLanguageManager
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 25/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface WalletFactoryProjectLanguageManager {

    /**
     * this method returns all the instances of WalletFactoryProjectLanguage related with the walletProjectProposal we are working with
     * (when we create an instance of this manager we pass throw parameters the walletProjectProposal).
     *
     * @return a list of the WalletFactoryProjectLanguages
     * @throws CantGetWalletFactoryProjectLanguagesException if something goes wrong
     */
    List<WalletFactoryProjectLanguage> getLanguages() throws CantGetWalletFactoryProjectLanguagesException;

    /**
     * this method return an instance of the WalletFactoryProjectLanguage with the id passed throw parameters
     *
     * @param id of the language
     * @return an instance of WalletFactoryProjectLanguage
     * @throws CantGetWalletFactoryProjectLanguageException if something goes wrong
     * @throws LanguageNotFoundException if we cant find the language
     */
    WalletFactoryProjectLanguage getLanguageById(UUID id) throws CantGetWalletFactoryProjectLanguageException, LanguageNotFoundException;

    /**
     * throw this method you can create a new empty WalletFactoryProjectLanguage
     *
     * @param name of the new WalletFactoryProjectLanguage
     * @param type of the new WalletFactoryProjectLanguage
     * @return an instance of the new WalletFactoryProjectLanguage
     * @throws CantCreateEmptyWalletFactoryProjectLanguageException if something goes wrong
     */
    WalletFactoryProjectLanguage createEmptyLanguage(String name, Languages type) throws CantCreateEmptyWalletFactoryProjectLanguageException;

    /**
     * throw this method you can clone an existent WalletFactoryProjectLanguage
     *
     * @param newName of the walletFactoryProjectLanguage
     * @param walletFactoryProjectLanguage you want to clone
     * @return a new instance of the WalletFactoryProjectLanguage you just create
     * @throws CantCopyWalletFactoryProjectLanguageException if something goes wrong
     * @throws LanguageNotFoundException if you cannot find the project that you're trying to copy
     */
    WalletFactoryProjectLanguage copyLanguage(String newName, WalletFactoryProjectLanguage walletFactoryProjectLanguage) throws CantCopyWalletFactoryProjectLanguageException, LanguageNotFoundException;

    /**
     * delete an existent walletFactoryProjectLanguage
     *
     * @param walletFactoryProjectLanguage that you're trying to delete
     * @throws CantDeleteWalletFactoryProjectLanguageException if something goes wrong
     * @throws LanguageNotFoundException if you can't find the language
     */
    void deleteLanguage(WalletFactoryProjectLanguage walletFactoryProjectLanguage) throws CantDeleteWalletFactoryProjectLanguageException, LanguageNotFoundException;

    /**
     * this methods returns the language structure of the xml related with the walletFactoryProjectLanguage
     *
     * @param walletFactoryProjectLanguage of the language you're trying to get
     * @return Language structure
     * @throws CantGetLanguageException if something goes wrong
     */
    Language getLanguage(WalletFactoryProjectLanguage walletFactoryProjectLanguage) throws CantGetLanguageException;

    /**
     * converts an xml file in a language
     *
     * @param languageStructure xml of the language
     * @return Language class structure
     * @throws CantGetLanguageException if something goes wrong
     */
    Language getLanguage(String languageStructure) throws CantGetLanguageException;

    /**
     * converts the given language in an xml file.
     *
     * @param language language class structure you're trying to convert to xml
     * @return xml string of the language
     * @throws CantGetLanguageException if something goes wrong
     */
    String getLanguageXml(Language language) throws CantGetLanguageException;

    /**
     * converts the given skin language in an xml file and saves in the proposal structure
     *
     * @param language class structure that you're trying to save
     * @param walletFactoryProjectLanguage to wich belongs
     * @throws CantSetLanguageException if something goes wrong
     */
    void setLanguageXml(Language language, WalletFactoryProjectLanguage walletFactoryProjectLanguage) throws CantSetLanguageException;

    /**
     * add language strings to a language file
     *
     * @param name of the string
     * @param value of the string
     * @param walletFactoryProjectLanguage to wich belongs
     * @throws CantAddLanguageStringException if something goes wrong
     */
    void addLanguageString(String name, String value, WalletFactoryProjectLanguage walletFactoryProjectLanguage) throws CantAddLanguageStringException;

    /**
     * delete string from a language file
     *
     * @param name of the string you want to delete
     * @param walletFactoryProjectLanguage to wich belongs
     * @throws CantDeleteLanguageStringException if something goes wrong
     */
    void deleteLanguageString(String name, WalletFactoryProjectLanguage walletFactoryProjectLanguage) throws CantDeleteLanguageStringException;

}