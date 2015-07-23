package functional.com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_server.developer.bitdubai.version_1.structure.CloudServiceManager;

import static org.fest.assertions.api.Assertions.*;
import functional.com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_server.developer.bitdubai.version_1.structure.mocks.MockFMPPacketsFactory;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket.FMPPacketType;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;



/**
 * Created by jorgeejgonzalez on 27/04/15.
 */
public class ConnectionRequestTest extends CloudServiceIntegrationTest {
	
	@Before
	public void setUpParameters() throws Exception{
		setUpAddressInfo(TCP_BASE_TEST_PORT+200);
		setUpKeyPair();
		setUpExecutor(2);		
	}

	@Ignore
	@Test
	public void ConnectionRequest_SendValidRequest_ClientGetsResponse() throws Exception{
		setUpConnections(0);
		FMPPacket request = MockFMPPacketsFactory.mockRequestConnectionPacket(testManager.getIdentityPublicKey());
		testClient.sendMessage(request);
		FMPPacket response = getResponse();
		assertThat(response).isNotNull();
	}

	@Ignore
	@Test
	public void ConnectionRequest_SendValidRequest_ResponseTypeConnectionAccept() throws Exception{
		setUpConnections(1);
		FMPPacket request = MockFMPPacketsFactory.mockRequestConnectionPacket(testManager.getIdentityPublicKey());
		testClient.sendMessage(request);				
		FMPPacket response = getResponse();
		assertThat(response.getType()).isEqualTo(FMPPacketType.CONNECTION_ACCEPT);
	}

	@Ignore
	@Test
	public void ConnectionRequest_SendValidRequest_ResponseDestinationEqualsRequestSender() throws Exception{
		setUpConnections(2);
		FMPPacket request = MockFMPPacketsFactory.mockRequestConnectionPacket(testManager.getIdentityPublicKey());
		testClient.sendMessage(request);
		FMPPacket response = getResponse();
		assertThat(response.getDestination()).isEqualTo(request.getSender());
	}

	@Ignore
	@Test
	public void ConnectionRequest_SendValidRequest_ResponseMessagePublicKey() throws Exception{
		setUpConnections(3);
		FMPPacket request = MockFMPPacketsFactory.mockRequestConnectionPacket(testManager.getIdentityPublicKey());
		testClient.sendMessage(request);
		FMPPacket response = getResponse();
		assertThat(response.getMessage()).isEqualTo(testPublicKey);
	}

	@Ignore
	@Test
	public void ConnectionRequest_SendValidRequest_ResponseSignatureVerified() throws Exception{
		setUpConnections(4);
		FMPPacket request = MockFMPPacketsFactory.mockRequestConnectionPacket(testManager.getIdentityPublicKey());
		testClient.sendMessage(request);		
		FMPPacket response = getResponse();		
		boolean signatureVerification = AsymmectricCryptography.verifyMessageSignature(response.getSignature(), response.getMessage(), response.getSender());
		assertThat(signatureVerification).isTrue();	
	}

	@Ignore
	@Test
	public void ConnectionRequest_RequestDestinationInvalid_NoResponse() throws Exception{
		setUpConnections(5);
		FMPPacket request = MockFMPPacketsFactory.mockRequestConnectionPacket();
		testClient.sendMessage(request);
		FMPPacket response = getResponse();
		assertThat(response).isNull();
	}

}