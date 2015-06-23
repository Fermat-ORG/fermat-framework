package com.bitdubai.fermat_p2p_api.layer.p2p_communication;

/**
 * Created by ciencias on 2/12/15.
 */
public class CantConnectToRemoteServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 898045857824423781L;
	
	private static final String DEFAULT_MESSAGE = "CAN'T CONNECT TO REMOTE SERVICE";
	
	public CantConnectToRemoteServiceException() {
		this("");
	}
	
	public CantConnectToRemoteServiceException(final String message){
		super(DEFAULT_MESSAGE + ": " + message);
	}
	
}
