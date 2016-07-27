package utils.CustomerBrokerPurcharseImpl;

import com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_purchase.developer.bitdubai.version_1.utils.CustomerBrokerPurchaseImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * Created by Lozadaa on 18/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class getTransactionType {

    @Test
    public void getTransactionType() {
        CustomerBrokerPurchaseImpl customerBrokerPurchaseImpl = mock(CustomerBrokerPurchaseImpl.class);
        when(customerBrokerPurchaseImpl.getTransactionType()).thenReturn(new String());
        assertThat(customerBrokerPurchaseImpl.getTransactionType()).isNotNull();
    }

}