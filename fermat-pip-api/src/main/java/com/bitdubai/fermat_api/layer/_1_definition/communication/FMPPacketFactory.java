package com.bitdubai.fermat_api.layer._1_definition.communication;

import com.bitdubai.fermat_api.layer._10_communication.fmp.FMPPacket;
import com.bitdubai.fermat_api.layer._10_communication.fmp.FMPPacket.FMPPacketType;
import com.bitdubai.fermat_api.layer._10_communication.fmp.FMPException;
import com.bitdubai.fermat_api.layer._1_definition.communication.cloud.CloudFMPPacket;

public class FMPPacketFactory {

	public static FMPPacket constructCloudPacket(String packetData) throws FMPException {
		return new CloudFMPPacket(packetData);
	}

	public static FMPPacket constructCloudPacket(final String sender, final String destination, final FMPPacketType type, 
						final String message, final String signature) throws FMPException{
		StringBuilder builder = new StringBuilder();
                builder.append(sender);
                builder.append(FMPPacket.PACKET_SEPARATOR);
                builder.append(destination);
                builder.append(FMPPacket.PACKET_SEPARATOR);
                builder.append(type);
                builder.append(FMPPacket.PACKET_SEPARATOR);
                builder.append(message);
                builder.append(FMPPacket.PACKET_SEPARATOR);
                builder.append(signature);
		return constructCloudPacket(builder.toString());
	}

}