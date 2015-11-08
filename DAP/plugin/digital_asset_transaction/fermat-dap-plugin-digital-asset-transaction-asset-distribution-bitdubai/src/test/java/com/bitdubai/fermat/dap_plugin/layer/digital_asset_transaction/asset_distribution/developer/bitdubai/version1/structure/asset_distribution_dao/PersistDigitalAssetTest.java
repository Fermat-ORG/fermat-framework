package com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.asset_distribution_dao;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantPersistDigitalAssetException;
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

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Luis Campo (campusprize@gmail.com) on 02/10/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class PersistDigitalAssetTest {
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

    @Before
    public void init () throws Exception {

        pluginId = UUID.randomUUID();
        when(pluginDatabaseSystem.openDatabase(pluginId, AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_DATABASE)).thenReturn(database);
        mockAssetDistributionDao = new AssetDistributionDao(pluginDatabaseSystem, pluginId);
        setUpGeneralMockitoRules();
    }

    private void setUpGeneralMockitoRules() throws Exception{
        when(database.getTable(AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_TABLE_NAME)).thenReturn(databaseTable);
        when(databaseTable.getEmptyRecord()).thenReturn(databaseTableRecord);
    }

    @Test
    public void persistDigitalAssetTest () throws CantPersistDigitalAssetException {
        String genesisTransaction = "d21633ba23f70118185227be58a63527675641ad37967e2aa461559f577aec43";
        String localStoragePath = "localStoragePath";
        String digitalAssetHash = "d21633ba23f70118185227be5";
        String actorReceiverPublicKey = "ASDS-16988807";
        String actorReceiverBitcoinAddress = "ASDS-16988807";
        mockAssetDistributionDao.persistDigitalAsset(genesisTransaction, localStoragePath, digitalAssetHash, actorReceiverPublicKey, actorReceiverBitcoinAddress);
    }

    @Test
    public void persistDigitalAssetThrowsCantPersistDigitalAssetException() throws Exception {
        String genesisTransaction = "d21633ba23f70118185227be58a63527675641ad37967e2aa461559f577aec43";
        String localStoragePath = "localStoragePath";
        String digitalAssetHash = "d21633ba23f70118185227be5";
        String actorReceiverPublicKey = "ASDS-16988807";
        String actorReceiverBitcoinAddress = "ASDS-16988807";
        when(pluginDatabaseSystem.openDatabase(pluginId, AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_DATABASE)).thenThrow(new CantOpenDatabaseException("error"));
        catchException(mockAssetDistributionDao).persistDigitalAsset(genesisTransaction, localStoragePath, digitalAssetHash, actorReceiverPublicKey, actorReceiverBitcoinAddress);
        Exception thrown = caughtException();
        assertThat(thrown)
                .isNotNull()
                .isInstanceOf(CantPersistDigitalAssetException.class);
    }

}
