package com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions;

/**
 * Created by ciencias on 3/24/15.
 */
public class DatabaseTransactionFailedException extends DatabaseSystemException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1431539427032863967L;

	private static final String DEFAULT_MESSAGE = "THE TRANSACTION HAS FAILED: ";

	public DatabaseTransactionFailedException(final String message, final Exception cause){
		super(DEFAULT_MESSAGE + message, cause);
	}

	public DatabaseTransactionFailedException(final String message){
		this(message, null);
	}

	public DatabaseTransactionFailedException(){
		this("");
	}
}
