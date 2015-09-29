package functional.com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.structure.NetworkServiceClientVPN;

import static org.fest.assertions.api.Assertions.*;

import org.junit.Ignore;
import org.junit.Test;


public class DataTransmitTest extends NetworkServiceClientVPNIntegrationTest {
	
	private static final int TCP_PORT_PADDING = 200;
	
	private String testMessage = "THIS IS A TEST MESSAGE";
	
	//TODO improve this test, it's flaky
	@Ignore
	@Test
	public void SendMessage_DataIsReceivedByPeer_SameMessageSent() throws Exception{
		setUpClient(TCP_PORT_PADDING + 1);
		//testClient.sendMessage(testMessage);
		Thread.sleep(getThreadSleepMillis());
		//String inconmingMessage = testClient.getPendingMessage();
		//assertThat(inconmingMessage).isEqualTo(testMessage);
	}

}
