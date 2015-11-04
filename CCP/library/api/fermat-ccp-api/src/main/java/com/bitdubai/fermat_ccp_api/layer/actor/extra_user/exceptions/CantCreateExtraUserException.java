package com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Natalia on 31/03/2015.
 */
public class CantCreateExtraUserException extends FermatException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4449635941216365461L;

	public static final String DEFAULT_MESSAGE = "CAN'T CREATE EXTRAUSER REGISTRY";

	public CantCreateExtraUserException(final String message, final Exception cause, final String context, final String possibleReason) {
		super(message, cause, context, possibleReason);
	}

	public CantCreateExtraUserException(final String message, final Exception cause) {
		this(message, cause, "", "");
	}

	public CantCreateExtraUserException(final String message) {
		this(message, null);
	}

	public CantCreateExtraUserException(final Exception exception) {
		this(exception.getMessage());
		setStackTrace(exception.getStackTrace());
	}

	public CantCreateExtraUserException() {
		this(DEFAULT_MESSAGE);
	}
}
