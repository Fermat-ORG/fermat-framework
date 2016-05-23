package com.bitdubai.fermat_tky_plugin.layer.sub_app_module.fan_identity.developer.bitdubai.version_1.structure.FanIdentityManagerTest;

import com.bitdubai.fermat_tky_plugin.layer.sub_app_module.fan_identity.developer.bitdubai.version_1.structure.FanIdentityManager;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Method;

/**
 * Created by MACUARE on 21/04/16.
 */
public class TestFanIdentityManager {
    private FanIdentityManager fanIdentityManager;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        System.out.println("initializing");
        Assert.assertNull(fanIdentityManager);
        fanIdentityManager = new FanIdentityManager(null,null,null);
        Assert.assertNotNull(fanIdentityManager);
    }

    @Test
    public void testFanIdentityManagerContents() throws Exception {
        System.out.println("testFanIdentityManagerContents");
        Method methods[] = fanIdentityManager.getClass().getDeclaredMethods();
        int actual = methods.length;
        int expected = 9;

        Assert.assertEquals(expected, actual);
        Assert.assertNotNull(methods);
    }

    @Test
    public void testFanIdentityManagerExceptions() throws Exception {
        System.out.println("testFanIdentityManagerExceptions");
        exception.expect(ArrayIndexOutOfBoundsException.class);
        exception.expectMessage("15");
        Method methods[] = fanIdentityManager.getClass().getDeclaredMethods();
        String name = methods[15].getName();
    }

}//end of class
