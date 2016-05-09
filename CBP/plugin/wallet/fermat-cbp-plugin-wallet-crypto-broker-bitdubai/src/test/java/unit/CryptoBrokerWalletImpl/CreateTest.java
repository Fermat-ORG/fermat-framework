package unit.CryptoBrokerWalletImpl;

import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantCreateCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util.CryptoBrokerWalletImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by José Vilchez on 22/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class CreateTest {

    @Test
    public void create() throws CantCreateCryptoBrokerWalletException {
        CryptoBrokerWalletImpl cryptoBrokerWallet = mock(CryptoBrokerWalletImpl.class);
        when(cryptoBrokerWallet.create(Mockito.any(String.class))).thenReturn(UUID.randomUUID()).thenCallRealMethod();
        assertThat(cryptoBrokerWallet.create(Mockito.any(String.class))).isNotNull();
    }

}
