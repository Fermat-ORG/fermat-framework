package com.bitdubai.fermat_api.layer.ccp_actor.intra_user.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by loui on 22/02/15.
 */
public class CantCreateIntraUserException extends FermatException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7137746546837677675L;

	public static final String DEFAULT_MESSAGE = "CAN'T CREATE INTRAUSER";

	public CantCreateIntraUserException(final String message, final Exception cause, final String context, final String possibleReason) {
		super(message, cause, context, possibleReason);
	}

	public CantCreateIntraUserException(final String message, final Exception cause) {
		this(message, cause, "", "");
	}

	public CantCreateIntraUserException(final String message) {
		this(message, null);
	}

	public CantCreateIntraUserException(final Exception exception) {
		this(exception.getMessage());
		setStackTrace(exception.getStackTrace());
	}

	public CantCreateIntraUserException() {
		this(DEFAULT_MESSAGE);
	}
}
