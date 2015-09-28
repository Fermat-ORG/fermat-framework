package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Language;
import com.bitdubai.fermat_api.layer.all_definition.util.VersionCompatibility;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantCloseWalletLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantUpdateLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantCopyWalletLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantCreateEmptyWalletLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantDeleteWalletLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantGetLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantGetWalletLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantGetWalletLanguagesException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantSaveLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.LanguageNotFoundException;

import java.util.List;
import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.middleware.wallet_language.interfaces.WalletLanguageManager</code>
 * indicates the functionality of a WalletLanguageManager
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 29/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface WalletLanguageManager {

    /**
     * this method returns all the instances of WalletLanguage related with the current logged translator
     *
     * @return a list of the WalletLanguages
     * @throws CantGetWalletLanguagesException if something goes wrong
     */
    List<WalletLanguage> getLanguages(String translatorPublicKey) throws CantGetWalletLanguagesException;

    /**
     * this method return an instance of the WalletLanguage with the id passed throw parameters
     *
     * @param id of the language
     * @return an instance of WalletLanguage
     * @throws CantGetWalletLanguageException if something goes wrong
     * @throws LanguageNotFoundException if we cant find the language
     */
    WalletLanguage getLanguageById(UUID id) throws CantGetWalletLanguageException, LanguageNotFoundException;

    /**
     * TODO importFromRepository (methods in plugin Wallet Language utils/RepositoryManager)
     * TODO exportToRepository (methods in plugin Wallet Language utils/RepositoryManager)
     */

    /**
     * throw this method you can create a new empty WalletLanguage
     *
     * @param name of the new WalletLanguage
     * @param type of the new WalletLanguage
     * @return an instance of the new WalletLanguage
     * @throws CantCreateEmptyWalletLanguageException if something goes wrong
     */
    WalletLanguage createEmptyLanguage(String name, Languages type, String translatorPublicKey) throws CantCreateEmptyWalletLanguageException;

    /**
     * throw this method you can clone an existent WalletLanguage creating a new version of it
     * you can identify it throw the alias
     * this language has to keep the language id of the old one
     *
     * @param walletLanguage you want to clone
     * @return a new instance of the WalletLanguage you just create
     * @throws CantCopyWalletLanguageException if something goes wrong
     * @throws LanguageNotFoundException if you cannot find the project that you're trying to copy
     */
    WalletLanguage createNewVersion(WalletLanguage walletLanguage) throws CantCopyWalletLanguageException;

    /**
     * throw this method you can clone an existent WalletLanguage with a new name
     * it creates a new language id
     *
     * @param newName of the walletFactoryProjectLanguage
     * @param walletLanguage you want to clone
     * @return a new instance of the WalletLanguage you just create
     * @throws CantCopyWalletLanguageException if something goes wrong
     * @throws LanguageNotFoundException if you cannot find the project that you're trying to copy
     */
    WalletLanguage copyLanguage(String newName, WalletLanguage walletLanguage, String translatorPublicKey) throws CantCopyWalletLanguageException, LanguageNotFoundException;

    /**
     * update a language
     *
     * @param walletLanguage
     * @throws CantUpdateLanguageException
     * @throws LanguageNotFoundException
     */
    void updateLanguage(WalletLanguage walletLanguage) throws CantUpdateLanguageException, LanguageNotFoundException;

    /**
     * delete an existent walletLanguage
     *
     * @param walletLanguage that you're trying to delete
     * @throws CantDeleteWalletLanguageException if something goes wrong
     * @throws LanguageNotFoundException if you can't find the language
     */
    void deleteLanguage(WalletLanguage walletLanguage) throws CantDeleteWalletLanguageException, LanguageNotFoundException;

    /**
     * close an existent language so it can be published
     *
     * @param walletLanguage
     * @throws CantCloseWalletLanguageException
     * @throws LanguageNotFoundException
     */
    void closeLanguage(WalletLanguage walletLanguage) throws CantCloseWalletLanguageException, LanguageNotFoundException;

    /**
     * converts an xml file in a language
     *
     * @param languageStructure xml of the language
     * @return Language class structure
     * @throws CantGetLanguageException if something goes wrong
     */
    Language getLanguageFromXmlString(String languageStructure) throws CantGetLanguageException;

    /**
     * converts the given language in an xml file.
     *
     * @param language language class structure you're trying to convert to xml
     * @return xml string of the language
     * @throws CantGetLanguageException if something goes wrong
     */
    String getLanguageXmlFromClassStructure(Language language) throws CantGetLanguageException;

    /**
     * this methods returns the language structure of the xml related with the walletFactoryProjectLanguage
     *
     * @param walletLanguage of the language you're trying to get
     * @return Language structure
     * @throws CantGetLanguageException if something goes wrong
     */
    Language getLanguage(WalletLanguage walletLanguage) throws CantGetLanguageException, LanguageNotFoundException;

    /**
     * converts the given skin language in an xml file and saves in the proposal structure
     *
     * @param language class structure that you're trying to save
     * @param walletLanguage to wich belongs
     * @throws CantSaveLanguageException if something goes wrong
     */
    void saveLanguage(Language language, WalletLanguage walletLanguage) throws CantSaveLanguageException;

}