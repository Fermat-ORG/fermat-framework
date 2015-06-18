package integration.com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.structure.CloudClientManager;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

public class RegisterNetworkServiceTest extends CloudClientManagerIntegrationTest {
	
	private static final int TCP_PORT_PADDING = 200;
	
	//TODO improve this test, it's flaky
	@Test
	public void RegisterNetworkService_ServerSupportsNetworkService_AddNetworkServiceClientToRegistry() throws Exception{
		setUp(TCP_PORT_PADDING + 1);
		testClient.requestConnectionToServer();
		Thread.sleep(getThreadSleepMillis());
		testClient.registerNetworkService(testNetworkService);
		Thread.sleep(getThreadSleepMillis());
		assertThat(testClient.getNetworkServiceClient(testNetworkService)).isNotNull();
	}
}
