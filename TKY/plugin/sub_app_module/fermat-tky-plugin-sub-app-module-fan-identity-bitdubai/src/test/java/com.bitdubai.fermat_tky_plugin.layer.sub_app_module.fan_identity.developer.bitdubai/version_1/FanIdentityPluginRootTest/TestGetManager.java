package com.bitdubai.fermat_tky_plugin.layer.sub_app_module.fan_identity.developer.bitdubai.version_1.FanIdentityPluginRootTest;



import com.bitdubai.fermat_tky_plugin.layer.sub_app_module.fan_identity.developer.bitdubai.version_1.FanIdentityPluginRoot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by MACUARE on 21/04/16.
 */
public class TestGetManager {
    private FanIdentityPluginRoot fanIdentityPluginRoot;

    @Before
    public void setUp() throws Exception {
        System.out.println("initializing");
        Assert.assertNull(fanIdentityPluginRoot);
        fanIdentityPluginRoot = new FanIdentityPluginRoot();
        Assert.assertNotNull(fanIdentityPluginRoot);
    }

    @Test
    public void testGetManagerNull() throws Exception {
        System.out.println("TestGetManager");
        Assert.assertNull(fanIdentityPluginRoot.getManager());
    }

    @Test
    public void testGetManagerNotNull() throws Exception {
        System.out.println("testGetManagerNotNull");
        fanIdentityPluginRoot.start();
        Assert.assertNotNull(fanIdentityPluginRoot.getManager());
    }

}//end of class
