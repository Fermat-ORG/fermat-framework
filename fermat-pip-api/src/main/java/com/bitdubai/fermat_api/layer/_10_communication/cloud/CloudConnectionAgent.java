package com.bitdubai.fermat_api.layer._10_communication.cloud;

/*
 *	Created by jorgeejgonzalez
 */
public interface CloudConnectionAgent {
	public void start() throws CloudConnectionException;
	public void stop() throws CloudConnectionException;
	public boolean isRunning();
}
