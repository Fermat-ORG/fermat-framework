package unit.com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.asset_issuing_transaction_manager;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.bitcoin_vault.CryptoVaultManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;

import org.fermat.fermat_dap_api.layer.all_definition.enums.IssuingStatus;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.exceptions.CantCheckAssetIssuingProgressException;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.database.AssetIssuingDAO;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.database.AssetIssuingDatabaseConstants;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.functional.AssetIssuingTransactionManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.support.membermodification.MemberModifier;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by frank on 03/11/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetIssuingStatusTest {
    AssetIssuingTransactionManager assetIssuingTransactionManager;
    UUID pluginId;

    @Mock
    CryptoVaultManager cryptoVaultManager;

    @Mock
    BitcoinWalletManager bitcoinWalletManager;

    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;

    @Mock
    PluginFileSystem pluginFileSystem;

    @Mock
    ErrorManager errorManager;

    @Mock
    AssetVaultManager assetVaultManager;

    @Mock
    CryptoAddressBookManager cryptoAddressBookManager;

    @Mock
    OutgoingIntraActorManager outgoingIntraActorManager;

    @Mock
    AssetIssuingDAO assetIssuingDAO;

    @Mock
    Database database;

    @Mock
    DatabaseTable databaseTable;

    @Mock
    DatabaseTableRecord databaseTableRecord;

    List<DatabaseTableRecord> records;
    List<DatabaseTableRecord> recordsForException;

    IssuingStatus issuingStatusExpected = IssuingStatus.ISSUING;

    String assetPublicKey = "assetPublicKey";

    @Before
    public void setUp() throws Exception {
        pluginId = UUID.randomUUID();
        assetIssuingTransactionManager = new AssetIssuingTransactionManager(pluginId,
                cryptoVaultManager,
                bitcoinWalletManager,
                pluginDatabaseSystem,
                pluginFileSystem,
                errorManager,
                assetVaultManager,
                cryptoAddressBookManager,
                outgoingIntraActorManager);
        assetIssuingTransactionManager.setPluginId(pluginId);
        assetIssuingTransactionManager.setAssetIssuingTransactionDao(assetIssuingDAO);

        MemberModifier.field(AssetIssuingDAO.class, "database").set(assetIssuingDAO, database);

        records = new LinkedList<>();
        records.add(databaseTableRecord);

        recordsForException = new LinkedList<>();
        recordsForException.add(databaseTableRecord);
        recordsForException.add(databaseTableRecord);

        setUpMockitoRules();
    }

    private void setUpMockitoRules() throws Exception {
        when(pluginDatabaseSystem.openDatabase(pluginId, AssetIssuingDatabaseConstants.ASSET_ISSUING_DATABASE)).thenReturn(database);
        when(database.getTable(AssetIssuingDatabaseConstants.ASSET_ISSUING_TABLE_NAME)).thenReturn(databaseTable);
        when(databaseTable.getRecords()).thenReturn(records);
        when(databaseTableRecord.getStringValue(AssetIssuingDatabaseConstants.ASSET_ISSUING_ISSUING_STATUS_COLUMN_NAME)).thenReturn(issuingStatusExpected.getCode());
        when(assetIssuingDAO.getIssuingStatusByPublicKey(assetPublicKey)).thenCallRealMethod();
    }

    @Test
    public void test_OK() throws Exception {
        IssuingStatus issuingStatus = assetIssuingTransactionManager.getIssuingStatus(assetPublicKey);
        assertThat(issuingStatus).isEqualTo(issuingStatusExpected);
    }

    @Test
    public void test_Throws_CantExecuteDatabaseOperationException() throws Exception {
        when(databaseTable.getRecords()).thenReturn(recordsForException);

        catchException(assetIssuingTransactionManager).getIssuingStatus(assetPublicKey);
        Exception thrown = caughtException();
        assertThat(thrown)
                .isNotNull()
                .isInstanceOf(CantExecuteDatabaseOperationException.class);
        assertThat(thrown.getCause()).isInstanceOf(CantCheckAssetIssuingProgressException.class);
    }
}
