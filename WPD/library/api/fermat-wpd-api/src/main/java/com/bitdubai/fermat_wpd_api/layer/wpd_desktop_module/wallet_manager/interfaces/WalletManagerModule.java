package com.bitdubai.fermat_wpd_api.layer.wpd_desktop_module.wallet_manager.interfaces;


import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_wpd_api.layer.wpd_desktop_module.wallet_manager.exceptions.NewWalletCreationFailedException;
import com.bitdubai.fermat_wpd_api.layer.wpd_desktop_module.wallet_manager.exceptions.WalletRemovalFailedException;
import com.bitdubai.fermat_wpd_api.layer.wpd_desktop_module.wallet_manager.exceptions.WalletRenameFailedException;
import com.bitdubai.fermat_wpd_api.layer.wpd_desktop_module.wallet_manager.exceptions.WalletsListFailedToLoadException;

import java.util.List;
import java.util.UUID;

/**
 * This interface is the connection between the front end and the middleware component of the
 * Wallet Manager SubApp
 *
 * @author Ezequiel Postan (ezequiel.postan@gmail.com)
 */
@PluginInfo(createdBy = "", maintainerMail = "", platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.DESKTOP_MODULE, plugin = Plugins.WALLET_MANAGER)
public interface WalletManagerModule extends FermatManager {

    /**
     * This method let the client create a new wallet of a type already intalled by the user.
     *
     * @param walletIdInTheDevice The identifier of the wallet to copy
     * @param newName the name to give to the wallet
     * @throws NewWalletCreationFailedException
     */
    void createNewWallet(UUID walletIdInTheDevice, String newName) throws NewWalletCreationFailedException;

    /**
     * This method returns the list of installed wallets in the device
     *
     * @return A list with the installed wallets informationt
     * @throws WalletsListFailedToLoadException
     */
    List<InstalledWallet> getInstalledWallets() throws WalletsListFailedToLoadException;

    /**
     * This method removes a wallet created by a user. <p>
     * Note that this won't uninstall the wallet type. It is used to delete a copy of a wallet created
     * using the <code>createWallet</code> method.
     *
     * @param walletIdInTheDevice the identifier of the wallet to delete
     * @throws WalletRemovalFailedException
     */
    void removeWallet(UUID walletIdInTheDevice) throws WalletRemovalFailedException;

    /**
     * This method let us change the name (alias) of a given wallet.
     *
     * @param walletIdInTheDevice the identifier of the wallet to rename
     * @param newName the new name for the wallet
     * @throws WalletRenameFailedException
     */
    void renameWallet(UUID walletIdInTheDevice, String newName) throws WalletRenameFailedException;



    }
