package com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.structure;

import java.nio.channels.SelectionKey;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannelAddress;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud.exceptions.CloudCommunicationException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket.FMPPacketType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.FMPPacketFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.cloud.CloudFMPConnectionManager;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServices;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.exceptions.IncorrectFMPPacketDestinationException;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.exceptions.NetworkServiceAlreadyRegisteredException;


public class CloudServiceManager extends CloudFMPConnectionManager {
	
	private final Map<NetworkServices, CloudNetworkServiceManager> networkServicesRegistry = new ConcurrentHashMap<NetworkServices, CloudNetworkServiceManager>();

	public CloudServiceManager(final CommunicationChannelAddress address, final ExecutorService executor, final ECCKeyPair keyPair) throws IllegalArgumentException{
		super(address, executor, keyPair.getPrivateKey(), keyPair.getPublicKey(), CloudFMPConnectionManagerMode.FMP_SERVER);
		networkServicesRegistry.clear();
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
	public void handleConnectionRequest(final FMPPacket packet) throws FMPException{
		if(!packet.getDestination().equals(getPublicKey()))
			throw constructIncorrectFMPPacketDestinationException(packet, eccPublicKey);

		if(registeredConnections.containsKey(packet.getSender()))
			processNetworkServiceConnectionRequest(registeredConnections.get(packet.getSender()), packet);
		else
			processConnectionRequest(unregisteredConnections.get(packet.getSender()), packet);
	}	
	
	@Override
	public void handleConnectionRegister(final FMPPacket packet) throws FMPException{
		if(unregisteredConnections.containsKey(packet.getSender()))
				processConnectionRegister(packet);
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

	public void registerNetworkServiceManager(final CloudNetworkServiceManager networkServiceManager) throws CloudCommunicationException {
		if(networkServiceManager == null)
			throw new IllegalArgumentException();
		if(networkServicesRegistry.containsKey(networkServiceManager.getNetworkService()))
			throw constructNetworkServiceAlreadyRegistered(networkServiceManager.getNetworkService());
		if(!networkServiceManager.isRunning())
			networkServiceManager.start();
		networkServicesRegistry.put(networkServiceManager.getNetworkService(), networkServiceManager);
	}

	private void processConnectionRequest(final SelectionKey connection, final FMPPacket dataPacket) throws FMPException{
		String sender = getPublicKey();
		String destination = dataPacket.getSender();
		String messageHash = getPublicKey();
		FMPPacketType type = FMPPacketType.CONNECTION_ACCEPT;
		String signature = AsymmectricCryptography.createMessageSignature(messageHash, eccPrivateKey);
		
		FMPPacket responsePacket = FMPPacketFactory.constructCloudPacket(sender, destination, type, messageHash, signature);		
		connection.attach(responsePacket);
		connection.interestOps(SelectionKey.OP_WRITE);
	}
	
	private void processNetworkServiceConnectionRequest(final SelectionKey connection, final FMPPacket dataPacket) throws FMPException{
		String sender = getPublicKey();
		String destination = dataPacket.getSender();
		FMPPacketType type;
		String message;
		String messageHash;
		String signature;
		
		NetworkServices networkService = NetworkServices.valueOf(AsymmectricCryptography.decryptMessagePrivateKey(dataPacket.getMessage(), eccPrivateKey));
		
		if(networkServicesRegistry.containsKey(networkService)){
			type = FMPPacketType.CONNECTION_ACCEPT_FORWARD;
			message = networkService.toString()
					+ FMPPacket.MESSAGE_SEPARATOR
					+ networkServicesRegistry.get(networkService).getAddress().getHost() 
					+ FMPPacket.MESSAGE_SEPARATOR
					+ networkServicesRegistry.get(networkService).getAddress().getPort()
					+ FMPPacket.MESSAGE_SEPARATOR
					+ networkServicesRegistry.get(networkService).getPublicKey();
			
		} else {
			type = FMPPacketType.CONNECTION_DENY;
			message = "NETWORK SERVICE " + networkService + " IS NOT SUPPORTED BY THE CLOUD SERVER";
		}
		messageHash = AsymmectricCryptography.encryptMessagePublicKey(message, destination);
		signature = AsymmectricCryptography.createMessageSignature(messageHash, eccPrivateKey);
		
		FMPPacket responsePacket = FMPPacketFactory.constructCloudPacket(sender, destination, type, messageHash, signature);		
		connection.attach(responsePacket);
		connection.interestOps(SelectionKey.OP_WRITE);
	}
	
	private void processConnectionRegister(final FMPPacket packet) throws FMPException{
		SelectionKey connection = unregisteredConnections.get(packet.getSender());
		registeredConnections.put(packet.getSender(), connection);
		unregisteredConnections.remove(packet.getSender());
		String sender = getPublicKey();
		String destination = packet.getSender();
		String messageHash = AsymmectricCryptography.encryptMessagePublicKey("REGISTERED", destination);		
		FMPPacketType type = FMPPacketType.DATA_TRANSMIT;
		String signature = AsymmectricCryptography.createMessageSignature(messageHash, eccPrivateKey);
		
		FMPPacket responsePacket = FMPPacketFactory.constructCloudPacket(sender, destination, type, messageHash, signature);		
		connection.attach(responsePacket);
		connection.interestOps(SelectionKey.OP_WRITE);
	}

	private IncorrectFMPPacketDestinationException constructIncorrectFMPPacketDestinationException(final FMPPacket packet, final String supposedDestination) {
		String message = IncorrectFMPPacketDestinationException.DEFAULT_MESSAGE;
		FermatException cause = null;
		String context = "Supposed Destination: " + supposedDestination;
		context += "Packet Info: " + packet.toString();
		String possibleReason = "This is a very weird error, we should check the sender to see if it's registered, we should be aware of this";
		return new IncorrectFMPPacketDestinationException(message, cause, context, possibleReason);
	}

	private NetworkServiceAlreadyRegisteredException constructNetworkServiceAlreadyRegistered(final NetworkServices networkService){
		String message = NetworkServiceAlreadyRegisteredException.DEFAULT_MESSAGE;
		FermatException cause = null;
		String context = "Network Service: " + networkService.toString();
		String possibleReason = "We are registring a NetworkService that has already been registered, check the invocations to this method";
		return new NetworkServiceAlreadyRegisteredException(message, cause, context, possibleReason);
	}
}
