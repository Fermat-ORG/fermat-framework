/*
 * @#CloudConnectionManager.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannelAddress;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud.exceptions.CloudCommunicationException;

/**
 * The interface <code>com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud.CloudConnectionManager</code> is
 * mark the basic method to have a class that use a connection to implement the communication using Java NIO.
 * <p/>
 *
 * Created by Jorge Gonzales
 * Update by Roberto Requena - (rart3001@gmail.com) on 08/06/15.
 *
 * @version 1.0
 */
public interface CloudConnectionManager extends CloudConnectionAgent {

    /**
     * Return the comunication channel address to this connection
     *
     * @return CommunicationChannelAddress
     */
	public CommunicationChannelAddress getCommunicationChannelAddress();

    /**
     * Method that process the different states that have a connection
     *
     * @param selector tha hold the communication channel
     * @throws CloudCommunicationException when error occurred
     */
	public void processConnectionsWithNewActivity(final Selector selector) throws CloudCommunicationException;

    /**
     * Method that accept a new connection
     *
     * @param connection
     * @throws CloudCommunicationException
     */
	public void acceptNewConnection(final SelectionKey connection) throws CloudCommunicationException;

    /**
     * Method that connect to a connection
     *
     * @param connection to connect
     * @throws CloudCommunicationException
     */
	public void connectToConnection(final SelectionKey connection) throws CloudCommunicationException;

    /**
     * Method that read data from a connection
     *
     * @param connection to read
     * @throws CloudCommunicationException
     */
	public void readFromConnection(final SelectionKey connection) throws CloudCommunicationException;

    /**
     * Method that write data into a connection
     *
     * @param connection into write
     * @throws CloudCommunicationException
     */
	public void writeToConnection(final SelectionKey connection) throws CloudCommunicationException;
}
