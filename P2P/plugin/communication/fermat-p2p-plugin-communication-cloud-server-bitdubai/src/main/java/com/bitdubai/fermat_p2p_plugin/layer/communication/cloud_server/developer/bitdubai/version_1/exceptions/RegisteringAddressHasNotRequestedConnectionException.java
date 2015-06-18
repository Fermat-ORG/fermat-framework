package com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPException;

public class RegisteringAddressHasNotRequestedConnectionException extends
		FMPException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5575796475237671382L;
	
	private static final String DEFAULT_MESSAGE = "Client Address Has Not Requested a Connection To This Service";
	
	public RegisteringAddressHasNotRequestedConnectionException(){
		super(DEFAULT_MESSAGE);
	}
	
	public RegisteringAddressHasNotRequestedConnectionException(final String message){
		super(DEFAULT_MESSAGE + ": " + message);
	}

}
