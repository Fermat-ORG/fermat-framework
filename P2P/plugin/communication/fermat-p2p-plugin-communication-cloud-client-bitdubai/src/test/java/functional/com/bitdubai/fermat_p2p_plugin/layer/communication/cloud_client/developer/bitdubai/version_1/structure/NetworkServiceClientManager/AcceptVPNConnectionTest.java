package functional.com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.structure.NetworkServiceClientManager;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Ignore;
import org.junit.Test;

import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.structure.CloudClientCommunicationNetworkServiceVPN;

public class AcceptVPNConnectionTest extends NetworkServiceClientManagerIntegrationTest {
	
	private static final int TCP_PORT_PADDING = 200;
	
	//TODO improve this test, it's flaky
	@Ignore
	@Test
	public void AcceptPendingVPNRequest_ReceivesAcceptForward_ActiveVPNStarted() throws Exception{
		setUp(TCP_PORT_PADDING + 3);
		testClient.requestVPNConnection(testClient.getIdentityPublicKey());
		Thread.sleep(getThreadSleepMillis());
		CloudClientCommunicationNetworkServiceVPN testVPN = testClient.getActiveVPN(CLIENT_PUBLIC_KEY);
		assertThat(testVPN.isRunning()).isTrue();
		assertThat(testVPN.isRegistered()).isTrue();
	}
}
