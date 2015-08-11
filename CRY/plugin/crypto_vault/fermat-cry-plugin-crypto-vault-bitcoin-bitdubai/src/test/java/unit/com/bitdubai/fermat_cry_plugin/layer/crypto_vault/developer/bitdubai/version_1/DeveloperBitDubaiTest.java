package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.DeveloperBitDubai;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by rodrigo on 2015.07.15..
 */
public class DeveloperBitDubaiTest {

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
