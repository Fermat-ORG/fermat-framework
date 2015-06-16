package com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.layer.p2p_communication.fmp.FMPException;

public class IllegalPacketSenderException extends FMPException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9052274264456380445L;

	public IllegalPacketSenderException(){
		super();
	}
	
	public IllegalPacketSenderException(final String message){
		super(message);
	}

}
