package com.bitdubai.fermat_dmp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure.blockchainingotransactionageingmonitoragent;

import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_dmp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure.BlockchainInfoTransactionAgeingMonitorAgent;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.Mockito.when;

/**
 * Created by leon on 5/6/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetTransactionTest extends TestCase {

    String apiKey;

    @Mock ErrorManager errorManager;

    @Mock PluginDatabaseSystem pluginDatabaseSystem;


    UUID pluginId;

    UUID walletId;

    @Mock Database database;

    @Mock DatabaseTable databaseTable;

    BlockchainInfoTransactionAgeingMonitorAgent transactionAgeingMonitorAgent;

    @Before
    public void setUp() {
        apiKey = "91c646ef-c3fd-4dd0-9dc9-eba5c5600549";
        pluginId = UUID.randomUUID();
        walletId = UUID.randomUUID();
        try{ when(database.getTable("incoming_crypto")).thenReturn(databaseTable); } catch (Exception e) { System.out.println(e);}
        try{ when(pluginDatabaseSystem.openDatabase(this.pluginId, this.walletId.toString())).thenReturn(database); } catch (Exception e) {System.out.println(e);}
        transactionAgeingMonitorAgent = new BlockchainInfoTransactionAgeingMonitorAgent(apiKey, errorManager, pluginDatabaseSystem, pluginId, walletId);
    }

    @Test
    public void testStart() throws CantStartAgentException {
        transactionAgeingMonitorAgent.start();
    }
}
