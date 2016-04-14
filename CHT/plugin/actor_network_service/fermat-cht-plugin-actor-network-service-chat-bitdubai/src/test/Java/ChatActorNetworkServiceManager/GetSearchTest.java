package ChatActorNetworkServiceManager;

import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantListChatException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.interfaces.ChatSearch;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.utils.ChatExposingData;
import com.bitdubai.fermat_cht_plugin.layer.actor_network_service.chat.developer.bitdubai.version_1.structure.ChatActorNetworkServiceManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Miguel on 4/14/16.
 */

@RunWith(MockitoJUnitRunner.class)
public class GetSearchTest {

    @Test
    public void getSearch() {
        ChatActorNetworkServiceManager chatActorNetworkServiceManager = mock(ChatActorNetworkServiceManager.class);

        when(chatActorNetworkServiceManager.getSearch()).thenReturn(new ChatSearch() {
            @Override
            public List<ChatExposingData> getResult() throws CantListChatException {
                return null;
            }

            @Override
            public List<ChatExposingData> getResult(Integer max) throws CantListChatException {
                return null;
            }
        }).thenCallRealMethod();
        assertThat(chatActorNetworkServiceManager.getSearch()).isNotNull();
    }

}
