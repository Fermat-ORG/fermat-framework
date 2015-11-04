package unit.com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.DeveloperBitDubai;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.DeveloperBitDubai;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by natalia on 09/09/15.
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
