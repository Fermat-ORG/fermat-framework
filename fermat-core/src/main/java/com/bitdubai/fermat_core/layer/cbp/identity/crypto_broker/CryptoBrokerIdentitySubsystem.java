package com.bitdubai.fermat_core.layer.cbp.identity.crypto_broker;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.CBPIdentitySubsystem;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.DeveloperBitdubai;


/**
 * Created by jorge on 13-10-2015.
 */
public class CryptoBrokerIdentitySubsystem implements CBPIdentitySubsystem {

    Plugin plugin;

    @Override
    public void start() throws CantStartSubsystemException {
        try{
            DeveloperBitdubai developer = new DeveloperBitdubai();
            plugin = developer.getPlugin();
        } catch(Exception ex){
            throw new CantStartSubsystemException(CantStartSubsystemException.DEFAULT_MESSAGE, FermatException.wrapException(ex), "Crypto Broker Identity","Crypto Broker Identity");
        }
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }
}
