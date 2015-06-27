package com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions;

/**
 * Created by ciencias on 3/23/15.
 */
public class InvalidOwnerId extends DatabaseSystemException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8223773873444754668L;

	public static final String DEFAULT_MESSAGE = "INVALID OWNER ID";

	public InvalidOwnerId(final String message, final Exception cause, final String context, final String possibleReason) {
		super(message, cause, context, possibleReason);
	}

	public InvalidOwnerId(final String message, final Exception cause) {
		this(message, cause, "", "");
	}

	public InvalidOwnerId(final String message) {
		this(message, null);
	}

	public InvalidOwnerId(final Exception exception) {
		this(exception.getMessage());
		setStackTrace(exception.getStackTrace());
	}

	public InvalidOwnerId() {
		this(DEFAULT_MESSAGE);
	}
}
