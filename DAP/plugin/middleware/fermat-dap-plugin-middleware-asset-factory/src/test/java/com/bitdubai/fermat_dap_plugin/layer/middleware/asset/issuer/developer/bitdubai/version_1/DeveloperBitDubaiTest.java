package com.bitdubai.fermat_dap_plugin.layer.middleware.asset.issuer.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;

import org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.DeveloperBitDubai;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by franklin on 22/09/15.
 */
public class DeveloperBitDubaiTest {
    @Test
    public void constructorTest(){
        DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
        Assert.assertNotNull(developerBitDubai.getPlugin());

    }

    @Test
    public void getterTest(){
        DeveloperBitDubai developerBitDubai= new DeveloperBitDubai();

        Assert.assertEquals(developerBitDubai.getAddress(), "13gpMizSNvQCbJzAPyGCUnfUGqFD8ryzcv");

        Assert.assertEquals(developerBitDubai.getAmountToPay(), 100);

        Assert.assertEquals(developerBitDubai.getCryptoCurrency(), CryptoCurrency.BITCOIN);

        Assert.assertEquals(developerBitDubai.getTimePeriod(), TimeFrequency.MONTHLY);
    }

}
