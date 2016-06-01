package unit.com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.asset_issuing_transaction_dao;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;

import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantPersistDigitalAssetException;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.database.AssetIssuingDAO;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.database.AssetIssuingDatabaseConstants;
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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * Created by frank on 31/10/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class PersistDigitalAssetTest {
    AssetIssuingDAO assetIssuingDAO;
    UUID pluginId;

    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;

    @Mock
    Database database;

    @Mock
    DatabaseTable databaseTable;

    @Mock
    DatabaseTableRecord databaseTableRecord;

    String digitalAssetPublicKey = "digitalAssetPublicKey";
    String digitalAssetLocalStoragePath = "digitalAssetLocalStoragePath";
    int assetsAmount = 1;
    BlockchainNetworkType blockchainNetworkType = BlockchainNetworkType.getDefaultBlockchainNetworkType();
    String walletPublickey = "walletPublickey";

    List<DatabaseTableRecord> records;

    @Before
    public void setUp() throws Exception {
        pluginId = UUID.randomUUID();

        when(pluginDatabaseSystem.openDatabase(pluginId, AssetIssuingDatabaseConstants.ASSET_ISSUING_DATABASE)).thenReturn(database);
        assetIssuingDAO = new AssetIssuingDAO(pluginDatabaseSystem, pluginId);

        records = new LinkedList<>();
        records.add(databaseTableRecord);

        mockitoRules();
    }

    private void mockitoRules() throws Exception {
        when(database.getTable(AssetIssuingDatabaseConstants.ASSET_ISSUING_TABLE_NAME)).thenReturn(databaseTable);
        when(databaseTable.getEmptyRecord()).thenReturn(databaseTableRecord);
    }

    @Test
    public void test_OK() throws Exception {
        assetIssuingDAO.persistDigitalAsset(digitalAssetPublicKey, digitalAssetLocalStoragePath, assetsAmount, blockchainNetworkType, walletPublickey);
    }

    @Test
    public void test_Throws_CantPersistDigitalAssetException() throws Exception {
        doThrow(new CantInsertRecordException("error")).when(databaseTable).insertRecord(databaseTableRecord);
        catchException(assetIssuingDAO).persistDigitalAsset(digitalAssetPublicKey, digitalAssetLocalStoragePath, assetsAmount, blockchainNetworkType, walletPublickey);
        Exception thrown = caughtException();
        assertThat(thrown)
                .isNotNull()
                .isInstanceOf(CantPersistDigitalAssetException.class);
        assertThat(thrown.getCause()).isInstanceOf(CantInsertRecordException.class);
    }
}
