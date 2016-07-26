package CryptoCustomerWalletModuleClausesImpl;

import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerWalletModuleClausesImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by roy on 7/02/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetClauseIdTest {
    @Test
    public void getClauseId() {
        CryptoCustomerWalletModuleClausesImpl cryptoCustomerWalletModuleClauses = mock(CryptoCustomerWalletModuleClausesImpl.class);
        when(cryptoCustomerWalletModuleClauses.getClauseId()).thenReturn(UUID.randomUUID());
        assertThat(cryptoCustomerWalletModuleClauses.getClauseId()).isNotNull();
    }
}
