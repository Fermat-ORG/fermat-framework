package com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPException;

public class VPNInitializationException extends FMPException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2518028395015785065L;

	public static final String DEFAULT_MESSAGE = "UNABLE TO INITIALIZE VPN CLIENT";

	public VPNInitializationException(final String message, final Exception cause, final String context, final String possibleReason) {
		super(message, cause, context, possibleReason);
	}

}
