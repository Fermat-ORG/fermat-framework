package utils;

import com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_sale.developer.bitdubai.version_1.utils.CustomerBrokerSaleImpl;

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
public class getContractTransactionId {

    @Test
    public void getContractTransactionId() {
        CustomerBrokerSaleImpl customerBrokerPurchaseImpl = mock(CustomerBrokerSaleImpl.class);
        when(customerBrokerPurchaseImpl.getContractTransactionId()).thenReturn(new String());
        assertThat(customerBrokerPurchaseImpl.getContractTransactionId()).isNotNull();
    }

}