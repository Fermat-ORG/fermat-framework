package CryptoCustomerWalletAssociatedSettingImpl;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerWalletAssociatedSettingImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

/**
 * Created by Roy on 6/02/16.
 */

@RunWith(MockitoJUnitRunner.class)
public class SetPlatformTest {
    @Test
    public void setPlatform() {
        CryptoCustomerWalletAssociatedSettingImpl cryptoCustomerWalletAssociatedSetting = mock(CryptoCustomerWalletAssociatedSettingImpl.class, Mockito.RETURNS_DEEP_STUBS);
        doCallRealMethod().when(cryptoCustomerWalletAssociatedSetting).setPlatform(Mockito.any(Platforms.class));
    }
}
