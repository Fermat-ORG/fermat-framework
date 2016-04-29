package com.bitdubai.fermat_tky_plugin.layer.wallet_module.fan.developer.bitdubai.DeveloperBitDubaiTest;

import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_tky_plugin.layer.wallet_module.fan.developer.bitdubai.DeveloperBitDubai;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by MACUARE on 27/04/16.
 */
public class TestGetTimePeriod {
    private DeveloperBitDubai developerBitDubai;


    @Before
    public void setUp() throws Exception {
        System.out.println("initializing");
        Assert.assertNull(developerBitDubai);
        developerBitDubai = new DeveloperBitDubai();
        Assert.assertNotNull(developerBitDubai);
    }

    @Test
    public void testGetTimePeriodNotNull() throws Exception {
        System.out.println("testGetTimePeriodNotNull");

        Assert.assertNotNull(developerBitDubai.getTimePeriod());
    }

    @Test
    public void testGetTimePeriodContext() throws Exception {
        System.out.println("testGetTimePeriodContext");
        String actual = developerBitDubai.getTimePeriod().getCode(),
               expected = TimeFrequency.MONTHLY.getCode();
        Assert.assertNotNull(expected);
        Assert.assertEquals(expected,actual);
    }
}//end of class
