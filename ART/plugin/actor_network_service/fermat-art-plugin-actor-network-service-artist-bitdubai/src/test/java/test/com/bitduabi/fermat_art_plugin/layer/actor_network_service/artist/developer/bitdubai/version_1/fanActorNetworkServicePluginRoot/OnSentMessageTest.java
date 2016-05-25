package test.com.bitduabi.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.fanActorNetworkServicePluginRoot;

import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.ArtistActorNetworkServicePluginRoot;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.powermock.api.mockito.PowerMockito.doCallRealMethod;

/**
 * Created by gianco on 03/05/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ArtistActorNetworkServicePluginRoot.class)
public class OnSentMessageTest {

    final FermatMessage fermatMessage = null;

    @Test
    public void onSentMessageTest() {

        ArtistActorNetworkServicePluginRoot artistActorNetworkServicePluginRoot = PowerMockito.mock(ArtistActorNetworkServicePluginRoot.class);

        doCallRealMethod().when(artistActorNetworkServicePluginRoot).onSentMessage(fermatMessage);
    }

}
