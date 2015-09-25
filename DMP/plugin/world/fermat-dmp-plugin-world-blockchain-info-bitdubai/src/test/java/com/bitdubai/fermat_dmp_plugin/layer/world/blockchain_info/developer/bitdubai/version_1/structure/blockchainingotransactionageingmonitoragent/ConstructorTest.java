package com.bitdubai.fermat_ccp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure.blockchainingotransactionageingmonitoragent;

import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_ccp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure.BlockchainInfoTransactionAgeingMonitorAgent;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

/**
 * Created by leon on 5/6/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructorTest extends TestCase {

    String apiKey;

    ErrorManager errorManager;

    PluginDatabaseSystem pluginDatabaseSystem;

    UUID pluginId;

    UUID walletId;

    @Test
    public void testConstructor_NotNull() {
        BlockchainInfoTransactionAgeingMonitorAgent transactionAgeingMonitorAgent = new BlockchainInfoTransactionAgeingMonitorAgent(apiKey, errorManager, pluginDatabaseSystem, pluginId, walletId);
        assertNotNull(transactionAgeingMonitorAgent);
    }
}
