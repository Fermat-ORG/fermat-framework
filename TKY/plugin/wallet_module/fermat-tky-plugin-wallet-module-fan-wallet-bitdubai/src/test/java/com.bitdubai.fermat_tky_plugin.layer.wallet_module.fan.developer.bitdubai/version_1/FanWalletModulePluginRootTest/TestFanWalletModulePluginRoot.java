package com.bitdubai.fermat_tky_plugin.layer.wallet_module.fan.developer.bitdubai.version_1.FanWalletModulePluginRootTest;

import com.bitdubai.fermat_tky_plugin.layer.wallet_module.fan.developer.bitdubai.version_1.FanWalletModulePluginRoot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by MACUARE on 27/04/16.
 */
public class TestFanWalletModulePluginRoot {
    private FanWalletModulePluginRoot fanWalletModulePluginRoot;


    @Before
    public void setUp() throws Exception {
        System.out.println("initializing");
        Assert.assertNull(fanWalletModulePluginRoot);
        fanWalletModulePluginRoot = new FanWalletModulePluginRoot();
        Assert.assertNotNull(fanWalletModulePluginRoot);
    }

    @Test
    public void testFanWalletModulePluginRootMethods() throws Exception {
        System.out.println("testFanWalletModulePluginRootMethods");
        int actual = fanWalletModulePluginRoot.getClass().getDeclaredMethods().length;
        int expected = 6;
        Assert.assertEquals(actual,expected);


    }
}//end of class
