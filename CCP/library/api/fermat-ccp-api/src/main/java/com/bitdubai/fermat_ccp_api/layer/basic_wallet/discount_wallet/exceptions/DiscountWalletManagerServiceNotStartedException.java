package com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by ciencias on 3/25/15.
 */
public class DiscountWalletManagerServiceNotStartedException extends FermatException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2128561424997846413L;

	private static final String DEFAULT_MESSAGE = "CAN'T CREATE THE REQUESTED";

	public DiscountWalletManagerServiceNotStartedException(final String message, final Exception cause, final String context, final String possibleReason){
		super(DEFAULT_MESSAGE + message, cause, context, possibleReason);
	}

	public DiscountWalletManagerServiceNotStartedException(final String message, final Exception cause){
		this(DEFAULT_MESSAGE + message, cause, "", "");
	}

	public DiscountWalletManagerServiceNotStartedException(final String message){
		this(message, null);
	}

	public DiscountWalletManagerServiceNotStartedException(){
		this("");
	}
}
