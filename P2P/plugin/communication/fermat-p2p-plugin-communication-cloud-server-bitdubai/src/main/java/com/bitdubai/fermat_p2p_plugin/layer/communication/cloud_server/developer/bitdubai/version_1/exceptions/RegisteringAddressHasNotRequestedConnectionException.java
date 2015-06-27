package com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPException;

public class RegisteringAddressHasNotRequestedConnectionException extends
		FMPException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5575796475237671382L;

	public static final String DEFAULT_MESSAGE = "CLIENT ADDRESS HAS NOT REQUESTED A CONNECTION TO THIS SERVICE";

	public RegisteringAddressHasNotRequestedConnectionException(final String message, final Exception cause, final String context, final String possibleReason) {
		super(message, cause, context, possibleReason);
	}

}
