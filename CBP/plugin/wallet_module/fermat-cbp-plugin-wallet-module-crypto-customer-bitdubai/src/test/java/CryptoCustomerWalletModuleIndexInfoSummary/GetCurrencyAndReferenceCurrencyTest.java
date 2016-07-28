package CryptoCustomerWalletModuleIndexInfoSummary;

import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerWalletModuleIndexInfoSummary;

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
public class GetCurrencyAndReferenceCurrencyTest {
    @Test
    public void getCurrencyAndReferenceCurrency() {
        CryptoCustomerWalletModuleIndexInfoSummary cryptoCustomerWalletModuleIndexInfoSummary = mock(CryptoCustomerWalletModuleIndexInfoSummary.class);
        when(cryptoCustomerWalletModuleIndexInfoSummary.getCurrencyAndReferenceCurrency()).thenReturn(new String());
        assertThat(cryptoCustomerWalletModuleIndexInfoSummary.getCurrencyAndReferenceCurrency()).isNotNull();
    }
}
