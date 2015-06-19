package integration.com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.structure.NetworkServiceClientManager;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

public class AcceptVPNConnectionTest extends NetworkServiceClientManagerIntegrationTest {
	
	private static final int TCP_PORT_PADDING = 200;
	
	//TODO improve this test, it's flaky
	@Test
	public void AcceptPendingVPNRequest_ReceivesAcceptForward_ActiveVPNInRegistry() throws Exception{
		setUp(TCP_PORT_PADDING + 1);
		Thread.sleep(getThreadSleepMillis());
		testClient.requestVPNConnection(testClient.getPublicKey());
		Thread.sleep(getThreadSleepMillis());
		String peerVPN = testClient.getPendingVPNRequest();
		assertThat(peerVPN).isNotNull();
		testClient.acceptPendingVPNRequest(peerVPN);
		Thread.sleep(getThreadSleepMillis());
		assertThat(testClient.getActiveVPN(peerVPN)).isNotNull();
	}
}
