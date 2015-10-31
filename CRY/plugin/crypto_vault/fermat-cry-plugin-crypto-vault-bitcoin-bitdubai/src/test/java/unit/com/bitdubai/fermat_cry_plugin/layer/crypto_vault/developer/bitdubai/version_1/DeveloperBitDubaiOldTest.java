package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.DeveloperBitDubaiOld;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by rodrigo on 2015.07.15..
 */
public class DeveloperBitDubaiOldTest {

    @Test
    public void constructorTest (){
        DeveloperBitDubaiOld developerBitDubaiOld = new DeveloperBitDubaiOld();
        Assert.assertNotNull(developerBitDubaiOld.getPlugin());
    }

    @Test
    public void getterTest(){
        DeveloperBitDubaiOld developerBitDubaiOld = new DeveloperBitDubaiOld();
        Assert.assertEquals(developerBitDubaiOld.getAddress(), "13gpMizSNvQCbJzAPyGCUnfUGqFD8ryzcv");

        Assert.assertEquals(developerBitDubaiOld.getAmountToPay(), 100);

        Assert.assertEquals(developerBitDubaiOld.getCryptoCurrency(), CryptoCurrency.BITCOIN);

        Assert.assertEquals(developerBitDubaiOld.getTimePeriod(), TimeFrequency.MONTHLY);
    }
}
