package EmptyCustomerBrokerNegotiationInformationImpl;

import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.EmptyCustomerBrokerNegotiationInformationImpl;

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
        EmptyCustomerBrokerNegotiationInformationImpl emptyCustomerBrokerNegotiationInformation = mock(EmptyCustomerBrokerNegotiationInformationImpl.class);
        when(emptyCustomerBrokerNegotiationInformation.getStatus()).thenReturn(NegotiationStatus.WAITING_FOR_BROKER);
        assertThat(emptyCustomerBrokerNegotiationInformation.getStatus()).isNotNull();
    }
}
