package unit.CryptoBrokerWalletPluginRoot;

import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.mocks.CryptoBrokerWalletMock;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CryptoBrokerWalletNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWallet;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.CryptoBrokerWalletPluginRoot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by José Vilchez on 21/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoadCryptoBrokerWalletTest {
    CryptoBrokerWallet cryptoBrokerWallet = new CryptoBrokerWalletMock();

    @Test
    public void loadCryptoBrokerWallet() throws CryptoBrokerWalletNotFoundException {
        CryptoBrokerWalletPluginRoot cryptoBrokerWalletPluginRoot = mock(CryptoBrokerWalletPluginRoot.class, Mockito.RETURNS_DEEP_STUBS);
        when((cryptoBrokerWalletPluginRoot).loadCryptoBrokerWallet(Mockito.any(String.class))).thenReturn(cryptoBrokerWallet);
    }

}
