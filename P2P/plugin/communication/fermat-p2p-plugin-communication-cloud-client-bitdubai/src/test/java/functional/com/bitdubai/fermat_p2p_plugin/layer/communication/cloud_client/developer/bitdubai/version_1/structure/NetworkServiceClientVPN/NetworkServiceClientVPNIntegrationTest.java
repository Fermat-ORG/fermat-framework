package functional.com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.structure.NetworkServiceClientVPN;

import static org.fest.assertions.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServices;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.CommunicationChannelAddressFactory;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannelAddress;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.structure.CloudClientCommunicationNetworkServiceVPN;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.structure.CloudNetworkServiceVPN;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;

public abstract class NetworkServiceClientVPNIntegrationTest {
	
	protected static final String CLIENT_PRIVATE_KEY = "18E14A7B6A307F426A94F8114701E7C8E774E7F9A47E2C2035DB29A206321725";
	protected static final String CLIENT_PUBLIC_KEY = "0450863AD64A87AE8A2FE83C1AF1A8403CB53F53E486D8511DAD8A04887E5B23522CD470243453A299FA9E77237716103ABC11A1DF38855ED6F2EE187E9C582BA6";

	protected String serverPrivateKey = "a06a1274b8bd327c0ddeebc75597101a1e09c3551915e25ad2d8949e0507c142";
	protected String serverPublicKey = "047F9F57BDE5771B4A9DA604B1CA138AA3B593B7EFBC3C890E384CF8B3EB3080E70DDA5130A26768DDEF30379AA6F9B924339407B0D429964503372CB71D3328D0";

	protected String testHost = "localhost";
	protected Integer testBasePort = 52000;
	protected CommunicationChannelAddress testAddress;
	
	protected CloudNetworkServiceVPN testServer;
	protected CloudClientCommunicationNetworkServiceVPN testClient;
	
	protected NetworkServices testNetworkService;
	protected Set<String> testParticipants;
	
	protected void setUpServer(final int tcpPadding) throws Exception {
		testAddress = CommunicationChannelAddressFactory.constructCloudAddress(testHost, testBasePort+tcpPadding);
		ECCKeyPair testKeyPair = new ECCKeyPair(serverPrivateKey);
		testParticipants = new HashSet<String>();
		testParticipants.add(CLIENT_PUBLIC_KEY);
		testNetworkService = NetworkServices.INTRA_USER;
		
		testServer = new CloudNetworkServiceVPN(testAddress, getExecutor(), testKeyPair, testNetworkService, testParticipants);
		testServer.start();
		
		assertThat(testServer.isRunning()).isTrue();
	}
	
	protected void setUpClient(final int tcpPadding) throws Exception{
		setUpServer(tcpPadding);
		testClient = new CloudClientCommunicationNetworkServiceVPN(testAddress, getExecutor(), CLIENT_PRIVATE_KEY, serverPublicKey, CLIENT_PUBLIC_KEY, testNetworkService);
		testClient.start();
		Thread.sleep(getThreadSleepMillis());
	}
	
	protected int getThreadSleepMillis(){
		return 1000;
	}
	
	private ExecutorService getExecutor(){
		return Executors.newCachedThreadPool();
	}

}
