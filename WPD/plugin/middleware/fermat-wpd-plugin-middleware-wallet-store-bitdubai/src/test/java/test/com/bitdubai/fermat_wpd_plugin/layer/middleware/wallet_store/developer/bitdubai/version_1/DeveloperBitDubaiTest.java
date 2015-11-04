package test.com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_store.developer.bitdubai.DeveloperBitDubai;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Nerio on 22/08/15.
 */
public class DeveloperBitDubaiTest {

    // TODO PLEASE DON'T COPY AND PASTE WITHOUT CHECK AFTER...
    @Test
    public void constructorTest (){
        DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
        Assert.assertNotNull(developerBitDubai.getPlugin());
    }

    @Test
    public void getterTest(){
        DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();

        Assert.assertEquals(developerBitDubai.getAddress(), "13gpMizSNvQCbJzAPyGCUnfUGqFD8ryzcv");

        Assert.assertEquals(developerBitDubai.getAmountToPay(), 100);

        Assert.assertEquals(developerBitDubai.getCryptoCurrency(), CryptoCurrency.BITCOIN);

        Assert.assertEquals(developerBitDubai.getTimePeriod(), TimeFrequency.MONTHLY);
    }
}