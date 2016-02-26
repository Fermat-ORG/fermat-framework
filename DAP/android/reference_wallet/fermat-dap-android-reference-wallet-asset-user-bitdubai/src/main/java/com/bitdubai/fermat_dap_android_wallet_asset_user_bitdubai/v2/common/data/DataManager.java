package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.v2.common.data;

import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.v2.models.Issuer;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;

import java.util.List;

/**
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 2/24/16.
 */
public class DataManager {
    private AssetUserWalletSubAppModuleManager moduleManager;

    public DataManager(AssetUserWalletSubAppModuleManager moduleManager) {
        moduleManager = moduleManager;
    }

    public List<Issuer> getIssuers() {
        return Issuer.getTestData();
    }
}
