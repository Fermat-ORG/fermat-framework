package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.events_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.ClientConnectionCloseNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.base.AbstractNetworkServiceBase;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.interfaces.NetworkService;

/**
 * Created by Matias Furszyfer on 2015.10.23..
 */
public class CloseConnectionNotificationEventHandler implements FermatEventHandler {

    /**
     * Represent the networkService
     */
    private AbstractNetworkServiceBase networkService;

    /**
     * Constructor with parameter
     *
     * @param networkService
     */
    public CloseConnectionNotificationEventHandler(NetworkService networkService){
        this.networkService = (AbstractNetworkServiceBase) networkService;
    }

    /**
     * (non-javadoc)
     * @see FermatEventHandler#handleEvent(FermatEvent)
     */
    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {
        if (this.networkService.getStatus().equals(ServiceStatus.STARTED)) {
            this.networkService.handleCloseConnectionNotificationEvent((ClientConnectionCloseNotificationEvent) fermatEvent);
        }
    }

}
