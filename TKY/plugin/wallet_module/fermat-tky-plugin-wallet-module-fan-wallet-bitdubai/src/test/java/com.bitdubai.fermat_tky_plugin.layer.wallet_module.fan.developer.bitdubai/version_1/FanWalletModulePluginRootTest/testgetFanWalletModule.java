package com.bitdubai.fermat_tky_plugin.layer.wallet_module.fan.developer.bitdubai.version_1.FanWalletModulePluginRootTest;

import com.bitdubai.fermat_tky_plugin.layer.wallet_module.fan.developer.bitdubai.version_1.FanWalletModulePluginRoot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Created by MACUARE on 27/04/16.
 */
public class testgetFanWalletModule {

    private FanWalletModulePluginRoot fanWalletModulePluginRoot;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        System.out.println("initializing");
        Assert.assertNull(fanWalletModulePluginRoot);
        fanWalletModulePluginRoot = new FanWalletModulePluginRoot();
        Assert.assertNotNull(fanWalletModulePluginRoot);
    }

    @Test
    public void testgetFanWalletModuleNotNull() throws Exception {
        System.out.println("testgetFanWalletModuleNotNull");
        Assert.assertNotNull(fanWalletModulePluginRoot.getFanWalletModule());
    }

    @Test
    public void testgetFanWalletModuleException() throws Exception{
        System.out.println("testgetFanWalletModuleException");
        exception.expect(NullPointerException.class);
        fanWalletModulePluginRoot.getFanWalletModule().getAvailableSongs();
    }

}//end of class
