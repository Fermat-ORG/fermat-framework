package com.bitdubai.fermat_api.layer._10_communication.cloud;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

import com.bitdubai.fermat_api.layer._10_communication.CommunicationChannelAddress;

/*
 *	created by jorgeejgonzalez
 */
public interface CloudConnectionManager extends CloudConnectionAgent {
	public CommunicationChannelAddress getAddress();
	public void iterateSelectedKeys(final Selector selector) throws CloudConnectionException;
	public void acceptKey(final SelectionKey key) throws CloudConnectionException;
	public void connectToKey(final SelectionKey key) throws CloudConnectionException;
	public void readFromKey(final SelectionKey key) throws CloudConnectionException;
	public void writeToKey(final SelectionKey key) throws CloudConnectionException;
}
