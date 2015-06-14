package unit.com.bitdubai.fermat_api.layer._1_definition.communication.FMPPacketFactory;

import static org.fest.assertions.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

import com.bitdubai.fermat_api.layer._10_communication.fmp.FMPPacket;
import com.bitdubai.fermat_api.layer._10_communication.fmp.FMPException;
import com.bitdubai.fermat_api.layer._10_communication.fmp.FMPPacket.FMPPacketType;
import com.bitdubai.fermat_api.layer._1_definition.communication.FMPPacketFactory;


public class ConstructCloudPacketTest {

	private FMPPacket testPacket;
	private String testPacketData;
	private String testSender;
	private String testDestination;
	private FMPPacketType testType;
	private String testMessage;
	private String testSignature;
	
	@Before
	public void mockPacketData() throws Exception {
		testSender = "0450863AD64A87AE8A2FE83C1AF1A8403CB53F53E486D8511DAD8A04887E5B23522CD470243453A299FA9E77237716103ABC11A1DF38855ED6F2EE187E9C582BA6";
		testDestination = "0450863AD64A87AE8A2FE83C1AF1A8403CB53F53E486D8511DAD8A04887E5B23522CD470243453A299FA9E77237716103ABC11A1DF38855ED6F2EE187E9C582BA6";
		testType = FMPPacketType.CONNECTION_REQUEST;
		testMessage = "04c130b3534d2384531f3290bf85e40011401f0c1aaaede69286e42909dae4c7a2692fb0b06a204320ff55f544c9363248612086f1f9be897cd141be10d53729023809bc6b3c752bbd7006ce50dd4dc1b90e945f876f68625eadf1a75b79a94dae2a46d0a3537678ab45894deaabd6a22a62d6e5e3996631c014372641e334f35c58746910dc25635d269f5db0ddbdadba74658071ac5cf2dbb8df7c95e9881fcc671a149c47233ea4a0aacb40ad912bed85cb79";
		testSignature = "efc4f8d8bfc778463e4d4916d88bf3f057e6dc96cb2adc26dfb91959c4bef4a5 e80dfe70cd1eeafe98a42e51ed02a0154bf9909f61d2ef6b71a45c1e4aaf5e07";
		
		StringBuilder builder = new StringBuilder();
		builder.append(testSender);
		builder.append(FMPPacket.PACKET_SEPARATOR);
		builder.append(testDestination);
		builder.append(FMPPacket.PACKET_SEPARATOR);
		builder.append(testType);
		builder.append(FMPPacket.PACKET_SEPARATOR);
		builder.append(testMessage);
		builder.append(FMPPacket.PACKET_SEPARATOR);
		builder.append(testSignature);

		testPacketData = builder.toString();

	}

	@Test
	public void ConstructCloudPacket_ValidDataPacket_NotNull() throws Exception {
		testPacket = FMPPacketFactory.constructCloudPacket(testPacketData);
		assertThat(testPacket).isNotNull();
	}

	@Test(expected=FMPException.class)
	public void ConstructCloudPacket_NullDataPacket_ThrowsFMPException() throws Exception {
		testPacket = FMPPacketFactory.constructCloudPacket(null);
	}

	@Test(expected=FMPException.class)
	public void ConstructCloudPacket_EmptyDataPacket_ThrowsFMPException() throws Exception {
		testPacket = FMPPacketFactory.constructCloudPacket("");
	}

}
