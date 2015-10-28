package unit.com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.asset_issuing_transaction_plugin_root;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.AssetIssuingTransactionPluginRoot;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.developer_utils.AssetIssuingTransactionDeveloperDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.AssetIssuingTransactionDatabaseConstants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by frank on 23/10/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetDatabaseTableContent {
    AssetIssuingTransactionPluginRoot assetIssuingPluginRoot;
    UUID pluginId;

    @Mock
    DeveloperObjectFactory developerObjectFactory;
    @Mock
    DeveloperDatabase developerDatabase;
    @Mock
    DeveloperDatabaseTable developerDatabaseTable;
    @Mock
    Database database;
    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;
    @Mock
    AssetIssuingTransactionDeveloperDatabaseFactory assetIssuingTransactionDeveloperDatabaseFactory;

    @Before
    public void setUp() throws Exception {
        assetIssuingPluginRoot = new AssetIssuingTransactionPluginRoot();
        assetIssuingPluginRoot.setPluginDatabaseSystem(pluginDatabaseSystem);

        pluginId = UUID.randomUUID();
        assetIssuingPluginRoot.setId(pluginId);

        setUpMockitoRules();
    }

    private void setUpMockitoRules() throws Exception {

    }

    @Test
    public void test_OK() throws Exception {
        when(pluginDatabaseSystem.openDatabase(pluginId, AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DATABASE)).thenReturn(database);
        List<DeveloperDatabaseTableRecord> list = assetIssuingPluginRoot.getDatabaseTableContent(developerObjectFactory, developerDatabase, developerDatabaseTable);

        assertThat(list).isNotNull();
    }

    public void test_Throws_CantOpenDatabaseException() throws Exception {
        when(pluginDatabaseSystem.openDatabase(pluginId, AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DATABASE)).thenThrow(new CantOpenDatabaseException("error"));

        List<DeveloperDatabaseTableRecord> list = assetIssuingPluginRoot.getDatabaseTableContent(developerObjectFactory, developerDatabase, developerDatabaseTable);
        assertThat(list).isNotNull();
        assertThat(list.size()).equals(0);
    }

    public void test_Throws_DatabaseNotFoundException() throws Exception {
        when(pluginDatabaseSystem.openDatabase(pluginId, AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DATABASE)).thenThrow(new DatabaseNotFoundException("error"));

        List<DeveloperDatabaseTableRecord> list = assetIssuingPluginRoot.getDatabaseTableContent(developerObjectFactory, developerDatabase, developerDatabaseTable);
        assertThat(list).isNotNull();
        assertThat(list.size()).equals(0);
    }
}
