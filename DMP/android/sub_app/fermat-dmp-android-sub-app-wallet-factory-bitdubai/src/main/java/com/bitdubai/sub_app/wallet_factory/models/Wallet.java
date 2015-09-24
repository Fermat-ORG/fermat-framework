package com.bitdubai.sub_app.wallet_factory.models;

import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.WalletNavigationStructure;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces.InstalledLanguage;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces.InstalledSkin;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.interfaces.InstalledWallet;

import java.util.List;

/**
 * Created by francisco on 23/09/15.
 */
public class Wallet implements InstalledWallet {



    private WalletNavigationStructure walletNavigationStructure;



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
        return WalletCategory.REFERENCE_WALLET;
    }

    @Override
    public String getWalletPlatformIdentifier() {
        return "";
    }

    @Override
    public String getWalletIcon() {
        return "";
    }

    @Override
    public String getWalletPublicKey() {
        return "wallet test";
    }

    @Override
    public String getWalletName() {
        return "Hola Mundo";
    }

    @Override
    public Version getWalletVersion() {
        return new Version(1, 0, 0);
    }

    @Override
    public WalletType getWalletType() {
        return WalletType.REFERENCE;
    }

    public WalletNavigationStructure getWalletNavigationStructure() {
        return walletNavigationStructure;
    }

    public String toString() {
        return getWalletPublicKey();
    }

    public void setNavigation(WalletNavigationStructure walletNavigationStructure) {
        this.walletNavigationStructure = walletNavigationStructure;
    }
}
