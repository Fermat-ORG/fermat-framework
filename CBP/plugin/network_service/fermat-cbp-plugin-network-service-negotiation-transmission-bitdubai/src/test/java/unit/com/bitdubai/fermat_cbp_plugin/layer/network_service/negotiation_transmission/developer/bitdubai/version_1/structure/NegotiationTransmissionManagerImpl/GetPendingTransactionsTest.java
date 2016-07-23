package unit.com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.structure.NegotiationTransmissionManagerImpl;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.interfaces.NegotiationTransmission;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.structure.NegotiationTransmissionManagerImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by yordin on 01/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetPendingTransactionsTest {

    private final Specialist specialist = Specialist.UNKNOWN_SPECIALIST;

    @Test
    public void getPendingTransactions() throws Exception {

        List<Transaction<NegotiationTransmission>> pendingTransaction = new ArrayList<>();

        NegotiationTransmissionManagerImpl negotiationTransmissionManagerImpl = mock(NegotiationTransmissionManagerImpl.class, Mockito.RETURNS_DEEP_STUBS);
        when(negotiationTransmissionManagerImpl.getPendingTransactions(specialist)).thenReturn(pendingTransaction).thenCallRealMethod();
        assertThat(negotiationTransmissionManagerImpl.getPendingTransactions(specialist)).isNotNull();

    }

}
