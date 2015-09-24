package unit.com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.structure.CloudClientManager;

import static org.fest.assertions.api.Assertions.*;
import static com.googlecode.catchexception.CatchException.*;

import org.junit.Ignore;
import org.junit.Test;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud.exceptions.CloudCommunicationException;


public class StartTest extends CloudClientManagerUnitTest{
	private static final int TCP_PORT_PADDING = 100;

	@Ignore
	@Test
	public void Start_NoServer_ThrowsCloudConnectionException() throws Exception {
		setUp(TCP_PORT_PADDING + 1);
		catchException(testClient).start();
		assertThat(caughtException()).isInstanceOf(CloudCommunicationException.class);
	}

	@Ignore
	@Test
	public void Start_Success_IsRunning() throws Exception{
		setUpWithServer(TCP_PORT_PADDING + 2);
		testClient.start();
		Thread.sleep(getThreadSleepMillis());
		assertThat(testClient.isRunning()).isTrue();
	}

	@Ignore
	@Test
	public void Start_AlreadyStarted_ThrowsCloudConnectionException() throws Exception{
		setUpWithServer(TCP_PORT_PADDING + 3);
		testClient.start();
		Thread.sleep(getThreadSleepMillis());
		catchException(testClient).start();
		assertThat(caughtException()).isInstanceOf(CloudCommunicationException.class);
	}

}
