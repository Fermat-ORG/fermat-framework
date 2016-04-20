package com.bitdubai.fermat_tky_plugin.layer.sub_app_module.artist_identity.developer.bitdubai.version_1.DeveloperBitDubaiTest;

import com.bitdubai.fermat_tky_plugin.layer.sub_app_module.artist_identity.developer.bitdubai.DeveloperBitDubai;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by MACUARE on 19/04/16.
 */
public class TestgetAmountToPay {
    private DeveloperBitDubai developerBitDubai;

    @Before
    public void setUp() throws Exception {
        System.out.println("initializing");
        Assert.assertNull(developerBitDubai);
        developerBitDubai = new DeveloperBitDubai();
        Assert.assertNotNull(developerBitDubai);
    }

    @Test
    public void testgetAmountToPay(){
        System.out.println("testgetAmountToPay");
        int value = developerBitDubai.getAmountToPay();
        int expected = 100;
        Assert.assertNotNull(value);
        Assert.assertEquals(expected,value);
    }


    @Test
    public void testgetAmountToPayfail() {
        System.out.println("testgetAmountTofail");
        int value = developerBitDubai.getAmountToPay();
        int expected = 200;
        Assert.assertNotEquals(expected,value);

    }
}//end of class
