package unit.com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.developer_utils.asset_issuing_transaction_developer_database_factory;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;

import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.developer_utils.AssetIssuingTransactionDeveloperDatabaseFactory;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.database.AssetIssuingDatabaseConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by frank on 05/11/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetDatabaseListTest {
    AssetIssuingTransactionDeveloperDatabaseFactory assetIssuingTransactionDeveloperDatabaseFactory;
    UUID pluginId;
    List<DeveloperDatabase> developerDatabasesExpected;

    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;

    @Mock
    DeveloperObjectFactory developerObjectFactory;

    @Mock
    DeveloperDatabase developerDatabase;

    @Before
    public void setUp() throws Exception {
        pluginId = UUID.randomUUID();
        assetIssuingTransactionDeveloperDatabaseFactory = new AssetIssuingTransactionDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);

        developerDatabasesExpected = new LinkedList<>();
        developerDatabasesExpected.add(developerDatabase);

        mockitoRules();
    }

    private void mockitoRules() throws Exception {
        when(developerObjectFactory.getNewDeveloperDatabase(AssetIssuingDatabaseConstants.ASSET_ISSUING_DATABASE, this.pluginId.toString())).thenReturn(developerDatabase);
    }

    @Test
    public void test_OK() throws Exception {
        List<DeveloperDatabase> developerDatabases = assetIssuingTransactionDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory);
        assertThat(developerDatabases).isNotNull().isNotEmpty();
        assertThat(developerDatabases).isEqualTo(developerDatabasesExpected);
    }
}
