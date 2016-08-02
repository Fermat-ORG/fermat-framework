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
public class ConstructionTest {
    UUID id = UUID.randomUUID();
    String customerPublicKey = new String();
    UUID plugin = UUID.randomUUID();
    String description = new String();

    @Test
    public void Construction_ValidParameters_NewObjectCreated(){
        CryptoCustomerWalletProviderSettingImpl cryptoCustomerWalletProviderSetting = new CryptoCustomerWalletProviderSettingImpl();
        assertThat(cryptoCustomerWalletProviderSetting).isNotNull();
    }
}
