package unit.com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.structure.CloudNetworkServiceManager;

import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.CommunicationChannelAddressFactory;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannelAddress;
import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServices;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.structure.CloudNetworkServiceManager;

public abstract class CloudNetworkServiceManagerUnitTest {
	
	protected CloudNetworkServiceManager testManager;
	protected String testHost;
	protected Integer testPort;
	protected CommunicationChannelAddress testAddress;
	protected ExecutorService testExecutor;
	protected ECCKeyPair testKeyPair;
	protected NetworkServices testNetworkService;
	protected Collection<Integer> testVPNPorts;
	
	private static final int TCP_BASE_TEST_PORT = 10000;
	
	protected void setUpParameters(int tcpPort){
		testHost = "localhost";
		testPort = TCP_BASE_TEST_PORT + tcpPort;
		testAddress = CommunicationChannelAddressFactory.constructCloudAddress(testHost, testPort);
		testExecutor = Executors.newFixedThreadPool(30);
		testKeyPair = new ECCKeyPair("BE823188D332F3C73BFA34F8F453CFC184718273195194D4A9F5BB27A191886D");
		testNetworkService = NetworkServices.INTRA_USER;
		testVPNPorts = new HashSet<Integer>();
		testVPNPorts.add(testPort+10*tcpPort+1);
	}

}
