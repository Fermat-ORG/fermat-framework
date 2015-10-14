package com.bitdubai.fermat_core.layer.dap_sub_app_module.redeem_point_community;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_dap_api.layer.dap_sub_app_module.CantStartSubsystemException;
import com.bitdubai.fermat_dap_api.layer.dap_sub_app_module.DAPSubAppModuleSubsystem;
import com.bitdubai.fermat_dap_plugin.layer.sub_app_module.redeem.point.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by Nerio on 13/10/15.
 */
public class RedeemPointSubAppModuleSubSystem implements DAPSubAppModuleSubsystem {

    Plugin plugin;

    @Override
    public void start() throws CantStartSubsystemException {
        try {
            DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
            plugin = developerBitDubai.getPlugin();
        }
        catch (Exception exception)
        {
            throw new CantStartSubsystemException(FermatException.wrapException(exception),"DAPModuleSubsystem","Unexpected Exception");
        }
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }
}
