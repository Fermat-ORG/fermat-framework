package com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.structure;

import java.nio.channels.SelectionKey;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServices;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.FMPPacketFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.cloud.CloudFMPConnectionManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannelAddress;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud_server.exceptions.CloudConnectionException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket.FMPPacketType;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.exceptions.IncorrectFMPPacketDestinationException;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.exceptions.WrongFMPPacketEncryptionException;

public class CloudNetworkServiceVPN extends CloudFMPConnectionManager {

	private static final int HASH_PRIME_NUMBER_PRODUCT = 2999;
	private static final int HASH_PRIME_NUMBER_ADD = 4013;
	
	private NetworkServices networkService;
	private Set<String> participants = new ConcurrentSkipListSet<String>();
	
	public CloudNetworkServiceVPN(final CommunicationChannelAddress address, final ExecutorService executor, final ECCKeyPair keyPair, final NetworkServices networkService, final Collection<String> participants) throws IllegalArgumentException{
		super(address, executor, keyPair.getPrivateKey(), keyPair.getPublicKey(), CloudFMPConnectionManagerMode.FMP_SERVER);
		if(networkService == null)
			throw new IllegalArgumentException();
		this.networkService = networkService;
		
		if(participants == null || participants.isEmpty())
			throw new IllegalArgumentException();
		for(String participant: participants)
			this.participants.add(participant);
	}
	
	@Override
	public void processDataPacket(final String data, final SelectionKey key) throws CloudConnectionException{
		try {
			FMPPacket dataPacket = FMPPacketFactory.constructCloudPacket(data);
			key.attach(dataPacket);
			if(dataPacket.getType() == FMPPacketType.CONNECTION_REQUEST){
				if(registeredConnections.isEmpty() 
						|| !registeredConnections.containsKey(dataPacket.getSender()) 
						&& !unregisteredConnections.containsKey(dataPacket.getSender()))
					unregisteredConnections.put(dataPacket.getSender(), key);
				handleConnectionRequest(dataPacket);
			}
			if(dataPacket.getType() == FMPPacketType.CONNECTION_ACCEPT)
				handleConnectionAccept(dataPacket);
			if(dataPacket.getType() == FMPPacketType.CONNECTION_ACCEPT_FORWARD)
				handleConnectionAcceptForward(dataPacket);
			if(dataPacket.getType() == FMPPacketType.CONNECTION_DENY)
				handleConnectionDeny(dataPacket);
			if(dataPacket.getType() == FMPPacketType.CONNECTION_REGISTER)
				handleConnectionRegister(dataPacket);
			if(dataPacket.getType() == FMPPacketType.CONNECTION_DEREGISTER)
				handleConnectionDeregister(dataPacket);
			if(dataPacket.getType() == FMPPacketType.CONNECTION_END)
				handleConnectionEnd(dataPacket);
			if(dataPacket.getType() == FMPPacketType.DATA_TRANSMIT)
				handleDataTransmit(dataPacket);
		} catch(FMPException ex){
			throw new CloudConnectionException(ex.getMessage());
		}
	}


	@Override
	public void handleConnectionRequest(final FMPPacket packet) throws FMPException{
		if(participants.contains(packet.getSender()))
			requestUnregisteredConnection(packet);
		else
			denyConnectionRequest(packet);
	}	
	
	@Override
	public void handleConnectionAccept(final FMPPacket packet) throws FMPException{
	}

	@Override
	public void handleConnectionAcceptForward(final FMPPacket packet) throws FMPException{
	}

	@Override
	public void handleConnectionDeny(final FMPPacket packet) throws FMPException{
	}

	@Override
	public void handleConnectionRegister(final FMPPacket packet) throws FMPException{
		SelectionKey connection = unregisteredConnections.get(packet.getSender());
		registeredConnections.put(packet.getSender(), connection);
		
		String sender = getPublicKey();
		String destination = packet.getSender();
		String messageHash = AsymmectricCryptography.encryptMessagePublicKey("REGISTERED", destination);		
		FMPPacketType type = FMPPacketType.DATA_TRANSMIT;
		String signature = AsymmectricCryptography.createMessageSignature(messageHash, eccPrivateKey);
		
		FMPPacket responsePacket = FMPPacketFactory.constructCloudPacket(sender, destination, type, messageHash, signature);		
		writeToRegisteredConnection(responsePacket);
	}

	@Override
	public void handleConnectionDeregister(final FMPPacket packet) throws FMPException{
	}

	@Override
	public void handleConnectionEnd(final FMPPacket packet) throws FMPException{
	}

	@Override
	public void handleDataTransmit(final FMPPacket packet) throws FMPException{
	}
	
	public NetworkServices getNetworkService(){
		return networkService;
	}
	
	private Set<String> getParticipants() {
		return participants;
	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof CloudNetworkServiceVPN))
			return false;
		
		CloudNetworkServiceVPN compare = (CloudNetworkServiceVPN) o;
		return address.equals(compare.getAddress()) && networkService == compare.getNetworkService() && participants.equals(compare.getParticipants());
	}
	
	@Override
	public int hashCode(){
		int c = 0;
		c += address.hashCode();
		c += networkService.hashCode();
		c += participants.hashCode();
		return 	HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
	}
	
	private void requestUnregisteredConnection(final FMPPacket packet) throws FMPException {
		if(!packet.getDestination().equals(getPublicKey()))
			throw new IncorrectFMPPacketDestinationException();
		
		NetworkServices networkService;
		try{
			networkService = NetworkServices.valueOf(AsymmectricCryptography.decryptMessagePrivateKey(packet.getMessage(), eccPrivateKey));
		} catch(Exception ex){
			throw new WrongFMPPacketEncryptionException(ex.getMessage());
		}
		
		if(this.networkService.equals(networkService))
			acceptConnectionRequest(packet);
		else
			denyConnectionRequest(packet);
	}
	
	private void acceptConnectionRequest(FMPPacket packet)  throws FMPException {
		String sender = getPublicKey();
		String destination = packet.getSender();
		String message = networkService.toString();
		FMPPacketType type = FMPPacketType.CONNECTION_ACCEPT;
		String messageHash = AsymmectricCryptography.encryptMessagePublicKey(message, destination);
		String signature = AsymmectricCryptography.createMessageSignature(messageHash, eccPrivateKey);
		FMPPacket responsePacket = FMPPacketFactory.constructCloudPacket(sender, destination, type, messageHash, signature);
		writeToUnregisteredConnection(responsePacket);
	}
	
	private void denyConnectionRequest(FMPPacket packet) throws FMPException{
		String sender = getPublicKey();
		String destination = packet.getSender();
		String message = "NETWORK SERVICE " + networkService + " IS NOT SUPPORTED BY THE SERVER";
		FMPPacketType type = FMPPacketType.CONNECTION_DENY;
		String messageHash = AsymmectricCryptography.encryptMessagePublicKey(message, destination);
		String signature = AsymmectricCryptography.createMessageSignature(messageHash, eccPrivateKey);
		FMPPacket responsePacket = FMPPacketFactory.constructCloudPacket(sender, destination, type, messageHash, signature);
		writeToUnregisteredConnection(responsePacket);
	}
	
	private void writeToUnregisteredConnection(final FMPPacket packet) {
		SelectionKey connection = unregisteredConnections.get(packet.getDestination());
		writeToConnection(packet, connection);
	}
	
	private void writeToRegisteredConnection(final FMPPacket packet) {
		SelectionKey connection = registeredConnections.get(packet.getDestination());
		writeToConnection(packet, connection);
	}
	
	private void writeToConnection(final FMPPacket packet, final SelectionKey connection){
		connection.attach(packet);
		connection.interestOps(SelectionKey.OP_WRITE);
	}

}
