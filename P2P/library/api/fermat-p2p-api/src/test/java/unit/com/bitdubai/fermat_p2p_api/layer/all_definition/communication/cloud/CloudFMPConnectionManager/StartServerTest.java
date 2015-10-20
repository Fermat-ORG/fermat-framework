package unit.com.bitdubai.fermat_p2p_api.layer.all_definition.communication.cloud.CloudFMPConnectionManager;

import static org.fest.assertions.api.Assertions.*;

import org.junit.Test;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.cloud.CloudFMPConnectionManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud.exceptions.CloudCommunicationException;


public class StartServerTest extends CloudFMPConnectionManagerUnitTest {
	
	private CloudFMPConnectionManager testManager;
	
	private int tcpPortPadding = TCP_BASE_TEST_PORT + 200;
	
	@Test
	public void Start_Successful_isRunning() throws Exception{
		setUpParameters(tcpPortPadding+1);
		testManager = new MockCloudFMPConnectionManagerServer(testAddress, testExecutor, testPrivateKey, testPublicKey);
		assertThat(testManager.isRunning()).isFalse();
		testManager.start();
		assertThat(testManager.isRunning()).isTrue();
	}
	
	@Test(expected=CloudCommunicationException.class)
	public void DoubleStart_Successful_isRunning() throws Exception{
		setUpParameters(tcpPortPadding+2);
		testManager = new MockCloudFMPConnectionManagerServer(testAddress, testExecutor, testPrivateKey, testPublicKey);
		testManager.start();
		testManager.start();
	}

}
