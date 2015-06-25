package com.bitdubai.fermat_api;

public class FermatException extends Exception {

	public static final String CONTEXT_CONTENT_SEPARATOR = "&&";

	/**
	 * 
	 */
	private static final long serialVersionUID = 6066719615565752788L;
	
	private static final String DEFAULT_MESSAGE = "FERMAT HAS DETECTED AN EXCEPTION: ";

	private final String exceptionName;
	private final FermatException cause;
	private final String context;
	private final String possibleReason;
	private int depth;

	public FermatException(final String message, final Exception cause, final String context, final String possibleReason){
		super(message, cause);
		if(cause instanceof FermatException)
			this.cause = (FermatException) cause;
		else
			this.cause = null;
		this.exceptionName = getClass().toString();
		this.context = context;
		this.possibleReason = possibleReason;
	}

	private FermatException(final String exceptionName, final String message, final Exception cause, final String context, final String possibleReason){
		super(message, cause);
		if(cause instanceof FermatException)
			this.cause = (FermatException) cause;
		else
			this.cause = null;
		this.exceptionName = exceptionName;
		this.context = context;
		this.possibleReason = possibleReason;
	}

	public static FermatException wrapException(final Exception exception){
		FermatException fermatException = new FermatException(exception.getClass().toString(), exception.getMessage(), null, "", "");
		fermatException.setStackTrace(exception.getStackTrace());
		return fermatException;
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

	public void setDepth(final int depth){
		this.depth = depth;
	}

	public int getDepth(){
		return depth;
	}

	public String getFormattedContext() {
		StringBuffer buffer = new StringBuffer("");
		for(String contextPart : context.split(CONTEXT_CONTENT_SEPARATOR))
			if(!contextPart.isEmpty())
				buffer.append(contextPart + "\n");
		return buffer.toString();
	}

	public String getFormattedTrace() {
		if(getStackTrace().length == 0)
			return "";

		StringBuffer buffer = new StringBuffer();
		for(StackTraceElement element : getStackTrace())
			//if(element.getClassName().contains("com.bitdubai"))
			buffer.append("Class: " + element.getClassName() + " - Line: " + element.getLineNumber() + "\n");
		return buffer.toString();
	}

	@Override
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("Exception Type: " + exceptionName + "\n");
		buffer.append("Exception Message: " + getMessage() + "\n");
		buffer.append("Exception Possible Cause: ");
		buffer.append(getPossibleReason().isEmpty() ? "N/A \n" : getPossibleReason() + "\n");
		buffer.append("Exception Context: " );
		buffer.append(!getFormattedContext().isEmpty() ? "\n---------------------------------------------------------------------------------\n" : "");
		buffer.append(getFormattedContext().isEmpty() ? "N/A \n" : getFormattedContext());
		buffer.append(!getFormattedContext().isEmpty() ? "---------------------------------------------------------------------------------\n" : "");
		buffer.append("Exception Stack Trace: \n");
		buffer.append("---------------------------------------------------------------------------------\n");
		buffer.append(getFormattedTrace());
		buffer.append("---------------------------------------------------------------------------------\n");
		return buffer.toString();
	}
}
