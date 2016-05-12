package com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.asset_distribution_monitor_agent;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.events.AssetDistributionMonitorAgent;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.junit.Assert.fail;

/**
 * Created by Luis Campo (campusprize@gmail.com)on 05/11/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class SetBitcoinNetworkManager {
    private String userPublicKey;
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
    private BitcoinNetworkManager bitcoinNetworkManager;
    private AssetDistributionMonitorAgent assetDistributionMonitorAgent;

    @Before
    public void init() throws CantSetObjectException {
        userPublicKey = new ECCKeyPair().getPublicKey();
        assetDistributionMonitorAgent = new AssetDistributionMonitorAgent(eventManager, pluginDatabaseSystem, errorManager, pluginId, userPublicKey, assetVaultManager);
    }

    @Test
    public void setBitcoinNetworkManagerTest() throws CantSetObjectException {
        assetDistributionMonitorAgent.setBitcoinNetworkManager(bitcoinNetworkManager);
    }

   @Test
    public void setBitcoinNetworkManagerThrowsCantSetObjectExceptionTest() throws CantSetObjectException {
       try {
           assetDistributionMonitorAgent.setBitcoinNetworkManager(null);
           fail("The method didn't throw when I expected it to");
       }catch (Exception ex) {
           Assert.assertTrue(ex instanceof CantSetObjectException);
       }
    }

}
