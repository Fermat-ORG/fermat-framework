package com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.structure;

import java.nio.channels.SelectionKey;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

import com.bitdubai.fermat_api.layer.p2p_communication.CommunicationChannelAddress;
import com.bitdubai.fermat_api.layer.p2p_communication.cloud.CloudConnectionException;
import com.bitdubai.fermat_api.layer.p2p_communication.fmp.FMPException;
import com.bitdubai.fermat_api.layer.p2p_communication.fmp.FMPPacket;
import com.bitdubai.fermat_api.layer.p2p_communication.fmp.FMPPacket.FMPPacketType;
import com.bitdubai.fermat_api.layer._1_definition.communication.FMPPacketFactory;
import com.bitdubai.fermat_api.layer._1_definition.communication.cloud.CloudFMPConnectionManager;
import com.bitdubai.fermat_api.layer._1_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer._1_definition.enums.NetworkServices;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.exceptions.IncorrectFMPPacketDestinationException;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.exceptions.NetworkServiceAlreadyRegisteredException;


public class CloudServiceManager extends CloudFMPConnectionManager {
	
	private static String UNENCRYPTED_MESSAGE_SEPARATOR = " ";
	
	private Map<NetworkServices, CloudNetworkServiceManager> networkServicesRegistry = new ConcurrentHashMap<NetworkServices, CloudNetworkServiceManager>();

	public CloudServiceManager(final CommunicationChannelAddress address, final ExecutorService executor, final ECCKeyPair keyPair) throws IllegalArgumentException{
		super(address, executor, keyPair.getPrivateKey(), keyPair.getPublicKey(), CloudFMPConnectionManagerMode.FMP_SERVER);
		networkServicesRegistry.clear();
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
		if(!packet.getDestination().equals(getPublicKey()))
			throw new IncorrectFMPPacketDestinationException();
		
		if(registeredConnections.containsKey(packet.getSender()))
			processNetworkServiceConnectionRequest(registeredConnections.get(packet.getSender()), packet);
		else
			processConnectionRequest(unregisteredConnections.get(packet.getSender()), packet);
		
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
		connection.attach(responsePacket);
		connection.interestOps(SelectionKey.OP_WRITE);		
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

	public void registerNetworkServiceManager(final CloudNetworkServiceManager networkServiceManager) throws CloudConnectionException {
		if(networkServiceManager == null)
			throw new IllegalArgumentException();
		if(networkServicesRegistry.containsKey(networkServiceManager.getNetworkService()))
			throw new NetworkServiceAlreadyRegisteredException();
		if(!networkServiceManager.isRunning())
			networkServiceManager.start();
		networkServicesRegistry.put(networkServiceManager.getNetworkService(), networkServiceManager);
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
			message = networkServicesRegistry.get(networkService).getAddress().getHost() 
					+ UNENCRYPTED_MESSAGE_SEPARATOR 
					+ networkServicesRegistry.get(networkService).getAddress().getPort();
			
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
}
