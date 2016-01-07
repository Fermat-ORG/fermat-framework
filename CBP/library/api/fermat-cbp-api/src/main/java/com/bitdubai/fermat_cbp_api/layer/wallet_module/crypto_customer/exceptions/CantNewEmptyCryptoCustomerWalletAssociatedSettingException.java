package com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Franklin Marcano on 23/12/15.
 */
public class CantNewEmptyCryptoCustomerWalletAssociatedSettingException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Can't get the Contracts history of this Broker";

    /**
     * This is the constructor that every inherited FermatException must implement
     *
     * @param message        the short description of the why this exception happened, there is a public static constant called DEFAULT_MESSAGE that can be used here
     * @param cause          the exception that triggered the throwing of the current exception, if there are no other exceptions to be declared here, the cause should be null
     * @param context        a String that provides the values of the variables that could have affected the exception
     * @param possibleReason an explicative reason of why we believe this exception was most likely thrown
     */
    public CantNewEmptyCryptoCustomerWalletAssociatedSettingException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    /**
     * Constructor that call CantGetContractsWaitingForBrokerException(message, cause, "", "")
     *
     * @param message the short description of the why this exception happened, there is a public static constant called DEFAULT_MESSAGE that can be used here
     * @param cause   the exception that triggered the throwing of the current exception, if there are no other exceptions to be declared here, the cause should be null
     */
    public CantNewEmptyCryptoCustomerWalletAssociatedSettingException(String message, Exception cause) {
        super(message, cause, "", "");
    }

    /**
     * Constructor that call CantGetContractsWaitingForBrokerException(DEFAULT_MESSAGE, cause, "", "")
     *
     * @param cause the exception that triggered the throwing of the current exception, if there are no other exceptions to be declared here, the cause should be null
     */
    public CantNewEmptyCryptoCustomerWalletAssociatedSettingException(Exception cause) {
        super(DEFAULT_MESSAGE, cause, "", "");
    }
}
