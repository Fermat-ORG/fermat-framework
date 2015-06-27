package com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp;

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

}
