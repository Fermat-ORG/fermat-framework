package CustomerBrokerPurchaseNegotiationImpl;

import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CustomerBrokerPurchaseNegotiationImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by roy on 9/02/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetStatusTest {
    @Test
    public void getStatus() {
        CustomerBrokerPurchaseNegotiationImpl customerBrokerPurchaseNegotiation = mock(CustomerBrokerPurchaseNegotiationImpl.class);
        when(customerBrokerPurchaseNegotiation.getStatus()).thenReturn(NegotiationStatus.WAITING_FOR_BROKER);
        assertThat(customerBrokerPurchaseNegotiation.getStatus()).isNotNull();
    }
}
