/*
 * @#CloudServiceManager.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.structure;

import java.nio.channels.SelectionKey;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.*;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannelAddress;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud.exceptions.CloudCommunicationException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket.FMPPacketType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.FMPPacketFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.cloud.CloudFMPConnectionManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServices;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.exceptions.IncorrectFMPPacketDestinationException;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.exceptions.NetworkServiceAlreadyRegisteredException;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.structure.CloudServiceManager</code> represent
 * the cloud server
 * <p/>
 *
 * Created by Jorge Gonzales
 * Update by Roberto Requena - (rart3001@gmail.com) on 09/06/15.
 *
 * @version 1.0
 */
public class CloudServiceManager extends CloudFMPConnectionManager {

	/**
	 * Represent the networkServicesRegistry
	 */
	private final Map<NetworkServices, CloudNetworkServiceManager> networkServicesRegistry;

	/**
	 * Constructor whit parameters
	 *
	 * @param address
	 * @param executor
	 * @param keyPair
	 * @throws IllegalArgumentException
	 */
	public CloudServiceManager(final CommunicationChannelAddress address, final ExecutorService executor, final com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair keyPair) throws IllegalArgumentException{
		super(address, executor, keyPair.getPrivateKey(), keyPair.getPublicKey(), CloudFMPConnectionManagerMode.FMP_SERVER);
		networkServicesRegistry = new ConcurrentHashMap<NetworkServices, CloudNetworkServiceManager>();
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

		System.out.println("CloudServiceManager - Starting method handleConnectionRequest");

		/*
		 * Validate that the destination are I
		 */
		if(!packet.getDestination().equals(getIdentityPublicKey())) {
            throw constructIncorrectFMPPacketDestinationException(packet, identity.getPublicKey());
        }

        /*
         * Validate is the sender have a register connection
         */
		if(registeredConnections.containsKey(packet.getSender())) {

            processNetworkServiceConnectionRequest(registeredConnections.get(packet.getSender()), packet);

        }else {

            processConnectionRequest(unregisteredConnections.get(packet.getSender()), packet);
        }
	}	
	
	@Override
	public void handleConnectionRegister(final FMPPacket packet) throws FMPException{

        System.out.println("CloudServiceManager - Starting method handleConnectionRegister");

        /*
         * Validate is the sender are in a unregister connection
         */
        if(unregisteredConnections.containsKey(packet.getSender())) {

            /*
             * Process to register a new connection
             */
            processConnectionRegister(packet);
        }
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

    /**
     * This method process a connection request, create a new package with the ???
     *
     * @param connection
     * @param dataPacket
     * @throws FMPException
     */
	private void processConnectionRequest(final SelectionKey connection, final FMPPacket dataPacket) throws FMPException{

        System.out.println("CloudServiceManager - Starting method processConnectionRequest");
        System.out.println("dataPacket = "+dataPacket);
        System.out.println(" ----------------------------------------------------------  ");

		String sender = identity.getPublicKey();
		String destination = dataPacket.getSender();
		String messageHash = identity.getPublicKey();
		FMPPacketType type = FMPPacketType.CONNECTION_ACCEPT;
		String signature = AsymmectricCryptography.createMessageSignature(messageHash, identity.getPrivateKey());
		FMPPacket responsePacket = FMPPacketFactory.constructCloudPacket(sender, destination, type, messageHash, signature);

        System.out.println("responsePacket = "+responsePacket);
        System.out.println(" ----------------------------------------------------------  ");

		connection.attach(responsePacket);
		connection.interestOps(SelectionKey.OP_WRITE);

	}
	
	private void processNetworkServiceConnectionRequest(final SelectionKey connection, final FMPPacket dataPacket) throws FMPException{

        System.out.println("CloudServiceManager - Starting method processNetworkServiceConnectionRequest");
        System.out.println("dataPacket = "+dataPacket);
        System.out.println(" ----------------------------------------------------------  ");

		String sender = getIdentityPublicKey();
		String destination = dataPacket.getSender();
		FMPPacketType type;
		String message;
		String messageHash;
		String signature;

		String[] messagePart = AsymmectricCryptography.decryptMessagePrivateKey(dataPacket.getMessage(), identity.getPrivateKey()).split(FMPPacket.MESSAGE_SEPARATOR);

		NetworkServices networkService = NetworkServices.valueOf(messagePart[0]);

		System.out.println("networkService = "+networkService);
		
		if(networkServicesRegistry.containsKey(networkService)){
			type = FMPPacketType.CONNECTION_ACCEPT_FORWARD;
			message = networkService.toString()
					+ FMPPacket.MESSAGE_SEPARATOR
					+ networkServicesRegistry.get(networkService).getCommunicationChannelAddress().getHost()
					+ FMPPacket.MESSAGE_SEPARATOR
					+ networkServicesRegistry.get(networkService).getCommunicationChannelAddress().getPort()
					+ FMPPacket.MESSAGE_SEPARATOR
					+ messagePart[1];
			
		} else {
			type = FMPPacketType.CONNECTION_DENY;
			message = "NETWORK SERVICE " + networkService + " IS NOT SUPPORTED BY THE CLOUD SERVER";
		}

		System.out.println("message = "+message);

		messageHash = AsymmectricCryptography.encryptMessagePublicKey(message, destination);
		signature = AsymmectricCryptography.createMessageSignature(messageHash, identity.getPrivateKey());
		
		FMPPacket responsePacket = FMPPacketFactory.constructCloudPacket(sender, destination, type, messageHash, signature);

        System.out.println("responsePacket = "+responsePacket);
        System.out.println(" ----------------------------------------------------------  ");

		connection.attach(responsePacket);
		connection.interestOps(SelectionKey.OP_WRITE);
	}
	
	private void processConnectionRegister(final FMPPacket packet) throws FMPException{

        System.out.println("CloudServiceManager - Starting method processConnectionRegister");
        System.out.println("dataPacket = "+packet);
        System.out.println(" ----------------------------------------------------------  ");

		SelectionKey connection = unregisteredConnections.get(packet.getSender());
		registeredConnections.put(packet.getSender(), connection);
		unregisteredConnections.remove(packet.getSender());

		String sender = getIdentityPublicKey();
		String destination = packet.getSender();
		String messageHash = AsymmectricCryptography.encryptMessagePublicKey("REGISTERED", destination);
		FMPPacketType type = FMPPacketType.DATA_TRANSMIT;
		String signature = AsymmectricCryptography.createMessageSignature(messageHash, identity.getPrivateKey());
		
		FMPPacket responsePacket = FMPPacketFactory.constructCloudPacket(sender, destination, type, messageHash, signature);


        System.out.println("responsePacket = "+responsePacket);
        System.out.println(" ----------------------------------------------------------  ");

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
