package com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantInstallWalletException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.enums.InstallationStatus;

import java.util.UUID;

/**
 * Created by eze on 2015.07.19..
 */
public interface WalletInstallationProcess {

    /**
     * This method gives us the progress of the current installation
     *
     * @return an InstallationStatus enum that reflects the said progress
     */
    public InstallationStatus getInstallationProgress();


    /**
     * This method starts the wallet installation process
     *
     * @param walletType The type of wallet to install
     * @param walletName  the name of the wallet
     * @param walletPublicKey  the public key of the wallet
     * @param walletPrivateKey the private key of wallet
     * @param deviceUserPublicKey
     * @param walletIconName  the name of the icon of the wallet
     * @param walletCatalogueId  the name of the icon of the wallet
     * @param walletVersion   the wallet version
     * @param screenSize   the screen size
      * @param skinId      the skin id
     * @param skinVersion   the skin version
     * @param skinName   the skin name
     * @param skinPreview the skin preview image name
     * @param languageId  the language id
     * @param languageVersion  the language version
     * @param languageName   the wallet name
     * @param languageLabel  the wallet label
     * @param developer the developer name
     * @param navigationStructureVersion
     * @throws CantInstallWalletException
     */
    public void startInstallation(WalletType walletType,
                                  String walletName,
                                  String walletPublicKey,
                                  String walletPrivateKey,
                                  String deviceUserPublicKey,
                                  String walletIconName,
                                  UUID walletCatalogueId,
                                  Version walletVersion,
                                  String screenSize,
                                  UUID skinId,
                                  Version skinVersion,
                                  String skinName,
                                  String skinPreview,
                                  UUID languageId,
                                  Version languageVersion,
                                  Languages languageName ,
                                  String languageLabel,
                                   String developer,
                                  String navigationStructureVersion) throws CantInstallWalletException;
}
