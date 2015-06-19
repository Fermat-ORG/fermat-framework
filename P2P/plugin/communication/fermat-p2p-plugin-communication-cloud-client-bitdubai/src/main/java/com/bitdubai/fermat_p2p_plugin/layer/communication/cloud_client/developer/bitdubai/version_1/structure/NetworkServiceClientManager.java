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
import java.util.concurrent.Executors;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannelAddress;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud_server.exceptions.CloudConnectionException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket.FMPPacketType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.CommunicationChannelAddressFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.FMPPacketFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.cloud.CloudFMPConnectionManager;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServices;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.exceptions.ConnectionAlreadyRegisteredException;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.exceptions.ConnectionAlreadyRequestedException;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.exceptions.IllegalPacketSenderException;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.exceptions.IllegalSignatureException;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.exceptions.VPNInitializationException;

public class NetworkServiceClientManager extends CloudFMPConnectionManager {
	
	private static final String CHARSET_NAME = "UTF-8";
	
	private final Queue<FMPPacket> pendingMessages = new ConcurrentLinkedQueue<FMPPacket>();
	private Map<String, SelectionKey> requestedConnections = new ConcurrentHashMap<String, SelectionKey>();
	
	private final String serverPublicKey;
	private final NetworkServices networkService;
	
	private final Map<String, NetworkServiceClientVPN> activeVPNRegistry = new ConcurrentHashMap<String, NetworkServiceClientVPN>();
	private final Map<String, String> pendingVPNRequests = new ConcurrentHashMap<String, String>();

	public NetworkServiceClientManager(final CommunicationChannelAddress serverAddress, final ExecutorService executor, final String clientPrivateKey, final String serverPublicKey, final NetworkServices networkService) throws IllegalArgumentException {
		super(serverAddress, executor, clientPrivateKey, AsymmectricCryptography.derivePublicKey(clientPrivateKey), CloudFMPConnectionManagerMode.FMP_CLIENT);
		this.serverPublicKey = serverPublicKey;
		this.networkService = networkService;
	}
	
	@Override
	public synchronized void writeToKey(final SelectionKey key) throws CloudConnectionException{
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
			throw new CloudConnectionException(ex.getMessage());
		}
	}
	
	@Override
	public void handleConnectionAccept(final FMPPacket dataPacket) throws FMPException {
		if(!serverPublicKey.equals(dataPacket.getSender()))
			throw new IllegalPacketSenderException("should be " + serverPublicKey + " but is " + dataPacket.getSender());
		if(!validatePacketSignature(dataPacket))
			throw new IllegalSignatureException("bad signature for the packet: \n"+dataPacket.toString());
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
			NetworkServiceClientVPN vpnClient = new NetworkServiceClientVPN(vpnAddress, Executors.newFixedThreadPool(2), eccPrivateKey, vpnPublicKey, networkService);
			vpnClient.start();
			activeVPNRegistry.put(dataPacket.getSender(), vpnClient);
			pendingVPNRequests.remove(dataPacket.getSender());
		}catch(CloudConnectionException ex){
			throw new VPNInitializationException(ex.getMessage());
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
	public void start() throws CloudConnectionException{
		if(running.get())
			throw new CloudConnectionException();
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
			throw new CloudConnectionException(ex.getMessage());
		}
	}
	
	public boolean isRegistered() {
		return registeredConnections.containsKey(serverPublicKey);
	}
	
	public void requestVPNConnection(final String peer) throws CloudConnectionException {
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
			throw new CloudConnectionException(ex.getMessage());
		}
	}
	
	
	
	public void acceptPendingVPNRequest(final String peer) throws FMPException {
		String sender = eccPublicKey;
		String destination = peer;
		FMPPacketType type = FMPPacketType.CONNECTION_ACCEPT;
		String messageHash = AsymmectricCryptography.encryptMessagePublicKey(pendingVPNRequests.get(peer), serverPublicKey);
		String signature = AsymmectricCryptography.createMessageSignature(messageHash, eccPrivateKey);
		FMPPacket packet = FMPPacketFactory.constructCloudPacket(sender, destination, type, messageHash, signature);
		System.out.println(packet.toString());
		sendPacketToRegisteredServer(packet);
	}
	
	public String getPendingVPNRequest(){
		return pendingVPNRequests.keySet().iterator().next();
	}
	
	public NetworkServiceClientVPN getActiveVPN(final String peer){
		return activeVPNRegistry.get(peer);
	}
	
	private void requestConnectionToServer() throws CloudConnectionException{
		if(isRegistered())
			throw new ConnectionAlreadyRegisteredException();
		if(!requestedConnections.isEmpty())
			throw new ConnectionAlreadyRequestedException();
		String sender = eccPublicKey;
		String destination = serverPublicKey;
		FMPPacketType type = FMPPacketType.CONNECTION_REQUEST;
		String message = AsymmectricCryptography.encryptMessagePublicKey(networkService.toString(), serverPublicKey);
		String signature = AsymmectricCryptography.createMessageSignature(message, eccPrivateKey);
		try{
			FMPPacket packet = FMPPacketFactory.constructCloudPacket(sender, destination, type, message, signature);
			pendingMessages.add(packet);
			SelectionKey serverConnection = unregisteredConnections.get(serverPublicKey);
			serverConnection.interestOps(SelectionKey.OP_WRITE);
			unregisteredConnections.remove(serverPublicKey);
			requestedConnections.put(serverPublicKey, serverConnection);
		} catch(FMPException ex){
			throw new CloudConnectionException(ex.getMessage());
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
	
}
