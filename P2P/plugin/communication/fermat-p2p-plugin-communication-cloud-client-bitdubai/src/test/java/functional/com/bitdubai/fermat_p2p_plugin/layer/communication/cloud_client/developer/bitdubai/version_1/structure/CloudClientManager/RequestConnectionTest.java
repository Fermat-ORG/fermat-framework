package functional.com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.structure.CloudClientManager;

import static org.fest.assertions.api.Assertions.*;
import static com.googlecode.catchexception.CatchException.*;

import org.junit.Ignore;
import org.junit.Test;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud.exceptions.CloudCommunicationException;


public class RequestConnectionTest extends CloudClientManagerIntegrationTest {
	
	private static final int TCP_PORT_PADDING = 100;
	
	//TODO improve this test, it's flaky
	@Ignore
	@Test
	public void RequestConnection_ReceivesAcceptPacket_RegistersConnectionToServer() throws Exception{
		setUp(TCP_PORT_PADDING + 1);
		testClient.requestConnectionToServer();
		Thread.sleep(getThreadSleepMillis());
		assertThat(testClient.isRegistered()).isTrue();
	}

	@Ignore
	@Test
	public void RequestConnection_ConnectionIsAlreadyRequested_ThrowsCloudConnectionException() throws Exception{
		setUp(TCP_PORT_PADDING + 3);
		testClient.requestConnectionToServer();
		catchException(testClient).requestConnectionToServer();
		assertThat(caughtException()).isInstanceOf(CloudCommunicationException.class);
	}
	
	//TODO improve this test, it's flaky
	@Ignore
	@Test
	public void RequestConnection_ConnectionIsAlreadyRegistered_ThrowsCloudConnectionException() throws Exception{
		setUp(TCP_PORT_PADDING + 5);
		testClient.requestConnectionToServer();
		Thread.sleep(getThreadSleepMillis());
		assertThat(testClient.isRegistered()).isTrue();
		catchException(testClient).requestConnectionToServer();
		assertThat(caughtException()).isInstanceOf(CloudCommunicationException.class);
	}

}
