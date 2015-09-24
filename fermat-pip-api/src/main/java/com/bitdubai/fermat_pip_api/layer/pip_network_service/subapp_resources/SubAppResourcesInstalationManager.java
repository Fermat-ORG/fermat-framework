package com.bitdubai.fermat_pip_api.layer.pip_network_service.subapp_resources;



import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.InstalationProgress;

import java.util.UUID;

/**
 * Created by natalia on 2015.07.28..
 */
public interface SubAppResourcesInstalationManager {



    public void installCompleteWallet(String subAppType, String developer, String screenSize, String skinName, String languageName, String navigationStructureVersion,String subAppPublickey) throws com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources.exceptions.WalletResourcesInstalationException;


    /**
     *
     * @param developer
     * @param screenSize
     * @param skinName
     * @param navigationStructureVersion
     * @throws com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources.exceptions.WalletResourcesInstalationException
     */
    public void installSkinForWallet(String subAppType, String developer, String screenSize, String skinName, String navigationStructureVersion,String subAppPublicKey);// throws com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources.exceptions.WalletResourcesInstalationException;


    /**
     *
     * @param developer
     * @param screenSize
     * @param skinId
     * @param languageName
     * @throws com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources.exceptions.WalletResourcesInstalationException
     */
    public void installLanguageForWallet(String subAppType, String developer, String screenSize, UUID skinId, String languageName,String subAppPublicKey); //throws com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources.exceptions.WalletResourcesInstalationException;


    /**
     *
     * @param developer
     * @param skinId
     * @param screenSize
     * @param navigationStructureVersion
     * @param isLastWallet
     */


    //TODO: la wallet puede tener mas de un lenguage y skin, este metodo va a recibir el array de skins y language?

    public void uninstallCompleteWallet(String subAppType, String developer, String walletName, UUID skinId, String screenSize, String navigationStructureVersion, boolean isLastWallet,String subAppPublicKey);//throws WalletResourcesUnninstallException;


    /**
     *
     * @param developer
     * @param skinId
     * @param screenSize
     * @param navigationStructureVersion
     * @param isLastWallet
     */

    public void uninstallSkinForWallet(String subAppType,String developer, String walletName, UUID skinId, String screenSize, String navigationStructureVersion, boolean isLastWallet,String subAppPublicKey);//throws WalletResourcesUnninstallException;


    /**
     * @param developer
     * @param isLastWallet
     */

    public void uninstallLanguageForWallet(String subAppType,String languageId, String developer,String walletName, boolean isLastWallet,String subAppPublicKey); //throws WalletResourcesUnninstallException;




    /**
     *  Get enum type of wallet instalation progress
     * @return
     */
    public InstalationProgress getInstallationProgress();
}
