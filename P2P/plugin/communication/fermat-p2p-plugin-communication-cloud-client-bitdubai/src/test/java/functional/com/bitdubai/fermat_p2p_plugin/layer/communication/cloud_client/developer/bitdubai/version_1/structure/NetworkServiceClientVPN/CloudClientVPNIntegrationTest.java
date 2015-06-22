package functional.com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.structure.NetworkServiceClientVPN;

import static org.fest.assertions.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServices;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.CommunicationChannelAddressFactory;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannelAddress;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.structure.NetworkServiceClientVPN;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.structure.CloudNetworkServiceManager;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.structure.CloudNetworkServiceVPN;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.structure.ECCKeyPair;

import functional.com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_server.developer.bitdubai.version_1.structure.mocks.MockFMPPacketsFactory;

public abstract class CloudClientVPNIntegrationTest {
	
	protected String clientPrivateKey = MockFMPPacketsFactory.MOCK_PRIVATE_KEY;
	
	protected String serverPrivateKey = "a06a1274b8bd327c0ddeebc75597101a1e09c3551915e25ad2d8949e0507c142";
	protected String serverPublicKey = "047F9F57BDE5771B4A9DA604B1CA138AA3B593B7EFBC3C890E384CF8B3EB3080E70DDA5130A26768DDEF30379AA6F9B924339407B0D429964503372CB71D3328D0";

	protected String testHost = "localhost";
	protected Integer testBasePort = 52000;
	protected CommunicationChannelAddress testAddress;
	
	protected CloudNetworkServiceVPN testServer;
	protected NetworkServiceClientVPN testClient;
	
	protected CloudNetworkServiceManager testNetworkServiceServer;
	protected NetworkServices testNetworkService;
	protected Set<String> testParticipants;
	
	protected void setUpServer(final int tcpPadding) throws Exception {
		testAddress = CommunicationChannelAddressFactory.constructCloudAddress(testHost, testBasePort+tcpPadding);
		ECCKeyPair testKeyPair = new ECCKeyPair(serverPrivateKey);
		testParticipants = new HashSet<String>();
		testParticipants.add(MockFMPPacketsFactory.MOCK_PUBLIC_KEY);
		testNetworkService = NetworkServices.INTRA_USER;
		
		testServer = new CloudNetworkServiceVPN(testAddress, getExecutor(), testKeyPair, testNetworkService, testParticipants);
		testServer.start();
		
		assertThat(testServer.isRunning()).isTrue();
	}
	
	protected void setUpClient(final int tcpPadding) throws Exception{
		setUpServer(tcpPadding);
		testClient = new NetworkServiceClientVPN(testAddress, getExecutor(), clientPrivateKey, serverPublicKey, MockFMPPacketsFactory.MOCK_PUBLIC_KEY, testNetworkService);
		testClient.start();
		Thread.sleep(getThreadSleepMillis());
	}
	
	protected int getThreadSleepMillis(){
		return 1000;
	}
	
	private ExecutorService getExecutor(){
		return Executors.newFixedThreadPool(3);
	}

}
