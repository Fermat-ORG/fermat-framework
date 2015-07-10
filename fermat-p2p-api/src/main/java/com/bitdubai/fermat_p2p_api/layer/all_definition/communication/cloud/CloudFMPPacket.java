package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.cloud;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.MalformedFMPPacketException;

import java.util.ArrayList;

public class CloudFMPPacket implements FMPPacket {
	
	private static final int HASH_PRIME_NUMBER_PRODUCT = 4517;
	private static final int HASH_PRIME_NUMBER_ADD = 7523;
	
	private final String sender;
	private final String destination;
	private final FMPPacketType type;
	private final String message;
	private final String signature;
	
	public CloudFMPPacket(final String packetData) throws FMPException {

		String[] validatedData =  validatePacketDataString(packetData);
		this.sender = validatedData[0];
		this.destination = validatedData[1];
		this.type = FMPPacketType.valueOf(validatedData[2]);
		this.message = validatedData[3];
		this.signature = validatedData[4];
	}
	
	public CloudFMPPacket(final String sender, final String destination, final FMPPacketType type, final String message) throws FMPException{
		this.sender = null;
		this.destination = null;
		this.type = null;
		this.message = null;
		this.signature = null;
	}

	/*
	 *	Implementation methods for the FMP interface
	 */
	@Override
	public String getSender() {
		return sender;
	}

	public String getDestination() {
		return destination;
	}	

	@Override
	public FMPPacketType getType() {
		return type;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public String getSignature() {
		return signature;
	}



	@Override
	public String convertToString(){
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
                return builder.toString();
	}

	@Override
	public String toString(){
		return convertToString();
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof FMPPacket){
			FMPPacket compare = (FMPPacket) o;
			return compare.getSender().equals(sender) 
					&& compare.getDestination().equals(destination)
					&& compare.getType().equals(type)
					&& compare.getMessage().equals(message)
					&& compare.getSignature().equals(signature);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		int c = 0;
		c += sender.hashCode();
		c+= destination.hashCode();
		c+= type.hashCode();
		c+= message.hashCode();
		c+= signature.hashCode();
		return 	HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
	}

	/*
	 *	private methods
	*/
	private String[] validatePacketDataString(final String packetData) throws FMPException {
		if(packetData == null)
			throw new MalformedFMPPacketException(MalformedFMPPacketException.DEFAULT_MESSAGE, null, "", "The packet data is null");
		if(packetData.isEmpty())
			throw new MalformedFMPPacketException(MalformedFMPPacketException.DEFAULT_MESSAGE, null, "", "The packet data is empty");
		String[] splitData = packetData.split(PACKET_SEPARATOR);
		if(splitData.length < PACKET_SEPARATOR_PARTS)
			throw new MalformedFMPPacketException(MalformedFMPPacketException.DEFAULT_MESSAGE, null, packetData, "The packet data is not properly assembled");
		if(splitData.length > PACKET_SEPARATOR_PARTS)
			throw new MalformedFMPPacketException(MalformedFMPPacketException.DEFAULT_MESSAGE, null, packetData, "The packet data is not properly assembled");
		return splitData;
	}

}
