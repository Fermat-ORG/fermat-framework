package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.FactoryProjectState;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantAddWalletFactoryProjectLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantAddWalletFactoryProjectSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantDeleteWalletFactoryProjectLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantDeleteWalletFactoryProjectSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectNavigationStructureException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CentGetWalletFactoryProjectLanguageFileException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CentGetWalletFactoryProjectSkinFileException;

import java.util.List;

/**
 * Created by eze on 2015.07.15..
 */
public interface WalletFactoryProjectProposal {

    // alias of the proposal of version
    String getAlias();

    // project to which it belongs
    WalletFactoryProject getProject();

    // state of the proposal
    FactoryProjectState getState();

    // brings the navigation structure of the project proposal
    String getWalletNavigationStructure() throws CantGetWalletFactoryProjectNavigationStructureException;

    // get list of the skins
    List<WalletFactoryProjectSkin> getSkins();

    // get list of languages
    List<WalletFactoryProjectLanguage> getLanguages();

    // get a specific skin
    WalletFactoryProjectSkin getSkin(String skinName) throws CentGetWalletFactoryProjectSkinFileException;

    // get a specific language
    WalletFactoryProjectLanguage getLanguageFile(String fileName) throws CentGetWalletFactoryProjectLanguageFileException;

    // create a new empty skin
    void createEmptySkin(WalletFactoryProjectSkin name) throws CantAddWalletFactoryProjectSkinException;

    // delete an existent skin
    void deleteSkin(WalletFactoryProjectSkin name) throws CantDeleteWalletFactoryProjectSkinException;

    // create a new skin
    WalletFactoryProjectLanguage addLanguage(byte[] file, String name) throws CantAddWalletFactoryProjectLanguageException;

    // delete an existent skin
    void deleteLanguage(WalletFactoryProjectLanguage language) throws CantDeleteWalletFactoryProjectLanguageException;

}
