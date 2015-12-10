package com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.exceptions;

import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CBPException;

/**
 * Created by root on 05/12/15.
 */
public class CantConfirmNotificationReception extends CBPException {

    public static final String DEFAULT_MESSAGE = "CAN'T CONFIRM THE NOTIFICATION RECEPTION";

    public CantConfirmNotificationReception(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
