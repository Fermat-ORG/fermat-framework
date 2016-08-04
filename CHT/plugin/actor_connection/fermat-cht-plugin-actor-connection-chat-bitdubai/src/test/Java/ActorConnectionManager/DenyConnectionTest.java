package ActorConnectionManager;

import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ActorConnectionNotFoundException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDenyActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.UnexpectedConnectionStateException;
import com.bitdubai.fermat_cht_plugin.layer.actor_connection.chat.developer.bitdubai.version_1.structure.ActorConnectionManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

/**
 * Created by Miguel Rincon on 4/13/16.
 */

@RunWith(MockitoJUnitRunner.class)
public class DenyConnectionTest {

    @Test
    public void denyConnection() throws CantDenyActorConnectionRequestException, ActorConnectionNotFoundException, UnexpectedConnectionStateException {
        ActorConnectionManager actorConnectionManager = mock(ActorConnectionManager.class);

        doCallRealMethod().when(actorConnectionManager).denyConnection(Mockito.any(UUID.class));
    }

}
