package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSetObjectException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 20/12/15.
 */
public abstract class AbstractBrokerSubmitOnlineMerchandiseEventHandler implements FermatEventHandler {

    public BrokerSubmitOnlineMerchandiseRecorderService brokerSubmitOnlineMerchandiseRecorderService;

    public void setBrokerSubmitOnlineMerchandiseRecorderService(
            BrokerSubmitOnlineMerchandiseRecorderService brokerSubmitOnlineMerchandiseRecorderService) throws
            CantSetObjectException {
        if (brokerSubmitOnlineMerchandiseRecorderService == null) {
            throw new CantSetObjectException("brokerSubmitOnlineMerchandiseRecorderService is null");
        }
        this.brokerSubmitOnlineMerchandiseRecorderService = brokerSubmitOnlineMerchandiseRecorderService;
    }

}
