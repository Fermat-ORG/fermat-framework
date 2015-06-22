package com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPException;

public class VPNInitializationException extends FMPException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2518028395015785065L;
	private static String DEFAULT_MESSAGE = "Unable to Initialize VPN Client";
	
	public VPNInitializationException(){
		this("");
	}
	
	public VPNInitializationException(final String message){
		super(DEFAULT_MESSAGE + ": " + message);
	}

}
