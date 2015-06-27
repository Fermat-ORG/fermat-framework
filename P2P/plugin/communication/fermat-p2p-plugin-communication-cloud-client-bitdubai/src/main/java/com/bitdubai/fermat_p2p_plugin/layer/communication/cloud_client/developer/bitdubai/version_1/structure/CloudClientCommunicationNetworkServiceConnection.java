package com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.structure;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.developer.Developer;
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

public class CloudClientCommunicationNetworkServiceConnection extends CloudFMPConnectionManager {
	
	private static final String CHARSET_NAME = "UTF-8";
	
	private final Queue<FMPPacket> pendingMessages = new ConcurrentLinkedQueue<FMPPacket>();
	private final Map<String, SelectionKey> requestedConnections = new ConcurrentHashMap<String, SelectionKey>();
	
	private final String serverPublicKey;
	private final NetworkServices networkService;
	
	private final Map<String, CloudClientCommunicationNetworkServiceVPN> activeVPNRegistry = new ConcurrentHashMap<String, CloudClientCommunicationNetworkServiceVPN>();
	private final Map<String, String> pendingVPNRequests = new ConcurrentHashMap<String, String>();

	public CloudClientCommunicationNetworkServiceConnection(final CommunicationChannelAddress serverAddress, final ExecutorService executor, final String clientPrivateKey, final String serverPublicKey, final NetworkServices networkService) throws IllegalArgumentException {
		super(serverAddress, executor, clientPrivateKey, AsymmectricCryptography.derivePublicKey(clientPrivateKey), CloudFMPConnectionManagerMode.FMP_CLIENT);
		this.serverPublicKey = serverPublicKey;
		this.networkService = networkService;
	}
	
	@Override
	public synchronized void writeToKey(final SelectionKey key) throws CloudCommunicationException {
		try{
			SocketChannel channel = (SocketChannel) key.channel();
			FMPPacket dataPacket = pendingMessages.remove();
			byte[] data = dataPacket.toString().getBytes(CHARSET_NAME);
			channel.write(ByteBuffer.wrap(data));
			if(!pendingMessages.isEmpty())
				writeToKey(key);
			key.interestOps(SelectionKey.OP_READ);
		}catch(NoSuchElementException ex){
			key.interestOps(SelectionKey.OP_READ);
		}catch(IOException ex){
			throw wrapNIOSocketIOException(ex);
		}
	}
	
	@Override
	public void handleConnectionAccept(final FMPPacket dataPacket) throws FMPException {
		if(!serverPublicKey.equals(dataPacket.getSender()))
			throw constructIllegalPacketSenderException(dataPacket);
		if(!validatePacketSignature(dataPacket))
			throw constructIllegalPacketSignatureException(dataPacket);
		if(requestedConnections.isEmpty())
			return;
		String serverAddress = dataPacket.getSender();
		SelectionKey serverConnection = requestedConnections.get(serverAddress);
		
		String sender = eccPublicKey;
		String destination = dataPacket.getSender();
		FMPPacketType type = FMPPacketType.CONNECTION_REGISTER;
		String message = eccPublicKey;
		String signature = AsymmectricCryptography.createMessageSignature(message, eccPrivateKey);
			
		FMPPacket packet = FMPPacketFactory.constructCloudPacket(sender, destination, type, message, signature);
		pendingMessages.add(packet);
		serverConnection.interestOps(SelectionKey.OP_WRITE);
	}

	@Override
	public void handleConnectionAcceptForward(final FMPPacket dataPacket) throws FMPException {
		String decryptedMessage = AsymmectricCryptography.decryptMessagePrivateKey(dataPacket.getMessage(), eccPrivateKey);
		String[] messageComponents = decryptedMessage.split(FMPPacket.MESSAGE_SEPARATOR);
		String host = messageComponents[0];
		Integer port = Integer.valueOf(messageComponents[1]);
		String vpnPublicKey = messageComponents[2];
		CommunicationChannelAddress vpnAddress = CommunicationChannelAddressFactory.constructCloudAddress(host, port);
		try{
			CloudClientCommunicationNetworkServiceVPN vpnClient = new CloudClientCommunicationNetworkServiceVPN(vpnAddress, Executors.newCachedThreadPool(), eccPrivateKey, vpnPublicKey, dataPacket.getSender(), networkService);
			vpnClient.start();
			activeVPNRegistry.put(dataPacket.getSender(), vpnClient);
			pendingVPNRequests.remove(dataPacket.getSender());
		}catch(CloudCommunicationException ex){
			String message = VPNInitializationException.DEFAULT_MESSAGE;
			FermatException cause = ex;
			String context = new String();
			context += "VPN Address Info: " + vpnAddress.toString();
			context += VPNInitializationException.CONTEXT_CONTENT_SEPARATOR;
			context += "VPN Public Key: " + vpnPublicKey;
			context += VPNInitializationException.CONTEXT_CONTENT_SEPARATOR;
			context += "Network Service Public Key: " + eccPublicKey;
			String possibleReason = "The VPN information can be wrong or the Cloud Communication Server might be down.";
			throw new VPNInitializationException(message, cause, context, possibleReason);
		}
	}

	@Override
	public void handleConnectionDeny(final FMPPacket dataPacket) throws FMPException {
		System.out.println(AsymmectricCryptography.decryptMessagePrivateKey(dataPacket.getMessage(), eccPrivateKey));
	}

	@Override
	public void handleConnectionDeregister(final FMPPacket dataPacket) throws FMPException {
		System.out.println(dataPacket.toString());
	}

	@Override
	public void handleConnectionEnd(final FMPPacket dataPacket) throws FMPException {
		System.out.println(dataPacket.toString());
	}

	@Override
	public void handleConnectionRegister(final FMPPacket dataPacket) throws FMPException {
		System.out.println(dataPacket.toString());
	}

	@Override
	public void handleConnectionRequest(final FMPPacket dataPacket) throws FMPException {
		String decryptedMessage = AsymmectricCryptography.decryptMessagePrivateKey(dataPacket.getMessage(), eccPrivateKey);
		pendingVPNRequests.put(dataPacket.getSender(), decryptedMessage);
		acceptPendingVPNRequest(dataPacket.getSender());
	}

	@Override
	public void handleDataTransmit(final FMPPacket dataPacket) throws FMPException {
		String message = AsymmectricCryptography.decryptMessagePrivateKey(dataPacket.getMessage(), eccPrivateKey);
		if(requestedConnections.isEmpty() || registeredConnections.containsKey(dataPacket.getSender()))
			return;
		if(!message.equals("REGISTERED"))
			return;
		SelectionKey serverConnection = requestedConnections.get(dataPacket.getSender());
		requestedConnections.remove(dataPacket.getSender());
		registeredConnections.put(dataPacket.getSender(), serverConnection);
	}
	
	@Override
	public void start() throws CloudCommunicationException {
		if(running.get())
			throw new CloudFMPClientStartFailedException(CloudFMPClientStartFailedException.DEFAULT_MESSAGE, null, getClass().toString(), "The Cloud FMP Client is already running");
		try{
			selector = Selector.open();
			clientChannel = SocketChannel.open();
			clientChannel.configureBlocking(false);
			SelectionKey serverConnection = clientChannel.register(selector, SelectionKey.OP_CONNECT);
			clientChannel.connect(address.getSocketAddress());
			if(clientChannel.isConnectionPending())
				clientChannel.finishConnect();
			running.set(clientChannel.isConnected());
			unregisteredConnections.put(serverPublicKey, serverConnection);
			executor.execute(this);
			requestConnectionToServer();
		}catch(IOException ex){
			throw wrapNIOSocketIOException(ex);
		}
	}
	

	
	public void requestVPNConnection(final String peer) throws CloudCommunicationException {
		if(!isRegistered())
			throw new CloudCommunicationException(CloudCommunicationException.DEFAULT_MESSAGE, null, address.toString(), "Network Service Not yet registered");

		String sender = eccPublicKey;
		String destination = peer;
		FMPPacketType type = FMPPacketType.CONNECTION_REQUEST;
		String messageHash = AsymmectricCryptography.encryptMessagePublicKey("VPN", serverPublicKey);
		String signature = AsymmectricCryptography.createMessageSignature(messageHash, eccPrivateKey);
		try{
			FMPPacket packet = FMPPacketFactory.constructCloudPacket(sender, destination, type, messageHash, signature);
			pendingMessages.add(packet);
			registeredConnections.get(serverPublicKey).interestOps(SelectionKey.OP_WRITE);
		}catch(FMPException ex){
			throw wrapFMPException(sender, destination, type.toString(), messageHash, signature, ex);
		}
	}

	public void acceptPendingVPNRequest(final String peer) throws FMPException {
		String sender = eccPublicKey;
		String destination = peer;
		FMPPacketType type = FMPPacketType.CONNECTION_ACCEPT;
		String messageHash = AsymmectricCryptography.encryptMessagePublicKey(pendingVPNRequests.get(peer), serverPublicKey);
		String signature = AsymmectricCryptography.createMessageSignature(messageHash, eccPrivateKey);
		FMPPacket packet = FMPPacketFactory.constructCloudPacket(sender, destination, type, messageHash, signature);
		sendPacketToRegisteredServer(packet);
	}

	public boolean isRegistered() {
		return registeredConnections.containsKey(serverPublicKey);
	}

	public String getPendingVPNRequest(){
		return pendingVPNRequests.keySet().iterator().next();
	}
	
	public CloudClientCommunicationNetworkServiceVPN getActiveVPN(final String peer){
		return activeVPNRegistry.get(peer);
	}

	public Collection<String> getPendingVPNRequests() {
		return pendingVPNRequests.keySet();
	}

	public Collection<String> getActiveVPNIdentifiers() {
		return activeVPNRegistry.keySet();
	}

	private void requestConnectionToServer() throws CloudCommunicationException {
		if(isRegistered())
			throw new ConnectionAlreadyRegisteredException(ConnectionAlreadyRegisteredException.DEFAULT_MESSAGE, null, "", "The connection is already registered to the Server");
		if(!requestedConnections.isEmpty())
			throw new ConnectionAlreadyRequestedException(ConnectionAlreadyRequestedException.DEFAULT_MESSAGE, null, "", "We've already requested a connection to the FMP Server");
		String sender = eccPublicKey;
		String destination = serverPublicKey;
		FMPPacketType type = FMPPacketType.CONNECTION_REQUEST;
		String messageHash = AsymmectricCryptography.encryptMessagePublicKey(networkService.toString(), serverPublicKey);
		String signature = AsymmectricCryptography.createMessageSignature(messageHash, eccPrivateKey);
		try{
			FMPPacket packet = FMPPacketFactory.constructCloudPacket(sender, destination, type, messageHash, signature);
			pendingMessages.add(packet);
			SelectionKey serverConnection = unregisteredConnections.get(serverPublicKey);
			serverConnection.interestOps(SelectionKey.OP_WRITE);
			unregisteredConnections.remove(serverPublicKey);
			requestedConnections.put(serverPublicKey, serverConnection);
		} catch(FMPException ex){
			throw wrapFMPException(sender, destination, type.toString(), messageHash, signature, ex);
		}
	}
	
	private boolean validatePacketSignature(final FMPPacket dataPacket){
		String message = dataPacket.getMessage();
		String signature = dataPacket.getSignature();
		String sender = dataPacket.getSender();
		
		return AsymmectricCryptography.verifyMessageSignature(signature, message, sender);
	}
	
	private void sendPacketToRegisteredServer(final FMPPacket packet){
		pendingMessages.add(packet);
		registeredConnections.get(serverPublicKey).interestOps(SelectionKey.OP_WRITE);
	}

	private IllegalPacketSenderException constructIllegalPacketSenderException(final FMPPacket packet){
		String message = IllegalPacketSenderException.DEFAULT_MESSAGE;
		String context = new String();
		context += "Server Public Key: " + serverPublicKey;
		context += IllegalPacketSenderException.CONTEXT_CONTENT_SEPARATOR;
		context += "Client Public Key: " + eccPublicKey;
		context += IllegalPacketSenderException.CONTEXT_CONTENT_SEPARATOR;
		context += "Packet Sender: " + packet.getSender();
		String possibleReason = new String();
		possibleReason += "This is a problem of the flow of the packets, this might be accidental or some echo loop.";
		possibleReason += "This can also be an unexpected attack from an unexpected sender.";
		return new IllegalPacketSenderException(message, null, context, possibleReason);
	}

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
