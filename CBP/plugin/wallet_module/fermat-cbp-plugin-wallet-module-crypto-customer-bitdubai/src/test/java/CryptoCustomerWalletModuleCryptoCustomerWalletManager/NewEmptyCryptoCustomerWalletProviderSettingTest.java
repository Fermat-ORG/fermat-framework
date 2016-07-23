package CryptoCustomerWalletModuleCryptoCustomerWalletManager;

import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantNewEmptyCryptoCustomerWalletProviderSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.settings.CryptoCustomerWalletProviderSetting;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerWalletModuleCryptoCustomerWalletManager;

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
public class NewEmptyCryptoCustomerWalletProviderSettingTest {
    @Test
    public void newEmptyCryptoCustomerWalletProviderSetting() throws CantNewEmptyCryptoCustomerWalletProviderSettingException {
        CryptoCustomerWalletModuleCryptoCustomerWalletManager cryptoCustomerWalletModuleCryptoCustomerWalletManager = mock(CryptoCustomerWalletModuleCryptoCustomerWalletManager.class);
        when(cryptoCustomerWalletModuleCryptoCustomerWalletManager.newEmptyCryptoCustomerWalletProviderSetting()).thenReturn(new CryptoCustomerWalletProviderSetting() {
            @Override
            public UUID getId() {
                return null;
            }

            @Override
            public void setId(UUID id) {

            }

            @Override
            public String getCustomerPublicKey() {
                return null;
            }

            @Override
            public void setCustomerPublicKey(String customerPublicKey) {

            }

            @Override
            public UUID getPlugin() {
                return null;
            }

            @Override
            public void setPlugin(UUID plugin) {

            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public void setDescription(String description) {

            }
        });
        assertThat(cryptoCustomerWalletModuleCryptoCustomerWalletManager.newEmptyCryptoCustomerWalletProviderSetting()).isNotNull();
    }
}
