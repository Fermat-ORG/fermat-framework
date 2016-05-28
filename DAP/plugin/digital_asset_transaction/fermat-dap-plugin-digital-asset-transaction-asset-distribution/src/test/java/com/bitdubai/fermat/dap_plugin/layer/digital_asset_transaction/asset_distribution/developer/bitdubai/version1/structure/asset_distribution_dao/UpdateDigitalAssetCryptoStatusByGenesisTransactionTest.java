package com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.asset_distribution_dao;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;

import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.UnexpectedResultReturnedFromDatabaseException;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.exceptions.CantCheckAssetDistributionProgressException;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.database.AssetDistributionDao;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.database.AssetDistributionDatabaseConstants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

/**
 * Created by Luis Campo (campusprize@gmail.com) on 03/10/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class UpdateDigitalAssetCryptoStatusByGenesisTransactionTest {
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
    public void updateDigitalAssetCryptoStatusByGenesisTransactionTest () throws CantCheckAssetDistributionProgressException, UnexpectedResultReturnedFromDatabaseException {
        String genesisTransaction = "d21633ba23f70118185227be58a63527675641ad37967e2aa461559f577aec43";
        mockAssetDistributionDao.updateDigitalAssetCryptoStatusByGenesisTransaction(genesisTransaction, CryptoStatus.PENDING_SUBMIT);
    }

    @Test
    public void updateDigitalAssetCryptoStatusByGenesisTransactionThrowsCantPersistsTransactionUUIDException() throws Exception {
        String genesisTransaction = "d21633ba23f70118185227be58a63527675641ad37967e2aa461559f577aec43";
        when(pluginDatabaseSystem.openDatabase(pluginId, AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_DATABASE)).thenThrow(new CantOpenDatabaseException("error"));
        try {
            mockAssetDistributionDao.updateDigitalAssetCryptoStatusByGenesisTransaction(genesisTransaction, CryptoStatus.PENDING_SUBMIT);
            fail("The method didn't throw when I expected it to");
        }catch (Exception ex) {
            Assert.assertTrue(ex instanceof CantCheckAssetDistributionProgressException);
        }
    }

    @Test
    public void updateDigitalAssetCryptoStatusByGenesisTransactionThrowsUnexpectedResultReturnedFromDatabaseException() throws Exception {
        String genesisTransaction = "d21633ba23f70118185227be58a63527675641ad37967e2aa461559f577aec43";

        when(databaseTable.getRecords()).thenReturn(recordsForException);
        try {
            mockAssetDistributionDao.updateDigitalAssetCryptoStatusByGenesisTransaction(genesisTransaction, CryptoStatus.PENDING_SUBMIT);
            fail("The method didn't throw when I expected it to");
        }catch (Exception ex) {
            Assert.assertTrue(ex instanceof CantCheckAssetDistributionProgressException);
            Assert.assertTrue(ex.getCause() instanceof UnexpectedResultReturnedFromDatabaseException);
        }
    }
}
