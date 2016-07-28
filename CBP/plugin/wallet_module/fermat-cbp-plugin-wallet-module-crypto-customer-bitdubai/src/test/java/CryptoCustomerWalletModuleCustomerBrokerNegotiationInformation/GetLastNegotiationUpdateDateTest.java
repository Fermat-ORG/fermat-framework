package CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation;

import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation;

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
public class GetLastNegotiationUpdateDateTest {
    @Test
    public void getLastNegotiationUpdateDate() {
        CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation = mock(CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation.class);
        when(cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation.getLastNegotiationUpdateDate()).thenReturn(0L);
        assertThat(cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation.getLastNegotiationUpdateDate()).isNotNull();
    }
}
