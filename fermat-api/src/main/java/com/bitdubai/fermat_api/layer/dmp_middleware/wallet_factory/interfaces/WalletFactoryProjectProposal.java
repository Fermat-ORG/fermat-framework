package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Wallet;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.FactoryProjectState;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantAddWalletFactoryProjectLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantCreateEmptyWalletFactoryProjectSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantDeleteWalletFactoryProjectLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantDeleteWalletFactoryProjectSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetObjectStructureFromXmlException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetObjectStructureXmlException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectNavigationStructureException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CentGetWalletFactoryProjectLanguageFileException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CentGetWalletFactoryProjectSkinFileException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.LanguageNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.SkinNotFoundException;

import java.util.List;
import java.util.UUID;

/**
 * Created by eze on 2015.07.15..
 */
public interface WalletFactoryProjectProposal {

    UUID getId();

    // alias of the proposal of version
    String getAlias();

    // project to which it belongs
    WalletFactoryProject getProject();

    // state of the proposal
    FactoryProjectState getState();

    // get list of languages
    List<WalletFactoryProjectLanguage> getLanguages();

    // get an specific language
    WalletFactoryProjectLanguage getLanguageFileByName(String fileName) throws CentGetWalletFactoryProjectLanguageFileException, LanguageNotFoundException;

    // get an specific language
    WalletFactoryProjectLanguage getLanguageFileById(UUID id) throws CentGetWalletFactoryProjectLanguageFileException, LanguageNotFoundException;

    // get all language files of an specific language
    List<WalletFactoryProjectLanguage> getLanguageFilesByType(Languages type) throws CentGetWalletFactoryProjectLanguageFileException;

    // create a new skin
    WalletFactoryProjectLanguage addLanguage(byte[] file, String name, Languages type) throws CantAddWalletFactoryProjectLanguageException;

    // delete an existent skin
    void deleteLanguage(UUID id) throws CantDeleteWalletFactoryProjectLanguageException, LanguageNotFoundException;

}
