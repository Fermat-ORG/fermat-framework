package com.bitdubai.fermat_api.layer._10_communication.fmp;

/*
 * Fermat Messaging Protocol
 * Created by jorgeejgonzalez 
 */
public interface FMPPacketHandler {
	public void handleConnectionRequest(final FMPPacket packet) throws FMPException; 
	public void handleConnectionAccept(final FMPPacket packet) throws FMPException;
	public void handleConnectionAcceptForward(final FMPPacket packet) throws FMPException;
	public void handleConnectionDeny(final FMPPacket packet) throws FMPException;
	public void handleConnectionRegister(final FMPPacket packet) throws FMPException;
	public void handleConnectionDeregister(final FMPPacket packet) throws FMPException;
	public void handleConnectionEnd(final FMPPacket packet) throws FMPException;
	public void handleDataTransmit(final FMPPacket packet) throws FMPException;
}
