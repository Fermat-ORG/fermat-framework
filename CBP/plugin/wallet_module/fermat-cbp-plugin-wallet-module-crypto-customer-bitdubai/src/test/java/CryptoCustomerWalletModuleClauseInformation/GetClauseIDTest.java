package CryptoCustomerWalletModuleClauseInformation;

import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.classes.CryptoCustomerWalletModuleClauseInformation;

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
public class GetClauseIDTest {
    @Test
    public void getClauseID() {
        CryptoCustomerWalletModuleClauseInformation cryptoCustomerWalletModuleClauseInformation = mock(CryptoCustomerWalletModuleClauseInformation.class);
        when(cryptoCustomerWalletModuleClauseInformation.getClauseID()).thenReturn(UUID.randomUUID());
        assertThat(cryptoCustomerWalletModuleClauseInformation.getClauseID()).isNotNull();
    }
}
