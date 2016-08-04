package com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.structure;


import com.bitdubai.fermat_api.AppsStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.FermatAppType;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.InstalledLanguage;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.InstalledSkin;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_api.layer.interface_objects.InterfaceType;

import java.io.Serializable;
import java.util.List;


/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.structure.WalletManagerMiddlewareInstalledWallet</code>
 * is the implementation of InstalledWallet.
 * <p/>
 * <p/>
 * Created by Natalia on 21/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */


public class WalletManagerModuleInstalledWallet implements InstalledWallet, Serializable {

    private WalletCategory walletCategory;
    private WalletType walletType;
    private List<InstalledSkin> skinsId;
    private List<InstalledLanguage> languajesId;
    private String walletIcon;
    private String walletName;
    private String publicKey;
    private String walletPlatformIdentifier;
    private Version version;
    private int iconResource;
    private int position;
    private int notifications;
    private AppsStatus appStatus;
    private int bannerRes;
//    private AppStructureType appStructureType = AppStructureType.REFERENCE;


    public WalletManagerModuleInstalledWallet(WalletCategory walletCategory, WalletType walletType, List<InstalledSkin> skinsId, List<InstalledLanguage> languajesId, String walletIcon, String walletName, String publicKey, String walletPlatformIdentifier, Version version, AppsStatus appsStatus) {
        this.walletCategory = walletCategory;
        this.skinsId = skinsId;
        this.languajesId = languajesId;
        this.walletIcon = walletIcon;
        this.walletName = walletName;
        this.publicKey = publicKey;
        this.walletPlatformIdentifier = walletPlatformIdentifier;
        this.version = version;
        this.walletType = walletType;
        this.appStatus = appsStatus;
    }

    public void setIconResource(int iconResource) {
        this.iconResource = iconResource;
    }

    /**
     * InstalledWallet Interface implementation.
     */

    /**
     * This method gives us the list of all the languages installed for this wallet
     */
    public List<InstalledLanguage> getLanguagesId() {
        return languajesId;
    }

    /**
     * This method gives us the list of all the skins installed for this wallet
     */
    public List<InstalledSkin> getSkinsId() {
        return skinsId;
    }

    /**
     * This method tell us the category of the wallet
     */
    public WalletCategory getWalletCategory() {
        return walletCategory;
    }

    /**
     * This method gives us a codification of the wallet identifier (the identifier is an enum that
     * registers the wallet)
     */
    public String getWalletPlatformIdentifier() {
        return walletPlatformIdentifier;
    }

    /**
     * This method gives us the name of the wallet icon used to identify the image in the wallet resources plug-in
     */
    public String getIcon() {
        return walletIcon;
    }

    @Override
    public int getIconResource() {
        return iconResource;
    }

    @Override
    public void setBanner(int res) {
        this.bannerRes = res;
    }

    @Override
    public int getBannerRes() {
        return bannerRes;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    public void setNotifications(int notifications) {
        this.notifications = notifications;
    }

    @Override
    public int getNotifications() {
        return notifications;
    }

    /**
     * This method gives us the public key of the wallet in this device. It is used as identifier of
     * the wallet
     */
    public String getWalletPublicKey() {
        return publicKey;
    }

    @Override
    public InterfaceType getType() {
        return InterfaceType.WALLET;
    }

    /**
     * This method gives us the wallet name
     */
    public String getName() {
        return walletName;
    }

    /**
     * This method gives us the version of the wallet
     */
    public Version getWalletVersion() {
        return version;
    }

    @Override
    public WalletType getWalletType() {
        return this.walletType;
    }

    @Override
    public void setAppStatus(AppsStatus appsStatus) {
        this.appStatus = appsStatus;
    }


    @Override
    public String getAppName() {
        return walletName;
    }

    @Override
    public String getAppPublicKey() {
        return publicKey;
    }

    @Override
    public AppsStatus getAppStatus() {
        return appStatus;
    }

    @Override
    public FermatAppType getAppType() {
        return FermatAppType.WALLET;
    }

//    @Override
//    public AppStructureType getAppStructureType() {
//        return appStructureType;
//    }

    @Override
    public byte[] getAppIcon() {
        return new byte[0];
    }


}
