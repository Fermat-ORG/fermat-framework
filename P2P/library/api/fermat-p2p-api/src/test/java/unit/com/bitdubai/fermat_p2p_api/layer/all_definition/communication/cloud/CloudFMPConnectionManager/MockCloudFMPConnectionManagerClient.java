package unit.com.bitdubai.fermat_p2p_api.layer.all_definition.communication.cloud.CloudFMPConnectionManager;

import java.util.concurrent.ExecutorService;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.cloud.CloudFMPConnectionManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannelAddress;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud.exceptions.CloudCommunicationException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket;

public class MockCloudFMPConnectionManagerClient extends
		CloudFMPConnectionManager {
	
	public MockCloudFMPConnectionManagerClient(
			CommunicationChannelAddress address, ExecutorService executor,
			String privateKey, String publicKey)
			throws IllegalArgumentException{
		super(address, executor, new ECCKeyPair(privateKey, publicKey), CloudFMPConnectionManagerMode.FMP_CLIENT);
	}

	@Override
	public void handleConnectionRequest(FMPPacket packet) throws FMPException {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleConnectionAccept(FMPPacket packet) throws FMPException {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleConnectionAcceptForward(FMPPacket packet)
			throws FMPException {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleConnectionDeny(FMPPacket packet) throws FMPException {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleConnectionRegister(FMPPacket packet) throws FMPException {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleConnectionDeregister(FMPPacket packet)
			throws FMPException {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleConnectionEnd(FMPPacket packet) throws FMPException {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleDataTransmit(FMPPacket packet) throws FMPException {
		// TODO Auto-generated method stub

	}

	@Override
	public void processDataPackets() throws CloudCommunicationException {
		// TODO Auto-generated method stub
	}

}
