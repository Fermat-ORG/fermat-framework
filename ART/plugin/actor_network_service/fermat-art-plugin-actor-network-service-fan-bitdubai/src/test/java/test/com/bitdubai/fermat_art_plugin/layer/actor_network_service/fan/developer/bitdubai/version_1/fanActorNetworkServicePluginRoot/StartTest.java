package test.com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.fanActorNetworkServicePluginRoot;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.FanActorNetworkServicePluginRoot;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus.STARTING;

/**
 * Created by gianco on 20/04/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class StartTest {



    @Test
    public void startTest() throws CantStartPluginException {
        FanActorNetworkServicePluginRoot fanActorNetworkServicePluginRoot = new FanActorNetworkServicePluginRoot();



        try {
            fanActorNetworkServicePluginRoot.start();
        } catch (CantStartPluginException exception) {
            Assert.assertNotNull(exception);
        } catch (Exception exception) {
            Assert.assertNotNull(exception);
        }

        Assert.assertEquals(STARTING, fanActorNetworkServicePluginRoot.getStatus());
    }
}

