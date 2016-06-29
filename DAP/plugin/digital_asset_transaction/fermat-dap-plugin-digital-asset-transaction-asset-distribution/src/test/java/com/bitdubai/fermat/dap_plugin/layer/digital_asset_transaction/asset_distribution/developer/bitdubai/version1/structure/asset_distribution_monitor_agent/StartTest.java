package com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.asset_distribution_monitor_agent;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;

import org.fermat.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.database.AssetDistributionDatabaseConstants;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.database.AssetDistributionDatabaseFactory;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.events.AssetDistributionMonitorAgent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.Mockito.when;

/**
 * Created by Luis Campo (campusprize@gmail.com)on 30/10/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class StartTest {
    @Mock
    private Database database;
    @Mock
    private DatabaseTableFactory table;
    @Mock
    private DatabaseFactory databaseFactory;
    @Mock
    private DatabaseTableFactory eventsRecorderTable;
    @Mock
    private DatabaseTableFactory assetDeliveringTable;
    private String userPublicKey;
    @Mock
    private LogManager logManager;
    @Mock
    private EventManager eventManager;
    @Mock
    private ErrorManager errorManager;
    @Mock
    private PluginDatabaseSystem pluginDatabaseSystem;
    UUID pluginId;
    @Mock
    private AssetVaultManager assetVaultManager;
    @Mock
    private AssetTransmissionNetworkServiceManager assetTransmissionManager;
    @Mock
    private BitcoinNetworkManager bitcoinNetworkManager;

    AssetDistributionDatabaseFactory assetIssuingTransactionDatabaseFactory = Mockito.mock(AssetDistributionDatabaseFactory.class);
    private AssetDistributionMonitorAgent assetDistributionMonitorAgent;

    @Before
    public void init() throws Exception {
        userPublicKey = new ECCKeyPair().getPublicKey();
        pluginId = UUID.randomUUID();
        assetDistributionMonitorAgent = new AssetDistributionMonitorAgent(eventManager, pluginDatabaseSystem, errorManager, pluginId, userPublicKey, assetVaultManager);
        setUpGeneralMockitoRules();
    }

    private void setUpGeneralMockitoRules() throws Exception {
        when(pluginDatabaseSystem.createDatabase(pluginId, userPublicKey)).thenReturn(database);
        when(database.getDatabaseFactory()).thenReturn(databaseFactory);
        when(databaseFactory.newTableFactory(pluginId, AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_TABLE_NAME)).thenReturn(table);
        when(databaseFactory.newTableFactory(pluginId, AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_EVENTS_RECORDED_TABLE_NAME)).thenReturn(eventsRecorderTable);
        when(databaseFactory.newTableFactory(pluginId, AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_DELIVERING_TABLE_NAME)).thenReturn(assetDeliveringTable);
    }

    @Test
    public void startSuccesTest() throws CantStartAgentException, InterruptedException {
        assetDistributionMonitorAgent.start();
    }

    @Test
    public void startThrowsDatabaseNotFoundExceptionTest() throws CantStartAgentException, InterruptedException, CantOpenDatabaseException, DatabaseNotFoundException {
        when(pluginDatabaseSystem.openDatabase(pluginId, userPublicKey)).thenThrow(new DatabaseNotFoundException());
        assetDistributionMonitorAgent.start();
    }

    @Test
    public void startThrowsCantCreateDatabaseExceptionTest() throws Exception {
        when(pluginDatabaseSystem.openDatabase(pluginId, userPublicKey)).thenThrow(new DatabaseNotFoundException());
        when(pluginDatabaseSystem.createDatabase(pluginId, userPublicKey)).thenThrow(new CantCreateDatabaseException());
        assetDistributionMonitorAgent.start();
    }

    @Test
    public void startThrowsCantOpenDatabaseExceptionTest() throws Exception {
        when(pluginDatabaseSystem.openDatabase(pluginId, userPublicKey)).thenThrow(new CantOpenDatabaseException());
        assetDistributionMonitorAgent.start();
    }

}
