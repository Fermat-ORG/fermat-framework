package com.bitdubai.fermat_tky_plugin.layer.sub_app_module.fan_identity.developer.bitdubai.version_1.FanIdentityPluginRootTest;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_tky_plugin.layer.sub_app_module.fan_identity.developer.bitdubai.version_1.FanIdentityPluginRoot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by MACUARE on 21/04/16.
 */
public class TestStart {
    private FanIdentityPluginRoot fanIdentityPluginRoot;

    @Before
    public void setUp() throws Exception {
        System.out.println("initializing");
        Assert.assertNull(fanIdentityPluginRoot);
        fanIdentityPluginRoot = new FanIdentityPluginRoot();
        Assert.assertNotNull(fanIdentityPluginRoot);
    }

    @Test
    public void testStart() throws Exception {
        System.out.println("TestStart");
        String actual = fanIdentityPluginRoot.getStatus().code;
        String expected = ServiceStatus.STARTED.code;

        Assert.assertFalse(actual.equalsIgnoreCase(expected));
        fanIdentityPluginRoot.start();
        actual = fanIdentityPluginRoot.getStatus().code;
        Assert.assertTrue(actual.equalsIgnoreCase(expected));
    }

}//end of class
