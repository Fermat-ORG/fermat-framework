package com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud.exceptions.CloudCommunicationException;

public class NetworkServiceAlreadyRegisteredException extends
		CloudCommunicationException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8102719875874124016L;

	public static final String DEFAULT_MESSAGE = "NETWORK SERVICE ALREADY REGISTERED";

	public NetworkServiceAlreadyRegisteredException(final String message, final Exception cause, final String context, final String possibleReason) {
		super(message, cause, context, possibleReason);
	}

	public NetworkServiceAlreadyRegisteredException(final String message, final Exception cause) {
		this(message, cause, "", "");
	}

	public NetworkServiceAlreadyRegisteredException(final String message) {
		this(message, null);
	}

	public NetworkServiceAlreadyRegisteredException(final Exception exception) {
		this(exception.getMessage());
		setStackTrace(exception.getStackTrace());
	}

	public NetworkServiceAlreadyRegisteredException() {
		this(DEFAULT_MESSAGE);
	}
}
