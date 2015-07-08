/*
 * @#CloudFMPConnectionManager.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.cloud;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
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

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.cloud.CloudFMPConnectionManager</code> is
 * the responsible to manage al logic to implement the communication using Java NIO.
 * <p/>
 *
 * Created by Jorge Gonzales
 * Update by Roberto Requena - (rart3001@gmail.com) on 08/06/15.
 *
 * @version 1.0
 */
public abstract class CloudFMPConnectionManager implements CloudConnectionManager, FMPPacketHandler, Runnable {

    /**
     * Represent the SELECTOR_SELECT_TIMEOUT value 20
     */
    private static final int SELECTOR_SELECT_TIMEOUT = 20;

    /**
     * Represent the CHARSET_NAME value UTF-8
     */
    private static final String CHARSET_NAME = "UTF-8";

    /**
     * Hold the key pair thar represent the identity for this connection manager
     */
    protected ECCKeyPair identity;

    /**
     * Represent the CommunicationChannelAddress for this connection manager
     */
	protected CommunicationChannelAddress communicationChannelAddress;

    /**
     * Represent the ExecutorService
     */
	protected ExecutorService executorService;

    /**
     * Represent the status of the thread
     */
	protected AtomicBoolean running;

    /**
     * Represent the mode of the connection are running
     */
	protected CloudFMPConnectionManagerMode mode;

    /**
     * Represent the unregistered connections cache
     */
    protected final Map<String, SelectionKey> unregisteredConnections;

    /**
     * Represent the registered connections cache
     */
    protected final Map<String, SelectionKey> registeredConnections;

    /**
     * Represent the queue of incoming messages
     */
    protected final Queue<FMPPacket> pendingIncomingMessages;

    //Start Java NIO attributes

    /**
     * Represent the selector
     */
	protected Selector selector;

    /**
     * Represent the serverChannel
     */
	protected ServerSocketChannel serverChannel;

    /**
     * Represent the clientChannel
     */
	protected SocketChannel clientChannel;

    //End Java NIO attributes

    /**
     * Constructor with parameters
     *
     * @param communicationChannelAddress
     * @param executorService
     * @param privateKey
     * @param publicKey
     * @param mode
     * @throws IllegalArgumentException
     */
	public CloudFMPConnectionManager(final CommunicationChannelAddress communicationChannelAddress, final ExecutorService executorService, final String privateKey, final String publicKey, final CloudFMPConnectionManagerMode mode) throws IllegalArgumentException{

        //Validate argument
		if(communicationChannelAddress == null ||
                executorService        == null ||
                privateKey == null || privateKey.isEmpty() ||
                publicKey  == null || privateKey.isEmpty()) {

            throw new IllegalArgumentException();
        }

		this.communicationChannelAddress = communicationChannelAddress;
		this.executorService             = executorService;
		this.identity                    = new ECCKeyPair(privateKey,publicKey);
		this.mode                        = mode;
		this.running                     = new AtomicBoolean(false);
        this.unregisteredConnections     = new ConcurrentHashMap<>();
        this.registeredConnections       = new ConcurrentHashMap<>();
        this.pendingIncomingMessages     = new ConcurrentLinkedQueue<>();

        this.unregisteredConnections.clear();
        this.registeredConnections.clear();
        this.pendingIncomingMessages.clear();
    }

    /**
     * Get the public key identity
     *
     * @return String
     */
    public String getIdentityPublicKey(){
        return this.identity.getPublicKey();
    }

    /**
     * (non-Javadoc)
     * @see CloudConnectionManager#getCommunicationChannelAddress()
     */
	@Override
	public CommunicationChannelAddress getCommunicationChannelAddress(){
		return communicationChannelAddress;
	}

    /**
     * (non-Javadoc)
     * @see CloudConnectionManager#processConnectionsWithNewActivity(Selector)
     */
	@Override
	public void processConnectionsWithNewActivity(final Selector selector) throws CloudCommunicationException {

        try{

            /*
             * Selects a set of connections whose corresponding channels are ready for I/O
             * operations and that have been active
             */
			selector.select(SELECTOR_SELECT_TIMEOUT);

            /*
             * Get the connections
             */
            Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

            /*
             * Iterate over the connections and perform the operation by state of the connection
             */
            while(keys.hasNext()){

                SelectionKey connection = keys.next();

                //If is valid
                if(!connection.isValid())
                    continue;

                //If is acceptable
                if(connection.isAcceptable())
                    acceptNewConnection(connection);

                //If is connectable
                if(connection.isConnectable())
                    connectToConnection(connection);

                //If is readable
                if(connection.isReadable())
                    readFromConnection(connection);

                //If is writable
                if(connection.isWritable())
                    writeToConnection(connection);
            }

        } catch(IOException ex){

            /*
             * Wrap and throw a CloudCommunicationException is IOException occurred
             */
            throw wrapNIOSocketIOException(ex);
        }
	}

    /**
     * (non-Javadoc)
     * @see CloudConnectionManager#acceptNewConnection(SelectionKey)
     */
	@Override
	public void acceptNewConnection(final SelectionKey connection) throws CloudCommunicationException {
		try {
			ServerSocketChannel serverSocketChannel = (ServerSocketChannel) connection.channel();
			SocketChannel socketChannel = serverSocketChannel.accept();
			if(socketChannel!=null){
				socketChannel.configureBlocking(false);		
				socketChannel.register(selector, SelectionKey.OP_READ);
			}
		} catch(IOException ex) {
			throw wrapNIOSocketIOException(ex);
		}
	}


    /**
     * (non-Javadoc)
     * @see CloudConnectionManager#connectToConnection(SelectionKey)
     */
	@Override
	public void connectToConnection(final SelectionKey connection) throws CloudCommunicationException {
		try {

            /*
             * Extract the socket chanel from the connection
             */
			SocketChannel socketChannel = (SocketChannel) connection.channel();
			if(socketChannel.isConnectionPending())
				socketChannel.finishConnect();
			socketChannel.configureBlocking(false);
			socketChannel.register(selector, SelectionKey.OP_READ);
			running.set(socketChannel.isConnected());
		} catch(IOException ex){
			throw wrapNIOSocketIOException(ex);
		}
	}

    /**
     * (non-Javadoc)
     * @see CloudConnectionManager#readFromConnection(SelectionKey)
     */
	@Override
	public synchronized void readFromConnection(final SelectionKey connection) throws CloudCommunicationException {

        /*
         * Extract the socket chanel from the connection
         */
        SocketChannel channel = (SocketChannel) connection.channel();

        try{

            /*
             * Prepare the read buffer
             */
            ByteBuffer readBuffer = ByteBuffer.allocate(FMPPacket.PACKET_MAX_BYTE_SIZE);
            readBuffer.clear();

            /*
             * Read from channel into the buffer
             */
            int read = channel.read(readBuffer);

            /*
             * Validate the read
             */
            if(read==-1){

                closeSocketChannelAndCancelKey(channel, connection);
                String message = CloudCommunicationException.DEFAULT_MESSAGE;
                String context = "";
                String possibleReason = "The channel has reached end-of-stream";
                throw new CloudCommunicationException(message, null, context, possibleReason);
            }

            /*
             * Flips this buffer.
             */
            readBuffer.flip();

            /*
             * Prepare the array of byte of the data
             */
            byte[] data = new byte[read];

            /*
             * Put the data from the read buffer into the array of byte
             */
            readBuffer.get(data, 0, read);

            /*
             * Create a string from the array of byte
             */
			StringBuffer stringBuffer = new StringBuffer(new String(data, CHARSET_NAME));

            /*
             * Create a new FMPPacket whit the string of data
             */
			FMPPacket incomingPacket = FMPPacketFactory.constructCloudPacket(stringBuffer.toString().trim());

            /**
             * Process the new incoming packet
             */
			processIncomingPacket(incomingPacket, connection);

		} catch(UnsupportedEncodingException ex){
			System.out.println("THIS IS NEVER GOING TO HAPPEN");
		} catch (FMPException ex) {
			System.out.println(ex.getMessage());
		}catch(IOException ex){
            closeSocketChannelAndCancelKey(channel, connection);
            throw wrapNIOSocketIOException(ex);
         }
	}

    /**
     * (non-Javadoc)
     * @see CloudConnectionManager#writeToConnection(SelectionKey)
     */
	@Override
	public synchronized void writeToConnection(final SelectionKey connection) throws CloudCommunicationException {
		if(connection.attachment() == null || !(connection.attachment() instanceof FMPPacket))
			throw new CloudCommunicationException(CloudCommunicationException.DEFAULT_MESSAGE, null, "", "The Connection has no FMPPacket attached for the write operation");
		try{

			/*
             * Extract the socket chanel from the connection
             */
            SocketChannel channel = (SocketChannel) connection.channel();
			FMPPacket dataPacket = (FMPPacket) connection.attachment();
			byte[] data = dataPacket.toString().getBytes(CHARSET_NAME);
			channel.write(ByteBuffer.wrap(data));
			connection.interestOps(SelectionKey.OP_READ);
		}catch(IOException ex){
			throw wrapNIOSocketIOException(ex);
		}
	}

    /**
     * (non-Javadoc)
     * @see CloudConnectionManager#start()
     */
	@Override
	public void start() throws CloudCommunicationException {

        System.out.println("Starting the CloudFMPConnectionManager in mode = "+mode);

		if(mode == CloudFMPConnectionManagerMode.FMP_CLIENT){
			initializeClient();
			running.set(clientChannel.isConnected());
		}
		if(mode == CloudFMPConnectionManagerMode.FMP_SERVER){
			initializeServer();
			running.set(serverChannel.isOpen()  && serverChannel.socket().isBound());
		}
		executorService.execute(this);
	}

    /**
     * (non-Javadoc)
     * @see CloudConnectionManager#stop()
     */
	@Override
	public void stop() throws CloudCommunicationException {
		running.set(false);
	}

    /**
     * (non-Javadoc)
     * @see CloudConnectionManager#isRunning()
     */
	@Override
	public boolean isRunning(){
		return running.get();
	}

    /**
     * Method thar process the Data Packets received
     *
     * @throws CloudCommunicationException
     */
	public void processDataPackets() throws CloudCommunicationException {

        FMPPacket dataPacket = null;

		try {

            dataPacket = pendingIncomingMessages.remove();

			if(dataPacket.getType() == FMPPacketType.CONNECTION_REQUEST) {
                handleConnectionRequest(dataPacket);
            }

			if(dataPacket.getType() == FMPPacketType.CONNECTION_ACCEPT){
				handleConnectionAccept(dataPacket);
            }

			if(dataPacket.getType() == FMPPacketType.CONNECTION_ACCEPT_FORWARD) {
                handleConnectionAcceptForward(dataPacket);
            }

			if(dataPacket.getType() == FMPPacketType.CONNECTION_DENY) {
                handleConnectionDeny(dataPacket);
            }

			if(dataPacket.getType() == FMPPacketType.CONNECTION_REGISTER) {
                handleConnectionRegister(dataPacket);
            }

			if(dataPacket.getType() == FMPPacketType.CONNECTION_DEREGISTER) {
                handleConnectionDeregister(dataPacket);
            }

			if(dataPacket.getType() == FMPPacketType.CONNECTION_END) {
                handleConnectionEnd(dataPacket);
            }

			if(dataPacket.getType() == FMPPacketType.DATA_TRANSMIT) {
                handleDataTransmit(dataPacket);
            }

		}catch(FMPException fMPException){

			String message = CloudCommunicationException.DEFAULT_MESSAGE;
			FermatException cause = fMPException;
			String context = "Packet Data: " + dataPacket.toString();
			String possibleReason = "Something failed in the processing of one of the different PacketType, you should check the FMPException that is linked below";
			throw new CloudCommunicationException(message, cause, context, possibleReason);

		}catch(NoSuchElementException ex){
			return;
		}
	}

    /**
     * (non-Javadoc)
     * @see Runnable#run()
     */
	@Override
	public void run(){

        /*
         * while is running
         */
		while(running.get()){

			try{

                /*
                 * Process the connections activity
                 */
				processConnectionsWithNewActivity(selector);

                /*
                 * Process the data packet
                 */
				processDataPackets();

			} catch(CloudCommunicationException ex){
				System.err.println(ex.toString());
			}
		}
	}

    /**
     * Method tha initialize this connection manager like a client
     *
     * @throws CloudCommunicationException
     */
	private void initializeClient() throws CloudCommunicationException {
		try{
			selector = Selector.open();
			clientChannel = SocketChannel.open();
			clientChannel.configureBlocking(false);
			clientChannel.register(selector, SelectionKey.OP_CONNECT);
			clientChannel.connect(communicationChannelAddress.getSocketAddress());
			if(clientChannel.isConnectionPending())
				clientChannel.finishConnect();
		}catch(IOException ex){
			throw wrapNIOSocketIOException(ex);
		}		 
	}

    /**
     * Method tha initialize this connection manager like a server
     *
     * @throws CloudCommunicationException
     */
	private void initializeServer() throws CloudCommunicationException {
		try{
			selector = Selector.open();
			serverChannel = ServerSocketChannel.open();
			serverChannel.configureBlocking(false);
			serverChannel.socket().bind(communicationChannelAddress.getSocketAddress());
			serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		}catch(IOException ex){
			throw wrapNIOSocketIOException(ex);
		}
	}


	private void closeSocketChannelAndCancelKey(final SocketChannel channel, final SelectionKey key) throws CloudCommunicationException {
		try {
			channel.close();
			key.cancel();
		} catch(IOException ex) {
			throw wrapNIOSocketIOException(ex);
		}
	}

    /**
     * Method that process the incoming packet
     *
     * @param packet
     * @param connection
     */
	private void processIncomingPacket(final FMPPacket packet, SelectionKey connection){

        /*
         * If is a new connection request and is not register
         */
		if(!registeredConnections.containsKey(packet.getSender())        &&
                !unregisteredConnections.containsKey(packet.getSender()) &&
                    packet.getType() == FMPPacketType.CONNECTION_REQUEST) {

            /*
             * cache into the unregister connections
             */
            unregisteredConnections.put(packet.getSender(), connection);
        }

        /*
         * cache the packet again
         */
		pendingIncomingMessages.add(packet);
	}

    /**
     * Method tha create a new CloudCommunicationException from a IOException
     *
     * @param iOException
     * @return CloudCommunicationException the new exception
     */
	protected CloudCommunicationException wrapNIOSocketIOException(final IOException iOException) {
		/*
		 * We prepare the values for the exception that is going to be thrown
		 */
		String message = CloudCommunicationException.DEFAULT_MESSAGE;
		FermatException cause = FermatException.wrapException(iOException);
		String context = communicationChannelAddress.toString();
		String possibleReason = "The NIO Socket can be unexpectedly closed, we need to check this more deeply.";
		possibleReason += " This can happen for the socket being already occupied or the port suddenly being yatched or problems ";
		possibleReason += "with the read/write operations on the network.";

		/*
		 * We create the exception and then throw it
		*/
		return new CloudCommunicationException(message, cause, context, possibleReason);
	}

    /**
     * (non-Javadoc)
     * @see FMPPacketHandler#handleConnectionRequest(FMPPacket)
     */
    @Override
    public abstract void handleConnectionRequest(final FMPPacket packet) throws FMPException;

    /**
     * (non-Javadoc)
     * @see FMPPacketHandler#handleConnectionAccept(FMPPacket)
     */
    @Override
    public abstract void handleConnectionAccept(final FMPPacket packet) throws FMPException;

    /**
     * (non-Javadoc)
     * @see FMPPacketHandler#handleConnectionAcceptForward(FMPPacket)
     */
    @Override
    public abstract void handleConnectionAcceptForward(final FMPPacket packet) throws FMPException;

    /**
     * (non-Javadoc)
     * @see FMPPacketHandler#handleConnectionDeny(FMPPacket)
     */
    @Override
    public abstract void handleConnectionDeny(final FMPPacket packet) throws FMPException;

    /**
     * (non-Javadoc)
     * @see FMPPacketHandler#handleConnectionRegister(FMPPacket)
     */
    @Override
    public abstract void handleConnectionRegister(final FMPPacket packet) throws FMPException;

    /**
     * (non-Javadoc)
     * @see FMPPacketHandler#handleConnectionDeregister(FMPPacket)
     */
    @Override
    public abstract void handleConnectionDeregister(final FMPPacket packet) throws FMPException;

    /**
     * (non-Javadoc)
     * @see FMPPacketHandler#handleConnectionEnd(FMPPacket)
     */
    @Override
    public abstract void handleConnectionEnd(final FMPPacket packet) throws FMPException;

    /**
     * (non-Javadoc)
     * @see FMPPacketHandler#handleDataTransmit(FMPPacket)
     */
    @Override
    public abstract void handleDataTransmit(final FMPPacket packet) throws FMPException;

    /**
     * Define the connection manager mode
     */
	protected enum CloudFMPConnectionManagerMode{
		FMP_CLIENT,
		FMP_SERVER
	}
	
}
