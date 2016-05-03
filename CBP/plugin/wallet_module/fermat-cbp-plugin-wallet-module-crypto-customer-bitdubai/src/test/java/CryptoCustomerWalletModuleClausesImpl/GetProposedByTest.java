package CryptoCustomerWalletModuleClausesImpl;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerWalletModuleClausesImpl;

import java.util.UUID;

import org.bitcoinj.testing.MockTransactionBroadcaster;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyShort;
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
public class GetProposedByTest {
    @Test
    public void getProposedBy() {
        CryptoCustomerWalletModuleClausesImpl cryptoCustomerWalletModuleClauses = mock(CryptoCustomerWalletModuleClausesImpl.class);
        when(cryptoCustomerWalletModuleClauses.getProposedBy()).thenReturn(new String());
        assertThat(cryptoCustomerWalletModuleClauses.getProposedBy()).isNotNull();
    }
}
