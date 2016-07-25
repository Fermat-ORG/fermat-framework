package CryptoCustomerWalletProviderSettingImpl;

import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerWalletProviderSettingImpl;

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
public class GetIdTest {
    @Test
    public void getId() {
        CryptoCustomerWalletProviderSettingImpl cryptoCustomerWalletProviderSetting = mock(CryptoCustomerWalletProviderSettingImpl.class);
        when(cryptoCustomerWalletProviderSetting.getId()).thenReturn(UUID.randomUUID());
        assertThat(cryptoCustomerWalletProviderSetting.getId()).isNotNull();
    }
}
