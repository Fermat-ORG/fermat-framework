package integration.com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_server.developer.bitdubai.version_1.structure.CloudNetworkServiceManager;

import static org.fest.assertions.api.Assertions.*;
import integration.com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_server.developer.bitdubai.version_1.structure.mocks.MockFMPPacketsFactory;

import org.junit.Before;
import org.junit.Test;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket.FMPPacketType;

public class ConnectionRequestVPNTest extends
		CloudNetworkServiceManagerIntegrationTest {
	
	@Before
	public void setUpParameters() throws Exception{
		setUpAddressInfo(TCP_BASE_TEST_PORT+300);
		setUpKeyPair();
		setUpExecutor(2);		
	}
	
	@Test
	public void ConnectionRequestToVPN_SendValidRequest_ClientGetsConnectionRequest() throws Exception{
		setUpConnections(1);
		assertThat(requestAndRegisterClient()).isNotNull();
		
		FMPPacket requestVPNResponse = requestVPN();
		assertThat(requestVPNResponse.getType()).isEqualTo(FMPPacketType.CONNECTION_REQUEST);
	}
	
	@Test
	public void ConnectionRequestToVPN_SendValidRequest_responsePackageMessageContainsParticipants() throws Exception{
		setUpConnections(2);
		assertThat(requestAndRegisterClient()).isNotNull();
		
		FMPPacket requestVPNResponse = requestVPN();
		String decryptedMessage = AsymmectricCryptography.decryptMessagePrivateKey(requestVPNResponse.getMessage(), MockFMPPacketsFactory.MOCK_PRIVATE_KEY);
		assertThat(decryptedMessage).contains(MockFMPPacketsFactory.MOCK_PUBLIC_KEY);
	}
	
	@Test
	public void ConnectionRequestToVPN_AcceptVPNRequest_ClientGetsAcceptForward() throws Exception{
		setUpConnections(3);
		assertThat(requestAndRegisterClient()).isNotNull();
		
		FMPPacket requestVPNResponse = requestVPN();
		String decryptedMessage = AsymmectricCryptography.decryptMessagePrivateKey(requestVPNResponse.getMessage(), MockFMPPacketsFactory.MOCK_PRIVATE_KEY);
		
		FMPPacket acceptVPNResponse = acceptVPN(requestVPNResponse.getSender(), decryptedMessage);
		assertThat(acceptVPNResponse.getType()).isEqualTo(FMPPacketType.CONNECTION_ACCEPT_FORWARD);
	}
	
	private FMPPacket requestAndRegisterClient() throws Exception {
		FMPPacket request = MockFMPPacketsFactory.mockRequestIntraUserNetworkServicePacket(testManager.getPublicKey());
		testClient.sendMessage(request);
		FMPPacket requestResponse = getResponse();
		FMPPacket register = MockFMPPacketsFactory.mockRegisterPacket(requestResponse.getSender());
		testClient.sendMessage(register);
		return getResponse();
	}
	
	private FMPPacket requestVPN() throws Exception {
		testClient.sendMessage(MockFMPPacketsFactory.mockRequestVPNPacket(MockFMPPacketsFactory.MOCK_PUBLIC_KEY, testManager.getPublicKey()));
		return getResponse();
	}
	
	private FMPPacket acceptVPN(final String destination, final String plainMessage) throws Exception {
		testClient.sendMessage(MockFMPPacketsFactory.mockAcceptVPNPacket(destination, plainMessage));
		return getResponse();
	}

}
