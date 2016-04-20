package com.bitdubai.fermat_tky_plugin.layer.sub_app_module.artist_identity.developer.bitdubai.revision1.DeveloperBitDubaiTest;

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
        Assert.assertNull(developerBitDubai);
        developerBitDubai = new DeveloperBitDubai();
        Assert.assertNotNull(developerBitDubai);
    }

    @Test
    public void testgetAmountToPay(){
        int value = developerBitDubai.getAmountToPay();
        int expected = 100;
        Assert.assertNotNull(value);
        Assert.assertEquals(expected,value);
    }


    @Test
    public void testgetAmountToPayfail() {
        int value = developerBitDubai.getAmountToPay();
        int expected = 200;
        Assert.assertNotEquals(expected,value);

    }
}//end of class
