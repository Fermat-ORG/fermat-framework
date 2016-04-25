package ChatActorNetworkServiceManager;

import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantExposeIdentityException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.utils.ChatExposingData;
import com.bitdubai.fermat_cht_plugin.layer.actor_network_service.chat.developer.bitdubai.version_1.structure.ChatActorNetworkServiceManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

/**
 * Created by Miguel Rincon on 4/14/16.
 */

@RunWith(MockitoJUnitRunner.class)
public class ExposeIdentityTest {

    @Test
    public void exposeIdentity() throws CantExposeIdentityException {
        ChatActorNetworkServiceManager chatActorNetworkServiceManager = mock(ChatActorNetworkServiceManager.class);

        doCallRealMethod().when(chatActorNetworkServiceManager).exposeIdentity(Mockito.any(ChatExposingData.class));
    }

}
