package unit.com.bitdubai.fermat_osa_addon.layer.android.logger.developer.bitdubai.version_1.AddonLoggerRoot;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_osa_addon.layer.android.logger.developer.bitdubai.version_1.LoggerAddonRoot;
import com.bitdubai.fermat_osa_addon.layer.android.logger.developer.bitdubai.version_1.structure.LoggerManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus.CREATED;
import static com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus.PAUSED;
import static com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus.STARTED;
import static com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus.STOPPED;

/**
 * Created by Nerio on 05/08/15.
 */
public class serviceStatusTest {

    LoggerAddonRoot root;

    @Before
    public void setUP(){
        //TODO: comentado porque no corre la app
       //root = new LoggerAddonRoot();

    }
    @Test
    public void pausedTest() {
        root.pause();
        Assert.assertEquals(PAUSED, root.getStatus());
    }

    @Test
    public void startedTest() throws CantStartPluginException {

        try {
            root.start();
            root.getLoggerManager();
        } catch (CantStartPluginException exception) {
            Assert.assertNotNull(exception);
        } catch (Exception exception) {
            Assert.assertNotNull(exception);
        }
        Assert.assertEquals(STARTED, root.getStatus());
        Assert.assertNotNull(root.getLoggerManager());
    }

    @Test
    public void resumeTest() {
        root.resume();
        Assert.assertEquals(STARTED, root.getStatus());
    }

    @Test
    public void createdTest() {
        Assert.assertEquals(CREATED, root.getStatus());
    }

    @Test
    public void stopTest() {
        root.stop();
        Assert.assertEquals(STOPPED, root.getStatus());
    }
}
