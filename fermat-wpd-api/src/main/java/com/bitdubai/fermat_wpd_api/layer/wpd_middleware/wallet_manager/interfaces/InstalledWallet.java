package com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.InstalledSkin;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.InstalledLanguage;
//import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces.InstalledSkin;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Created by eze on 2015.07.10..
 */
public interface InstalledWallet extends Serializable {

    /**
     * This method gives us the list of all the languages installed for this wallet
     *
     * @return the saud list of languages
     */
    public List<InstalledLanguage> getLanguagesId();

    /**
     * This method gives us the list of all the skins installed for this wallet
     *
     * @return the saud list of skins
     */
    public List<InstalledSkin> getSkinsId();

    /**
     * This method tell us the category of the wallet
     *
     * @return the category of the wallet
     */
    public WalletCategory getWalletCategory();

    /**
     * This method tell us the type of the wallet
     * @return WalletType enum
     */
    public WalletType getWalletType();

    /**
     * This method tell us the Screen Size of the wallet
     * @return String ScreenSize
     */
    public String getWalletScreenSize();


    /**
     * This method tell us the Navigation Structure Version of the wallet
     * @return String NavigationStructureVersion
     */
    public String getWalletNavigationStructureVersion();

    /**
     * This method gives us a codification of the wallet identifier (the identifier is an enum that
     * registers the wallet)
     *
     * @return an string that is result of the method getCode of an enum that can be inferred by the
     *         WalletCategory of the wallet.
     */
    public String getWalletPlatformIdentifier();

    /**
     * This method gives us the name of the wallet icon used to identify the image in the wallet resources plug-in
     *
     * @return the name of the said icon
     */
    public String getWalletIcon();

    /**
     * This method gives us the public key of the wallet in this device. It is used as identifier of
     * the wallet
     *
     * @return the public key represented as a string
     */
    public String getWalletPublicKey();

    /**
     * This method gives us the wallet name
     *
     * @return the name of the wallet
     */
    public String getWalletName();

    /**
     * This method gives us the version of the wallet
     *
     * @return the version of the wallet
     */
    public Version getWalletVersion();

    /**
     * This method gives us the catalog id of the wallet
     *
     * @return the catalog id of the wallet
     */
    public UUID getWalletCatalogId();

    /**
     * This method gives us the developer name of the wallet
     *
     * @return the catalog id of the wallet
     */
    public String getWalletDeveloperName();

    /**
     *
     * @return
     */
    public String getWalletDeviceUserPublicKey();

    /**
     * @return an element of the Platforms enum indicating to which platform belongs.
     */
    Platforms getPlatform();

    /**
     * @return an element of the Actors enum indicating which type of actors uses it.
     */
    Actors getActorType();

    /**
     * @return an element of the CryptoCurrency enum indicating to which crypto currency manages.
     */
    CryptoCurrency getCryptoCurrency();

    /**
     *  return an element of the BlockchainNetworkType enum indicating to which network is connected.
     */
   // BlockchainNetworkType getBlockchainNetworkType();
}
