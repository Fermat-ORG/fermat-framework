package CryptoCustomerWalletAssociatedSettingImpl;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerWalletAssociatedSettingImpl;

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
public class GetPlatformTest {
    @Test
    public void getPlatform() {
        CryptoCustomerWalletAssociatedSettingImpl cryptoCustomerWalletAssociatedSetting = mock(CryptoCustomerWalletAssociatedSettingImpl.class);
        when(cryptoCustomerWalletAssociatedSetting.getPlatform()).thenReturn(Platforms.BANKING_PLATFORM);
        assertThat(cryptoCustomerWalletAssociatedSetting.getPlatform()).isNotNull();
    }
}
