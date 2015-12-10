package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSaveEventException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_cbp_api.layer.network_service.NegotiationTransmission.events.IncomingNegotiationTransactionEvent;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.database.CustomerBrokerNewNegotiationTransactionDatabaseDao;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.logging.Logger;

/**
 * Created by Yordin Alayn on 10.12.15.
 * Based On OpenContractRecorderService Create by Manuel Perez.
 */
public class CustomerBrokerNewServiceEventHandler {

    private EventManager eventManager;

    private CustomerBrokerNewNegotiationTransactionDatabaseDao customerBrokerNewNegotiationTransactionDatabaseDao;

    public CustomerBrokerNewServiceEventHandler(
        CustomerBrokerNewNegotiationTransactionDatabaseDao customerBrokerNewNegotiationTransactionDatabaseDao,
        EventManager eventManager
    ){
        this.customerBrokerNewNegotiationTransactionDatabaseDao = customerBrokerNewNegotiationTransactionDatabaseDao;
        this.eventManager                                       = eventManager;
    }

    /*PUBLIC METHOD*/
    public void incomingBusinessTransactionContractHashEventHandler(IncomingNegotiationTransactionEvent event) throws CantSaveEventException {
        Logger LOG = Logger.getGlobal();
        LOG.info("EVENT TEST, I GOT AN EVENT:\n"+event);
        this.customerBrokerNewNegotiationTransactionDatabaseDao.saveNewEvent(event.getEventType().getCode(), event.getSource().getCode());
        LOG.info("CHECK THE DATABASE");
    }
    /*END PUBLIC METHOD*/

    /*PRIVATE METHOD*/
    private void setDatabaseDao(CustomerBrokerNewNegotiationTransactionDatabaseDao customerBrokerNewNegotiationTransactionDatabaseDao)
            throws CantSetObjectException {
        if(customerBrokerNewNegotiationTransactionDatabaseDao==null){
            throw new CantSetObjectException("The CustomerBrokerNewNegotiationTransactionDatabaseDao is null");
        }
        this.customerBrokerNewNegotiationTransactionDatabaseDao=customerBrokerNewNegotiationTransactionDatabaseDao;
    }

    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }


    /*END PRIVATE METHOD*/

}
