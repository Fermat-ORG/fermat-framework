package com.bitdubai.fermat_api.layer._10_communication.cloud;

import com.bitdubai.fermat_api.layer._10_communication.CommunicationException;


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
