package CryptoCustomerWalletProviderSettingImpl;

import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerWalletProviderSettingImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by roy on 9/02/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructionTest {
    UUID id = UUID.randomUUID();
    String customerPublicKey = new String();
    UUID plugin = UUID.randomUUID();
    String description = new String();

    @Test
    public void Construction_ValidParameters_NewObjectCreated() {
        CryptoCustomerWalletProviderSettingImpl cryptoCustomerWalletProviderSetting = new CryptoCustomerWalletProviderSettingImpl();
        assertThat(cryptoCustomerWalletProviderSetting).isNotNull();
    }
}
