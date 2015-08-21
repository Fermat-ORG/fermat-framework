package test.com.bitdubai.fermat_dmp_plugin.layer.module.wallet_store.developer.bitdubai.version_1.WalletStoreModulePluginRoot;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_pip_plugin.layer.module.developer.developer.bitdubai.version_1.ModuleDeveloperPluginRoot;

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
        ModuleDeveloperPluginRoot root = new ModuleDeveloperPluginRoot();
        root.pause();
        Assert.assertEquals(PAUSED, root.getStatus());
    }

    @Test
    public void startedTest() throws CantStartPluginException {
        ModuleDeveloperPluginRoot root = new ModuleDeveloperPluginRoot();
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
        ModuleDeveloperPluginRoot root = new ModuleDeveloperPluginRoot();
        root.resume();
        Assert.assertEquals(STARTED, root.getStatus());
    }

    @Test
    public void createdTest() {
        ModuleDeveloperPluginRoot root = new ModuleDeveloperPluginRoot();
        Assert.assertEquals(CREATED, root.getStatus());
    }

    @Test
    public void stopTest() {
        ModuleDeveloperPluginRoot root = new ModuleDeveloperPluginRoot();
        root.stop();
        Assert.assertEquals(STOPPED, root.getStatus());
    }
}