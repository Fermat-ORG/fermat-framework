package com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud.exceptions.CloudCommunicationException;

public class ConnectionAlreadyRegisteredException extends
		CloudCommunicationException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7916961691657131265L;
	
	public static final String DEFAULT_MESSAGE = "CONNECTION IS ALREADY REGISTERED";

	public ConnectionAlreadyRegisteredException(final String message, final Exception cause, final String context, final String possibleReason) {
		super(message, cause, context, possibleReason);
	}

}
