package com.bitdubai.fermat_core.layer._10_communication.cloud;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.p2p_communication.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.p2p_communication.CommunicationSubsystem;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by ciencias on 20.01.15.
 */
public class CloudSubsystem implements CommunicationSubsystem {

    private Plugin plugin;

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void start() throws CantStartSubsystemException {
        /**
         * I will choose from the different Developers available which implementation to use. Right now there is only
         * one, so it is not difficult to choose.
         */

        try {
            DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
            plugin = developerBitDubai.getPlugin();
        }
        catch (Exception e)
        {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException();
        }

    }


}
