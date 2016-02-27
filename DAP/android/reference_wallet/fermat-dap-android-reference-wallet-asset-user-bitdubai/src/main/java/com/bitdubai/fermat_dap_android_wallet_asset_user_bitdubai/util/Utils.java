package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.util;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;

import java.util.List;

/**
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 2/27/16.
 */
public class Utils {
    public static String getBitcoinWalletPublicKey(AssetUserWalletSubAppModuleManager manager) throws CantListWalletsException {
        List<InstalledWallet> installedWallets = manager.getInstallWallets();
        for (InstalledWallet installedWallet :
                installedWallets) {
            if (installedWallet.getPlatform().equals(Platforms.CRYPTO_CURRENCY_PLATFORM)) {
                return installedWallet.getWalletPublicKey();
            }
        }
        return null;
    }
}
