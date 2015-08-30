package test.com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.WalletContactsMiddlewarePluginRoot;

import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.WalletContactsMiddlewarePluginRoot;

import org.junit.Assert;
import org.junit.Test;

import static com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus.CREATED;
import static com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus.PAUSED;
import static com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus.STARTED;
import static com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus.STOPPED;

/**
 * Created by Nerio on 18/07/15.
 */
public class GerviceStatusTest {

    @Test
    public void pausedTest(){
        WalletContactsMiddlewarePluginRoot root = new WalletContactsMiddlewarePluginRoot();
        root.pause();
        Assert.assertEquals(PAUSED, root.getStatus());
    }

    @Test
    public void startedTest(){
        WalletContactsMiddlewarePluginRoot root = new WalletContactsMiddlewarePluginRoot();
        root.start();
        Assert.assertEquals(STARTED, root.getStatus());
    }

    @Test
    public void resumeTest(){
        WalletContactsMiddlewarePluginRoot root = new WalletContactsMiddlewarePluginRoot();
        root.resume();
        Assert.assertEquals(STARTED, root.getStatus());
    }

    @Test
    public void createdTest(){
        WalletContactsMiddlewarePluginRoot root = new WalletContactsMiddlewarePluginRoot();
        Assert.assertEquals(CREATED, root.getStatus());
    }

    @Test
    public void stopTest(){
        WalletContactsMiddlewarePluginRoot root = new WalletContactsMiddlewarePluginRoot();
        root.stop();
        Assert.assertEquals(STOPPED, root.getStatus());
    }
}