package com.bitdubai.fermat_api;

public class FermatException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6066719615565752788L;
	
	private static final String DEFAULT_MESSAGE = "FERMAT HAS DETECTED AN EXCEPTION: ";
	
	public FermatException(final String message, final Exception cause){
		super(DEFAULT_MESSAGE + message, cause);
	}
	
	public FermatException(final String message){
		this(message, null);
	}
	
	public FermatException(){
		this("");
	}
}
