package unit.CryptoBrokerWalletSettingSpreadImpl;

import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util.CryptoBrokerWalletSettingSpreadImpl;

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
public class SetRestockAutomaticTest {

    @Test
    public void setRestockAutomatic() {
        CryptoBrokerWalletSettingSpreadImpl cryptoBrokerWalletSettingSpread = mock(CryptoBrokerWalletSettingSpreadImpl.class, Mockito.RETURNS_DEEP_STUBS);
        doCallRealMethod().when(cryptoBrokerWalletSettingSpread).setRestockAutomatic(Mockito.any(boolean.class));
    }

}
