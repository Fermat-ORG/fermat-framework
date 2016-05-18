package test.com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure.IncomingCryptoRelayAgent;

import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookManager;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure.IncomingCryptoRegistry;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure.IncomingCryptoRelayAgent;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by Franklin Marcano 04/08/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructionTest {
    @Mock
    private ActorAddressBookManager mockActorAddressBookManager;

    @Mock
    private ErrorManager mockErrorManager;

    @Mock
    private EventManager mockeventManager;

    @Mock
    private IncomingCryptoRegistry mockregistry;

    private IncomingCryptoRelayAgent testRelayAgent;

    @Test
    public void Construction_ValidParameters_NewObjectCreated() {
        testRelayAgent = new IncomingCryptoRelayAgent();
        testRelayAgent.setActorAddressBookManager(mockActorAddressBookManager);
        testRelayAgent.setErrorManager(mockErrorManager);
        testRelayAgent.setEventManager(mockeventManager);
        testRelayAgent.setRegistry(mockregistry);
        assertThat(testRelayAgent).isNotNull();
    }
}
