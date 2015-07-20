/*
 * @#CloudFMPConnectionManager.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.cloud;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.FMPPacketFactory;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannelAddress;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud.exceptions.CloudCommunicationException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud.CloudConnectionManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket.FMPPacketType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacketHandler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.GZIPOutputStream;

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

    /*
     * Represent the sleep time (1000 milliseconds)
     */
    private static final long SLEEP_TIME = 1000;

    /**
     * Represent the SELECTOR_SELECT_TIMEOUT value 30
     */
    private static final int SELECTOR_SELECT_TIMEOUT = 30;

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

    /**
     * Represent the queue of outgoing messages for destination
     */
    protected final Map<String, Queue<FMPPacket>> pendingOutgoingPacketCache;

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
        this.pendingOutgoingPacketCache = new ConcurrentHashMap<>();

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
            Iterator<SelectionKey> connections = selector.selectedKeys().iterator();

            /*
             * Iterate over the connections and perform the operation by state of the connection
             */
            while(connections.hasNext()){

                SelectionKey connection = connections.next();

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

			/*
             * Extract the socket chanel from the connection
             */
			ServerSocketChannel serverSocketChannel = (ServerSocketChannel) connection.channel();

			/*
			 * Accept the new connection
			 */
			SocketChannel socketChannel = serverSocketChannel.accept();

            /**
             * If was accepted
             */
			if(socketChannel!=null){

                /*
                 * Set blocking to false
                 */
				socketChannel.configureBlocking(false);

                /*
                 * Register the connection to read
                 */
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

            /*
             * Is connection pending
             */
			if(socketChannel.isConnectionPending()) {

                /*
                 * Complete the connect process
                 */
                socketChannel.finishConnect();
            }

            /*
             * Set blocking to false
             */
			socketChannel.configureBlocking(false);

            /*
             * Register the connection to read
             */
			socketChannel.register(selector, SelectionKey.OP_READ);

            /*
             * Set to running
             */
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

        System.out.println("--- readFromConnection = "+connection);

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
            int bytesRead = channel.read(readBuffer);

           /*
            * Create a string from the array of byte
            */
            StringBuffer stringBuffer = new StringBuffer();


            while (bytesRead > 0) {

                System.out.println("--- bytesRead= "+bytesRead);

                /*
                 * Flips this buffer.
                 */
                readBuffer.flip();

                /*
                 * while the buffer has remaining data
                 */
                while(readBuffer.hasRemaining()){

                    /*
                     * Get byte by byte and append to the string buffer
                     */
                    stringBuffer.append(Character.toString((char) readBuffer.get()));
                }

                /*
                 * Make buffer ready for writing
                 */
                readBuffer.clear();

                /*
                 * Read more data from the channel
                 */
                bytesRead = channel.read(readBuffer);
            }

            System.out.println("--- Received encryptedJson = "+stringBuffer);
            System.out.println("---  encryptedJson.length() = " + stringBuffer.length());


            /*
             * If string buffer have data
             */
            if(stringBuffer.length() > 0){

                /*
                 * Decrypt the data packet json object string
                 */
                String decryptedJson = AsymmectricCryptography.decryptMessagePrivateKey(stringBuffer.toString(), identity.getPrivateKey());


                System.out.println("--- Received json = " + decryptedJson);

                /*
                 * Create a new FMPPacket whit the decrypted string of data
                 */
                FMPPacket incomingPacket = FMPPacketFactory.constructCloudFMPPacket(decryptedJson);

                /**
                 * Process the new incoming packet
                 */
                processIncomingPacket(incomingPacket, connection);

            }

		} catch(UnsupportedEncodingException ex){
			System.out.println("THIS IS NEVER GOING TO HAPPEN");
		} catch (FMPException ex) {
			System.out.println(ex.getMessage());
		}catch(IOException ex){
            closeSocketChannelAndCancelConnection(channel, connection);
            throw wrapNIOSocketIOException(ex);
         }
	}

    /**
     * (non-Javadoc)
     * @see CloudConnectionManager#writeToConnection(SelectionKey)
     */
	@Override
	public synchronized void writeToConnection(final SelectionKey connection) throws CloudCommunicationException {

        System.out.println("--- writeToConnection = "+connection);

		try{

			/*
             * Extract the socket chanel from the connection
             */
            WritableByteChannel writableByteChannel = (SocketChannel) connection.channel();

            /*
             * Get the destination of the next packet
             */
            String destination = (String) connection.attachment();

            /*
             * If the connection have a destination
             */
            if (destination != null){

               /*
                * Get the next packet
                */
                FMPPacket dataPacketToSend = getNextPendingOutgoingPacketCacheForDestination(destination);

                /*
                 * If any pending package
                 */
                if (dataPacketToSend != null){

                    /*
                     * Prepare the write buffer
                     */
                    ByteBuffer writeBuffer = ByteBuffer.allocate(FMPPacket.PACKET_MAX_BYTE_SIZE);
                    writeBuffer.clear();

                    System.out.println("--- Sending jsom = " + dataPacketToSend.toJson());

                    /*
                     * Encrypt the data packet json object string
                     */
                    String encryptedJson = AsymmectricCryptography.encryptMessagePublicKey(dataPacketToSend.toJson(), dataPacketToSend.getDestination());

                    System.out.println("--- Sending encryptedJson = "+encryptedJson);
                    System.out.println("---  encryptedJson.length() = "+encryptedJson.length());

                    /*
                     * Get json format and convert to bytes
                     */
                    InputStream inputStream = new ByteArrayInputStream(encryptedJson.getBytes(CHARSET_NAME));

                    /*
                     * Create a readable channel to put the data to send
                     */
                    ReadableByteChannel inputChannel = Channels.newChannel(inputStream);

                    /*
                     * Read the first chunk of byte to send
                     */
                    int bytesToWrite = inputChannel.read(writeBuffer);

                    /*
                     * While has data to write
                     */
                    while(bytesToWrite != -1 || writeBuffer.position() > 0){

                        System.out.println("--- bytesToWrite= "+bytesToWrite);

                        /*
                         * Flips this buffer.
                         */
                        writeBuffer.flip();

                        /*
                         * Write into the channel the chunk
                         */
                        writableByteChannel.write(writeBuffer);

                        /*
                         * Compact the buffer
                         */
                        writeBuffer.compact();

                        /*
                         * Read another chunk
                         */
                        bytesToWrite = inputChannel.read(writeBuffer);

                    }

                    /*
                     * Mark the connection with new activity read
                     */
                    connection.interestOps(SelectionKey.OP_READ);

                    /*
                     * If the package was sent successfully, it is removed from the cache
                     */
                    removeIntoPendingOutgoingPacketCache(destination, dataPacketToSend);

                    /*
                     * Remove the destination from the connection
                     */
                    connection.attach(null);
                }

            }

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

        /*
         *  Validate the mode to run
         */
		if(mode == CloudFMPConnectionManagerMode.FMP_CLIENT){

            /*
             * Initialize the component
             */
			initializeClient();

            /*
             * Mark tu running
             */
			running.set(clientChannel.isConnected());

		} else if(mode == CloudFMPConnectionManagerMode.FMP_SERVER){

            /*
             * Initialize the component
             */
			initializeServer();

            /*
             * Mark tu running
             */
			running.set(serverChannel.isOpen()  && serverChannel.socket().isBound());
		}

        /*
         * Hold the this thread on the pool
         */
		executorService.execute(this);
	}

    /**
     * (non-Javadoc)
     * @see CloudConnectionManager#stop()
     */
	@Override
	public void stop() throws CloudCommunicationException {

        /*
         * Iterate over all connection
         */
        for (SelectionKey connection : selector.keys()){

            /*
             * Cancel the connection
             */
            connection.cancel();
        }

        /*
         * Mark to no running
         */
        running.set(false);

        /*
         * Shutdown executor service
         */
        executorService.shutdown();
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

            /**
             * Validate are pending incoming messages
             */
            if (!pendingIncomingMessages.isEmpty()){

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

            }

		}catch(FMPException fMPException){

            System.out.println("fMPException = "+fMPException.getMessage());

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

                /*
                 * Sleep for a time
                 */
                Thread.sleep(CloudFMPConnectionManager.SLEEP_TIME);

			} catch(CloudCommunicationException ex){
				System.err.println(ex.toString());
			} catch (InterruptedException e) {
                System.err.println(e.toString());
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

            /*
             * Open the selector
             */
			selector = Selector.open();

            /*
             * Open a socket channel
             */
			clientChannel = SocketChannel.open();

            /*
             * Set the blocking to false
             */
			clientChannel.configureBlocking(false);

            /*
             * Register the channel to connect
             */
			clientChannel.register(selector, SelectionKey.OP_CONNECT);

            /*
             * Connect the channel to the socket address
             */
			clientChannel.connect(communicationChannelAddress.getSocketAddress());

            /*
             * Is connection pending
             */
            if(clientChannel.isConnectionPending()) {

                /*
                 * Complete the connect process
                 */
                clientChannel.finishConnect();
            }

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

            /*
             * Open the selector
             */
			selector = Selector.open();

            /*
             * Open a server socket channel
             */
			serverChannel = ServerSocketChannel.open();

            /*
             * Set the blocking to false
             */
			serverChannel.configureBlocking(false);

            /*
             * Bind a socket to a socket address
             */
			serverChannel.socket().bind(communicationChannelAddress.getSocketAddress());

            /*
             * Register the channel to accept
             */
			serverChannel.register(selector, SelectionKey.OP_ACCEPT);

		}catch(IOException ex){
			throw wrapNIOSocketIOException(ex);
		}
	}

    /**
     * Method that close socket chanel and cancel connection
     *
     * @param channel to close
     * @param connection to cancel
     * @throws CloudCommunicationException
     */
	private void closeSocketChannelAndCancelConnection(final SocketChannel channel, final SelectionKey connection) throws CloudCommunicationException {

		try {

            /*
             * Close the channel
             */
			channel.close();

            /*
             * Cancel the connection
             */
            connection.cancel();

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
		 * Prepare the values for the exception that is going to be thrown
		 */
		String message = CloudCommunicationException.DEFAULT_MESSAGE;
		FermatException cause = FermatException.wrapException(iOException);
		String context = communicationChannelAddress.toString();
		String possibleReason = "The NIO Socket can be unexpectedly closed, we need to check this more deeply.";
		possibleReason += " This can happen for the socket being already occupied or the port suddenly being yatched or problems ";
		possibleReason += "with the read/write operations on the network.";

		/*
		 * Create the exception and then throw it
		*/
		return new CloudCommunicationException(message, cause, context, possibleReason);
	}

    /**
     * Put new packet into the pending outgoing packet cache and then
     * they are sent to the writing process of the connections for
     * every destination
     *
     * @param destination
     * @param fmpPacket
     */
    protected void putIntoPendingOutgoingPacketCache(String destination, FMPPacket fmpPacket){

        /*
         * Validate if exist a queue of message for this destination
         */
        if(pendingOutgoingPacketCache.containsKey(destination)){

            /*
             * If exist the queue get it, and add the new message into the queue
             */
            ((Queue) pendingOutgoingPacketCache.get(destination)).add(fmpPacket);

        }else{

            /*
             * If no exist create a new the queue
             */
            Queue queue = new ConcurrentLinkedQueue<>();

            /*
             * Add the new message into the queue
             */
            queue.add(fmpPacket);

            /*
             * Put the new queue in the pendingOutgoingPacketCache cache
             */
            pendingOutgoingPacketCache.put(destination, queue);
        }


    }

    /**
     * Compress the data string
     *
     * @param str
     * @return String
     * @throws IOException
     */
    public static String compress(String str) throws IOException {

        if (str == null || str.length() == 0) {
            return str;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(str.getBytes());
        gzip.close();

        return out.toString("UTF-8");
    }

    /**
     * Remove a packet from the pending outgoing packet cache after he was sent
     * to the writing process of the connection to your destination
     *
     * @param destination
     * @param fmpPacket
     */
    protected void removeIntoPendingOutgoingPacketCache(String destination, FMPPacket fmpPacket){

        /*
         * Validate if exist a queue of message for this destination
         */
        if(pendingOutgoingPacketCache.containsKey(destination)){

            /*
             * If exist the queue get it, and remove the packet from the queue
             */
            ((Queue) pendingOutgoingPacketCache.get(destination)).remove(fmpPacket);

        }

    }


    /**
     * Remove a packet from the pending outgoing packet cache after he was sent
     * to the writing process of the connection to your destination
     *
     * @param destination
     * @return FMPPacket
     */
    protected FMPPacket getNextPendingOutgoingPacketCacheForDestination(String destination){

        /*
         * Validate if exist a queue of message for this destination
         */
        if(pendingOutgoingPacketCache.containsKey(destination)){


            /*
             * If the queue is no empty
             */
            if (!((Queue) pendingOutgoingPacketCache.get(destination)).isEmpty()){

                 /*
                 * Get the next packet to send
                 */
                  return (FMPPacket) ((Queue) pendingOutgoingPacketCache.get(destination)).iterator().next();

            }

        }

        return null;

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
