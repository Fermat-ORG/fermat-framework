package utils.CustomerBrokerPurcharseImpl;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_cbp_api.layer.user_level_business_transaction.common.enums.TransactionStatus;
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
public class getTransactionStatus {

    @Test
    public void getPurchaseStatus() {
        CustomerBrokerPurchaseImpl customerBrokerPurchaseImpl = mock(CustomerBrokerPurchaseImpl.class);
        try {
            when(customerBrokerPurchaseImpl.getTransactionStatus()).thenReturn(TransactionStatus.getByCode(new String()));
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        }
        assertThat(customerBrokerPurchaseImpl.getTransactionStatus()).isNotNull();
    }

}