package com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantGetCryptoBrokerSearchResult</code>
 * is thrown when there is an error trying to BLABLABLA.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 29/12/2015.
 */
public class CantGetCryptoBrokerSearchResult extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T GET CRYPTO BROKER SEARCH RESULT EXCEPTION";

    public CantGetCryptoBrokerSearchResult(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetCryptoBrokerSearchResult(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
