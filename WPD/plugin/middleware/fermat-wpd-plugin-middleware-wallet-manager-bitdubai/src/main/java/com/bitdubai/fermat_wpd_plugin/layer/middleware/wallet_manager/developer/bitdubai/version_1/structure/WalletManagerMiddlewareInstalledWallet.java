package com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.InstalledLanguage;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.InstalledSkin;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;


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

    private String         publicKey;
    private UUID           walletCatalogId;
    private WalletCategory walletCategory;
    private WalletType     walletType;
    private String         walletName;
    private String         walletIcon;
    private String         walletPlatformIdentifier;
    private Version        version;
    private String         screenSize;
    private String         navigationStructureVersion;
    private String         walletDeveloper;
    private String         deviceUserPublicKey;
    private Platforms      platforms;

    private CryptoCurrency currency;

    private List<InstalledSkin>     skinsId;
    private List<InstalledLanguage> languagesId;
    private BlockchainNetworkType blockchainNetworkType;

    public WalletManagerMiddlewareInstalledWallet(WalletCategory walletCategory,
                                                  List<InstalledSkin> skinsId,
                                                  List<InstalledLanguage> languajesId,
                                                  String walletIcon, String walletName,
                                                  String publicKey,
                                                  String walletPlatformIdentifier,
                                                  Version version,
                                                  WalletType walletType,
                                                  String screenSize,
                                                  String navigationStructureVersion,
                                                  UUID walletCatalogId,
                                                  String walletDeveloper,
                                                  String deviceUserPublicKey,
                                                  BlockchainNetworkType blockchainNetworkType)
    {
        this.walletCategory = walletCategory;
        this.skinsId = skinsId;
        this.languagesId = languajesId;
        this.walletIcon = walletIcon;
        this.walletName = walletName;
        this.publicKey = publicKey;
        this.walletPlatformIdentifier = walletPlatformIdentifier;
        this.version = version;
        this.walletType = walletType;
        this.screenSize = screenSize;
        this.navigationStructureVersion = navigationStructureVersion;
        this.walletCatalogId = walletCatalogId;
        this.walletDeveloper =  walletDeveloper;
        this.deviceUserPublicKey = deviceUserPublicKey;
        this.blockchainNetworkType = blockchainNetworkType;
    }


    public WalletManagerMiddlewareInstalledWallet(WalletCategory walletCategory,
                                                  List<InstalledSkin> skinsId,
                                                  List<InstalledLanguage> languajesId,
                                                  String walletIcon, String walletName,
                                                  String publicKey,
                                                  String walletPlatformIdentifier,
                                                  Version version,
                                                  WalletType walletType,
                                                  String screenSize,
                                                  String navigationStructureVersion,
                                                  UUID walletCatalogId,
                                                  String walletDeveloper,
                                                  String deviceUserPublicKey,Platforms platforms,
                                                  BlockchainNetworkType blockchainNetworkType)
    {
        this.walletCategory = walletCategory;
        this.skinsId = skinsId;
        this.languagesId = languajesId;
        this.walletIcon = walletIcon;
        this.walletName = walletName;
        this.publicKey = publicKey;
        this.walletPlatformIdentifier = walletPlatformIdentifier;
        this.version = version;
        this.walletType = walletType;
        this.screenSize = screenSize;
        this.navigationStructureVersion = navigationStructureVersion;
        this.walletCatalogId = walletCatalogId;
        this.walletDeveloper =  walletDeveloper;
        this.deviceUserPublicKey = deviceUserPublicKey;
        this.platforms = platforms;
        this.blockchainNetworkType = blockchainNetworkType;
    }


    public WalletManagerMiddlewareInstalledWallet(WalletCategory walletCategory,
                                                  List<InstalledSkin> skinsId,
                                                  List<InstalledLanguage> languajesId,
                                                  String walletIcon, String walletName,
                                                  String publicKey,
                                                  String walletPlatformIdentifier,
                                                  Version version,
                                                  WalletType walletType,
                                                  String screenSize,
                                                  String navigationStructureVersion,
                                                  UUID walletCatalogId,
                                                  String walletDeveloper,
                                                  String deviceUserPublicKey,Platforms platforms,
                                                  BlockchainNetworkType blockchainNetworkType,
                                                  CryptoCurrency currency)
    {
        this.walletCategory = walletCategory;
        this.skinsId = skinsId;
        this.languagesId = languajesId;
        this.walletIcon = walletIcon;
        this.walletName = walletName;
        this.publicKey = publicKey;
        this.walletPlatformIdentifier = walletPlatformIdentifier;
        this.version = version;
        this.walletType = walletType;
        this.screenSize = screenSize;
        this.navigationStructureVersion = navigationStructureVersion;
        this.walletCatalogId = walletCatalogId;
        this.walletDeveloper =  walletDeveloper;
        this.deviceUserPublicKey = deviceUserPublicKey;
        this.platforms = platforms;
        this.blockchainNetworkType = blockchainNetworkType;
        this.currency = currency;

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
        return languagesId;
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
    public UUID getWalletCatalogId() {
        return walletCatalogId;
    }

    @Override
    public String getWalletDeveloperName(){
        return this.walletDeveloper;
    }

    @Override
    public String getWalletDeviceUserPublicKey(){
        return this.deviceUserPublicKey;
    }

    @Override
    public Platforms getPlatform() {
        // TODO NOW IS BY DEFECT
        if(this.platforms!=null){
            return this.platforms;
        }
        return Platforms.CRYPTO_CURRENCY_PLATFORM;
    }

    @Override
    public Actors getActorType() {
        // TODO NOW IS BY DEFECT
        return Actors.INTRA_USER;
    }

    @Override
    public CryptoCurrency getCryptoCurrency() {
        // TODO NOW IS BY DEFECT
        if(this.currency!=null){
            return this.currency;
        }
        return CryptoCurrency.BITCOIN;
    }

    public BlockchainNetworkType getBlockchainNetworkType() {
        return blockchainNetworkType;
    }

    public void setBlockchainNetworkType(BlockchainNetworkType blockchainNetworkType) {
        this.blockchainNetworkType = blockchainNetworkType;
    }
}