/*
 * @#WsCommunicationCloudClientSubsystem.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_core.layer.p2p_communication.ws_cloud_client;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CantStartSubsystemException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationSubsystem;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.DeveloperBitDubai;


/**
 * The Class <code>com.bitdubai.fermat_core.layer.p2p_communication.ws_cloud_server.WsCommunicationCloudClientSubsystem</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 01/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WsCommunicationCloudClientSubsystem implements CommunicationSubsystem {

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
