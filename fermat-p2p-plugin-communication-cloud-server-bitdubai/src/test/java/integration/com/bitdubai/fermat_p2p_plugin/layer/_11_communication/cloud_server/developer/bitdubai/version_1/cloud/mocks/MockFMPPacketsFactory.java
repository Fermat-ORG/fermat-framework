package integration.com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_server.developer.bitdubai.version_1.cloud.mocks;

import com.bitdubai.fermat_api.layer._10_communication.fmp.FMPPacket;
import com.bitdubai.fermat_api.layer._10_communication.fmp.FMPPacket.FMPPacketType;
import com.bitdubai.fermat_api.layer._10_communication.fmp.FMPException;
import com.bitdubai.fermat_api.layer._1_definition.communication.FMPPacketFactory;
import com.bitdubai.fermat_api.layer._1_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer._1_definition.enums.NetworkServices;

/**
 * Created by jorgeejgonzalez on 27/04/15.
 */
public class MockFMPPacketsFactory {
	
	public static final String MOCK_PRIVATE_KEY = "18E14A7B6A307F426A94F8114701E7C8E774E7F9A47E2C2035DB29A206321725";
	public static final String MOCK_PUBLIC_KEY = "0450863AD64A87AE8A2FE83C1AF1A8403CB53F53E486D8511DAD8A04887E5B23522CD470243453A299FA9E77237716103ABC11A1DF38855ED6F2EE187E9C582BA6";

	private static String mockSender = "0450863AD64A87AE8A2FE83C1AF1A8403CB53F53E486D8511DAD8A04887E5B23522CD470243453A299FA9E77237716103ABC11A1DF38855ED6F2EE187E9C582BA6";
	private static String mockDestination = "0450863AD64A87AE8A2FE83C1AF1A8403CB53F53E486D8511DAD8A04887E5B23522CD470243453A299FA9E77237716103ABC11A1DF38855ED6F2EE187E9C582BA6";
	private static FMPPacketType mockType;
	private static String mockMessage = "04c130b3534d2384531f3290bf85e40011401f0c1aaaede69286e42909dae4c7a2692fb0b06a204320ff55f544c9363248612086f1f9be897cd141be10d53729023809bc6b3c752bbd7006ce50dd4dc1b90e945f876f68625eadf1a75b79a94dae2a46d0a3537678ab45894deaabd6a22a62d6e5e3996631c014372641e334f35c58746910dc25635d269f5db0ddbdadba74658071ac5cf2dbb8df7c95e9881fcc671a149c47233ea4a0aacb40ad912bed85cb79";
	private static String mockSignature = "efc4f8d8bfc778463e4d4916d88bf3f057e6dc96cb2adc26dfb91959c4bef4a5 e80dfe70cd1eeafe98a42e51ed02a0154bf9909f61d2ef6b71a45c1e4aaf5e07";

	public static FMPPacket mockRequestPacket() throws FMPException {
		mockType = FMPPacketType.CONNECTION_REQUEST;
		return FMPPacketFactory.constructCloudPacket(mockSender, mockDestination, mockType, mockMessage, mockSignature);
	}
	
	public static FMPPacket mockRequestPacket(final String destination) throws FMPException {
		mockType = FMPPacketType.CONNECTION_REQUEST;
		return FMPPacketFactory.constructCloudPacket(mockSender, destination, mockType, mockMessage, mockSignature);
	}
	
	public static FMPPacket mockRequestIntraUserNetworkServicePacket(final String destination) throws FMPException {
		mockType = FMPPacketType.CONNECTION_REQUEST;
		String message = AsymmectricCryptography.encryptMessagePublicKey(NetworkServices.INTRA_USER.toString(), destination);
		String signature = AsymmectricCryptography.createMessageSignature(message, MOCK_PRIVATE_KEY);
		return FMPPacketFactory.constructCloudPacket(mockSender, destination, mockType, message, signature);
	}
	
	public static FMPPacket mockRequestMoneyNetworkServicePacket(final String destination) throws FMPException {
		mockType = FMPPacketType.CONNECTION_REQUEST;
		String message = AsymmectricCryptography.encryptMessagePublicKey(NetworkServices.MONEY.toString(), destination);
		String signature = AsymmectricCryptography.createMessageSignature(message, MOCK_PRIVATE_KEY);
		return FMPPacketFactory.constructCloudPacket(mockSender, destination, mockType, message, signature);
	}
	
	public static FMPPacket mockRegisterPacket(final String destination) throws FMPException {
		mockType = FMPPacketType.CONNECTION_REGISTER;
		return FMPPacketFactory.constructCloudPacket(mockSender, destination, mockType, mockMessage, mockSignature);
	}

}
