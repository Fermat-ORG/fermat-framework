
package com.bitdubai.fermat_api.layer;


import com.bitdubai.fermat_api.FermatException;

public class CantStartLayerException extends FermatException {
	
	
	private static final long serialVersionUID = 4150733208425219871L;
	public static final String DEFAULT_MESSAGE = "CAN'T START THE LAYER DUE TO AN EXCEPTION: ";

	public CantStartLayerException(final String message, final Exception cause, final String context, final String possibleReason){
		super(DEFAULT_MESSAGE + message, cause, context, possibleReason);
	}

	public CantStartLayerException(final String message, final Exception cause){
		this(DEFAULT_MESSAGE + message, cause, "", "");
	}

	public CantStartLayerException(final String message){
		this(message, null);
	}

	public CantStartLayerException(){
		this("");
	}
}