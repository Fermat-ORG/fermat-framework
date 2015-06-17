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

	public CloudConnectionException(){
		super();
	}

	public CloudConnectionException(final String message){
		super(message);
	}

}
