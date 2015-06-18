package com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPException;

public class WrongFMPPacketEncryptionException extends FMPException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2845188917623252517L;
	private static final String DEFAULT_MESSAGE = "Wron FMPPacket Encryption";
	
	
	public WrongFMPPacketEncryptionException(){
		this("");
	}
	
	public WrongFMPPacketEncryptionException(final String message){
		super(DEFAULT_MESSAGE + ": " + message);
	}

}
