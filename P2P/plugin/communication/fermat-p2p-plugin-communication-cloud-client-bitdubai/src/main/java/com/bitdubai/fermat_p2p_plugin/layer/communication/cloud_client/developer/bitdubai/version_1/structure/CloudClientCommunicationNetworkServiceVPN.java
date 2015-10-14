/*
 * @#CloudClientCommunicationNetworkServiceVPN.java - 2015
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
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CantConnectToRemoteServiceException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CantSendMessageException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannelAddress;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.ConnectionStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.Message;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.MessagesStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.ServiceToServiceOnlineConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud.exceptions.CloudCommunicationException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket.FMPPacketType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.FMPPacketFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.cloud.CloudFMPConnectionManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServices;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.exceptions.CloudFMPClientStartFailedException;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.exceptions.ConnectionAlreadyRegisteredException;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.exceptions.ConnectionAlreadyRequestedException;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.exceptions.IllegalPacketSenderException;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.exceptions.IllegalPacketSignatureException;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.structure.CloudClientCommunicationNetworkServiceVPN</code> is
 * the representation of the network service vpn connection.
 * <p/>
 *
 * Created by ciencias on 20/01/15.
 * Update by Jorge Gonzales
 * Update by Roberto Requena - (rart3001@gmail.com) on 21/07/15.
 *
 * @version 1.0
 */
public class CloudClientCommunicationNetworkServiceVPN extends CloudFMPConnectionManager implements ServiceToServiceOnlineConnection {

    /**
     * Represent the requested connections cache
     */
	private Map<String, SelectionKey> requestedConnections;

    /**
     * Represent the vpn identity
     */
	private final String vpnIdentityPublicKey;

    /**
     * Represent the peer identity
     */
	private final String peerIdentityPublicKey;

    /**
     * Represent the network service type
     */
	private final NetworkServices networkService;

    /**
     * Represent is the vpn was registered
     */
	private final AtomicBoolean registered;

    /**
     * Represent the pending incoming messages cache
     */
	private final Set<String> pendingIncomingMessages;

    /**
     * Constructor with parameters
     *
     * @param vpnAddress
     * @param executor
     * @param clientPrivateKey
     * @param vpnIdentityPublicKey
     * @param peerIdentityPublicKey
     * @param networkService
     * @throws IllegalArgumentException
     */
	public CloudClientCommunicationNetworkServiceVPN(final CommunicationChannelAddress vpnAddress, final ExecutorService executor, final String clientPrivateKey, final String vpnIdentityPublicKey, final String peerIdentityPublicKey, final NetworkServices networkService) throws IllegalArgumentException {

        super(vpnAddress, executor, new ECCKeyPair(clientPrivateKey, AsymmetricCryptography.derivePublicKey(clientPrivateKey)), CloudFMPConnectionManagerMode.FMP_CLIENT);
		this.vpnIdentityPublicKey = vpnIdentityPublicKey;
		this.peerIdentityPublicKey = peerIdentityPublicKey;
		this.networkService = networkService;
		this.registered = new AtomicBoolean(false);
        requestedConnections = new ConcurrentHashMap<>();
        pendingIncomingMessages = new ConcurrentSkipListSet<>();
    }

    /**
     * (non-Javadoc)
     *
     * @see CloudFMPConnectionManager#handleConnectionAccept(FMPPacket)
     */
	@Override
	public void handleConnectionAccept(final FMPPacket fMPPacketReceive) throws FMPException {

        System.out.println("CloudClientCommunicationNetworkServiceVPN - Starting method handleConnectionAccept");

        if(!vpnIdentityPublicKey.equals(fMPPacketReceive.getSender())) {
            throw constructIllegalPacketSenderException(fMPPacketReceive);
        }

		if(!validatePacketSignature(fMPPacketReceive)){
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
                                                                                              networkService,
                                                                                              identity.getPrivateKey());
        /*
         * Put the packet into PendingOutgoingPacketCache
         */
        putIntoPendingOutgoingPacketCache(responsePacket.getDestination(), responsePacket);

        /*
         * Get the server connection from the requested connection
         */
        SelectionKey serverConnection = requestedConnections.get(vpnIdentityPublicKey);

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
        System.out.println(dataPacket.toString());
	}

    /**
     * (non-Javadoc)
     *
     * @see CloudFMPConnectionManager#handleConnectionDeny(FMPPacket)
     */
	@Override
	public void handleConnectionDeny(final FMPPacket dataPacket) throws FMPException {
        System.out.println(dataPacket.toString());
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

        System.out.println("CloudClientCommunicationNetworkServiceVPN - Starting method handleDataTransmit");

        /**
         * Get the decrypt content of packet
         */
		String message = AsymmetricCryptography.decryptMessagePrivateKey(dataPacket.getMessage(), identity.getPrivateKey());

        /*
         * Validate the message content
         */
		if(message.equals("REGISTERED")) {

            /*
             * Register the vnp connection
             */
            registerVPNConnection();

        }else {

            /*
             * Put the message into the cache
             */
            pendingIncomingMessages.add(message);
        }

	}

    /**
     *
     */
	private void registerVPNConnection() {
		SelectionKey serverConnection = requestedConnections.get(vpnIdentityPublicKey);
		requestedConnections.remove(vpnIdentityPublicKey);
		registeredConnections.put(vpnIdentityPublicKey, serverConnection);
	}

    /**
     * (non-Javadoc)
     *
     * @see CloudFMPConnectionManager#start()
     */
	@Override
	public void start() throws CloudCommunicationException {

        System.out.println("Starting the CloudClientCommunicationNetworkServiceVPN for network service = "+networkService);

        /*
         * Validate is running
         */
		if(running.get()) {

            /*
             * throw CloudFMPClientStartFailedException
             */
            throw new CloudFMPClientStartFailedException(CloudFMPClientStartFailedException.DEFAULT_MESSAGE, null, communicationChannelAddress.toString(), "The FMP Client is already running");
        }

		try{

            /*
             * Open a new selector
             */
            selector = Selector.open();

            /*
             * Open a new channel
             */
            clientChannel = SocketChannel.open();

            /*
             * Configure the blocking
             */
            clientChannel.configureBlocking(false);

            /*
             * Register the channel with the selector to connect
             */
            SelectionKey serverConnection = clientChannel.register(selector, SelectionKey.OP_CONNECT);

            /*
             * Connect the client channel whit the server
             */
            clientChannel.connect(communicationChannelAddress.getSocketAddress());

            System.out.println("clientChannel.connect = " + communicationChannelAddress);
            System.out.println("clientChannel.isConnectionPending() = " + clientChannel.isConnectionPending());

            /*
             * While the connection is pending
             */
            while(clientChannel.isConnectionPending()) {

                /*
                 * try the finish to connect
                 */
                clientChannel.finishConnect();
            }

            System.out.println("clientChannel.isConnected() = "+clientChannel.isConnected());

            /*
             * Configure to run the main thread
             */
            running.set(clientChannel.isConnected());

            /*
             * Cache the server connection on the uregistered list
             */
            unregisteredConnections.put(vpnIdentityPublicKey, serverConnection);

            /*
             * Execute this thread into the pool of thread
             */
            executorService.execute(this);

            /*
             * Request a connection to server
             */
            requestConnectionToServer();


		}catch(IOException ex){

			/*
             * Throw new CloudCommunicationException
             */
            throw wrapFMPException(identity.getPublicKey(), vpnIdentityPublicKey, FMPPacketType.CONNECTION_REQUEST.toString(), identity.getPublicKey(), AsymmetricCryptography.createMessageSignature(identity.getPublicKey(), identity.getPrivateKey()), null);

        }
	}

    /**
     * This method create a new request connection to the server
     *
     * @throws CloudCommunicationException
     */
	private void requestConnectionToServer() throws CloudCommunicationException {

        /*
         * Validate is registered
         */
        if (isRegistered()) {
            throw new ConnectionAlreadyRegisteredException(ConnectionAlreadyRegisteredException.DEFAULT_MESSAGE, null, getClass().toString(), "We've already registered our connection in the Server");
        }

        /*
         * Validate is requested connection are not empty
         */
        if (!requestedConnections.isEmpty()) {
            throw new ConnectionAlreadyRequestedException(ConnectionAlreadyRequestedException.DEFAULT_MESSAGE, null, getClass().toString(), "We've already requested a connection to the FMP Server");
        }

        try {

            /*
             * Create the request packet
             */
            FMPPacket requestPacket = FMPPacketFactory.constructCloudFMPPacketEncryptedAndSinged(identity.getPublicKey(),        //sender
                                                                                                 peerIdentityPublicKey,  //destination
                                                                                                 networkService.toString(),     // message
                                                                                                 FMPPacketType.CONNECTION_REQUEST,
                                                                                                 networkService,
                                                                                                 identity.getPrivateKey());
			/*
             * Put the packet into PendingOutgoingPacketCache
             */
            putIntoPendingOutgoingPacketCache(requestPacket.getDestination(), requestPacket);

            /*
             * Get the connection and remove from the uregistered cache
             */
            SelectionKey serverConnection = unregisteredConnections.remove(vpnIdentityPublicKey);

            /*
             * Cache the server connection on the requested list
             */
            requestedConnections.put(vpnIdentityPublicKey, serverConnection);

            /*
             * Attach the destination of the packet
             */
            serverConnection.attach(requestPacket.getDestination());

			/*
             * Mark the connection to write
             */
            serverConnection.interestOps(SelectionKey.OP_WRITE);

            /*
             * Mark this registered
             */
            registered.set(Boolean.TRUE);

        } catch (FMPException ex) {

			/*
             * Throw new CloudCommunicationException
             */
            throw wrapFMPException(identity.getPublicKey(), vpnIdentityPublicKey, FMPPacketType.CONNECTION_REQUEST.toString(), identity.getPublicKey(), AsymmetricCryptography.createMessageSignature(identity.getPublicKey(), identity.getPrivateKey()), ex);

        }
    }

    /**
     * Get is registered
     *
     * @return boolean
     */
	public boolean isRegistered() {
		return registered.get();
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
        return AsymmetricCryptography.verifyMessageSignature(dataPacket.getSignature(), dataPacket.getMessage(), dataPacket.getSender());
    }

    /**
     * (non-Javadoc)
     *
     * @see ServiceToServiceOnlineConnection#reConnect()
     */
	@Override
	public void reConnect() throws CantConnectToRemoteServiceException {
		try{

            /*
             * Call start again
             */
			start();

		} catch(Exception ex){

            /*
             * Throw new CantConnectToRemoteServiceException
             */
			throw new CantConnectToRemoteServiceException(CantConnectToRemoteServiceException.DEFAULT_MESSAGE, ex, communicationChannelAddress.toString(), "The Reconnection Failed, check the cause");
		}
	}

    /**
     * (non-Javadoc)
     *
     * @see ServiceToServiceOnlineConnection#disconnect()
     */
	@Override
	public void disconnect() {
		try{

            /*
             * Call stop method
             */
			stop();

		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

    /**
     * (non-Javadoc)
     *
     * @see ServiceToServiceOnlineConnection#getStatus()
     */
	@Override
	public ConnectionStatus getStatus() {

        /*
         * Validate if registered
         */
		if(isRegistered()) {

            return ConnectionStatus.CONNECTED;

        } else {

            return ConnectionStatus.DISCONNECTED;
        }
	}

    /**
     * (non-Javadoc)
     *
     * @see ServiceToServiceOnlineConnection#sendMessage(Message)
     */
	@Override
	public void sendMessage(Message message) throws CantSendMessageException {

		try{

            /*
             * Validate is register
             */
            if(!isRegistered()) {
                throw new CloudCommunicationException(CloudCommunicationException.DEFAULT_MESSAGE, null, "", "We haven't registered the connection to the server");
            }

            /*
             * Construct the message packet
             */
            FMPPacket messagePacket = FMPPacketFactory.constructCloudFMPPacketEncryptedAndSinged(identity.getPublicKey(), //sender
                                                                                                 peerIdentityPublicKey,   //destination
                                                                                                 message.toString(),      // message
                                                                                                 FMPPacketType.DATA_TRANSMIT,
                                                                                                 networkService,
                                                                                                 identity.getPrivateKey());
            /*
             * Put the packet into PendingOutgoingPacketCache
             */
            putIntoPendingOutgoingPacketCache(messagePacket.getDestination(), messagePacket);

             /*
             * Get the connection and remove from the uregistered cache
             */
            SelectionKey serverConnection = registeredConnections.remove(vpnIdentityPublicKey);

            /*
             * Attach the destination of the packet
             */
            serverConnection.attach(messagePacket.getDestination());

			/*
             * Mark the connection to write
             */
            serverConnection.interestOps(SelectionKey.OP_WRITE);


		}catch(Exception ex){
			throw new CantSendMessageException(CantSendMessageException.DEFAULT_MESSAGE, ex, "FermatMessage Content:" + message.getTextContent(), "There was an errror sending the message, check the cause");
		}
	}

    /**
     * (non-Javadoc)
     *
     * @see ServiceToServiceOnlineConnection#getUnreadMessagesCount()
     */
	@Override
	public int getUnreadMessagesCount() {

        /*
         * Validate if not null
         */
        if (pendingIncomingMessages != null){

            /*
             * Return the size of the cache
             */
            return pendingIncomingMessages.size();

        }

        /*
         * Empty cache return 0
         */
		return 0;
	}

    /**
     * (non-Javadoc)
     *
     * @see ServiceToServiceOnlineConnection#readNextMessage()
     */
	@Override
	public Message readNextMessage() {

        String messageContent = "";

        /*
         * Validate no empty
         */
        if(!pendingIncomingMessages.isEmpty()) {

            /*
             * Return the next message
             */
            messageContent = pendingIncomingMessages.iterator().next();
        }

        /**
         * Construct a new CloudClientCommunicationMessage
         */
		return new CloudClientCommunicationMessage(messageContent, MessagesStatus.DELIVERED);
	}

    /**
     * (non-Javadoc)
     *
     * @see ServiceToServiceOnlineConnection#clearMessage(Message)
     */
	@Override
	public void clearMessage(Message message) {

        /*
         * Validate is pending messages if no empty and contain the message pass
         * for parameter
         */
		if(pendingIncomingMessages.isEmpty() ||
                !pendingIncomingMessages.contains(message.getTextContent())) {

            throw new IllegalArgumentException("FermatMessage not in the stack");
        }

        /*
         * Remove the message
         */
		pendingIncomingMessages.remove(message.getTextContent());
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
		String context = "Server Public Key: " + vpnIdentityPublicKey;
		context += IllegalPacketSenderException.CONTEXT_CONTENT_SEPARATOR;
		context += "Client Public Key: " + identity.getPublicKey();
		context += IllegalPacketSenderException.CONTEXT_CONTENT_SEPARATOR;
		context += "FermatPacketCommunication Sender: " + packet.getSender();
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
		String context = "Data FermatPacketCommunication Information: " + packet.toString();
		String possibleReason = "There was an improper signature associated with this packet; check if you're using the standard Asymmetric Cryptography Signature method";

		return new IllegalPacketSignatureException(message, null, context, possibleReason);
	}

	/**
	 * Created a instance of the CloudCommunicationException whit the
	 * parameter
	 *
	 * @param sender
	 * @param destination
	 * @param type
	 * @param messageHash
	 * @param signature
	 * @param cause
	 * @return CloudCommunicationException
	 */
	private CloudCommunicationException wrapFMPException(final String sender, final String destination, final String type, final String messageHash, final String signature, final FMPException cause){

        String message = CloudCommunicationException.DEFAULT_MESSAGE;
		String context = "Sender: " + sender;
		context += CloudCommunicationException.CONTEXT_CONTENT_SEPARATOR;
		context += "Destination: " + destination;
		context += CloudCommunicationException.CONTEXT_CONTENT_SEPARATOR;
		context += "Type: " + type;
		context += CloudCommunicationException.CONTEXT_CONTENT_SEPARATOR;
		context += "FermatMessage Hash: " + messageHash;
		context += CloudCommunicationException.CONTEXT_CONTENT_SEPARATOR;
		context += "Signature: " + signature;
		String possibleReason = "The FMP FermatPacketCommunication construction failed, check the cause and the values in the context";

		return new CloudCommunicationException(message, cause, context, possibleReason);
	}
	
}
