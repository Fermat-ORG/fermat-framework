package com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by loui on 16/02/15.
 */
public class CantCreateWalletException extends FermatException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7332906635871050415L;

	private static final String DEFAULT_MESSAGE = "CAN'T CREATE THE REQUESTED";

	public CantCreateWalletException(final String message, final Exception cause, final String context, final String possibleReason){
		super(DEFAULT_MESSAGE + message, cause, context, possibleReason);
	}

	public CantCreateWalletException(final String message, final Exception cause){
		this(DEFAULT_MESSAGE + message, cause, "", "");
	}

	public CantCreateWalletException(final String message){
		this(message, null);
	}

	public CantCreateWalletException(){
		this("");
	}
}
