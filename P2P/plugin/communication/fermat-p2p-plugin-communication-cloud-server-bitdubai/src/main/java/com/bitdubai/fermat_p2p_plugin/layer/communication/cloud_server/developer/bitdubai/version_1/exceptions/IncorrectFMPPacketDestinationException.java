package com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPException;

public class IncorrectFMPPacketDestinationException extends FMPException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1518260429938607463L;
	
	public static final String DEFAULT_MESSAGE = "INCORRECT FMPPACKET DESTINATION";

	public IncorrectFMPPacketDestinationException(final String message, final Exception cause, final String context, final String possibleReason) {
		super(message, cause, context, possibleReason);
	}

	public IncorrectFMPPacketDestinationException(final String message, final Exception cause) {
		this(message, cause, "", "");
	}

	public IncorrectFMPPacketDestinationException(final String message) {
		this(message, null);
	}

	public IncorrectFMPPacketDestinationException(final Exception exception) {
		this(exception.getMessage());
		setStackTrace(exception.getStackTrace());
	}

	public IncorrectFMPPacketDestinationException() {
		this(DEFAULT_MESSAGE);
	}

}
