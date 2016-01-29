package com.bitdubai.fermat_cbp_api.layer.user_level_business_transaction.customer_broker_purchase.interfaces;

/**
 * Created by franklin on 13/12/15.
 */
public interface CustomerBrokerPurchaseEventRecord {
    /**
     * The method <code>getEventId</code> returns the event id of the customer broker purchase event record
     *
     * @return an String id of the event
     */
    String getEventId();

    /**
     * The method <code>getEvent</code> returns the event of the customer broker purchase event record
     *
     * @return an String of the event
     */
    String getEvent();

    /**
     * The method <code>getSource</code> returns the source of the customer broker purchase event record
     *
     * @return an String of the source
     */
    String getSource();

    /**
     * The method <code>getStatus</code> returns the status of the customer broker purchase event record
     *
     * @return an String of the status
     */
    String getStatus();

    /**
     * The method <code>getTimestamp</code> returns the timestamp of the customer broker purchase event record
     *
     * @return a long of the timestamp
     */
    long getTimestamp();
}
