package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetFactoryProjectResourcesException;

/**
 * Created by eze on 2015.07.14..
 */
public interface WalletFactoryProject {

    public String getProjectName();

    public String getWalletNavigationStructure();

    /**
     *
     * @return string that contains the resources manifest (a file describing the resources)
     */
    public String getManifest();

    /**
     *
     * @param fileName the name of the Skin file without the path structure.
     * @return The content of the file
     */
    public String getSkinFile(String fileName);

    public String getLanguageFile(String fileName);

    public byte[] getImageResource(String imageName) throws CantGetFactoryProjectResourcesException;

    public byte[] getVideoResource(String videoName) throws CantGetFactoryProjectResourcesException;

    public byte[] getSoundResource(String soundName) throws CantGetFactoryProjectResourcesException;

    public String getLayoutResource(String layoutName) throws CantGetFactoryProjectResourcesException;
}
