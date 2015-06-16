package integration.com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_client.developer.bitdubai.version_1.structure.CloudClientManager;

import org.junit.Test;

public class RegisterNetworkServiceTest extends CloudClientManagerIntegrationTest {
	
	private static final int TCP_PORT_PADDING = 200;
	
	//TODO improve this test, it's flaky
	@Test
	public void RequestConnection_ReceivesAcceptPacket_RegistersConnectionToServer() throws Exception{
		setUp(TCP_PORT_PADDING + 1);
	}
}
