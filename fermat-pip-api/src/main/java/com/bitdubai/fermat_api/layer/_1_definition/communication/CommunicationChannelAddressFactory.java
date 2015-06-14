package com.bitdubai.fermat_api.layer._1_definition.communication;

import com.bitdubai.fermat_api.layer._10_communication.CommunicationChannelAddress;
import com.bitdubai.fermat_api.layer._1_definition.communication.cloud.CloudConnectionAddress;

public class CommunicationChannelAddressFactory {

	public static CommunicationChannelAddress constructCloudAddress(final String host, final Integer port) throws IllegalArgumentException{
		return new CloudConnectionAddress(host, port);
	}
	
}
