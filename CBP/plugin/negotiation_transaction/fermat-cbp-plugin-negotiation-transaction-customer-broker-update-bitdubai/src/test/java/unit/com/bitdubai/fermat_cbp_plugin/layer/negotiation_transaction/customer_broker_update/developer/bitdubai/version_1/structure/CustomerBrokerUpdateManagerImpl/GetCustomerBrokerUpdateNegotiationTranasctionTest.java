package unit.com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.structure.CustomerBrokerUpdateManagerImpl;

import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_update.interfaces.CustomerBrokerUpdate;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.structure.CustomerBrokerUpdateManagerImpl;

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
public class GetCustomerBrokerUpdateNegotiationTranasctionTest {

    private UUID transactionId = UUID.randomUUID();

    @Test
    public void getCustomerBrokerUpdateNegotiationTranasction() throws Exception {

        CustomerBrokerUpdate customerBrokerUpdate = null;

        CustomerBrokerUpdateManagerImpl customerBrokerNewManagerImpl = mock(CustomerBrokerUpdateManagerImpl.class, Mockito.RETURNS_DEEP_STUBS);
        when(customerBrokerNewManagerImpl.getCustomerBrokerNewNegotiationTranasction(transactionId)).thenReturn(customerBrokerUpdate).thenCallRealMethod();
        assertThat(customerBrokerNewManagerImpl.getCustomerBrokerNewNegotiationTranasction(transactionId)).isNotNull();

    }
}
