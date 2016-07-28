package unit.CryptoBrokerWalletProviderSettingImpl;

import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util.CryptoBrokerWalletProviderSettingImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by José Vilchez on 22/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetBrokerPublicKeyTest {

    @Test
    public void getBrokerPublicKey() {
        CryptoBrokerWalletProviderSettingImpl cryptoBrokerWalletProviderSetting = mock(CryptoBrokerWalletProviderSettingImpl.class);
        when(cryptoBrokerWalletProviderSetting.getBrokerPublicKey()).thenReturn(new String());
        assertThat(cryptoBrokerWalletProviderSetting.getBrokerPublicKey()).isNotNull();
    }

}
