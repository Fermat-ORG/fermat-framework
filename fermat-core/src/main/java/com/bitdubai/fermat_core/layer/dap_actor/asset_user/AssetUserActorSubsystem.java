package com.bitdubai.fermat_core.layer.dap_actor.asset_user;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_dap_api.layer.dap_actor.CantStartSubsystemException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.DAPActorSubsystem;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.DeveloperBitDubaiOld;

/**
 * Created by Nerio on 11/09/15.
 */
public class AssetUserActorSubsystem implements DAPActorSubsystem {

    Plugin plugin;

    @Override
    public void start() throws CantStartSubsystemException {
        try {
            DeveloperBitDubaiOld developerBitDubai = new DeveloperBitDubaiOld();
            plugin = developerBitDubai.getPlugin();
        }
        catch (Exception exception)
        {
            throw new CantStartSubsystemException(FermatException.wrapException(exception),"AssetUserActorSubsystem","Unexpected Exception");
        }
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }
}