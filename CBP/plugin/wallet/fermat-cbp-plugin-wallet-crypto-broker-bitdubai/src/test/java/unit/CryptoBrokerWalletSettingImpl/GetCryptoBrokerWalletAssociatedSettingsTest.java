package unit.CryptoBrokerWalletSettingImpl;

import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
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
public class GetCryptoBrokerWalletAssociatedSettingsTest {

    @Test
    public void getCryptoBrokerWalletAssociatedSettings() throws CantGetCryptoBrokerWalletSettingException {
        CryptoBrokerWalletSettingImpl cryptoBrokerWalletSetting = mock(CryptoBrokerWalletSettingImpl.class);
        when(cryptoBrokerWalletSetting.getCryptoBrokerWalletAssociatedSettings()).thenReturn(new ArrayList<CryptoBrokerWalletAssociatedSetting>());
        assertThat(cryptoBrokerWalletSetting.getCryptoBrokerWalletAssociatedSettings()).isNotNull();
    }

}
