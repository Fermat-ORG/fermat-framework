package com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces;


import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.InstalationProgress;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.exceptions.WalletResourcesUnninstallException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.exceptions.WalletResourcesInstalationException;

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
public interface WalletResourcesInstalationManager extends FermatManager {


    /**
     *
     * @param walletCategory
     * @param walletType
     * @param developer
     * @param screenSize
     * @param skinName
     * @param languageName
     * @param navigationStructureVersion
     */

    public void installCompleteWallet(String walletCategory, String walletType, String developer, String screenSize, String skinName, String languageName, String navigationStructureVersion,String walletPublicKey) throws WalletResourcesInstalationException;


    /**
     *
     * @param walletCategory
     * @param walletType
     * @param developer
     * @param screenSize
     * @param skinName
     * @param navigationStructureVersion
     * @throws WalletResourcesInstalationException
     */
    public void installSkinForWallet(String walletCategory, String walletType, String developer, String screenSize, String skinName, String navigationStructureVersion,String walletPublicKey) throws WalletResourcesInstalationException;


    /**
     *
     * @param walletCategory
     * @param walletType
     * @param developer
     * @param screenSize
     * @param skinId
     * @param languageName
     * @throws WalletResourcesInstalationException
     */
    public void installLanguageForWallet(String walletCategory, String walletType, String developer, String screenSize, UUID skinId, String languageName,String walletPublicKey) throws WalletResourcesInstalationException;


    /**
     *
     * @param walletCategory
     * @param walletType
     * @param developer
     * @param skinId
     * @param screenSize
     * @param navigationStructureVersion
     * @param isLastWallet
     */


    //TODO: la wallet puede tener mas de un lenguage y skin, este metodo va a recibir el array de skins y language?

    public void uninstallCompleteWallet(String walletCategory, String walletType, String developer, String walletName, UUID skinId, String screenSize, String navigationStructureVersion, boolean isLastWallet,String walletPublicKey)throws WalletResourcesUnninstallException;


    /**
     *
     * @param skinId
     * @param walletPublicKey
     * @throws WalletResourcesUnninstallException
     */
    public void uninstallSkinForWallet( UUID skinId,String walletPublicKey) throws WalletResourcesUnninstallException;


    /**
     *
     * @param skinId
     * @param walletPublicKey
     * @param languageName
     * @throws WalletResourcesUnninstallException
     */
    public void uninstallLanguageForWallet(UUID skinId,String walletPublicKey, String languageName) throws WalletResourcesUnninstallException;




    /**
     *  Get enum type of wallet instalation progress
     * @return
     */
    public InstalationProgress getInstallationProgress();


}
