
package com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions;

/**
 * Created by mati
 */
public class DatabaseNotOpenException extends DatabaseSystemException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6278437435368629668L;

	public static final String DEFAULT_MESSAGE = "THE DATABASE IS NOT OPEN";

	public DatabaseNotOpenException(final String message, final Exception cause, final String context, final String possibleReason) {
		super(message, cause, context, possibleReason);
	}

	public DatabaseNotOpenException(final String message, final Exception cause) {
		this(message, cause, "", "");
	}

	public DatabaseNotOpenException(final String message) {
		this(message, null);
	}

	public DatabaseNotOpenException(final Exception exception) {
		this(exception.getMessage());
		setStackTrace(exception.getStackTrace());
	}

	public DatabaseNotOpenException() {
		this(DEFAULT_MESSAGE);
	}
}