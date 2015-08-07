package com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources;


import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.WalletInstalationProgress;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces.WalletInstallationProcess;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.enums.InstallationStatus;

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

    public void installCompleteWallet(String walletCategory, String walletType, String developer, String screenSize, String screenDensity, String skinName, String languageName, String navigationStructureVersion) throws WalletResourcesInstalationException;


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
     * @throws WalletResourcesInstalationException
     */
    public void installSkinForWallet(String walletCategory, String walletType, String developer, String screenSize, String screenDensity, String skinName, String languageName, String navigationStructureVersion) throws WalletResourcesInstalationException;


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
     * @throws WalletResourcesInstalationException
     */
    public void installLanguageForWallet(String walletCategory, String walletType, String developer, String screenSize, String screenDensity, String skinName, String languageName, String navigationStructureVersion) throws WalletResourcesInstalationException;


    /**
     *
     * @param walletCategory
     * @param walletType
     * @param developer
     * @param skinName
     * @param skinId
     * @param screenSize
     * @param screenDensity
     * @param navigationStructureVersion
     * @param isLastWallet
     */
    public void unninstallCompleteWallet(String walletCategory, String walletType, String developer, String skinName, UUID skinId, String screenSize, String screenDensity, String navigationStructureVersion, boolean isLastWallet);


    /**
     *
     * @param walletCategory
     * @param walletType
     * @param developer
     * @param skinName
     * @param skinId
     * @param screenSize
     * @param screenDensity
     * @param navigationStructureVersion
     * @param isLastWallet
     */

    public void unninstallSkinForWallet(String walletCategory, String walletType, String developer, String skinName, UUID skinId, String screenSize, String screenDensity, String navigationStructureVersion, boolean isLastWallet);


    /**
     *
     * @param walletCategory
     * @param walletType
     * @param developer
     * @param skinName
     * @param skinId
     * @param screenSize
     * @param screenDensity
     * @param navigationStructureVersion
     * @param isLastWallet
     */

    public void unninstallLanguageForWallet(String walletCategory, String walletType, String developer, String skinName, UUID skinId, String screenSize, String screenDensity, String navigationStructureVersion, boolean isLastWallet);




    /**
     *  Get enum type of wallet instalation progress
     * @return
     */
    public WalletInstalationProgress getWalletInstalationProgress();


}
