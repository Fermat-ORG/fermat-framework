package com.bitdubai.fermat_pip_api.layer.pip_user;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantStartSubsystemException extends FermatException {

	private static final String DEFAULT_MESSAGE = "CAN'T START THE USER SUBSYSTEM DUE TO AN EXCEPTION: ";

	public CantStartSubsystemException(final String message, final Exception cause, final String context, final String possibleReason){
		super(DEFAULT_MESSAGE + message, cause, context, possibleReason);
	}

	public CantStartSubsystemException(final String message, final Exception cause){
		this(DEFAULT_MESSAGE + message, cause, "", "");
	}

	public CantStartSubsystemException(final String message){
		this(message, null);
	}

	public CantStartSubsystemException(){
		this("");
	}
}
