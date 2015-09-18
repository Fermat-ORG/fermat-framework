package unit.com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.structure.CloudNetworkServiceManager;

import static org.fest.assertions.api.Assertions.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.CommunicationChannelAddressFactory;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannelAddress;
import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServices;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.structure.CloudNetworkServiceManager;

public class EqualsAndHashTest extends CloudNetworkServiceManagerUnitTest {
	
	private static final int TCP_PORT_PADDING = 200;
	
	private CloudNetworkServiceManager testManager2;
	
	@Before
	public void setUpTestManager(){
		setUpParameters(TCP_PORT_PADDING);
		testManager = new CloudNetworkServiceManager(testAddress, testExecutor, testKeyPair, testNetworkService, testVPNPorts);
	}

	@Ignore
	@Test
	public void SameValues_IsEquals_SameHash() throws Exception{
		testManager2 = new CloudNetworkServiceManager(testAddress, testExecutor, testKeyPair, testNetworkService, testVPNPorts);
		assertThat(testManager).isEqualTo(testManager2);
		assertThat(testManager.hashCode()).isEqualTo(testManager2.hashCode());
	}

	@Ignore
	@Test
	public void DifferentAddress_NotEquals_DifferentHash() throws Exception{
		CommunicationChannelAddress testAddress2 = CommunicationChannelAddressFactory.constructCloudAddress(testHost, testPort+1);
		testManager2 = new CloudNetworkServiceManager(testAddress2, testExecutor, testKeyPair, testNetworkService, testVPNPorts);
		assertThat(testManager).isNotEqualTo(testManager2);
		assertThat(testManager.hashCode()).isNotEqualTo(testManager2.hashCode());
	}

	@Ignore
	@Test
	public void DifferentNetworkService_NotEquals_DifferentHash() throws Exception{
		testManager2 = new CloudNetworkServiceManager(testAddress, testExecutor, testKeyPair, NetworkServices.MONEY, testVPNPorts);
		assertThat(testManager).isNotEqualTo(testManager2);
		assertThat(testManager.hashCode()).isNotEqualTo(testManager2.hashCode());
	}

	@Ignore
	@Test
	public void NullValue_NotEquals() throws Exception{
		testManager2 = null;
		assertThat(testManager).isNotEqualTo(testManager2);
	}
	
}
