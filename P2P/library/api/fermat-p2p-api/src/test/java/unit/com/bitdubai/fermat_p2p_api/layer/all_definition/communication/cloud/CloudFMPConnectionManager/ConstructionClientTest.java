package unit.com.bitdubai.fermat_p2p_api.layer.all_definition.communication.cloud.CloudFMPConnectionManager;

import static org.fest.assertions.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.cloud.CloudFMPConnectionManager;


public class ConstructionClientTest extends
		CloudFMPConnectionManagerUnitTest {
	
	private CloudFMPConnectionManager testManager;
	
	@Before
	public void setUpTest() throws Exception{
		setUpParameters(TCP_BASE_TEST_PORT);
	}
	
	@Test
	public void Construction_ValidParameter_NotNull() throws Exception{
		testManager = new MockCloudFMPConnectionManagerClient(testAddress, testExecutor, testPrivateKey, testPublicKey);
		assertThat(testManager).isNotNull();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void Construction_NullAddress_InvalidParameterException() throws Exception{
		testManager = new MockCloudFMPConnectionManagerClient(null, testExecutor, testPrivateKey, testPublicKey);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void Construction_NullExecutor_InvalidParameterException() throws Exception{
		testManager = new MockCloudFMPConnectionManagerClient(testAddress, null, testPrivateKey, testPublicKey);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void Construction_NullPrivateKey_InvalidParameterException() throws Exception{
		testManager = new MockCloudFMPConnectionManagerClient(testAddress, testExecutor, null, testPublicKey);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void Construction_EmptyPrivateKey_InvalidParameterException() throws Exception{
		testManager = new MockCloudFMPConnectionManagerClient(testAddress, testExecutor, "", testPublicKey);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void Construction_NullPublicKey_InvalidParameterException() throws Exception{
		testManager = new MockCloudFMPConnectionManagerClient(testAddress, testExecutor, testPrivateKey, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void Construction_EmptyPublicKey_InvalidParameterException() throws Exception{
		testManager = new MockCloudFMPConnectionManagerClient(testAddress, testExecutor, testPrivateKey, null);
	}

}