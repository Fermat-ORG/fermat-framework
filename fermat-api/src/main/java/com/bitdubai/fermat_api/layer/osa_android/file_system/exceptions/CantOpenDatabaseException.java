package com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions;

import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseSystemException;


//TODO LUIS: Que hace este clase aqui? me la consegui haciendo el refactoring de DatabaseSystemException. Jorge
/**
 * Created by ciencias on 01.02.15.
 */
public class CantOpenDatabaseException extends DatabaseSystemException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -851526264746247669L;

	public static final String DEFAULT_MESSAGE = "THIS IS THE WRONG EXCEPTION - CAN'T OPEN DATABASE EXCEPTION";

	public CantOpenDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
		super(message, cause, context, possibleReason);
	}

	public CantOpenDatabaseException(final String message, final Exception cause) {
		this(message, cause, "", "");
	}

	public CantOpenDatabaseException(final String message) {
		this(message, null);
	}

	public CantOpenDatabaseException(final Exception exception) {
		this(exception.getMessage());
		setStackTrace(exception.getStackTrace());
	}

	public CantOpenDatabaseException() {
		this(DEFAULT_MESSAGE);
	}
}
