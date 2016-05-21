package org.fermat.fermat_dap_android_sub_app_asset_factory.util;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;

import org.fermat.fermat_dap_api.layer.dap_module.asset_factory.interfaces.AssetFactoryModuleManager;

import java.util.List;

/**
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 1/27/16.
 */
public class Utils {
    public static String getBitcoinWalletPublicKey(AssetFactoryModuleManager manager) throws CantListWalletsException {
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
