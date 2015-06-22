package com.bitdubai.fermat_p2p_api.layer.p2p_communication;

/**
 * Created by ciencias on 2/23/15.
 */
public class CantSendMessageException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 164783413909499319L;
	
	private static final String DEFAULT_MESSAGE = "CAN'T SEND MESSAGE";
	
	public CantSendMessageException(){
		this("");
	}
	
	public CantSendMessageException(final String message){
		super(DEFAULT_MESSAGE + ": " + message);
	}
	
	
}
