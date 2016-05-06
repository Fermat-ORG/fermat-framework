package com.bitdubai.fermat_tky_plugin.layer.wallet_module.fan.developer.bitdubai.version_1.FanWalletModulePluginRootTest.structure;

import com.bitdubai.fermat_tky_plugin.layer.wallet_module.fan.developer.bitdubai.version_1.structure.FanWalletModuleManagerImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by MACUARE on 27/04/16.
 */
public class TestFanWalletModuleManagerImpl {
    private FanWalletModuleManagerImpl fanWalletModuleManager;

    @Before
    public void setUp() throws Exception {
        System.out.println("initializing");
        Assert.assertNull(fanWalletModuleManager);
        fanWalletModuleManager = new FanWalletModuleManagerImpl(null, null, null, null,null,null);
        Assert.assertNotNull(fanWalletModuleManager);
    }

    @Test
    public void testFanWalletModuleManagerImplContext() throws Exception {
        System.out.println("testFanWalletModuleManagerImplContext");

        int actual = fanWalletModuleManager.getClass().getDeclaredMethods().length;
        int expected = 18;
        Assert.assertEquals(expected, actual);
    }
}//end of class
