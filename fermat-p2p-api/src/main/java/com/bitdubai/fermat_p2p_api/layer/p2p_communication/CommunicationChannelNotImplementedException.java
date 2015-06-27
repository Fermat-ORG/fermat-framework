package com.bitdubai.fermat_p2p_api.layer.p2p_communication;

/**
 * Created by ciencias on 2/23/15.
 */
public class CommunicationChannelNotImplementedException extends CommunicationException {
	
	private static final long serialVersionUID = 4150733208425549872L;

	public static final String DEFAULT_MESSAGE = "COMMUNICATION CHANNEL NOT IMPLEMENTED";

	public CommunicationChannelNotImplementedException(final String message, final Exception cause, final String context, final String possibleReason) {
		super(message, cause, context, possibleReason);
	}

}
