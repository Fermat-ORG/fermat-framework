package unit.CryptoBrokerWalletProviderSettingImpl;

import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util.CryptoBrokerWalletProviderSettingImpl;

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
public class SetDescriptionTest {

    @Test
    public void setDescription() {
        CryptoBrokerWalletProviderSettingImpl cryptoBrokerWalletProviderSetting = mock(CryptoBrokerWalletProviderSettingImpl.class, Mockito.RETURNS_DEEP_STUBS);
        doCallRealMethod().when(cryptoBrokerWalletProviderSetting).setDescription(Mockito.any(String.class));
    }

}
