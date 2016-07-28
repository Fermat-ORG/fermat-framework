package CryptoCustomerWalletModuleClauseInformation;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.classes.CryptoCustomerWalletModuleClauseInformation;

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
public class GetStatusTest {
    @Test
    public void getStatus() {
        CryptoCustomerWalletModuleClauseInformation cryptoCustomerWalletModuleClauseInformation = mock(CryptoCustomerWalletModuleClauseInformation.class);
        when(cryptoCustomerWalletModuleClauseInformation.getStatus()).thenReturn(ClauseStatus.AGREED);
        assertThat(cryptoCustomerWalletModuleClauseInformation.getStatus()).isNotNull();
    }
}
