package com.bitdubai.fermat_cbp_api.layer.user_level_business_transaction.customer_broker_purchase.interfaces;

/**
 * Created by franklin on 13/12/15.
 */
public interface CustomerBrokerPurchaseEventRecord {
    //TODO:Documentar
    String getEventId();
    String getEvent();
    String getSource();
    String getStatus();
    long   getTimestamp();
}
