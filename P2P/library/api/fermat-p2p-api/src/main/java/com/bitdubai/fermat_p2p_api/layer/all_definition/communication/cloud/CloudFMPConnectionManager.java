/*
 * @#CloudFMPConnectionManager.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.cloud;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
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
     * Represent the queue of incoming packets cache
     */
    protected final Queue<FMPPacket> pendingIncomingPackets;

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
     * @param keyPair
     * @param mode
     * @throws IllegalArgumentException
     */
	public CloudFMPConnectionManager(final CommunicationChannelAddress communicationChannelAddress, final ExecutorService executorService, final ECCKeyPair keyPair, final CloudFMPConnectionManagerMode mode) throws IllegalArgumentException{

        //Validate argument
		if(communicationChannelAddress == null ||
                executorService        == null ||
                keyPair == null) {

            throw new IllegalArgumentException();
        }

		this.communicationChannelAddress = communicationChannelAddress;
		this.executorService             = executorService;
		this.identity                    = keyPair;
		this.mode                        = mode;
		this.running                     = new AtomicBoolean(false);
        this.unregisteredConnections     = new ConcurrentHashMap<>();
        this.registeredConnections       = new ConcurrentHashMap<>();
        this.pendingIncomingPackets = new ConcurrentLinkedQueue<>();
        this.pendingOutgoingPacketCache = new ConcurrentHashMap<>();

        this.unregisteredConnections.clear();
        this.registeredConnections.clear();
        this.pendingIncomingPackets.clear();
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

            /**
             * Clear all connection select with activity
             */
            selector.selectedKeys().clear();

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

        System.out.println("CloudFMPConnectionManager - Starting read data from Connection ");
        System.out.println(" -------------------------------------------------------- ");

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

                System.out.println("CloudFMPConnectionManager - bytesRead= " + bytesRead);

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

                /*
                 * Validate that the channel is no closed by the client
                 */
                if(bytesRead == -1){

                    /*
                     * Close the channel
                     */
                    closeSocketChannelAndCancelConnection(channel, connection);

                    String message = CloudCommunicationException.DEFAULT_MESSAGE;
                    String context = "";
                    String possibleReason = "The channel has reached end-of-stream";

                    /*
                     * Throw a new  CloudCommunicationException
                     */
                    throw new CloudCommunicationException(message, null, context, possibleReason);
                }

            }


            System.out.println("CloudFMPConnectionManager - Data length received = " + stringBuffer.length());


            /*
             * If string buffer have data
             */
            if(stringBuffer.length() > 0){

                /*
                 * Decrypt the data packet json object string
                 */
                String decryptedJson = AsymmetricCryptography.decryptMessagePrivateKey(stringBuffer.toString(), identity.getPrivateKey());


                System.out.println("CloudFMPConnectionManager - Received packet json = " + decryptedJson);

                /*
                 * Create a new FermatPacketCommunication whit the decrypted string of data
                 */
                FMPPacket incomingPacket = FMPPacketFactory.constructCloudFMPPacket(decryptedJson);

                /**
                 * Process the new incoming packet
                 */
                processIncomingPacket(incomingPacket, connection);

            }else {

                System.out.println("CloudFMPConnectionManager - No Data received, channel client closed");

                /*
                 * Close the channel
                 */
                closeSocketChannelAndCancelConnection(channel, connection);
            }

		} catch (IllegalArgumentException ex){

            System.out.println("CloudFMPConnectionManager - Invalid MAC, package chuck loose");

            ex.printStackTrace();

            //TODO: RESPOND PACKAGE LOOSE TO CLIENT

        } catch(UnsupportedEncodingException ex){

            System.out.println("CloudFMPConnectionManager - THIS IS NEVER GOING TO HAPPEN, Error = " + ex.getMessage());

            /*
             * Close the channel
             */
            closeSocketChannelAndCancelConnection(channel, connection);

		} catch (FMPException ex) {

            System.out.println("CloudFMPConnectionManager - Error = " + ex.getMessage());

            /*
             * Close the channel
             */
            closeSocketChannelAndCancelConnection(channel, connection);

		}catch(IOException ex){

            /*
             * Close the channel
             */
            closeSocketChannelAndCancelConnection(channel, connection);

            /*
             * Throw a new  CloudCommunicationException
             */
            throw wrapNIOSocketIOException(ex);
         }

        System.out.println(" -------------------------------------------------------- ");
        System.out.println("");
	}

    /**
     * (non-Javadoc)
     * @see CloudConnectionManager#writeToConnection(SelectionKey)
     */
	@Override
	public synchronized void writeToConnection(final SelectionKey connection) throws CloudCommunicationException {

        System.out.println("CloudFMPConnectionManager - Starting write to Connection ");
        System.out.println(" -------------------------------------------------------- ");

        WritableByteChannel writableByteChannel = null;

		try{

			/*
             * Extract the socket chanel from the connection
             */
            writableByteChannel = (SocketChannel) connection.channel();

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

                    System.out.println("CloudFMPConnectionManager - FermatPacketCommunication json to send = " + dataPacketToSend.toJson());

                    /*
                     * Encrypt the data packet json object string
                     */
                    String encryptedJson = AsymmetricCryptography.encryptMessagePublicKey(dataPacketToSend.toJson(), dataPacketToSend.getDestination());

                    //System.out.println("CloudFMPConnectionManager - Encrypted packet json to send  = " + encryptedJson);
                    System.out.println("CloudFMPConnectionManager - FermatPacketCommunication data length = " + encryptedJson.length());

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

                        System.out.println("CloudFMPConnectionManager - bytes to write " + bytesToWrite);

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

            /*
             * Close the channel
             */
            closeSocketChannelAndCancelConnection((SocketChannel) writableByteChannel, connection);

            /*
             * Throw a new  CloudCommunicationException
             */
			throw wrapNIOSocketIOException(ex);
		}

        System.out.println(" -------------------------------------------------------- ");
        System.out.println("");
	}

    /**
     * (non-Javadoc)
     * @see CloudConnectionManager#start()
     */
	@Override
	public void start() throws CloudCommunicationException {

        System.out.println("CloudFMPConnectionManager - Starting in mode " + mode);

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
            if (!pendingIncomingPackets.isEmpty()){

                dataPacket = pendingIncomingPackets.remove();

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

            System.out.println("CloudFMPConnectionManager - fMPException = " + fMPException.getMessage());

			String message = CloudCommunicationException.DEFAULT_MESSAGE;
			FermatException cause = fMPException;
			String context = "FermatPacketCommunication Data: " + dataPacket.toString();
			String possibleReason = "Something failed in the processing of one of the different FermatPacketType, you should check the FMPException that is linked below";
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

            System.out.println("CloudFMPConnectionManager - Binding to " + communicationChannelAddress.getSocketAddress());

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
		pendingIncomingPackets.add(packet);

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
     * @return FermatPacketCommunication
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
