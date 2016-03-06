package unit.CryptoBrokerWalletPluginRoot;

import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantCreateCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.CryptoBrokerWalletPluginRoot;

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
public class CreateCryptoBrokerWalletTest {

    @Test
    public void createCryptoBrokerWallet() throws CantCreateCryptoBrokerWalletException {
        CryptoBrokerWalletPluginRoot cryptoBrokerWalletPluginRoot = mock(CryptoBrokerWalletPluginRoot.class, Mockito.RETURNS_DEEP_STUBS);
        doCallRealMethod().when(cryptoBrokerWalletPluginRoot).createCryptoBrokerWallet(Mockito.any(String.class));
    }
}
