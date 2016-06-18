/*
* @#CantSendCryptoAddressException.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package org.fermat.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions;

/**
 * The Class <code>com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantSendCryptoAddressException</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 26/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantSendCryptoAddressException extends org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException {


    public static final String DEFAULT_MESSAGE = "CAN'T SEND CRYPTO ADDRESS";


    public CantSendCryptoAddressException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantSendCryptoAddressException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantSendCryptoAddressException(final String message) {
        this(message, null);
    }

    public CantSendCryptoAddressException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantSendCryptoAddressException() {
        this(DEFAULT_MESSAGE);
    }
}
