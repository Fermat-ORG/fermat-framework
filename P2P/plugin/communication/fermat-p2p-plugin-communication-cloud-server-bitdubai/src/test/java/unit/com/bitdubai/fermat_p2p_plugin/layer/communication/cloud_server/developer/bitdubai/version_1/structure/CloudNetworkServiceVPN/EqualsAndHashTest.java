package unit.com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.structure.CloudNetworkServiceVPN;

import static org.fest.assertions.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServices;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.CommunicationChannelAddressFactory;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannelAddress;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.structure.CloudNetworkServiceVPN;

public class EqualsAndHashTest extends CloudNetworkServiceVPNUnitTest {
	
	private static final int TCP_PORT_PADDING = 200;
	
	private CloudNetworkServiceVPN testVPN2;
	
	@Before
	public void setUpTestManager(){
		setUpParameters(TCP_PORT_PADDING);
		testVPN = new CloudNetworkServiceVPN(testAddress, testExecutor, testKeyPair, testNetworkService, testParticipants);
	}

	@Ignore
	@Test
	public void SameValues_IsEquals_SameHash() throws Exception{
		testVPN2 = new CloudNetworkServiceVPN(testAddress, testExecutor, testKeyPair, testNetworkService, testParticipants);
		assertThat(testVPN).isEqualTo(testVPN2);
		assertThat(testVPN.hashCode()).isEqualTo(testVPN2.hashCode());
	}

	@Ignore
	@Test
	public void DifferentAddress_NotEquals_DifferentHash() throws Exception{
		CommunicationChannelAddress testAddress2 = CommunicationChannelAddressFactory.constructCloudAddress(testHost, testPort+1);
		testVPN2 = new CloudNetworkServiceVPN(testAddress2, testExecutor, testKeyPair, testNetworkService, testParticipants);
		assertThat(testVPN).isNotEqualTo(testVPN2);
		assertThat(testVPN.hashCode()).isNotEqualTo(testVPN2.hashCode());
	}

	@Ignore
	@Test
	public void DifferentNetworkService_NotEquals_DifferentHash() throws Exception{
		testVPN2 = new CloudNetworkServiceVPN(testAddress, testExecutor, testKeyPair, NetworkServices.MONEY, testParticipants);
		assertThat(testVPN).isNotEqualTo(testVPN2);
		assertThat(testVPN.hashCode()).isNotEqualTo(testVPN2.hashCode());
	}

	@Ignore
	@Test
	public void DifferentParticipants_NotEquals_DifferentHash() throws Exception{
		Set<String> participants = new HashSet<String>();
		participants.add("A1");
		testVPN2 = new CloudNetworkServiceVPN(testAddress, testExecutor, testKeyPair, testNetworkService, participants);
		assertThat(testVPN).isNotEqualTo(testVPN2);
		assertThat(testVPN.hashCode()).isNotEqualTo(testVPN2.hashCode());
	}

	@Ignore
	@Test
	public void NullValue_NotEquals() throws Exception{
		testVPN2 = null;
		assertThat(testVPN).isNotEqualTo(testVPN2);
	}
	
}
