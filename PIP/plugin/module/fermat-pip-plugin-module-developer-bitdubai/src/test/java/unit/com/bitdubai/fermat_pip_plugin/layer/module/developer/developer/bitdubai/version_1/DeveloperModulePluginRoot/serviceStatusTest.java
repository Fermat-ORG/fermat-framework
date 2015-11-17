package unit.com.bitdubai.fermat_pip_plugin.layer.module.developer.developer.bitdubai.version_1.DeveloperModulePluginRoot;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_pip_plugin.layer.sub_app_module.developer.developer.bitdubai.version_1.DeveloperSubAppModulePluginRoot;

import org.junit.Assert;
import org.junit.Test;

import static com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus.CREATED;
import static com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus.PAUSED;
import static com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus.STARTED;
import static com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus.STOPPED;

/**
 * Created by Nerio on 18/07/15.
 */
public class serviceStatusTest {

    @Test
    public void pausedTest() {
        DeveloperSubAppModulePluginRoot root = new DeveloperSubAppModulePluginRoot();
        root.pause();
        Assert.assertEquals(PAUSED, root.getStatus());
    }

    @Test
    public void startedTest() throws CantStartPluginException {
        DeveloperSubAppModulePluginRoot root = new DeveloperSubAppModulePluginRoot();
        try {
            root.start();
        } catch (CantStartPluginException exception) {
            Assert.assertNotNull(exception);
        } catch (Exception exception) {
            Assert.assertNotNull(exception);
        }
        Assert.assertEquals(STARTED, root.getStatus());
    }

    @Test
    public void resumeTest() {
        DeveloperSubAppModulePluginRoot root = new DeveloperSubAppModulePluginRoot();
        root.resume();
        Assert.assertEquals(STARTED, root.getStatus());
    }

    @Test
    public void createdTest() {
        DeveloperSubAppModulePluginRoot root = new DeveloperSubAppModulePluginRoot();
        Assert.assertEquals(CREATED, root.getStatus());
    }

    @Test
    public void stopTest() {
        DeveloperSubAppModulePluginRoot root = new DeveloperSubAppModulePluginRoot();
        root.stop();
        Assert.assertEquals(STOPPED, root.getStatus());
    }
}