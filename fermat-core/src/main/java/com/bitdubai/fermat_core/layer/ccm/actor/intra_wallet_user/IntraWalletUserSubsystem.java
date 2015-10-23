package com.bitdubai.fermat_core.layer.ccm.actor.intra_wallet_user;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.PluginDeveloper;
import com.bitdubai.fermat_ccm_api.layer.actor.ActorSubsystemCCM;
import com.bitdubai.fermat_ccm_api.layer.actor.CantStartSubsystemException;
import com.bitdubai.fermat_ccm_plugin.layer.actor.intra_wallet_user.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by natalia on 19/10/15.
 */
public class IntraWalletUserSubsystem implements ActorSubsystemCCM {

    Plugin plugin;

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void start() throws CantStartSubsystemException {
        try {
            PluginDeveloper developer = new DeveloperBitDubai();
            plugin = developer.getPlugin();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException(e, null, null);
        }
    }
}
