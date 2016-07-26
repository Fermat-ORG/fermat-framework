package CryptoCustomerWalletProviderSettingImpl;

import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerWalletProviderSettingImpl;

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
public class GetCustomerPublicKeyTest {
    @Test
    public void getCustomerPublicKey() {
        CryptoCustomerWalletProviderSettingImpl cryptoCustomerWalletProviderSetting = mock(CryptoCustomerWalletProviderSettingImpl.class);
        when(cryptoCustomerWalletProviderSetting.getCustomerPublicKey()).thenReturn(new String());
        assertThat(cryptoCustomerWalletProviderSetting.getCustomerPublicKey()).isNotNull();
    }
}
