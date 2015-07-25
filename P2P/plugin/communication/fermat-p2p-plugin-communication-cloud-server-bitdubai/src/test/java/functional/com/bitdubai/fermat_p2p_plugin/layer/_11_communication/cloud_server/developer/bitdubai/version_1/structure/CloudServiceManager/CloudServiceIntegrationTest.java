package functional.com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_server.developer.bitdubai.version_1.structure.CloudServiceManager;

import static org.junit.Assert.assertEquals;
import functional.com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_server.developer.bitdubai.version_1.structure.mocks.MockFMPPacketsFactory;
import functional.com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_server.developer.bitdubai.version_1.structure.mocks.MockNIOClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.CommunicationChannelAddressFactory;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannelAddress;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket.FMPPacketType;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.structure.CloudServiceManager;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;

public abstract class CloudServiceIntegrationTest {
	
	protected CloudServiceManager testManager;
	protected String testHost;
	protected Integer testPort;
	protected CommunicationChannelAddress testAddress;
	protected ExecutorService testExecutor;
	protected String testPrivateKey;
	protected String testPublicKey;
	protected ECCKeyPair testKeyPair;
	protected MockNIOClient testClient;
	
	protected static final int RESPONSE_READ_ATTEMPTS = 10;
	
	protected static final int TCP_BASE_TEST_PORT = 52000;
	
	protected void setUpAddressInfo(int port) throws Exception{
		testHost = "localhost";
		testPort = Integer.valueOf(port);		
	}
	
	protected void setUpKeyPair(){
		testPrivateKey = "BE823188D332F3C73BFA34F8F453CFC184718273195194D4A9F5BB27A191886D";
		testPublicKey = "04F0F591F89E3CA948824F3CA8FD7D2115AE20B801EDE4CA090E3DA1856C1AC199CAB9BCF755162159C3C999F921ACE78B9529DFE67715C321DA8208B483DC74DB";
		testKeyPair = new ECCKeyPair(testPrivateKey, testPublicKey);			
	}
	
	protected void setUpExecutor(int threads) throws Exception{
		testExecutor = Executors.newFixedThreadPool(threads);
	}
	
	protected void setUpConnections(int portPadding) throws Exception{
		testAddress = CommunicationChannelAddressFactory.constructCloudAddress(testHost, testPort+portPadding);
		testManager = new CloudServiceManager(testAddress, testExecutor, testKeyPair);
		testManager.start();
		testClient = new MockNIOClient(testHost, testPort+portPadding);
	}
	
	protected void requestConnection() throws Exception{
		FMPPacket request = MockFMPPacketsFactory.mockRequestConnectionPacket(testManager.getIdentityPublicKey());
		testClient.sendMessage(request);
		FMPPacket response = getResponse();
		assertEquals(FMPPacketType.CONNECTION_ACCEPT, response.getType());
	}
	
	protected synchronized FMPPacket getResponse() throws Exception {
		FMPPacket response = null;
		int attempts = 0;
		while(response == null){
			if(attempts>RESPONSE_READ_ATTEMPTS)
				break;
			response = testClient.getResponse();
			++attempts;
		}
		return response;
	}

}
