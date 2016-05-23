package com.bitdubai.fermat_tky_plugin.layer.sub_app_module.artist_identity.developer.bitdubai.version_1.DeveloperBitDubaiTest;

import com.bitdubai.fermat_tky_plugin.layer.sub_app_module.artist_identity.developer.bitdubai.DeveloperBitDubai;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by MACUARE on 19/04/16.
 */
public class TestgetAddress {

    private DeveloperBitDubai developerBitDubai;

    @Before
    public void setUp() throws Exception {
        System.out.println("initializing");
        Assert.assertNull(developerBitDubai);
        developerBitDubai = new DeveloperBitDubai();
        Assert.assertNotNull(developerBitDubai);
    }

    @Test
    public void testgetAddress() throws Exception {
        System.out.println("testgetAddress");
        String actual = developerBitDubai.getAddress();
        String expected = "19qRypu7wrndwW4FRCxU1JPr5we134Q3eh";
        Assert.assertTrue(actual.length() >= 34);
        Assert.assertNotNull(actual);
        Assert.assertEquals(expected,actual);
    }

}// end of class
