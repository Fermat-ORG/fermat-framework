package com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud.exceptions;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationException;


/**
 * Created by jorgeejgonzalez
 */
public class CloudCommunicationException extends CommunicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3147635757820692887L;

	public static final String DEFAULT_MESSAGE = "THE CLOUD COMMUNICATION CHANNEL HAS THROWN AN EXCEPTION";

	public CloudCommunicationException(final String message, final Exception cause, final String context, final String possibleReason) {
		super(message, cause, context, possibleReason);
	}

}
