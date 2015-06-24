package com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions;

/**
 * Created by toshiba on 24/03/2015.
 */
public class CantInsertRecordException extends DatabaseSystemException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -327709783649435604L;

	private static final String DEFAULT_MESSAGE = "CAN'T INSERT THE RECORD: ";

	public CantInsertRecordException(final String message, final Exception cause){
		super(DEFAULT_MESSAGE + message, cause);
	}

	public CantInsertRecordException(final String message){
		this(message, null);
	}

	public CantInsertRecordException(){
		this("");
	}
}
