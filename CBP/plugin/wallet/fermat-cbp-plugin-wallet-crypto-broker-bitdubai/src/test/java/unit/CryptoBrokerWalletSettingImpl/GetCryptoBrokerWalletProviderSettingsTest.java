package unit.CryptoBrokerWalletSettingImpl;

import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletProviderSetting;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util.CryptoBrokerWalletSettingImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by José Vilchez on 22/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetCryptoBrokerWalletProviderSettingsTest {

    @Test
    public void getCryptoBrokerWalletProviderSettings() throws CantGetCryptoBrokerWalletSettingException {
        CryptoBrokerWalletSettingImpl cryptoBrokerWalletSetting = mock(CryptoBrokerWalletSettingImpl.class);
        when(cryptoBrokerWalletSetting.getCryptoBrokerWalletProviderSettings()).thenReturn(new ArrayList<CryptoBrokerWalletProviderSetting>());
        assertThat(cryptoBrokerWalletSetting.getCryptoBrokerWalletProviderSettings()).isNotNull();
    }

}
