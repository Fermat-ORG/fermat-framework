package com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud.exceptions.CloudCommunicationException;

/*
 *	Created by jorgeejgonzalez
 */
public interface CloudConnectionAgent {
	public void start() throws CloudCommunicationException;
	public void stop() throws CloudCommunicationException;
	public boolean isRunning();
}
