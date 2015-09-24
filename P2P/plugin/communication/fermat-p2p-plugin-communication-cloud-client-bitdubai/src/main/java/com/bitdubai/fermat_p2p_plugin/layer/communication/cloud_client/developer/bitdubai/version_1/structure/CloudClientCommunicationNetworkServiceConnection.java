/*
 * @#CloudClientCommunicationNetworkServiceConnection.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.structure;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
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
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.exceptions.VPNInitializationException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.structure.CloudClientCommunicationNetworkServiceConnection</code> is
 * the responsible to manage the network service connection.
 * <p/>
 *
 * Created by ciencias on 20/01/15.
 * Update by Jorge Gonzales
 * Update by Roberto Requena - (rart3001@gmail.com) on 21/07/15.
 *
 * @version 1.0
 */
public class CloudClientCommunicationNetworkServiceConnection extends CloudFMPConnectionManager {

    /**
     * Represent the requested connections cache
     */
	private final Map<String, SelectionKey> requestedConnections;

    /*
     * Represent the identity public key of the centralize remote server
     */
	private final String identityPublicKeyRemoteServer;

    /*
     * Represent the identity public key of the network service registered with this client
     */
    private final String identityPublicKeyNetworkServiceRegistered;

    /**
     * Represent the network service type
     */
	private final NetworkServices networkService;

    /**
     * Represent the active vpn registry cache
     */
	private final Map<String, CloudClientCommunicationNetworkServiceVPN> activeVPNRegistry;

    /**
     * Represent the pending vpn request
     */
	private final Map<String, String> pendingVPNRequests;

    /**
     * Constructor with parameters
     *
     * @param serverAddress
     * @param executor
     * @param clientPrivateKey
     * @param identityPublicKeyRemoteServer
     * @param networkService
     * @throws IllegalArgumentException
     */
	public CloudClientCommunicationNetworkServiceConnection(final CommunicationChannelAddress serverAddress, final ExecutorService executor, final String clientPrivateKey, final String identityPublicKeyRemoteServer, final String identityPublicKeyNetworkServiceRegistered, final NetworkServices networkService) throws IllegalArgumentException {

        super(serverAddress, executor, new ECCKeyPair(clientPrivateKey, AsymmectricCryptography.derivePublicKey(clientPrivateKey)), CloudFMPConnectionManagerMode.FMP_CLIENT);
		this.identityPublicKeyRemoteServer = identityPublicKeyRemoteServer;
        this.identityPublicKeyNetworkServiceRegistered = identityPublicKeyNetworkServiceRegistered;
		this.networkService = networkService;
        this.requestedConnections = new ConcurrentHashMap<>();
        this.activeVPNRegistry = new ConcurrentHashMap<>();
        this.pendingVPNRequests = new ConcurrentHashMap<>();
    }

    /**
     * (non-Javadoc)
     *
     * @see CloudFMPConnectionManager#handleConnectionAccept(FMPPacket)
     */
    @Override
    public void handleConnectionAccept(final FMPPacket fMPPacketReceive) throws FMPException {

        System.out.println("CloudClientCommunicationNetworkServiceConnection - Starting method handleConnectionAccept");

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
                                                                                              networkService,
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

        CommunicationChannelAddress vpnAddress = null;
        String vpnPublicKey = null;

		try{

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
            vpnAddress = CommunicationChannelAddressFactory.constructCloudAddress(messageReceived.get("host").toString(), new Integer(messageReceived.get("port").toString()));

            /*
             * Get the vpnPublicKey
             */
            vpnPublicKey = messageReceived.get("vpnPublicKey").toString();

            /*
             * Construct the CloudClientCommunicationNetworkServiceVPN
             */
			CloudClientCommunicationNetworkServiceVPN vpnClient = new CloudClientCommunicationNetworkServiceVPN(vpnAddress, Executors.newCachedThreadPool(), identity.getPrivateKey(), vpnPublicKey, dataPacket.getSender(), networkService);
			vpnClient.start();

            /*
             * Put into the vpn registry cache
             */
			activeVPNRegistry.put(dataPacket.getSender(), vpnClient);

            /*
             * Remove from vpn request cache
             */
			pendingVPNRequests.remove(dataPacket.getSender());


		}catch(CloudCommunicationException ex){

            String message = VPNInitializationException.DEFAULT_MESSAGE;
			FermatException cause = ex;
			String context = "VPN Address Info: " + vpnAddress.toString();
			context += VPNInitializationException.CONTEXT_CONTENT_SEPARATOR;
			context += "VPN Public Key: " + vpnPublicKey;
			context += VPNInitializationException.CONTEXT_CONTENT_SEPARATOR;
			context += "Network Service Public Key: " + identity.getPublicKey();
			String possibleReason = "The VPN information can be wrong or the Cloud Communication Server might be down.";

			throw new VPNInitializationException(message, cause, context, possibleReason);
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

        /*
         * Decrypt the message
         */
		String decryptedMessage = AsymmectricCryptography.decryptMessagePrivateKey(dataPacket.getMessage(), identity.getPrivateKey());

        /*
         * put into vpn request
         */
		pendingVPNRequests.put(dataPacket.getSender(), decryptedMessage);

        /*
         * Create a response packet
         */
        FMPPacket responsePacket = FMPPacketFactory.constructCloudFMPPacketEncryptedAndSinged(identity.getPublicKey(),        //sender
                                                                                              dataPacket.getSender(),                           //destination
                                                                                              pendingVPNRequests.get(dataPacket.getSender()),   // message
                                                                                              FMPPacketType.CONNECTION_ACCEPT,
                                                                                              NetworkServices.UNDEFINED,
                                                                                              identity.getPrivateKey());

		/*
         * Put the packet into PendingOutgoingPacketCache
         */
        putIntoPendingOutgoingPacketCache(responsePacket.getDestination(), responsePacket);


        /*
         * Get the connection the registered
         */
        SelectionKey connection = registeredConnections.get(identityPublicKeyRemoteServer);

        /*
         * Attach the destination of the packet
         */
        connection.attach(responsePacket.getDestination());

        /*
         * Mark the connection to write
         */
        connection.interestOps(SelectionKey.OP_WRITE);

	}

    /**
     * (non-Javadoc)
     *
     * @see CloudFMPConnectionManager#handleDataTransmit(FMPPacket)
     */
	@Override
	public void handleDataTransmit(final FMPPacket dataPacket) throws FMPException {

        System.out.println("CloudClientCommunicationNetworkServiceConnection - Starting method handleDataTransmit");

        /*
         * Get the content of the message
         */
        String message = AsymmectricCryptography.decryptMessagePrivateKey(dataPacket.getMessage(), identity.getPrivateKey());


        /*
         * If requested connection is empty or register connection contains the connecction
         * or message is equal to register do nothing
         */
        if(requestedConnections.isEmpty() ||
                registeredConnections.containsKey(dataPacket.getSender()) ||
                !message.equals("REGISTERED")) {
            return;
        }

        /*
         * Remove the connection from requested connections and put into the register connections
         */
		SelectionKey serverConnection = requestedConnections.remove(dataPacket.getSender());
		registeredConnections.put(dataPacket.getSender(), serverConnection);

    }

    /**
     * (non-Javadoc)
     *
     * @see CloudFMPConnectionManager#start()
     */
	@Override
	public void start() throws CloudCommunicationException {

        System.out.println("Starting the CloudClientCommunicationNetworkServiceConnection for network service = "+networkService);

        /*
         * Validate is running
         */
		if(running.get()) {

            /*
             * throw CloudFMPClientStartFailedException
             */
            throw new CloudFMPClientStartFailedException(CloudFMPClientStartFailedException.DEFAULT_MESSAGE, null, getClass().toString(), "The Cloud FMP Client is already running");
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

            System.out.println("clientChannel.connect = " + communicationChannelAddress);

            /*
             * Connect the client channel whit the server
             */
			clientChannel.connect(communicationChannelAddress.getSocketAddress());

            System.out.println("clientChannel.isConnectionPending() = "+clientChannel.isConnectionPending());

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
            unregisteredConnections.put(identityPublicKeyRemoteServer, serverConnection);

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
            throw wrapFMPException(identity.getPublicKey(), identityPublicKeyRemoteServer, FMPPacketType.CONNECTION_REQUEST.toString(), identity.getPublicKey(), AsymmectricCryptography.createMessageSignature(identity.getPublicKey(), identity.getPrivateKey()), null );

        }
	}

    /**
     * Metho thar request a new vpn connection
     *
     * @param peer
     * @throws CloudCommunicationException
     */
	public void requestVPNConnection(final String peer) throws CloudCommunicationException {

        /*
         * Validate is registered
         */
		if(!isRegistered()) {
            throw new CloudCommunicationException(CloudCommunicationException.DEFAULT_MESSAGE, null, communicationChannelAddress.toString(), "Network Service Not yet registered");
        }

		try{

            /**
             * Construct the request packet
             */
            FMPPacket requestPacket = FMPPacketFactory.constructCloudFMPPacketEncryptedAndSinged(identity.getPublicKey(),         //sender
                                                                                                  peer,          //destination
                                                                                                  "VPN",         // message
                                                                                                  FMPPacketType.CONNECTION_REQUEST,
                                                                                                  networkService,
                                                                                                  identity.getPrivateKey());

			/*
             * Put the packet into PendingOutgoingPacketCache
             */
            putIntoPendingOutgoingPacketCache(requestPacket.getDestination(), requestPacket);

            /*
             * Get the connection the registered
             */
            SelectionKey connection = registeredConnections.get(identityPublicKeyRemoteServer);

            /*
             * Attach the destination of the packet
             */
            connection.attach(requestPacket.getDestination());

            /*
             * Mark the connection to write
             */
            connection.interestOps(SelectionKey.OP_WRITE);


		}catch(FMPException ex){

			/*
             * Throw new CloudCommunicationException
             */
            throw wrapFMPException(identity.getPublicKey(), identityPublicKeyRemoteServer, FMPPacketType.CONNECTION_REQUEST.toString(), identity.getPublicKey(), AsymmectricCryptography.createMessageSignature(identity.getPublicKey(), identity.getPrivateKey()), ex);

        }
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
     * Get the pending vpn request
     * @return String
     */
	public String getPendingVPNRequest(){
		return pendingVPNRequests.keySet().iterator().next();
	}

    /**
     * Get the active vpn
     *
     * @param peer
     * @return CloudClientCommunicationNetworkServiceVPN
     */
	public CloudClientCommunicationNetworkServiceVPN getActiveVPN(final String peer){
		return activeVPNRegistry.get(peer);
	}

    /**
     * Get pending vpn request
     *
     * @return Collection<String>
     */
	public Collection<String> getPendingVPNRequests() {
		return pendingVPNRequests.keySet();
	}

    /**
     * Get active vpn indentifiers
     *
     * @return Collection<String>
     */
	public Collection<String> getActiveVPNIdentifiers() {
		return activeVPNRegistry.keySet();
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
		if(isRegistered()){
			throw new ConnectionAlreadyRegisteredException(ConnectionAlreadyRegisteredException.DEFAULT_MESSAGE, null, "", "The connection is already registered to the Server");
        }

        /*
         * Validate is requested connection are not empty
         */
		if(!requestedConnections.isEmpty()) {
            throw new ConnectionAlreadyRequestedException(ConnectionAlreadyRequestedException.DEFAULT_MESSAGE, null, "", "We've already requested a connection to the FMP Server");
        }

		try{

            /*
             * Create the request packet
             */
            FMPPacket requestPacket = FMPPacketFactory.constructCloudFMPPacketEncryptedAndSinged(identity.getPublicKey(),        //sender
                                                                                                identityPublicKeyRemoteServer,  //destination
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
			SelectionKey serverConnection = unregisteredConnections.remove(identityPublicKeyRemoteServer);

            /*
             * Cache the server connection on the requested list
             */
            requestedConnections.put(identityPublicKeyRemoteServer, serverConnection);

            /*
             * Mark the connection to write
             */
            serverConnection.interestOps(SelectionKey.OP_WRITE);

		} catch(FMPException ex){

			/*
             * Throw new CloudCommunicationException
             */
            throw wrapFMPException(identity.getPublicKey(), identityPublicKeyRemoteServer, FMPPacketType.CONNECTION_REQUEST.toString(), identity.getPublicKey(), AsymmectricCryptography.createMessageSignature(identity.getPublicKey(), identity.getPrivateKey()), ex);

        }
	}

    /**
     * This method accept a pending vpn request
     *
     * @param peer
     * @throws FMPException
     */
    public void acceptPendingVPNRequest(final String peer) throws FMPException {

        /*
         * Create the request packet
         */
        FMPPacket responsePacket = FMPPacketFactory.constructCloudFMPPacketEncryptedAndSinged(identity.getPublicKey(),          //sender
                                                                                                peer,                           //destination
                                                                                                pendingVPNRequests.get(peer),   // message
                                                                                                FMPPacketType.CONNECTION_ACCEPT,
                                                                                                networkService,
                                                                                                identity.getPrivateKey());
        /*
         * Put the packet into PendingOutgoingPacketCache
         */
        putIntoPendingOutgoingPacketCache(responsePacket.getDestination(), responsePacket);
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

    /**
     * Get the identity for the network service that represent
     * this client
     *
     * @return String
     */
    public String getIdentityPublicKeyNetworkServiceRegistered() {
        return identityPublicKeyNetworkServiceRegistered;
    }
}
