package com.bitdubai.fermat_tky_plugin.layer.wallet_module.fan.developer.bitdubai.DeveloperBitDubaiTest;

import com.bitdubai.fermat_tky_plugin.layer.wallet_module.fan.developer.bitdubai.DeveloperBitDubai;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Created by MACUARE on 27/04/16.
 */
public class TestDeveloperBitDubai {
    private DeveloperBitDubai developerBitDubai;


    @Before
    public void setUp() throws Exception {
        System.out.println("initializing");
        Assert.assertNull(developerBitDubai);
        developerBitDubai = new DeveloperBitDubai();
        Assert.assertNotNull(developerBitDubai);
    }

    @Test
    public void testDeveloperBitDubaiConstructor() throws Exception {
        System.out.println("testDeveloperBitdubaiConstructor");

        int actual = developerBitDubai.getClass().getDeclaredMethods().length;
        int expected = 5;

        Assert.assertEquals(expected, actual);
    }



}//end of class
