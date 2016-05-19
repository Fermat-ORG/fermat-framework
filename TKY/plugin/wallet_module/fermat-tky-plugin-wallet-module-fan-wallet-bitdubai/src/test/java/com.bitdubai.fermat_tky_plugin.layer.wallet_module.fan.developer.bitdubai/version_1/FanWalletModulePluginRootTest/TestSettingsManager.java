package com.bitdubai.fermat_tky_plugin.layer.wallet_module.fan.developer.bitdubai.version_1.FanWalletModulePluginRootTest;

import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_tky_plugin.layer.wallet_module.fan.developer.bitdubai.version_1.FanWalletModulePluginRoot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by MACUARE on 27/04/16.
 */
public class TestSettingsManager {
    private FanWalletModulePluginRoot fanWalletModulePluginRoot;

    @Before
    public void setUp() throws Exception {
        System.out.println("initializing");
        Assert.assertNull(fanWalletModulePluginRoot);
        fanWalletModulePluginRoot = new FanWalletModulePluginRoot();
        Assert.assertNotNull(fanWalletModulePluginRoot);
    }

    @Test
    public void testSettingsManagerInit() throws Exception {
        System.out.println("testSettingsManagerInit");
        Assert.assertNotNull(fanWalletModulePluginRoot.getModuleManager().getSettingsManager());
    }

    @Test
    public void testSettingManagerNewInstance() throws Exception {
        System.out.println("testSettingManagerNewInstance");
        Assert.assertTrue(fanWalletModulePluginRoot.getModuleManager().getSettingsManager() instanceof SettingsManager);
    }

}//end of class
