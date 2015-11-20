package com.bitdubai.fermat_wpd_plugin.layer.desktop_module.wallet_manager.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
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
 *
 * Created by Natalia on 21/07/15.
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


    public WalletManagerModuleInstalledWallet(WalletCategory walletCategory,WalletType walletType, List<InstalledSkin> skinsId, List<InstalledLanguage> languajesId, String walletIcon, String walletName, String publicKey, String walletPlatformIdentifier, Version version) {
        this.walletCategory = walletCategory;
        this.skinsId = skinsId;
        this.languajesId = languajesId;
        this.walletIcon = walletIcon;
        this.walletName = walletName;
        this.publicKey = publicKey;
        this.walletPlatformIdentifier = walletPlatformIdentifier;
        this.version = version;
        this.walletType=walletType;
    }

    /**
     * InstalledWallet Interface implementation.
     */

    /**
     * This method gives us the list of all the languages installed for this wallet
     *
      */
    public List<InstalledLanguage> getLanguagesId(){
        return languajesId;
    }

    /**
     * This method gives us the list of all the skins installed for this wallet
     *
       */
    public List<InstalledSkin> getSkinsId(){
        return skinsId;
    }

    /**
     * This method tell us the category of the wallet
     *
      */
    public WalletCategory getWalletCategory(){
        return walletCategory;
    }

    /**
     * This method gives us a codification of the wallet identifier (the identifier is an enum that
     * registers the wallet)
     *
     */
    public String getWalletPlatformIdentifier(){
        return walletPlatformIdentifier;
    }

    /**
     * This method gives us the name of the wallet icon used to identify the image in the wallet resources plug-in
     *
     */
    public String getIcon(){
        return walletIcon;
    }

    /**
     * This method gives us the public key of the wallet in this device. It is used as identifier of
     * the wallet
     *
    */
    public String getWalletPublicKey(){
        return publicKey;
    }

    @Override
    public InterfaceType getType() {
        return InterfaceType.WALLET;
    }

    /**
     * This method gives us the wallet name
     *
     */
    public String getName(){
        return walletName;
    }

    /**
     * This method gives us the version of the wallet
     *
      */
    public Version getWalletVersion(){
        return version;
    }

    @Override
    public WalletType getWalletType() {
        return this.walletType;
    }


    @Override
    public String getAppPublicKey() {
        return publicKey;
    }
}
