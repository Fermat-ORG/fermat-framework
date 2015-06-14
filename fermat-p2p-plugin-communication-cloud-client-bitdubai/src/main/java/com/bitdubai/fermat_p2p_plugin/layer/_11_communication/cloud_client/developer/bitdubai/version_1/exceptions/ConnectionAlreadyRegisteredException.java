package com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_client.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.layer._10_communication.cloud.CloudConnectionException;

public class ConnectionAlreadyRegisteredException extends
		CloudConnectionException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7916961691657131265L;
	
	public static final String DEFAULT_MESSAGE = "CONNECTION IS ALREADY REQUESTED";
	
	public ConnectionAlreadyRegisteredException(){
		super(DEFAULT_MESSAGE);
	}
	
	public ConnectionAlreadyRegisteredException(final String message){
		super(DEFAULT_MESSAGE + ": " + message);
	}

}
