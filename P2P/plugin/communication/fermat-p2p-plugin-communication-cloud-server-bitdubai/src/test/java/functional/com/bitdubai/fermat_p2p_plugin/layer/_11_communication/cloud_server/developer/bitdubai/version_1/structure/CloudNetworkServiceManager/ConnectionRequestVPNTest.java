package functional.com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_server.developer.bitdubai.version_1.structure.CloudNetworkServiceManager;

import static org.fest.assertions.api.Assertions.*;
import functional.com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_server.developer.bitdubai.version_1.structure.mocks.MockFMPPacketsFactory;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
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

	@Ignore
	@Test
	public void ConnectionRequestToVPN_SendValidRequest_ClientGetsConnectionRequest() throws Exception{
		setUpConnections(0);
		assertThat(requestAndRegisterClient()).isNotNull();
		FMPPacket requestVPNResponse = requestVPN();
		System.out.println(AsymmetricCryptography.decryptMessagePrivateKey(requestVPNResponse.getMessage(), MockFMPPacketsFactory.MOCK_PRIVATE_KEY));
		assertThat(requestVPNResponse.getType()).isEqualTo(FMPPacketType.CONNECTION_REQUEST);
	}

	@Ignore
	@Test
	public void ConnectionRequestToVPN_SendValidRequest_ResponsePackageMessageContainsParticipants() throws Exception{
		setUpConnections(2);
		assertThat(requestAndRegisterClient()).isNotNull();
		
		FMPPacket requestVPNResponse = requestVPN();
		String decryptedMessage = AsymmetricCryptography.decryptMessagePrivateKey(requestVPNResponse.getMessage(), MockFMPPacketsFactory.MOCK_PRIVATE_KEY);
		assertThat(decryptedMessage).contains(MockFMPPacketsFactory.MOCK_PUBLIC_KEY);
	}

	@Ignore
	@Test
	public void ConnectionRequestToVPN_AcceptVPNRequest_ResponseTypeIsAcceptForward() throws Exception{
		setUpConnections(4);
		assertThat(requestAndRegisterClient()).isNotNull();
		
		FMPPacket requestVPNResponse = requestVPN();
		String decryptedMessage = AsymmetricCryptography.decryptMessagePrivateKey(requestVPNResponse.getMessage(), MockFMPPacketsFactory.MOCK_PRIVATE_KEY);
		
		FMPPacket acceptVPNResponse = acceptVPN(requestVPNResponse.getSender(), decryptedMessage);
		assertThat(acceptVPNResponse.getType()).isEqualTo(FMPPacketType.CONNECTION_ACCEPT_FORWARD);
	}

	@Ignore
	@Test
	public void ConnectionRequestToVPN_RequestMessageIsNotEncrypted_ResponseTypeIsConnectionDeny() throws Exception{
		setUpConnections(6);
		assertThat(requestAndRegisterClient()).isNotNull();
		FMPPacket request = MockFMPPacketsFactory.mockRequestConnectionPacket(testManager.getIdentityPublicKey());
		testClient.sendMessage(request);
		FMPPacket requestResponse= getResponse();
		assertThat(requestResponse.getType()).isEqualTo(FMPPacketType.CONNECTION_DENY);
	}

	@Ignore
	@Test
	public void ConnectionRequestToVPN_RequestMessageIsNotVPNRequest_ResponseTypeIsConnectionDeny() throws Exception{
		setUpConnections(8);
		assertThat(requestAndRegisterClient()).isNotNull();
		FMPPacket requestResponse = requestConnection();
		assertThat(requestResponse.getType()).isEqualTo(FMPPacketType.CONNECTION_DENY);
	}

	@Ignore
	@Test
	public void ConnectionRequestToVPN_NoMoreAvailablePorts_ResponseTypeIsConnectionDeny() throws Exception{
		setUpConnections(10);
		assertThat(requestAndRegisterClient()).isNotNull();
		FMPPacket requestResponse = requestVPN();
		assertThat(requestResponse.getType()).isEqualTo(FMPPacketType.CONNECTION_REQUEST);
		FMPPacket requestResponse2 = requestVPN();
		assertThat(requestResponse2.getType()).isEqualTo(FMPPacketType.CONNECTION_DENY);
	}
	
	private FMPPacket requestAndRegisterClient() throws Exception {
		FMPPacket request = requestConnection();
		assertThat(request).isNotNull();
		FMPPacket register = registerConnection();
		return register;
	}
	
	private FMPPacket requestVPN() throws Exception {
		testClient.sendMessage(MockFMPPacketsFactory.mockRequestConnectionNetworkServiceToVPNPacket(testNetworkService, MockFMPPacketsFactory.MOCK_PUBLIC_KEY, MockFMPPacketsFactory.MOCK_PUBLIC_KEY, testManager.getIdentityPublicKey()));
		return getResponse();
	}
	
	private FMPPacket acceptVPN(final String destination, final String plainMessage) throws Exception {
		testClient.sendMessage(MockFMPPacketsFactory.mockAcceptVPNPacket(destination, plainMessage));
		return getResponse();
	}

}
