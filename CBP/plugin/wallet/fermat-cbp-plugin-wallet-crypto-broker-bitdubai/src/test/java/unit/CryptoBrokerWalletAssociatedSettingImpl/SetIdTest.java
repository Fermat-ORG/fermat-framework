package unit.CryptoBrokerWalletAssociatedSettingImpl;

import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util.CryptoBrokerWalletAssociatedSettingImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

/**
 * Created by José Vilchez on 21/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class SetIdTest {

    @Test
    public void setId() {
        CryptoBrokerWalletAssociatedSettingImpl cryptoBrokerWalletAssociatedSetting = mock(CryptoBrokerWalletAssociatedSettingImpl.class, Mockito.RETURNS_DEEP_STUBS);
        doCallRealMethod().when(cryptoBrokerWalletAssociatedSetting).setId(Mockito.any(UUID.class));
    }

}
