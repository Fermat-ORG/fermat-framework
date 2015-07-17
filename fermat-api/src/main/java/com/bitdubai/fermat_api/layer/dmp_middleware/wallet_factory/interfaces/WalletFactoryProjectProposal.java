package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.FactoryProjectState;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectManifestException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectNavigationStructureException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CentGetWalletFactoryProjectLanguageFileException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CentGetWalletFactoryProjectSkinFileException;

import java.util.List;

/**
 * Created by eze on 2015.07.15..
 */
public interface WalletFactoryProjectProposal {

    String getAlias();

    WalletFactoryProject getProject();

    FactoryProjectState getState();

    String getWalletNavigationStructure() throws CantGetWalletFactoryProjectNavigationStructureException;

    List<WalletFactoryProjectSkin> getSkinList();

    List<String> getLanguages();

    WalletFactoryProjectSkin getSkin(String skinName) throws CentGetWalletFactoryProjectSkinFileException;

    WalletFactoryProjectLanguage getLanguageFile(String fileName) throws CentGetWalletFactoryProjectLanguageFileException;

}
