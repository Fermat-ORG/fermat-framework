package com.bitdubai.fermat_core.layer.p2p_communication.cloud_server;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CantStartSubsystemException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationSubsystem;

/**
 * Created by loui on 26/04/15.
 */
public class CloudServerSubsystem implements CommunicationSubsystem {

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
          //  DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
          //  plugin = developerBitDubai.getPlugin();
        }
        catch (Exception exception)
        {
            String message = CantStartSubsystemException.DEFAULT_MESSAGE;
            FermatException cause = (exception instanceof FermatException) ? (FermatException) exception : FermatException.wrapException(exception);
            String context = "Plugin: " + plugin.getClass();
            String possibleReason = "This vary between plugins, you have to check the cause of this exception to rally determine the reason";
            throw new CantStartSubsystemException(message, cause, context, possibleReason);
        }

    }


}
