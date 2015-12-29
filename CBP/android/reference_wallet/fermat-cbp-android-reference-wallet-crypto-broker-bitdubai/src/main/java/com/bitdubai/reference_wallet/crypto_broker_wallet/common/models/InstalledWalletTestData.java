package com.bitdubai.reference_wallet.crypto_broker_wallet.common.models;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.InstalledLanguage;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.InstalledSkin;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;

import java.util.List;
import java.util.UUID;

/**
 * Created by nelson on 28/12/15.
 */
public class InstalledWalletTestData implements InstalledWallet {
    private String name;
    private Platforms platform;
    private CryptoCurrency currency;
    private String publicKey;

    public InstalledWalletTestData(String name, Platforms platform, CryptoCurrency currency, String publicKey) {
        this.name = name;
        this.platform = platform;
        this.currency = currency;
        this.publicKey = publicKey;
    }

    @Override
    public List<InstalledLanguage> getLanguagesId() {
        return null;
    }

    @Override
    public List<InstalledSkin> getSkinsId() {
        return null;
    }

    @Override
    public WalletCategory getWalletCategory() {
        return null;
    }

    @Override
    public WalletType getWalletType() {
        return null;
    }

    @Override
    public String getWalletScreenSize() {
        return null;
    }

    @Override
    public String getWalletNavigationStructureVersion() {
        return null;
    }

    @Override
    public String getWalletPlatformIdentifier() {
        return null;
    }

    @Override
    public String getWalletIcon() {
        return null;
    }

    @Override
    public String getWalletPublicKey() {
        return publicKey;
    }

    @Override
    public String getWalletName() {
        return name;
    }

    @Override
    public Version getWalletVersion() {
        return null;
    }

    @Override
    public UUID getWalletCatalogId() {
        return null;
    }

    @Override
    public String getWalletDeveloperName() {
        return null;
    }

    @Override
    public String getWalletDeviceUserPublicKey() {
        return null;
    }

    @Override
    public Platforms getPlatform() {
        return platform;
    }

    @Override
    public Actors getActorType() {
        return null;
    }

    @Override
    public CryptoCurrency getCryptoCurrency() {
        return currency;
    }
}
