package com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.CantCreateNewWalletException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantFindProcessException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantGetInstalledWalletException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantInstallLanguageException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantInstallSkinException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantRemoveWalletException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantRenameWalletException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantUninstallLanguageException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantUninstallSkinException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantUninstallWalletException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.DefaultWalletNotFoundException;

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
@PluginInfo(createdBy = "", maintainerMail = "", platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.DESKTOP_MODULE, plugin = Plugins.WALLET_MANAGER)
public interface WalletManagerManager extends FermatManager {

    /**
     * This method let the client create a new wallet of a type already intalled by the user.
     *
     * @param walletIdInTheDevice The identifier of the wallet to copy
     * @param newName the name to give to the wallet
     * @throws CantCreateNewWalletException
     */
    void createNewWallet(UUID walletIdInTheDevice, String newName) throws CantCreateNewWalletException;

       /**
     * This method returns the list of installed wallets in the device
     *
     * @return A list with the installed wallets information
     */
       List<InstalledWallet> getInstalledWallets() throws CantListWalletsException;

    /**

     * This method starts the process of installing a new language for an specific wallet
     *
     * @param walletCatalogueId the identifier of the wallet we want to install the language to
     * @param languageId the identifier of the language to install
     * @param language the enum that represent the language
     * @param label a label associated to the language (e.g. fot the English language we can have the UK label)
     * @param version the version of the language package
     * @throws CantInstallLanguageException
     */
    void installLanguage(UUID walletCatalogueId, UUID languageId, Languages language, String label, Version version) throws CantInstallLanguageException;

    /**
     * This method starts the process of installing a new skin for an specific wallet
     *
     * @param walletCatalogueId the identifier of the wallet we want to install the skin to
     * @param skinId the identifier of the skin
     * @param alias the alias (name) of the skin
     * @param Preview the name of the preview image of the skin
     * @param version the version of the skin
     * @throws CantInstallSkinException
     */
    void installSkin(UUID walletCatalogueId, UUID skinId, String alias, String Preview, Version version) throws CantInstallSkinException;

    /**
     *
     * This method returns the interface responsible of the installation process of a niche wallet
     *
     * @param walletCategory The category of the wallet to install
     * @param walletPlatformIdentifier an string that encodes the wallet identifier in the platform
     *                                 We are usign the term platform to identify the software installed
     *                                 in the device and not the network.
     * @return an interface to manage the installation of a new wallet
     * @throws CantFindProcessException
     */
    com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletInstallationProcess installWallet(WalletCategory walletCategory, String walletPlatformIdentifier) throws CantFindProcessException;


    /**
     * This method starts the process of uninstalling a new language for an specific wallet
     *
     * @param walletCatalogueId the identifier of the wallet where we want to uninstall the language
     * @param languageId the identifier of the language to uninstall
     */
    void uninstallLanguage(UUID walletCatalogueId, UUID languageId) throws CantUninstallLanguageException;

    /**
     * This method starts the process of uninstalling a new skin for an specific wallet
     *
     * @param walletCatalogueId the identifier of the wallet in which we want to uninstall the language
     * @param skinId the identifier of the skin
     */
    void uninstallSkin(UUID walletCatalogueId, UUID skinId) throws CantUninstallSkinException;

    /**
     * This method starts the uninstalling process of a walled
     *
     * @param walletIdInThisDevice the id of the wallet to uninstall
     */
    void uninstallWallet(UUID walletIdInThisDevice) throws CantUninstallWalletException;

    /**
     * This method removes a wallet created by a user. <p>
     * Note that this won't uninstall the wallet type. It is used to delete a copy of a wallet created
     * using the <code>createWallet</code> method.
     *
     * @param walletIdInTheDevice the identifier of the wallet to delete
     * @throws CantRemoveWalletException
     */
    void removeWallet(UUID walletIdInTheDevice) throws CantRemoveWalletException;

    /**
     * This method let us change the name (alias) of a given wallet.
     *
     * @param walletIdInTheDevice the identifier of the wallet to rename
     * @param newName the new name for the wallet
     * @throws CantRenameWalletException
     */
    void renameWallet(UUID walletIdInTheDevice, String newName) throws CantRenameWalletException;

    /**
     *  get Installed wallet
     * @return
     */
    InstalledWallet getInstalledWallet(String walletPublicKey) throws CantCreateNewWalletException;

    /**
     * Throw the method <code>getDefaultWallet</code> you can get a default wallet for a specific crypto currency and
     * network type
     *
     * @param cryptoCurrency        crypto currency that the wallet need to manage.
     * @param actorType             actor type that manages the wallet.
     * @param blockchainNetworkType blockchain network type in which is created the wallet.
     *
     * @return an instance of InstalledWallet with the values of the specific wallet.
     *
     * @throws CantGetInstalledWalletException if something goes wrong
     * @throws DefaultWalletNotFoundException  if cannot find a default installed wallet.
     */
    InstalledWallet getDefaultWallet(CryptoCurrency        cryptoCurrency,
                                     Actors                actorType,
                                     BlockchainNetworkType blockchainNetworkType) throws CantGetInstalledWalletException,
                                                                                         DefaultWalletNotFoundException ;
}