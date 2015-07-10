package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces;

import java.util.List;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.middleware.wallet_factory.interfaces.WalletManagerManager</code>
 * indicates the functionality of a WalletManagerManager
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface WalletManagerManager {
    public List<InstalledWallet> getInstalledWallets();
    public void installWallet(WalletInstallationInformation walletInstallationInformation);
}