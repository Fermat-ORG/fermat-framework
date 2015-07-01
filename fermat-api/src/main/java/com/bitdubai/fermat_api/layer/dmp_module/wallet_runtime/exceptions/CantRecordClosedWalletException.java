package com.bitdubai.fermat_api.layer.dmp_module.wallet_runtime.exceptions;

/**
 * Created by loui on 05/02/15.
 */
public class CantRecordClosedWalletException extends WalletRuntimeExceptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6359965012408786034L;

	public static final String DEFAULT_MESSAGE = "CAN'T RECORD CLOSE WALLET";

	//private final String fileName;

	public CantRecordClosedWalletException(final String message, final Exception cause, final String context, final String possibleReason) {
		super(message, cause, context, possibleReason);
	}

	public CantRecordClosedWalletException(final String message, final Exception cause) {
		this(message, cause, "", "");
	}

	public CantRecordClosedWalletException(final String message) {
		this(message, null);
	}

	public CantRecordClosedWalletException(final Exception exception) {
		this(exception.getMessage());
		setStackTrace(exception.getStackTrace());
	}

	public CantRecordClosedWalletException() {
		this(DEFAULT_MESSAGE);
	}

}
