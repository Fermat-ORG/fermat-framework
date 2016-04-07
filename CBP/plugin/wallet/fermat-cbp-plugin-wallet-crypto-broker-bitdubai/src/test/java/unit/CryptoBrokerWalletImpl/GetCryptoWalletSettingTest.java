package unit.CryptoBrokerWalletImpl;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantClearCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantSaveCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletProviderSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletSettingSpread;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util.CryptoBrokerWalletImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by José Vilchez on 22/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetCryptoWalletSettingTest {

    @Test
    public void getCryptoWalletSetting() throws CantGetCryptoBrokerWalletSettingException {
        CryptoBrokerWalletImpl cryptoBrokerWallet = mock(CryptoBrokerWalletImpl.class);
        when(cryptoBrokerWallet.getCryptoWalletSetting()).thenReturn(new CryptoBrokerWalletSetting() {
            @Override
            public void saveCryptoBrokerWalletSpreadSetting(CryptoBrokerWalletSettingSpread cryptoBrokerWalletSettingSpread) throws CantSaveCryptoBrokerWalletSettingException {

            }

            @Override
            public void clearCryptoBrokerWalletSpreadSetting() throws CantClearCryptoBrokerWalletSettingException {

            }

            @Override
            public CryptoBrokerWalletSettingSpread getCryptoBrokerWalletSpreadSetting() throws CantGetCryptoBrokerWalletSettingException {
                return null;
            }

            @Override
            public void saveCryptoBrokerWalletAssociatedSetting(CryptoBrokerWalletAssociatedSetting cryptoBrokerWalletAssociatedSetting) throws CantSaveCryptoBrokerWalletSettingException {

            }

            @Override
            public void clearCryptoBrokerWalletAssociatedSetting(Platforms platform) throws CantClearCryptoBrokerWalletSettingException {

            }

            @Override
            public List<CryptoBrokerWalletAssociatedSetting> getCryptoBrokerWalletAssociatedSettings() throws CantGetCryptoBrokerWalletSettingException {
                return null;
            }

            @Override
            public void saveCryptoBrokerWalletProviderSetting(CryptoBrokerWalletProviderSetting cryptoBrokerWalletProviderSetting) throws CantSaveCryptoBrokerWalletSettingException {

            }

            @Override
            public void clearCryptoBrokerWalletProviderSetting() throws CantClearCryptoBrokerWalletSettingException {

            }

            @Override
            public List<CryptoBrokerWalletProviderSetting> getCryptoBrokerWalletProviderSettings() throws CantGetCryptoBrokerWalletSettingException {
                return null;
            }
        });
        assertThat(cryptoBrokerWallet.getCryptoWalletSetting()).isNotNull();
    }

}
