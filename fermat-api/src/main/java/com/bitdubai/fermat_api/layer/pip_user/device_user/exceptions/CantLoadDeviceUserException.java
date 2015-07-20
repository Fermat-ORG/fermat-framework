package com.bitdubai.fermat_api.layer.pip_user.device_user.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by ciencias on 23.01.15.
 */
public class CantLoadDeviceUserException extends FermatException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8314707695673071053L;

	public static final String DEFAULT_MESSAGE = "CAN'T LOAD DEVICE USER";

	public CantLoadDeviceUserException(final String message, final Exception cause, final String context, final String possibleReason) {
		super(message, cause, context, possibleReason);
	}

	public CantLoadDeviceUserException(final String message, final Exception cause) {
		this(message, cause, "", "");
	}

	public CantLoadDeviceUserException(final String message) {
		this(message, null);
	}

	public CantLoadDeviceUserException(final Exception exception) {
		this(exception.getMessage());
		setStackTrace(exception.getStackTrace());
	}

	public CantLoadDeviceUserException() {
		this(DEFAULT_MESSAGE);
	}
}
