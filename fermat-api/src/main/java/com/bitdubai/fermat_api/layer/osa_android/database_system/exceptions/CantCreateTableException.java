package com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions;

/**
 * Created by ciencias on 3/23/15.
 */
public class CantCreateTableException extends DatabaseSystemException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5641418231316021954L;
	
	private static final String DEFAULT_MESSAGE = "CAN'T CREATE THE TABLE: ";
	
	public CantCreateTableException(final String message, final Exception cause){
		super(DEFAULT_MESSAGE + message, cause);
	}
	
	public CantCreateTableException(final String message){
		this(message, null);
	}
	
	public CantCreateTableException(){
		this("");
	}
}
