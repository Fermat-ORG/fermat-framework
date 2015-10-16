package com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.structure;

import java.nio.channels.SelectionKey;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.*;
import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServices;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.FMPPacketFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.cloud.CloudFMPConnectionManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannelAddress;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket.FMPPacketType;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.exceptions.RegisteringAddressHasNotRequestedConnectionException;

public class CloudNetworkServiceVPN extends CloudFMPConnectionManager {

	private static final int HASH_PRIME_NUMBER_PRODUCT = 2999;
	private static final int HASH_PRIME_NUMBER_ADD = 4013;
	
	private NetworkServices networkService;
	private Set<String> participants = new ConcurrentSkipListSet<String>();
	
	public CloudNetworkServiceVPN(final CommunicationChannelAddress address, final ExecutorService executor, final com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair keyPair, final NetworkServices networkService, final Collection<String> participants) throws IllegalArgumentException{
		super(address, executor, new ECCKeyPair(keyPair.getPrivateKey(), keyPair.getPublicKey()), CloudFMPConnectionManagerMode.FMP_SERVER);
		if(networkService == null)
			throw new IllegalArgumentException();
		this.networkService = networkService;
		
		if(participants == null || participants.isEmpty())
			throw new IllegalArgumentException();
		for(String participant: participants)
			this.participants.add(participant);
	}
	
	@Override
	public void handleConnectionRequest(final FMPPacket packet) throws FMPException{
		if(participants.contains(packet.getSender()) && participants.contains(packet.getDestination()))
			requestUnregisteredConnection(packet);
		else
			denyConnectionRequest(packet, "Address Is Not A Participant Of This VPN");
	}	
	
	@Override
	public void handleConnectionAccept(final FMPPacket packet) throws FMPException{
	}

	@Override
	public void handleConnectionAcceptForward(final FMPPacket packet) throws FMPException{
	}

	@Override
	public void handleConnectionDeny(final FMPPacket packet) throws FMPException{
	}

	@Override
	public void handleConnectionRegister(final FMPPacket packet) throws FMPException{
		if(unregisteredConnections.containsKey(packet.getSender()))
			registerConnection(packet);
		else
			throw constructRegisteringAddressHasNotRequestedConnectionException(packet);
	}

	@Override
	public void handleConnectionDeregister(final FMPPacket packet) throws FMPException{
	}

	@Override
	public void handleConnectionEnd(final FMPPacket packet) throws FMPException{
	}

	@Override
	public void handleDataTransmit(final FMPPacket packet) throws FMPException{
		sendPacketToRecipient(packet);
	}
	
	private void sendPacketToRecipient(final FMPPacket packet) throws FMPException{

        FMPPacket dataPacket = FMPPacketFactory.constructCloudFMPPacketEncryptedAndSinged(packet.getDestination(),         //sender
                                                                                            packet.getSender(),                 //destination
                                                                                            packet.getMessage(),                         // message
                                                                                            FMPPacketType.DATA_TRANSMIT,
                                                                                            networkService,
                                                                                            identity.getPrivateKey());

		writeToRegisteredConnection(dataPacket);
	}

	public NetworkServices getNetworkService(){
		return networkService;
	}
	
	public Set<String> getParticipants() {
		return participants;
	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof CloudNetworkServiceVPN))
			return false;
		
		CloudNetworkServiceVPN compare = (CloudNetworkServiceVPN) o;
		return communicationChannelAddress.equals(compare.getCommunicationChannelAddress()) && networkService == compare.getNetworkService() && participants.equals(compare.getParticipants());
	}
	
	@Override
	public int hashCode(){
		int c = 0;
		c += communicationChannelAddress.hashCode();
		c += networkService.hashCode();
		c += participants.hashCode();
		return 	HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
	}
	
	private void requestUnregisteredConnection(final FMPPacket packet) throws FMPException {
		NetworkServices networkService;
		try{
			networkService = NetworkServices.valueOf(AsymmetricCryptography.decryptMessagePrivateKey(packet.getMessage(), identity.getPrivateKey()));
		} catch(Exception ex){
			denyConnectionRequest(packet, ex.getMessage());
			return;
		}
		
		if(this.networkService.equals(networkService))
			acceptConnectionRequest(packet);
		else
			denyConnectionRequest(packet, "NETWORK SERVICE " + networkService + " IS NOT SUPPORTED BY THE SERVER");
	}
	
	private void acceptConnectionRequest(FMPPacket packet)  throws FMPException {

        FMPPacket responsePacket = FMPPacketFactory.constructCloudFMPPacketEncryptedAndSinged(identity.getPublicKey(),         //sender
                packet.getSender(),                 //destination
                networkService.toString(),                         // message
                FMPPacketType.CONNECTION_ACCEPT,
                networkService,
                identity.getPrivateKey());

		writeToUnregisteredConnection(responsePacket);
	}
	
	private void denyConnectionRequest(FMPPacket packet, final String reason) throws FMPException{

        FMPPacket responsePacket = FMPPacketFactory.constructCloudFMPPacketEncryptedAndSinged(identity.getPublicKey(),         //sender
                packet.getSender(),                 //destination
                reason,                         // message
                FMPPacketType.CONNECTION_DENY,
                networkService,
                identity.getPrivateKey());

        writeToUnregisteredConnection(responsePacket);
	}
	
	private void registerConnection(final FMPPacket packet) throws FMPException {
		SelectionKey connection = unregisteredConnections.get(packet.getSender());
		registeredConnections.put(packet.getSender(), connection);
		unregisteredConnections.remove(packet.getSender());

        FMPPacket responsePacket = FMPPacketFactory.constructCloudFMPPacketEncryptedAndSinged(identity.getPublicKey(),         //sender
                                                                                                packet.getSender(),                 //destination
                                                                                                "REGISTERED",                         // message
                                                                                                FMPPacketType.DATA_TRANSMIT,
                                                                                                networkService,
                                                                                                identity.getPrivateKey());

        writeToUnregisteredConnection(responsePacket);
	}
	
	private void writeToUnregisteredConnection(final FMPPacket packet) {
		SelectionKey connection = unregisteredConnections.get(packet.getDestination());
		if(connection!=null)
			writeToConnection(packet, connection);
	}
	
	private void writeToRegisteredConnection(final FMPPacket packet) {
		SelectionKey connection = registeredConnections.get(packet.getDestination());
		if(connection!=null)
			writeToConnection(packet, connection);
	}
	
	private void writeToConnection(final FMPPacket packet, final SelectionKey connection){
		connection.attach(packet);
		connection.interestOps(SelectionKey.OP_WRITE);
	}

	private RegisteringAddressHasNotRequestedConnectionException constructRegisteringAddressHasNotRequestedConnectionException(final FMPPacket packet){
		String message = RegisteringAddressHasNotRequestedConnectionException.DEFAULT_MESSAGE;
		String possibleReason = "This can happen whenever we receive a CONNECTION_REGISTER packet before a CONNECTION_REQUEST packet";
		possibleReason += " even though this might be due to improper client message flow, it can also be a threading problem";
		possibleReason += " as we can process a register packet for a connection that has already been registered, we need to improve this";

		String context = "FermatPacketCommunication Data: " + packet.toString();
		context += RegisteringAddressHasNotRequestedConnectionException.CONTEXT_CONTENT_SEPARATOR;
		context += "Is this connection already registered? " + registeredConnections.containsKey(packet.getSender());

		return new RegisteringAddressHasNotRequestedConnectionException(message, null, context, possibleReason);

	}

}
