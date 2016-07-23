package CryptoCustomerWalletModuleClausesImpl;

import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerWalletModuleClausesImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * Created by roy on 7/02/16.
 */

@RunWith(MockitoJUnitRunner.class)
public class GetValueTest {
    @Test
    public void getValue() {
        CryptoCustomerWalletModuleClausesImpl cryptoCustomerWalletModuleClauses = mock(CryptoCustomerWalletModuleClausesImpl.class);
        when(cryptoCustomerWalletModuleClauses.getValue()).thenReturn(new String());
        assertThat(cryptoCustomerWalletModuleClauses.getValue()).isNotNull();
    }
}
