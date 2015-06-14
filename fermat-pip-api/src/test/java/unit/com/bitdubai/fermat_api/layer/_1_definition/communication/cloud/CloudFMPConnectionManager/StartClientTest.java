package unit.com.bitdubai.fermat_api.layer._1_definition.communication.cloud.CloudFMPConnectionManager;

import static org.fest.assertions.api.Assertions.*;
import static com.googlecode.catchexception.CatchException.*;

import org.junit.Before;
import org.junit.Test;

import com.bitdubai.fermat_api.layer._10_communication.cloud.CloudConnectionException;
import com.bitdubai.fermat_api.layer._1_definition.communication.cloud.CloudFMPConnectionManager;
import com.bitdubai.fermat_api.layer._1_definition.crypto.asymmetric.AsymmectricCryptography;

public class StartClientTest extends CloudFMPConnectionManagerUnitTest {
	
	private CloudFMPConnectionManager testServer;
	private CloudFMPConnectionManager testClient;
	
	private String testClientPrivateKey;
	private String testClientPublicKey;
	
	private int tcpPortPadding = TCP_BASE_TEST_PORT + 100;
	
	@Before
	public void setUpClientKeys(){
		testClientPrivateKey = AsymmectricCryptography.createPrivateKey();
		testClientPublicKey = AsymmectricCryptography.derivePublicKey(testClientPrivateKey);
	}
	
	@Test
	public void Start_Successful_isRunning() throws Exception{
		setUpParameters(tcpPortPadding+1);
		testServer = new MockCloudFMPConnectionManagerServer(testAddress, testExecutor, testPrivateKey, testPublicKey);
		testServer.start();

		testClient = new MockCloudFMPConnectionManagerClient(testAddress, testExecutor, testClientPrivateKey, testClientPublicKey);
		testClient.start();
		assertThat(testClient.isRunning()).isTrue();
	}
	
	@Test
	public void Start_NoServerAtAddress_CloudConnectionException() throws Exception{
		setUpParameters(tcpPortPadding+2);
		testClient = new MockCloudFMPConnectionManagerClient(testAddress, testExecutor, testClientPrivateKey, testClientPublicKey);
		catchException(testClient).start();
		assertThat(caughtException()).isInstanceOf(CloudConnectionException.class);
	}
	
}