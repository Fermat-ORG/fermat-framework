package com.bitdubai.fermat_core.layer.dap_sub_app_module;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;

import com.bitdubai.fermat_core.layer.dap_sub_app_module.asset_issuer_community.AssetIssuerSubAppModuleSubSystem;
import com.bitdubai.fermat_core.layer.dap_sub_app_module.asset_user_community.AssetUserSubAppModuleSubSystem;
import com.bitdubai.fermat_core.layer.dap_sub_app_module.redeem_point_community.RedeemPointSubAppModuleSubSystem;

import com.bitdubai.fermat_dap_api.layer.dap_sub_app_module.DAPSubAppModuleSubsystem;
import com.bitdubai.fermat_dap_api.layer.dap_sub_app_module.CantStartSubsystemException;

/**
 * Created by Nerio on 13/10/15.
 */
public class DAPSubAppModuleLayer implements PlatformLayer {

    private Plugin assetIssuerCommunity;

    private Plugin assetUserCommunity;

    private Plugin redeemPointCommunity;

    /**
     * Each layer is started and by that time has the chance to initialize its services.
     */
    @Override
    public void start() throws CantStartLayerException {

        assetIssuerCommunity = getPlugin(new AssetIssuerSubAppModuleSubSystem());

        assetUserCommunity = getPlugin(new AssetUserSubAppModuleSubSystem());

        redeemPointCommunity = getPlugin(new RedeemPointSubAppModuleSubSystem());
    }

    private Plugin getPlugin(DAPSubAppModuleSubsystem dapSubAppModuleSubsystem) throws CantStartLayerException {
        try {
            dapSubAppModuleSubsystem.start();

            return dapSubAppModuleSubsystem.getPlugin();

        } catch (CantStartSubsystemException exception) {
            throw new CantStartLayerException();
        }
    }

    public Plugin getAssetIssuerCommunity() {
        return assetIssuerCommunity;
    }

    public Plugin getAssetUserCommunity() {
        return assetUserCommunity;
    }

    public Plugin getRedeemPointCommunity() {
        return redeemPointCommunity;
    }
}
