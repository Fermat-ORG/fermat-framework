package functional.com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.structure.NetworkServiceClientManager;

import static org.fest.assertions.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServices;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.CommunicationChannelAddressFactory;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannelAddress;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.structure.NetworkServiceClientManager;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.structure.CloudNetworkServiceManager;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.structure.ECCKeyPair;

public abstract class NetworkServiceClientManagerIntegrationTest {
	
	protected String clientPrivateKey = "362b22fdc53e753f17edf3ec98a39b855e7c7ac0614fb5a33a3bdebeff18932e";
	
	protected String serverPrivateKey = "a06a1274b8bd327c0ddeebc75597101a1e09c3551915e25ad2d8949e0507c142";
	protected String serverPublicKey = "047F9F57BDE5771B4A9DA604B1CA138AA3B593B7EFBC3C890E384CF8B3EB3080E70DDA5130A26768DDEF30379AA6F9B924339407B0D429964503372CB71D3328D0";

	protected String testHost = "localhost";
	protected Integer testBasePort = 51000;
	protected CommunicationChannelAddress testAddress;
	
	protected NetworkServiceClientManager testClient;
	
	protected CloudNetworkServiceManager testServer;
	protected NetworkServices testNetworkService;
	
	protected void setUpServer(final int tcpPadding) throws Exception {
		testAddress = CommunicationChannelAddressFactory.constructCloudAddress(testHost, testBasePort+tcpPadding);
		ECCKeyPair testKeyPair = new ECCKeyPair(serverPrivateKey);
		testNetworkService = NetworkServices.INTRA_USER;
		Set<Integer> networkServiceVNPorts = new HashSet<Integer>();
		networkServiceVNPorts.add(testBasePort+tcpPadding+1);
		testServer = new CloudNetworkServiceManager(testAddress, getExecutor(), testKeyPair, testNetworkService, networkServiceVNPorts);
		testServer.start();
		
		assertThat(testServer.isRunning()).isTrue();
	}
	
	protected void setUp(final int tcpPadding) throws Exception{
		setUpServer(tcpPadding);
		testClient = new NetworkServiceClientManager(testAddress, getExecutor(), clientPrivateKey, serverPublicKey, testNetworkService);
		testClient.start();
	}
	
	protected int getThreadSleepMillis(){
		return 1000;
	}
	
	private ExecutorService getExecutor(){
		return Executors.newFixedThreadPool(2);
	}

}
