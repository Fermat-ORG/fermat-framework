package com.bitdubai.fermat_p2p_api.layer.p2p_communication;

import java.net.InetSocketAddress;

/**
 * Created by jorgeejgonzalez
 */
public interface CommunicationChannelAddress {

	public String getHost();
	public Integer getPort();
	public InetSocketAddress getSocketAddress();
	
}
