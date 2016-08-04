package CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation;

import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by roy on 9/02/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetNegotiationIdTest {
    @Test
    public void getNegotiationId() {
        CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation = mock(CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation.class);
        when(cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation.getNegotiationId()).thenReturn(UUID.randomUUID());
        assertThat(cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation.getNegotiationId()).isNotNull();
    }
}
