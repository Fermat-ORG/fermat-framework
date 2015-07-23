package com.bitdubai.fermat_api.layer.dmp_actor.extra_user.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Natalia on 31/03/2015.
 */
public class CantCreateExtraUserRegistry extends FermatException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4449635941216365461L;

	public static final String DEFAULT_MESSAGE = "CAN'T CREATE EXTRAUSER REGISTRY";

	public CantCreateExtraUserRegistry(final String message, final Exception cause, final String context, final String possibleReason) {
		super(message, cause, context, possibleReason);
	}

	public CantCreateExtraUserRegistry(final String message, final Exception cause) {
		this(message, cause, "", "");
	}

	public CantCreateExtraUserRegistry(final String message) {
		this(message, null);
	}

	public CantCreateExtraUserRegistry(final Exception exception) {
		this(exception.getMessage());
		setStackTrace(exception.getStackTrace());
	}

	public CantCreateExtraUserRegistry() {
		this(DEFAULT_MESSAGE);
	}
}
