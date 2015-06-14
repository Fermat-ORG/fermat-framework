package com.bitdubai.fermat_api.layer._10_communication.fmp;

/*
 * Fermat Messaging Protocol
 * Created by jorgeejgonzalez 
 */
public abstract class FMPException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8596468775669289882L;

	public FMPException(){
		super();
	}

	public FMPException(final String message){
		super(message);
	}

}
