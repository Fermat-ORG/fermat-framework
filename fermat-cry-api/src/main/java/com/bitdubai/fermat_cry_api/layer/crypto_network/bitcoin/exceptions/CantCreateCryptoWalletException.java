package com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.exceptions;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_cry_api.CryptoException;

/**
 * Created by ciencias on 26.01.15.
 */
public class CantCreateCryptoWalletException extends FermatException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1693468929863890293L;

	public CantCreateCryptoWalletException(String message, Exception cause, String context, String possibleReason) {
		super(message, cause, context, possibleReason);
	}
}
