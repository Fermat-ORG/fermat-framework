package com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.asset_distribution_dao;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantPersistsTransactionUUIDException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.database.AssetDistributionDao;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.database.AssetDistributionDatabaseConstants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

/**
 * Created by Luis Campo (campusprize@gmail.com) on 03/10/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class PersistDistributionIdTest {
    @Mock
    Database database;
    @Mock
    DatabaseTable databaseTable;
    @Mock
    DatabaseTableRecord databaseTableRecord;
    UUID pluginId;
    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;
    private AssetDistributionDao mockAssetDistributionDao;
    List<DatabaseTableRecord> records, recordsForException;

    @Before
    public void init () throws Exception {

        pluginId = UUID.randomUUID();
        when(pluginDatabaseSystem.openDatabase(pluginId, AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_DATABASE)).thenReturn(database);
        mockAssetDistributionDao = new AssetDistributionDao(pluginDatabaseSystem, pluginId);
        records = new LinkedList<>();
        records.add(databaseTableRecord);
        recordsForException = new LinkedList<>();
        recordsForException.add(databaseTableRecord);
        recordsForException.add(databaseTableRecord);
        setUpGeneralMockitoRules();
    }

    private void setUpGeneralMockitoRules() throws Exception{
        when(database.getTable(AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_TABLE_NAME)).thenReturn(databaseTable);
        when(databaseTable.getRecords()).thenReturn(records);
    }

    @Test
    public void persistDistributionIdTest () throws CantPersistsTransactionUUIDException {
        String genesisTransaction = "d21633ba23f70118185227be58a63527675641ad37967e2aa461559f577aec43";
        UUID distributionId = UUID.randomUUID();
        mockAssetDistributionDao.persistDistributionId(genesisTransaction, distributionId);
    }

    /*@Test
    public void persistDistributionIdThrowsCantPersistsTransactionUUIDException() throws Exception {
        String genesisTransaction = "d21633ba23f70118185227be58a63527675641ad37967e2aa461559f577aec43";
        UUID distributionId = UUID.randomUUID();

        when(pluginDatabaseSystem.openDatabase(pluginId, AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_DATABASE)).thenThrow(new CantOpenDatabaseException("error"));
        catchException(mockAssetDistributionDao).persistDistributionId(genesisTransaction, distributionId);
        Exception thrown = caughtException();
        assertThat(thrown)
                .isNotNull()
                .isInstanceOf(CantPersistsTransactionUUIDException.class);
    }

    @Test
    public void persistDistributionIdThrowsUnexpectedResultReturnedFromDatabaseException() throws Exception {
        String genesisTransaction = "d21633ba23f70118185227be58a63527675641ad37967e2aa461559f577aec43";
        UUID distributionId = UUID.randomUUID();

        when(databaseTable.getRecords()).thenReturn(recordsForException);
        catchException(mockAssetDistributionDao).persistDistributionId(genesisTransaction, distributionId);
        Exception thrown = caughtException();
        assertThat(thrown)
                .isNotNull()
                .isInstanceOf(CantPersistsTransactionUUIDException.class);
        assertThat(thrown.getCause()).isNotNull().isInstanceOf(UnexpectedResultReturnedFromDatabaseException.class);
    }*/
}
