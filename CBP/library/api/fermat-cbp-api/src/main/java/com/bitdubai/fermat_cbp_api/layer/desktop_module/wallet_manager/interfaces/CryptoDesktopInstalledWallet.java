package com.bitdubai.fermat_cbp_api.layer.desktop_module.wallet_manager.interfaces;


import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_cbp_api.all_definition.util.Version;

import java.util.List;
import java.util.UUID;

/**
 * Created by natalia on 16/09/15.
 */
public interface CryptoDesktopInstalledWallet {
    /**
     * This method gives us the list of all the languages installed for this wallet
     *
     * @return the list of languages
     */
    List<CryptoDesktopWalletInstalledLanguage> getLanguagesId();

    /**
     * This method gives us the list of all the skins installed for this wallet
     *
     * @return the saud list of skins
     */
    List<com.bitdubai.fermat_cbp_api.layer.desktop_module.wallet_manager.interfaces.CryptoDesktopInstalledSkin> getSkinsId();

    /**
     * This method tell us the category of the wallet
     *
     * @return the category of the wallet
     */
    WalletCategory getWalletCategory();

    /**
     * This method tell us the type of the wallet
     *
     * @return WalletType enum
     */
    WalletType getWalletType();

    /**
     * This method tell us the Screen Size of the wallet
     *
     * @return String ScreenSize
     */
    String getWalletScreenSize();


    /**
     * This method tell us the Navigation Structure Version of the wallet
     *
     * @return String NavigationStructureVersion
     */
    String getWalletNavigationStructureVersion();

    /**
     * This method gives us a codification of the wallet identifier (the identifier is an enum that
     * registers the wallet)
     *
     * @return an string that is result of the method getCode of an enum that can be inferred by the
     * WalletCategory of the wallet.
     */
    String getWalletPlatformIdentifier();

    /**
     * This method gives us the name of the wallet icon used to identify the image in the wallet resources plug-in
     *
     * @return the name of the said icon
     */
    String getWalletIcon();

    /**
     * This method gives us the public key of the wallet in this device. It is used as identifier of
     * the wallet
     *
     * @return the public key represented as a string
     */
    String getWalletPublicKey();

    /**
     * This method gives us the wallet name
     *
     * @return the name of the wallet
     */
    String getWalletName();

    /**
     * This method gives us the version of the wallet
     *
     * @return the version of the wallet
     */
    Version getWalletVersion();

    /**
     * This method gives us the catalog id of the wallet
     *
     * @return the catalog id of the wallet
     */
    UUID getWalletCatalogId();

    /**
     * This method gives us the developer name of the wallet
     *
     * @return the catalog id of the wallet
     */
    String getWalletDeveloperName();

    /**
     * @return
     */
    String getWalletDeviceUserPublicKey();
}

