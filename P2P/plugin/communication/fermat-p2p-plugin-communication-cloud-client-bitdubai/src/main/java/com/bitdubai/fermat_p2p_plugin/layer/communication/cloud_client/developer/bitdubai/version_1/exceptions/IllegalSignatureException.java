package com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPException;

public class IllegalSignatureException extends FMPException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8291688297061754874L;

	public static final String DEFAULT_MESSAGE = "Illegal FMP Packet Signature";

	public IllegalSignatureException(final String message, final Exception cause, final String context, final String possibleReason) {
		super(message, cause, context, possibleReason);
	}

	public IllegalSignatureException(final String message, final Exception cause) {
		this(message, cause, "", "");
	}

	public IllegalSignatureException(final String message) {
		this(message, null);
	}

	public IllegalSignatureException(final Exception exception) {
		this(exception.getMessage());
		setStackTrace(exception.getStackTrace());
	}

	public IllegalSignatureException() {
		this(DEFAULT_MESSAGE);
	}

}
