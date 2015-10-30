package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1;

import com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.DeveloperBitDubaiOld;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by rodrigo on 2015.07.14..
 */
public class DeveloperBitDubaiTest {
    @Test
    public void test(){
        DeveloperBitDubaiOld developerBitDubaiOld = new DeveloperBitDubaiOld();
        Assert.assertNotNull(developerBitDubaiOld.getPlugin());
    }
}
