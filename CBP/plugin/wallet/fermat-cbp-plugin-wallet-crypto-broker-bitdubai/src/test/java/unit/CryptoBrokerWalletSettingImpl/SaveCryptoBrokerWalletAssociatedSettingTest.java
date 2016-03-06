package unit.CryptoBrokerWalletSettingImpl;

import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantSaveCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util.CryptoBrokerWalletSettingImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

/**
 * Created by José Vilchez on 22/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class SaveCryptoBrokerWalletAssociatedSettingTest {

    @Test
    public void saveCryptoBrokerWalletAssociatedSetting() throws CantSaveCryptoBrokerWalletSettingException {
        CryptoBrokerWalletSettingImpl cryptoBrokerWalletSetting = mock(CryptoBrokerWalletSettingImpl.class, Mockito.RETURNS_DEEP_STUBS);
        doCallRealMethod().when(cryptoBrokerWalletSetting).saveCryptoBrokerWalletAssociatedSetting(Mockito.any(CryptoBrokerWalletAssociatedSetting.class));
    }

}
