package com.bitdubai.fermat_p2p_api.layer.p2p_communication;

/**
 * Created by ciencias on 2/23/15.
 */
public class CommunicationChannelNotImplemented extends Exception {
	
	private static final long serialVersionUID = 4150733208425549872L;

	private static final String DEFAULT_MESSAGE = "COMMUNICATION CHANNEL NOT IMPLEMENTED";

	public CommunicationChannelNotImplemented(final String message){
		super(DEFAULT_MESSAGE + ": " + message);
	}

	public CommunicationChannelNotImplemented(){
		this("");
	}


}
