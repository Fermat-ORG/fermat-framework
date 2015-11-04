package com.bitdubai.fermat_core.layer.dap_module.Wallet;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_dap_api.layer.dap_module.CantStartSubsystemException;
import com.bitdubai.fermat_dap_api.layer.dap_module.DAPModuleSubsystem;
import com.bitdubai.fermat_dap_plugin.layer.module.asset.issuer.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by franklin on 08/10/15.
 */
public class AssetIssuerWalletModuleSubSystem implements DAPModuleSubsystem {
    Plugin plugin;
    @Override
    public void start() throws CantStartSubsystemException {
        try
        {
            DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
            plugin = developerBitDubai.getPlugin();
        }catch (Exception exception)
        {
            throw new CantStartSubsystemException(FermatException.wrapException(exception),"DAPModuleSubsystem","Unexpected Exception");
        }

    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }
}
