package com.bitdubai.fermat_api.layer._1_definition.communication.cloud;

import com.bitdubai.fermat_api.layer._10_communication.CommunicationChannelAddress;
import com.bitdubai.fermat_api.layer._10_communication.cloud.CloudConnectionException;
import com.bitdubai.fermat_api.layer._10_communication.cloud.CloudConnectionManager;
import com.bitdubai.fermat_api.layer._10_communication.fmp.FMPException;
import com.bitdubai.fermat_api.layer._10_communication.fmp.FMPPacket;
import com.bitdubai.fermat_api.layer._10_communication.fmp.FMPPacketHandler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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
	
	protected Map<String, SelectionKey> unregisteredConnections = new ConcurrentHashMap<String, SelectionKey>();
	protected Map<String, SelectionKey> registeredConnections = new ConcurrentHashMap<String, SelectionKey>();
	
	private static final int SELECTOR_SELECT_TIMEOUT = 50;
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
	public void iterateSelectedKeys(final Selector selector) throws CloudConnectionException{
		try{
			selector.select(SELECTOR_SELECT_TIMEOUT);
		} catch(IOException ex){
			throw new CloudConnectionException(ex.getMessage());
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
	public void acceptKey(final SelectionKey key) throws CloudConnectionException{
		try {
			ServerSocketChannel serverSocketChannel = extractServerSocketChannel(key);
			SocketChannel socketChannel = serverSocketChannel.accept();
			if(socketChannel!=null){
				socketChannel.configureBlocking(false);		
				socketChannel.register(selector, SelectionKey.OP_READ);
			}
		} catch(IOException ex) {
			throw new CloudConnectionException(ex.getMessage());
		}
	}

	@Override
	public void connectToKey(final SelectionKey key) throws CloudConnectionException{
		try {
			SocketChannel socketChannel = extractSocketChannel(key);
			if(socketChannel.isConnectionPending())
				socketChannel.finishConnect();
			socketChannel.configureBlocking(false);
			socketChannel.register(selector, SelectionKey.OP_READ);
			running.set(socketChannel.isConnected());
		} catch(IOException ex){
			throw new CloudConnectionException(ex.getMessage());
		}
	}

	@Override
	public void readFromKey(final SelectionKey key) throws CloudConnectionException{
		SocketChannel channel = extractSocketChannel(key);
		ByteBuffer readBuffer = ByteBuffer.allocate(FMPPacket.PACKET_MAX_BYTE_SIZE);
		readBuffer.clear();
		int read;
			
		try{
			read = channel.read(readBuffer);
		}catch(IOException e){
			closeSocketChannelAndCancelKey(channel, key);
			throw new CloudConnectionException();
		}
		
		if(read==-1){
			closeSocketChannelAndCancelKey(channel, key);
			throw new CloudConnectionException();
		}
		
		readBuffer.flip();
		byte[] data = new byte[FMPPacket.PACKET_MAX_BYTE_SIZE];
		readBuffer.get(data, 0, read);
		try{
			StringBuffer stringBuffer = new StringBuffer(new String(data, CHARSET_NAME));
			if(!stringBuffer.toString().trim().isEmpty())
				processDataPacket(stringBuffer.toString().trim(), key);			
		} catch(UnsupportedEncodingException ex){
			ex.printStackTrace();
		}		
	}

	@Override
	public void writeToKey(final SelectionKey key) throws CloudConnectionException{
		if(key.attachment() == null || !(key.attachment() instanceof FMPPacket))
			throw new CloudConnectionException();
		try{
			SocketChannel channel = extractSocketChannel(key);
			FMPPacket dataPacket = (FMPPacket) key.attachment();
			byte[] data = dataPacket.toString().getBytes(CHARSET_NAME);
			channel.write(ByteBuffer.wrap(data));
			key.interestOps(SelectionKey.OP_READ);
		}catch(IOException ex){
			throw new CloudConnectionException(ex.getMessage());
		}
	}
	
	@Override
	public void start() throws CloudConnectionException{
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
	public void stop() throws CloudConnectionException{
		running.set(false);
	}

	@Override
	public boolean isRunning(){
		return running.get();
	}

	public abstract void processDataPacket(final String data, final SelectionKey key) throws CloudConnectionException;

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
			} catch(CloudConnectionException ex){
				System.err.println(ex.getMessage());
				//ex.printStackTrace();
			}
		}
	}

	private void initializeClient() throws CloudConnectionException{
		if(running.get())
			throw new CloudConnectionException();
		try{
			selector = Selector.open();
			clientChannel = SocketChannel.open();
			clientChannel.configureBlocking(false);
			clientChannel.register(selector, SelectionKey.OP_CONNECT);
			clientChannel.connect(address.getSocketAddress());
			if(clientChannel.isConnectionPending())
				clientChannel.finishConnect();
		}catch(IOException ex){
			throw new CloudConnectionException(ex.getMessage());
		}		 
	}
	
	private void initializeServer() throws CloudConnectionException{
		if(running.get())
			throw new CloudConnectionException();
		try{
			selector = Selector.open();
			serverChannel = ServerSocketChannel.open();
			serverChannel.configureBlocking(false);
			serverChannel.socket().bind(address.getSocketAddress());
			serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		}catch(IOException ex){
			throw new CloudConnectionException(ex.getMessage());
		}
	}

	private ServerSocketChannel extractServerSocketChannel(final SelectionKey key) {
		return (ServerSocketChannel) key.channel();
	}
	
	private SocketChannel extractSocketChannel(final SelectionKey key) {
		return (SocketChannel) key.channel();
	}

	private void closeSocketChannelAndCancelKey(final SocketChannel channel, final SelectionKey key) throws CloudConnectionException{
		try {
			channel.close();
			key.cancel();
		} catch(IOException ex) {
			throw new CloudConnectionException();
		}
	}
	
	protected enum CloudFMPConnectionManagerMode{
		FMP_CLIENT,
		FMP_SERVER
	}
	
}
