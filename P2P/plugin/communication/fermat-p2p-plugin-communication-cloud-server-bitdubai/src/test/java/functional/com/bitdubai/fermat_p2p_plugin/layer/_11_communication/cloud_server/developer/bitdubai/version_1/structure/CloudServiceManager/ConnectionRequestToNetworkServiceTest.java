package functional.com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_server.developer.bitdubai.version_1.structure.CloudServiceManager;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import functional.com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_server.developer.bitdubai.version_1.structure.mocks.MockFMPPacketsFactory;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.CommunicationChannelAddressFactory;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannelAddress;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket.FMPPacketType;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServices;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.structure.CloudNetworkServiceManager;

/**
 * Created by jorgeejgonzalez on 27/04/15.
 */
public class ConnectionRequestToNetworkServiceTest extends CloudServiceIntegrationTest{

	public void registerConnection() throws Exception{
		FMPPacket register = MockFMPPacketsFactory.mockRegisterConnectionPacket(testManager.getIdentityPublicKey());
		testClient.sendMessage(register);
		FMPPacket response = getResponse();
		assertThat(response).isNotNull();
	}
	
	public CloudNetworkServiceManager createNetworkServiceManager(int tcpPadding){
		String host = "localhost";
		Integer port = Integer.valueOf(tcpPadding);
		CommunicationChannelAddress address = CommunicationChannelAddressFactory.constructCloudAddress(host, port);
		ExecutorService executor = Executors.newFixedThreadPool(30);
		ECCKeyPair keyPair = new ECCKeyPair();
		NetworkServices networkService = NetworkServices.INTRA_USER;
		Set<Integer> vpnPorts = new HashSet<Integer>();
		vpnPorts.add(port+100*tcpPadding+1);
		vpnPorts.add(port+100*tcpPadding+2);
		vpnPorts.add(port+100*tcpPadding+3);
		return new CloudNetworkServiceManager(address, executor, keyPair, networkService, vpnPorts);
	}
	
	@Before
	public void setUpParameters() throws Exception{
		setUpAddressInfo(TCP_BASE_TEST_PORT+300);
		setUpKeyPair();
		setUpExecutor(2);
	}

	@Ignore
	@Test
	public void ConnectionRegister_SendValidRequest_ClientGetsAcceptForward() throws Exception{
		setUpConnections(1);
		testManager.registerNetworkServiceManager(createNetworkServiceManager(testManager.getCommunicationChannelAddress().getPort()+1000));
		requestConnection();
		registerConnection();
		
		FMPPacket requestNetworkService = MockFMPPacketsFactory.mockRequestConnectionNetworkServicePacket(NetworkServices.INTRA_USER,testManager.getIdentityPublicKey());
		testClient.sendMessage(requestNetworkService);
		FMPPacket response = getResponse();
		assertThat(response.getType()).isEqualTo(FMPPacketType.CONNECTION_ACCEPT_FORWARD);
		System.out.println(AsymmectricCryptography.decryptMessagePrivateKey(response.getMessage(), MockFMPPacketsFactory.MOCK_PRIVATE_KEY));
	}

	@Ignore
	@Test
	public void ConnectionRegister_SendValidRequest_ClientGetsDeny() throws Exception{
		setUpConnections(2);
		testManager.registerNetworkServiceManager(createNetworkServiceManager(testManager.getCommunicationChannelAddress().getPort()+1000));
		requestConnection();
		registerConnection();
		
		FMPPacket requestNetworkService = MockFMPPacketsFactory.mockRequestConnectionNetworkServicePacket(NetworkServices.MONEY, testManager.getIdentityPublicKey());
		testClient.sendMessage(requestNetworkService);
		FMPPacket response = getResponse();
		assertThat(response.getType()).isEqualTo(FMPPacketType.CONNECTION_DENY);
		System.out.println(AsymmectricCryptography.decryptMessagePrivateKey(response.getMessage(), MockFMPPacketsFactory.MOCK_PRIVATE_KEY));
	}

}