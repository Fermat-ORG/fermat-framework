package com.bitdubai.fermat_api.layer._1_definition.communication.cloud;

import java.net.InetSocketAddress;

import com.bitdubai.fermat_api.layer._10_communication.CommunicationChannelAddress;

/**
 * Created by jorgeejgonzalez
 */
public class CloudConnectionAddress implements CommunicationChannelAddress {

	private static final int HASH_PRIME_NUMBER_PRODUCT = 1721;
	private static final int HASH_PRIME_NUMBER_ADD = 1789;
	
	private final String host;
	private final Integer port;
	private final InetSocketAddress socketAddress;
	
	public CloudConnectionAddress(final String host, final Integer port) throws IllegalArgumentException{
		if(host == null)
			throw new IllegalArgumentException();
		if(host.isEmpty())
			throw new IllegalArgumentException();
		if(port == null)
			throw new IllegalArgumentException();
		if(port.intValue() <= 1000)
			throw new IllegalArgumentException();
		try {
			this.host = host;
			this.port = port;
			this.socketAddress = new InetSocketAddress(host, port);
		} catch(Exception ex){
			throw new IllegalArgumentException(ex.getMessage());
		}
	}

	@Override
	public String getHost() {
		return host;
	}

	@Override
	public Integer getPort() {
		return port;
	}

	@Override
	public InetSocketAddress getSocketAddress() {
		return socketAddress;
	}

	@Override
	public boolean equals(Object o){
		if(o instanceof CloudConnectionAddress) {
			CloudConnectionAddress compare = (CloudConnectionAddress) o;
			return getHost().equals(compare.getHost()) && getPort().equals(compare.getPort());
		}
		return false;
	}

	@Override
	public int hashCode() {
		int c = 0;
		if(host != null)
			c += host.hashCode();
		if(port != null)
			c += port.hashCode();
		return 	HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
	}

}
