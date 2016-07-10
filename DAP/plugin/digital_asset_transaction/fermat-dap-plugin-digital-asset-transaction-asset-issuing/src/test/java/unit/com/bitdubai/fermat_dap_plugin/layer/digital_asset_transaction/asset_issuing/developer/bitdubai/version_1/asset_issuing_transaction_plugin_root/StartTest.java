package unit.com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.asset_issuing_transaction_plugin_root;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
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
import com.bitdubai.fermat_bch_api.layer.crypto_vault.currency_vault.CryptoVaultManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUser;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUserManager;

import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.AssetIssuingDigitalAssetTransactionPluginRoot;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.developer_utils.AssetIssuingTransactionDeveloperDatabaseFactory;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.database.AssetIssuingDatabaseConstants;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.database.AssetIssuingDatabaseFactory;
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
 * Created by frank on 22/10/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class StartTest {

    AssetIssuingDigitalAssetTransactionPluginRoot assetIssuingPluginRoot;
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
    ActorAssetIssuerManager actorAssetIssuerManager;

    @Mock
    CryptoVaultManager cryptoVaultManager;

    @Mock
    CryptoWalletManager cryptoWalletManager;

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
    AssetIssuingDatabaseFactory assetIssuingDatabaseFactory;

    @Mock
    ActorAssetIssuer actorAssetIssuer;

    @Mock
    DatabaseFactory mockDatabaseFactory;

    DatabaseTable mockDatabaseTable = Mockito.mock(DatabaseTable.class);
    DatabaseTableRecord mockDatabaseTableRecord = Mockito.mock(DatabaseTableRecord.class);
    Database mockDatabase = Mockito.mock(Database.class);

    @Before
    public void setUp() throws Exception {
        pluginId = UUID.randomUUID();

        assetIssuingPluginRoot = new AssetIssuingDigitalAssetTransactionPluginRoot();
        assetIssuingPluginRoot.setId(pluginId);
        assetIssuingPluginRoot.setErrorManager(errorManager);
        assetIssuingPluginRoot.setLogManager(logManager);
        assetIssuingPluginRoot.setEventManager(eventManager);
        assetIssuingPluginRoot.setPluginDatabaseSystem(pluginDatabaseSystem);
        assetIssuingPluginRoot.setPluginFileSystem(pluginFileSystem);
        assetIssuingPluginRoot.setAssetIssuerManager(assetIssuerWalletManager);
        assetIssuingPluginRoot.setActorAssetIssuerManager(actorAssetIssuerManager);
        assetIssuingPluginRoot.setCryptoVaultManager(cryptoVaultManager);
        assetIssuingPluginRoot.setBitcoinWalletManager(cryptoWalletManager);
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
        when(mockDatabase.getTable(AssetIssuingDatabaseConstants.ASSET_ISSUING_TABLE_NAME)).thenReturn(mockDatabaseTable);
        when(pluginDatabaseSystem.openDatabase(pluginId, AssetIssuingDatabaseConstants.ASSET_ISSUING_DATABASE)).thenReturn(mockDatabase);

//        when(mockExtraUserActorDatabaseFactory.createDatabase(pluginId, pluginId.toString())).thenReturn(mockDatabase);
        when(deviceUser.getPublicKey()).thenReturn("myPublicKey");
        when(deviceUserManager.getLoggedInDeviceUser()).thenReturn(deviceUser);
        when(eventManager.getNewListener(EventType.INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_ISSUER)).thenReturn(fermatEventListener1);
        when(eventManager.getNewListener(EventType.INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_ISSUER)).thenReturn(fermatEventListener2);
        when(eventManager.getNewListener(EventType.INCOMING_ASSET_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_ISSUER)).thenReturn(fermatEventListener3);
        when(eventManager.getNewListener(EventType.INCOMING_ASSET_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_ISSUER)).thenReturn(fermatEventListener4);
        when(eventManager.getNewListener(EventType.RECEIVED_NEW_DIGITAL_ASSET_METADATA_NOTIFICATION)).thenReturn(fermatEventListener5);

        when(actorAssetIssuerManager.getActorAssetIssuer()).thenReturn(actorAssetIssuer);
    }

    @Test
    public void testStart_OK() throws Exception {
        assetIssuingPluginRoot.start();
        ServiceStatus serviceStatus = assetIssuingPluginRoot.getStatus();
        assertThat(serviceStatus).isEqualTo(ServiceStatus.STARTED);
    }

    @Test
    public void test_Throws_DatabaseNotFoundException() throws Exception {
        assetIssuingPluginRoot.setAssetIssuerManager(null);
        catchException(assetIssuingPluginRoot).start();
        Exception thrown = caughtException();
        assertThat(thrown)
                .isNotNull()
                .isInstanceOf(CantStartPluginException.class);
        assertThat(thrown.getCause())
                .isNotNull()
                .isInstanceOf(CantSetObjectException.class);
        assetIssuingPluginRoot.setAssetIssuerManager(assetIssuerWalletManager);
    }

    //TODO develop tests of exceptions
    public void test_Throws_CantOpenDatabaseException() throws Exception {

    }

    @Test
    public void test_Throws_CantCreateDatabaseException() throws Exception {
        /*when(pluginDatabaseSystem.openDatabase(this.pluginId, AssetIssuingTransactionDatabaseConstants.ASSET_ISSUING_DATABASE)).thenThrow(new DatabaseNotFoundException("error"));
        when(assetIssuingTransactionDatabaseFactory.createDatabase(pluginId, AssetIssuingTransactionDatabaseConstants.ASSET_ISSUING_DATABASE)).thenThrow(new CantCreateDatabaseException("error"));
        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantStartPluginException.class);*/
    }

//    @Test
//    public void test_Throws_CantSetObjectException() throws Exception {
//        assetIssuingPluginRoot.setAssetIssuerManager(null);
//
//        catchException(assetIssuingPluginRoot).start();
//        Exception thrown = caughtException();
//        assertThat(thrown)
//                .isNotNull()
//                .isInstanceOf(CantStartPluginException.class);
//        assertThat(thrown.getCause()).isInstanceOf(CantSetObjectException.class);
//        assertThat(assetIssuingPluginRoot.getStatus()).isEqualTo(ServiceStatus.STOPPED);
//
//        assetIssuingPluginRoot.setAssetIssuerManager(assetIssuerWalletManager);
//    }

    @Test
    public void test_Throws_CantExecuteDatabaseOperationException() throws Exception {
        /*when(pluginDatabaseSystem.openDatabase(this.pluginId, AssetIssuingTransactionDatabaseConstants.ASSET_ISSUING_DATABASE)).thenThrow(new CantExecuteDatabaseOperationException(null, null, null));

        catchException(assetIssuingPluginRoot).start();
        Exception thrown = caughtException();
        assertThat(thrown)
                .isNotNull()
                .isInstanceOf(CantStartPluginException.class);
        assertThat(thrown.getCause()).isInstanceOf(CantExecuteDatabaseOperationException.class);
        assertThat(assetIssuingPluginRoot.getStatus()).isEqualTo(ServiceStatus.STOPPED);*/
    }

    @Test
    public void test_Throws_Exception() throws Exception {

    }
}
