package test.com.bitduabi.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.fanActorNetworkServicePluginRoot;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.ArtistActorNetworkServicePluginRoot;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus.PAUSED;
import static com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus.STARTED;
import static com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus.STARTING;
import static com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus.STOPPED;
import static org.powermock.api.mockito.PowerMockito.doCallRealMethod;

/**
 * Created by gianco on 03/05/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class OnStartTest {

    ArtistActorNetworkServicePluginRoot artistActorNetworkServicePluginRoot = new ArtistActorNetworkServicePluginRoot();

    @Test
    public void onStartTest() throws CantStartPluginException {

        try {
            artistActorNetworkServicePluginRoot.start();
        } catch (CantStartPluginException exception) {
            Assert.assertNotNull(exception);
        } catch (Exception exception) {
            Assert.assertNotNull(exception);
        }

        Assert.assertEquals(STARTING, artistActorNetworkServicePluginRoot.getStatus());
    }

    private PluginVersionReference plugin;

    class Abstract extends AbstractPlugin {

        public Abstract(ServiceStatus pluginVersionReference) {
            super(plugin);
        }

        public void resume()
        {
            super.resume();
            this.serviceStatus = ServiceStatus.STARTED;
        }
    }

    @Test
    public void pauseTest() {

        Abstract call = new Abstract(PAUSED);
        call.pause();
        Assert.assertEquals(PAUSED, call.getStatus());
    }

    @Test
    public void resumeTest() {
        Abstract call = new Abstract(STARTED);
        call.resume();
        Assert.assertEquals(STARTED, call.getStatus());
    }

    @Test
    public void stopTest() {
        Abstract call = new Abstract(STOPPED);
        call.stop();
        Assert.assertEquals(STOPPED, call.getStatus());
    }

}
