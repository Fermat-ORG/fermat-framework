package unit.CryptoBrokerWalletPluginRoot;

import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.CryptoBrokerWalletPluginRoot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by Jose Vilchez on 18/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructionTest {

    @Test
    public void Construction_ValidParameters_NewObjectCreated() {

        CryptoBrokerWalletPluginRoot cryptoBrokerWalletPluginRoot = new CryptoBrokerWalletPluginRoot();
        assertThat(cryptoBrokerWalletPluginRoot).isNotNull();
    }
}
