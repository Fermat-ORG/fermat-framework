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
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.exceptions.ConnectionAlreadyRegisteredException;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.exceptions.ConnectionAlreadyRequestedException;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.exceptions.IllegalPacketSenderException;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.exceptions.IllegalSignatureException;

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
	public synchronized void writeToKey(final SelectionKey key) throws CloudCommunicationException {
		try{
			SocketChannel channel = (SocketChannel) key.channel();
			FMPPacket dataPacket = pendingPackets.remove();
			byte[] data = dataPacket.toString().getBytes(CHARSET_NAME);
			channel.write(ByteBuffer.wrap(data));
			if(!pendingPackets.isEmpty())
				writeToKey(key);
			key.interestOps(SelectionKey.OP_READ);
		}catch(NoSuchElementException ex){
			key.interestOps(SelectionKey.OP_READ);
		}catch(IOException ex){
			throw new CloudCommunicationException(ex.getMessage());
		}
	}

	@Override
	public void handleConnectionAccept(final FMPPacket dataPacket) throws FMPException {
		if(!vpnPublicKey.equals(dataPacket.getSender()))
			throw new IllegalPacketSenderException("should be " + vpnPublicKey + " but is " + dataPacket.getSender());
		if(!validatePacketSignature(dataPacket))
			throw new IllegalSignatureException("bad signature for the packet: \n"+dataPacket.toString());
		if(requestedConnections.isEmpty())
			return;
		
		String sender = eccPublicKey;
		String destination = dataPacket.getSender();
		FMPPacketType type = FMPPacketType.CONNECTION_REGISTER;
		String message = AsymmectricCryptography.encryptMessagePublicKey(networkService.toString(), vpnPublicKey);
		String signature = AsymmectricCryptography.createMessageSignature(message, eccPrivateKey);
		FMPPacket packet = FMPPacketFactory.constructCloudPacket(sender, destination, type, message, signature);
		pendingPackets.add(packet);
		
		SelectionKey serverConnection = requestedConnections.get(vpnPublicKey);
		serverConnection.interestOps(SelectionKey.OP_WRITE);
	}

	@Override
	public void handleConnectionAcceptForward(final FMPPacket dataPacket)
			throws FMPException {
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
	}

	@Override
	public void handleConnectionRequest(final FMPPacket dataPacket) throws FMPException {
	}

	@Override
	public void handleDataTransmit(final FMPPacket dataPacket) throws FMPException {
		String message = AsymmectricCryptography.decryptMessagePrivateKey(dataPacket.getMessage(), eccPrivateKey);
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
			throw new CloudCommunicationException();
		try{
			selector = Selector.open();
			clientChannel = SocketChannel.open();
			clientChannel.configureBlocking(false);
			SelectionKey serverConnection = clientChannel.register(selector, SelectionKey.OP_CONNECT);
			clientChannel.connect(address.getSocketAddress());
			if(clientChannel.isConnectionPending())
				clientChannel.finishConnect();
			running.set(clientChannel.isConnected());
			unregisteredConnections.put(vpnPublicKey, serverConnection);
			executor.execute(this);
			requestConnectionToServer();
		}catch(IOException ex){
			throw new CloudCommunicationException(ex.getMessage());
		}
	}
	
	private void requestConnectionToServer() throws CloudCommunicationException {
		if(isRegistered())
			throw new ConnectionAlreadyRegisteredException();
		if(!requestedConnections.isEmpty())
			throw new ConnectionAlreadyRequestedException();
		String sender = eccPublicKey;
		String destination = peerPublicKey;
		FMPPacketType type = FMPPacketType.CONNECTION_REQUEST;
		String message = AsymmectricCryptography.encryptMessagePublicKey(networkService.toString(), vpnPublicKey);
		String signature = AsymmectricCryptography.createMessageSignature(message, eccPrivateKey);
		try{
			FMPPacket packet = FMPPacketFactory.constructCloudPacket(sender, destination, type, message, signature);
			pendingPackets.add(packet);
			SelectionKey serverConnection = unregisteredConnections.get(vpnPublicKey);
			serverConnection.interestOps(SelectionKey.OP_WRITE);
			unregisteredConnections.remove(vpnPublicKey);
			requestedConnections.put(vpnPublicKey, serverConnection);
			registered.set(true);
		} catch(FMPException ex){
			throw new CloudCommunicationException(ex.getMessage());
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
			throw new CloudCommunicationException("Aun no estamos conectados");
		String sender = eccPublicKey;
		String destination = peerPublicKey;
		FMPPacketType type = FMPPacketType.DATA_TRANSMIT;
		String messageHash = AsymmectricCryptography.encryptMessagePublicKey(message, peerPublicKey);
		String signature = AsymmectricCryptography.createMessageSignature(messageHash, eccPrivateKey);
		FMPPacket packet = FMPPacketFactory.constructCloudPacket(sender, destination, type, messageHash, signature);
		pendingPackets.add(packet);
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
			throw new CantConnectToRemoteServiceException(ex.getMessage());
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
			throw new CantSendMessageException(ex.getMessage());
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
	
}
