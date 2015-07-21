package unit.com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.structure.CloudNetworkServiceVPN;

import static org.fest.assertions.api.Assertions.*;

import org.junit.Ignore;
import org.junit.Test;

import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.structure.CloudNetworkServiceVPN;

public class ConstructionTest extends CloudNetworkServiceVPNUnitTest {
	
	private static final int TCP_PORT_PADDING = 100;

	@Ignore
	@Test
	public void NetworkServiceManager_ValidParameters_ValidValues() throws Exception{
		setUpParameters(TCP_PORT_PADDING+1);
		testVPN = new CloudNetworkServiceVPN(testAddress, testExecutor, testKeyPair, testNetworkService, testParticipants);
		assertThat(testVPN).isNotNull();
		assertThat(testVPN.getNetworkService()).isEqualTo(testNetworkService);
		testVPN.start();
		assertThat(testVPN.isRunning()).isTrue();
	}

	@Ignore
	@Test
	public void NetworkServiceManager_ValidParameters_CanBeStarted() throws Exception{
		setUpParameters(TCP_PORT_PADDING+2);
		testVPN = new CloudNetworkServiceVPN(testAddress, testExecutor, testKeyPair, testNetworkService, testParticipants);
		testVPN.start();
		assertThat(testVPN.isRunning()).isTrue();
	}

	@Ignore
	@Test(expected=IllegalArgumentException.class)
	public void NetworkServiceManager_NullNetworkService_IllegalArgumentException() throws Exception{
		setUpParameters(TCP_PORT_PADDING+3);
		testVPN = new CloudNetworkServiceVPN(testAddress, testExecutor, testKeyPair, null, testParticipants);
	}

	@Ignore
	@Test(expected=IllegalArgumentException.class)
	public void NetworkServiceManager_NullVPNPorts_IllegalArgumentException() throws Exception{
		setUpParameters(TCP_PORT_PADDING+3);
		testVPN = new CloudNetworkServiceVPN(testAddress, testExecutor, testKeyPair, testNetworkService, null);
	}

	@Ignore
	@Test(expected=IllegalArgumentException.class)
	public void NetworkServiceManager_NoVPNPorts_IllegalArgumentException() throws Exception{
		setUpParameters(TCP_PORT_PADDING+3);
		testParticipants.clear();
		testVPN = new CloudNetworkServiceVPN(testAddress, testExecutor, testKeyPair, testNetworkService, testParticipants);
	}

}
