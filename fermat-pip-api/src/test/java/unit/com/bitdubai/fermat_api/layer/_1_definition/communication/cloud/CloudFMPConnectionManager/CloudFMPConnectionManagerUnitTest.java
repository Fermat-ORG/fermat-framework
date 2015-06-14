package unit.com.bitdubai.fermat_api.layer._1_definition.communication.cloud.CloudFMPConnectionManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.bitdubai.fermat_api.layer._10_communication.CommunicationChannelAddress;
import com.bitdubai.fermat_api.layer._1_definition.communication.CommunicationChannelAddressFactory;

public abstract class CloudFMPConnectionManagerUnitTest {
	
	protected CommunicationChannelAddress testAddress;
	protected String testHost;
	protected Integer testPort;	
	protected ExecutorService testExecutor;
	protected String testPrivateKey;
	protected String testPublicKey;

	protected static final int TCP_BASE_TEST_PORT = 20000;
	
	public void setUpParameters(int tcpPort) throws Exception{
		testHost = "localhost";
		testPort = Integer.valueOf(tcpPort);
		testAddress = CommunicationChannelAddressFactory.constructCloudAddress(testHost, testPort);
		testExecutor = Executors.newFixedThreadPool(30);
		testPrivateKey = "BE823188D332F3C73BFA34F8F453CFC184718273195194D4A9F5BB27A191886D";
		testPublicKey = "04F0F591F89E3CA948824F3CA8FD7D2115AE20B801EDE4CA090E3DA1856C1AC199CAB9BCF755162159C3C999F921ACE78B9529DFE67715C321DA8208B483DC74DB";		
	}
	
}
