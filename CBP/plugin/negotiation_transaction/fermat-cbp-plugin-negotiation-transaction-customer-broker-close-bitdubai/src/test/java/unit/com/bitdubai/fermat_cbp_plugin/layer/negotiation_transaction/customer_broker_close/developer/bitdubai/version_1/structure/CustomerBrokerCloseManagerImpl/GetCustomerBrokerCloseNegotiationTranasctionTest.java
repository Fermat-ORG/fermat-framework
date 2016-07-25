package unit.com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.structure.CustomerBrokerCloseManagerImpl;

import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.interfaces.CustomerBrokerClose;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.structure.CustomerBrokerCloseManagerImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Yordin Alayn on 02.01.16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetCustomerBrokerCloseNegotiationTranasctionTest {

    private UUID transactionId = UUID.randomUUID();

    @Test
    public void getCustomerBrokerNewNegotiationTranasction() throws Exception {

        CustomerBrokerClose customerBrokerClose = null;

        CustomerBrokerCloseManagerImpl customerBrokerCloseManagerImpl = mock(CustomerBrokerCloseManagerImpl.class, Mockito.RETURNS_DEEP_STUBS);
        when(customerBrokerCloseManagerImpl.getCustomerBrokerCloseNegotiationTranasction(transactionId)).thenReturn(customerBrokerClose).thenCallRealMethod();
        assertThat(customerBrokerCloseManagerImpl.getCustomerBrokerCloseNegotiationTranasction(transactionId)).isNotNull();

    }
}
