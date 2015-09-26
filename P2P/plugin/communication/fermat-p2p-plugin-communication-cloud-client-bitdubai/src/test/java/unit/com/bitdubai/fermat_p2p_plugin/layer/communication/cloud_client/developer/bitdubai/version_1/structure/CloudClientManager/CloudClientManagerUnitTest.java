package unit.com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.structure.CloudClientManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.CommunicationChannelAddressFactory;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannelAddress;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.structure.CloudClientCommunicationManager;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.structure.CloudServiceManager;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;

public class CloudClientManagerUnitTest {
	
	
	protected String clientPrivateKey;
	
	protected String serverPrivateKey = "a06a1274b8bd327c0ddeebc75597101a1e09c3551915e25ad2d8949e0507c142";
	protected String serverPublicKey = "047F9F57BDE5771B4A9DA604B1CA138AA3B593B7EFBC3C890E384CF8B3EB3080E70DDA5130A26768DDEF30379AA6F9B924339407B0D429964503372CB71D3328D0";

	protected String testHost = "192.168.1.6";
	protected Integer testBasePort = 9090;
	protected CommunicationChannelAddress testAddress;
	protected ExecutorService testExecutor = Executors.newFixedThreadPool(30);
	
	protected CloudServiceManager testServer;
	protected CloudClientCommunicationManager testClient;
	
	protected void setUpParameters(int tcpPadding) throws Exception{
		testAddress = CommunicationChannelAddressFactory.constructCloudAddress(testHost, testBasePort+tcpPadding);
		clientPrivateKey = "362b22fdc53e753f17edf3ec98a39b855e7c7ac0614fb5a33a3bdebeff18932e";
	}
	
	protected void setUp(int tcpPadding) throws Exception{
		setUpParameters(tcpPadding);
		testClient = new CloudClientCommunicationManager(testAddress, testExecutor, clientPrivateKey, serverPublicKey);
	}
	
	protected void setUpWithServer(int tcpPadding) throws Exception{
		setUpParameters(tcpPadding);
		ECCKeyPair testKeyPair = new ECCKeyPair(serverPrivateKey);
		testServer = new CloudServiceManager(testAddress, testExecutor, testKeyPair);
		testServer.start();
		testClient = new CloudClientCommunicationManager(testAddress, testExecutor, clientPrivateKey, serverPublicKey);
		
	}
	
	protected int getThreadSleepMillis(){
		return 1000;
	}


}
