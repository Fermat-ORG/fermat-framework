package com.bitdubai.fermat_core.layer.dap_identity;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_core.layer.dap_identity.asset_issuer.AssetIssuerIdentitySubsystem;
import com.bitdubai.fermat_core.layer.dap_identity.asset_user.AssetUserIdentitySubsystem;
import com.bitdubai.fermat_core.layer.dap_identity.redeem_point.RedeemPointIdentitySubsystem;
import com.bitdubai.fermat_dap_api.layer.dap_identity.CantStartSubsystemException;
import com.bitdubai.fermat_dap_api.layer.dap_identity.DAPIdentitySubsystem;

/**
 * Created by Nerio on 10/09/15.
 */
public class DAPIdentityLayer implements PlatformLayer {

    private Plugin assetIssuerIdentity;

    private Plugin assetUserIdentity;

    private Plugin redeemPointIdentity;
    /**
     * Each layer is started and by that time has the chance to initialize its services.
     */
    @Override
    public void start() throws CantStartLayerException {

        assetIssuerIdentity = getPlugin(new AssetIssuerIdentitySubsystem());

        assetUserIdentity = getPlugin(new AssetUserIdentitySubsystem());

        redeemPointIdentity = getPlugin(new RedeemPointIdentitySubsystem());
    }

    private Plugin getPlugin(DAPIdentitySubsystem dapIdentitySubsystem) throws CantStartLayerException {
        try {
            dapIdentitySubsystem.start();
            return dapIdentitySubsystem.getPlugin();
        } catch (CantStartSubsystemException exception) {
            throw new CantStartLayerException();
        }
    }

    public Plugin getAssetIssuerIdentity() {
        return assetIssuerIdentity;
    }

    public Plugin getAssetUserIdentity() {return assetUserIdentity;}

    public Plugin getRedeemPointIdentity() {
        return redeemPointIdentity;
    }
}
