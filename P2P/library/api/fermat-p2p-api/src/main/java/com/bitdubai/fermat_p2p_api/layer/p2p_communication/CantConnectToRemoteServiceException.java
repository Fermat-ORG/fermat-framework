package com.bitdubai.fermat_p2p_api.layer.p2p_communication;

/**
 * Created by ciencias on 2/12/15.
 */
public class CantConnectToRemoteServiceException extends CommunicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 898045857824423781L;
	
	public static final String DEFAULT_MESSAGE = "CAN'T CONNECT TO REMOTE SERVICE";

	public CantConnectToRemoteServiceException(final String message, final Exception cause, final String context, final String possibleReason) {
		super(message, cause, context, possibleReason);
	}
	
}
