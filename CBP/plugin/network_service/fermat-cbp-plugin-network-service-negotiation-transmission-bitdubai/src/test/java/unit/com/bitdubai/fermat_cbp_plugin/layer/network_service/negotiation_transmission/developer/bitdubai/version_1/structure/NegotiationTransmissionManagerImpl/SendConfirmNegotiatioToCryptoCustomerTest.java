package unit.com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.structure.NegotiationTransmissionManagerImpl;

import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionType;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation_transaction.NegotiationTransaction;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.structure.NegotiationTransmissionManagerImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

/**
 * Created by Yordin Alayn on 01.01.16.
 */
@RunWith(MockitoJUnitRunner.class)
public class SendConfirmNegotiatioToCryptoCustomerTest {

    @Mock
    private NegotiationTransaction negotiationTransaction;

    private final NegotiationTransactionType transactionType = NegotiationTransactionType.CUSTOMER_BROKER_CLOSE;

    @Test
    public void sendConfirmNegotiatioToCryptoCustomer() throws Exception {

        NegotiationTransmissionManagerImpl negotiationTransmissionManagerImpl = mock(NegotiationTransmissionManagerImpl.class, Mockito.RETURNS_DEEP_STUBS);
        doCallRealMethod().when(negotiationTransmissionManagerImpl).sendConfirmNegotiationToCryptoCustomer(negotiationTransaction, transactionType);

    }
}
