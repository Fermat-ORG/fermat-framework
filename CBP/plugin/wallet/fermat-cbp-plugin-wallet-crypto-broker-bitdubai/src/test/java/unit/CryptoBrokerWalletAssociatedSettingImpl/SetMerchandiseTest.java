package unit.CryptoBrokerWalletAssociatedSettingImpl;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util.CryptoBrokerWalletAssociatedSettingImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

/**
 * Created by José Vilchez on 21/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class SetMerchandiseTest {

    @Test
    public void setMerchandise() {
        CryptoBrokerWalletAssociatedSettingImpl cryptoBrokerWalletAssociatedSetting = mock(CryptoBrokerWalletAssociatedSettingImpl.class, Mockito.RETURNS_DEEP_STUBS);
        doCallRealMethod().when(cryptoBrokerWalletAssociatedSetting).setMerchandise(Mockito.any(Currency.class));
    }

}
