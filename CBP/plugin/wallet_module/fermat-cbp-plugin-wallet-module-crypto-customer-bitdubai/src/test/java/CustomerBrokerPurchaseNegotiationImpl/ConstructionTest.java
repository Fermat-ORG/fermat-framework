package CustomerBrokerPurchaseNegotiationImpl;

import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CustomerBrokerPurchaseNegotiationImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by roy on 9/02/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructionTest {
    @Test
    public void Construction_ValidParameters_NewObjectCreated() {
        CustomerBrokerPurchaseNegotiationImpl customerBrokerPurchaseNegotiation = new CustomerBrokerPurchaseNegotiationImpl();
        assertThat(customerBrokerPurchaseNegotiation).isNotNull();
    }
}
