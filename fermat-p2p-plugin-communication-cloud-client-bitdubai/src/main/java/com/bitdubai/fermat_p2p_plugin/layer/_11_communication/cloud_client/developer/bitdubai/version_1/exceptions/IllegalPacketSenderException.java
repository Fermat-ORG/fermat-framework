package com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_client.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.layer._10_communication.fmp.FMPException;

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
