package com.bitdubai.fermat_api.layer.dmp_actor.extra_user.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by toshiba on 29/04/2015.
 */
public class CantInitializeExtraUserRegistryException extends FermatException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4810983392545111966L;

	public static final String DEFAULT_MESSAGE = "CAN'T INITIALIZE EXTRAUSER REGISTRY";

	public CantInitializeExtraUserRegistryException(final String message, final Exception cause, final String context, final String possibleReason) {
		super(message, cause, context, possibleReason);
	}

	public CantInitializeExtraUserRegistryException(final String message, final Exception cause) {
		this(message, cause, "", "");
	}

	public CantInitializeExtraUserRegistryException(final String message) {
		this(message, null);
	}

	public CantInitializeExtraUserRegistryException(final Exception exception) {
		this(exception.getMessage());
		setStackTrace(exception.getStackTrace());
	}

	public CantInitializeExtraUserRegistryException() {
		this(DEFAULT_MESSAGE);
	}
}
