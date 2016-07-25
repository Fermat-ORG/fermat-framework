package unit.com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.structure.CustomerBrokerNewManagerImpl;

import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.interfaces.CustomerBrokerNew;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.structure.CustomerBrokerNewManagerImpl;

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
public class GetAllCustomerBrokerNewNegotiationTranasctionTest {

    @Test
    public void getAllCustomerBrokerNewNegotiationTranasction() throws Exception {

        List<CustomerBrokerNew> list = new ArrayList<>();

        CustomerBrokerNewManagerImpl customerBrokerNewManagerImpl = mock(CustomerBrokerNewManagerImpl.class, Mockito.RETURNS_DEEP_STUBS);
        when(customerBrokerNewManagerImpl.getAllCustomerBrokerNewNegotiationTransaction()).thenReturn(list).thenCallRealMethod();
        assertThat(customerBrokerNewManagerImpl.getAllCustomerBrokerNewNegotiationTransaction()).isNotNull();

    }
}
