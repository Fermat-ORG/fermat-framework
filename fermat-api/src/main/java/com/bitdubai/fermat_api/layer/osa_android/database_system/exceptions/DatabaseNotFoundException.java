package com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions;

/**
 * Created by ciencias on 01.02.15.
 */
public class DatabaseNotFoundException extends DatabaseSystemException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5578619047961290769L;

	private static final String DEFAULT_MESSAGE = "THE DATABASE HAS NOT BEEN FOUND: ";

	public DatabaseNotFoundException(final String message, final Exception cause){
		super(DEFAULT_MESSAGE + message, cause);
	}

	public DatabaseNotFoundException(final String message){
		this(message, null);
	}

	public DatabaseNotFoundException(){
		this("");
	}
}
