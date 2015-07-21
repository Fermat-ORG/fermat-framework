package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;



import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;

import java.util.Set;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */
public interface WalletSessionManager {

    public Set<WalletSession> listOpenWallets();
    public boolean openWalletSession(Wallets wallet);
    public boolean closeWalletSession(Wallets wallet);
    public boolean isWalletOpen(Wallets wallet);
}
