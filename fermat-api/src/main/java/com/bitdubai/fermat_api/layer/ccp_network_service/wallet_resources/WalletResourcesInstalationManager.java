package com.bitdubai.fermat_api.layer.ccp_network_service.wallet_resources;


import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.InstalationProgress;
import com.bitdubai.fermat_api.layer.ccp_network_service.wallet_resources.exceptions.WalletResourcesUnninstallException;

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
     * @param skinName
     * @param languageName
     * @param navigationStructureVersion
     */

    public void installCompleteWallet(String walletCategory, String walletType, String developer, String screenSize, String skinName, String languageName, String navigationStructureVersion,String walletPublicKey) throws com.bitdubai.fermat_api.layer.ccp_network_service.wallet_resources.exceptions.WalletResourcesInstalationException;


    /**
     *
     * @param walletCategory
     * @param walletType
     * @param developer
     * @param screenSize
     * @param skinName
     * @param navigationStructureVersion
     * @throws com.bitdubai.fermat_api.layer.ccp_network_service.wallet_resources.exceptions.WalletResourcesInstalationException
     */
    public void installSkinForWallet(String walletCategory, String walletType, String developer, String screenSize, String skinName, String navigationStructureVersion) throws com.bitdubai.fermat_api.layer.ccp_network_service.wallet_resources.exceptions.WalletResourcesInstalationException;


    /**
     *
     * @param walletCategory
     * @param walletType
     * @param developer
     * @param screenSize
     * @param skinId
     * @param languageName
     * @throws com.bitdubai.fermat_api.layer.ccp_network_service.wallet_resources.exceptions.WalletResourcesInstalationException
     */
    public void installLanguageForWallet(String walletCategory, String walletType, String developer, String screenSize, UUID skinId, String languageName) throws com.bitdubai.fermat_api.layer.ccp_network_service.wallet_resources.exceptions.WalletResourcesInstalationException;


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

    public void unninstallCompleteWallet(String walletCategory, String walletType, String developer, String walletName, UUID skinId, String screenSize, String navigationStructureVersion, boolean isLastWallet)throws WalletResourcesUnninstallException;


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

    public void unninstallSkinForWallet(String walletCategory, String walletType, String developer, String walletName, UUID skinId, String screenSize, String navigationStructureVersion, boolean isLastWallet)throws WalletResourcesUnninstallException;


    /**
     *
     * @param walletCategory
     * @param walletType
     * @param developer
     * @param isLastWallet
     */

//TODO: No deberia recibir el id del lenguage que va a desinstalar?

    public void unninstallLanguageForWallet(String walletCategory, String walletType, String developer,String walletName, boolean isLastWallet) throws WalletResourcesUnninstallException;




    /**
     *  Get enum type of wallet instalation progress
     * @return
     */
    public InstalationProgress getInstalationProgress();


}
