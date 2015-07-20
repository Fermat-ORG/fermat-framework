package com.bitdubai.fermat_pip_api.layer.pip_platform_service;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by ciencias on 23.01.15.
 */
public class CantStartSubsystemException extends FermatException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6805380384041432067L;
	public static final String DEFAULT_MESSAGE = "CAN'T START SUBSYSTEM";

	public CantStartSubsystemException(final String message, final Exception cause, final String context, final String possibleReason) {
		super(message, cause, context, possibleReason);
	}

	public CantStartSubsystemException(final String message, final Exception cause) {
		this(message, cause, "", "");
	}

	public CantStartSubsystemException(final String message) {
		this(message, null);
	}

	public CantStartSubsystemException(final Exception exception) {
		this(exception.getMessage());
		setStackTrace(exception.getStackTrace());
	}

	public CantStartSubsystemException() {
		this(DEFAULT_MESSAGE);
	}
}
