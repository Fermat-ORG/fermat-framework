package com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.interfaces.NetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.interfaces.CommunicationBaseEvent;


/**
 * Created by mati on 2015.10.23..
 */
public abstract class AbstractCommunicationBaseEventHandler<E extends CommunicationBaseEvent> implements FermatEventHandler{

    /*
    * Represent the networkService
    */
    protected NetworkService ns;

    public AbstractCommunicationBaseEventHandler(NetworkService networkService) {
        this.ns = networkService;
    }

    /**
     * (non-Javadoc)
     *
     * @see FermatEventHandler#handleEvent(FermatEvent)
     *
     * @param platformEvent
     * @throws Exception
     */
    @Override
    public final void handleEvent(FermatEvent platformEvent) throws FermatException {

        if (((Service) this.ns).getStatus() == ServiceStatus.STARTED) {

            E event = (E) platformEvent;

            if(event.getNetworkServiceTypeApplicant() == ns.getNetworkServiceType()){

                processEvent(event);

            }


        }
    }


    public abstract void processEvent(E event);
}
