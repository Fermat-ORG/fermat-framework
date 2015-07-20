package com.bitdubai.fermat_api.layer.dmp_actor.extra_user.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Natalia on 31/03/2015.
 */
public class CantGetExtraUserRegistry extends FermatException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7275101695435720889L;

	public static final String DEFAULT_MESSAGE = "CAN'T GET EXTRAUSER REGISTRY";

	public CantGetExtraUserRegistry(final String message, final Exception cause, final String context, final String possibleReason) {
		super(message, cause, context, possibleReason);
	}

	public CantGetExtraUserRegistry(final String message, final Exception cause) {
		this(message, cause, "", "");
	}

	public CantGetExtraUserRegistry(final String message) {
		this(message, null);
	}

	public CantGetExtraUserRegistry(final Exception exception) {
		this(exception.getMessage());
		setStackTrace(exception.getStackTrace());
	}

	public CantGetExtraUserRegistry() {
		this(DEFAULT_MESSAGE);
	}
}
