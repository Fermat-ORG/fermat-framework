package integration.com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_server.developer.bitdubai.version_1.cloud.CloudServiceManager;

import static org.fest.assertions.api.Assertions.*;

import integration.com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_server.developer.bitdubai.version_1.cloud.mocks.MockFMPPacketsFactory;

import org.junit.Before;
import org.junit.Test;

import com.bitdubai.fermat_api.layer.p2p_communication.fmp.FMPPacket;
import com.bitdubai.fermat_api.layer.p2p_communication.fmp.FMPPacket.FMPPacketType;
import com.bitdubai.fermat_api.layer._1_definition.crypto.asymmetric.AsymmectricCryptography;

/**
 * Created by jorgeejgonzalez on 27/04/15.
 */
public class ConnectionRegisterTest extends CloudServiceIntegrationTest{

	@Before
	public void setUpParameters() throws Exception{
		setUpAddressInfo(TCP_BASE_TEST_PORT+100);
		setUpKeyPair();
		setUpExecutor(2);		
	}
	
	@Test
	public void ConnectionRegister_SendValidRequest_ClientGetsResponse() throws Exception{
		setUpConnections(0);
		requestConnection();
		FMPPacket register = MockFMPPacketsFactory.mockRegisterPacket(testManager.getPublicKey());
		testClient.sendMessage(register);
		FMPPacket response = getResponse();
		assertThat(response).isNotNull();
	}
	
	@Test
	public void ConnectionRegister_SendValidRequest_ResponsePacketTypeDataTransmit() throws Exception{
		setUpConnections(1);
		requestConnection();
		FMPPacket register = MockFMPPacketsFactory.mockRegisterPacket(testManager.getPublicKey());
		testClient.sendMessage(register);
		FMPPacket response = getResponse();
		assertThat(response.getType()).isEqualTo(FMPPacketType.DATA_TRANSMIT);
	}
	
	@Test
	public void ConnectionRegister_SendValidRequest_ResponseMessageDecrypted() throws Exception{
		setUpConnections(2);
		requestConnection();
		FMPPacket register = MockFMPPacketsFactory.mockRegisterPacket(testManager.getPublicKey());
		testClient.sendMessage(register);
		FMPPacket response = getResponse();
		String decryptedMessage = AsymmectricCryptography.decryptMessagePrivateKey(response.getMessage(), MockFMPPacketsFactory.MOCK_PRIVATE_KEY);		
		assertThat(decryptedMessage).isEqualTo("REGISTERED");
	}
	
	@Test
	public void ConnectionRegister_SendValidRequest_ResponseSignatureIsValid() throws Exception{
		setUpConnections(3);
		requestConnection();
		FMPPacket register = MockFMPPacketsFactory.mockRegisterPacket(testManager.getPublicKey());
		testClient.sendMessage(register);
		FMPPacket response = getResponse();
		boolean signatureVerification = AsymmectricCryptography.verifyMessageSignature(response.getSignature(), response.getMessage(), response.getSender());
		assertThat(signatureVerification).isTrue();
	}
}