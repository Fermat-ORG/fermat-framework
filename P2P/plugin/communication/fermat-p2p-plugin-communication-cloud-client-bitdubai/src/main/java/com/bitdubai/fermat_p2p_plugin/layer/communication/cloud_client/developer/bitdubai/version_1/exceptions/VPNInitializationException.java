package com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPException;

public class VPNInitializationException extends FMPException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2518028395015785065L;

	public static final String DEFAULT_MESSAGE = "Unable To Initialize VPN Client";

	public VPNInitializationException(final String message, final Exception cause, final String context, final String possibleReason) {
		super(message, cause, context, possibleReason);
	}

	public VPNInitializationException(final String message, final Exception cause) {
		this(message, cause, "", "");
	}

	public VPNInitializationException(final String message) {
		this(message, null);
	}

	public VPNInitializationException(final Exception exception) {
		this(exception.getMessage());
		setStackTrace(exception.getStackTrace());
	}

	public VPNInitializationException() {
		this(DEFAULT_MESSAGE);
	}

}
