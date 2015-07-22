package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.NicheWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;

import java.util.List;
import java.util.UUID;

/**
 * Created by eze on 2015.07.10..
 */
public interface InstalledWallet {

    List<InstalledLanguage> getLanguagesId();
    List<InstalledSkin> getSkinsId();
    WalletCategory getWalletCategory();
    WalletType getWalletType();
    NicheWallet getNicheWallet();
    String getWalletIcon();
    UUID getWalletIdInThisDevice();
    String getWalletName();
    ReferenceWallet getReferenceWallet();
}
