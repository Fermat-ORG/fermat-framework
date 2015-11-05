package com.bitdubai.fermat_core.layer.dap_transaction.asset_redemption;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.CantStartSubsystemException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.DAPTransactionSubsystem;
import com.bitdubai.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.bitdubai.DeveloperBitDubai;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 30/10/15.
 */
public class AssetRedeemPointRedemptionSubsystem implements DAPTransactionSubsystem {


    //VARIABLE DECLARATION

    private Plugin plugin;

    //CONSTRUCTORS

    public AssetRedeemPointRedemptionSubsystem() {
    }

    //PUBLIC METHODS
    @Override
    public void start() throws CantStartSubsystemException {
        try {
            DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
            plugin = developerBitDubai.getPlugin();
        } catch (Exception e) {
            throw new CantStartSubsystemException(FermatException.wrapException(e), "AssetRedeemPointRedemptionSubsystem", "Unexpected Exception");
        }
    }

    //PRIVATE METHODS

    //GETTER AND SETTERS

    @Override
    public Plugin getPlugin() {
        return plugin;
    }
    //INNER CLASSES
}
