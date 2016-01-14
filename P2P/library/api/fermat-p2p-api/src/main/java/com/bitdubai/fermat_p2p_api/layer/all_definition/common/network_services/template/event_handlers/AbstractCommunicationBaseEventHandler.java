package com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.abstract_classes.AbstractNetworkServiceV2;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.interfaces.CommunicationBaseEvent;


/**
 * Created by mati on 2015.10.23..
 */
public abstract class AbstractCommunicationBaseEventHandler<E extends CommunicationBaseEvent> implements FermatEventHandler{

    /*
    * Represent the networkService
    */
    protected AbstractNetworkServiceV2 networkService;

    protected NetworkService ns;

    /**
     * Constructor with parameter
     *
     * @param networkService
     */
    public AbstractCommunicationBaseEventHandler(AbstractNetworkServiceV2 networkService) {
        this.networkService = networkService;
    }

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

        if(ns!=null){
            if (((Service) this.ns).getStatus() == ServiceStatus.STARTED) {

                E event = (E) platformEvent;

                if(event.getNetworkServiceTypeApplicant() == ns.getNetworkServiceType()){

                    processEvent(event);

                }


            }
        }else if (networkService!=null) {

            if (((Service) this.networkService).getStatus() == ServiceStatus.STARTED) {

                E event = (E) platformEvent;

                if (event.getNetworkServiceTypeApplicant() == networkService.getNetworkServiceType()) {

                    processEvent(event);

                }


            }
        }
    }


    public abstract void processEvent(E event);
}
