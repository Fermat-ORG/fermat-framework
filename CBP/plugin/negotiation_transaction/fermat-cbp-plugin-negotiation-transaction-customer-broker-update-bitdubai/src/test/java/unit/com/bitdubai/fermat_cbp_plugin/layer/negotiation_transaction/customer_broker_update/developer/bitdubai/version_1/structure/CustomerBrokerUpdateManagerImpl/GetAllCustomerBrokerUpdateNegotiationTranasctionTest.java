package unit.com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.structure.CustomerBrokerUpdateManagerImpl;

import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_update.interfaces.CustomerBrokerUpdate;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.structure.CustomerBrokerUpdateManagerImpl;

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
 * Created by Yordin Alayn on 02.01.16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetAllCustomerBrokerUpdateNegotiationTranasctionTest {

    @Test
    public void getAllCustomerBrokerUpdateNegotiationTranasction() throws Exception {

        List<CustomerBrokerUpdate> list = new ArrayList<>();

        CustomerBrokerUpdateManagerImpl customerBrokerUpdateManagerImpl = mock(CustomerBrokerUpdateManagerImpl.class, Mockito.RETURNS_DEEP_STUBS);
        when(customerBrokerUpdateManagerImpl.getAllCustomerBrokerNewNegotiationTranasction()).thenReturn(list).thenCallRealMethod();
        assertThat(customerBrokerUpdateManagerImpl.getAllCustomerBrokerNewNegotiationTranasction()).isNotNull();

    }
}
