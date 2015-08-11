package functional.com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.structure.NetworkServiceClientManager;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Ignore;
import org.junit.Test;

public class RequestVPNConnectionTest extends NetworkServiceClientManagerIntegrationTest {
	
	private static final int TCP_PORT_PADDING = 100;
	
	//TODO improve this test, it's flaky
	@Ignore
	@Test
	public void RequestVPN_PeersAreRegistered_ReceivePendingVPNRequest() throws Exception{
		setUp(TCP_PORT_PADDING + 1);
		testClient.requestVPNConnection(testClient.getIdentityPublicKey());
		Thread.sleep(getThreadSleepMillis());
		assertThat(testClient.getPendingVPNRequests()).isNotNull();
	}
}
