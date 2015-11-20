
package com.bitdubai.fermat_api;


public class PluginNotRecognizedException extends FermatException {
	
	
	private static final long serialVersionUID = 4150733208425009563L;

	public static final String DEFAULT_MESSAGE = "PLUGIN NOT RECOGNIZED";

	public PluginNotRecognizedException(final String message, final Exception cause, final String context, final String possibleReason) {
		super(message, cause, context, possibleReason);
	}

	public PluginNotRecognizedException(final String message, final Exception cause) {
		this(message, cause, "", "");
	}

	public PluginNotRecognizedException(final String context, final String possibleReason) {
		this(DEFAULT_MESSAGE, null, context, possibleReason);
	}

	public PluginNotRecognizedException(final String message) {
		this(message, null, null, null);
	}

	public PluginNotRecognizedException(final Exception exception) {
		this(exception.getMessage());
		setStackTrace(exception.getStackTrace());
	}

	public PluginNotRecognizedException() {
		this(DEFAULT_MESSAGE);
	}
}