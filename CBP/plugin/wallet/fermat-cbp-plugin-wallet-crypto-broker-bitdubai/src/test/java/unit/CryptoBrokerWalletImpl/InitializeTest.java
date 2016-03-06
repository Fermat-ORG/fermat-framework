package unit.CryptoBrokerWalletImpl;

import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CryptoBrokerWalletNotFoundException;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util.CryptoBrokerWalletImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

/**
 * Created by José Vilchez on 22/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class InitializeTest {

    @Test
    public void initialize() throws CryptoBrokerWalletNotFoundException {
        CryptoBrokerWalletImpl cryptoBrokerWallet = mock(CryptoBrokerWalletImpl.class);
        doCallRealMethod().when(cryptoBrokerWallet).initialize(Mockito.any(UUID.class));
    }

}
