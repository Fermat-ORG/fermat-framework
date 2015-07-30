package unit.com.bitdubai.fermat_pip_plugin.layer.module.developer.developer.bitdubai.version_1.DeveloperModulePluginRoot;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_pip_plugin.layer.module.developer.developer.bitdubai.version_1.DeveloperModulePluginRoot;

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
        DeveloperModulePluginRoot root = new DeveloperModulePluginRoot();
        root.pause();
        Assert.assertEquals(PAUSED, root.getStatus());
    }

    @Test
    public void startedTest() throws CantStartPluginException {
        DeveloperModulePluginRoot root = new DeveloperModulePluginRoot();
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
        DeveloperModulePluginRoot root = new DeveloperModulePluginRoot();
        root.resume();
        Assert.assertEquals(STARTED, root.getStatus());
    }

    @Test
    public void createdTest() {
        DeveloperModulePluginRoot root = new DeveloperModulePluginRoot();
        Assert.assertEquals(CREATED, root.getStatus());
    }

    @Test
    public void stopTest() {
        DeveloperModulePluginRoot root = new DeveloperModulePluginRoot();
        root.stop();
        Assert.assertEquals(STOPPED, root.getStatus());
    }
}