package com.bitdubai.fermat_p2p_api.layer.p2p_communication;

/**
 * Created by ciencias on 20.01.15.
 */
public class CantStartSubsystemException extends CommunicationException {
	
	
	private static final long serialVersionUID = 4150733292125009872L;

	public static final String DEFAULT_MESSAGE = "THE CLOUD COMMUNICATION CHANNEL HAS THROWN AN EXCEPTION";

	public CantStartSubsystemException(final String message, final Exception cause, final String context, final String possibleReason){
		super(message, cause, context, possibleReason);
	}

}
