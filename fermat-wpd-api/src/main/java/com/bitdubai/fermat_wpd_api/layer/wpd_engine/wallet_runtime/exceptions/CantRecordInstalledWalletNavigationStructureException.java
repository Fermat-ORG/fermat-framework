package com.bitdubai.fermat_wpd_api.layer.wpd_engine.wallet_runtime.exceptions;

/**
 * Created by loui on 05/02/15.
 */
public class CantRecordInstalledWalletNavigationStructureException extends WalletRuntimeExceptions {
	/**
	 *
	 */
	private static final long serialVersionUID = 3116091246281644240L;

	public static final String DEFAULT_MESSAGE = "CAN'T RECORD NAVIGATION STRUCTURE REQUESTED";

	/**
	 * This is the constructor that every inherited FermatException must implement
	 *
	 * @param message        the short description of the why this exception happened, there is a public static constant called DEFAULT_MESSAGE that can be used here
	 * @param cause          the exception that triggered the throwing of the current exception, if there are no other exceptions to be declared here, the cause should be null
	 * @param context        a String that provides the values of the variables that could have affected the exception
	 * @param possibleReason an explicative reason of why we believe this exception was most likely thrown
	 */


	public CantRecordInstalledWalletNavigationStructureException(final String message, final Exception cause, final String context, final String possibleReason) {
		super(message, cause, context, possibleReason);
	}

}
