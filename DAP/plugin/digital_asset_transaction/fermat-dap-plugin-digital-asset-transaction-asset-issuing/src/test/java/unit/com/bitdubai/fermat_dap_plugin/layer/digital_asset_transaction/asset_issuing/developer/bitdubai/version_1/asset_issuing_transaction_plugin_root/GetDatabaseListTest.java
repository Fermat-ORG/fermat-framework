package unit.com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.asset_issuing_transaction_plugin_root;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;

import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.AssetIssuingDigitalAssetTransactionPluginRoot;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by frank on 23/10/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetDatabaseListTest {
    AssetIssuingDigitalAssetTransactionPluginRoot assetIssuingPluginRoot;
    UUID pluginId;

    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;

    @Mock
    DeveloperObjectFactory developerObjectFactory;

    @Before
    public void setUp() throws Exception {
        assetIssuingPluginRoot = new AssetIssuingDigitalAssetTransactionPluginRoot();

        pluginId = UUID.randomUUID();

        assetIssuingPluginRoot = new AssetIssuingDigitalAssetTransactionPluginRoot();
        assetIssuingPluginRoot.setId(pluginId);
        assetIssuingPluginRoot.setPluginDatabaseSystem(pluginDatabaseSystem);

        setUpMockitoRules();
    }

    private void setUpMockitoRules() throws Exception {
    }

    @Test
    public void test() {
        List<DeveloperDatabase> list = assetIssuingPluginRoot.getDatabaseList(developerObjectFactory);

        assertThat(list).isNotNull();
        assertThat(list).isNotEmpty();
    }
}
