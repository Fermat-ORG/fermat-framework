package com.bitdubai.fermat_tky_plugin.layer.sub_app_module.artist_identity.developer.bitdubai.version_1.DeveloperBitDubaiTest;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_tky_plugin.layer.sub_app_module.artist_identity.developer.bitdubai.DeveloperBitDubai;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by MACUARE on 19/04/16.
 */
public class TestgetCryptoCurrency {
    private DeveloperBitDubai developerBitDubai;

    @Before
    public void setUp(){
        System.out.println("initializing");
        Assert.assertNull(developerBitDubai);
        developerBitDubai = new DeveloperBitDubai();
        Assert.assertNotNull(developerBitDubai);
    }

    @Test
    public void testgetCryptoCurrency(){
        System.out.println("testgetCryptoCurrency");
        CryptoCurrency actual = developerBitDubai.getCryptoCurrency();
        CryptoCurrency expected = CryptoCurrency.BITCOIN;
        Assert.assertNotNull(actual);
        Assert.assertNotNull(expected);
        Assert.assertEquals(expected,actual);
    }

}// end of class
