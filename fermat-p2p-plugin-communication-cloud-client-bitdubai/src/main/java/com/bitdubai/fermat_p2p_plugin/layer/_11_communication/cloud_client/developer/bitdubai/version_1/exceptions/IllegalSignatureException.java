package com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_client.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.layer._10_communication.fmp.FMPException;

public class IllegalSignatureException extends FMPException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8291688297061754874L;
	
	public IllegalSignatureException(){
		super();
	}
	
	public IllegalSignatureException(final String message){
		super(message);
	}

}
