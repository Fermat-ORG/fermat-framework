package com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannelAddress;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud.exceptions.CloudCommunicationException;

/*
 *	created by jorgeejgonzalez
 */
public interface CloudConnectionManager extends CloudConnectionAgent {
	public CommunicationChannelAddress getAddress();
	public void iterateSelectedKeys(final Selector selector) throws CloudCommunicationException;
	public void acceptKey(final SelectionKey key) throws CloudCommunicationException;
	public void connectToKey(final SelectionKey key) throws CloudCommunicationException;
	public void readFromKey(final SelectionKey key) throws CloudCommunicationException;
	public void writeToKey(final SelectionKey key) throws CloudCommunicationException;
}
