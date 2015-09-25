package com.bitdubai.fermat_api.layer.ccp_middleware;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by loui on 16/02/15.
 */
public class MiddlewareNotStartedException extends FermatException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2406980312218459852L;
	private static final String DEFAULT_MESSAGE = "MIDDLEWARE NOT STARTED";

	public MiddlewareNotStartedException(final String message, final Exception cause, final String context, final String possibleReason){
		super(DEFAULT_MESSAGE + message, cause, context, possibleReason);
	}

	public MiddlewareNotStartedException(final String message, final Exception cause){
		this(DEFAULT_MESSAGE + message, cause, "", "");
	}

	public MiddlewareNotStartedException(final String message){
		this(message, null);
	}

	public MiddlewareNotStartedException(){
		this("");
	}
}
