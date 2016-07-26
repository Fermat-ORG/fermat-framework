package CryptoCustomerWalletModuleClausesImpl;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
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
public class GetTypeTest {
    @Test
    public void getType() {
        CryptoCustomerWalletModuleClausesImpl cryptoCustomerWalletModuleClauses = mock(CryptoCustomerWalletModuleClausesImpl.class);
        when(cryptoCustomerWalletModuleClauses.getType()).thenReturn(ClauseType.BROKER_CRYPTO_ADDRESS);
        assertThat(cryptoCustomerWalletModuleClauses.getType()).isNotNull();
    }
}
