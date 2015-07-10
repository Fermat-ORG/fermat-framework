package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformWalletType;

import java.util.UUID;

/**
 * Created by eze on 2015.07.10..
 */
public interface InstalledWallet {
    UUID getWalletId();
    String getWalletName();
    PlatformWalletType getWalletType();
    byte[] getIcon();
}
