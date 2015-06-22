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
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket.FMPPacketType;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.exceptions.RegisteringAddressHasNotRequestedConnectionException;
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
	public void handleConnectionRequest(final FMPPacket packet) throws FMPException{
		if(participants.contains(packet.getSender()) && participants.contains(packet.getDestination()))
			requestUnregisteredConnection(packet);
		else
			denyConnectionRequest(packet, "Address Is Not A Participant Of This VPN");
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
		if(unregisteredConnections.containsKey(packet.getSender()))
			registerConnection(packet);
		else
			throw new RegisteringAddressHasNotRequestedConnectionException(packet.getSender());
	}

	@Override
	public void handleConnectionDeregister(final FMPPacket packet) throws FMPException{
	}

	@Override
	public void handleConnectionEnd(final FMPPacket packet) throws FMPException{
	}

	@Override
	public void handleDataTransmit(final FMPPacket packet) throws FMPException{
		sendPacketToRecipient(packet);
	}
	
	private void sendPacketToRecipient(final FMPPacket packet) throws FMPException{
		String sender = packet.getDestination();
		String destination = packet.getSender();
		FMPPacketType type = FMPPacketType.DATA_TRANSMIT;
		String message = packet.getMessage();
		String signature = packet.getSignature();
		FMPPacket dataPacket = FMPPacketFactory.constructCloudPacket(sender, destination, type, message, signature);
		writeToRegisteredConnection(dataPacket);
	}

	public NetworkServices getNetworkService(){
		return networkService;
	}
	
	public Set<String> getParticipants() {
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
		NetworkServices networkService;
		try{
			networkService = NetworkServices.valueOf(AsymmectricCryptography.decryptMessagePrivateKey(packet.getMessage(), eccPrivateKey));
		} catch(Exception ex){
			FMPException exception = new WrongFMPPacketEncryptionException(ex.getMessage());
			denyConnectionRequest(packet, exception.getMessage());
			return;
		}
		
		if(this.networkService.equals(networkService))
			acceptConnectionRequest(packet);
		else
			denyConnectionRequest(packet, "NETWORK SERVICE " + networkService + " IS NOT SUPPORTED BY THE SERVER");
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
	
	private void denyConnectionRequest(FMPPacket packet, final String reason) throws FMPException{
		String sender = getPublicKey();
		String destination = packet.getSender();
		String message = reason;
		FMPPacketType type = FMPPacketType.CONNECTION_DENY;
		String messageHash = AsymmectricCryptography.encryptMessagePublicKey(message, destination);
		String signature = AsymmectricCryptography.createMessageSignature(messageHash, eccPrivateKey);
		FMPPacket responsePacket = FMPPacketFactory.constructCloudPacket(sender, destination, type, messageHash, signature);
		writeToUnregisteredConnection(responsePacket);
	}
	
	private void registerConnection(final FMPPacket packet) throws FMPException {
		SelectionKey connection = unregisteredConnections.get(packet.getSender());
		registeredConnections.put(packet.getSender(), connection);
		unregisteredConnections.remove(packet.getSender());
		String sender = getPublicKey();
		String destination = packet.getSender();
		String messageHash = AsymmectricCryptography.encryptMessagePublicKey("REGISTERED", destination);		
		FMPPacketType type = FMPPacketType.DATA_TRANSMIT;
		String signature = AsymmectricCryptography.createMessageSignature(messageHash, eccPrivateKey);
		
		FMPPacket responsePacket = FMPPacketFactory.constructCloudPacket(sender, destination, type, messageHash, signature);		
		writeToRegisteredConnection(responsePacket);
	}
	
	private void writeToUnregisteredConnection(final FMPPacket packet) {
		SelectionKey connection = unregisteredConnections.get(packet.getDestination());
		if(connection!=null)
			writeToConnection(packet, connection);
	}
	
	private void writeToRegisteredConnection(final FMPPacket packet) {
		SelectionKey connection = registeredConnections.get(packet.getDestination());
		if(connection!=null)
			writeToConnection(packet, connection);
	}
	
	private void writeToConnection(final FMPPacket packet, final SelectionKey connection){
		connection.attach(packet);
		connection.interestOps(SelectionKey.OP_WRITE);
	}

}
