package unit.CryptoBrokerWalletSettingImpl;

import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletSettingSpread;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util.CryptoBrokerWalletSettingImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by José Vilchez on 22/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetCryptoBrokerWalletSpreadSettingTest {

    @Test
    public void getCryptoBrokerWalletSpreadSetting() throws CantGetCryptoBrokerWalletSettingException {
        CryptoBrokerWalletSettingImpl cryptoBrokerWalletSetting = mock(CryptoBrokerWalletSettingImpl.class);
        when(cryptoBrokerWalletSetting.getCryptoBrokerWalletSpreadSetting()).thenReturn(new CryptoBrokerWalletSettingSpread() {
            @Override
            public UUID getId() {
                return null;
            }

            @Override
            public void setId(UUID id) {

            }

            @Override
            public String getBrokerPublicKey() {
                return null;
            }

            @Override
            public void setBrokerPublicKey(String brokerPublicKey) {

            }

            @Override
            public float getSpread() {
                return 0;
            }

            @Override
            public void setSpread(float spread) {

            }

            @Override
            public boolean getRestockAutomatic() {
                return false;
            }

            @Override
            public void setRestockAutomatic(boolean restockAutomatic) {

            }
        });
        assertThat(cryptoBrokerWalletSetting.getCryptoBrokerWalletSpreadSetting()).isNotNull();
    }

}
