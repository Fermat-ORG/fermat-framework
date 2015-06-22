package com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.structure;

import java.nio.channels.SelectionKey;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannelAddress;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud_server.exceptions.CloudConnectionException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket.FMPPacketType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.CommunicationChannelAddressFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.FMPPacketFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.cloud.CloudFMPConnectionManager;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServices;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.exceptions.IncorrectFMPPacketDestinationException;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.exceptions.RegisteringAddressHasNotRequestedConnectionException;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.exceptions.WrongFMPPacketEncryptionException;


public class CloudNetworkServiceManager extends CloudFMPConnectionManager {
	
	private static final int HASH_PRIME_NUMBER_PRODUCT = 5651;
	private static final int HASH_PRIME_NUMBER_ADD = 3191 ;
	
	private NetworkServices networkService;
	private Queue<Integer> availableVPNPorts = new ConcurrentLinkedQueue<Integer>();
	private Map<String, CloudNetworkServiceVPN> activeVPNConnections = new ConcurrentHashMap<String, CloudNetworkServiceVPN>();
	
	public CloudNetworkServiceManager(final CommunicationChannelAddress address, final ExecutorService executor, final ECCKeyPair keyPair, final NetworkServices networkService, final Collection<Integer> availableVPNPorts) throws IllegalArgumentException{
		super(address, executor, keyPair.getPrivateKey(), keyPair.getPublicKey(), CloudFMPConnectionManagerMode.FMP_SERVER);
		if(networkService == null)
			throw new IllegalArgumentException();
		this.networkService = networkService;
		
		if(availableVPNPorts == null || availableVPNPorts.isEmpty())
			throw new IllegalArgumentException();
		for(Integer vpnPort: availableVPNPorts)
			this.availableVPNPorts.add(vpnPort);
	}
	
	@Override
	public void handleConnectionRequest(final FMPPacket packet) throws FMPException{
		if(registeredConnections.containsKey(packet.getSender()))
			requestRegisteredConnection(packet);
		else
			requestUnregisteredConnection(packet);
	}	
	
	@Override
	public void handleConnectionAccept(final FMPPacket packet) throws FMPException{
		String vpnKey1 = packet.getSender() + packet.getDestination();
		if(activeVPNConnections.containsKey(vpnKey1)){
			processVPNAccept(packet.getDestination(), packet.getSender(), activeVPNConnections.get(vpnKey1));
			return;
		}

		String vpnKey2 = packet.getDestination() + packet.getSender();
		if(activeVPNConnections.containsKey(vpnKey2)){
			processVPNAccept(packet.getDestination(), packet.getSender(), activeVPNConnections.get(vpnKey2));
			return;
		}
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
	}
	
	public NetworkServices getNetworkService(){
		return networkService;
	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof CloudNetworkServiceManager))
			return false;
		
		CloudNetworkServiceManager compare = (CloudNetworkServiceManager) o;
		return address.equals(compare.getAddress()) && networkService == compare.getNetworkService();
	}
	
	@Override
	public int hashCode(){
		int c = 0;
		c += address.hashCode();
		c += networkService.hashCode();
		return 	HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
	}
	
	private void requestRegisteredConnection(FMPPacket packet) throws FMPException {
		String decryptedMessage;
		try{
			decryptedMessage = AsymmectricCryptography.decryptMessagePrivateKey(packet.getMessage(), eccPrivateKey);
		}catch(Exception ex){
			FMPException exception = new WrongFMPPacketEncryptionException(ex.getMessage());
			denyConnectionRequest(packet, exception.getMessage()); 
			return;
		}
		if(decryptedMessage.equals("VPN"))
			try{
				processVPNRequest(packet);
			} catch(Exception ex){
				denyConnectionRequest(packet, "Failed To Initialize The VPN Connection");
			}
		else
			denyConnectionRequest(packet, "New Requests Are Only Supported For VPN Requests");
	
	}
	
	private void requestUnregisteredConnection(final FMPPacket packet) throws FMPException {
		if(!packet.getDestination().equals(getPublicKey()))
			throw new IncorrectFMPPacketDestinationException();
		
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
	
	private void processVPNRequest(final FMPPacket packet) throws FMPException, CloudConnectionException {
		Integer vpnPort = availableVPNPorts.remove();
		CommunicationChannelAddress vpnAddress = CommunicationChannelAddressFactory.constructCloudAddress(address.getHost(), vpnPort);
		Set<String> vpnParticipants = new HashSet<String>();
		vpnParticipants.add(packet.getSender());
		vpnParticipants.add(packet.getDestination());
		StringBuilder messageBuilder = new StringBuilder();
		for(String participant : vpnParticipants)
			messageBuilder.append(participant + FMPPacket.MESSAGE_SEPARATOR);
		String message = messageBuilder.toString().trim();
		
		CloudNetworkServiceVPN vpn = new CloudNetworkServiceVPN(vpnAddress, Executors.newFixedThreadPool(4), new ECCKeyPair(), networkService, vpnParticipants);
		vpn.start();
		String vpnKey = packet.getSender() + packet.getDestination();
		activeVPNConnections.put(vpnKey, vpn);
		
		sendVPNRequest(packet.getSender(), packet.getDestination(), message);
		sendVPNRequest(packet.getDestination(), packet.getSender(), message);
	}
	
	private void sendVPNRequest(final String sender, final String destination, final String message) throws FMPException{
		FMPPacketType type = FMPPacketType.CONNECTION_REQUEST;
		String messageHash = AsymmectricCryptography.encryptMessagePublicKey(message, destination);
		String signature = AsymmectricCryptography.createMessageSignature(messageHash, eccPrivateKey);
		FMPPacket responsePacket = FMPPacketFactory.constructCloudPacket(sender, destination, type, messageHash, signature);
		writeToRegisteredConnection(responsePacket);
	}
	
	private void processVPNAccept(final String requestor, final String participant, final CloudNetworkServiceVPN vpn) throws FMPException{
		String sender = requestor;
		String destination = participant;
		String message = vpn.getAddress().getHost() + FMPPacket.MESSAGE_SEPARATOR + vpn.getAddress().getPort() + FMPPacket.MESSAGE_SEPARATOR + vpn.getPublicKey();
		FMPPacketType type = FMPPacketType.CONNECTION_ACCEPT_FORWARD;
		String messageHash = AsymmectricCryptography.encryptMessagePublicKey(message, destination);
		String signature = AsymmectricCryptography.createMessageSignature(messageHash, eccPrivateKey);
		FMPPacket responsePacket = FMPPacketFactory.constructCloudPacket(sender, destination, type, messageHash, signature);
		writeToRegisteredConnection(responsePacket);
	}
	
	private void acceptConnectionRequest(final FMPPacket packet)  throws FMPException {
		String sender = getPublicKey();
		String destination = packet.getSender();
		String message = networkService.toString();
		FMPPacketType type = FMPPacketType.CONNECTION_ACCEPT;
		String messageHash = AsymmectricCryptography.encryptMessagePublicKey(message, destination);
		String signature = AsymmectricCryptography.createMessageSignature(messageHash, eccPrivateKey);
		FMPPacket responsePacket = FMPPacketFactory.constructCloudPacket(sender, destination, type, messageHash, signature);
		writeToUnregisteredConnection(responsePacket);
	}
	
	private void denyConnectionRequest(final FMPPacket packet, final String reason) throws FMPException{
		String sender = getPublicKey();
		String destination = packet.getSender();
		String message = reason;
		FMPPacketType type = FMPPacketType.CONNECTION_DENY;
		String messageHash = AsymmectricCryptography.encryptMessagePublicKey(message, destination);
		String signature = AsymmectricCryptography.createMessageSignature(messageHash, eccPrivateKey);
		FMPPacket responsePacket = FMPPacketFactory.constructCloudPacket(sender, destination, type, messageHash, signature);
		if(registeredConnections.containsKey(destination))
			writeToRegisteredConnection(responsePacket);
		else
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
