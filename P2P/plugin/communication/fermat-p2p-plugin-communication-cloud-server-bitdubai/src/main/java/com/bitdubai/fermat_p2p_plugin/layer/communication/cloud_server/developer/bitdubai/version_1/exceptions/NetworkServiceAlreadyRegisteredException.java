package com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.layer.p2p_communication.cloud.CloudConnectionException;

public class NetworkServiceAlreadyRegisteredException extends
		CloudConnectionException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8102719875874124016L;
	private static final String DEFAULT_MESSAGE = "NETWORK SERVICE ALREADY REGISTERED";
	
	public NetworkServiceAlreadyRegisteredException() {
		super(DEFAULT_MESSAGE);
	}
	
	public NetworkServiceAlreadyRegisteredException(final String message) {
		super(DEFAULT_MESSAGE+ ": "+ message);
	}

}
