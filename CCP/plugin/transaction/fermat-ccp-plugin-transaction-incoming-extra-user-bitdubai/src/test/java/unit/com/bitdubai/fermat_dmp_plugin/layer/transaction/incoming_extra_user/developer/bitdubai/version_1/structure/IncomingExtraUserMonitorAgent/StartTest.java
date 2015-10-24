/*
package unit.com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.structure.IncomingExtraUserMonitorAgent;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cry_api.layer.crypto_router.incoming_crypto.IncomingCryptoManager;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.structure.IncomingExtraUserMonitorAgent;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.structure.IncomingExtraUserRegistry;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

*/
/**
 * Created by jorgegonzalez on 2015.07.02..
 *//*

 @RunWith(MockitoJUnitRunner.class)
public class StartTest {

    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;
    @Mock
    private Database mockDatabase;
    @Mock
    private DatabaseTable mockTable;
    private IncomingExtraUserRegistry testRegistry;

    @Mock
    private IncomingCryptoManager mockCryptoManager;

    private MockErrorManager mockErrorManager = new MockErrorManager();

    private IncomingExtraUserMonitorAgent testMonitorAgent;

    @Before
    public void setUpRegistry() throws Exception{
        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);
        testRegistry = new IncomingExtraUserRegistry();
        testRegistry.setErrorManager(mockErrorManager);
        testRegistry.setPluginDatabaseSystem(mockPluginDatabaseSystem);
    }

    @Test
    public void Start_ParametersProperlySet_ThreadStarted() throws Exception{

        testMonitorAgent = new IncomingExtraUserMonitorAgent(mockErrorManager, mockCryptoManager, testRegistry);

        testMonitorAgent.start();
        Thread.sleep(100);
        assertThat(testMonitorAgent.isRunning()).isTrue();
    }

    @Test
    public void Start_ParametersProperlySet_ThreadStopped() throws Exception{

        testMonitorAgent = new IncomingExtraUserMonitorAgent(mockErrorManager, mockCryptoManager, testRegistry);

        testMonitorAgent.start();
        Thread.sleep(100);
        testMonitorAgent.stop();
        assertThat(testMonitorAgent.isRunning()).isFalse();
    }

}
*/
