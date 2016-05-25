package test.com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure.IncomingCryptoRelayAgent;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookManager;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure.IncomingCryptoRegistry;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure.IncomingCryptoRelayAgent;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;
/**
 * Created by Franklin Marcano 04/08/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class StartTest {
    @Mock
    private ActorAddressBookManager mockActorAddressBookManager;

    @Mock
    private ErrorManager mockErrorManager;

    @Mock
    private EventManager mockeventManager;

    @Mock
    private IncomingCryptoRegistry mockregistry;

    private IncomingCryptoRelayAgent testRelayAgent;

    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;
    @Mock
    private Database mockDatabase;
    @Mock
    private DatabaseTable mockTable;

    @Before
    public void setUpRegistry() throws Exception{
        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);
        mockregistry = new IncomingCryptoRegistry();
        mockregistry.setErrorManager(mockErrorManager);
        mockregistry.setPluginDatabaseSystem(mockPluginDatabaseSystem);
    }

    @Test
    public void Start_ParametersProperlySet_ThreadStarted() throws Exception{
        testRelayAgent = new IncomingCryptoRelayAgent();
        testRelayAgent.setEventManager(mockeventManager);
        testRelayAgent.setRegistry(mockregistry);
        testRelayAgent.setErrorManager(mockErrorManager);
        testRelayAgent.setActorAddressBookManager(mockActorAddressBookManager);

        testRelayAgent.start();
        Thread.sleep(100);
        assertThat(testRelayAgent.isRunning()).isTrue();
    }

}
