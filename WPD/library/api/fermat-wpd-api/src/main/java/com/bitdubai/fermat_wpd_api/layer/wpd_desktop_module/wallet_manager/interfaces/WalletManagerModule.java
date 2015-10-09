package com.bitdubai.fermat_wpd_api.layer.wpd_desktop_module.wallet_manager.interfaces;


import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_wallet_user.interfaces.IntraWalletUser;
import com.bitdubai.fermat_wpd_api.layer.wpd_desktop_module.wallet_manager.exceptions.WalletCreateNewIntraUserIdentityException;
import com.bitdubai.fermat_wpd_api.layer.wpd_desktop_module.wallet_manager.exceptions.WalletGetIntraUsersIdentityException;
import com.bitdubai.fermat_wpd_api.layer.wpd_desktop_module.wallet_manager.exceptions.WalletRemovalFailedException;
import com.bitdubai.fermat_wpd_api.layer.wpd_desktop_module.wallet_manager.exceptions.WalletRenameFailedException;
import com.bitdubai.fermat_wpd_api.layer.wpd_desktop_module.wallet_manager.exceptions.WalletsListFailedToLoadException;
import com.bitdubai.fermat_wpd_api.layer.wpd_desktop_module.wallet_manager.exceptions.NewWalletCreationFailedException;

import java.util.List;
import java.util.UUID;

/**
 * This interface is the connection between the front end and the middleware component of the
 * Wallet Manager SubApp
 *
 * @author Ezequiel Postan (ezequiel.postan@gmail.com)
 */
public interface WalletManagerModule {

    /**
     * This method let the client create a new wallet of a type already intalled by the user.
     *
     * @param walletIdInTheDevice The identifier of the wallet to copy
     * @param newName the name to give to the wallet
     * @throws NewWalletCreationFailedException
     */
    public void createNewWallet(UUID walletIdInTheDevice, String newName) throws NewWalletCreationFailedException;

    /**
     * This method returns the list of installed wallets in the device
     *
     * @return A list with the installed wallets information
     * @throws WalletsListFailedToLoadException
     */
    public List<InstalledWallet> getInstalledWallets() throws WalletsListFailedToLoadException;

    /**
     * This method removes a wallet created by a user. <p>
     * Note that this won't uninstall the wallet type. It is used to delete a copy of a wallet created
     * using the <code>createWallet</code> method.
     *
     * @param walletIdInTheDevice the identifier of the wallet to delete
     * @throws WalletRemovalFailedException
     */
    public void removeWallet(UUID walletIdInTheDevice) throws WalletRemovalFailedException;

    /**
     * This method let us change the name (alias) of a given wallet.
     *
     * @param walletIdInTheDevice the identifier of the wallet to rename
     * @param newName the new name for the wallet
     * @throws WalletRenameFailedException
     */
    public void renameWallet(UUID walletIdInTheDevice, String newName) throws WalletRenameFailedException;

    /**
     * This method create a new Intra User Identity
     *
     * @param alias
     * @param profileImage
     * @return
     * @throws WalletCreateNewIntraUserIdentityException
     */

     IntraWalletUser createNewIntraWalletUser(String alias, byte[] profileImage) throws WalletCreateNewIntraUserIdentityException;

    /**
     * This method returns a all Intra User Identity form current device user
     *
     * @return
     * @throws WalletGetIntraUsersIdentityException
     */
     List<IntraWalletUser> getAllIntraWalletUsersFromCurrentDeviceUser() throws WalletGetIntraUsersIdentityException;

    }
