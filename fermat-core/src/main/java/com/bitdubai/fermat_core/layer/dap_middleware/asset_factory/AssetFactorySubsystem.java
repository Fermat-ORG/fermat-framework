package com.bitdubai.fermat_core.layer.dap_middleware.asset_factory;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantStartSubsystemException;



/**
 * Created by franklin on 10/09/15.
 */
public class AssetFactorySubsystem implements com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.AssetFactorySubsystem {
    Plugin plugin;


    @Override
    public void star() throws CantStartSubsystemException {
//        try {
//            DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
//            plugin = developerBitDubai.getPlugin();
//        }
//        catch (Exception exception)
//        {
//            throw new CantStartSubsystemException(FermatException.wrapException(exception),"AssetIssuingSubsystem","Unexpected Exception");
//        }
    }

    @Override
    public Plugin getPlugin() {
        return null;
    }
}
