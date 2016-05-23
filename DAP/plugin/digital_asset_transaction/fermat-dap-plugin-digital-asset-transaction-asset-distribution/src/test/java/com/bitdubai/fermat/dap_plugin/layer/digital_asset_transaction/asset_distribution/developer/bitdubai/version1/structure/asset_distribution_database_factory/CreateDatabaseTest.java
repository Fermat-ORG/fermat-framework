package com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.asset_distribution_database_factory;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;

import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.database.AssetDistributionDatabaseConstants;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.database.AssetDistributionDatabaseFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by Luis Campo (campusprize@gmail.com) on 29/10/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class CreateDatabaseTest {
    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;
    @Mock
    private DatabaseFactory mockDatabaseFactory;
    @Mock
    private DatabaseTableFactory mockTableFactory;
    @Mock
    private Database mockDatabase;

    private UUID testId;
    private String testDataBaseName;
    private AssetDistributionDatabaseFactory assetDistributionDatabaseFactory;

    public void setUpTestValues(){
        testId = UUID.randomUUID();
        testDataBaseName = AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_DATABASE;
    }

    public void setUpGeneralMockitoRules() throws Exception{

        when(mockPluginDatabaseSystem.createDatabase(testId, testDataBaseName)).thenReturn(mockDatabase);
        when(mockDatabase.getDatabaseFactory()).thenReturn(mockDatabaseFactory);

        when(mockDatabaseFactory.newTableFactory(any(UUID.class), anyString())).thenReturn(mockTableFactory);
    }

    @Before
    public void init() throws Exception{
        setUpTestValues();
        setUpGeneralMockitoRules();
    }

    @Test
    public void createDatabaseSuccessfulInvocationReturnsDatabase() throws Exception{
        System.out.println("Probando el metodo createDatabaseSuccessfulInvocationReturnsDatabase");
        assetDistributionDatabaseFactory = new AssetDistributionDatabaseFactory(mockPluginDatabaseSystem);
        Database checkDatabase = assetDistributionDatabaseFactory.createDatabase(testId, testDataBaseName);
        Assert.assertNotNull(checkDatabase);
    }

    /*@Test
    public void createDatabaseThrowsCantCreateDatabaseException() throws Exception{
        System.out.println("Probando el metodo createDatabaseThrowsCantCreateDatabaseException");
        assetDistributionDatabaseFactory = new AssetDistributionDatabaseFactory(mockPluginDatabaseSystem);
        catchException(assetDistributionDatabaseFactory).createDatabase(null, testDataBaseName);
        Exception thrown = caughtException();
        assertThat(thrown)
                .isNotNull()
                .isInstanceOf(CantCreateDatabaseException.class);
    }*/
}
