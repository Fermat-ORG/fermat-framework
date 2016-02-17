package CryptoCustomerWalletProviderSettingImpl;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.settings.CryptoCustomerWalletProviderSetting;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerWalletProviderSettingImpl;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by roy on 9/02/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class SetDescriptionTest {
    @Test
    public void setDescription(){
        CryptoCustomerWalletProviderSettingImpl cryptoCustomerWalletProviderSetting = mock(CryptoCustomerWalletProviderSettingImpl.class, Mockito.RETURNS_DEEP_STUBS);
        doCallRealMethod().when(cryptoCustomerWalletProviderSetting).setDescription(Mockito.any(String.class));
    }
}
