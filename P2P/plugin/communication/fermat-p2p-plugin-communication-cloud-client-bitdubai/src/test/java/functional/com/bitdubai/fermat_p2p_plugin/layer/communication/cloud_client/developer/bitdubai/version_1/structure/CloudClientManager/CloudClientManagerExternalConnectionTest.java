package functional.com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.structure.CloudClientManager;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.CommunicationChannelAddressFactory;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannelAddress;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud.exceptions.CloudCommunicationException;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.structure.CloudClientCommunicationManager;

import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.Executors;


public class CloudClientManagerExternalConnectionTest {
	
	protected static final String CLIENT_PRIVATE_KEY = "18E14A7B6A307F426A94F8114701E7C8E774E7F9A47E2C2035DB29A206321725";
	protected static String serverPublicKey = "047F9F57BDE5771B4A9DA604B1CA138AA3B593B7EFBC3C890E384CF8B3EB3080E70DDA5130A26768DDEF30379AA6F9B924339407B0D429964503372CB71D3328D0";

	protected static String testHost = "192.168.1.6";
	protected static Integer testBasePort = 9090;
	protected static CommunicationChannelAddress testAddress;
	protected static CloudClientCommunicationManager testClient;

    @Ignore
	@Test
    public void CloudClientCommunicationManagerConnection() throws Exception{

		testAddress = CommunicationChannelAddressFactory.constructCloudAddress(testHost, testBasePort);
		testClient = new CloudClientCommunicationManager(testAddress, Executors.newCachedThreadPool(), CLIENT_PRIVATE_KEY, serverPublicKey);
		testClient.start();
	}

}
