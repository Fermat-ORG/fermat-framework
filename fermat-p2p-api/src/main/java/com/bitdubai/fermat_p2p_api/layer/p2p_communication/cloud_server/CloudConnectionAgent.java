package com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud_server;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud_server.exceptions.CloudConnectionException;

/*
 *	Created by jorgeejgonzalez
 */
public interface CloudConnectionAgent {
	public void start() throws CloudConnectionException;
	public void stop() throws CloudConnectionException;
	public boolean isRunning();
}
