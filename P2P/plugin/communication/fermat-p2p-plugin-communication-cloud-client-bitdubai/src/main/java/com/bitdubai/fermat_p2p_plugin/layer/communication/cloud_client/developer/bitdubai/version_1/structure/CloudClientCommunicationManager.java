package com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.structure;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;

import com.bitdubai.fermat_api.FermatException;
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

public class CloudClientCommunicationManager extends CloudFMPConnectionManager {
	
	private static final String CHARSET_NAME = "UTF-8";
	
	private final Queue<FMPPacket> pendingOutgoingMessages = new ConcurrentLinkedQueue<FMPPacket>();
	private Map<String, SelectionKey> requestedConnections = new ConcurrentHashMap<String, SelectionKey>();
	
	private Map<NetworkServices, CloudClientCommunicationNetworkServiceConnection> networkServiceRegistry;
	
	private final String serverPublicKey;
	
	public CloudClientCommunicationManager(final CommunicationChannelAddress serverAddress, final ExecutorService executor, final String clientPrivateKey, final String serverPublicKey) throws IllegalArgumentException {
		super(serverAddress, executor, clientPrivateKey, AsymmectricCryptography.derivePublicKey(clientPrivateKey), CloudFMPConnectionManagerMode.FMP_CLIENT);
		this.serverPublicKey = serverPublicKey;
		networkServiceRegistry = new ConcurrentHashMap<NetworkServices, CloudClientCommunicationNetworkServiceConnection>();
		networkServiceRegistry.clear();
	}
	
	@Override
	public synchronized void writeToKey(final SelectionKey key) throws CloudCommunicationException {
		try{
			SocketChannel channel = (SocketChannel) key.channel();
			FMPPacket dataPacket = pendingOutgoingMessages.remove();
			byte[] data = dataPacket.toString().getBytes(CHARSET_NAME);
			channel.write(ByteBuffer.wrap(data));
			if(!pendingOutgoingMessages.isEmpty())
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
		pendingOutgoingMessages.add(packet);
		serverConnection.interestOps(SelectionKey.OP_WRITE);
	}

	@Override
	public void handleConnectionAcceptForward(final FMPPacket dataPacket) throws FMPException {
		String decryptedMessage;
		try{
			decryptedMessage = AsymmectricCryptography.decryptMessagePrivateKey(dataPacket.getMessage(), eccPrivateKey);
		}catch(Exception ex){
			ex.printStackTrace();
			return;
		}
		String[] messageComponents = decryptedMessage.split(FMPPacket.MESSAGE_SEPARATOR);
		NetworkServices networkService = NetworkServices.valueOf(messageComponents[0]);
		if(networkServiceRegistry.containsKey(networkService))
			return;
		String host = messageComponents[1];
		Integer port = Integer.valueOf(messageComponents[2]);
		CommunicationChannelAddress networkServiceServerAddress = CommunicationChannelAddressFactory.constructCloudAddress(host, port);
		String networkServiceServerPublicKey = messageComponents[3];
		CloudClientCommunicationNetworkServiceConnection networkServiceClient = new CloudClientCommunicationNetworkServiceConnection(networkServiceServerAddress, executor, AsymmectricCryptography.createPrivateKey(), networkServiceServerPublicKey, networkService);
		try{
			networkServiceClient.start();
		}catch(CloudCommunicationException ex){
			ex.printStackTrace();
		}
		networkServiceRegistry.put(networkService, networkServiceClient);
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
		System.out.println(dataPacket.toString());
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
			throw new CloudFMPClientStartFailedException(CloudFMPClientStartFailedException.DEFAULT_MESSAGE, null, address.toString(), "The FMP Client is already running");
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
		} catch(IOException ex){
			throw wrapNIOSocketIOException(ex);
		}
	}
	
	public void requestConnectionToServer() throws CloudCommunicationException {
		if(isRegistered())
			throw new ConnectionAlreadyRegisteredException(ConnectionAlreadyRegisteredException.DEFAULT_MESSAGE, null, getClass().toString(), "We've already registered our connection in the Server");
		if(!requestedConnections.isEmpty())
			throw new ConnectionAlreadyRequestedException(ConnectionAlreadyRequestedException.DEFAULT_MESSAGE, null, getClass().toString(), "We've already requested a connection to the FMP Server");
		String sender = eccPublicKey;
		String destination = serverPublicKey;
		FMPPacketType type = FMPPacketType.CONNECTION_REQUEST;
		String messageHash = eccPublicKey;
		String signature = AsymmectricCryptography.createMessageSignature(messageHash, eccPrivateKey);
		try{
			FMPPacket packet = FMPPacketFactory.constructCloudPacket(sender, destination, type, messageHash, signature);
			pendingOutgoingMessages.add(packet);
			SelectionKey serverConnection = unregisteredConnections.get(serverPublicKey);
			serverConnection.interestOps(SelectionKey.OP_WRITE);
			unregisteredConnections.remove(serverPublicKey);
			requestedConnections.put(serverPublicKey, serverConnection);
		} catch(FMPException ex){
			throw wrapFMPException(sender, destination, type.toString(), messageHash, signature, ex);
		}
	}
	
	public void registerNetworkService(final NetworkServices networkService) throws CloudCommunicationException {
		String sender = eccPublicKey;
		String destination = serverPublicKey;
		FMPPacketType type = FMPPacketType.CONNECTION_REQUEST;
		String messageHash = AsymmectricCryptography.encryptMessagePublicKey(networkService.toString(), serverPublicKey);
		String signature = AsymmectricCryptography.createMessageSignature(messageHash, eccPrivateKey);
		try{
			FMPPacket packet = FMPPacketFactory.constructCloudPacket(sender, destination, type, messageHash, signature);
			pendingOutgoingMessages.add(packet);
			SelectionKey serverConnection = registeredConnections.get(serverPublicKey);
			serverConnection.interestOps(SelectionKey.OP_WRITE);
		} catch(FMPException ex){
			throw wrapFMPException(sender, destination, type.toString(), messageHash, signature, ex);
		}
	}
	
	public CloudClientCommunicationNetworkServiceConnection getNetworkServiceClient(final NetworkServices networkService) throws CloudCommunicationException {
		return networkServiceRegistry.get(networkService);
	}

	public boolean isRegistered() {
		return registeredConnections.containsKey(serverPublicKey);
	}
	
	private boolean validatePacketSignature(final FMPPacket dataPacket){
		String message = dataPacket.getMessage();
		String signature = dataPacket.getSignature();
		String sender = dataPacket.getSender();
		return AsymmectricCryptography.verifyMessageSignature(signature, message, sender);
	}

	private IllegalPacketSenderException constructIllegalPacketSenderException(final FMPPacket packet){
		String message = IllegalPacketSenderException.DEFAULT_MESSAGE;
		String context = "Server Public Key: " + serverPublicKey;
		context += IllegalPacketSenderException.CONTEXT_CONTENT_SEPARATOR;
		context += "Client Public Key: " + eccPublicKey;
		context += IllegalPacketSenderException.CONTEXT_CONTENT_SEPARATOR;
		context += "Packet Sender: " + packet.getSender();
		String possibleReason = "This is a problem of the flow of the packets, this might be accidental or some echo loop.";
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
