package functional.com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.structure.NetworkServiceClientManager;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.structure.NetworkServiceClientVPN;

public class AcceptVPNConnectionTest extends NetworkServiceClientManagerIntegrationTest {
	
	private static final int TCP_PORT_PADDING = 200;
	
	//TODO improve this test, it's flaky
	@Test
	public void AcceptPendingVPNRequest_ReceivesAcceptForward_ActiveVPNInRegistry() throws Exception{
		setUp(TCP_PORT_PADDING + 1);
		testClient.requestVPNConnection(testClient.getPublicKey());
		Thread.sleep(getThreadSleepMillis());
		String peerVPN = testClient.getPendingVPNRequest();
		testClient.acceptPendingVPNRequest(peerVPN);
		Thread.sleep(getThreadSleepMillis());
		assertThat(testClient.getActiveVPN(peerVPN)).isNotNull();
	}
	
	@Test
	public void AcceptPendingVPNRequest_ReceivesAcceptForward_ActiveVPNStarted() throws Exception{
		setUp(TCP_PORT_PADDING + 3);
		testClient.requestVPNConnection(testClient.getPublicKey());
		Thread.sleep(getThreadSleepMillis());
		String peerVPNSolicitant = testClient.getPendingVPNRequest();
		testClient.acceptPendingVPNRequest(peerVPNSolicitant);
		Thread.sleep(getThreadSleepMillis());
		NetworkServiceClientVPN testVPN = testClient.getActiveVPN(peerVPNSolicitant);
		assertThat(testVPN.isRunning()).isTrue();
		assertThat(testVPN.isRegistered()).isTrue();
	}
}
