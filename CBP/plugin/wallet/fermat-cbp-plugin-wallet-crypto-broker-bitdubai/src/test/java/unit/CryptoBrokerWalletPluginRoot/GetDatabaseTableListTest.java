package unit.CryptoBrokerWalletPluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
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
public class GetDatabaseTableListTest {

    @Test
    public void getDatabaseTableList() throws Exception {
        CryptoBrokerWalletPluginRoot cryptoBrokerWalletPluginRoot = mock(CryptoBrokerWalletPluginRoot.class);
        when(cryptoBrokerWalletPluginRoot.getDatabaseTableList(Mockito.any(DeveloperObjectFactory.class), Mockito.any(DeveloperDatabase.class))).thenReturn(new ArrayList<DeveloperDatabaseTable>());
        assertThat(cryptoBrokerWalletPluginRoot.getDatabaseTableList(Mockito.any(DeveloperObjectFactory.class), Mockito.any(DeveloperDatabase.class))).isNotNull();
    }

}
