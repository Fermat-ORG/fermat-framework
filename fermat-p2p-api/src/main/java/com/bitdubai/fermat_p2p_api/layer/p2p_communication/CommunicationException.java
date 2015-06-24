package com.bitdubai.fermat_p2p_api.layer.p2p_communication;

import com.bitdubai.fermat_api.FermatException;

import java.io.IOException;

/**
 * Created by ciencias on 31.12.14.
 */
public class CommunicationException extends FermatException {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 6617598263430521619L;

	private static final String DEFAULT_MESSAGE = "THE COMMUNICATION LAYER HAS TRIGGERED AN EXCEPTION: ";

	public CommunicationException(final String message, final Exception cause){
		super(message, cause);
	}

	public CommunicationException(final String message){
		this(message, null);
	}

	public CommunicationException(){
		this("");
	}

}
