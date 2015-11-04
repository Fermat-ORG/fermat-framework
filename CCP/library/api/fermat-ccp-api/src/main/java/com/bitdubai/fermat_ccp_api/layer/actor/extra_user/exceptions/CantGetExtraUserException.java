package com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The Class <code>CantGetExtraUserException</code>
 * is thrown when we can get an extra user and cannot manage the exception.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 02/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantGetExtraUserException extends FermatException {

	public static final String DEFAULT_MESSAGE = "CAN'T GET EXTRA USER EXCEPTION";

	public CantGetExtraUserException(final String message, final Exception cause, final String context, final String possibleReason) {
		super(message, cause, context, possibleReason);
	}

	public CantGetExtraUserException(final String message, final Exception cause) {
		this(message, cause, "", "");
	}

	public CantGetExtraUserException(final String message) {
		this(message, null);
	}

	public CantGetExtraUserException(final Exception exception) {
		this(exception.getMessage());
		setStackTrace(exception.getStackTrace());
	}

	public CantGetExtraUserException() {
		this(DEFAULT_MESSAGE);
	}

}
