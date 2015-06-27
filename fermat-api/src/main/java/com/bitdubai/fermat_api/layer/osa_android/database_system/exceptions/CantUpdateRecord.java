package com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions;

/**
 * Created by toshiba on 24/03/2015.
 */
public class CantUpdateRecord extends DatabaseSystemException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9099890596007453950L;

	public static final String DEFAULT_MESSAGE = "CAN'T UPDATE RECORD";

	public CantUpdateRecord(final String message, final Exception cause, final String context, final String possibleReason) {
		super(message, cause, context, possibleReason);
	}

	public CantUpdateRecord(final String message, final Exception cause) {
		this(message, cause, "", "");
	}

	public CantUpdateRecord(final String message) {
		this(message, null);
	}

	public CantUpdateRecord(final Exception exception) {
		this(exception.getMessage());
		setStackTrace(exception.getStackTrace());
	}

	public CantUpdateRecord() {
		this(DEFAULT_MESSAGE);
	}
}
