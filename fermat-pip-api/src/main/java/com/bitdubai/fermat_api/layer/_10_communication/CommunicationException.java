package com.bitdubai.fermat_api.layer._10_communication;

import java.io.IOException;

/**
 * Created by ciencias on 31.12.14.
 */
public class CommunicationException extends IOException {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 6617598263430521619L;

	public CommunicationException(){
		super();
	}

	public CommunicationException(final String message){
		super(message);
	}

}
