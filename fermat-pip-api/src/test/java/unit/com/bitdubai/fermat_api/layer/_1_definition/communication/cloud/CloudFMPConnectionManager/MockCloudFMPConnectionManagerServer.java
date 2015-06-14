package unit.com.bitdubai.fermat_api.layer._1_definition.communication.cloud.CloudFMPConnectionManager;

import java.nio.channels.SelectionKey;
import java.util.concurrent.ExecutorService;

import com.bitdubai.fermat_api.layer._10_communication.CommunicationChannelAddress;
import com.bitdubai.fermat_api.layer._10_communication.cloud.CloudConnectionException;
import com.bitdubai.fermat_api.layer._10_communication.fmp.FMPException;
import com.bitdubai.fermat_api.layer._10_communication.fmp.FMPPacket;
import com.bitdubai.fermat_api.layer._1_definition.communication.cloud.CloudFMPConnectionManager;

public class MockCloudFMPConnectionManagerServer extends
		CloudFMPConnectionManager {
	
	public MockCloudFMPConnectionManagerServer(
			CommunicationChannelAddress address, ExecutorService executor,
			String privateKey, String publicKey)
			throws IllegalArgumentException {
		super(address, executor, privateKey, publicKey, CloudFMPConnectionManagerMode.FMP_SERVER);
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
	public void processDataPacket(String data, SelectionKey key)
			throws CloudConnectionException {
		// TODO Auto-generated method stub
		
	}

}
