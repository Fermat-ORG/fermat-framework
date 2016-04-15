package com.bitdubai.fermat_api.layer.all_definition.util.ip_address;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.all_definition.util.ip_address.CantGetCurrentIPAddressException</code>
 * is thrown when there is an error trying to get the current ip address.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 01/04/2016.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantGetCurrentIPAddressException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T GET CURRENT IP ADDRESS EXCEPTION";

    public CantGetCurrentIPAddressException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetCurrentIPAddressException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
