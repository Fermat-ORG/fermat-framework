package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.InstalledLanguage;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.InstalledSkin;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CBPInstalledWallet;

import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 26.05.16.
 */
public class CBPInstalledWalletImpl implements CBPInstalledWallet {

    private List<InstalledLanguage> languagesId;
    private List<InstalledSkin> skinsId;
    private WalletCategory walletCategory;
    private WalletType walletType;
    private String walletScreenSize;
    private String walletNavigationStructureVersion;
    private String walletPlatformIdentifier;
    private String walletIcon;
    private String walletPublicKey;
    private String walletName;
    private Version walletVersion;
    private UUID walletCatalogId;
    private String walletDeveloperName;
    private String walletDeviceUserPublicKey;
    private Platforms platform;
    private Actors actorType;
    private CryptoCurrency cryptoCurrency;
    private Currency currency;

    public CBPInstalledWalletImpl(
            List<InstalledLanguage> languagesId,
            List<InstalledSkin> skinsId,
            WalletCategory walletCategory,
            WalletType walletType,
            String walletScreenSize,
            String walletNavigationStructureVersion,
            String walletPlatformIdentifier,
            String walletIcon,
            String walletPublicKey,
            String walletName,
            Version walletVersion,
            UUID walletCatalogId,
            String walletDeveloperName,
            String walletDeviceUserPublicKey,
            Platforms platform,
            Actors actorType,
            CryptoCurrency cryptoCurrency
    ) {
        this.languagesId = languagesId;
        this.skinsId = skinsId;
        this.walletCategory = walletCategory;
        this.walletType = walletType;
        this.walletScreenSize = walletScreenSize;
        this.walletNavigationStructureVersion = walletNavigationStructureVersion;
        this.walletPlatformIdentifier = walletPlatformIdentifier;
        this.walletIcon = walletIcon;
        this.walletPublicKey = walletPublicKey;
        this.walletName = walletName;
        this.walletVersion = walletVersion;
        this.walletCatalogId = walletCatalogId;
        this.walletDeveloperName = walletDeveloperName;
        this.walletDeviceUserPublicKey = walletDeviceUserPublicKey;
        this.platform = platform;
        this.actorType = actorType;
        this.cryptoCurrency = cryptoCurrency;
    }

    @Override
    public List<InstalledLanguage> getLanguagesId() {
        return this.languagesId;
    }

    @Override
    public List<InstalledSkin> getSkinsId() {
        return this.skinsId;
    }

    @Override
    public WalletCategory getWalletCategory() {
        return this.walletCategory;
    }

    @Override
    public WalletType getWalletType() {
        return this.walletType;
    }

    @Override
    public String getWalletScreenSize() {
        return this.walletScreenSize;
    }

    @Override
    public String getWalletNavigationStructureVersion() {
        return this.walletNavigationStructureVersion;
    }

    @Override
    public String getWalletPlatformIdentifier() {
        return this.walletPlatformIdentifier;
    }

    @Override
    public String getWalletIcon() {
        return this.walletIcon;
    }

    @Override
    public String getWalletPublicKey() {
        return this.walletPublicKey;
    }

    @Override
    public String getWalletName() {
        return this.walletName;
    }

    @Override
    public Version getWalletVersion() {
        return this.walletVersion;
    }

    @Override
    public UUID getWalletCatalogId() {
        return this.walletCatalogId;
    }

    @Override
    public String getWalletDeveloperName() {
        return this.walletDeveloperName;
    }

    @Override
    public String getWalletDeviceUserPublicKey() {
        return this.walletDeviceUserPublicKey;
    }

    @Override
    public Platforms getPlatform() {
        return this.platform;
    }

    @Override
    public Actors getActorType() {
        return this.actorType;
    }

    @Override
    public CryptoCurrency getCryptoCurrency() {
        return this.cryptoCurrency;
    }

    @Override
    public Currency getCurrency() {
        return this.currency;
    }

    @Override
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
