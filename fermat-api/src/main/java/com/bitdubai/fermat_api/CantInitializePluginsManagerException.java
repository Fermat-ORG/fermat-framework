
package com.bitdubai.fermat_api;


public class CantInitializePluginsManagerException extends FermatException {

	private static final long serialVersionUID = -2909149892880578689L;

	public static final String DEFAULT_MESSAGE = "CAN'T INITIALIZE PLUGINS MANAGER";

	public CantInitializePluginsManagerException(final String message, final Exception cause, final String context, final String possibleReason) {
		super(message, cause, context, possibleReason);
	}

	public CantInitializePluginsManagerException(final String message, final Exception cause) {
		this(message, cause, "", "");
	}

	public CantInitializePluginsManagerException(final Exception cause, final String context, final String possibleReason) {
		this(DEFAULT_MESSAGE, cause, context, possibleReason);
	}

	public CantInitializePluginsManagerException(final String message) {
		this(message, null);
	}

	public CantInitializePluginsManagerException(final Exception exception) {
		this(exception.getMessage());
		setStackTrace(exception.getStackTrace());
	}

	public CantInitializePluginsManagerException() {
		this(DEFAULT_MESSAGE);
	}
}