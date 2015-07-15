package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetFactoryProjectResourceException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectManifestException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectNavigationStructureException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CentGetWalletFactoryProjectLanguageFileException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CentGetWalletFactoryProjectSkinFileException;

import java.util.List;

/**
 * Created by eze on 2015.07.15..
 */
public interface WalletFactoryProjectVersion {

    public List<String> getSkinList();

    public List<String> getLanguages();

    public String getProjectVersion();

    public String getProjectName();

    public String getWalletNavigationStructure() throws CantGetWalletFactoryProjectNavigationStructureException;



    /**
     *
     * @return string that contains the resources manifest (a file describing the resources)
     */
    public String getManifest() throws CantGetWalletFactoryProjectManifestException;

    /**
     *
     *
     * @param skinName the name of the Skin file without the path structure.
     * @return The content of the file
     */
    public WalletFactorySkin getSkin(String skinName) throws CentGetWalletFactoryProjectSkinFileException;

    public String getLanguageFile(String fileName) throws CentGetWalletFactoryProjectLanguageFileException;


}
