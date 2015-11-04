package com.bitdubai.fermat_core.layer.cbp.sub_app_module.crypto_customer_identity;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.CBPSubAppModuleSubsystem;
import com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_customer_identity.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by jorge on 14-10-2015.
 */
public class CryptoCustomerIdentitySubsystem implements CBPSubAppModuleSubsystem {

    Plugin plugin;

    @Override
    public void start() throws CantStartSubsystemException {
        try{
            DeveloperBitDubai bitDubai = new DeveloperBitDubai();
            plugin = bitDubai.getPlugin();
        } catch(Exception ex){
            throw new CantStartSubsystemException(CantStartSubsystemException.DEFAULT_MESSAGE, FermatException.wrapException(ex), "Sub App Module Crypto Broker Identity", "Check the cause");
        }

    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }
}
