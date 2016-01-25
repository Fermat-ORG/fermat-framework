package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.NegotiationTransactionCustomerBrokerNewPluginRoot;

/**
 * Created by yordin on 16/01/16.
 */
public class CustomerBromerNewEventHandler implements FermatEventHandler {

    private CustomerBrokerNewExecutorService executorService;
    private NegotiationTransactionCustomerBrokerNewPluginRoot pluginRoot;

    public CustomerBromerNewEventHandler(
        final CustomerBrokerNewExecutorService executorService,
        final NegotiationTransactionCustomerBrokerNewPluginRoot pluginRoot
    ){
        this.executorService = executorService;
        this.pluginRoot = pluginRoot;
    }

    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {

        if (this.pluginRoot.getStatus() == ServiceStatus.STARTED) {

            System.out.print("\n**** MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER NEW - EVENT HANDLER - 1) CustomerBromerNewEventHandler handleEvent ****\n");
            executorService.executePendingActions();

        }
    }

}
