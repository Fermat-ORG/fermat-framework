package com.bitdubai.fermat_api.layer._10_communication.fmp;

/*
 * Fermat Messaging Protocol
 * Created by jorgeejgonzalez 
 */
public interface FMPPacket {

	public static final int PACKET_MAX_BYTE_SIZE = 1000;
        public static final String PACKET_SEPARATOR = "\n";
        public static final int PACKET_SEPARATOR_PARTS = 5;

	public String getSender();
	public String getDestination();
	public FMPPacketType getType();
	public String getMessage();
	public String getSignature();
	public String convertToString();

	public enum FMPPacketType {
		CONNECTION_REQUEST,
		CONNECTION_ACCEPT,
		CONNECTION_ACCEPT_FORWARD,
		CONNECTION_DENY,
		CONNECTION_REGISTER,
		CONNECTION_DEREGISTER,
		CONNECTION_END,
		DATA_TRANSMIT
	}


}
