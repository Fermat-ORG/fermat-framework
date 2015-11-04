package com.bitdubai.fermat_core.layer.dap_actor;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_core.layer.dap_actor.asset_issuer.AssetIssuerActorSubsystem;
import com.bitdubai.fermat_core.layer.dap_actor.asset_user.AssetUserActorSubsystem;
import com.bitdubai.fermat_core.layer.dap_actor.redeem_point.RedeemPointActorSubsystem;
import com.bitdubai.fermat_dap_api.layer.dap_actor.CantStartSubsystemException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.DAPActorSubsystem;

/**
 * Created by Nerio on 10/09/15.
 */
public class DAPActorLayer implements PlatformLayer {

    private Plugin assetIssuerActor;

    private Plugin assetUserActor;

    private Plugin redeemPointActor;

    /**
     * Each layer is started and by that time has the chance to initialize its services.
     */
    @Override
    public void start() throws CantStartLayerException {

        assetIssuerActor = getPlugin(new AssetIssuerActorSubsystem());

        assetUserActor = getPlugin(new AssetUserActorSubsystem());

        redeemPointActor = getPlugin(new RedeemPointActorSubsystem());
    }

    private Plugin getPlugin(DAPActorSubsystem dapActorSubsystem) throws CantStartLayerException {
        try {
            dapActorSubsystem.start();
            return dapActorSubsystem.getPlugin();
        } catch (CantStartSubsystemException exception) {
            throw new CantStartLayerException();
        }
    }

    public Plugin getAssetIssuerActor() {
        return assetIssuerActor;
    }

    public Plugin getAssetUserActor() {
        return assetUserActor;
    }

    public Plugin getRedeemPointActor() {
        return redeemPointActor;
    }
}
