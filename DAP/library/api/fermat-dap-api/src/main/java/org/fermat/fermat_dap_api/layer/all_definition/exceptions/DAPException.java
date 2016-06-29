package org.fermat.fermat_dap_api.layer.all_definition.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 04/09/15.
 */
// TODO DELETE OR MOVE THIS
public class DAPException extends FermatException {

    public static final String DEFAULT_MESSAGE = "THE DAP LAYER HAS DETECTED AN EXCEPTION";

    public DAPException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public DAPException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public DAPException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public DAPException(final String message) {
        this(message, null);
    }

    public DAPException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public DAPException() {
        this(DEFAULT_MESSAGE);
    }
}
