package unit.com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.structure.CloudNetworkServiceManager;

import static org.fest.assertions.api.Assertions.*;

import org.junit.Ignore;
import org.junit.Test;

import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.structure.CloudNetworkServiceManager;

public class ConstructionTest extends CloudNetworkServiceManagerUnitTest {
	
	private static final int TCP_PORT_PADDING = 100;

	@Ignore
	@Test
	public void NetworkServiceManager_ValidParameters_ValidValues() throws Exception{
		setUpParameters(TCP_PORT_PADDING+1);
		testManager = new CloudNetworkServiceManager(testAddress, testExecutor, testKeyPair, testNetworkService, testVPNPorts);
		assertThat(testManager).isNotNull();
		assertThat(testManager.getNetworkService()).isEqualTo(testNetworkService);
		testManager.start();
		assertThat(testManager.isRunning()).isTrue();
	}

	@Ignore
	@Test
	public void NetworkServiceManager_ValidParameters_CanBeStarted() throws Exception{
		setUpParameters(TCP_PORT_PADDING+2);
		testManager = new CloudNetworkServiceManager(testAddress, testExecutor, testKeyPair, testNetworkService, testVPNPorts);
		testManager.start();
		assertThat(testManager.isRunning()).isTrue();
	}

	@Ignore
	@Test(expected=IllegalArgumentException.class)
	public void NetworkServiceManager_NullNetworkService_IllegalArgumentException() throws Exception{
		setUpParameters(TCP_PORT_PADDING+3);
		testManager = new CloudNetworkServiceManager(testAddress, testExecutor, testKeyPair, null, testVPNPorts);
	}

	@Ignore
	@Test(expected=IllegalArgumentException.class)
	public void NetworkServiceManager_NullVPNPorts_IllegalArgumentException() throws Exception{
		setUpParameters(TCP_PORT_PADDING+3);
		testManager = new CloudNetworkServiceManager(testAddress, testExecutor, testKeyPair, testNetworkService, null);
	}

	@Ignore
	@Test(expected=IllegalArgumentException.class)
	public void NetworkServiceManager_NoVPNPorts_IllegalArgumentException() throws Exception{
		setUpParameters(TCP_PORT_PADDING+3);
		testVPNPorts.clear();
		testManager = new CloudNetworkServiceManager(testAddress, testExecutor, testKeyPair, testNetworkService, testVPNPorts);
	}

}
