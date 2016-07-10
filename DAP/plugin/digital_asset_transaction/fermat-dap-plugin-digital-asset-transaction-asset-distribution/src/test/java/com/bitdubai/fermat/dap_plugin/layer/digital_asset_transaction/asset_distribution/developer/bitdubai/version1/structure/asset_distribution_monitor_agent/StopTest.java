package com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.asset_distribution_monitor_agent;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;

import org.fermat.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.events.AssetDistributionMonitorAgent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

/**
 * Created by Luis Campo (campusprize@gmail.com)on 31/10/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class StopTest {
    @Mock
    private Database database;
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
    private AssetDistributionMonitorAgent assetDistributionMonitorAgent;

    @Before
    public void init() throws CantSetObjectException {
        userPublicKey = new ECCKeyPair().getPublicKey();
//        assetDistributionMonitorAgent = new AssetDistributionMonitorAgent(eventManager, pluginDatabaseSystem, errorManager, pluginId, userPublicKey, assetVaultManager);
    }

    @Test
    public void stopSuccesTest() throws CantStartAgentException, InterruptedException {
        assetDistributionMonitorAgent.start();
        assetDistributionMonitorAgent.stop();
    }

}
