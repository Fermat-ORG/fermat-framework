package com.bitdubai.fermat_core.layer.dap_identity.redeem_point;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_dap_api.layer.dap_identity.CantStartSubsystemException;
import com.bitdubai.fermat_dap_api.layer.dap_identity.DAPIdentitySubsystem;
import com.bitdubai.fermat_dap_plugin.layer.identity.redeem.point.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by Nerio on 11/09/15.
 */
public class RedeemPointIdentitySubsystem implements DAPIdentitySubsystem {

    Plugin plugin;

    @Override
    public void start() throws CantStartSubsystemException {
        try {
            DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
            plugin = developerBitDubai.getPlugin();
        }
        catch (Exception exception)
        {
            throw new CantStartSubsystemException(FermatException.wrapException(exception),"RedeemPointIdentitySubsystem","Unexpected Exception");
        }
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }
}
