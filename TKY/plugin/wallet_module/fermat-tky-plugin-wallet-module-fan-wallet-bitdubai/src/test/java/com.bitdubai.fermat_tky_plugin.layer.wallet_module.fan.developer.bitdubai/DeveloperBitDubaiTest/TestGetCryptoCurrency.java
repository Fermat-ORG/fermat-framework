package com.bitdubai.fermat_tky_plugin.layer.wallet_module.fan.developer.bitdubai.DeveloperBitDubaiTest;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_tky_plugin.layer.wallet_module.fan.developer.bitdubai.DeveloperBitDubai;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by MACUARE on 27/04/16.
 */
public class TestGetCryptoCurrency {
    private DeveloperBitDubai developerBitDubai;


    @Before
    public void setUp() throws Exception {
        System.out.println("initializing");
        Assert.assertNull(developerBitDubai);
        developerBitDubai = new DeveloperBitDubai();
        Assert.assertNotNull(developerBitDubai);
    }

    @Test
    public void testGetCryptoCurrencyNotNull() throws Exception {
        System.out.println("testGetCryptoCurrencyNotNull");
        Assert.assertNotNull(developerBitDubai.getCryptoCurrency());
    }

    @Test
    public void testGetCryptoCurrencyObject() throws Exception {
        System.out.println("testGetCryptoCurrencyObject");
        Assert.assertNotNull(CryptoCurrency.BITCOIN.getCode());
        Assert.assertTrue(developerBitDubai.getCryptoCurrency().getCode().equalsIgnoreCase(CryptoCurrency.BITCOIN.getCode()));
    }
}//end of class
