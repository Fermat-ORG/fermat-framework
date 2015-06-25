package com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPException;

public class WrongFMPPacketEncryptionException extends FMPException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2845188917623252517L;

	public static final String DEFAULT_MESSAGE = "WRONG FMP PACKET ENCRYPTION";

	public WrongFMPPacketEncryptionException(final String message, final Exception cause, final String context, final String possibleReason) {
		super(message, cause, context, possibleReason);
	}

	public WrongFMPPacketEncryptionException(final String message, final Exception cause) {
		this(message, cause, "", "");
	}

	public WrongFMPPacketEncryptionException(final String message) {
		this(message, null);
	}

	public WrongFMPPacketEncryptionException(final Exception exception) {
		this(exception.getMessage());
		setStackTrace(exception.getStackTrace());
	}

	public WrongFMPPacketEncryptionException() {
		this(DEFAULT_MESSAGE);
	}

}
