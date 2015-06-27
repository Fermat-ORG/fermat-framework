package com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp;

import com.bitdubai.fermat_api.FermatException;

/*
 * Fermat Messaging Protocol
 * Created by jorgeejgonzalez 
 */
public abstract class FMPException extends FermatException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8596468775669289882L;

	private static final String DEFAULT_MESSAGE = "THE FERMAT MESSAGING PROTOCOL HAS THROWN AN EXCEPTION";

	public FMPException(final String message, final Exception cause, final String context, final String possibleReason) {
		super(message, cause, context, possibleReason);
	}

}
