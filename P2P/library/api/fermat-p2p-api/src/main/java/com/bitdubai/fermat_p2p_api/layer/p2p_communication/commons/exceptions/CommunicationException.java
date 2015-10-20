package com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by ciencias on 31.12.14.
 */
	public class CommunicationException extends FermatException {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 6617598263430521619L;

	public static final String DEFAULT_MESSAGE = "THE COMMUNICATION LAYER HAS TRIGGERED AN EXCEPTION";

	public CommunicationException(final String message, final Exception cause, final String context, final String possibleReason) {
		super(message, cause, context, possibleReason);
	}

	public CommunicationException(final String message, final Exception cause) {
		this(message, cause, "", "");
	}

	public CommunicationException(final String message) {
		this(message, null);
	}

	public CommunicationException(final Exception exception) {
		this(exception.getMessage());
		setStackTrace(exception.getStackTrace());
	}

	public CommunicationException() {
		this(DEFAULT_MESSAGE);
	}

}
