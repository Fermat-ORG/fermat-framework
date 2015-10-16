package com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions;


/**
 * Created by ciencias on 2/23/15.
 */
public class CantRegisterComponentException extends CommunicationException {


	public static final String DEFAULT_MESSAGE = "CAN'T SEND MESSAGE";

	public CantRegisterComponentException(final String message, final Exception cause, final String context, final String possibleReason) {
		super(message, cause, context, possibleReason);
	}

}
