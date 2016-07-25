package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_offline_merchandise.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSetObjectException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 20/12/15.
 */
public abstract class AbstractBrokerSubmitOfflineMerchandiseEventHandler implements FermatEventHandler {

    public BrokerSubmitOfflineMerchandiseRecorderService brokerSubmitOfflineMerchandiseRecorderService;

    public void setBrokerSubmitOfflineMerchandiseRecorderService(
            BrokerSubmitOfflineMerchandiseRecorderService brokerSubmitOfflineMerchandiseRecorderService) throws
            CantSetObjectException {
        if (brokerSubmitOfflineMerchandiseRecorderService == null) {
            throw new CantSetObjectException("brokerSubmitOfflineMerchandiseRecorderService is null");
        }
        this.brokerSubmitOfflineMerchandiseRecorderService = brokerSubmitOfflineMerchandiseRecorderService;
    }

}
