package com.bitdubai.fermat_api.layer.p2p_communication.cloud;

/*
 *	Created by jorgeejgonzalez
 */
public interface CloudConnectionAgent {
	public void start() throws CloudConnectionException;
	public void stop() throws CloudConnectionException;
	public boolean isRunning();
}
