package com.bitdubai.fermat_pip_api.layer.pip_user.device_user.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by ciencias on 23.01.15.
 */
public class CantPersistDeviceUserException extends FermatException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -889314543845194215L;

	public static final String DEFAULT_MESSAGE = "CAN'T PERSIS DEVICE USER";

	public CantPersistDeviceUserException(final String message, final Exception cause, final String context, final String possibleReason) {
		super(message, cause, context, possibleReason);
	}

	public CantPersistDeviceUserException(final String message, final Exception cause) {
		this(message, cause, "", "");
	}

	public CantPersistDeviceUserException(final String message) {
		this(message, null);
	}

	public CantPersistDeviceUserException(final Exception exception) {
		this(exception.getMessage());
		setStackTrace(exception.getStackTrace());
	}

	public CantPersistDeviceUserException() {
		this(DEFAULT_MESSAGE);
	}
}
