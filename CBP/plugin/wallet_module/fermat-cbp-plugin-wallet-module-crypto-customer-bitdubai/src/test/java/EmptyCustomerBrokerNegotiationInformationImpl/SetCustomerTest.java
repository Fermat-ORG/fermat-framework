package EmptyCustomerBrokerNegotiationInformationImpl;

import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.EmptyCustomerBrokerNegotiationInformationImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

/**
 * Created by roy on 9/02/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class SetCustomerTest {
    @Test
    public void setCustomer() {
        EmptyCustomerBrokerNegotiationInformationImpl emptyCustomerBrokerNegotiationInformation = mock(EmptyCustomerBrokerNegotiationInformationImpl.class, Mockito.RETURNS_DEEP_STUBS);
        doCallRealMethod().when(emptyCustomerBrokerNegotiationInformation).setCustomer(Mockito.any(ActorIdentity.class));
    }
}
