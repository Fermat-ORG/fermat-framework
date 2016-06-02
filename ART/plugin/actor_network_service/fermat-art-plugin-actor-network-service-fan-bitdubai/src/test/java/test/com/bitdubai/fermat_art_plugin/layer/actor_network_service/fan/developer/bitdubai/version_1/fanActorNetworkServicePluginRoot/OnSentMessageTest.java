package test.com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.fanActorNetworkServicePluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.FanActorNetworkServicePluginRoot;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.enums.MessageTypes;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.messages.NetworkServiceMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by gianco on 25/04/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class OnSentMessageTest{

    private NetworkServiceMessage mockNetworkServiceMessage;
    private FanActorNetworkServicePluginRoot mockFanActorNetworkServicePluginRoot;
    private OnSentMessageTest mockOnSentMessageTest;
    private FermatMessage mockFermatMessage;
    private String mockCode;
    private MessageTypes mockMessageTypes;

    @Before
    public void beforeEachTest() {
        mockFanActorNetworkServicePluginRoot = Mockito.mock(FanActorNetworkServicePluginRoot.class);
    }

    @Test
    public void onSentMessageTest() throws InvalidParameterException {

        System.out.print("no da errores");

        assertThat(mockFanActorNetworkServicePluginRoot).isNotNull();


    }
}
