package com.bitdubai.fermat_api.layer._10_communication;

import java.net.InetSocketAddress;

/**
 * Created by jorgeejgonzalez
 */
public interface CommunicationChannelAddress {

	public String getHost();
	public Integer getPort();
	public InetSocketAddress getSocketAddress();
	
}
