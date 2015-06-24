package com.bitdubai.fermat_api;

public class FermatException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6066719615565752788L;
	
	private static final String DEFAULT_MESSAGE = "FERMAT HAS DETECTED AN EXCEPTION: ";

	private final FermatException cause;
	private final String context;
	private final String possibleReason;

	public FermatException(final String message, final Exception cause, final String context, final String possibleReason){
		super(DEFAULT_MESSAGE + message, cause);
		if(cause instanceof FermatException)
			this.cause = (FermatException) cause;
		else
			this.cause = null;
		this.context = context;
		this.possibleReason = possibleReason;
	}

	@Override
	public FermatException getCause(){
		return cause;
	}

	public String getContext(){
		return context;
	}

	public String getPossibleReason(){
		return possibleReason;
	}

	public String getFormattedTrace() {
		if(getStackTrace().length == 0)
			return "";

		StringBuffer buffer = new StringBuffer();
		for(StackTraceElement element : getStackTrace())
			if(element.getClassName().contains("com.bitdubai"))
				buffer.append("Class: " + element.getClassName() + " - Line: " + element.getLineNumber() + "\n");
		return buffer.toString();
	}

	@Override
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("Exception Type: " + getClass().toString() + "\n");
		buffer.append("Exception Message: " + getMessage() + "\n");
		buffer.append("Exception Context: " + getContext() + "\n");
		buffer.append("Exception Possible Cause: " + getPossibleReason() + "\n");
		buffer.append("Exception Stack Trace: \n" + getFormattedTrace());
		return buffer.toString();
	}
}
