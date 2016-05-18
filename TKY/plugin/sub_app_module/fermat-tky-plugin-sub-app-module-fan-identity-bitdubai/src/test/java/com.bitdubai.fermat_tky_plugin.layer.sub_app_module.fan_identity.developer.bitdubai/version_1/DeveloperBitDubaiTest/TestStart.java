package com.bitdubai.fermat_tky_plugin.layer.sub_app_module.fan_identity.developer.bitdubai.version_1.DeveloperBitDubaiTest;

import com.bitdubai.fermat_tky_plugin.layer.sub_app_module.fan_identity.developer.bitdubai.DeveloperBitDubai;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Created by MACUARE on 20/04/16.
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
    public void testStart() throws Exception {
        System.out.println("TestStart");
        developerBitDubai.start();
        int actual = developerBitDubai.getClass().getDeclaredMethods().length;
        int expected = 5;
        Assert.assertEquals(expected,actual);



    }
}//end of class
