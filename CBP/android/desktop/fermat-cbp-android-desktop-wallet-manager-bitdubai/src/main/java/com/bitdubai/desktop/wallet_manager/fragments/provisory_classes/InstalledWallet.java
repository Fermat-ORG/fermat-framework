package com.bitdubai.desktop.wallet_manager.fragments.provisory_classes;

import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.InstalledLanguage;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.InstalledSkin;
import com.bitdubai.fermat_api.layer.interface_objects.InterfaceType;

import java.util.List;

/**
 * Created by Matias Furszyfer on 2015.08.19..
 */

public class InstalledWallet implements com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet {



    private WalletCategory walletCategory;
    private WalletType walletType;
    private List<InstalledSkin> skinsId;
    private List<InstalledLanguage> languajesId;
    private String walletIcon;
    private String walletName;
    private String publicKey;
    private String walletPlatformIdentifier;
    private Version version;

    public InstalledWallet(WalletCategory walletCategory, WalletType walletType, List<InstalledSkin> skinsId, List<InstalledLanguage> languajesId, String walletIcon, String walletName, String publicKey, String walletPlatformIdentifier, Version version) {
        this.walletCategory = walletCategory;
        this.walletType = walletType;
        this.skinsId = skinsId;
        this.languajesId = languajesId;
        this.walletIcon = walletIcon;
        this.walletName = walletName;
        this.publicKey = publicKey;
        this.walletPlatformIdentifier = walletPlatformIdentifier;
        this.version = version;
    }

    /**
     * This method gives us the list of all the languages installed for this wallet
     *
     * @return the saud list of languages
     */
    @Override
    public List<InstalledLanguage> getLanguagesId() {
        return null;
    }

    /**
     * This method gives us the list of all the skins installed for this wallet
     *
     * @return the saud list of skins
     */
    @Override
    public List<InstalledSkin> getSkinsId() {
        return null;
    }

    /**
     * This method tell us the category of the wallet
     *
     * @return the category of the wallet
     */
    @Override
    public WalletCategory getWalletCategory() {
        return walletCategory;
    }

    /**
     * This method gives us a codification of the wallet identifier (the identifier is an enum that
     * registers the wallet)
     *
     * @return an string that is result of the method getCode of an enum that can be inferred by the
     * WalletCategory of the wallet.
     */
    @Override
    public String getWalletPlatformIdentifier() {
        return walletPlatformIdentifier;
    }

    /**
     * This method gives us the name of the wallet icon used to identify the image in the wallet resources plug-in
     *
     * @return the name of the said icon
     */
    @Override
    public String getIcon() {
        return walletIcon;
    }

    /**
     * This method gives us the public key of the wallet in this device. It is used as identifier of
     * the wallet
     *
     * @return the public key represented as a string
     */
    @Override
    public String getWalletPublicKey() {
        return publicKey;
    }

    @Override
    public InterfaceType getType() {
        return InterfaceType.WALLET;
    }

    /**
     * This method gives us the wallet name
     *
     * @return the name of the wallet
     */
    @Override
    public String getName() {
        return walletName;
    }

    /**
     * This method gives us the version of the wallet
     *
     * @return the version of the wallet
     */
    @Override
    public Version getWalletVersion() {
        return version;
    }

    @Override
    public WalletType getWalletType() {
        return walletType;
    }
}
