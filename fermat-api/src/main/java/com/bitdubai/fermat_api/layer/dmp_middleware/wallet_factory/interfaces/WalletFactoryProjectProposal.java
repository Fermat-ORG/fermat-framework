package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.FactoryProjectState;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.ResourceType;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantAddWalletFactoryProjectLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantAddWalletFactoryProjectResourceException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantAddWalletFactoryProjectSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantDeleteWalletFactoryProjectLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantDeleteWalletFactoryProjectResourceException;
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
    List<WalletFactoryProjectSkin> getSkinList();

    // get list of languages
    List<String> getLanguages();

    // get a specific skin
    WalletFactoryProjectSkin getSkin(String skinName) throws CentGetWalletFactoryProjectSkinFileException;

    // get a specific language
    WalletFactoryProjectLanguage getLanguageFile(String fileName) throws CentGetWalletFactoryProjectLanguageFileException;

    // create a new skin
    void addSkin(WalletFactoryProjectSkin name) throws CantAddWalletFactoryProjectSkinException;

    // delete an existent skin
    void deleteSkin(WalletFactoryProjectSkin name) throws CantDeleteWalletFactoryProjectSkinException;

    void addResource(String name, byte[] resource, ResourceType resourceType) throws CantAddWalletFactoryProjectResourceException;

    void deleteResource(String name, byte[] resource, ResourceType resourceType) throws CantDeleteWalletFactoryProjectResourceException;

    // create a new skin
    void addLanguage(WalletFactoryProjectLanguage language) throws CantAddWalletFactoryProjectLanguageException;

    // delete an existent skin
    void deleteLanguage(WalletFactoryProjectLanguage language) throws CantDeleteWalletFactoryProjectLanguageException;

}
