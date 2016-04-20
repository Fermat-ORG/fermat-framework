package com.bitdubai.fermat_tky_plugin.layer.sub_app_module.artist_identity.developer.bitdubai.revision1.DeveloperBitDubaiTest;

import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_tky_plugin.layer.sub_app_module.artist_identity.developer.bitdubai.DeveloperBitDubai;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by MACUARE on 19/04/16.
 */
public class TestgetTimePeriod {

    private DeveloperBitDubai developerBitDubai;


    @Before
    public void setUp(){
        Assert.assertNull(developerBitDubai);
        developerBitDubai = new DeveloperBitDubai();
        Assert.assertNotNull(developerBitDubai);
    }

    @Test
    public void testgetTimePeriod(){
        TimeFrequency actual = developerBitDubai.getTimePeriod();
        TimeFrequency expected = TimeFrequency.MONTHLY;

        Assert.assertNotNull(actual);
        Assert.assertNotNull(expected);
        Assert.assertEquals(expected, actual);
        }
}
