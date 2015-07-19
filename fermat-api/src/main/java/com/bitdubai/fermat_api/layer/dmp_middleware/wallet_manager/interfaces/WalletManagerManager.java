package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.exceptions.CantCreateNewWalletException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.exceptions.CantInstallLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.exceptions.CantInstallSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.exceptions.CantInstallWalletException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.exceptions.CantListWalletsException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.exceptions.CantRemoveWalletException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.exceptions.CantRenameWalletException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.exceptions.CantUninstallLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.exceptions.CantUninstallSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.exceptions.CantUninstallWalletException;

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
     * This method let the client create a new wallet of a type already intalled by the user.
     *
     * @param walletIdInTheDevice The identifier of the wallet to copy
     * @param newName the name to give to the wallet
     * @throws CantCreateNewWalletException
     */
    public void createNewWallet(UUID walletIdInTheDevice, String newName) throws CantCreateNewWalletException;

    /**
     * This method gives us the progress of the current installation
     *
     * @return an integer that reflects the said progress
     */
    public int getInstallationProgress();

    /**
     * This method returns the list of installed wallets in the device
     *
     * @return A list with the installed wallets information
     */
    public List<InstalledWallet> getInstalledWallets() throws CantListWalletsException;

    /**
     * This method starts the process of installing a new language for an specific wallet
     *
     * @param walletIdInThisDevice the identifier of the wallet where we want to install the language
     * @param languageId the identifier of the language to install
     */
    public void installLanguage(UUID walletIdInThisDevice, UUID languageId) throws CantInstallLanguageException;

    /**
     * This method starts the process of installing a new skin for an specific wallet
     *
     * @param walletIdInThisDevice the identifier of the wallet where we want to install the language
     * @param skinId the identifier of the skin
     */
    public void installSkin(UUID walletIdInThisDevice, UUID skinId) throws CantInstallSkinException;

    /**
     *  This method starts the process of installing a new wallet
     *
     * @param walletInstallationInformation the information needed for the installation
     */
    public void installWallet(WalletInstallationInformation walletInstallationInformation) throws CantInstallWalletException;

    /**
     * This method starts the process of uninstalling a new language for an specific wallet
     *
     * @param walletIdInThisDevice the identifier of the wallet where we want to uninstall the language
     * @param languageId the identifier of the language to uninstall
     */
    public void uninstallLanguage(UUID walletIdInThisDevice, UUID languageId) throws CantUninstallLanguageException;

    /**
     * This method starts the process of uninstalling a new skin for an specific wallet
     *
     * @param walletIdInThisDevice the identifier of the wallet where we want to uninstall the language
     * @param skinId the identifier of the skin
     */
    public void uninstallSkin(UUID walletIdInThisDevice, UUID skinId) throws CantUninstallSkinException;

    /**
     * This method starts the uninstalling process of a walled
     *
     * @param walletIdInThisDevice the id of the wallet to uninstall
     */
    public void uninstallWallet(UUID walletIdInThisDevice) throws CantUninstallWalletException;

    /**
     * This method removes a wallet created by a user. <p>
     * Note that this won't uninstall the wallet type. It is used to delete a copy of a wallet created
     * using the <code>createWallet</code> method.
     *
     * @param walletIdInTheDevice the identifier of the wallet to delete
     * @throws CantRemoveWalletException
     */
    public void removeWallet(UUID walletIdInTheDevice) throws CantRemoveWalletException;

    /**
     * This method let us change the name (alias) of a given wallet.
     *
     * @param walletIdInTheDevice the identifier of the wallet to rename
     * @param newName the new name for the wallet
     * @throws CantRenameWalletException
     */
    public void renameWallet(UUID walletIdInTheDevice, String newName) throws CantRenameWalletException;

}