package com.bitdubai.fermat_api.layer.dmp_network_service;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by toshiba on 09/03/2015.
 */
public class CantGetResourcesException extends FermatException {
	
	
	private static final long serialVersionUID = 4150767203325009811L;

	private static final String DEFAULT_MESSAGE = "CANT GET RESOURCES: ";

	public CantGetResourcesException(final String message, final Exception cause, final String context, final String possibleReason){
		super(DEFAULT_MESSAGE + message, cause, context, possibleReason);
	}
	public CantGetResourcesException(final String message, final Exception cause){
		this(DEFAULT_MESSAGE + message, cause, "", "");
	}

	public CantGetResourcesException(final String message){
		this(message, null);
	}

	public CantGetResourcesException(){
		this("");
	}
}
