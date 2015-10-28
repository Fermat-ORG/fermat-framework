package unit.com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.asset_issuing_transaction_plugin_root;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.AssetIssuingTransactionPluginRoot;

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
public class GetDatabaseTableListTest {
    AssetIssuingTransactionPluginRoot assetIssuingPluginRoot;
    UUID pluginId;

    @Mock
    DeveloperObjectFactory developerObjectFactory;

    @Before
    public void setUp() throws Exception {
        assetIssuingPluginRoot = new AssetIssuingTransactionPluginRoot();

        pluginId = UUID.randomUUID();
        assetIssuingPluginRoot.setId(pluginId);

        setUpMockitoRules();
    }

    private void setUpMockitoRules() throws Exception {
    }

    @Test
    public void test() {
        List<DeveloperDatabaseTable> list = assetIssuingPluginRoot.getDatabaseTableList(developerObjectFactory, null);

        assertThat(list).isNotNull();
        assertThat(list).isNotEmpty();
    }
}
