package unit.com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.asset_issuing_transaction_plugin_root;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_ccp_api.layer.transaction.outgoing_intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.AssetIssuingTransactionPluginRoot;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.developer_utils.AssetIssuingTransactionDeveloperDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantCheckAssetIssuingProgressException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.AssetIssuingTransactionDatabaseConstants;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.AssetIssuingTransactionDatabaseFactory;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUser;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUserManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by frank on 23/10/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetNumberOfIssuedAssetsTest {
    AssetIssuingTransactionPluginRoot assetIssuingPluginRoot;
    UUID pluginId;

    @Mock
    ErrorManager errorManager;

    @Mock
    LogManager logManager;

    @Mock
    EventManager eventManager;

    @Mock
    FermatEventListener fermatEventListener1;
    @Mock
    FermatEventListener fermatEventListener2;
    @Mock
    FermatEventListener fermatEventListener3;
    @Mock
    FermatEventListener fermatEventListener4;
    @Mock
    FermatEventListener fermatEventListener5;

    @Mock
    PluginFileSystem pluginFileSystem;

    @Mock
    AssetIssuerWalletManager assetIssuerWalletManager;

    @Mock
    CryptoVaultManager cryptoVaultManager;

    @Mock
    BitcoinWalletManager bitcoinWalletManager;

    @Mock
    BitcoinNetworkManager bitcoinNetworkManager;

    @Mock
    AssetVaultManager assetVaultManager;

    @Mock
    CryptoAddressBookManager cryptoAddressBookManager;

    @Mock
    OutgoingIntraActorManager outgoingIntraActorManager;

    @Mock
    DeviceUserManager deviceUserManager;

    @Mock
    DeviceUser deviceUser;

    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;

    @Mock
    AssetIssuingTransactionDeveloperDatabaseFactory assetIssuingTransactionDeveloperDatabaseFactory;
    @Mock
    AssetIssuingTransactionDatabaseFactory assetIssuingTransactionDatabaseFactory;

    @Mock
    DatabaseFactory mockDatabaseFactory;

    DatabaseTable mockDatabaseTable = Mockito.mock(DatabaseTable.class);
    DatabaseTableRecord mockDatabaseTableRecord = Mockito.mock(DatabaseTableRecord.class);
    Database mockDatabase = Mockito.mock(Database.class);

    @Mock
    DigitalAsset digitalAsset;

    BlockchainNetworkType blockchainNetworkType = BlockchainNetworkType.REG_TEST;

    @Before
    public void setUp() throws Exception {
        assetIssuingPluginRoot = new AssetIssuingTransactionPluginRoot();

        pluginId = UUID.randomUUID();

        assetIssuingPluginRoot = new AssetIssuingTransactionPluginRoot();
        assetIssuingPluginRoot.setId(pluginId);
        assetIssuingPluginRoot.setErrorManager(errorManager);
        assetIssuingPluginRoot.setLogManager(logManager);
        assetIssuingPluginRoot.setEventManager(eventManager);
        assetIssuingPluginRoot.setPluginDatabaseSystem(pluginDatabaseSystem);
        assetIssuingPluginRoot.setPluginFileSystem(pluginFileSystem);
        assetIssuingPluginRoot.setAssetIssuerManager(assetIssuerWalletManager);
        assetIssuingPluginRoot.setCryptoVaultManager(cryptoVaultManager);
        assetIssuingPluginRoot.setBitcoinWalletManager(bitcoinWalletManager);
        assetIssuingPluginRoot.setAssetVaultManager(assetVaultManager);
        assetIssuingPluginRoot.setCryptoAddressBookManager(cryptoAddressBookManager);
        assetIssuingPluginRoot.setOutgoingIntraActorManager(outgoingIntraActorManager);
        assetIssuingPluginRoot.setDeviceUserManager(deviceUserManager);
        assetIssuingPluginRoot.setBitcoinNetworkManager(bitcoinNetworkManager);

        setUpMockitoRules();
    }

    private void setUpMockitoRules() throws Exception {

        when(mockDatabase.getDatabaseFactory()).thenReturn(mockDatabaseFactory);
        when(mockDatabaseTable.getEmptyRecord()).thenReturn(mockDatabaseTableRecord);
        when(mockDatabase.getTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_TABLE_NAME)).thenReturn(mockDatabaseTable);
        when(pluginDatabaseSystem.openDatabase(pluginId, AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DATABASE)).thenReturn(mockDatabase);

//        when(mockExtraUserActorDatabaseFactory.createDatabase(pluginId, pluginId.toString())).thenReturn(mockDatabase);
        when(deviceUser.getPublicKey()).thenReturn("myPublicKey");
        when(deviceUserManager.getLoggedInDeviceUser()).thenReturn(deviceUser);

        //TODO fix: call getNewListener
//        when(eventManager.getNewListener(EventType.INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_ISSUER)).thenReturn(fermatEventListener1);
//        when(eventManager.getNewListener(EventType.INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_ISSUER)).thenReturn(fermatEventListener2);
//        when(eventManager.getNewListener(EventType.INCOMING_ASSET_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_ISSUER)).thenReturn(fermatEventListener3);
//        when(eventManager.getNewListener(EventType.INCOMING_ASSET_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_ISSUER)).thenReturn(fermatEventListener4);
//        when(eventManager.getNewListener(EventType.RECEIVED_NEW_DIGITAL_ASSET_METADATA_NOTIFICATION)).thenReturn(fermatEventListener5);
    }

    @Test
    public void testStart_OK() throws Exception {
        //TODO cannot run issueAssets method
//        assetIssuingPluginRoot.start();
//        assetIssuingPluginRoot.issueAssets(digitalAsset, 1, "walletPublicKey", blockchainNetworkType);
//        int n = assetIssuingPluginRoot.getNumberOfIssuedAssets("assetPublicKey");
//        assertThat(n).isGreaterThan(0);
    }

    @Test
    public void test_Throws_CantExecuteDatabaseOperationException() throws Exception {
        assetIssuingPluginRoot.start();
        catchException(assetIssuingPluginRoot).getNumberOfIssuedAssets(null);
        Exception thrown = caughtException();
        assertThat(thrown)
                .isNotNull()
                .isInstanceOf(CantExecuteDatabaseOperationException.class);
        assertThat(thrown.getCause()).isInstanceOf(CantCheckAssetIssuingProgressException.class);
    }
}
