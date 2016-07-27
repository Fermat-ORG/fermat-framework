package CryptoCustomerWalletModuleActorIdentityImpl;

import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerWalletModuleActorIdentityImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by roy on 6/02/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetPublicKeyTest {
    @Test
    public void getPublicKey() {
        CryptoCustomerWalletModuleActorIdentityImpl cryptoCustomerWalletModuleActorIdentity = mock(CryptoCustomerWalletModuleActorIdentityImpl.class);
        when(cryptoCustomerWalletModuleActorIdentity.getPublicKey()).thenReturn(new String());
        assertThat(cryptoCustomerWalletModuleActorIdentity.getPublicKey()).isNotNull();
    }
}
