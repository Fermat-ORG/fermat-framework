package com.bitdubai.fermat_core.layer.dap_transaction;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_core.layer.dap_transaction.asset_distribution.AssetDistributionSubsystem;
import com.bitdubai.fermat_core.layer.dap_transaction.asset_issuing.AssetIssuingSubsystem;
import com.bitdubai.fermat_core.layer.dap_transaction.asset_reception.AssetReceptionSubsystem;
import com.bitdubai.fermat_core.layer.dap_transaction.user_redemption.UserRedemptionSubsystem;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.CantStartSubsystemException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.DAPTransactionSubsystem;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/09/15.
 */
public class DAPTransactionLayer implements PlatformLayer {
    private Plugin assetIssuing;
    private Plugin assetDistribution;
    private Plugin assetReception;
    private Plugin userRedemption;

    @Override
    public void start() throws CantStartLayerException {
        assetIssuing =getPlugin(new AssetIssuingSubsystem());
        assetDistribution=getPlugin(new AssetDistributionSubsystem());
        assetReception=getPlugin(new AssetReceptionSubsystem());
        userRedemption=getPlugin(new UserRedemptionSubsystem());
    }

    private Plugin getPlugin(DAPTransactionSubsystem dapTransactionSubsystem) throws CantStartLayerException {
        try {
            dapTransactionSubsystem.start();
            return dapTransactionSubsystem.getPlugin();
        } catch (CantStartSubsystemException e) {
            throw new CantStartLayerException();
        }
    }

    public Plugin getAssetIssuingPlugin() {return assetIssuing;}
    public Plugin getAssetDistributionPlugin() {return assetDistribution;}
    public Plugin getAssetReceptionPlugin() {return assetReception;}
    public Plugin getUserRedemptionPlugin() {return userRedemption;}

}
