package com.bitdubai.fermat_cbp_plugin.layer.request.customer_broker_sale.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by eze on 2015.09.21..
 */
public class CantInitializeCustomerBrokerSaleRequestDaoException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INITIALIZE CUSTOMER BROKER SALE REQUEST DATABASE EXCEPTION";

    /**
     * This is the constructor that every inherited FermatException must implement
     *
     * @param message        the short description of the why this exception happened, there is a public static constant called DEFAULT_MESSAGE that can be used here
     * @param cause          the exception that triggered the throwing of the current exception, if there are no other exceptions to be declared here, the cause should be null
     * @param context        a String that provides the values of the variables that could have affected the exception
     * @param possibleReason an explicative reason of why we believe this exception was most likely thrown
     */
    public CantInitializeCustomerBrokerSaleRequestDaoException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
