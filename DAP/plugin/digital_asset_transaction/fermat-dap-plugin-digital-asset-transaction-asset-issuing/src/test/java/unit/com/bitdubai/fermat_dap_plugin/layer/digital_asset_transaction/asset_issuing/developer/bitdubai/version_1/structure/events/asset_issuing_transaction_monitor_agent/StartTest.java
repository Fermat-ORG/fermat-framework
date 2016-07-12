package unit.com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.events.asset_issuing_transaction_monitor_agent;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.events.AssetIssuingTransactionMonitorAgent;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;

import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.database.AssetIssuingDatabaseConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.Mockito.when;

/**
 * Created by frank on 05/11/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class StartTest {
    AssetIssuingTransactionMonitorAgent assetIssuingTransactionMonitorAgent;
    UUID pluginId;
    String userPublicKey = "userPublicKey";

    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;

    @Mock
    EventManager eventManager;

    @Mock
    ErrorManager errorManager;

    @Mock
    AssetVaultManager assetVaultManager;

    @Mock
    OutgoingIntraActorManager outgoingIntraActorManager;

    @Mock
    Database database;

    @Before
    public void setUp() throws Exception {
        pluginId = UUID.randomUUID();
        assetIssuingTransactionMonitorAgent = new AssetIssuingTransactionMonitorAgent(eventManager, pluginDatabaseSystem, errorManager, pluginId, userPublicKey, assetVaultManager, outgoingIntraActorManager);

        mockitoRules();
    }

    private void mockitoRules() throws Exception {
        when(pluginDatabaseSystem.openDatabase(pluginId, AssetIssuingDatabaseConstants.ASSET_ISSUING_DATABASE)).thenReturn(database);
        when(pluginDatabaseSystem.createDatabase(pluginId, userPublicKey)).thenReturn(database);
    }

    @Test
    public void test_OK() throws Exception {
        assetIssuingTransactionMonitorAgent.start();
    }
}
