/*
* @#VPNConnectionCloseNotificationEventHandler.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.events_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.VPNConnectionCloseNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.base.AbstractNetworkServiceBase;

/**
 * The Class <code>com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.event_handlers.VPNConnectionCloseNotificationEventHandler</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 23/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class VPNConnectionCloseNotificationEventHandler implements FermatEventHandler<VPNConnectionCloseNotificationEvent> {

    /**
     * Represent the networkService
     */
    private AbstractNetworkServiceBase networkService;

    /**
     * Constructor with parameter
     * @param networkService
     */
    public VPNConnectionCloseNotificationEventHandler(AbstractNetworkServiceBase networkService){
        this.networkService = networkService;
    }

    /**
     * (non-javadoc)
     * @see FermatEventHandler#handleEvent(FermatEvent)
     */
    @Override
    public void handleEvent(VPNConnectionCloseNotificationEvent vpnConnectionCloseNotificationEvent) throws FermatException {

        if (this.networkService.getStatus() == ServiceStatus.STARTED) {

            if(networkService.getNetworkServiceProfile() == null ||
                    vpnConnectionCloseNotificationEvent.getNetworkServiceApplicant() == null) {

                if (networkService.getNetworkServiceProfile() == null)
                    System.out.println("********************************************************** \n\n plugin " + this.networkService.eventSource + " networkService.getNetworkServiceProfile() == null \n\n\n **********************************************************");

                if (vpnConnectionCloseNotificationEvent.getNetworkServiceApplicant() == null)
                    System.out.println("********************************************************** \n\n plugin " + this.networkService.eventSource + " vpnConnectionCloseNotificationEvent.getNetworkServiceApplicant() == null \n\n\n **********************************************************");


            }else if (vpnConnectionCloseNotificationEvent.getNetworkServiceApplicant() == networkService.getNetworkServiceProfile().getNetworkServiceType()) {
                this.networkService.handleVpnConnectionCloseNotificationEvent(vpnConnectionCloseNotificationEvent);
            }
        }

    }
}
