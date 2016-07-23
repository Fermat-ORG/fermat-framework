package unit.CryptoBrokerWalletPluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.CryptoBrokerWalletPluginRoot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by José Vilchez on 21/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetDatabaseListTest {

    @Test
    public void getDatabaseList() throws Exception {
        CryptoBrokerWalletPluginRoot cryptoBrokerWalletPluginRoot = mock(CryptoBrokerWalletPluginRoot.class);
        when(cryptoBrokerWalletPluginRoot.getDatabaseList(Mockito.any(DeveloperObjectFactory.class))).thenReturn(new ArrayList<DeveloperDatabase>());
        assertThat(cryptoBrokerWalletPluginRoot.getDatabaseList(Mockito.any(DeveloperObjectFactory.class))).isNotNull();
    }

}
