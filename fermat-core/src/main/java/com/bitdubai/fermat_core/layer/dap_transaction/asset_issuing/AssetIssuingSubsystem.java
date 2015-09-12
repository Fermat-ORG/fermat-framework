package com.bitdubai.fermat_core.layer.dap_transaction.asset_issuing;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_dap_api.layer.transaction.CantStartSubsystemException;
import com.bitdubai.fermat_dap_api.layer.transaction.DAPTransactionSubsystem;
import com.bitdubai.fermat_dap_plugin.layer.transaction.asset_issuing.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/09/15.
 */
public class AssetIssuingSubsystem implements DAPTransactionSubsystem {

    Plugin plugin;
    @Override
    public void start() throws CantStartSubsystemException {
        try {
            DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
            plugin = developerBitDubai.getPlugin();
        }
        catch (Exception exception)
        {
            //System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException(FermatException.wrapException(exception),"AssetIssuingSubsystem","Unexpected Exception");
        }
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }
}
