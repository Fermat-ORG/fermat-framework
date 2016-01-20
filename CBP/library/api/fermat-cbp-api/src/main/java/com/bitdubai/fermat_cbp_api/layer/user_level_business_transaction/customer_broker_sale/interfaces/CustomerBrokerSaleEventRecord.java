package com.bitdubai.fermat_cbp_api.layer.user_level_business_transaction.customer_broker_sale.interfaces;

/**
 * Created by franklin on 13/12/15.
 */
public interface CustomerBrokerSaleEventRecord {
    //TODO:Documentar
    String getEventId();
    String getEvent();
    String getSource();
    String getStatus();
    long   getTimestamp();
}
