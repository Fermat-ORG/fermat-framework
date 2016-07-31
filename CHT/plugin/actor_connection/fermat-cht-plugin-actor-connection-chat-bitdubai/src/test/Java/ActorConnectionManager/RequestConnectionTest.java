package ActorConnectionManager;

import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantRequestActorConnectionException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ConnectionAlreadyRequestedException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.UnsupportedActorTypeException;
import com.bitdubai.fermat_api.layer.actor_connection.common.structure_common_classes.ActorIdentityInformation;
import com.bitdubai.fermat_cht_plugin.layer.actor_connection.chat.developer.bitdubai.version_1.structure.ActorConnectionManager;

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
public class RequestConnectionTest {

    @Test
    public void requestConnection() throws CantRequestActorConnectionException, UnsupportedActorTypeException, ConnectionAlreadyRequestedException {
        ActorConnectionManager actorConnectionManager = mock(ActorConnectionManager.class);

        doCallRealMethod().when(actorConnectionManager).requestConnection(Mockito.any(ActorIdentityInformation.class), Mockito.any(ActorIdentityInformation.class));
    }

}
