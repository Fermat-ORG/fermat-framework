package test.com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.fanActorNetworkServicePluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus.STARTED;

/**
 * Created by gianco on 20/04/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ResumeTest {


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
    public void resumeTest() {
        Abstract call = new Abstract(STARTED);
        call.resume();
        Assert.assertEquals(STARTED, call.getStatus());
    }
}
