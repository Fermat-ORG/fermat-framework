package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.NicheWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.exceptions.CantInstallWalletException;

import java.util.UUID;

/**
 * Created by eze on 2015.07.19..
 */
public interface WalletInstallationProcess {

    /**
     * This method gives us the progress of the current installation
     *
     * @return an integer that reflects the said progress
     */
    public int getInstallationProgress();

    /**
     * This method starts the wallet installation process
     *
     * @param walletName the name of the wallet
     * @param walletIconName the name of the icon of the wallet
     * @param walletIdentifier The identification of the wallet, this coul represent a niche or reference wallet
     * @param skinId the identifier of the skin to install
     * @param skinVersion the version of the skin to install
     * @param languageId the indentifier of the language to install
     * @param languageVersion the version of the language to install
     * @param walletCatalogueId An identifier of the wallet to install
     * @param walletVersion the version of the wallet to install
     * @throws CantInstallWalletException
     */
    public void startInstallation(String walletName,
                                  String walletIconName,
                                  String walletIdentifier,
                                  UUID skinId,
                                  Version skinVersion,
                                  UUID languageId,
                                  Version languageVersion,
                                  UUID walletCatalogueId,
                                  Version walletVersion) throws CantInstallWalletException;
}
