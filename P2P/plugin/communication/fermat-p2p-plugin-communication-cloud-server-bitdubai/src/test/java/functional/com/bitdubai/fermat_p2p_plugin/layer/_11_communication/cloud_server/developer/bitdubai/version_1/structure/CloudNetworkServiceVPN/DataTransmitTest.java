package functional.com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_server.developer.bitdubai.version_1.structure.CloudNetworkServiceVPN;

import static org.fest.assertions.api.Assertions.*;

import java.util.HashSet;

import functional.com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_server.developer.bitdubai.version_1.structure.mocks.MockFMPPacketsFactory;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket.FMPPacketType;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;

/**
 * Created by jorgeejgonzalez on 27/04/15.
 */
public class DataTransmitTest extends CloudNetworkServiceVPNIntegrationTest{
	
	private String testMessage;
	
	@Before
	public void setUpParameters() throws Exception{
		setUpAddressInfo(TCP_BASE_TEST_PORT+300);
		setUpKeyPair();
		setUpExecutor(2);
		testParticipants = new HashSet<String>();
		testParticipants.add(MockFMPPacketsFactory.MOCK_PUBLIC_KEY);
	}

	@Ignore
	@Test
	public void DataTransmit_RecipientGetsMessage_ClientGetsResponse() throws Exception{
		FMPPacket response = transmitDataThroughVPN(0);
		assertThat(response).isNotNull();
	}

	@Ignore
	@Test
	public void DataTransmit_RecipientGetsMessage_ResponseTypeIsDataTransmit() throws Exception{
		FMPPacket response = transmitDataThroughVPN(2);
		assertThat(response.getType()).isEqualTo(FMPPacketType.DATA_TRANSMIT);
	}

	@Ignore
	@Test
	public void DataTransmit_RecipientGetsMessage_ResponseMessageIsTestMessage() throws Exception{
		FMPPacket response = transmitDataThroughVPN(4);
		String decryptedMessage = AsymmetricCryptography.decryptMessagePrivateKey(response.getMessage(), MockFMPPacketsFactory.MOCK_PRIVATE_KEY);
		assertThat(decryptedMessage).isEqualTo(testMessage);
	}

	@Ignore
	@Test
	public void DataTransmit_RecipientGetsMessage_SignatureIsValid() throws Exception{
		FMPPacket response = transmitDataThroughVPN(6);
		boolean signatureCheck = AsymmetricCryptography.verifyMessageSignature(response.getSignature(), response.getMessage(), MockFMPPacketsFactory.MOCK_PUBLIC_KEY);
		assertThat(signatureCheck).isTrue();
	}
	
	protected FMPPacket transmitDataThroughVPN(final int tcpPadding) throws Exception{
		setUpConnections(tcpPadding);
		requestConnection();
		registerConnection();
		testMessage = "TEST MESSAGE";
		FMPPacket register = MockFMPPacketsFactory.mockDataTransmitVPNPacket(MockFMPPacketsFactory.MOCK_PUBLIC_KEY, MockFMPPacketsFactory.MOCK_PUBLIC_KEY, testMessage);
		testClient.sendMessage(register);
		return getResponse();
	}
	
}