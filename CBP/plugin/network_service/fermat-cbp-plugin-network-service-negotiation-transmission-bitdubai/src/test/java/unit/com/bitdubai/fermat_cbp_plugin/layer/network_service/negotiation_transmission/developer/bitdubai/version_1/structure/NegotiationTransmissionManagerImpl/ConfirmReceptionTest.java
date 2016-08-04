package unit.com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.structure.NegotiationTransmissionManagerImpl;

import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.structure.NegotiationTransmissionManagerImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

/**
 * Created by Yordin Alayn on 01.01.16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConfirmReceptionTest {

    private final UUID transmissionId = UUID.randomUUID();

    @Test
    public void confirmReception() throws Exception {

        NegotiationTransmissionManagerImpl negotiationTransmissionManagerImpl = mock(NegotiationTransmissionManagerImpl.class, Mockito.RETURNS_DEEP_STUBS);
        doCallRealMethod().when(negotiationTransmissionManagerImpl).confirmReception(transmissionId);

    }

}
