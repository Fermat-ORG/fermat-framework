package ChatActorNetworkServiceManager;

import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantDisconnectException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_cht_plugin.layer.actor_network_service.chat.developer.bitdubai.version_1.structure.ChatActorNetworkServiceManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

/**
 * Created by Miguel Rincon on 4/14/16.
 */

@RunWith(MockitoJUnitRunner.class)
public class DisconnectTest {

    @Test
    public void disconnect() throws CantDisconnectException, ConnectionRequestNotFoundException {
        ChatActorNetworkServiceManager chatActorNetworkServiceManager = mock(ChatActorNetworkServiceManager.class);

        doCallRealMethod().when(chatActorNetworkServiceManager).disconnect(Mockito.any(UUID.class));
    }

}
