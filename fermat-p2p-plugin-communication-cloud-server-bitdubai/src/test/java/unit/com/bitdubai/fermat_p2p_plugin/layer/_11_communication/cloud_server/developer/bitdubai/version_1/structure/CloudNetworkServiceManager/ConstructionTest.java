package unit.com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_server.developer.bitdubai.version_1.structure.CloudNetworkServiceManager;

import static org.fest.assertions.api.Assertions.*;

import org.junit.Test;

import com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_server.developer.bitdubai.version_1.structure.CloudNetworkServiceManager;

public class ConstructionTest extends CloudNetworkServiceManagerUnitTest {
	
	private static final int TCP_BASE_TEST_PORT = 12000;
	
	@Test
	public void NetworkServiceManager_ValidParameters_ValidValues() throws Exception{
		setUpParameters(TCP_BASE_TEST_PORT+1);
		testManager = new CloudNetworkServiceManager(testAddress, testExecutor, testKeyPair, testNetworkService, testVPNPorts);
		assertThat(testManager).isNotNull();
		assertThat(testManager.getNetworkService()).isEqualTo(testNetworkService);
		testManager.start();
		assertThat(testManager.isRunning()).isTrue();
	}
	
	@Test
	public void NetworkServiceManager_ValidParameters_CanBeStarted() throws Exception{
		setUpParameters(TCP_BASE_TEST_PORT+2);
		testManager = new CloudNetworkServiceManager(testAddress, testExecutor, testKeyPair, testNetworkService, testVPNPorts);
		testManager.start();
		assertThat(testManager.isRunning()).isTrue();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void NetworkServiceManager_NullNetworkService_IllegalArgumentException() throws Exception{
		setUpParameters(TCP_BASE_TEST_PORT+3);
		testManager = new CloudNetworkServiceManager(testAddress, testExecutor, testKeyPair, null, testVPNPorts);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void NetworkServiceManager_NullVPNPorts_IllegalArgumentException() throws Exception{
		setUpParameters(TCP_BASE_TEST_PORT+3);
		testManager = new CloudNetworkServiceManager(testAddress, testExecutor, testKeyPair, testNetworkService, null);
	}
	@Test(expected=IllegalArgumentException.class)
	public void NetworkServiceManager_NoVPNPorts_IllegalArgumentException() throws Exception{
		setUpParameters(TCP_BASE_TEST_PORT+3);
		testVPNPorts.clear();
		testManager = new CloudNetworkServiceManager(testAddress, testExecutor, testKeyPair, testNetworkService, testVPNPorts);
	}

}
