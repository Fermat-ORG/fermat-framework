package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Language;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantAddLanguageStringException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantCopyWalletLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantCreateEmptyWalletLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantDeleteLanguageStringException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantDeleteWalletLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantGetLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantGetWalletLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantGetWalletLanguagesException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantSetLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.LanguageNotFoundException;

import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.middleware.wallet_language.interfaces.WalletLanguageManager</code>
 * indicates the functionality of a WalletLanguageManager
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 29/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface WalletLanguageManager {

    /**
     * this method returns all the instances of WalletFactoryProjectLanguage related with the walletProjectProposal we are working with
     * (when we create an instance of this manager we pass throw parameters the walletProjectProposal).
     *
     * @return a list of the WalletFactoryProjectLanguages
     * @throws CantGetWalletLanguagesException if something goes wrong
     */
    List<WalletLanguage> getLanguages() throws CantGetWalletLanguagesException;

    /**
     * this method return an instance of the WalletFactoryProjectLanguage with the id passed throw parameters
     *
     * @param id of the language
     * @return an instance of WalletFactoryProjectLanguage
     * @throws CantGetWalletLanguageException if something goes wrong
     * @throws LanguageNotFoundException if we cant find the language
     */
    WalletLanguage getLanguageById(UUID id) throws CantGetWalletLanguageException, LanguageNotFoundException;

    /**
     * throw this method you can create a new empty WalletFactoryProjectLanguage
     *
     * @param name of the new WalletFactoryProjectLanguage
     * @param type of the new WalletFactoryProjectLanguage
     * @return an instance of the new WalletFactoryProjectLanguage
     * @throws CantCreateEmptyWalletLanguageException if something goes wrong
     */
    WalletLanguage createEmptyLanguage(String name, Languages type) throws CantCreateEmptyWalletLanguageException;

    /**
     * throw this method you can clone an existent WalletFactoryProjectLanguage
     *
     * @param newName of the walletFactoryProjectLanguage
     * @param walletLanguage you want to clone
     * @return a new instance of the WalletLanguage you just create
     * @throws CantCopyWalletLanguageException if something goes wrong
     * @throws LanguageNotFoundException if you cannot find the project that you're trying to copy
     */
    WalletLanguage copyLanguage(String newName, WalletLanguage walletLanguage) throws CantCopyWalletLanguageException, LanguageNotFoundException;

    /**
     * delete an existent walletFactoryProjectLanguage
     *
     * @param walletLanguage that you're trying to delete
     * @throws CantDeleteWalletLanguageException if something goes wrong
     * @throws LanguageNotFoundException if you can't find the language
     */
    void deleteLanguage(WalletLanguage walletLanguage) throws CantDeleteWalletLanguageException, LanguageNotFoundException;

    /**
     * this methods returns the language structure of the xml related with the walletFactoryProjectLanguage
     *
     * @param walletLanguage of the language you're trying to get
     * @return Language structure
     * @throws CantGetLanguageException if something goes wrong
     */
    Language getLanguage(WalletLanguage walletLanguage) throws CantGetLanguageException;

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
     * @param walletLanguage to wich belongs
     * @throws CantSetLanguageException if something goes wrong
     */
    void setLanguageXml(Language language, WalletLanguage walletLanguage) throws CantSetLanguageException;

    /**
     * add language strings to a language file
     *
     * @param name of the string
     * @param value of the string
     * @param walletLanguage to wich belongs
     * @throws CantAddLanguageStringException if something goes wrong
     */
    void addLanguageString(String name, String value, WalletLanguage walletLanguage) throws CantAddLanguageStringException;

    /**
     * delete string from a language file
     *
     * @param name of the string you want to delete
     * @param walletLanguage to wich belongs
     * @throws CantDeleteLanguageStringException if something goes wrong
     */
    void deleteLanguageString(String name, WalletLanguage walletLanguage) throws CantDeleteLanguageStringException;
}