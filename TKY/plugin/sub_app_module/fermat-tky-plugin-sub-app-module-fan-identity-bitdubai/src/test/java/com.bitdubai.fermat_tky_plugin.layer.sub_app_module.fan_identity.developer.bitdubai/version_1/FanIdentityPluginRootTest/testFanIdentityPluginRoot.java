package com.bitdubai.fermat_tky_plugin.layer.sub_app_module.fan_identity.developer.bitdubai.version_1.FanIdentityPluginRootTest;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_tky_plugin.layer.sub_app_module.fan_identity.developer.bitdubai.version_1.FanIdentityPluginRoot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runners.Parameterized;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * Created by MACUARE on 20/04/16.
 */

public class testFanIdentityPluginRoot {
    private FanIdentityPluginRoot fanIdentityPluginRoot;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        System.out.println("initializing");
        Assert.assertNull(fanIdentityPluginRoot);
        fanIdentityPluginRoot = new FanIdentityPluginRoot();
        Assert.assertNotNull(fanIdentityPluginRoot);
    }

    @Test
    public void testFanIdentityPluginRootContents() throws Exception {
        System.out.println("testFanIdentityPluginRoot");
        Method methods[] = fanIdentityPluginRoot.getClass().getDeclaredMethods();
        int actual = methods.length;
        int expected = 5;

        Assert.assertEquals(expected, actual);
        Assert.assertNotNull(methods);
    }

    @Test
    public void testFanIdentityPluginRootException() throws Exception {
        System.out.println("testFanIdentityPluginRootException");
        exception.expect(ArrayIndexOutOfBoundsException.class);
        exception.expectMessage("10");
        Method methods[] = fanIdentityPluginRoot.getClass().getDeclaredMethods();
        String name = methods[10].getName();
    }

}//end of class
