package com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.asset_distribution_plugin_root;

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
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import org.fermat.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.AssetDistributionDigitalAssetTransactionPluginRoot;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.database.AssetDistributionDatabaseConstants;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUser;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUserManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.Mockito.when;

/**
 * Created by Luis Campo (campusprize@gmail.com) on 29/10/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class StartTest {
    private AssetDistributionDigitalAssetTransactionPluginRoot assetDistributionDigitalAssetTransactionPluginRoot;
    private UUID pluginId;

    @Mock
    private ErrorManager errorManager;

    @Mock
    private  LogManager logManager;

    @Mock
    private EventManager eventManager;

    @Mock
    private FermatEventListener fermatEventListener1;
    @Mock
    private  FermatEventListener fermatEventListener2;
    @Mock
    private FermatEventListener fermatEventListener3;
    @Mock
    private  FermatEventListener fermatEventListener4;
    @Mock
    private  FermatEventListener fermatEventListener5;

    @Mock
    private PluginFileSystem pluginFileSystem;

    @Mock
    private AssetIssuerWalletManager assetIssuerWalletManager;

    @Mock
    private AssetTransmissionNetworkServiceManager assetTransmissionNetworkServiceManager;

    @Mock
    private ActorAssetIssuerManager actorAssetIssuerManager;

    @Mock
    private BitcoinNetworkManager bitcoinNetworkManager;

    @Mock
    private AssetVaultManager assetVaultManager;

    @Mock
    private DeviceUserManager deviceUserManager;

    @Mock
    private DeviceUser deviceUser;

    @Mock
    private PluginDatabaseSystem pluginDatabaseSystem;

    @Mock
    private DatabaseFactory mockDatabaseFactory;

    private DatabaseTable mockDatabaseTable = Mockito.mock(DatabaseTable.class);
    private DatabaseTableRecord mockDatabaseTableRecord = Mockito.mock(DatabaseTableRecord.class);
    private Database mockDatabase = Mockito.mock(Database.class);

    @Before
    public void setUp() throws Exception {

        pluginId = UUID.randomUUID();

        assetDistributionDigitalAssetTransactionPluginRoot = new AssetDistributionDigitalAssetTransactionPluginRoot();
        assetDistributionDigitalAssetTransactionPluginRoot.setId(pluginId);
        /*assetDistributionDigitalAssetTransactionPluginRoot.setErrorManager(errorManager);
        assetDistributionDigitalAssetTransactionPluginRoot.setLogManager(logManager);
        assetDistributionDigitalAssetTransactionPluginRoot.setEventManager(eventManager);
        assetDistributionDigitalAssetTransactionPluginRoot.setPluginDatabaseSystem(pluginDatabaseSystem);
        assetDistributionDigitalAssetTransactionPluginRoot.setPluginFileSystem(pluginFileSystem);
        assetDistributionDigitalAssetTransactionPluginRoot.setAssetIssuerManager(assetIssuerWalletManager);
        assetDistributionDigitalAssetTransactionPluginRoot.setAssetVaultManager(assetVaultManager);
        assetDistributionDigitalAssetTransactionPluginRoot.setDeviceUserManager(deviceUserManager);
        assetDistributionDigitalAssetTransactionPluginRoot.setBitcoinNetworkManager(bitcoinNetworkManager);
        assetDistributionDigitalAssetTransactionPluginRoot.setAssetTransmissionNetworkServiceManager(assetTransmissionNetworkServiceManager);
        assetDistributionDigitalAssetTransactionPluginRoot.setActorAssetIssuerManager(actorAssetIssuerManager);*/

        setUpMockitoRules();
    }

    private void setUpMockitoRules() throws Exception {
        when(mockDatabase.getDatabaseFactory()).thenReturn(mockDatabaseFactory);
        when(mockDatabaseTable.getEmptyRecord()).thenReturn(mockDatabaseTableRecord);
        when(mockDatabase.getTable(AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_TABLE_NAME)).thenReturn(mockDatabaseTable);
        when(pluginDatabaseSystem.openDatabase(pluginId, AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_DATABASE)).thenReturn(mockDatabase);
        when(deviceUser.getPublicKey()).thenReturn("myPublicKey");
        when(deviceUserManager.getLoggedInDeviceUser()).thenReturn(deviceUser);
        when(eventManager.getNewListener(EventType.INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_USER)).thenReturn(fermatEventListener1);
        when(eventManager.getNewListener( EventType.INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_USER)).thenReturn(fermatEventListener2);
        when(eventManager.getNewListener(EventType.INCOMING_ASSET_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_USER)).thenReturn(fermatEventListener3);
        when(eventManager.getNewListener(EventType.INCOMING_ASSET_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_USER)).thenReturn(fermatEventListener4);
        when(eventManager.getNewListener(EventType.RECEIVED_NEW_DIGITAL_ASSET_METADATA_NOTIFICATION)).thenReturn(fermatEventListener5);
    }

    @Test
    public void testStartOK() throws Exception {
        System.out.println("Test AssetDistributionDigitalAssetTransactionPluginRoot.start()");
        assetDistributionDigitalAssetTransactionPluginRoot.start();
        ServiceStatus serviceStatus = assetDistributionDigitalAssetTransactionPluginRoot.getStatus();
        Assert.assertEquals(ServiceStatus.STARTED, serviceStatus);
    }

   /*@Test
    public void testStartThrowsCantStartPluginException() throws Exception {
        System.out.println("Test AssetDistributionDigitalAssetTransactionPluginRoot.start() ThrowsCantStartPluginException");
        assetDistributionDigitalAssetTransactionPluginRoot.setActorAssetIssuerManager(null);

       try {
           assetDistributionDigitalAssetTransactionPluginRoot.start();
           fail("The method didn't throw when I expected it to");
       }catch (Exception ex) {
           Assert.assertTrue(ex instanceof CantStartPluginException);
       }
    }*/
}
