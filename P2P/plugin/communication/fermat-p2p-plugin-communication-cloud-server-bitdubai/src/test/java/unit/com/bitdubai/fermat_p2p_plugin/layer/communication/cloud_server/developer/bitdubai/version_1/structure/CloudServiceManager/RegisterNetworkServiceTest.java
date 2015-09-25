package unit.com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.structure.CloudServiceManager;

import static org.fest.assertions.api.Assertions.*;
import static com.googlecode.catchexception.CatchException.*;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Ignore;
import org.junit.Test;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.CommunicationChannelAddressFactory;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannelAddress;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud.exceptions.CloudCommunicationException;
import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServices;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.structure.CloudServiceManager;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.structure.CloudNetworkServiceManager;

public class RegisterNetworkServiceTest {
	
	private CloudServiceManager testManager;
	private String testHost;
	private Integer testPort;
	private CommunicationChannelAddress testAddress;
	private ExecutorService testExecutor;
	private ECCKeyPair testKeyPair;
	private CloudNetworkServiceManager testNetworkServiceManager;
	
	protected static final int TCP_BASE_TEST_PORT = 11000;
	
	public void setUpManager(int tcpPadding){
		testHost = "localhost";
		testPort = TCP_BASE_TEST_PORT+tcpPadding;
		testAddress = CommunicationChannelAddressFactory.constructCloudAddress(testHost, testPort);
		testExecutor = Executors.newFixedThreadPool(30);
		testKeyPair = new ECCKeyPair("BE823188D332F3C73BFA34F8F453CFC184718273195194D4A9F5BB27A191886D");
		testManager = new CloudServiceManager(testAddress, testExecutor, testKeyPair);
	}
	
	public CloudNetworkServiceManager createNetworkServiceManager(int tcpPadding){
		String host = "localhost";
		Integer port = Integer.valueOf(TCP_BASE_TEST_PORT+100*tcpPadding);
		CommunicationChannelAddress address = CommunicationChannelAddressFactory.constructCloudAddress(host, port);
		ExecutorService executor = Executors.newFixedThreadPool(30);
		ECCKeyPair keyPair = new ECCKeyPair();
		NetworkServices networkService = NetworkServices.INTRA_USER;
		Set<Integer> vpnPorts = new HashSet<Integer>();
		vpnPorts.add(port+100*tcpPadding+1);
		vpnPorts.add(port+100*tcpPadding+2);
		vpnPorts.add(port+100*tcpPadding+3);
		return new CloudNetworkServiceManager(address, executor, keyPair, networkService, vpnPorts);
	}

	@Ignore
	@Test
	public void NetworkServiceManager_ValidNetworkService_IsStarted() throws Exception{
		setUpManager(1);
		testNetworkServiceManager = createNetworkServiceManager(1);
		assertThat(testNetworkServiceManager.isRunning()).isFalse();
		testManager.registerNetworkServiceManager(testNetworkServiceManager);
		assertThat(testNetworkServiceManager.isRunning()).isTrue();
	}

	@Ignore
	@Test
	public void NetworkServiceManager_NullNetworkService_ThrowsIllegalArgumentException() throws Exception{
		setUpManager(2);
		catchException(testManager).registerNetworkServiceManager(testNetworkServiceManager);
		assertThat(caughtException()).isInstanceOf(IllegalArgumentException.class);
	}

	@Ignore
	@Test
	public void NetworkServiceManager_NetworkServiceAlreadyRegistered_ThrowsCloudConnectionException() throws Exception{
		setUpManager(3);
		testNetworkServiceManager = createNetworkServiceManager(3);
		testManager.registerNetworkServiceManager(testNetworkServiceManager);
		catchException(testManager).registerNetworkServiceManager(testNetworkServiceManager);
		assertThat(caughtException()).isInstanceOf(CloudCommunicationException.class);
	}
	

}
