package test.com.bitduabi.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.DeveloperBitDubai;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by gianco on 03/05/16.
 */
public class DeveloperBitDubaiTest {

    @Test
    public void constructorTest (){
        DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
        Assert.assertNotNull(developerBitDubai);
    }

    @Test
    public void getterTest(){
        DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();

        Assert.assertEquals(developerBitDubai.getAddress(), "19qRypu7wrndwW4FRCxU1JPr5we134Q3eh");

        Assert.assertEquals(developerBitDubai.getAmountToPay(), 100);

        Assert.assertEquals(developerBitDubai.getCryptoCurrency(), CryptoCurrency.BITCOIN);

        Assert.assertEquals(developerBitDubai.getTimePeriod(), TimeFrequency.MONTHLY);
    }

}
