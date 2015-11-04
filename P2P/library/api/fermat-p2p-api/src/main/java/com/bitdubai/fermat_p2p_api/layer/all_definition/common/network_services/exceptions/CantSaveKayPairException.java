package com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.exceptions.CantSaveKayPairException</code>
 * is thrown when there is an error trying to save key pair.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 02/11/2015.
 */
public class CantSaveKayPairException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T SAVE KEY PAIR EXCEPTION";

    public CantSaveKayPairException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantSaveKayPairException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
