package com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantListCryptoBrokersException</code>
 * is thrown when there is an error trying to BLABLABLA.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/12/2015.
 */
public class CantListCryptoBrokersException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T LIST CRYPTO BROKERS EXCEPTION";

    public CantListCryptoBrokersException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantListCryptoBrokersException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
