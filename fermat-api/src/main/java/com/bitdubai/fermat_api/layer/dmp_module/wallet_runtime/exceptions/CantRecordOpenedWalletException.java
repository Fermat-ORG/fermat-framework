package com.bitdubai.fermat_api.layer.dmp_module.wallet_runtime.exceptions;

/**
 * Created by loui on 05/02/15.
 */
public class CantRecordOpenedWalletException extends WalletRuntimeExceptions {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3116091246281644240L;

	public static final String DEFAULT_MESSAGE = "CAN'T RECORD OPENED WALLET";

	public CantRecordOpenedWalletException(final String message, final Exception cause, final String context, final String possibleReason) {
		super(message, cause, context, possibleReason);
	}

	public CantRecordOpenedWalletException(final String message, final Exception cause) {
		this(message, cause, "", "");
	}

	public CantRecordOpenedWalletException(final String message) {
		this(message, null);
	}

	public CantRecordOpenedWalletException(final Exception exception) {
		this(exception.getMessage());
		setStackTrace(exception.getStackTrace());
	}

	public CantRecordOpenedWalletException() {
		this(DEFAULT_MESSAGE);
	}
}
