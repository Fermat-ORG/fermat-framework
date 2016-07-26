package CryptoCustomerWalletProviderSettingImpl;

import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerWalletProviderSettingImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

/**
 * Created by roy on 9/02/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class SetCustomerPublicKeyTest {
    @Test
    public void setCustomerPublicKey() {
        CryptoCustomerWalletProviderSettingImpl cryptoCustomerWalletProviderSetting = mock(CryptoCustomerWalletProviderSettingImpl.class, Mockito.RETURNS_DEEP_STUBS);
        doCallRealMethod().when(cryptoCustomerWalletProviderSetting).setCustomerPublicKey(Mockito.any(String.class));
    }
}
