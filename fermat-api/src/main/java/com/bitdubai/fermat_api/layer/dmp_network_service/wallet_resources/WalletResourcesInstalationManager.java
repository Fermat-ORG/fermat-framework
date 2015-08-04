package com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources;


import java.util.UUID;

/**
 *
 *  <p>The abstract class <code>com.bitdubai.fermat_api.layer.network_service.wallet_resources.WalletResourcesInstalationManager/code> is a interface
 *     that define the methods to install wallets resource files.
 *
 *
 *  @author  Matias Furszyfer
 *  @since   4/08/15.
 * */
public interface WalletResourcesInstalationManager {


    /**
     *
     * @param walletCategory
     * @param walletType
     * @param developer
     * @param screenSize
     * @param screenDensity
     * @param skinName
     * @param languageName
     * @param navigationStructureVersion
     */

    public void installResources(String walletCategory, String walletType,String developer,String screenSize,String screenDensity,String skinName,String languageName,String navigationStructureVersion);


    /**
     *
     * @param walletPath
     * @param skinId
     * @param navigationStructureVersion
     */
    public void unninstallResources(String walletPath,UUID skinId,String navigationStructureVersion);


}
