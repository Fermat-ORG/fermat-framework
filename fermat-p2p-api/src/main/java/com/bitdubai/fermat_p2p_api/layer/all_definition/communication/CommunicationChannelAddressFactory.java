package com.bitdubai.fermat_p2p_api.layer.all_definition.communication;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannelAddress;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.cloud.CloudConnectionAddress;

public class CommunicationChannelAddressFactory {

	public static CommunicationChannelAddress constructCloudAddress(final String host, final Integer port) throws IllegalArgumentException{
		return new CloudConnectionAddress(host, port);
	}
	
}
