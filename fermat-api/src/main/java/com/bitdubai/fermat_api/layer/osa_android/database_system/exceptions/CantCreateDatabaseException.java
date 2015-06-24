package com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by toshiba on 23/03/2015.
 */
public class CantCreateDatabaseException extends DatabaseSystemException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4831581407768860533L;
	
	private static final String DEFAULT_MESSAGE = "CAN'T CREATE THE DATABASE: ";
	
	public CantCreateDatabaseException(final String message, final Exception cause){
		super(DEFAULT_MESSAGE + message, cause);
	}
	
	public CantCreateDatabaseException(final String message){
		this(message, null);
	}
	
	public CantCreateDatabaseException(){
		this("");
	}
}
