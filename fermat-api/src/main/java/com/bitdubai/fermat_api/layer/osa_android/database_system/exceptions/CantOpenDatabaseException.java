package com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions;

/**
 * Created by toshiba on 24/03/2015.
 */
public class CantOpenDatabaseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4736497835462025622L;
	
	private static final String DEFAULT_MESSAGE = "CAN'T CREATE THE DATABASE: ";
	
	public CantOpenDatabaseException(final String message, final Exception cause){
		super(DEFAULT_MESSAGE + message, cause);
	}
	
	public CantOpenDatabaseException(final String message){
		this(message, null);
	}
	
	public CantOpenDatabaseException(){
		this("");
	}
}
