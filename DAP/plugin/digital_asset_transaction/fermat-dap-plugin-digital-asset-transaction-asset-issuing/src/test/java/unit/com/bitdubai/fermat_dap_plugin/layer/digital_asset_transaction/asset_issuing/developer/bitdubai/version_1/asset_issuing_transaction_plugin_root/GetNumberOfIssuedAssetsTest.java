package unit.com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.asset_issuing_transaction_plugin_root;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.bitcoin_vault.CryptoVaultManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletBalance;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletWallet;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUser;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUserManager;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContract;
import org.fermat.fermat_dap_api.layer.all_definition.enums.State;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;
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

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

/**
 * Created by frank on 23/10/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetNumberOfIssuedAssetsTest {
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
    PluginFileSystem pluginFileSystem;

    @Mock
    AssetIssuerWalletManager assetIssuerWalletManager;

    @Mock
    ActorAssetIssuerManager actorAssetIssuerManager;

    @Mock
    ActorAssetIssuer actorAssetIssuer;

    @Mock
    CryptoVaultManager cryptoVaultManager;

    @Mock
    BitcoinWalletManager bitcoinWalletManager;

    @Mock
    BitcoinWalletWallet bitcoinWalletWallet;

    @Mock
    BitcoinWalletBalance bitcoinWalletBalance;

    @Mock
    BitcoinNetworkManager bitcoinNetworkManager;

    @Mock
    AssetVaultManager assetVaultManager;

    @Mock
    CryptoAddressBookManager cryptoAddressBookManager;

    @Mock
    CryptoAddress cryptoAddress;

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
    DatabaseFactory mockDatabaseFactory;

    DatabaseTable mockDatabaseTable = Mockito.mock(DatabaseTable.class);
    DatabaseTable mockDatabaseTable2 = Mockito.mock(DatabaseTable.class);
    DatabaseTableRecord mockDatabaseTableRecord = Mockito.mock(DatabaseTableRecord.class);
    Database mockDatabase = Mockito.mock(Database.class);
    List<DatabaseTableRecord> records;

    DigitalAsset digitalAsset;

    @Mock
    DigitalAssetContract digitalAssetContract;

    @Mock
    IdentityAssetIssuer identityAssetIssuer;

    List<Resource> resources;
    String description = "description";
    String name = "name";
    String publicKey = "publicKey";
    State state = State.DRAFT;

    String walletPublicKey = "walletPublicKey";
    long bitcoinWalletAvailableBalance = 0;

    @Mock
    PluginTextFile pluginTextFile;

    BlockchainNetworkType blockchainNetworkType = BlockchainNetworkType.getDefaultBlockchainNetworkType();

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
        assetIssuingPluginRoot.setBitcoinWalletManager(bitcoinWalletManager);
        assetIssuingPluginRoot.setAssetVaultManager(assetVaultManager);
        assetIssuingPluginRoot.setCryptoAddressBookManager(cryptoAddressBookManager);
        assetIssuingPluginRoot.setOutgoingIntraActorManager(outgoingIntraActorManager);
        assetIssuingPluginRoot.setDeviceUserManager(deviceUserManager);
        assetIssuingPluginRoot.setBitcoinNetworkManager(bitcoinNetworkManager);

        resources = new LinkedList<>();

        digitalAsset = new DigitalAsset();
        digitalAsset.setContract(digitalAssetContract);
        digitalAsset.setResources(resources);
        digitalAsset.setDescription(description);
        digitalAsset.setName(name);
        digitalAsset.setPublicKey(publicKey);
        digitalAsset.setState(state);
        digitalAsset.setIdentityAssetIssuer(identityAssetIssuer);

        records = new LinkedList<>();
//        records.add(mockDatabaseTableRecord);

        setUpMockitoRules();
    }

    private void setUpMockitoRules() throws Exception {

        when(mockDatabase.getDatabaseFactory()).thenReturn(mockDatabaseFactory);
        when(mockDatabaseTable.getEmptyRecord()).thenReturn(mockDatabaseTableRecord);
        when(mockDatabase.getTable(AssetIssuingDatabaseConstants.ASSET_ISSUING_TABLE_NAME)).thenReturn(mockDatabaseTable);
        when(mockDatabase.getTable(AssetIssuingDatabaseConstants.ASSET_ISSUING_METADATA_TABLE)).thenReturn(mockDatabaseTable2);
        when(mockDatabaseTable2.getEmptyRecord()).thenReturn(mockDatabaseTableRecord);
        when(mockDatabaseTable2.getRecords()).thenReturn(records);
        when(pluginDatabaseSystem.openDatabase(pluginId, AssetIssuingDatabaseConstants.ASSET_ISSUING_DATABASE)).thenReturn(mockDatabase);

        when(deviceUser.getPublicKey()).thenReturn("myPublicKey");
        when(deviceUserManager.getLoggedInDeviceUser()).thenReturn(deviceUser);

        when(actorAssetIssuerManager.getActorAssetIssuer()).thenReturn(actorAssetIssuer);

        when(eventManager.getNewListener(EventType.INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_ISSUER)).thenReturn(fermatEventListener1);
        when(eventManager.getNewListener(EventType.INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_ISSUER)).thenReturn(fermatEventListener2);
        when(eventManager.getNewListener(EventType.INCOMING_ASSET_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_ISSUER)).thenReturn(fermatEventListener3);
        when(eventManager.getNewListener(EventType.INCOMING_ASSET_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_ISSUER)).thenReturn(fermatEventListener4);

        when(pluginFileSystem.createTextFile(this.pluginId, "digital-asset-issuing/publicKey", "name.xml", FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT)).thenReturn(pluginTextFile);
        when(bitcoinWalletManager.loadWallet(this.walletPublicKey)).thenReturn(bitcoinWalletWallet);
        when(bitcoinWalletWallet.getBalance(BalanceType.AVAILABLE)).thenReturn(bitcoinWalletBalance);
        when(bitcoinWalletBalance.getBalance()).thenReturn(bitcoinWalletAvailableBalance);
        when(assetVaultManager.getNewAssetVaultCryptoAddress(this.blockchainNetworkType)).thenReturn(cryptoAddress);
//        doNothing().when(assetIssuingPluginRoot).issueAssets(digitalAsset, 1, walletPublicKey, blockchainNetworkType);
    }

    //TODO uncomment and develop unit test
    @Test
    public void test_OK() throws Exception {
//        int assetsAmount = 1;
//        assetIssuingPluginRoot.start();
//        assetIssuingPluginRoot.issueAssets(digitalAsset, assetsAmount, walletPublicKey, blockchainNetworkType);
//        int issuedAssets = assetIssuingPluginRoot.getNumberOfIssuedAssets("assetPublicKey");
//        assertThat(issuedAssets).isEqualTo(assetsAmount);
    }
}
