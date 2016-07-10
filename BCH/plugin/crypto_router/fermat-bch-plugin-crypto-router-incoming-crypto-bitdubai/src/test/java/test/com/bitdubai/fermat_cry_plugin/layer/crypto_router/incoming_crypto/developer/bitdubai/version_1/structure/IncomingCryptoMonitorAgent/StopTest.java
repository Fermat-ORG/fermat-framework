package test.com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure.IncomingCryptoMonitorAgent;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.currency_vault.CryptoVaultManager;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure.IncomingCryptoMonitorAgent;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure.IncomingCryptoRegistry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by Franklin Marcano 04/08/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class StopTest {
    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;
    @Mock
    private Database mockDatabase;
    @Mock
    private DatabaseTable mockTable;

    @Mock
    private CryptoVaultManager mockIncomingCryptoManager;

    @Mock
    private IncomingCryptoRegistry mockRegistry;

    @Mock
    private IncomingCryptoMonitorAgent testMonitorAgent;

    private MockErrorManager mockErrorManager = new MockErrorManager();

    @Before
    public void setUpRegistry() throws Exception{
        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);
        mockRegistry = new IncomingCryptoRegistry();
        mockRegistry.setErrorManager(mockErrorManager);
        mockRegistry.setPluginDatabaseSystem(mockPluginDatabaseSystem);
    }

    @Test
    public void Stop_AgentStops_TheThreadIsStoppedInmediately() throws Exception{

        testMonitorAgent = new IncomingCryptoMonitorAgent();
        testMonitorAgent.setRegistry(mockRegistry);
        testMonitorAgent.setCryptoVaultManager(mockIncomingCryptoManager);
        testMonitorAgent.setErrorManager(mockErrorManager);

        testMonitorAgent.start();

        Thread.sleep(100);
        int i = 0;
        while(testMonitorAgent.isRunning()){
            ++i;
            if(i>5)
                testMonitorAgent.stop();
            Thread.sleep(100);
            if(i>200)
                break;
        }
        assertThat(i).isLessThan(200);

    }
}
