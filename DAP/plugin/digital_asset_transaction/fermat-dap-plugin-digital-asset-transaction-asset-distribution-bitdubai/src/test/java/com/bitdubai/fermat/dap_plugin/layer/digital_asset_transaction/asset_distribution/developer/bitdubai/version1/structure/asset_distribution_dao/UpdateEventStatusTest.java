package com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.asset_distribution_dao;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.UnexpectedResultReturnedFromDatabaseException;
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
public class UpdateEventStatusTest {
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
        when(database.getTable(AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_EVENTS_RECORDED_TABLE_NAME)).thenReturn(databaseTable);
        when(databaseTable.getRecords()).thenReturn(records);
    }

    @Test
    public void updateEventStatusTest () throws CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException {
        mockAssetDistributionDao.updateEventStatus("eventId");
    }

   /* @Test
    public void updateEventStatusThrowsCantExecuteQueryException() throws Exception {
        when(pluginDatabaseSystem.openDatabase(pluginId, AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_DATABASE)).thenThrow(new CantOpenDatabaseException("error"));
        catchException(mockAssetDistributionDao).updateEventStatus("eventId");
        Exception thrown = caughtException();
        assertThat(thrown)
                .isNotNull()
                .isInstanceOf(CantExecuteQueryException.class);
    }

    @Test
    public void updateEventStatusThrowsUnexpectedResultReturnedFromDatabaseException() throws Exception {
        when(databaseTable.getRecords()).thenReturn(recordsForException);
        catchException(mockAssetDistributionDao).updateEventStatus("eventId");
        Exception thrown = caughtException();
        assertThat(thrown)
                .isNotNull()
                .isInstanceOf(CantExecuteQueryException.class);
        assertThat(thrown.getCause()).isNotNull().isInstanceOf(UnexpectedResultReturnedFromDatabaseException.class);
    }*/
}
