package unit.com.bitdubai.fermat_p2p_api.layer.all_definition.communication.CommunicationChannelAddressFactory;

import static org.fest.assertions.api.Assertions.*;

import org.junit.Test;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.CommunicationChannelAddressFactory;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannelAddress;

public class ConstructCloudServerAddressTest {
	
	private CommunicationChannelAddress address;
	private String testHost = "localhost";
	private Integer testPort = Integer.valueOf(8080);

	@Test
	public void ConstructCloudServerAddress_ValidParameters_NotNull() throws Exception{
		address = CommunicationChannelAddressFactory.constructCloudAddress(testHost, testPort);
		assertThat(address).isNotNull();
	}

	@Test(expected=IllegalArgumentException.class)
	public void ConstructCloudServerAddress_NullHost_ThrowsInvalidParameterException() throws Exception{
		address = CommunicationChannelAddressFactory.constructCloudAddress(null, testPort);
	}

	@Test(expected=IllegalArgumentException.class)
	public void ConstructCloudServerAddress_NullPort_ThrowsInvalidParameterException() throws Exception{
		address = CommunicationChannelAddressFactory.constructCloudAddress(testHost, null);
	}

}
