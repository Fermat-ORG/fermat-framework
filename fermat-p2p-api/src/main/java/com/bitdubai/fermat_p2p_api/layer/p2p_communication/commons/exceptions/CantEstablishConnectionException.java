package com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions;


/**
 * Created by ciencias on 2/23/15.
 */
public class CantEstablishConnectionException extends CommunicationException {


	public static final String DEFAULT_MESSAGE = "CAN'T ESTABLISH THE CONNECTION";

	public CantEstablishConnectionException(final String message, final Exception cause, final String context, final String possibleReason) {
		super(message, cause, context, possibleReason);
	}

}
