package unit.com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.asset_issuing_transaction_database_factory;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;

import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.database.AssetIssuingDatabaseConstants;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.database.AssetIssuingDatabaseFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by frank on 05/11/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class CreateDatabaseTest {
    AssetIssuingDatabaseFactory assetIssuingDatabaseFactory;
    UUID ownerId;
    String databaseName = "test";

    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;

    @Mock
    Database database;

    @Mock
    DatabaseFactory databaseFactory;

    @Mock
    DatabaseTableFactory databaseTableFactory;

    @Mock
    DatabaseTableFactory eventsRecorderTable;

    @Mock
    DatabaseTableFactory assetIssuingTable;

    @Before
    public void setUp() throws Exception {
        ownerId = UUID.randomUUID();
        assetIssuingDatabaseFactory = new AssetIssuingDatabaseFactory(pluginDatabaseSystem);

        mockitoRules();
    }

    private void mockitoRules() throws Exception {
        when(pluginDatabaseSystem.createDatabase(ownerId, databaseName)).thenReturn(database);
        when(database.getDatabaseFactory()).thenReturn(databaseFactory);
        when(databaseFactory.newTableFactory(ownerId, AssetIssuingDatabaseConstants.ASSET_ISSUING_TABLE_NAME)).thenReturn(databaseTableFactory);
        when(databaseFactory.newTableFactory(ownerId, AssetIssuingDatabaseConstants.ASSET_ISSUING_EVENTS_RECORDED_TABLE_NAME)).thenReturn(eventsRecorderTable);
        when(databaseFactory.newTableFactory(ownerId, AssetIssuingDatabaseConstants.ASSET_ISSUING_METADATA_TABLE)).thenReturn(assetIssuingTable);
    }

    @Test
    public void test_OK() throws Exception {
        assetIssuingDatabaseFactory.createDatabase(ownerId, databaseName);
    }

    @Test
    public void test_Throws_CantCreateDatabaseException() throws Exception {
        when(pluginDatabaseSystem.createDatabase(ownerId, databaseName)).thenThrow(new CantCreateDatabaseException());

        catchException(assetIssuingDatabaseFactory).createDatabase(ownerId, databaseName);
        Exception thrown = caughtException();
        assertThat(thrown)
                .isNotNull()
                .isInstanceOf(CantCreateDatabaseException.class);
    }
}
