package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.cloud;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.FMPPacketFactory;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannelAddress;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud.exceptions.CloudCommunicationException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud.CloudConnectionManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket.FMPPacketType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacketHandler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

/*
 *	created by jorgeejgonzalez
 */
public abstract class CloudFMPConnectionManager implements CloudConnectionManager, FMPPacketHandler, Runnable {

	protected final String eccPrivateKey;
	protected final String eccPublicKey;
	
	protected CommunicationChannelAddress address;
	protected ExecutorService executor;
	protected AtomicBoolean running;
	
	protected CloudFMPConnectionManagerMode mode;

	protected Selector selector;
	protected ServerSocketChannel serverChannel;
	protected SocketChannel clientChannel;
	
	protected final Map<String, SelectionKey> unregisteredConnections = new ConcurrentHashMap<String, SelectionKey>();
	protected final Map<String, SelectionKey> registeredConnections = new ConcurrentHashMap<String, SelectionKey>();
	protected final Queue<FMPPacket> pendingIncomingMessages = new ConcurrentLinkedQueue<FMPPacket>();
	
	private static final int SELECTOR_SELECT_TIMEOUT = 20;
	private static final String CHARSET_NAME = "UTF-8";

	public CloudFMPConnectionManager(final CommunicationChannelAddress address, final ExecutorService executor, final String privateKey, final String publicKey, final CloudFMPConnectionManagerMode mode) throws IllegalArgumentException{
		if(address == null)
			throw new IllegalArgumentException();
		if(executor == null)
			throw new IllegalArgumentException();
		if(privateKey == null || privateKey.isEmpty())
			throw new IllegalArgumentException();
		if(publicKey == null || privateKey.isEmpty())
			throw new IllegalArgumentException();
		this.address = address;
		this.executor = executor;
		this.eccPrivateKey = privateKey;
		this.eccPublicKey = publicKey;
		this.mode = mode;
		this.running = new AtomicBoolean(false);
		this.unregisteredConnections.clear();
		this.registeredConnections.clear();
	}

	@Override
	public CommunicationChannelAddress getAddress(){
		return address;
	}
	
	public String getPublicKey(){
		return this.eccPublicKey;		
	}

	@Override
	public void iterateSelectedKeys(final Selector selector) throws CloudCommunicationException {
		try{
			selector.select(SELECTOR_SELECT_TIMEOUT);
		} catch(IOException ex){
			throw wrapNIOSocketIOException(ex);
		}
		Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
		while(keys.hasNext()){
			SelectionKey key = keys.next();

			if(!key.isValid())
				continue;

			if(key.isAcceptable())
				acceptKey(key);

			if(key.isConnectable())
				connectToKey(key);

			if(key.isReadable())
				readFromKey(key);

			if(key.isWritable())
				writeToKey(key);
		}
	}

	@Override
	public void acceptKey(final SelectionKey key) throws CloudCommunicationException {
		try {
			ServerSocketChannel serverSocketChannel = extractServerSocketChannel(key);
			SocketChannel socketChannel = serverSocketChannel.accept();
			if(socketChannel!=null){
				socketChannel.configureBlocking(false);		
				socketChannel.register(selector, SelectionKey.OP_READ);
			}
		} catch(IOException ex) {
			throw wrapNIOSocketIOException(ex);
		}
	}

	@Override
	public void connectToKey(final SelectionKey key) throws CloudCommunicationException {
		try {
			SocketChannel socketChannel = extractSocketChannel(key);
			if(socketChannel.isConnectionPending())
				socketChannel.finishConnect();
			socketChannel.configureBlocking(false);
			socketChannel.register(selector, SelectionKey.OP_READ);
			running.set(socketChannel.isConnected());
		} catch(IOException ex){
			throw wrapNIOSocketIOException(ex);
		}
	}

	@Override
	public synchronized void readFromKey(final SelectionKey key) throws CloudCommunicationException {
		SocketChannel channel = extractSocketChannel(key);
		ByteBuffer readBuffer = ByteBuffer.allocate(FMPPacket.PACKET_MAX_BYTE_SIZE);
		readBuffer.clear();
		int read;
			
		try{
			read = channel.read(readBuffer);
		}catch(IOException ex){
			closeSocketChannelAndCancelKey(channel, key);
			throw wrapNIOSocketIOException(ex);
		}
		
		if(read==-1){
			closeSocketChannelAndCancelKey(channel, key);
			String message = CloudCommunicationException.DEFAULT_MESSAGE;
			String context = "";
			String possibleReason = "The channel has reached end-of-stream";
			throw new CloudCommunicationException(message, null, context, possibleReason);
		}
		
		readBuffer.flip();
		byte[] data = new byte[read];
		readBuffer.get(data, 0, read);
		try{
			StringBuffer stringBuffer = new StringBuffer(new String(data, CHARSET_NAME));
			FMPPacket incomingPacket = FMPPacketFactory.constructCloudPacket(stringBuffer.toString().trim());
			processIncomingPacket(incomingPacket, key);
		} catch(UnsupportedEncodingException ex){
			System.out.println("THIS IS NEVER GOING TO HAPPEN");
		} catch (FMPException ex) {
			System.out.println(ex.getMessage());
		}		
	}

	@Override
	public synchronized void writeToKey(final SelectionKey key) throws CloudCommunicationException {
		if(key.attachment() == null || !(key.attachment() instanceof FMPPacket))
			throw new CloudCommunicationException(CloudCommunicationException.DEFAULT_MESSAGE, null, "", "The Connection has no FMPPacket attached for the write operation");
		try{
			SocketChannel channel = extractSocketChannel(key);
			FMPPacket dataPacket = (FMPPacket) key.attachment();
			byte[] data = dataPacket.toString().getBytes(CHARSET_NAME);
			channel.write(ByteBuffer.wrap(data));
			key.interestOps(SelectionKey.OP_READ);
		}catch(IOException ex){
			throw wrapNIOSocketIOException(ex);
		}
	}
	
	@Override
	public void start() throws CloudCommunicationException {
		if(mode == CloudFMPConnectionManagerMode.FMP_CLIENT){
			initializeClient();
			running.set(clientChannel.isConnected());
		}
		if(mode == CloudFMPConnectionManagerMode.FMP_SERVER){
			initializeServer();
			running.set(serverChannel.isOpen()  && serverChannel.socket().isBound());
		}
		executor.execute(this);
	}

	@Override
	public void stop() throws CloudCommunicationException {
		running.set(false);
	}

	@Override
	public boolean isRunning(){
		return running.get();
	}

	public void processDataPackets() throws CloudCommunicationException {
		FMPPacket dataPacket = null;
		try {
			dataPacket = pendingIncomingMessages.remove();
			if(dataPacket.getType() == FMPPacketType.CONNECTION_REQUEST)
				handleConnectionRequest(dataPacket);
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
		}catch(FMPException ex){
			String message = CloudCommunicationException.DEFAULT_MESSAGE;
			FermatException cause = ex;
			String context = "Packet Data: " + dataPacket.toString();
			String possibleReason = "Something failed in the processing of one of the different PacketType, you should check the FMPException that is linked below";
			throw new CloudCommunicationException(message, cause, context, possibleReason);
		}catch(NoSuchElementException ex){
			return;
		}
	}

	@Override
	public abstract void handleConnectionRequest(final FMPPacket packet) throws FMPException;
	
	@Override
	public abstract void handleConnectionAccept(final FMPPacket packet) throws FMPException;

	@Override
	public abstract void handleConnectionAcceptForward(final FMPPacket packet) throws FMPException;

	@Override
	public abstract void handleConnectionDeny(final FMPPacket packet) throws FMPException;

	@Override
	public abstract void handleConnectionRegister(final FMPPacket packet) throws FMPException;

	@Override
	public abstract void handleConnectionDeregister(final FMPPacket packet) throws FMPException;

	@Override
	public abstract void handleConnectionEnd(final FMPPacket packet) throws FMPException;

	@Override
	public abstract void handleDataTransmit(final FMPPacket packet) throws FMPException;
	
	@Override
	public void run(){
		while(running.get()){
			try{
				iterateSelectedKeys(selector);
				processDataPackets();
			} catch(CloudCommunicationException ex){
				System.err.println(ex.toString());
			}
		}
	}

	private void initializeClient() throws CloudCommunicationException {
		try{
			selector = Selector.open();
			clientChannel = SocketChannel.open();
			clientChannel.configureBlocking(false);
			clientChannel.register(selector, SelectionKey.OP_CONNECT);
			clientChannel.connect(address.getSocketAddress());
			if(clientChannel.isConnectionPending())
				clientChannel.finishConnect();
		}catch(IOException ex){
			throw wrapNIOSocketIOException(ex);
		}		 
	}
	
	private void initializeServer() throws CloudCommunicationException {
		try{
			selector = Selector.open();
			serverChannel = ServerSocketChannel.open();
			serverChannel.configureBlocking(false);
			serverChannel.socket().bind(address.getSocketAddress());
			serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		}catch(IOException ex){
			throw wrapNIOSocketIOException(ex);
		}
	}

	private ServerSocketChannel extractServerSocketChannel(final SelectionKey key) {
		return (ServerSocketChannel) key.channel();
	}
	
	private SocketChannel extractSocketChannel(final SelectionKey key) {
		return (SocketChannel) key.channel();
	}

	private void closeSocketChannelAndCancelKey(final SocketChannel channel, final SelectionKey key) throws CloudCommunicationException {
		try {
			channel.close();
			key.cancel();
		} catch(IOException ex) {
			throw wrapNIOSocketIOException(ex);
		}
	}
	
	private void processIncomingPacket(final FMPPacket packet, SelectionKey connection){
		if(!registeredConnections.containsKey(packet.getSender()) && !unregisteredConnections.containsKey(packet.getSender()) && packet.getType() == FMPPacketType.CONNECTION_REQUEST)
			unregisteredConnections.put(packet.getSender(), connection);
		pendingIncomingMessages.add(packet);
	}

	protected CloudCommunicationException wrapNIOSocketIOException(final IOException ex) {
		/*
		 * We prepare the values for the exception that is going to be thrown
		 */
		String message = CloudCommunicationException.DEFAULT_MESSAGE;
		FermatException cause = FermatException.wrapException(ex);
		String context = address.toString();
		String possibleReason = "The NIO Socket can be unexpectedly closed, we need to check this more deeply.";
		possibleReason += " This can happen for the socket being already occupied or the port suddenly being yatched or problems ";
		possibleReason += "with the read/write operations on the network.";

		/*
		 * We create the exception and then throw it
		*/
		return new CloudCommunicationException(message, cause, context, possibleReason);
	}

	protected enum CloudFMPConnectionManagerMode{
		FMP_CLIENT,
		FMP_SERVER
	}
	
}
