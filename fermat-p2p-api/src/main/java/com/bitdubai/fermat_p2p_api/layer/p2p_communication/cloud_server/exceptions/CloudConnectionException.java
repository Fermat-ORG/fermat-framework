package com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud_server.exceptions;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationException;


/**
 * Created by jorgeejgonzalez
 */
public class CloudConnectionException extends CommunicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3147635757820692887L;
	private static final String DEFAULT_MESSAGE = "THE CLOUD COMMUNICATION CHANNEL HAS THROWN AN EXCEPTION: ";

	public CloudConnectionException(final String message, final Exception cause, final String context, final String possibleReason){
		super(DEFAULT_MESSAGE + message, cause, context, possibleReason);
	}
	public CloudConnectionException(final String message, final Exception cause){
		this(DEFAULT_MESSAGE + message, cause, "", "");
	}

	public CloudConnectionException(final String message){
		this(message, null);
	}

	public CloudConnectionException(){
		this("");
	}



}
