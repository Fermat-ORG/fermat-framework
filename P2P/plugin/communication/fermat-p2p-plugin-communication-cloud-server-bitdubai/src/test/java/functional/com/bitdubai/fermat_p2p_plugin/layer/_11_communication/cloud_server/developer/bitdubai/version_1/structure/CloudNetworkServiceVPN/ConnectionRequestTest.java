package functional.com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_server.developer.bitdubai.version_1.structure.CloudNetworkServiceVPN;

import static org.fest.assertions.api.Assertions.*;

import java.util.HashSet;

import functional.com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_server.developer.bitdubai.version_1.structure.mocks.MockFMPPacketsFactory;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket.FMPPacketType;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServices;

/**
 * Created by jorgeejgonzalez on 27/04/15.
 */
public class ConnectionRequestTest extends CloudNetworkServiceVPNIntegrationTest {
	
	
	@Before
	public void setUpParameters() throws Exception{
		setUpAddressInfo(TCP_BASE_TEST_PORT+200);
		setUpKeyPair();
		setUpExecutor(2);
		testParticipants = new HashSet<String>();
		testParticipants.add(MockFMPPacketsFactory.MOCK_PUBLIC_KEY);
	}

	@Ignore
	@Test
	public void ConnectionRequest_SendValidRequest_ClientGetsResponse() throws Exception{
		setUpConnections(0);
		FMPPacket response = requestConnection();
		assertThat(response).isNotNull();
	}

	@Ignore
	@Test
	public void ConnectionRequest_SendValidRequest_ResponseTypeConnectionAccept() throws Exception{
		setUpConnections(2);
		FMPPacket response = requestConnection();
		assertThat(response.getType()).isEqualTo(FMPPacketType.CONNECTION_ACCEPT);
	}

	@Ignore
	@Test
	public void ConnectionRequest_SendValidRequest_ResponseDestinationEqualsRequestSender() throws Exception{
		setUpConnections(4);
		FMPPacket response = requestConnection();
		assertThat(response.getDestination()).isEqualTo(MockFMPPacketsFactory.MOCK_PUBLIC_KEY);
	}

	@Ignore
	@Test
	public void ConnectionRequest_SendValidRequest_ResponseSignatureVerified() throws Exception{
		setUpConnections(6);
		FMPPacket response = requestConnection();		
		boolean signatureVerification = AsymmetricCryptography.verifyMessageSignature(response.getSignature(), response.getMessage(), response.getSender());
		assertThat(signatureVerification).isTrue();	
	}

	@Ignore
	@Test
	public void ConnectionRequest_SendRequestForDifferentNetworkService_ResponseConnectionDeny() throws Exception{
		setUpConnections(8);
		FMPPacket request = MockFMPPacketsFactory.mockRequestConnectionNetworkServicePacket(NetworkServices.MONEY, MockFMPPacketsFactory.MOCK_PUBLIC_KEY, MockFMPPacketsFactory.MOCK_PUBLIC_KEY);
		testClient.sendMessage(request);
		FMPPacket response = getResponse();		
		assertThat(response.getType()).isEqualTo(FMPPacketType.CONNECTION_DENY);
	}

	@Ignore
	@Test
	public void ConnectionRequest_SenderIsNotParticipant_ResponseConnectionDeny() throws Exception{
		setUpConnections(10);
		FMPPacket request = MockFMPPacketsFactory.mockRequestConnectionNetworkServiceVPNPacket(testNetworkService, new ECCKeyPair().getPublicKey(), MockFMPPacketsFactory.MOCK_PUBLIC_KEY, testVPN.getIdentityPublicKey());
		testClient.sendMessage(request);
		FMPPacket response = getResponse();
		assertThat(response.getType()).isEqualTo(FMPPacketType.CONNECTION_DENY);
	}

	@Ignore
	@Test
	public void ConnectionRequest_RequestDestinationInvalid_ResponseTypeConnectionDeny() throws Exception{
		setUpConnections(12);
		FMPPacket request = MockFMPPacketsFactory.mockRequestConnectionNetworkServicePacket(testNetworkService, MockFMPPacketsFactory.MOCK_PUBLIC_KEY, new ECCKeyPair().getPublicKey());
		testClient.sendMessage(request);
		FMPPacket response = getResponse();
		assertThat(response.getType()).isEqualTo(FMPPacketType.CONNECTION_DENY);
	}

	@Ignore
	@Test
	public void ConnectionRequest_RequestDoesNotIDentifyNetworkService_ResponseTypeConnectionDeny() throws Exception{
		setUpConnections(14);
		FMPPacket request = MockFMPPacketsFactory.mockRequestConnectionPacket(MockFMPPacketsFactory.MOCK_PUBLIC_KEY, MockFMPPacketsFactory.MOCK_PUBLIC_KEY);
		testClient.sendMessage(request);
		FMPPacket response = getResponse();
		assertThat(response.getType()).isEqualTo(FMPPacketType.CONNECTION_DENY);
	}

}