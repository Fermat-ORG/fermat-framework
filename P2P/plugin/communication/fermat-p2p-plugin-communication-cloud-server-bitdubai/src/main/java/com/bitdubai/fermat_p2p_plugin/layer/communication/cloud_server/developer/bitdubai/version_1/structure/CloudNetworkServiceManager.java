package com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.structure;

import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.*;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannelAddress;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud.exceptions.CloudCommunicationException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket.FMPPacketType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.CommunicationChannelAddressFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.FMPPacketFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.cloud.CloudFMPConnectionManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServices;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.exceptions.IncorrectFMPPacketDestinationException;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.exceptions.RegisteringAddressHasNotRequestedConnectionException;


public class CloudNetworkServiceManager extends CloudFMPConnectionManager {
	
	private static final int HASH_PRIME_NUMBER_PRODUCT = 5651;
	private static final int HASH_PRIME_NUMBER_ADD = 3191 ;
	
	private NetworkServices networkService;
	private Queue<Integer> availableVPNPorts = new ConcurrentLinkedQueue<Integer>();
	private Map<String, CloudNetworkServiceVPN> activeVPNConnections = new ConcurrentHashMap<String, CloudNetworkServiceVPN>();
	
	public CloudNetworkServiceManager(final CommunicationChannelAddress address, final ExecutorService executor, final ECCKeyPair keyPair, final NetworkServices networkService, final Collection<Integer> availableVPNPorts) throws IllegalArgumentException{
		super(address, executor, keyPair, CloudFMPConnectionManagerMode.FMP_SERVER);
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
			throw constructRegisteringAddressHasNotRequestedConnectionException(packet);
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
		return communicationChannelAddress.equals(compare.getCommunicationChannelAddress()) && networkService == compare.getNetworkService();
	}
	
	@Override
	public int hashCode(){
		int c = 0;
		c += communicationChannelAddress.hashCode();
		c += networkService.hashCode();
		return 	HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
	}
	
	private void requestRegisteredConnection(FMPPacket packet) throws FMPException {
		String decryptedMessage;
		try{
			decryptedMessage = AsymmectricCryptography.decryptMessagePrivateKey(packet.getMessage(), identity.getPrivateKey());
		}catch(Exception ex){
			denyConnectionRequest(packet, ex.getMessage());
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
		if(!packet.getDestination().equals(getIdentityPublicKey()))
			throw new IncorrectFMPPacketDestinationException(IncorrectFMPPacketDestinationException.DEFAULT_MESSAGE, null, "CommunicationFermatPacket Data: " + packet.toString(), "The Destination of the CommunicationFermatPacket is not the server");
		
		NetworkServices networkService;
		try{
			networkService = NetworkServices.valueOf(AsymmectricCryptography.decryptMessagePrivateKey(packet.getMessage(), identity.getPrivateKey()));
		} catch(Exception ex){
			denyConnectionRequest(packet, ex.getMessage());
			return;
		}
		
		if(this.networkService.equals(networkService))
			acceptConnectionRequest(packet);
		else
			denyConnectionRequest(packet, "NETWORK SERVICE " + networkService + " IS NOT SUPPORTED BY THE SERVER");
		
	}
	
	private void processVPNRequest(final FMPPacket packet) throws FMPException, CloudCommunicationException {
		Integer vpnPort = availableVPNPorts.remove();
		CommunicationChannelAddress vpnAddress = CommunicationChannelAddressFactory.constructCloudAddress(communicationChannelAddress.getHost(), vpnPort);
		Set<String> vpnParticipants = new HashSet<String>();
		vpnParticipants.add(packet.getSender());
		vpnParticipants.add(packet.getDestination());
		StringBuilder messageBuilder = new StringBuilder();
		for(String participant : vpnParticipants)
			messageBuilder.append(participant + ",");
		String message = messageBuilder.toString().trim();
		
		CloudNetworkServiceVPN vpn = new CloudNetworkServiceVPN(vpnAddress, Executors.newFixedThreadPool(4), new com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair(), networkService, vpnParticipants);
		vpn.start();
		String vpnKey = packet.getSender() + packet.getDestination();
		activeVPNConnections.put(vpnKey, vpn);
		
		sendVPNRequest(packet.getSender(), packet.getDestination(), message);
		sendVPNRequest(packet.getDestination(), packet.getSender(), message);
	}
	
	private void sendVPNRequest(final String sender, final String destination, final String message) throws FMPException{


        FMPPacket responsePacket = FMPPacketFactory.constructCloudFMPPacketEncryptedAndSinged(sender,         //sender
                                                                                                destination,                 //destination
                                                                                                message,                         // message
                                                                                                FMPPacketType.CONNECTION_REQUEST,
                                                                                                networkService,
                                                                                                identity.getPrivateKey());


        writeToRegisteredConnection(responsePacket);
	}
	
	private void processVPNAccept(final String requestor, final String participant, final CloudNetworkServiceVPN vpn) throws FMPException{


		String message = vpn.getCommunicationChannelAddress().getHost() + "," + vpn.getCommunicationChannelAddress().getPort() + "," + vpn.getIdentityPublicKey();

        FMPPacket responsePacket = FMPPacketFactory.constructCloudFMPPacketEncryptedAndSinged(requestor,         //sender
                                                                                              participant,                 //destination
                                                                                              message,                         // message
                                                                                              FMPPacketType.CONNECTION_ACCEPT_FORWARD,
                                                                                              networkService,
                                                                                              identity.getPrivateKey());

		writeToRegisteredConnection(responsePacket);
	}
	
	private void acceptConnectionRequest(final FMPPacket packet)  throws FMPException {

		String message = networkService.toString();

        FMPPacket responsePacket = FMPPacketFactory.constructCloudFMPPacketEncryptedAndSinged(identity.getPublicKey(),         //sender
                packet.getSender(),                 //destination
                message,                         // message
                FMPPacketType.CONNECTION_ACCEPT,
                networkService,
                identity.getPrivateKey());

		writeToUnregisteredConnection(responsePacket);
	}
	
	private void denyConnectionRequest(final FMPPacket packet, final String reason) throws FMPException{


        FMPPacket responsePacket = FMPPacketFactory.constructCloudFMPPacketEncryptedAndSinged(identity.getPrivateKey(),         //sender
                                                                                                packet.getSender(),                 //destination
                                                                                                reason,                         // message
                                                                                                FMPPacketType.CONNECTION_DENY,
                                                                                                networkService,
                                                                                                identity.getPrivateKey());

		if(registeredConnections.containsKey(packet.getSender()))
			writeToRegisteredConnection(responsePacket);
		else
			writeToUnregisteredConnection(responsePacket);
	}
	
	private void registerConnection(final FMPPacket packet) throws FMPException {
		SelectionKey connection = unregisteredConnections.get(packet.getSender());
		registeredConnections.put(packet.getSender(), connection);
		unregisteredConnections.remove(packet.getSender());


        FMPPacket responsePacket = FMPPacketFactory.constructCloudFMPPacketEncryptedAndSinged(identity.getPublicKey(),         //sender
                packet.getSender(),                 //destination
                "REGISTERED",                         // message
                FMPPacketType.DATA_TRANSMIT,
                networkService,
                identity.getPrivateKey());


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

	private RegisteringAddressHasNotRequestedConnectionException constructRegisteringAddressHasNotRequestedConnectionException(final FMPPacket packet){
		String message = RegisteringAddressHasNotRequestedConnectionException.DEFAULT_MESSAGE;
		String possibleReason = "This can happen whenever we receive a CONNECTION_REGISTER packet before a CONNECTION_REQUEST packet";
		possibleReason += " even though this might be due to improper client message flow, it can also be a threading problem";
		possibleReason += " as we can process a register packet for a connection that has already been registered, we need to improve this";

		String context = "CommunicationFermatPacket Data: " + packet.toString();
		context += RegisteringAddressHasNotRequestedConnectionException.CONTEXT_CONTENT_SEPARATOR;
		context += "Is this connection already registered? " + registeredConnections.containsKey(packet.getSender());

		return new RegisteringAddressHasNotRequestedConnectionException(message, null, context, possibleReason);

	}

	public List<String> getNetworkServicesConnectedList(){

		return  new ArrayList<>(activeVPNConnections.keySet());
	}
}
