
package com.bitdubai.fermat_api;


public class CantReportCriticalStartingProblemException extends FermatException {

	
	private static final long serialVersionUID = 4150733208425009872L;

	public static final String DEFAULT_MESSAGE = "CAN'T REPORT CRITICAL STARTING PROBLEM";

	public CantReportCriticalStartingProblemException(final String message, final Exception cause, final String context, final String possibleReason) {
		super(message, cause, context, possibleReason);
	}

	public CantReportCriticalStartingProblemException(final String message, final Exception cause) {
		this(message, cause, "", "");
	}

	public CantReportCriticalStartingProblemException(final String message) {
		this(message, null);
	}

	public CantReportCriticalStartingProblemException(final Exception exception) {
		this(exception.getMessage());
		setStackTrace(exception.getStackTrace());
	}

	public CantReportCriticalStartingProblemException() {
		this(DEFAULT_MESSAGE);
	}
}