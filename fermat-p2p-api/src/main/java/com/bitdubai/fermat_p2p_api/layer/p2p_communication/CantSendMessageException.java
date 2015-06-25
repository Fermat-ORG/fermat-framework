package com.bitdubai.fermat_p2p_api.layer.p2p_communication;

/**
 * Created by ciencias on 2/23/15.
 */
public class CantSendMessageException extends CommunicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 164783413909499319L;
	
	public static final String DEFAULT_MESSAGE = "CAN'T SEND MESSAGE";

	public CantSendMessageException(final String message, final Exception cause, final String context, final String possibleReason) {
		super(message, cause, context, possibleReason);
	}

	public CantSendMessageException(final String message, final Exception cause) {
		this(message, cause, "", "");
	}

	public CantSendMessageException(final String message) {
		this(message, null);
	}

	public CantSendMessageException(final Exception exception) {
		this(exception.getMessage());
		setStackTrace(exception.getStackTrace());
	}

	public CantSendMessageException() {
		this(DEFAULT_MESSAGE);
	}
	
	
}
