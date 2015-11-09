package com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.asset_distribution_monitor_agent;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.DigitalAssetDistributionVault;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.events.AssetDistributionMonitorAgent;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

/**
 * Created by Luis Campo (campusprize@gmail.com)on 05/11/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class SetDigitalAssetDistributionVaultTest {

    private String userPublicKey;
    @Mock
    private EventManager eventManager;
    @Mock
    private ErrorManager errorManager;
    @Mock
    private PluginDatabaseSystem pluginDatabaseSystem;
    @Mock
    private PluginFileSystem pluginFileSystem;
    UUID pluginId;
    @Mock
    private AssetVaultManager assetVaultManager;
    @Mock
    private AssetTransmissionNetworkServiceManager assetTransmissionManager;

    private DigitalAssetDistributionVault digitalAssetDistributionVault;
    private AssetDistributionMonitorAgent assetDistributionMonitorAgent;

    @Before
    public void init() throws CantSetObjectException {
        userPublicKey = new ECCKeyPair().getPublicKey();
        pluginId = UUID.randomUUID();
        assetDistributionMonitorAgent = new AssetDistributionMonitorAgent(eventManager, pluginDatabaseSystem, errorManager, pluginId, userPublicKey, assetVaultManager);
        digitalAssetDistributionVault = new DigitalAssetDistributionVault(pluginId, pluginFileSystem, errorManager);
    }

    @Test
    public void setDigitalAssetDistributionVaultTest() throws CantSetObjectException {
        assetDistributionMonitorAgent.setDigitalAssetDistributionVault(digitalAssetDistributionVault);
    }

    /*@Test
    public void setDigitalAssetDistributionVaultThrowsCantSetObjectExceptionTest() throws CantSetObjectException {
        catchException(assetDistributionMonitorAgent).setDigitalAssetDistributionVault(null);
        Exception thrown = caughtException();
        assertThat(thrown)
                .isNotNull()
                .isInstanceOf(CantSetObjectException.class);
    }*/

}
