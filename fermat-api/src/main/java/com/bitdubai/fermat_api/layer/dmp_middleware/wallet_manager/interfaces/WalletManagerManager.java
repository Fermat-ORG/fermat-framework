package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces;

import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.middleware.wallet_factory.interfaces.WalletManagerManager</code>
 * indicates the functionality of a WalletManagerManager
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface WalletManagerManager {

    /**
     * This method returns the list of installed wallets in the device
     *
     * @return A list with the installed wallets information
     */
    public List<InstalledWallet> getInstalledWallets();

    /**
     * This method starts the process of installing a new language for an specific wallet
     *
     * @param walletIdInThisDevice the identifier of the wallet where we want to install the language
     * @param languageId the identifier of the language to install
     */
    public void installLanguage(UUID walletIdInThisDevice, UUID languageId);

    /**
     * This method starts the process of installing a new skin for an specific wallet
     *
     * @param walletIdInThisDevice the identifier of the wallet where we want to install the language
     * @param skinId the identifier of the skin
     */
    public void installSkin(UUID walletIdInThisDevice, UUID skinId);

    /**
     *  This method starts the process of installing a new wallet
     *
     * @param walletInstallationInformation the information needed for the installation
     */
    public void installWallet(WalletInstallationInformation walletInstallationInformation);

    /**
     * This method starts the process of uninstalling a new language for an specific wallet
     *
     * @param walletIdInThisDevice the identifier of the wallet where we want to uninstall the language
     * @param languageId the identifier of the language to uninstall
     */
    public void uninstallLanguage(UUID walletIdInThisDevice, UUID languageId);

    /**
     * This method starts the process of uninstalling a new skin for an specific wallet
     *
     * @param walletIdInThisDevice the identifier of the wallet where we want to uninstall the language
     * @param skinId the identifier of the skin
     */
    public void uninstallSkin(UUID walletIdInThisDevice, UUID skinId);

    /**
     * This method starts the uninstalling process of a walled
     *
     * @param walletIdInThisDevice the id of the wallet to uninstall
     */
    public void uninstallWallet(UUID walletIdInThisDevice);

    /**
     * This method gives us the progress of the current installation
     *
     * @return an integer that reflects the said progress
     */
    public int getInstallationProgress();
}