package functional.com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.structure.NetworkServiceClientVPN;

import static org.fest.assertions.api.Assertions.*;
import static com.googlecode.catchexception.CatchException.*;

import org.junit.Test;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud.exceptions.CloudCommunicationException;


public class RequestConnectionTest extends NetworkServiceClientVPNIntegrationTest {
	
	private static final int TCP_PORT_PADDING = 100;
	
	//TODO improve this test, it's flaky
	@Test
	public void RequestConnection_ReceivesAcceptPacket_RegistersConnectionToServer() throws Exception{
		setUpClient(TCP_PORT_PADDING + 1);
		assertThat(testClient.isRegistered()).isTrue();
	}
	
	@Test
	public void RequestConnection_ConnectionIsAlreadyRequested_ThrowsCloudConnectionException() throws Exception{
		setUpClient(TCP_PORT_PADDING + 3);
		catchException(testClient).start();
		assertThat(caughtException()).isInstanceOf(CloudCommunicationException.class);
	}
	

}
