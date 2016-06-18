package unit.com.bitdubai.fermat_pip_plugin.layer.module.developer.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_pip_plugin.layer.sub_app_module.developer.developer.bitdubai.DeveloperBitDubai;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Nerio on 18/07/15.
 */
public class DeveloperBitDubaiTest {

    @Test
    public void constructorTest() {
        DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
        Assert.assertNotNull(developerBitDubai.getPlugin());
    }

    @Test
    public void getterTest() {
        DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
        Assert.assertEquals(developerBitDubai.getAddress(), "19qRypu7wrndwW4FRCxU1JPr5hvMmcQ3eh");

        Assert.assertEquals(developerBitDubai.getAmountToPay(), 50);

        Assert.assertEquals(developerBitDubai.getCryptoCurrency(), CryptoCurrency.BITCOIN);

        Assert.assertEquals(developerBitDubai.getTimePeriod(), TimeFrequency.MONTHLY);
    }

}
