/*
 * @#CloudClientCommunicationManager.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.structure;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannelAddress;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud.exceptions.CloudCommunicationException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket.FMPPacketType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.CommunicationChannelAddressFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.FMPPacketFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.cloud.CloudFMPConnectionManager;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServices;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.exceptions.CloudFMPClientStartFailedException;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.exceptions.ConnectionAlreadyRegisteredException;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.exceptions.ConnectionAlreadyRequestedException;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.exceptions.IllegalPacketSenderException;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.exceptions.IllegalPacketSignatureException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;


/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.structure.CloudClientCommunicationManager</code> represent
 * the cloud client communication manager
 * <p/>
 *
 * Created by Jorge Gonzales
 * Update by Roberto Requena - (rart3001@gmail.com) on 09/06/15.
 *
 * @version 1.0
 */
public class CloudClientCommunicationManager extends CloudFMPConnectionManager {

    /**
     * Represent the requested connections cache
     */
	private Map<String, SelectionKey> requestedConnections;

    /**
     * Represent the network service registry
     */
	private Map<NetworkServices, CloudClientCommunicationNetworkServiceConnection> networkServiceRegistry;

    /*
     * Represent the identity public key of the centralize remote server
     */
	private final String identityPublicKeyRemoteServer;

    /**
     * Constructor whit parameters
     *
     * @param serverAddress
     * @param executor
     * @param clientPrivateKey
     * @param identityPublicKeyRemoteServer
     * @throws IllegalArgumentException
     */
	public CloudClientCommunicationManager(final CommunicationChannelAddress serverAddress, final ExecutorService executor, final String clientPrivateKey, final String identityPublicKeyRemoteServer) throws IllegalArgumentException {
		super(serverAddress, executor, clientPrivateKey, AsymmectricCryptography.derivePublicKey(clientPrivateKey), CloudFMPConnectionManagerMode.FMP_CLIENT);
		this.identityPublicKeyRemoteServer = identityPublicKeyRemoteServer;
		networkServiceRegistry = new ConcurrentHashMap<>();
		networkServiceRegistry.clear();
		requestedConnections = new ConcurrentHashMap<>();
	}

    /**
     * (non-Javadoc)
     *
     * @see CloudFMPConnectionManager#handleConnectionAccept(FMPPacket)
     */
	@Override
	public void handleConnectionAccept(final FMPPacket fMPPacketReceive) throws FMPException {

        System.out.println("CloudClientCommunicationManager - Starting method handleConnectionAccept");
        System.out.println("fMPPacketReceive = "+fMPPacketReceive);
        System.out.println(" ----------------------------------------------------------  ");

		if(!identityPublicKeyRemoteServer.equals(fMPPacketReceive.getSender())) {
            throw constructIllegalPacketSenderException(fMPPacketReceive);
        }

		if(!validatePacketSignature(fMPPacketReceive)) {
            throw constructIllegalPacketSignatureException(fMPPacketReceive);
        }

		if(requestedConnections.isEmpty()) {
            return;
        }

        /*
         * Construct a response packet
         */
		FMPPacket responsePacket = FMPPacketFactory.constructCloudFMPPacketEncryptedAndSinged(identity.getPublicKey(),      //sender
                                                                                              fMPPacketReceive.getSender(), //destination
                                                                                              identity.getPublicKey(),      // message
                                                                                              FMPPacketType.CONNECTION_REGISTER,
                                                                                              NetworkServices.UNDEFINED,
                                                                                              identity.getPrivateKey());

        /*
         * Put the packet into PendingOutgoingPacketCache
         */
		putIntoPendingOutgoingPacketCache(responsePacket.getDestination(), responsePacket);

        /*
         * Get the server connection from the requested connection
         */
        SelectionKey serverConnection = requestedConnections.get(fMPPacketReceive.getSender());

         /*
         * Attach the destination of the packet
         */
        serverConnection.attach(responsePacket.getDestination());

        /*
         * Mark the connection to write
         */
		serverConnection.interestOps(SelectionKey.OP_WRITE);
	}

    /**
     * (non-Javadoc)
     *
     * @see CloudFMPConnectionManager#handleConnectionAcceptForward(FMPPacket)
     */
	@Override
	public void handleConnectionAcceptForward(final FMPPacket dataPacket) throws FMPException {

		System.out.println("CloudClientCommunicationManager - Starting method handleConnectionAcceptForward");
        System.out.println("dataPacket = "+dataPacket);
        System.out.println(" ----------------------------------------------------------  ");

		try{

            /*
             * Get the network service type
             */
            NetworkServices networkService = dataPacket.getNetworkServices();

            /*
             * Validate if is already register
             */
            if(networkServiceRegistry.containsKey(networkService)) {
                return;
            }

            /*
             * Decrypt the message
             */
            String decryptedMessage = AsymmectricCryptography.decryptMessagePrivateKey(dataPacket.getMessage(), identity.getPrivateKey());

            /*
             * Get the JsonObject structure from the message
             */
            Gson gson = new Gson();
            JsonObject messageReceived = gson.fromJson(decryptedMessage, JsonObject.class);

            /*
             * Construct the CommunicationChannelAddress
             */
            CommunicationChannelAddress networkServiceServerAddress = CommunicationChannelAddressFactory.constructCloudAddress(messageReceived.get("host").toString(),
                                                                                                                               new Integer(messageReceived.get("port").toString()));

            /*
             * Get the identity of the cloud network service register
             */
            String identityCloudNetworkServiceManager = messageReceived.get("identityCloudNetworkServiceManager").toString();

            /*
             * Get the identity of the network service register
             */
		    String identityPublicKeyNetworkService = messageReceived.get("identityRemoteNetworkService").toString();

            /*
             * Construct a new CloudClientCommunicationNetworkServiceConnection
             */
		    CloudClientCommunicationNetworkServiceConnection networkServiceClient = new CloudClientCommunicationNetworkServiceConnection(networkServiceServerAddress,
                                                                                                                                         executorService,
                                                                                                                                         identityCloudNetworkServiceManager,
                                                                                                                                         identityPublicKeyNetworkService,
                                                                                                                                         networkService);
            /*
             * Star the new  CloudClientCommunicationNetworkServiceConnection
             */
			networkServiceClient.start();

            /*
             * Put into the network service cache
             */
            networkServiceRegistry.put(networkService, networkServiceClient);

		}catch(CloudCommunicationException ex){
			ex.printStackTrace();
		}

	}

    /**
     * (non-Javadoc)
     *
     * @see CloudFMPConnectionManager#handleConnectionDeny(FMPPacket)
     */
	@Override
	public void handleConnectionDeny(final FMPPacket dataPacket) throws FMPException {
		System.out.println(AsymmectricCryptography.decryptMessagePrivateKey(dataPacket.getMessage(), identity.getPrivateKey()));
	}

    /**
     * (non-Javadoc)
     *
     * @see CloudFMPConnectionManager#handleConnectionDeregister(FMPPacket)
     */
	@Override
	public void handleConnectionDeregister(final FMPPacket dataPacket) throws FMPException {
		System.out.println(dataPacket.toString());
	}

    /**
     * (non-Javadoc)
     *
     * @see CloudFMPConnectionManager#handleConnectionEnd(FMPPacket)
     */
	@Override
	public void handleConnectionEnd(final FMPPacket dataPacket) throws FMPException {
		System.out.println(dataPacket.toString());
	}

    /**
     * (non-Javadoc)
     *
     * @see CloudFMPConnectionManager#handleConnectionRegister(FMPPacket)
     */
	@Override
	public void handleConnectionRegister(final FMPPacket dataPacket) throws FMPException {
		System.out.println(dataPacket.toString());
	}

    /**
     * (non-Javadoc)
     *
     * @see CloudFMPConnectionManager#handleConnectionRequest(FMPPacket)
     */
	@Override
	public void handleConnectionRequest(final FMPPacket dataPacket) throws FMPException {
		System.out.println(dataPacket.toString());
	}

    /**
     * (non-Javadoc)
     *
     * @see CloudFMPConnectionManager#handleDataTransmit(FMPPacket)
     */
	@Override
	public void handleDataTransmit(final FMPPacket dataPacket) throws FMPException {

        System.out.println("CloudClientCommunicationManager - Starting method handleDataTransmit");
		System.out.println("dataPacket = "+dataPacket);

		String message = AsymmectricCryptography.decryptMessagePrivateKey(dataPacket.getMessage(), identity.getPrivateKey());

        System.out.println(" message = "+message);
        System.out.println(" ----------------------------------------------------------  ");

		if(requestedConnections.isEmpty() ||
                registeredConnections.containsKey(dataPacket.getSender()) ||
                    !message.equals("REGISTERED")) {
            return;
        }

		SelectionKey serverConnection = requestedConnections.get(dataPacket.getSender());
		requestedConnections.remove(dataPacket.getSender());
		registeredConnections.put(dataPacket.getSender(), serverConnection);
	}

    /**
     * (non-Javadoc)
     *
     * @see CloudFMPConnectionManager#start()
     */
	@Override
	public void start() throws CloudCommunicationException {

		System.out.println("Starting the CloudClientCommunicationManager for this device");


		if(running.get())
			throw new CloudFMPClientStartFailedException(CloudFMPClientStartFailedException.DEFAULT_MESSAGE, null, communicationChannelAddress.toString(), "The FMP Client is already running");
		try{
				selector = Selector.open();
				clientChannel = SocketChannel.open();
				clientChannel.configureBlocking(false);
				SelectionKey serverConnection = clientChannel.register(selector, SelectionKey.OP_CONNECT);

			    clientChannel.connect(communicationChannelAddress.getSocketAddress());

			    System.out.println("clientChannel.connect = "+communicationChannelAddress);

				while(clientChannel.isConnectionPending()) {
                    clientChannel.finishConnect();
                }

                System.out.println("clientChannel.isConnectionPending() = "+clientChannel.isConnectionPending());
                System.out.println("clientChannel.isConnected() = "+clientChannel.isConnected());
				running.set(clientChannel.isConnected());
				unregisteredConnections.put(identityPublicKeyRemoteServer, serverConnection);
				executorService.execute(this);

			} catch(IOException ex){
				System.out.println("ex = "+ex.getMessage());
			    throw wrapNIOSocketIOException(ex);
		    }
	}

    /**
     *
     * @throws CloudCommunicationException
     */
	public void requestConnectionToServer() throws CloudCommunicationException {

		if(isRegistered())
			throw new ConnectionAlreadyRegisteredException(ConnectionAlreadyRegisteredException.DEFAULT_MESSAGE, null, getClass().toString(), "We've already registered our connection in the Server");
		if(!requestedConnections.isEmpty())
			throw new ConnectionAlreadyRequestedException(ConnectionAlreadyRequestedException.DEFAULT_MESSAGE, null, getClass().toString(), "We've already requested a connection to the FMP Server");

		String sender = identity.getPublicKey();
		final String destination = identityPublicKeyRemoteServer;
		FMPPacketType type = FMPPacketType.CONNECTION_REQUEST;
		String messageHash = identity.getPublicKey();
		String signature = AsymmectricCryptography.createMessageSignature(messageHash, identity.getPrivateKey());

		try{

            FMPPacket responsePacket = FMPPacketFactory.constructCloudFMPPacketEncryptedAndSinged(identity.getPublicKey(),         //sender
                                                                                                  destination,                 //destination
                                                                                                  identity.getPublicKey(),         // message
                                                                                                  FMPPacketType.CONNECTION_REQUEST,
                                                                                                  NetworkServices.UNDEFINED,
                                                                                                  identity.getPrivateKey());

			putIntoPendingOutgoingPacketCache(responsePacket.getDestination(), responsePacket);


			SelectionKey serverConnection = unregisteredConnections.remove(identityPublicKeyRemoteServer);
			serverConnection.attach(responsePacket.getDestination());
			requestedConnections.put(identityPublicKeyRemoteServer, serverConnection);
            serverConnection.interestOps(SelectionKey.OP_WRITE);

		} catch(FMPException ex){
			throw wrapFMPException(sender, destination, type.toString(), messageHash, signature, ex);
		}
	}

    /**
     *
     * @param networkService
     * @param networkServicePublicKey
     * @throws CloudCommunicationException
     */
	public void registerNetworkService(final NetworkServices networkService, String networkServicePublicKey) throws CloudCommunicationException {
		String sender =  identity.getPublicKey();
		String destination = identityPublicKeyRemoteServer;
		FMPPacketType type = FMPPacketType.CONNECTION_REQUEST;

        String message = networkService.toString() + "," + networkServicePublicKey;

		String messageHash = AsymmectricCryptography.encryptMessagePublicKey(message, identityPublicKeyRemoteServer);
		String signature = AsymmectricCryptography.createMessageSignature(messageHash, identity.getPrivateKey());
		try{

            FMPPacket responsePacket = FMPPacketFactory.constructCloudFMPPacketEncryptedAndSinged(identity.getPublicKey(),         //sender
                    identityPublicKeyRemoteServer,                 //destination
                    message,                         // message
                    FMPPacketType.CONNECTION_REQUEST,
                    networkService,
                    identity.getPrivateKey());
            putIntoPendingOutgoingPacketCache(responsePacket.getDestination(), responsePacket);
			SelectionKey serverConnection = registeredConnections.get(identityPublicKeyRemoteServer);
            serverConnection.attach(responsePacket.getDestination());
			serverConnection.interestOps(SelectionKey.OP_WRITE);

		} catch(FMPException ex){
			throw wrapFMPException(sender, destination, type.toString(), messageHash, signature, ex);
		}
	}

    /**
     * Return the CloudClientCommunicationNetworkServiceConnection for the network services passed by
     * parameter
     *
     * @param networkService
     * @return CloudClientCommunicationNetworkServiceConnection
     * @throws CloudCommunicationException
     */
	public CloudClientCommunicationNetworkServiceConnection getNetworkServiceClient(final NetworkServices networkService) throws CloudCommunicationException {
		return networkServiceRegistry.get(networkService);
	}

    /**
     * Return is register
     *
     * @return boolean
     */
	public boolean isRegistered() {
		return registeredConnections.containsKey(identityPublicKeyRemoteServer);
	}

    /**
     * Method that validate a packet signature are valid
     *
     * @param dataPacket
     * @return boolean
     */
	private boolean validatePacketSignature(final FMPPacket dataPacket){

        /*
         * Validate the signature
         */
		return AsymmectricCryptography.verifyMessageSignature(dataPacket.getSignature(), dataPacket.getMessage(), dataPacket.getSender());
	}

    /**
     * Created a instance of the IllegalPacketSenderException whit the
     * parameter
     *
     * @param packet
     * @return IllegalPacketSenderException
     */
	private IllegalPacketSenderException constructIllegalPacketSenderException(final FMPPacket packet){
		String message = IllegalPacketSenderException.DEFAULT_MESSAGE;
		String context = "Server Public Key: " + identityPublicKeyRemoteServer;
		context += IllegalPacketSenderException.CONTEXT_CONTENT_SEPARATOR;
		context += "Client Public Key: " + identity.getPublicKey();
		context += IllegalPacketSenderException.CONTEXT_CONTENT_SEPARATOR;
		context += "Packet Sender: " + packet.getSender();
		String possibleReason = "This is a problem of the flow of the packets, this might be accidental or some echo loop.";
		possibleReason += "This can also be an unexpected attack from an unexpected sender.";
		return new IllegalPacketSenderException(message, null, context, possibleReason);
	}

    /**
     * Created a instance of the IllegalPacketSignatureException whit the
     * parameter
     *
     * @param packet
     * @return IllegalPacketSignatureException
     */
	private IllegalPacketSignatureException constructIllegalPacketSignatureException(final FMPPacket packet){

        String message = IllegalPacketSignatureException.DEFAULT_MESSAGE;
		String context = "Data Packet Information: " + packet.toString();
		String possibleReason = "There was an improper signature associated with this packet; check if you're using the standard Asymmetric Cryptography Signature method";

		return new IllegalPacketSignatureException(message, null, context, possibleReason);
	}

	private CloudCommunicationException wrapFMPException(final String sender, final String destination, final String type, final String messageHash, final String signature, final FMPException cause){

        String message = CloudCommunicationException.DEFAULT_MESSAGE;
		String context = "Sender: " + sender;
		context += CloudCommunicationException.CONTEXT_CONTENT_SEPARATOR;
		context += "Destination: " + destination;
		context += CloudCommunicationException.CONTEXT_CONTENT_SEPARATOR;
		context += "Type: " + type;
		context += CloudCommunicationException.CONTEXT_CONTENT_SEPARATOR;
		context += "Message Hash: " + messageHash;
		context += CloudCommunicationException.CONTEXT_CONTENT_SEPARATOR;
		context += "Signature: " + signature;

		String possibleReason = "The FMP Packet construction failed, check the cause and the values in the context";

		return new CloudCommunicationException(message, cause, context, possibleReason);
	}

	
}
