package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetFactoryProjectResourceException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectManifestException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectNavigationStructureException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CentGetWalletFactoryProjectLanguageFileException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CentGetWalletFactoryProjectSkinFileException;

/**
 * Created by eze on 2015.07.15..
 */
public interface WalletFactoryProjectVersion {

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
     * @param fileName the name of the Skin file without the path structure.
     * @return The content of the file
     */
    public String getSkinFile(String fileName) throws CentGetWalletFactoryProjectSkinFileException;

    public String getLanguageFile(String fileName) throws CentGetWalletFactoryProjectLanguageFileException;

    public byte[] getImageResource(String imageName) throws CantGetFactoryProjectResourceException;

    public byte[] getVideoResource(String videoName) throws CantGetFactoryProjectResourceException;

    public byte[] getSoundResource(String soundName) throws CantGetFactoryProjectResourceException;

    public String getLayoutResource(String layoutName) throws CantGetFactoryProjectResourceException;
}
