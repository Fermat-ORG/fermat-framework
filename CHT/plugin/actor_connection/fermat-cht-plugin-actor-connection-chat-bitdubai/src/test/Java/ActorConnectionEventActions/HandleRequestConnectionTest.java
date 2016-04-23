package ActorConnectionEventActions;

import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantRequestActorConnectionException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ConnectionAlreadyRequestedException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.UnsupportedActorTypeException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.utils.ChatConnectionRequest;
import com.bitdubai.fermat_cht_plugin.layer.actor_connection.chat.developer.bitdubai.version_1.structure.ActorConnectionEventActions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

/**
 * Created by Miguel Rincon on 4/13/16.
 */

@RunWith(MockitoJUnitRunner.class)
public class HandleRequestConnectionTest {

    @Test
    public void handleRequestConnection() throws CantRequestActorConnectionException, UnsupportedActorTypeException, ConnectionAlreadyRequestedException {
        ActorConnectionEventActions actorConnectionEventActions = mock(ActorConnectionEventActions.class);

        doCallRealMethod().when(actorConnectionEventActions).handleRequestConnection(Mockito.any(ChatConnectionRequest.class));
    }

}
