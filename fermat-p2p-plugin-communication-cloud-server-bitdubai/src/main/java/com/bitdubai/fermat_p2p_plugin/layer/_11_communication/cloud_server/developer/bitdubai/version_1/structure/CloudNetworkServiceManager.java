package com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_server.developer.bitdubai.version_1.structure;

import java.nio.channels.SelectionKey;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;

import com.bitdubai.fermat_api.layer._10_communication.CommunicationChannelAddress;
import com.bitdubai.fermat_api.layer._10_communication.cloud.CloudConnectionException;
import com.bitdubai.fermat_api.layer._10_communication.fmp.FMPException;
import com.bitdubai.fermat_api.layer._10_communication.fmp.FMPPacket;
import com.bitdubai.fermat_api.layer._10_communication.fmp.FMPPacket.FMPPacketType;
import com.bitdubai.fermat_api.layer._1_definition.communication.FMPPacketFactory;
import com.bitdubai.fermat_api.layer._1_definition.communication.cloud.CloudFMPConnectionManager;
import com.bitdubai.fermat_api.layer._1_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer._1_definition.enums.NetworkServices;
import com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_server.developer.bitdubai.version_1.exceptions.IncorrectFMPPacketDestinationException;


public class CloudNetworkServiceManager extends CloudFMPConnectionManager {
	
	private static final int HASH_PRIME_NUMBER_PRODUCT = 5651;
	private static final int HASH_PRIME_NUMBER_ADD = 3191 ;
	
	private NetworkServices networkService;
	private Set<Integer> availableVPNPorts = new ConcurrentSkipListSet<Integer>();
	
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
		
		SelectionKey unregisteredConnection = unregisteredConnections.get(packet.getSender());
		
		NetworkServices networkService = NetworkServices.valueOf(AsymmectricCryptography.decryptMessagePrivateKey(packet.getMessage(), eccPrivateKey));
		
		String sender = getPublicKey();
		String destination = packet.getSender();
		String message;
		FMPPacketType type;
		if(this.networkService.equals(networkService)){
			message = networkService.toString();
			type = FMPPacketType.CONNECTION_ACCEPT;
		} else {
			message = "NETWORK SERVICE " + networkService + " IS NOT SUPPORTED BY THE SERVER";
			type = FMPPacketType.CONNECTION_DENY;
		}
		
		String messageHash = AsymmectricCryptography.encryptMessagePublicKey(message, destination);
		String signature = AsymmectricCryptography.createMessageSignature(messageHash, eccPrivateKey);
		
		FMPPacket responsePacket = FMPPacketFactory.constructCloudPacket(sender, destination, type, messageHash, signature);		
		unregisteredConnection.attach(responsePacket);
		unregisteredConnection.interestOps(SelectionKey.OP_WRITE);
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

}
