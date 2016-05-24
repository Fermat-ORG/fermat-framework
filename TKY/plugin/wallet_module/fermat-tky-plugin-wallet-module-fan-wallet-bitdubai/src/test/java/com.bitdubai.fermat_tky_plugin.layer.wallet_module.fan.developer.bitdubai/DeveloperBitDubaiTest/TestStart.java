package com.bitdubai.fermat_tky_plugin.layer.wallet_module.fan.developer.bitdubai.DeveloperBitDubaiTest;

import com.bitdubai.fermat_tky_plugin.layer.wallet_module.fan.developer.bitdubai.DeveloperBitDubai;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by MACUARE on 27/04/16.
 */
public class TestStart {
    private DeveloperBitDubai developerBitDubai;


    @Before
    public void setUp() throws Exception {
        System.out.println("initializing");
        Assert.assertNull(developerBitDubai);
        developerBitDubai = new DeveloperBitDubai();
        Assert.assertNotNull(developerBitDubai);
    }

    @Test
    public void testStartBehavior() throws Exception {
        System.out.println("testStartBehavior");
        developerBitDubai.start();
        String actual = developerBitDubai.getPluginDeveloperReference().getDeveloper().toString();
        Assert.assertNotNull(actual);
        Assert.assertEquals(actual, "BITDUBAI");
    }

}//end of class
