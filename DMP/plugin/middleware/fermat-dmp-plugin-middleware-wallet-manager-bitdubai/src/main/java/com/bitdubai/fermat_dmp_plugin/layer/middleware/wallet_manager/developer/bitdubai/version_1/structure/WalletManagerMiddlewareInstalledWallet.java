package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces.InstalledLanguage;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces.InstalledSkin;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces.InstalledWallet;

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


public class WalletManagerMiddlewareInstalledWallet implements InstalledWallet, Serializable {

    private WalletCategory walletCategory;
    private List<InstalledSkin> skinsId;
    private List<InstalledLanguage> languajesId;
    private String walletIcon;
    private String walletName;
    private String publicKey;
    private String walletPlatformIdentifier;
    private Version version;
    private WalletType walletType;
    private String screenSize;
    private String screenDensity;
    private String navigationStructureVersion;
    private String walletCatalogId;
    private String walletDeveloper;


    public WalletManagerMiddlewareInstalledWallet(WalletCategory walletCategory,
                                                  List<InstalledSkin> skinsId,
                                                  List<InstalledLanguage> languajesId,
                                                  String walletIcon, String walletName,
                                                  String publicKey,
                                                  String walletPlatformIdentifier,
                                                  Version version,
                                                  WalletType walletType,
                                                  String screenSize,
                                                  String screenDensity,
                                                  String navigationStructureVersion,
                                                  String walletCatalogId,
                                                  String walletDeveloper)
    {
        this.walletCategory = walletCategory;
        this.skinsId = skinsId;
        this.languajesId = languajesId;
        this.walletIcon = walletIcon;
        this.walletName = walletName;
        this.publicKey = publicKey;
        this.walletPlatformIdentifier = walletPlatformIdentifier;
        this.version = version;
        this.walletType = walletType;
        this.screenSize = screenSize;
        this.screenDensity = screenDensity;
        this.navigationStructureVersion = navigationStructureVersion;
        this.walletCatalogId = walletCatalogId;
        this.walletDeveloper =  walletDeveloper;
    }

    /**
     * InstalledWallet Interface implementation.
     */

    /**
     * This method gives us the list of all the languages installed for this wallet
     *
     */
    @Override
    public List<InstalledLanguage> getLanguagesId(){
        return languajesId;
    }

    /**
     * This method gives us the list of all the skins installed for this wallet
     *
       */
    @Override
    public List<InstalledSkin> getSkinsId(){
        return skinsId;
    }

    /**
     * This method tell us the category of the wallet
     *
      */
    @Override
    public WalletCategory getWalletCategory(){
        return walletCategory;
    }

    /**
     * This method gives us the wallet Type for this wallet
     *
     */
    @Override
    public WalletType getWalletType(){
            return this.walletType;
    }

    /**
     * This method gives us the screen Size for this wallet
     *
     */
    @Override
    public String getWalletScreenSize(){
        return this.screenSize;
    }

    /**
     * This method gives us the screen Density for this wallet
     *
     */
    @Override
    public String getWalletScreenDensity(){
        return this.screenDensity = screenDensity;
    }

    /**
     * This method gives us the navigation structure version for this wallet
     *
     */
    @Override
    public String getWalletNavigationStructureVersion(){
        return this.navigationStructureVersion;
    }


    /**
     * This method gives us a codification of the wallet identifier (the identifier is an enum that
     * registers the wallet)
     *
     */
    @Override
    public String getWalletPlatformIdentifier(){
        return walletPlatformIdentifier;
    }

    /**
     * This method gives us the name of the wallet icon used to identify the image in the wallet resources plug-in
     *
     */
    @Override
    public String getWalletIcon(){
        return walletIcon;
    }

    /**
     * This method gives us the public key of the wallet in this device. It is used as identifier of
     * the wallet
     *
    */
    @Override
    public String getWalletPublicKey(){
        return publicKey;
    }

    /**
     * This method gives us the wallet name
     *
     */
    @Override
    public String getWalletName(){
        return walletName;
    }

    /**
     * This method gives us the version of the wallet
     *
      */
    @Override
    public Version getWalletVersion(){
        return version;
    }

    /**
     * This method gives us the catalog id for this wallet
     *
     */
    @Override
    public String getWalletCatalogId() {
        return walletCatalogId;
    }

    @Override
    public String getWalletDeveloper(){
        return this.walletDeveloper;
    }

}
