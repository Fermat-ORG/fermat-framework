package com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.structure;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

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
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServices;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.exceptions.CloudFMPClientStartFailedException;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.exceptions.ConnectionAlreadyRegisteredException;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.exceptions.ConnectionAlreadyRequestedException;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.exceptions.IllegalPacketSenderException;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.exceptions.IllegalPacketSignatureException;

public class CloudClientCommunicationNetworkServiceVPN extends CloudFMPConnectionManager implements ServiceToServiceOnlineConnection {
	
	private static final String CHARSET_NAME = "UTF-8";
	
	private final Queue<FMPPacket> pendingPackets = new ConcurrentLinkedQueue<FMPPacket>();
	private Map<String, SelectionKey> requestedConnections = new ConcurrentHashMap<String, SelectionKey>();
	
	private final String vpnPublicKey;
	private final String peerPublicKey;
	private final NetworkServices networkService;
	
	private final AtomicBoolean registered;
	private final Set<String> pendingMessages = new ConcurrentSkipListSet<String>();
	
	public CloudClientCommunicationNetworkServiceVPN(final CommunicationChannelAddress vpnAddress, final ExecutorService executor, final String clientPrivateKey, final String vpnPublicKey, final String peerPublicKey, final NetworkServices networkService) throws IllegalArgumentException {
		super(vpnAddress, executor, clientPrivateKey, AsymmectricCryptography.derivePublicKey(clientPrivateKey), CloudFMPConnectionManagerMode.FMP_CLIENT);
		this.vpnPublicKey = vpnPublicKey;
		this.peerPublicKey = peerPublicKey;
		this.networkService = networkService;
		this.registered = new AtomicBoolean(false);
	}
	
	@Override
	public synchronized void writeToConnection(final SelectionKey connection) throws CloudCommunicationException {
		try{
			SocketChannel channel = (SocketChannel) connection.channel();
			FMPPacket dataPacket = pendingPackets.remove();
			byte[] data = dataPacket.toString().getBytes(CHARSET_NAME);
			channel.write(ByteBuffer.wrap(data));
			if(!pendingPackets.isEmpty())
				writeToConnection(connection);
			connection.interestOps(SelectionKey.OP_READ);
		}catch(NoSuchElementException ex){
			connection.interestOps(SelectionKey.OP_READ);
		}catch(IOException ex){
			throw wrapNIOSocketIOException(ex);
		}
	}

	@Override
	public void handleConnectionAccept(final FMPPacket dataPacket) throws FMPException {
		if(!vpnPublicKey.equals(dataPacket.getSender()))
			throw constructIllegalPacketSenderException(dataPacket);
		if(!validatePacketSignature(dataPacket))
			throw constructIllegalPacketSignatureException(dataPacket);
		if(requestedConnections.isEmpty())
			return;
		
        FMPPacket responsePacket = FMPPacketFactory.constructCloudFMPPacketEncryptedAndSinged(identity.getPublicKey(),     //sender
                dataPacket.getSender(),             //destination
                networkService.toString(),   // message
                FMPPacketType.CONNECTION_REGISTER,
                NetworkServices.UNDEFINED,
                identity.getPrivateKey());


		pendingPackets.add(responsePacket);
		
		SelectionKey serverConnection = requestedConnections.get(vpnPublicKey);
		serverConnection.interestOps(SelectionKey.OP_WRITE);
	}

	@Override
	public void handleConnectionAcceptForward(final FMPPacket dataPacket)
			throws FMPException {
	}

	@Override
	public void handleConnectionDeny(final FMPPacket dataPacket) throws FMPException {
		System.out.println(AsymmectricCryptography.decryptMessagePrivateKey(dataPacket.getMessage(), identity.getPrivateKey()));
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
	}

	@Override
	public void handleConnectionRequest(final FMPPacket dataPacket) throws FMPException {
	}

	@Override
	public void handleDataTransmit(final FMPPacket dataPacket) throws FMPException {
		String message = AsymmectricCryptography.decryptMessagePrivateKey(dataPacket.getMessage(), identity.getPrivateKey());
		if(message.equals("REGISTERED"))
			registerVPNConnection();
		else
			pendingMessages.add(message);	
	}
	
	private void registerVPNConnection() {
		SelectionKey serverConnection = requestedConnections.get(vpnPublicKey);
		requestedConnections.remove(vpnPublicKey);
		registeredConnections.put(vpnPublicKey, serverConnection);
	}
	
	@Override
	public void start() throws CloudCommunicationException {
		if(running.get())
			throw new CloudFMPClientStartFailedException(CloudFMPClientStartFailedException.DEFAULT_MESSAGE, null, communicationChannelAddress.toString(), "The FMP Client is already running");
		try{
			selector = Selector.open();
			clientChannel = SocketChannel.open();
			clientChannel.configureBlocking(false);
			SelectionKey serverConnection = clientChannel.register(selector, SelectionKey.OP_CONNECT);
			clientChannel.connect(communicationChannelAddress.getSocketAddress());
			if(clientChannel.isConnectionPending())
				clientChannel.finishConnect();
			running.set(clientChannel.isConnected());
			unregisteredConnections.put(vpnPublicKey, serverConnection);
			executorService.execute(this);
			requestConnectionToServer();
		}catch(IOException ex){
			throw wrapNIOSocketIOException(ex);
		}
	}
	
	private void requestConnectionToServer() throws CloudCommunicationException {
		if(isRegistered())
			throw new ConnectionAlreadyRegisteredException(ConnectionAlreadyRegisteredException.DEFAULT_MESSAGE, null, getClass().toString(), "We've already registered our connection in the Server");
		if(!requestedConnections.isEmpty())
			throw new ConnectionAlreadyRequestedException(ConnectionAlreadyRequestedException.DEFAULT_MESSAGE, null, getClass().toString(), "We've already requested a connection to the FMP Server");
		String sender = identity.getPublicKey();
		String destination = peerPublicKey;
		FMPPacketType type = FMPPacketType.CONNECTION_REQUEST;
		String message = AsymmectricCryptography.encryptMessagePublicKey(networkService.toString(), vpnPublicKey);
		String signature = AsymmectricCryptography.createMessageSignature(message, identity.getPrivateKey());
		try{

            FMPPacket responsePacket = FMPPacketFactory.constructCloudFMPPacketEncryptedAndSinged(identity.getPublicKey(),     //sender
                    peerPublicKey,             //destination
                    networkService.toString(),   // message
                    FMPPacketType.CONNECTION_REQUEST,
                    NetworkServices.UNDEFINED,
                    identity.getPrivateKey());

			pendingPackets.add(responsePacket);
			SelectionKey serverConnection = unregisteredConnections.remove(vpnPublicKey);
            requestedConnections.put(vpnPublicKey, serverConnection);
			serverConnection.interestOps(SelectionKey.OP_WRITE);
            registered.set(true);
		} catch(FMPException ex){
			throw wrapFMPException(sender, destination, type.toString(), message, signature, ex);
		}
	}

	public boolean isRegistered() {
		return registered.get();
	}
	
	private boolean validatePacketSignature(final FMPPacket dataPacket){
		String message = dataPacket.getMessage();
		String signature = dataPacket.getSignature();
		String sender = dataPacket.getSender();
		
		return AsymmectricCryptography.verifyMessageSignature(signature, message, sender);
	}

	public void sendMessage(String message) throws FMPException, CloudCommunicationException {
		if(!isRegistered())
			throw new CloudCommunicationException(CloudCommunicationException.DEFAULT_MESSAGE, null, "", "We haven't registered the connection to the server");

            FMPPacket responsePacket = FMPPacketFactory.constructCloudFMPPacketEncryptedAndSinged(identity.getPublicKey(),     //sender
                                        peerPublicKey,             //destination
                                        message,   // message
                                        FMPPacketType.DATA_TRANSMIT,
                                        NetworkServices.UNDEFINED,
                                        identity.getPrivateKey());

		pendingPackets.add(responsePacket);
		registeredConnections.get(vpnPublicKey).interestOps(SelectionKey.OP_WRITE);
	}

	public String getPendingMessage() {
		if(pendingMessages.isEmpty())
			return "";
		return pendingMessages.iterator().next();
	}

	@Override
	public void reConnect() throws CantConnectToRemoteServiceException {
		try{
			start();
		} catch(Exception ex){
			throw new CantConnectToRemoteServiceException(CantConnectToRemoteServiceException.DEFAULT_MESSAGE, ex, communicationChannelAddress.toString(), "The Reconnection Failed, check the cause");
		}
	}

	@Override
	public void disconnect() {
		try{
			stop();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	@Override
	public ConnectionStatus getStatus() {
		if(isRegistered())
			return ConnectionStatus.CONNECTED;
		else
			return ConnectionStatus.DISCONNECTED;
	}

	@Override
	public void sendMessage(Message message) throws CantSendMessageException {
		try{
			sendMessage(message.getTextContent());
		}catch(Exception ex){
			throw new CantSendMessageException(CantSendMessageException.DEFAULT_MESSAGE, ex, "Message Content:" + message.getTextContent(), "There was an errror sending the message, check the cause");
		}
	}

	@Override
	public int getUnreadMessagesCount() {
		return pendingMessages.size();
	}

	@Override
	public Message readNextMessage() {
		String messageContent = getPendingMessage();
		return new CloudClientCommunicationMessage(messageContent, MessagesStatus.DELIVERED);
	}

	@Override
	public void clearMessage(Message message) {
		if(pendingMessages.isEmpty()|| !pendingMessages.contains(message.getTextContent()))
			throw new IllegalArgumentException("Message not in the stack");
		pendingMessages.remove(message.getTextContent());
	}

	private IllegalPacketSenderException constructIllegalPacketSenderException(final FMPPacket packet){
		String message = IllegalPacketSenderException.DEFAULT_MESSAGE;
		String context = "Server Public Key: " + vpnPublicKey;
		context += IllegalPacketSenderException.CONTEXT_CONTENT_SEPARATOR;
		context += "Client Public Key: " + identity.getPublicKey();
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
